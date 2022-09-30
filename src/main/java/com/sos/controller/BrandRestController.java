package com.sos.controller;

import java.net.URI;
import java.net.URISyntaxException;

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

import com.sos.common.SorterConstant.BrandSorter;
import com.sos.entity.Brand;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.BrandService;

@RestController
@RequestMapping(value = "/api/v1/brands")
public class BrandRestController {

	@Autowired
	private BrandService brandService;

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(brandService.findAll());
	}

	// @formatter:off
	@GetMapping(params = "page")
	public ResponseEntity<?> get(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") BrandSorter brandSorter) {
		return ResponseEntity.ok(brandService.findAll(PageRequest.of(page - 1, size, brandSorter.getSort())));
	}
	// @formatter:on

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(brandService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brand not found with id : " + id)));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Brand brand, HttpServletRequest request) throws URISyntaxException {
		Brand created = brandService.save(brand);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		brandService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
