package com.sos.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import com.sos.common.ApplicationConstant.AccountStatus;
import com.sos.common.SorterConstant.BrandSorter;
import com.sos.entity.Account;
import com.sos.entity.Product;
import com.sos.exception.ResourceNotFoundException;
import com.sos.service.AccountService;

@RestController
@RequestMapping(value = "/api/v1/accounts")
public class AccountRestController {

	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private AccountService accountService;

//	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/accounts")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(accountService.findAll());
	}

	// @formatter:off
	@GetMapping(params = "page")
	public ResponseEntity<?> get(
			@RequestParam(name = "page") int page,
			@RequestParam(name = "size", defaultValue = "8") int size,
			@RequestParam(name = "sort", defaultValue = "id_asc") BrandSorter sorter) {
		return ResponseEntity.ok(accountService.findAll(PageRequest.of(page - 1, size, sorter.getSort())));
	}
	
	@PostMapping
	public ResponseEntity<?> post(@RequestBody Account account, HttpServletRequest request) throws URISyntaxException {
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setCreateDate(new Date());
		Account created = accountService.save(account);
		return ResponseEntity.created(new URI("/api/v1/accounts/" + created.getId())).body(created);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity put(@PathVariable int id, @RequestBody Account account) {
		Account currentAccount = accountService.findById(id).orElseThrow(RuntimeException::new);
		currentAccount.setEmail(account.getEmail());
		currentAccount.setPassword(account.getPassword());
		currentAccount.setUpdateDate(new Date());
		currentAccount = accountService.save(account);

        return ResponseEntity.ok(currentAccount);
    }
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
		accountService.deleteById(id);
		logger.info("Deleted account with id : " + id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize(value = "hasRole('ROLE_USER') and #id == authentication.id")
	@GetMapping(value = "/accounts/{id}")
	public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
		return ResponseEntity.of(accountService.findAccountDTOById(id));
	}

}
