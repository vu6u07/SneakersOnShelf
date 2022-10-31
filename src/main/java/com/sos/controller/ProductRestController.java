package com.sos.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sos.common.SorterConstant.BrandSorter;
import com.sos.entity.Product;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.ProductService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductRestController {

	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(productService.findAll());
	}

	// @formatter:off
	@GetMapping(params = "page")
	public ResponseEntity<?> get(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") BrandSorter sorter) {
		return ResponseEntity.ok(productService.findAll(PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		System.out.println("IDDD"+productService.findProductInfoDTOById(id).getId());
		return ResponseEntity.ok(productService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id)));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Product product, HttpServletRequest request) throws URISyntaxException {
		Product created = productService.save(product);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}

	@DeleteMapping(value = "delete/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		productService.deleteById(id);
		logger.info("Deleted product with id : " + id);
		return ResponseEntity.noContent().build();
	}

}
