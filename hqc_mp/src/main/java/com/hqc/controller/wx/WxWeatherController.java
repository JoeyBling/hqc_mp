package com.hqc.controller.wx;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.util.R;
import com.hqc.util.wx.WxUtil;
import com.hqc.weather.ws.ArrayOfString;
import com.hqc.weather.ws.WeatherWS;
import com.hqc.weather.ws.WeatherWSSoap;

/**
 * 微信天气控制器
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年5月23日
 * 
 */
@RestController
@RequestMapping("/wx/weather")
@SuppressWarnings("unchecked")
public class WxWeatherController extends BaseController {

	/** 天气查询API */
	protected WeatherWS ws = new WeatherWS();

	@RequestMapping("/getWeather")
	public R getWeather(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 从Redis缓存获取天气查询值
		Object weather = redisUtil.get(WxUtil.REDIS_WEATHER_KEY);
		if (null != weather) { // 有缓存直接返回数据
			if (weather instanceof List)
				return R.ok().put("weather", (List<String>) weather)
						.put("province", WxUtil.WEATHE_PROVINCE)
						.put("city", WxUtil.WEATHE_CITY);
		}
		WeatherWSSoap weatherWSSoap = ws.getWeatherWSSoap();
		// 读取配置文件获取省份所有的城市
		ArrayOfString supportCityString = weatherWSSoap
				.getSupportCityString(WxUtil.WEATHE_PROVINCE);
		if (null == supportCityString) {
			return R.error("获取城市失败，请联系管理员!");
		}
		// 配置文件市区的code
		String cityCode = null;
		for (String string : supportCityString.getString()) {
			if (null == string) {
				continue;
			}
			if (string.startsWith(WxUtil.WEATHE_CITY)) {
				cityCode = string.split(",")[1];
			}
		}
		if (cityCode == null) {
			return R.error("获取城市失败，请联系管理员!");
		}
		// 获得天气
		List<String> weatherString = weatherWSSoap.getWeather(cityCode, "")
				.getString();
		if (weatherString == null) {
			return R.error("查询天气失败，请稍后重试!");
		}
		// 删除Redis缓存
		redisUtil.evict(WxUtil.REDIS_WEATHER_KEY);
		// 默认缓存有效期60分钟
		if (weatherString.size() < 30) {
			return R.error("查询天气失败，请稍后重试!");
		}
		redisUtil.put(WxUtil.REDIS_WEATHER_KEY, weatherString, 3600L, false);
		return R.ok().put("weather", weatherString)
				.put("province", WxUtil.WEATHE_PROVINCE)
				.put("city", WxUtil.WEATHE_CITY);
	}

}
