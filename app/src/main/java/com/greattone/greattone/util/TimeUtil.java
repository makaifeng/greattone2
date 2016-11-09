package com.greattone.greattone.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	public static String format(String template, String time) {
		SimpleDateFormat df = new SimpleDateFormat(template,
				Locale.CHINA);
		if (time.isEmpty()) {
			return "";
		}
		return df.format(new Date(Long.valueOf(time)*1000));
	}

	public static String format(String template, long time) {
		return format(template, Long.valueOf(time));
	}
}
