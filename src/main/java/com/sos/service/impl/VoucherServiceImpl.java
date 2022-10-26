package com.sos.service.impl;

import com.sos.entity.Voucher;
import com.sos.repository.VoucherRepository;
import com.sos.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	private VoucherRepository voucherRepository;

	@Override
	public List<Voucher> findAll() {
		return voucherRepository.findAll();
	}

	@Override
	public Page<Voucher> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Optional<Voucher> findById(Integer id) {
		return voucherRepository.findById(id);
	}

	@Override
	public Voucher save(Voucher entity) {
		return voucherRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {

	}

}
