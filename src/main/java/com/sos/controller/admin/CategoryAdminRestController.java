package com.sos.controller.admin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.entity.Category;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.CategoryService;

@RestController
@RequestMapping(value = "/admin/v1/categories")
public class CategoryAdminRestController {

	private static Logger logger = LoggerFactory.getLogger(CategoryAdminRestController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(categoryService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id)));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody Category category, HttpServletRequest request) throws URISyntaxException {
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
