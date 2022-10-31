	package com.sos.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final DateFormat ddMMyyyyDashes = new SimpleDateFormat("dd-MM-yyyy");

	public static String convertToString(Date date) {
		return ddMMyyyyDashes.format(date);
	}
}
