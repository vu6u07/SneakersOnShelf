package com.sos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sos.entity.OrderTimeline;
import com.sos.repository.OrderTimelineRepository;
import com.sos.service.OrderTimelineService;

@Service
public class OrderTimelineServiceImpl implements OrderTimelineService {

	@Autowired
	private OrderTimelineRepository orderTimelineRepository;
	
	@Override
	public List<OrderTimeline> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OrderTimeline> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OrderTimeline> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderTimeline save(OrderTimeline entity) {
		return orderTimelineRepository.save(entity);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

}
