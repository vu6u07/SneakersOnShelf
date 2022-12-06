package com.sos.service;

import java.util.List;

import com.sos.entity.MemberOfferPolicy;

public interface MemberOfferPolicyService {

	List<MemberOfferPolicy> getMemberOfferPolicies();
	
	void rewardPoint(String orderId, long total);
	
	MemberOfferPolicy getMemberOfferPolicyByPoint(long point);
	
	MemberOfferPolicy getMemberOfferPolicyByAccountId(int id);
	
	void updateMemberOfferPolicy(MemberOfferPolicy MemberOfferPolicy);
}
