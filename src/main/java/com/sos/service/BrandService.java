package com.sos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Brand;

public interface BrandService extends CrudService<Brand, Integer> {

	List<Brand> findAll(ActiveStatus activeStatus);
	
	Page<Brand> findAll(String query, ActiveStatus activeStatus, Pageable pageable);
	
}
