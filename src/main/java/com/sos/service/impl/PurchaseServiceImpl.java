package com.sos.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.sos.common.ApplicationConstant.PaymentMethod;
import com.sos.common.ApplicationConstant.PaymentStatus;
import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.CustomerInfoRepository;
import com.sos.repository.DeliveryRepository;
import com.sos.repository.OrderRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private CustomerInfoRepository customerInfoRepository;

	@Value("${vietqr.bank.id}")
	private String bankId;

	@Value("${vietqr.account.id}")
	private String accountId;

	@Value("${vietqr.account.name}")
	private String accountName;

	@Value("${vietqr.template}")
	private String vietQRTemplate;

	@Override
	public PurchaseInfoDTO findPurchaseDTO(UUID id, String userTokenQuery) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id, userTokenQuery)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));

		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setDelivery(deliveryRepository.findByOrderId(purchaseDTO.getId()));
		purchaseDTO.setCustomerInfo(customerInfoRepository.findCustomerInfoFromOrder(purchaseDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin khách hàng")));
		if (purchaseDTO.getPaymentMethod() == PaymentMethod.BANKING
				&& purchaseDTO.getPaymentStatus() == PaymentStatus.PENDING) {
			long total = purchaseDTO.getTotal()
					+ (purchaseDTO.getDelivery() == null ? 0 : purchaseDTO.getDelivery().getFee())
					+ purchaseDTO.getSurcharge() - purchaseDTO.getDiscount();
			purchaseDTO.setPaymentQRCode(getPaymentQRCode(bankId, accountId, vietQRTemplate, total,
					String.format("%s SneakersOnShelf ThanhToan", purchaseDTO.getId()), accountName));
		}
		return purchaseDTO;
	}

	@Override
	public PurchaseInfoDTO findPurchaseDTO(UUID id, AccountAuthentication authentication) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));

		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setDelivery(deliveryRepository.findByOrderId(purchaseDTO.getId()));
		purchaseDTO.setCustomerInfo(customerInfoRepository.findCustomerInfoFromOrder(purchaseDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin khách hàng")));
		if (purchaseDTO.getPaymentMethod() == PaymentMethod.BANKING
				&& purchaseDTO.getPaymentStatus() == PaymentStatus.PENDING) {
			long total = purchaseDTO.getTotal()
					+ (purchaseDTO.getDelivery() == null ? 0 : purchaseDTO.getDelivery().getFee())
					+ purchaseDTO.getSurcharge() - purchaseDTO.getDiscount();
			purchaseDTO.setPaymentQRCode(getPaymentQRCode(bankId, accountId, vietQRTemplate, total,
					String.format("%s SneakersOnShelf ThanhToan", purchaseDTO.getId()), accountName));
		}
		return purchaseDTO;
	}

	// @formatter:off
	private String getPaymentQRCode(String bankId, String accountId, String vietQRTemplate, long amount, String addInfo, String accountName) {
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("img.vietqr.io")
				.path("/image/{imgName}")
				.query("amount={amount}")
				.query("addInfo={addInfo}")
				.query("accountName={accountName}")
				.buildAndExpand(String.format("%s-%s-%s.png", bankId, accountId, vietQRTemplate), amount, addInfo, accountName);
		return uriComponents.toUriString();
	}
	// @formatter:on

	@Override
	public Page<PurchaseDTO> findAllPurchaseDTOByAccountId(AccountAuthentication accountAuthentication,
			Pageable pageable) {
		return orderRepository.findAllPurchaseDTOByAccountId(accountAuthentication.getId(), pageable);
	}

}
