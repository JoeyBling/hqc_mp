package com.hqc.config;

import com.hqc.util.FormatTimeFTLHelper;
import com.hqc.util.MyOrderFTLHelper;
import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * 继承FreeMarkerConfigurer类,重写afterPropertiesSet()方法； 集成shiroTags标签 Created by
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月12日
 * 
 */
public class CustomFreeMarkerConfigurer extends FreeMarkerConfigurer {

	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
		this.getConfiguration().setSharedVariable("formatTime", new FormatTimeFTLHelper());
		this.getConfiguration().setSharedVariable("order", new MyOrderFTLHelper());
	}

}