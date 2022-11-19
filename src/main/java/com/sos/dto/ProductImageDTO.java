package com.sos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductImageDTO {
    private int id;
    private List<String> image;
    private int idproduct;

    public ProductImageDTO() {
    }
}
