package com.utility;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cottonconnect.elearning.ELearning.exception.CustomException;

public class WebUtils {

	public static String getHeadersInfo(HttpServletRequest request, String headerKey) throws CustomException {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		if (map.get(headerKey) != null) {
			return map.get(headerKey);
		} else {
			throw new CustomException("User header not exists");
		}
	}

}
