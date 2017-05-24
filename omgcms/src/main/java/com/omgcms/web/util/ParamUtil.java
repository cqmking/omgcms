package com.omgcms.web.util;

import org.apache.commons.lang3.StringUtils;

public class ParamUtil {
	
	public static int get(Integer value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public static long get(Long value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public static String get(String value, String defaultValue) {
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	public static boolean get(Boolean value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

}
