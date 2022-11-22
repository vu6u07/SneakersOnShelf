package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.SorterConstant.ProductSorter;
import com.sos.service.ProductService;

@RestController
@RequestMapping(value = "/content/v1/products")
public class ProductCollectionRestController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(productService.findProductInfoDTOById(id));
	}

	// @formatter:off
	@GetMapping
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "brand", required = false) Integer brandId,
			@RequestParam(name = "category", required = false) Integer categoryId,
			@RequestParam(name = "gender", required = false) ProductGender productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "9") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		if(sorter == ProductSorter.best_selling) {
			return ResponseEntity.ok(productService.findBestSellingProductDTO(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, productGender, PageRequest.of(page - 1, size)));
		}
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, productGender, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

}
