package com.sos.service.util;

import javax.validation.ValidationException;

public class ValidationUtil {

	public static void validatePhone(String phone) {
		if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})")) {
			throw new ValidationException("Số điện thoại không đúng định dạng.");
		}
	}

	public static void validateEmail(String email) {
		if (!email.matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$")) {
			throw new ValidationException("Email không đúng định dạng.");
		}
	}

}
