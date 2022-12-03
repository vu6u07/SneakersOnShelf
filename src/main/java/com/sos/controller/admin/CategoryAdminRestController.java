package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sos.common.ApplicationConstant;
import com.sos.common.SorterConstant;
import com.sos.dto.CategoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sos.entity.Category;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.CategoryService;

@RestController
@RequestMapping(value = "/admin/v1/categories")
public class CategoryAdminRestController {

	private static Logger logger = LoggerFactory.getLogger(CategoryAdminRestController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<?> get(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) ApplicationConstant.ActiveStatus activeStatus,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") SorterConstant.BrandSorter brandSorter) {
		return ResponseEntity.ok(categoryService.findAll(query, activeStatus, PageRequest.of(page - 1, size, brandSorter.getSort())));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(categoryService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id)));
	}

	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody CategoryRequest category, HttpServletRequest request) throws URISyntaxException {
		Category created = categoryService.save(category);
		return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
				.build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		logger.info("Inactive category with id : " + id);
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
