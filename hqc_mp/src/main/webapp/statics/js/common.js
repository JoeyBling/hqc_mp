// 命名空间定义
var hqc = window.NameSpace || {};

hqc.base = "/";
hqc.url = "http://dev2.66666684.cn/";

// 工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};
T.p = url;

// 全局配置
$.ajaxSetup({
			dataType : "json",
			contentType : "application/json",
			cache : false
		});

// 取得地址栏参数
var Request = {
	QueryString : function(item, paramurl) {
		var reg = new RegExp("(^|&)" + item + "=([^&]*)(&|$)", "i");
		var r = paramurl.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
}

// 取得顶窗口地址栏参数
var topRequest = {
	QueryString : function(item) {
		return Request.QueryString(item, top.location.search);
	}
}

/**
 * 替换字符串
 */
var replace = {
	/**
	 * 所有\改为/
	 */
	FilePath : function(item) {
		return item.replace(/\\/g, "/");
	}
}

// 禁用F5,退格键
function forbidF5(event) {
	var event = event || window.event;
	var evtnode = event.srcElement ? event.srcElement : event.target;
	var key = event.keyCode || event.which;

	// 116 F5 117 F6 8 BACKSPACE
	if (key == 116
			|| key == 117
			|| (key == 8 && evtnode.nodeName != "TEXTAREA" && evtnode.nodeName != "INPUT")) {
		eventstop(event);
	} else {
		return true;
	}
}

function eventstop(event) {
	try {
		event.keyCode = 0;
	} catch (err) {
	}
	try {
		event.preventDefault();
	} catch (err) {
	}
	try {
		event.stopPropagation();
	} catch (err) {
	}
	try {
		event.returnValue = false;
	} catch (err) {
	}
	try {
		event.cancelBubble = true;
	} catch (err) {
	}
	return false;
}

/**
 * Button交互
 * 
 * @param {}
 *            e
 */
function loadingButton(e) {
	var $btn = $(e);
	$btn.attr("disabled", "disabled");
	$btn.button('loading');
	setTimeout(function() {
				$btn.removeAttr("disabled");
				$btn.button('reset');
			}, 2000);
}

/**
 * 时间倒计时
 * 
 * @param {}
 *            x 倒计时最后开始分
 * @param {}
 *            y 倒计时最后开始秒
 * @param {}
 *            element 倒计时显示的元素
 */
function countdown(x, y, element) {
	var d = new Date("1111/1/1,0:" + x + ":" + y);
	var interval = setInterval(function() {
				var m = d.getMinutes();
				var s = d.getSeconds();
				m = m < 10 ? "0" + m : m;
				s = s < 10 ? "0" + s : s;
				$(element).text(m + "分" + s + "秒");
				if (m == 0 && s == 0) {
					clearInterval(interval);
					return;
				}
				d.setSeconds(s - 1);
			}, 1000);
}

/**
 * 将Date转为yyy-MM-dd HH:mm:ss时间格式
 * 
 * @param {}
 *            now
 * @return {}
 */
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
	if (second >= 1 && second <= 9) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + date + " " + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}

