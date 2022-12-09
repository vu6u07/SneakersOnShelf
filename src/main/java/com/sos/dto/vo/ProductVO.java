package com.sos.dto.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sos.common.ApplicationConstant.Benefit;
import com.sos.common.ApplicationConstant.ProductGender;
import com.sos.common.ApplicationConstant.ProductStatus;
import com.sos.common.ApplicationConstant.ShoeFeel;
import com.sos.common.ApplicationConstant.ShoeHeight;
import com.sos.common.ApplicationConstant.Surface;
import com.sos.entity.Brand;
import com.sos.entity.Category;
import com.sos.entity.Color;
import com.sos.entity.Material;
import com.sos.entity.Sole;

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

	@NotNull(message = "Vui lòng chọn màu sản phẩm.")
	private Color color;

	@NotNull(message = "Vui lòng chọn đế sản phẩm.")
	private Sole sole;

	@NotNull(message = "Vui lòng chọn vật liệu sản phẩm.")
	private Material material;

	@NotNull(message = "Vui lòng chọn chiều cao giày.")
	private ShoeHeight shoeHeight;

	@NotNull(message = "Vui lòng chọn thời tiết thích hợp của sản phẩm.")
	private Benefit benefit;

	@NotNull(message = "Vui lòng chọn cảm giác sản phẩm.")
	private ShoeFeel shoeFeel;

	@NotNull(message = "Vui lòng chọn môi trường thích hợp của sản phẩm.")
	private Surface surface;

}
