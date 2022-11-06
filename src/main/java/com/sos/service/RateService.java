package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.dto.RateDTO;
import com.sos.entity.Rate;

public interface RateService extends CrudService<Rate, Integer> {

	Page<RateDTO> findByProductDetailId(int id, Pageable pageable);

	Rate save(int orderItemId, Rate rate);

}
