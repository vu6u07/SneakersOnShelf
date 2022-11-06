package com.sos.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.dto.OrderChartDataProjection;
import com.sos.dto.OrderStatisticProjection;
import com.sos.repository.StatisticRepository;
import com.sos.service.StatisticService;
import com.sos.service.util.DateUtil;

@Service
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private StatisticRepository statisticRepository;

	@Override
	public OrderStatisticProjection getOrderStatisticProjection(Date date) {
		return statisticRepository.getOrderStatisticProjection(DateUtil.convertToString(date),
				DateUtil.convertToString(DateUtil.getFirstDayOfThisMonth(date)));
	}
	
	@Override
	public List<OrderChartDataProjection> getOrderChartDataProjection(Date fromDate, Date toDate) {
		return statisticRepository.getOrderChartDataProjection(DateUtil.convertToString(fromDate), DateUtil.convertToString(toDate));
	}

}
