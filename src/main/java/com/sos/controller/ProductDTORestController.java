package com.sos.controller;

import com.sos.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sos.common.SorterConstant.ProductSorter;
import com.sos.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
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

	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@RequestBody Product product) {
		try {
			return ResponseEntity.ok(productService.save(product));
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
