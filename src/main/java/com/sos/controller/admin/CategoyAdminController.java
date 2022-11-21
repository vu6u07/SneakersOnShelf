package com.sos.controller.admin;

import com.sos.common.SorterConstant;
import com.sos.entity.Category;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping(value = "/admin/v1/categories")
public class CategoyAdminController {
    private static Logger logger = LoggerFactory.getLogger(CategoyAdminController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // @formatter:off
    @GetMapping(params = "page")
    public ResponseEntity<?> get(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            @RequestParam(name = "sort", defaultValue = "id_asc") SorterConstant.BrandSorter brandSorter) {
        return ResponseEntity.ok(categoryService.findAll(PageRequest.of(page - 1, size, brandSorter.getSort())));
    }
    // @formatter:on

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
        categoryService.deleteById(id);
        logger.info("Deleted category with id : " + id);
        return ResponseEntity.noContent().build();
    }
}
