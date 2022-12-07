package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Color;

public interface ColorService extends CrudService<Color, Integer> {

	Page<Color> findAll(String query, ActiveStatus activeStatus, Pageable pageable);

	void updateColorStatus(int id, ActiveStatus activeStatus);

	void updateColor(int id, Color color);

}
