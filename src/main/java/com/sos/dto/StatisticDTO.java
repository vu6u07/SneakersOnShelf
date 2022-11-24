package com.sos.dto;

import com.sos.common.ApplicationConstant.OrderStatus;

public class StatisticDTO {

	public static interface OrderChartDataProjection {

		String getDate();

		long getCount();

		long getAmount();

	}

	public static interface OrderStatisticProjection {

		long getMonthlyCount();

		long getMonthlyAmount();

		long getDailyCount();

		long getDailyAmount();

		long getMonthlyProductQuantity();

	}
	
	public static interface OrderStatusStatisticProjection {

		OrderStatus getOrderStatus();

		long getCount();

	}

	public static class BestSellingProductStatistic {

		private int id;

		private String name;

		private String image;

		private long sellPrice;

		private long count;

		public BestSellingProductStatistic(int id, String name, String image, long sellPrice, long count) {
			this.id = id;
			this.name = name;
			this.image = image;
			this.sellPrice = sellPrice;
			this.count = count;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public long getSellPrice() {
			return sellPrice;
		}

		public void setSellPrice(long sellPrice) {
			this.sellPrice = sellPrice;
		}

		public long getCount() {
			return count;
		}

		public void setCount(long count) {
			this.count = count;
		}

	}

}
