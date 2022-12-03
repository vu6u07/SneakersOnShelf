package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sos.dto.BrandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.common.SorterConstant.BrandSorter;
import com.sos.entity.Brand;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.BrandService;

@RestController
@RequestMapping(value = "/admin/v1/brands")
public class BrandAdminRestController {

	private static Logger logger = LoggerFactory.getLogger(BrandAdminRestController.class);

	@Autowired
	private BrandService brandService;

	// @formatter:off
	@GetMapping
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) ActiveStatus activeStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") BrandSorter brandSorter) {
		return ResponseEntity.ok(brandService.findAll(query, activeStatus, PageRequest.of(page - 1, size, brandSorter.getSort())));
	}
	// @formatter:on

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(brandService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brand not found with id : " + id)));
	}

	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody BrandRequest brand, HttpServletRequest request) throws URISyntaxException {
		Brand created = brandService.save(brand);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		logger.info("Inactive brand with id : " + id);
		brandService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
