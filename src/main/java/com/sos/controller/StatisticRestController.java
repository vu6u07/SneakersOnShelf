package com.sos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.StatisticService;
import com.sos.service.util.DateUtil;

@RestController
@RequestMapping(value = "/api/v1")
public class StatisticRestController {

	@Autowired
	private StatisticService statisticService;

	@GetMapping(value = "/statistics/orders")
	public ResponseEntity<?> getOrderStatistic() {
		return ResponseEntity.ok(statisticService.getOrderStatisticProjection(new Date()));
	}
	
	@GetMapping(value = "/statistics/order-chart-datas")
	public ResponseEntity<?> getOrderChartStatistic() {
		Date date = new Date();
		return ResponseEntity.ok(statisticService.getOrderChartDataProjection(DateUtil.getFirstDayOfThisMonth(date), date));
	}
}
