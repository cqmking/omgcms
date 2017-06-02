package com.omgcms.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.omgcms.bean.SystemInfo;

public class CmsUtil {

	private static CmsUtil cmsUtil = null;
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	
	private CmsUtil() {

	}

	public static CmsUtil getInstance() {

		if (cmsUtil == null) {
			cmsUtil = new CmsUtil();
		}

		return cmsUtil;
	}

	public static SystemInfo getSystemInfo(HttpServletRequest request) {

		String osName = System.getProperties().getProperty("os.name");
		String runtimeEnv = "Java(TM) SE Runtime Environment " + System.getProperties().getProperty("java.version");
		String javaVm = System.getProperties().getProperty("java.vm.name").concat(" ")
				.concat(System.getProperties().getProperty("java.vm.version"));

		double totalMemory = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
		double maxMemory = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
		// double freeMemory = (Runtime.getRuntime().freeMemory()) / (1024.0 *
		// 1024);
		double freeMemory = maxMemory - totalMemory;

		String serverInfo = request.getSession().getServletContext().getServerInfo();

		SystemInfo sysInfo = new SystemInfo(osName, runtimeEnv, javaVm, totalMemory, maxMemory, freeMemory, serverInfo);

		return sysInfo;

	}

	
	public static String objectToJsonString(Object dataObject) {

		try {
			
			String jsonData = "";
			
			if (dataObject instanceof String) {
				jsonData = String.valueOf(dataObject);
			} else {

				jsonData = OBJECT_MAPPER.writeValueAsString(dataObject);
			}

			return jsonData;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	
	
}
