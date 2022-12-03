package com.sos.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.TransactionStatus;
import com.sos.common.ApplicationConstant.TransactionType;
import com.sos.dto.CreateOrderMomoRequest;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.dto.TransactionDTO;
import com.sos.entity.Order;
import com.sos.entity.Transaction;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.TransactionRepository;
import com.sos.service.PaymentPartnerService;

import brave.Tracer;

@Service
public class MomoPaymentPartnerServiceImpl implements PaymentPartnerService<PurchaseInfoDTO> {

	private static final Logger logger = LoggerFactory.getLogger(MomoPaymentPartnerServiceImpl.class);

	@Autowired
	private Tracer tracer;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${parnet.payment.momo.partner-code}")
	private String PARTNER_CODE;

	@Value("${parnet.payment.momo.signature.algorith}")
	private String ALGORITH;

	@Value("${parnet.payment.momo.signature.access-key}")
	private String accessKey;

	@Value("${parnet.payment.momo.signature.secret-key}")
	private String secretKey;

	@Value("${parnet.payment.momo.domain}")
	private String partnerDomain;

	@Value("${parnet.payment.momo.api}")
	private String partnerApi;

	@Value("${sos.client.domain}")
	private String clientDomain;

	@Value("${sos.server.domain}")
	private String serverDomain;

	@Override
	public String getPayUrl(PurchaseInfoDTO entity) throws Exception {
		logger.info("Đang tạo pay url với momo cho đơn hàng {}", entity.getId());
		// Transaction
		Date date = new Date();
		Transaction transaction = new Transaction();
		transaction.setOrder(new Order(entity.getId()));
		transaction.setPaymentMethod(PaymentMethod.BANKING);
		transaction.setCreateDate(date);
		transaction.setAmount(entity.getTotal() + entity.getSurcharge() + entity.getFee() - entity.getDiscount());
		transaction.setTransactionStatus(TransactionStatus.PENDING);
		transaction.setTransactionType(TransactionType.PAYMENT);
		transaction.setUpdateDate(date);
		transactionRepository.save(transaction);

		CreateOrderMomoRequest data = new CreateOrderMomoRequest();
		data.setPartnerCode(PARTNER_CODE);
		data.setRequestId(tracer.currentSpan().context().traceIdString());
		data.setAmount(transaction.getAmount());
		data.setOrderId(UUID.randomUUID().toString());
		data.setOrderInfo(String.format("Thanh toán đơn hàng %s trên website %s", entity.getId(), "Sneakers On Shelf"));
		data.setRedirectUrl(clientDomain.concat("/purchase/").concat(entity.getId())
				.concat(StringUtils.hasText(entity.getToken()) ? entity.getToken() : ""));
		data.setIpnUrl(String.format("%s/api/v1/partner/payment/transactions/%s", serverDomain, transaction.getId()));
		data.setRequestType("captureWallet");
		data.setExtraData(passwordEncoder.encode(String.format("%s_sos_%s", entity.getId(), transaction.getId())));
		data.setLang("vi");
		data.setSignature(getSignature(data));

		// Momo
		@SuppressWarnings("rawtypes")
		ResponseEntity<LinkedHashMap> response = null;
		response = restTemplate.postForEntity(partnerDomain.concat(partnerApi), data, LinkedHashMap.class);
		if (response.getBody().get("resultCode").equals(0)) {
			return response.getBody().get("payUrl").toString();
		}
		logger.info("Momo phản hồi không mong muốn : {}", response);
		throw new RestClientException("Nhận được phản hồi không hợp lệ.");
	}

	@Transactional
	@Override
	public void confirmTransaction(int id, JsonNode data) {
		logger.info("Momo trả về : {}", data);
		if (data.get("resultCode").asInt() != 0) {
			transactionRepository.updateTransactionStatus(id, TransactionStatus.FAILED);
			throw new RestClientException("Momo thông báo giao dịch không thành công.");
		}
		TransactionDTO transactionDTO = transactionRepository.findTransactionDTOById(id, TransactionStatus.PENDING)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id : " + id));
		if (data.get("amount").asLong() != transactionDTO.getAmount()) {
			throw new RestClientException("Số tiền giao dịch không khớp.");
		}
		if (passwordEncoder.matches(String.format("%s_sos_%s", transactionDTO.getOrderId(), transactionDTO.getId()),
				data.get("signature").asText())) {
			throw new RestClientException("Signature không khớp.");
		}
		if (transactionRepository.updateTransactionStatus(transactionDTO.getId(), TransactionStatus.APPROVED) != 1) {
			throw new RestClientException("Cập nhật trạng thái transaction không thành công.");
		}
	}

	private String getSignature(CreateOrderMomoRequest data)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String format = "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s";
		String rs = String.format(format, accessKey, data.getAmount(), data.getExtraData(), data.getIpnUrl(),
				data.getOrderId(), data.getOrderInfo(), data.getPartnerCode(), data.getRedirectUrl(),
				data.getRequestId(), data.getRequestType());
		return encode(rs, secretKey);
	}

	private String encode(String data, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITH);
		Mac mac = Mac.getInstance(ALGORITH);
		mac.init(secretKeySpec);
		byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
		return toHexString(rawHmac);
	}

	private String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return sb.toString();
	}
}
