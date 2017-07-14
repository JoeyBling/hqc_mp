/**
 * 将Date转为yyy年MM月dd日 HH:mm:ss时间格式
 * 
 * @param {}
 *            now
 * @return {}
 */
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	return year + "年" + month + "月" + date + "日";
}

/**
 * 将Date转为yyy.MM.dd HH.mm.ss时间格式
 * 
 * @param {}
 *            now
 * @return {}
 */
function formatDateTime(now) {
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
	return year + "." + month + "." + date + " " + (hour == 0 ? "00" : hour)
			+ "." + (minute == 0 ? "00" : minute) + "."
			+ (second == 0 ? "00" : second);
}

var element;
/** 删除订单 */
function delOrder(e) {
	var s = $(e).next("input").val();
	if (s == null) {
		return false;
	}
	element = $(e).parents(".on_d");
	$('#delOrder-confirm').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			$.ajax({
				url : '../order/delOrder',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					id : s
				},
				success : function(result) {
					$("#modal-alert").text(result.msg);
					$('#delOrder-alert').modal({});
					if (result.code === 0) {
						$(element).fadeOut();
					} else {
						if (result.code === 100) {
							setTimeout(function() {
										var url = location.href; // 获取当前url地址
										var paraString = url.replace(/\//g,
												"%2F").replace(/:/g, "%3A")
												.replace(/=/g, "%3D").replace(
														/\?/g, "%3F");
										parent.location.href = hqc.base
												+ 'wx/user/login.html?returnUrl='
												+ paraString;
									}, 2000);
						}
					}
				},
				error : function() {
					$("#delOrder-confirm").modal('close');
					AMUI.dialog.alert({
								content : '系统出现异常!',
								onConfirm : function() {
									if (location.href.indexOf("?") > 0) {
										window.location.href = location.href
												+ '&time='
												+ ((new Date()).getTime());
									} else {
										window.location.href = location.href
												+ '?time='
												+ ((new Date()).getTime());
									}
									// console.log('close');
								}
							});
				}
			});
		},
		onCancel : function() {
		}
	});
	// js阻止事件冒泡
	window.event.stopPropagation();
}

/**
 * 支付订单
 * 
 * @param {}
 *            e
 */
$("#nowPay").click(function() {
			if (null != $("#notPay").val()) {
				$("#modal-alert").text('订单已过期');
				$('#delOrder-alert').modal({});
			} else {
				$("#modal-alert").text('正在开发中....');
				$('#delOrder-alert').modal({});
			}
			// js阻止事件冒泡
			window.event.stopPropagation();
		});

/** 取消订单 */
function canOrder(e) {
	var s = $(e).next("input").val();
	if (s == null) {
		return false;
	}
	$('#canOrder-confirm').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			$.ajax({
				url : '../order/canOrder',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					id : s
				},
				success : function(result) {
					$("#modal-alert").text(result.msg);
					$('#delOrder-alert').modal({});
					if (result.code === 0) {
						setTimeout(function() {
									if (location.href.indexOf("?") > 0) {
		window.location.href = location.href + '&time='
				+ ((new Date()).getTime());
	} else {
		window.location.href = location.href + '?time='
				+ ((new Date()).getTime());
	}
								}, 2000);
					} else {
						if (result.code === 100) {
							setTimeout(function() {
										var url = location.href; // 获取当前url地址
										var paraString = url.replace(/\//g,
												"%2F").replace(/:/g, "%3A")
												.replace(/=/g, "%3D").replace(
														/\?/g, "%3F");
										parent.location.href = hqc.base
												+ 'wx/user/login.html?returnUrl='
												+ paraString;
									}, 2000);
						}
					}
				},
				error : function() {
					$("#canOrder-confirm").modal('close');
					AMUI.dialog.alert({
								content : '系统出现异常!',
								onConfirm : function() {
									if (location.href.indexOf("?") > 0) {
		window.location.href = location.href + '&time='
				+ ((new Date()).getTime());
	} else {
		window.location.href = location.href + '?time='
				+ ((new Date()).getTime());
	}
									// console.log('close');
								}
							});
				}
			});
		},
		onCancel : function() {
		}
	});
	// js阻止事件冒泡
	window.event.stopPropagation();
}

/**
 * 倒计时
 * 
 * @param {}
 *            timestamp 时间戳
 * @param {}
 *            index 下标值（唯一性）
 */
function gotoStamp(timestamp, index) {
	var nowStamp = Math.round(new Date().getTime() / 1000).toString();
	var s = 1800 - (nowStamp - timestamp);
	var minute = Math.floor(s / 60); // 分
	var seconds = s % 60; // 秒
	if (s < 0 || minute > 30) { // 已过期
		return "已过期";
	} else if (((minute) < 0 && (seconds) <= 0) || (minute) > 30) { // 最多为30分钟内
		return "已过期";
	} else {
		var render = "还剩<b id='interval" + index + "'>" + (minute) + "分"
				+ (seconds) + "秒</b>";
		setTimeout(function() {
					countdown(minute, seconds, $("#interval" + index));
				}, 1000);
		return render;
	}
}

function toDetail() {
	$(".on_d").click(function() {
		location.href = $(this).attr('data-href') + "&time="
				+ ((new Date()).getTime());
	});
}