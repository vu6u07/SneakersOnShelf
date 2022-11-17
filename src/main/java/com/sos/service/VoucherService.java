package com.sos.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.entity.Voucher;
import com.sos.security.AccountAuthentication;

public interface VoucherService extends CrudService<Voucher, Integer>{
	
	Voucher save(Voucher voucher, AccountAuthentication authentication);
	
	Page<Voucher> findAll(String query, Pageable pageable);
	
	Page<Voucher> findAll(VoucherStatus voucherStatus, Pageable pageable);
	
	Page<Voucher> findAllAvailableVoucher(Date date, Pageable pageable);
	
	Page<Voucher> findAllAvailableVoucher(String query, Date date, Pageable pageable);
	
	//User
	Page<Voucher> findAllAvailableVoucher(Date date, VoucherStatus voucherStatus, VoucherAccess voucherAccess, Pageable pageable);
	
	Page<Voucher> findAvailableVoucherByCode(String code, Date date, Pageable pageable);
}
