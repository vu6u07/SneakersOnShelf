package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Sole;
import com.sos.service.SoleService;

@RestController
@RequestMapping(value = "/admin/v1")
public class SoleAdminRestController {

	@Autowired
	private SoleService soleService;

	// @formatter:off
	@PostMapping(value = "/soles")
	public ResponseEntity<?> post(@RequestBody @Valid Sole sole, HttpServletRequest request) throws URISyntaxException {
		Sole created = soleService.save(sole);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}
	
	@GetMapping(value = "/soles/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.of(soleService.findById(id));
	}

	@GetMapping(value = "/soles")
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) ActiveStatus activeStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(soleService.findAll(query, activeStatus, PageRequest.of(page - 1, size)));
	}
	
	@PutMapping(value = "/soles/{id}/status")
	public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id, @RequestBody ActiveStatus activeStatus){
		soleService.updateSoleStatus(id, activeStatus);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/soles/{id}/name")
	public ResponseEntity<?> updateMaterialName(@PathVariable(name = "id") int id, @RequestBody @Valid Sole sole){
		soleService.updateSoleName(id, sole.getName());
		return ResponseEntity.noContent().build();
	}
	
}
