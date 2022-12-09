package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Sole;
import com.sos.repository.SoleRepository;
import com.sos.service.SoleService;

@Service
public class SoleServiceImpl implements SoleService {

	@Autowired
	private SoleRepository soleRepository;

	@Override
	public List<Sole> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Sole> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Sole> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sole save(Sole entity) {
		entity.setId(0);
		entity.setActiveStatus(ActiveStatus.ACTIVE);
		return soleRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Sole> findAll(String query, ActiveStatus activeStatus, Pageable pageable) {
		return soleRepository.findAll(query, activeStatus, pageable);
	}

	@Transactional
	@Override
	public void updateSoleStatus(int id, ActiveStatus activeStatus) {
		soleRepository.updateSoleStatus(id, activeStatus);
	}

	@Transactional
	@Override
	public void updateSoleName(int id, String name) {
		soleRepository.updateSoleName(id, name);
	}

}
