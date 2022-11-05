package com.sos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.VoucherService;

@RestController
@RequestMapping(value = "/api/v1/vouchers")
public class VoucherRestController {
    private static Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

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
