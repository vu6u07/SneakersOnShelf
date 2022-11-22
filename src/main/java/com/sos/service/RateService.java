package com.sos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sos.dto.RateDTO;
import com.sos.entity.Rate;
import com.sos.security.AccountAuthentication;

public interface RateService extends CrudService<Rate, Integer> {

	Page<RateDTO> findByProductDetailId(int id, Pageable pageable);

	Rate save(int orderItemId, Rate rate, AccountAuthentication authentication);

}
