package com.sos.service;

import java.util.Date;
import java.util.List;

import com.sos.dto.OrderChartDataProjection;
import com.sos.dto.OrderStatisticProjection;

public interface StatisticService {

	OrderStatisticProjection getOrderStatisticProjection(Date date);
	
	List<OrderChartDataProjection> getOrderChartDataProjection(Date fromDate, Date toDate);
	
}
