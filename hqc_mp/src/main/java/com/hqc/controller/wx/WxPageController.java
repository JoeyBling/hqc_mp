package com.hqc.controller.wx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信页面视图
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月23日
 * 
 */
@Controller
public class WxPageController {

	@RequestMapping("wx/{url}.html")
	public String page(@PathVariable("url") String url) {
		return "wx/" + url + ".ftl";
	}

	@RequestMapping("wx/{path}/{url}.html")
	public String page(@PathVariable("path") String path,
			@PathVariable("url") String url) {
		return "wx/" + path + "/" + url + ".ftl";
	}

}
