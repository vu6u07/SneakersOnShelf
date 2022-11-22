package com.sos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.dto.RateDTO;
import com.sos.entity.OrderItem;
import com.sos.entity.Rate;
import com.sos.exception.ResourceNotFoundException;
import com.sos.repository.OrderItemRepository;
import com.sos.repository.RateRepository;
import com.sos.security.AccountAuthentication;
import com.sos.service.RateService;

@Service
public class RateServiceImpl implements RateService {

	@Autowired
	private RateRepository rateRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public List<Rate> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Rate> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Rate> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rate save(Rate entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<RateDTO> findByProductDetailId(int id, Pageable pageable) {
		return rateRepository.findByProductDetailId(id, pageable);
	}

	@Transactional
	@Override
	public Rate save(int orderItemId, Rate rate, AccountAuthentication authentication) {
		OrderItem orderItem = orderItemRepository.findOrderItem(orderItemId, authentication.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng."));
		rate.setId(0);
		rate.setCreateDate(new Date());
		Rate created = rateRepository.save(rate);
		if (rateRepository.updadteRateIdOfOrderItem(orderItem.getId(), rate) < 1) {
			throw new ResourceNotFoundException("OrderItem not found");
		}
		return created;
	}

}
