package com.sos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.entity.MemberOfferPolicy;
import com.sos.service.MemberOfferPolicyService;

@RestController
@RequestMapping(value = "/api/v1")
public class MemberOfferPolicyController {

	@Autowired
	private MemberOfferPolicyService memberOfferPolicyService;

	@GetMapping(value = "/member-offer-policies")
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(memberOfferPolicyService.getMemberOfferPolicies());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/member-offer-policies/{id}")
	public ResponseEntity<?> put(@PathVariable(name = "id") int id, @RequestBody MemberOfferPolicy memberOfferPolicy) {
		memberOfferPolicy.setId(id);
		memberOfferPolicyService.updateMemberOfferPolicy(memberOfferPolicy);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("#id == authentication.id or hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/accounts/{id}/member-offer-policy")
	public ResponseEntity<?> getOfferByPoint(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(memberOfferPolicyService.getMemberOfferPolicyByAccountId(id));
	}

}
