package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ProductStatus;
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
			@RequestParam(name = "size", required = false) String sizeName,
			@RequestParam(name = "brand", required = false) String brandId,
			@RequestParam(name = "category", required = false) String categoryId,
			@RequestParam(name = "color", required = false) String colorId,
			@RequestParam(name = "sole", required = false) String soleId,
			@RequestParam(name = "material", required = false) String materialId,
			@RequestParam(name = "height", required = false) String shoeHeight,
			@RequestParam(name = "benefit", required = false) String benefit,
			@RequestParam(name = "feel", required = false) String shoeFeel,
			@RequestParam(name = "surface", required = false) String surface,
			@RequestParam(name = "gender", required = false) String productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "psize", defaultValue = "9") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		if(sorter == ProductSorter.best_selling) {
			return ResponseEntity.ok(productService.findBestSellingProductDTO(query, sizeName, brandId, categoryId, colorId, soleId, materialId, shoeHeight, benefit, shoeFeel, surface, productGender, ProductStatus.ACTIVE, PageRequest.of(page - 1, size)));
		}
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(query, sizeName, brandId, categoryId, colorId, soleId, materialId, shoeHeight, benefit, shoeFeel, surface, productGender, ProductStatus.ACTIVE, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

}
