package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.common.SorterConstant.ProductSorter;
import com.sos.dto.vo.ProductVO;
import com.sos.entity.Product;
import com.sos.service.ProductService;

@RestController
@RequestMapping(value = "/admin/v1/products")
public class ProductAdminRestController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	// @formatter:off
	@GetMapping
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "brand", required = false) Integer brandId,
			@RequestParam(name = "category", required = false) Integer categoryId,
			@RequestParam(name = "gender", required = false) ProductGender productGender,
			@RequestParam(name = "status", required = false) ProductStatus productStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "9") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") ProductSorter sorter) {
		if(sorter == ProductSorter.best_selling) {
			return ResponseEntity.ok(productService.findBestSellingProductDTO(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, productGender, productStatus, PageRequest.of(page - 1, size)));
		}
		return ResponseEntity
				.ok(productService.findCollectionProductDTO(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, brandId, categoryId, productGender, productStatus, PageRequest.of(page - 1, size, sorter.getSort())));
	}
	// @formatter:on

	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody ProductVO productVO, HttpServletRequest request) throws URISyntaxException {
		Product created = productService.save(productVO);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString())).body(created);
	}
}
