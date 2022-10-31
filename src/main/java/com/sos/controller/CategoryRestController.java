package com.sos.controller;

import com.sos.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/categorys")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    private static Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(categoryService.findAll());
    }


}
