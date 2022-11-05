package com.sos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.entity.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {

	@Query(value = "SELECT new com.sos.entity.CustomerInfo(o.customerInfo.id, o.customerInfo.fullname, o.customerInfo.phone, o.customerInfo.provinceId, o.customerInfo.provinceName, o.customerInfo.districtId, o.customerInfo.districtName, o.customerInfo.wardCode, o.customerInfo.wardName, o.customerInfo.address, o.customerInfo.createDate, o.customerInfo.updateDate) FROM Order o WHERE o.id = :orderId")
	Optional<CustomerInfo> findCustomerInfoFromOrder(int orderId);
	
	@Query(value = "SELECT o FROM CustomerInfo o WHERE o.account.id = :accountId")
	Optional<CustomerInfo> findCustomerByAccountId(int accountId);
}
