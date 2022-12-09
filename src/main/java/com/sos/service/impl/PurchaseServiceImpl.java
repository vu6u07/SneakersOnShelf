package com.sos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.sos.dto.PurchaseDTO;
import com.sos.dto.PurchaseInfoDTO;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.OrderRepository;
import com.sos.repository.OrderTimelineRepository;
import com.sos.repository.TransactionRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderTimelineRepository orderTimelineRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${vietqr.bank.id}")
	private String bankId;

	@Value("${vietqr.account.id}")
	private String accountId;

	@Value("${vietqr.account.name}")
	private String accountName;

	@Value("${vietqr.template}")
	private String vietQRTemplate;

	@Override
	public Page<PurchaseDTO> findAllPurchaseDTOByAccountId(AccountAuthentication accountAuthentication,
			Pageable pageable) {
		return orderRepository.findAllPurchaseDTOByAccountId(accountAuthentication.getId(), pageable);
	}

	@Override
	public PurchaseInfoDTO findPurchaseDTO(String id, String userTokenQuery) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id, userTokenQuery)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setTimelines(orderTimelineRepository.findOrderTimelineDTOsByOrderId(purchaseDTO.getId()));
		purchaseDTO.setTransactions(transactionRepository.findAllTransactionDTOByOrderId(purchaseDTO.getId()));
		long total = purchaseDTO.getTotal() + purchaseDTO.getFee() + purchaseDTO.getSurcharge()
				- purchaseDTO.getDiscount() - purchaseDTO.getMemberOffer() - purchaseDTO.getRefund();
		purchaseDTO.setPaymentQRCode(getPaymentQRCode(bankId, accountId, vietQRTemplate, total,
				String.format("%s SneakersOnShelf ThanhToan", purchaseDTO.getId()), accountName));
		return purchaseDTO;
	}

	@Override
	public PurchaseInfoDTO findPurchaseDTO(String id, AccountAuthentication authentication) {
		PurchaseInfoDTO purchaseDTO = orderRepository.findPurchaseInfoDTO(id, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
		purchaseDTO.setItems(orderRepository.findAllPurchaseItemDTO(purchaseDTO.getId()));
		purchaseDTO.setTimelines(orderTimelineRepository.findOrderTimelineDTOsByOrderId(purchaseDTO.getId()));
		purchaseDTO.setTransactions(transactionRepository.findAllTransactionDTOByOrderId(purchaseDTO.getId()));
		long total = purchaseDTO.getTotal() + purchaseDTO.getFee() + purchaseDTO.getSurcharge()
				- purchaseDTO.getDiscount() - purchaseDTO.getRefund();
		purchaseDTO.setPaymentQRCode(getPaymentQRCode(bankId, accountId, vietQRTemplate, total,
				String.format("%s SneakersOnShelf ThanhToan", purchaseDTO.getId()), accountName));
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

}
