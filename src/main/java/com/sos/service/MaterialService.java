package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Material;

public interface MaterialService extends CrudService<Material, Integer> {

	Page<Material> findAll(String query, ActiveStatus activeStatus, Pageable pageable);

	void updateMaterialStatus(int id, ActiveStatus activeStatus);

	void updateMaterialName(int id, String name);

}
