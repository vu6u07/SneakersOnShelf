package com.sos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.common.ApplicationConstant.CustomerInfoStatus;
import com.sos.entity.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {

	@Query(value = "SELECT new com.sos.entity.CustomerInfo(o.customerInfo.id, o.customerInfo.fullname, o.customerInfo.phone, o.customerInfo.provinceId, o.customerInfo.provinceName, o.customerInfo.districtId, o.customerInfo.districtName, o.customerInfo.wardCode, o.customerInfo.wardName, o.customerInfo.address, o.customerInfo.createDate, o.customerInfo.updateDate) FROM Order o WHERE o.id = :orderId")
	Optional<CustomerInfo> findCustomerInfoFromOrder(UUID orderId);

	@Query(value = "SELECT new com.sos.entity.CustomerInfo(c.id, c.fullname, c.phone, c.provinceId, c.provinceName, c.districtId, c.districtName, c.wardCode, c.wardName, c.address, c.createDate, c.updateDate) FROM CustomerInfo c WHERE c.id = :id AND c.account.id = :accountId AND c.customerInfoStatus = :customerInfoStatus")
	Optional<CustomerInfo> findCustomerInfo(int id, int accountId, CustomerInfoStatus customerInfoStatus);

	@Query(value = "SELECT new com.sos.entity.CustomerInfo(c.id, c.fullname, c.phone, c.provinceId, c.provinceName, c.districtId, c.districtName, c.wardCode, c.wardName, c.address, c.createDate, c.updateDate) FROM CustomerInfo c WHERE c.account.id = :accountId AND c.customerInfoStatus = :customerInfoStatus")
	List<CustomerInfo> findByAccountId(int accountId, CustomerInfoStatus customerInfoStatus);

	@Modifying
	@Query(value = "UPDATE Account a SET a.customerInfo = :customerInfo WHERE a.id = :accountId AND a.customerInfo IS NULL")
	int setDefaultCustomerInfoIfNull(CustomerInfo customerInfo, int accountId);

	@Modifying
	@Query(value = "UPDATE Account a SET a.customerInfo = :customerInfo WHERE id = :accountId")
	int setDefaultCustomerInfo(CustomerInfo customerInfo, int accountId);

	@Modifying
	@Query(value = "UPDATE CustomerInfo c SET c.customerInfoStatus = :customerInfoStatus WHERE c.id = :id")
	int setCustomerInfoStatus(int id, CustomerInfoStatus customerInfoStatus);

}
