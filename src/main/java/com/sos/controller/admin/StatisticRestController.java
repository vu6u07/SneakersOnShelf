package com.sos.controller.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sos.service.StatisticService;
import com.sos.service.util.DateUtil;

@RestController
@RequestMapping(value = "/admin/v1")
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
	
	@GetMapping(value = "/statistics/best-selling-product")
	public ResponseEntity<?> getBestSellingProduct() {
		Date date = new Date();
		return ResponseEntity.ok(statisticService.getBestSellingProductStatistic(DateUtil.getFirstDayOfThisMonth(date), DateUtil.getFirstDayOfNextMonth(date)));
	}
	
	@GetMapping(value = "/statistics/order-status")
	public ResponseEntity<?> getOrderStatusStatisticProjections() {
		Date date = new Date();
		return ResponseEntity.ok(statisticService.getOrderStatusStatisticProjections(DateUtil.getFirstDayOfThisMonth(date), DateUtil.getFirstDayOfNextMonth(date)));
	}
}
