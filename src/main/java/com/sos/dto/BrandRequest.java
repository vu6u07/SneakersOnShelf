package com.sos.dto;

import com.sos.common.ApplicationConstant.ActiveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandRequest {
    private int id;

    @NotBlank(message = "Vui lòng nhập tên nhãn hiệu.")
    private String name;

    private ActiveStatus activeStatus;
}
