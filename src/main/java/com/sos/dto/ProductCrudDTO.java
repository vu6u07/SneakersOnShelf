package com.sos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sos.common.ApplicationConstant;
import com.sos.entity.Brand;
import com.sos.entity.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
public class ProductCrudDTO {

    private int id;

    private String name;

    private String productGender;

    private String brand;

    private String category;

    @JsonProperty("productImage")
    private List<?> productImage;

    private long sellPrice;

    private long originalPrice;

    private long importPrice;

    private String description;

    private Date createDate;

    private Date updateDate;

    private String size35;
    private String size36;
    private String size37;
    private String size38;
    private String size39;
    private String size40;
    private String size41;
    private String size42;
    private String size43;
}
