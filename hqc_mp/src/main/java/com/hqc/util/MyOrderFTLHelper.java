package com.hqc.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * 我的订单倒计时
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月27日
 * 
 */
public class MyOrderFTLHelper implements TemplateDirectiveModel {

	@Autowired
	@Override
	public void execute(Environment env,
			@SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (body == null) {// 自定义标签必须有内容，即自定义 开始标签与结束标签之间必须有 内容
			throw new TemplateModelException("自定义标签必须有内容，即自定义开始标签与结束标签之间必须有内容");
		}
		Writer out = env.getOut();
		TemplateScalarModel scalarModel = (TemplateScalarModel) params
				.get("unix"); // 传过来的时间戳
		TemplateScalarModel other = (TemplateScalarModel) params.get("flag"); // 临时
		TemplateScalarModel index = (TemplateScalarModel) params.get("index"); // 下标值
		if (null == scalarModel || null == index) {
			return;
		}
		long flag = Long.valueOf(scalarModel.getAsString());
		String others = other == null ? null : other.getAsString();
		int _index = Integer.valueOf(index.getAsString());
		long s = 1800 - (DateUtils.getCurrentUnixTime() - flag);
		long minute = s / 60; // 分
		long seconds = s % 60; // 秒
		if (s < 0 || minute > 30) { // 已过期
			if (null != others) {
				out.write("0分0秒<input type='hidden' value='1' id='notPay'/>");
			} else {
				out.write("已过期");
			}
		} else if (((minute) < 0 && (seconds) <= 0) || (minute) > 30) { // 最多为30分钟内
			if (null != others) {
				out.write("0分0秒<input type='hidden' value='1' id='notPay'/>");
			} else {
				out.write("已过期");
			}
		} else {
			String render = null;
			if (null != others) {
				render = "<b id='interval" + _index + "'>" + (minute) + "分"
						+ (seconds) + "秒</b><script>countdown(" + (minute)
						+ "," + (seconds) + ",$('#interval" + _index
						+ "'));</script>";
			} else {
				render = "还剩<b id='interval" + _index + "'>" + (minute) + "分"
						+ (seconds) + "秒</b><script>countdown(" + (minute)
						+ "," + (seconds) + ",$('#interval" + _index
						+ "'));</script>";
			}
			// 设置倒计时
			out.write(render);
		}
		body.render(out);
	}
}
