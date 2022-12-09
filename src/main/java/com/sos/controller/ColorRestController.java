package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.service.ColorService;

@RestController
@RequestMapping(value = "/api/v1")
public class ColorRestController {

	@Autowired
	private ColorService colorService;

	@GetMapping(value = "/colors")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(colorService.findAll(null, ActiveStatus.ACTIVE, PageRequest.of(0, 100)));
	}

}
