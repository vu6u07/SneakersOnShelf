package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Sole;

public interface SoleService extends CrudService<Sole, Integer> {

	Page<Sole> findAll(String query, ActiveStatus activeStatus, Pageable pageable);

	void updateSoleStatus(int id, ActiveStatus activeStatus);

	void updateSoleName(int id, String name);

}
