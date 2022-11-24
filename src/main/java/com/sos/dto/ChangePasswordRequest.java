package com.sos.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

	@NotBlank
	@Size(min = 6, message = "Mật khẩu không chính xác.")
	private String password;

	@NotBlank
	@Size(min = 6, message = "Mật khẩu mới phải có ít nhất 6 kí tự.")
	private String newPassword;
	
}
