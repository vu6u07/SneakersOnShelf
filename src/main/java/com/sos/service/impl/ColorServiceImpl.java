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
import com.sos.entity.Color;
import com.sos.repository.ColorRepository;
import com.sos.service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorRepository colorRepository;

	@Override
	public List<Color> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Color> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Color> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color save(Color entity) {
		entity.setId(0);
		entity.setActiveStatus(ActiveStatus.ACTIVE);
		return colorRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Color> findAll(String query, ActiveStatus activeStatus, Pageable pageable) {
		return colorRepository.findAll(StringUtils.hasText(query) ? "%" + query + "%" : null, activeStatus, pageable);
	}

	@Transactional
	@Override
	public void updateColorStatus(int id, ActiveStatus activeStatus) {
		colorRepository.updateColorStatus(id, activeStatus);
	}

	@Transactional
	@Override
	public void updateColor(int id, Color color) {
		colorRepository.updateColor(id, color.getCode());
	}

}
