package com.sos.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sos.common.SorterConstant.BrandSorter;
import com.sos.entity.Account;
import com.sos.entity.CustomerInfo;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.CustomerService;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerRestController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(customerService.findAll());
	}
	
	// @formatter:off
		@GetMapping(params = "page")
		public ResponseEntity<?> get(
				@RequestParam(name = "page") int page,
				@RequestParam(name = "size", defaultValue = "8") int size,
				@RequestParam(name = "sort", defaultValue = "id_asc") BrandSorter sorter) {
			return ResponseEntity.ok(customerService.findAll(PageRequest.of(page - 1, size, sorter.getSort())));
		}
		
		// @formatter:on
		@GetMapping(value = "/{id}")
		public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
			return ResponseEntity.ok(customerService.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Customer not found with id : " + id)));
		}
		
		@PostMapping
		public ResponseEntity<?> post(@RequestBody CustomerInfo customer, HttpServletRequest request) throws URISyntaxException {
			CustomerInfo created = customerService.save(customer);
			return ResponseEntity.created(new URI(request.getRequestURL().append("/").append(created.getId()).toString()))
					.build();
		}
		
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
			customerService.deleteById(id);
			logger.info("Deleted customer with id : " + id);
			return ResponseEntity.noContent().build();
		}

}
