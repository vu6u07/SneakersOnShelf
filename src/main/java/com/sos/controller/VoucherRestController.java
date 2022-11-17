package com.sos.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.VoucherService;

@RestController
@RequestMapping(value = "/api/v1")
public class VoucherRestController {
	private static Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

	@Autowired
	private VoucherService voucherService;

	@GetMapping(value = "/vouchers")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(voucherService.findAll());
	}

	@GetMapping(value = "/vouchers/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) throws ResourceNotFoundException {
		Voucher data = voucherService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Voucher not found : " + id));
		return ResponseEntity.ok().body(data);
	}

	@PostMapping(value = "/vouchers")
	public ResponseEntity<?> post(@RequestBody Voucher voucher) {
		try {
			Voucher created = voucherService.save(voucher);
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("some messages", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/vouchers/available")
	public ResponseEntity<?> getAvailable(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(voucherService.findAllAvailableVoucher(new Date(), VoucherStatus.ACTIVE,
				VoucherAccess.PUBLIC, PageRequest.of(page - 1, size)));
	}

	@GetMapping(value = "/vouchers/available", params = { "query" })
	public ResponseEntity<?> getAvailable(@RequestParam(name = "query") String query,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity
				.ok(voucherService.findAvailableVoucherByCode(query, new Date(), PageRequest.of(page - 1, size)));
	}

}
