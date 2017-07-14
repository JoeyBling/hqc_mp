package com.hqc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Http帮助类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月6日
 * 
 */
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);
	private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
	private final static String DEFAULT_ENCODING = "UTF-8";

	public static String postData(String urlStr, String data) {
		return postData(urlStr, data, null);
	}

	public static String postData(String urlStr, String data, String contentType) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(CONNECT_TIMEOUT);
			if (contentType != null)
				conn.setRequestProperty("content-type", contentType);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), DEFAULT_ENCODING);
			if (data == null)
				data = "";
			writer.write(data);
			writer.flush();
			writer.close();

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error("Error connecting to " + urlStr + ": "
					+ e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * 获取http请求的所有参数
	 * 
	 * @param request
	 * @return
	 */
	public static SortedMap<Object, Object> getParams(HttpServletRequest request) {
		SortedMap<Object, Object> params = new TreeMap<Object, Object>();

		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			params.put(paraName, request.getParameter(paraName));
		}

		return params;
	}

	/**
	 * 验证参数是否有空值
	 * 
	 * @param params
	 *            参数集合
	 * @param paramArr
	 *            要验证的参数名数组
	 * @return
	 */
	public static boolean verificationMap(SortedMap<Object, Object> params,
			String[] paramArr) {

		if (paramArr == null || paramArr.length == 0) {
			return false;
		}

		// 先验证参数是否完整
		for (String paramName : paramArr) {
			if (!params.containsKey(paramName)) {
				return false;
			}
		}

		// 验证参数是否有空值
		Set<Entry<Object, Object>> set = params.entrySet();

		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry mapentry = (Map.Entry) iterator.next();
			Object key = mapentry.getKey();
			if (mapentry.getValue() == null) {

				for (String paramName : paramArr) {
					if (key.toString().equals(paramName)) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
