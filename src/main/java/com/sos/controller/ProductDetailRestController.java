package com.sos.controller;

import com.sos.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/v1/productDetails")
public class ProductDetailRestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ProductDetailRestController.class);

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(productDetailService.findProductDetailByProductID(id));
    }
}
