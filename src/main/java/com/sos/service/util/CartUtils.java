package com.sos.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartUtils {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");

	public static String generateOrderId(Date date, int id) {
		return DATE_FORMAT.format(date).concat(String.valueOf(id));
	}
	
}
