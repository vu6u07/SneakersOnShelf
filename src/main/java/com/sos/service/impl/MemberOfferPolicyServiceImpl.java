package com.sos.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.MemberRank;
import com.sos.dto.AccountDTO;
import com.sos.entity.MemberOfferPolicy;
import com.sos.repository.AccountRepository;
import com.sos.repository.MemberOfferPolicyRepository;
import com.sos.service.MemberOfferPolicyService;

@Service
public class MemberOfferPolicyServiceImpl implements MemberOfferPolicyService {

	@Autowired
	private MemberOfferPolicyRepository memberOfferPolicyRepository;

	@Autowired
	private AccountRepository accountRepository;

	private Map<MemberRank, MemberOfferPolicy> memberOfferPolicies;

	@PostConstruct
	private void init() {
		memberOfferPolicies = memberOfferPolicyRepository.findAll().stream()
				.collect(Collectors.toMap(MemberOfferPolicy::getMemberRank, Function.identity()));
	}

	@Override
	public List<MemberOfferPolicy> getMemberOfferPolicies() {
		return memberOfferPolicyRepository.findAll(Sort.by("requiredPoint").descending());
	}

	@Transactional
	@Override
	public void rewardPoint(String orderId, long point) {
		Optional<AccountDTO> accountOptional = memberOfferPolicyRepository.getAccountDTOByOrderId(orderId);
		if (accountOptional.isPresent()) {
			AccountDTO account = accountOptional.get();
			long total = account.getPoint() + point;
			memberOfferPolicyRepository.updateAccountPoint(account.getId(), total);
		}
	}

	@Override
	public MemberOfferPolicy getMemberOfferPolicyByAccountId(int id) {
		Optional<AccountDTO> accountOptional = accountRepository.findAccountInfoDTOById(id);
		if (accountOptional.isPresent()) {
			return getMemberOfferPolicyByPoint(accountOptional.get().getPoint());
		}
		return new MemberOfferPolicy(0, MemberRank.CLIENT, 0, 0);
	}

	@Override
	public MemberOfferPolicy getMemberOfferPolicyByPoint(long point) {
		int offer = 0;
		MemberOfferPolicy rs = new MemberOfferPolicy(0, MemberRank.CLIENT, 0, 0);
		for (MemberOfferPolicy memberOfferPolicy : memberOfferPolicies.values()) {
			if (point >= memberOfferPolicy.getRequiredPoint() && memberOfferPolicy.getOffer() > offer) {
				offer = memberOfferPolicy.getOffer();
				rs = memberOfferPolicy;
			}
		}
		return rs;
	}

	@Transactional
	@Override
	public void updateMemberOfferPolicy(MemberOfferPolicy memberOfferPolicy) {
		if (memberOfferPolicy.getOffer() < 0) {
			throw new ValidationException("Offer not valid.");
		}
		
		if (memberOfferPolicy.getRequiredPoint() < 0) {
			throw new ValidationException("Required point not valid.");
		}
		
		memberOfferPolicyRepository.updateMemberOfferPolicy(memberOfferPolicy.getId(), memberOfferPolicy.getOffer(), memberOfferPolicy.getRequiredPoint());
	}

}
