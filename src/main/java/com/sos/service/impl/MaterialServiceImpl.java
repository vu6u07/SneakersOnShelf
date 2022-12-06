package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Material;
import com.sos.repository.MaterialRepository;
import com.sos.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	private MaterialRepository materialRepository;

	@Override
	public List<Material> findAll() {
		return null;
	}

	@Override
	public Page<Material> findAll(Pageable pageable) {
		return materialRepository.findAll(pageable);
	}

	@Override
	public Page<Material> findAll(String query, ActiveStatus activeStatus, Pageable pageable) {
		return materialRepository.findAll(StringUtils.hasText(query) ? "%" + query + "%" : null, activeStatus,
				pageable);
	}

	@Override
	public Optional<Material> findById(Integer id) {
		return materialRepository.findById(id);
	}

	@Override
	public Material save(Material entity) {
		entity.setId(0);
		entity.setActiveStatus(ActiveStatus.ACTIVE);
		return materialRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
	}

	@Transactional
	@Override
	public void updateMaterialStatus(int id, ActiveStatus activeStatus) {
		materialRepository.updateMaterialStatus(id, activeStatus);
	}

	@Transactional
	@Override
	public void updateMaterialName(int id, String name) {
		materialRepository.updateMaterialName(id, name);
	}
	
}
