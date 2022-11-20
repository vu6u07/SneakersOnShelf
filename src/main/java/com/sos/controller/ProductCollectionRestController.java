package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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

//	@GetMapping(value = "/{id}")
//	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
//		return ResponseEntity.ok(productService.findProductInfoDTOById(id));
//	}

	// @formatter:off
	@GetMapping
	public ResponseEntity<?> get(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = "brand")
	public ResponseEntity<?> getByBrand(
			@RequestParam(name = "brand") int brandId,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTOByBrandId(brandId, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = "gender")
	public ResponseEntity<?> getByProductGender(
			@RequestParam(name = "gender") ProductGender productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(productGender, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = "category")
	public ResponseEntity<?> getByCategory(
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTOByCategoryId(categoryId, PageRequest.of(page - 1, size, sorter.getSort())));
	}

	// @formatter:off
	@GetMapping(params = {"brand", "gender"})
	public ResponseEntity<?> getByBrandAndProductGender(
			@RequestParam(name = "brand") int brandId,
			@RequestParam(name = "gender") ProductGender productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(brandId, productGender, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = {"brand", "category"})
	public ResponseEntity<?> getByBrandAndCategory(
			@RequestParam(name = "brand") int brandId,
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(brandId, categoryId, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = {"brand", "category", "gender"})
	public ResponseEntity<?> getByBrandAndCategoryAndProductGender(
			@RequestParam(name = "brand") int brandId,
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "gender") ProductGender productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(brandId, categoryId, productGender, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	// @formatter:off
	@GetMapping(params = {"gender", "category"})
	public ResponseEntity<?> getByCategoryAndProductGender(
			@RequestParam(name = "gender") ProductGender productGender,
			@RequestParam(name = "category") int categoryId,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		return ResponseEntity
				.ok(productService.findCollectionProductDTOByCategoryIdAndProductGender(categoryId, productGender, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

}
