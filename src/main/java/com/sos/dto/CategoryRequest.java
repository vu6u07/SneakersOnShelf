package com.sos.dto;

import com.sos.common.ApplicationConstant.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequest {
    private int id;

    @NotBlank(message = "Vui lòng nhập tên danh mục.")
    private String name;

    private ActiveStatus activeStatus;
}
