package com.sos.controller;

import com.sos.entity.Brand;
import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/vouchers")
public class VoucherController {
    private static Logger logger = LoggerFactory.getLogger(BrandRestController.class);

    @Autowired
    private VoucherService voucherService;


    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) throws ResourceNotFoundException {
        Voucher data = voucherService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found : " + id));
        return ResponseEntity.ok().body(data);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Voucher voucher) {
        try {
            Voucher created = voucherService.save(voucher);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("some messages", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
