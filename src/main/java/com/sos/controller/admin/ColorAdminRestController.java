package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.sos.entity.Color;
import com.sos.service.ColorService;

@RestController
@RequestMapping(value = "/admin/v1")
public class ColorAdminRestController {

	@Autowired
	private ColorService colorService;

	// @formatter:off
	@PostMapping(value = "/colors")
	public ResponseEntity<?> post(@RequestBody @Valid Color color, HttpServletRequest request) throws URISyntaxException {
		Color created = colorService.save(color);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}
	
	@GetMapping(value = "/colors/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.of(colorService.findById(id));
	}

	@GetMapping(value = "/colors")
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) ActiveStatus activeStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size) {
		return ResponseEntity.ok(colorService.findAll(query, activeStatus, PageRequest.of(page - 1, size, Sort.by("id").descending())));
	}
	
	@PutMapping(value = "/colors/{id}/status")
	public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id, @RequestBody ActiveStatus activeStatus){
		colorService.updateColorStatus(id, activeStatus);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/colors/{id}")
	public ResponseEntity<?> updateMaterialName(@PathVariable(name = "id") int id, @RequestBody @Valid Color color){
		colorService.updateColor(id, color);
		return ResponseEntity.noContent().build();
	}
	
}
