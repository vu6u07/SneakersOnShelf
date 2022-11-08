package com.sos.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/admin/v1/productDetails")
public class ProductDetailAdminController extends BaseController{


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(productDetailService.findProductDetailByProductID(id));
    }
}
