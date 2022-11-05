package com.sos.dto;

import com.sos.common.ApplicationConstant;
import com.sos.entity.Brand;
import com.sos.entity.Category;
import com.sos.entity.ProductDetail;
import com.sos.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ApplicationConstant.ProductGender productGender;

    private Brand brand;

    private Category category;

    private ProductImage productImage;

    private long sellPrice;

    private long originalPrice;

    private String description;

    private long importPrice;

    private Date createDate;

    private Date updateDate;
}
