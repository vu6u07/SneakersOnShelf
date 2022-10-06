package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.SorterConstant.ProductSorter;
import com.sos.service.ProductService;

@RestController
@RequestMapping(value = "/content/v1/products")
public class ProductDTORestController {

	@Autowired
	private ProductService productService;

	// @formatter:off
	@GetMapping(params = "page")
	public ResponseEntity<?> get(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity.ok(productService.findCollectionProductDTO(PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(productService.findProductInfoDTOById(id));
	}

}
