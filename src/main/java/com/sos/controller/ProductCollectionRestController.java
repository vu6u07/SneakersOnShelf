package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.Benefit;
import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.common.ApplicationConstant.ShoeFeel;
import com.sos.common.ApplicationConstant.ShoeHeight;
import com.sos.common.ApplicationConstant.Surface;
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
			@RequestParam(name = "color", required = false) Integer colorId,
			@RequestParam(name = "sole", required = false) Integer soleId,
			@RequestParam(name = "material", required = false) Integer materialId,
			@RequestParam(name = "height", required = false) ShoeHeight shoeHeight,
			@RequestParam(name = "benefit", required = false) Benefit benefit,
			@RequestParam(name = "feel", required = false) ShoeFeel shoeFeel,
			@RequestParam(name = "surface", required = false) Surface surface,
			@RequestParam(name = "gender", required = false) ProductGender productGender,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "9") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		if(sorter == ProductSorter.best_selling) {
			return ResponseEntity.ok(productService.findBestSellingProductDTO(query, brandId, categoryId, colorId, soleId, materialId, shoeHeight, benefit, shoeFeel, surface, productGender, ProductStatus.ACTIVE, PageRequest.of(page - 1, size)));
		}
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(query, brandId, categoryId, colorId, soleId, materialId, shoeHeight, benefit, shoeFeel, surface,  productGender,ProductStatus.ACTIVE, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

}
