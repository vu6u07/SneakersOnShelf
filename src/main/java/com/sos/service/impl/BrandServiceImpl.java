package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Brand;
import com.sos.repository.BrandRepository;
import com.sos.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public Page<Brand> findAll(Pageable pageable) {
		return brandRepository.findAll(pageable);
	}
	
	@Override
	public Optional<Brand> findById(Integer id) {
		return brandRepository.findById(id);
	}

	@Override
	public Brand save(Brand entity) {
		return brandRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Brand> findAll(ActiveStatus activeStatus) {
		return brandRepository.findAll(activeStatus);
	}
	
	@Override
	public Page<Brand> findAll(String query, ActiveStatus activeStatus, Pageable pageable) {
		return brandRepository.findAll(StringUtils.hasText(query) ? "%".concat(query).concat("%") : null, activeStatus, pageable);
	}
	
}
