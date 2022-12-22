package com.sos.common;

import org.springframework.data.domain.Sort;

public class SorterConstant {

	public enum BrandSorter {
		// @formatter:off
		id_asc("id tăng dần", Sort.by("id").ascending()),
		id_desc("id giảm dần", Sort.by("id").descending()),
		name_asc("A -> Z", Sort.by("name").ascending()),
		name_desc("Z -> A", Sort.by("name").descending());
		// @formatter:on
		private final String description;
		private final Sort sort;

		private BrandSorter(String description, Sort sort) {
			this.description = description;
			this.sort = sort;
		}

		public String getDescription() {
			return description;
		}

		public Sort getSort() {
			return sort;
		}
	}

	public enum ProductSorter {
		// @formatter:off
		id_asc("id tăng dần", Sort.by("id").ascending()),
		id_desc("id giảm dần", Sort.by("id").descending()),
		name_asc("A -> Z", Sort.by("name").ascending()),
		name_desc("Z -> A", Sort.by("name").descending()),
		price_asc("giá tăng dần", Sort.by("sellPrice").ascending()),
		price_desc("giá giảm dần", Sort.by("sellPrice").descending()),
		date_desc("hàng mới nhất", Sort.by("createDate").descending()),
		best_selling("bán chạy nhất", Sort.by("createDate").descending());
		// @formatter:on
		private final String description;
		private final Sort sort;

		private ProductSorter(String description, Sort sort) {
			this.description = description;
			this.sort = sort;
		}

		public String getDescription() {
			return description;
		}

		public Sort getSort() {
			return sort;
		}
	}

	public enum TransactionSorter {
		// @formatter:off
		date_desc("Mới nhất", Sort.by("createDate").descending()),
		amount_asc("Giá trị tăng dần", Sort.by("amount").ascending()),
		amount_desc("Giá trị giảm dần", Sort.by("amount").descending());
		// @formatter:on

		private final String description;
		private final Sort sort;

		private TransactionSorter(String description, Sort sort) {
			this.description = description;
			this.sort = sort;
		}

		public String getDescription() {
			return description;
		}

		public Sort getSort() {
			return sort;
		}
	}

	public enum OrderSorter {
		// @formatter:off
		date_desc("Mới nhất", Sort.by("createDate").descending()),
		date_asc("Cũ nhất", Sort.by("createDate").ascending()),
		total_asc("Giá trị tăng dần", Sort.by("total").ascending()),
		total_desc("Giá trị giảm dần", Sort.by("total").descending());
		// @formatter:on

		private final String description;
		private final Sort sort;

		private OrderSorter(String description, Sort sort) {
			this.description = description;
			this.sort = sort;
		}

		public String getDescription() {
			return description;
		}

		public Sort getSort() {
			return sort;
		}
	}

}
