// 查询天气
function queryWeather() {
	$.ajax({
				url : '../weather/getWeather',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				type : 'post',
				success : function(r) {
					if (r.code === 0) {
						$("#province").text(r.province); // 显示省份
						$("#city").text(r.city); // 显示城市
						if (r.weather.length >= 32) { // 查询天气成功
							$("#now").html(r.weather[8]); // 今天的温度
							$("#nowTC").text(r.weather[8]); // 今天的温度
							var nowImg = '<img src="' + hqc.base
									+ 'statics/images/weather/a_'
									+ r.weather[10] + '"><img src="' + hqc.base
									+ 'statics/images/weather/a_'
									+ r.weather[11] + '">';
							$(".forecast-icon").html(nowImg); // 今天的天气图片
							$("#nwoHc").text(r.weather[7].split(" ")[1]); // 今天的气温
							$("#tomorrow").html(r.weather[12] + ' <span>'
									+ r.weather[13] + '</span>'); // 明天的气温
							$("#afterTomorrow").html(r.weather[17] + ' <span>'
									+ r.weather[18] + '</span>'); // 后天的气温
							$("#afterSecondDays").html(r.weather[22]
									+ ' <span>' + r.weather[23] + '</span>'); // 大后天的气温
							$("#afterThreeDays").html(r.weather[27] + ' <span>'
									+ r.weather[28] + '</span>'); // 大大后天的气温
						}
					} else {
						AMUI.dialog.alert({
									content : r.msg,
									onConfirm : function() {
										// console.log('close');
									}
								});
					}
				},
				error : function() {
					AMUI.dialog.alert({
								content : '系统异常,请联系管理员!',
								onConfirm : function() {
									// console.log('close');
								}
							});
				}
			});
}

$(function() {
			queryWeather();
		})
