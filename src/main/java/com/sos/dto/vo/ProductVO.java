package com.sos.dto.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.entity.Brand;
import com.sos.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVO {

	private int id;

	@NotBlank(message = "Vui lòng nhập tên sản phẩm.")
	private String name;

	@NotNull(message = "Vui lòng chọn giới tính sản phẩm.")
	private ProductGender productGender;

	@NotNull(message = "Vui lòng chọn trạng thái sản phẩm.")
	private ProductStatus productStatus;

	@NotNull(message = "Vui lòng chọn hãng sản phẩm.")
	private Brand brand;

	@NotNull(message = "Vui lòng chọn danh mục sản phẩm.")
	private Category category;

	@Min(value = 0, message = "Giá sản phẩm không hợp lệ.")
	private long sellPrice;

	private String description;

}
