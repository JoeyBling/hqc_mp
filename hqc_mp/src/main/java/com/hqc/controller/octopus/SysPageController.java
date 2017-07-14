package com.hqc.controller.octopus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理员页面视图
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {

	@RequestMapping("octopus/{path}/{url}.html")
	public String page(@PathVariable("path") String path,
			@PathVariable("url") String url) {
		return "octopus/" + path + "/" + url + ".ftl";
	}

}
