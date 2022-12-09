package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.service.SoleService;

@RestController
@RequestMapping(value = "/api/v1")
public class SoleRestController {

	@Autowired
	private SoleService soleService;

	@GetMapping(value = "/soles")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(soleService.findAll(null, ActiveStatus.ACTIVE, PageRequest.of(0, 100)));
	}

}
