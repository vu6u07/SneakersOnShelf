package com.sos.dto;

public interface OrderStatisticProjection {

	long getMonthlyTotalOrderCount();
	
	long getMonthlyOrderCount();
	
	long getMonthlyProductCount();

	long getMonthlyOrderAmount();
	
	long getDailyOrderCount();
	
	long getDailyProductCount();
	
	long getDailyOrderAmount();
	
	long getMonthlyCancelledOrderCount();
	
	long getMonthlyCancelledProductCount();

	long getMonthlyCancelledOrderAmount();
	
}
