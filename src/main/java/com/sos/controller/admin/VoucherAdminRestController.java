package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.common.ApplicationConstant.VoucherType;
import com.sos.entity.Voucher;
import com.sos.security.AccountAuthentication;
import com.sos.service.VoucherService;

@RestController
@RequestMapping(value = "/admin/v1")
public class VoucherAdminRestController {

	@Autowired
	private VoucherService voucherService;

	// @formatter:off
	@PostMapping(value = "/vouchers")
	public ResponseEntity<?> post(@RequestBody Voucher voucher, AccountAuthentication authentication, HttpServletRequest request) throws URISyntaxException {
		Voucher created = voucherService.save(voucher, authentication);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}
	
	@GetMapping(value = "/vouchers/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.of(voucherService.findById(id));
	}

	@GetMapping(value = "/vouchers")
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "type", required = false) VoucherType voucherType,
			@RequestParam(name = "access", required = false) VoucherAccess voucherAccess,
			@RequestParam(name = "status", required = false) VoucherStatus voucherStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(voucherService.findAll(query, voucherType, voucherAccess, voucherStatus, PageRequest.of(page - 1, size)));
	}
	
	@GetMapping(value = "/vouchers/available")
	public ResponseEntity<?> getAvailable(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(voucherService.findAllAvailableVoucher(new Date(), PageRequest.of(page - 1, size)));
	}
	
	@GetMapping(value = "/vouchers/available", params = {"query"})
	public ResponseEntity<?> getAvailable(
			@RequestParam(name = "query") String query,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(voucherService.findAllAvailableVoucher(query, new Date(), PageRequest.of(page - 1, size)));
	}
	
	@DeleteMapping(value = "/vouchers/{id}")
	public ResponseEntity<?> deleteVoucher(@PathVariable(name = "id") int id){
		voucherService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	// @formatter:on

}
