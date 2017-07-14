package com.hqc.controller.octopus;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.util.DateUtils;
import com.hqc.util.R;

/**
 * 微信配置文件更改
 * 
 * @author cxw
 * @date 2017年5月20日
 */
@RestController
@RequestMapping("wx/config")
public class MpConfigController extends AbstractController {

	/**
	 * 获取配置文件信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getWxConfig")
	@RequiresPermissions("mp:config:list")
	public R getWxConfig(HttpServletRequest request) throws IOException {
		Properties prop = new Properties();
		R r = new R();
		String url = request.getRequestURL().toString();
		url = url.replace("config/getWxConfig", "core");
		r.put("url", url);
		InputStream realPath = getClass().getClassLoader().getResourceAsStream(
				"wx.properties");
		InputStream in = new BufferedInputStream(realPath);
		prop.load(in); // /加载属性列表
		Iterator<String> it = prop.stringPropertyNames().iterator();
		while (it.hasNext()) {
			String key = it.next();
			r.put(key, prop.getProperty(key));
		}

		in.close();
		realPath.close();

		return r;
	}

	/**
	 * 修改配置文件信息
	 * 
	 * @param appid
	 * @param appsecret
	 * @param token
	 * @param aeskey
	 * @param partener_id
	 * @param partener_key
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateWxConfig")
	@RequiresPermissions("mp:config:update")
	public R updateWxConfig(HttpServletRequest request, String appid,
			String appsecret, String token, String aeskey, String partener_id,
			String partener_key) throws IOException {
		Properties prop = new Properties();

		InputStream realPath = getClass().getClassLoader().getResourceAsStream(
				"wx.properties");
		InputStream in = new BufferedInputStream(realPath);
		prop.load(in); // /加载属性列表
		in.close();
		realPath.close();
		prop.setProperty("appid", appid);
		prop.setProperty("appsecret", appsecret);
		prop.setProperty("token", token);
		prop.setProperty("aeskey", aeskey);
		prop.setProperty("partener_id", partener_id);
		prop.setProperty("partener_key", partener_key);
		FileOutputStream fos = new FileOutputStream(getClass().getClassLoader()
				.getResource("wx.properties").getPath()); // 也可以设置编码
		prop.store(fos, null);
		fos.flush();
		fos.close();
		logger.info("\n修改微信配置文件：\n操作人：" + getAdmin().getUsername() + "\n时间"
				+ DateUtils.format(new Date()));

		// Runtime.getRuntime().exec(
		// "cmd /c  "
		// + request.getServletContext().getRealPath(
		// "/../../bin\\shutdown.bat")); // 调用外部程序
		// Runtime.getRuntime().exec(
		// "cmd /c  "
		// + request.getServletContext().getRealPath(
		// "/../../bin\\startup.bat")); // 调用外部程序
		return R.ok();
	}
}
