package com.sos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.VoucherAccess;
import com.sos.common.ApplicationConstant.VoucherStatus;
import com.sos.common.ApplicationConstant.VoucherType;
import com.sos.entity.Account;
import com.sos.entity.Voucher;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.VoucherRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	private VoucherRepository voucherRepository;

	@Override
	public List<Voucher> findAll() {
		return null;
	}

	@Override
	public Optional<Voucher> findById(Integer id) {
		return voucherRepository.findVoucher(id);
	}

	@Override
	public Voucher save(Voucher entity) {
		return null;
	}

	@Override
	public Voucher save(Voucher voucher, AccountAuthentication authentication) {
		if (!voucher.getStartDate().before(voucher.getExperationDate())) {
			throw new ValidationException("Ngày bắt đầu và kết thúc voucher không hợp lệ.");
		}
		if (voucher.getExperationDate().before(new Date())) {
			throw new ValidationException("Voucher đã hết hạn.");
		}
		voucher.setId(0);
		voucher.setStaff(new Account(authentication.getId()));
		return voucherRepository.save(voucher);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		if (voucherRepository.updateVoucherStatus(id, VoucherStatus.INACTIVE) != 1) {
			throw new ResourceNotFoundException("Hệ thống đang bận, hãy thử lại sau.");
		}
	}

	@Override
	public Page<Voucher> findAll(Pageable pageable) {
		return voucherRepository.findAllVoucher(pageable);
	}

	@Override
	public Page<Voucher> findAll(String query, VoucherType voucherType, VoucherAccess voucherAccess,
			VoucherStatus voucherStatus, Pageable pageable) {
		return voucherRepository.findAllVoucher(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null,
				voucherType, voucherAccess, voucherStatus, pageable);
	}

	@Override
	public Page<Voucher> findAllAvailableVoucher(Date date, Pageable pageable) {
		return voucherRepository.findAllAvailableVoucher(date, VoucherStatus.ACTIVE, pageable);
	}

	@Override
	public Page<Voucher> findAllAvailableVoucher(String query, Date date, Pageable pageable) {
		return voucherRepository.findAllAvailableVoucher("%".concat(query).concat("%"), date, VoucherStatus.ACTIVE,
				pageable);
	}

	// User
	@Override
	public Page<Voucher> findAllAvailableVoucher(Date date, VoucherStatus voucherStatus, VoucherAccess voucherAccess,
			Pageable pageable) {
		return voucherRepository.findAllAvailableVoucher(date, voucherStatus, voucherAccess, pageable);
	}

	@Override
	public Page<Voucher> findAvailableVoucherByCode(String code, Date date, Pageable pageable) {
		return voucherRepository.findAvailableVoucherByCode(code, VoucherStatus.ACTIVE, date, pageable);
	}

}
