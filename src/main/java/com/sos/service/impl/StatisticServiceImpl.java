package com.sos.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sos.dto.StatisticDTO.BestSellingProductStatistic;
import com.sos.dto.StatisticDTO.OrderChartDataProjection;
import com.sos.dto.StatisticDTO.OrderStatisticProjection;
import com.sos.dto.StatisticDTO.OrderStatusStatisticProjection;
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
		return statisticRepository.getOrderChartDataProjection(DateUtil.convertToString(fromDate),
				DateUtil.convertToString(toDate));
	}

	@Override
	public Page<BestSellingProductStatistic> getBestSellingProductStatistic(Date fromDate, Date toDate) {
		return statisticRepository.findBestSellingProductDTO(fromDate, toDate, PageRequest.of(0, 10));
	}

	@Override
	public List<OrderStatusStatisticProjection> getOrderStatusStatisticProjections(Date fromDate, Date toDate) {
		return statisticRepository.getOrderStatusStatisticProjections(DateUtil.convertToString(fromDate), DateUtil.convertToString(toDate));
	}
}
