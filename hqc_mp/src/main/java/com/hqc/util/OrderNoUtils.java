package com.hqc.util;

import java.util.Date;

/**
 * 订单号生成规则
 * 
 * @author Administrator
 * 
 */
public class OrderNoUtils {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;

	/**
	 * 生成唯一订单号
	 * 
	 * @return
	 */
	public static synchronized String getOrder_no() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d",
				date, seq++);
		return str;
	}

}
