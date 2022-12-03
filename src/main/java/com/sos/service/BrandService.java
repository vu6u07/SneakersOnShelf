package com.sos.service;

import java.util.List;

import com.sos.dto.BrandRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.common.ApplicationConstant.ActiveStatus;
import com.sos.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService extends CrudService<Brand, Integer> {
    Page<Brand> findAll(String query, Pageable pageable);

	Brand save(BrandRequest brandRequest);

	List<Brand> findAll(ActiveStatus activeStatus);
	
	Page<Brand> findAll(String query, ActiveStatus activeStatus, Pageable pageable);
	
}
