package com.sos.service.util;

import java.text.DecimalFormat;

public class NumberUtil {

	public static String VND(long number) {
		return thousandSeparator(number) + " đ";
	}

	public static String thousandSeparator(long number) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		return df.format(number);
	}
	
}
