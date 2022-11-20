package com.sos.controller.admin;

import com.sos.common.SorterConstant;
import com.sos.entity.Brand;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.BrandService;
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
@RequestMapping(value = "/admin/v1/brands")
public class BrandAdminController {
    private static Logger logger = LoggerFactory.getLogger(BrandRestController.class);

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(brandService.findAll());
    }

    // @formatter:off
    @GetMapping(params = "page")
    public ResponseEntity<?> get(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            @RequestParam(name = "sort", defaultValue = "id_asc") SorterConstant.BrandSorter brandSorter) {
        return ResponseEntity.ok(brandService.findAll(PageRequest.of(page - 1, size, brandSorter.getSort())));
    }
    // @formatter:on

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(brandService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id : " + id)));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Brand brand, HttpServletRequest request) throws URISyntaxException {
        Brand created = brandService.save(brand);
        return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        brandService.deleteById(id);
        logger.info("Deleted brand with id : " + id);
        return ResponseEntity.noContent().build();
    }
}
