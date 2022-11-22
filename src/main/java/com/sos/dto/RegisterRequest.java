package com.sos.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {

	@Size(min = 4, message = "Tên tài khoản quá ngắn.")
	@NotBlank(message = "Vui lòng nhập tên tài khoản.")
	private String username;

	@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 kí tự.")
	@NotBlank(message = "Vui lòng nhập mật khẩu.")
	private String password;

	@Email(message = "Định dạng email không hợp lệ.")
	@NotBlank(message = "Vui lòng nhập email.")
	private String email;

	@NotBlank(message = "Vui lòng nhập họ và tên.")
	private String fullname;

}
