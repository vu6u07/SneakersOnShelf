package com.sos.service.util;

import javax.validation.ValidationException;

import org.springframework.util.StringUtils;

public class ValidationUtil {

	public static void validatePhone(String phone) {
		if (!StringUtils.hasText(phone)) {
			throw new ValidationException("Vui lòng nhập email.");
		}
		if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})")) {
			throw new ValidationException("Số điện thoại không đúng định dạng.");
		}
	}

	public static void validateEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new ValidationException("Vui lòng nhập email.");
		}
		if (!email.matches(
				"^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")) {
			throw new ValidationException("Email không đúng định dạng.");
		}
	}

	public static void validateUsername(String username) {
		if (!StringUtils.hasText(username)) {
			throw new ValidationException("Vui lòng nhập tên tài khoản.");
		}
		if(!username.matches("^[a-zA-Z0-9._-]{3,}$")) {
			throw new ValidationException("Tên tài khoản không hợp lệ.");
		}
	}

	public static void validatePassword(String username) {
		if (!StringUtils.hasText(username)) {
			throw new ValidationException("Vui lòng nhập mật khẩu.");
		}
		if(!username.matches("^[a-zA-Z0-9._-]{3,}$")) {
			throw new ValidationException("Mật khẩu không hợp lệ.");
		}
	}

	
}
