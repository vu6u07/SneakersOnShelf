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
}
