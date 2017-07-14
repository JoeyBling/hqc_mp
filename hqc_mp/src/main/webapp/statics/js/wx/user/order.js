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

$(function() {
			toDetail();
		});

/**
 * 初始化
 */
function init() {
	var pageNum = 2;
	// dropload
	$('.ding_d').dropload({
		scrollArea : window,
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-我的订单</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-我的订单...</div>',
			domNoData : '<div class="dropload-noData">暂无数据-我的订单</div>'
		},
		loadDownFn : function(me) {
			$.ajax({
				type : 'POST',
				url : '../order/record',
				dataType : 'json',
				data : {
					page : pageNum,
					type : topRequest.QueryString('type')
				},
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				success : function(r) {
					pageNum++;
					// 为了测试，延迟1秒加载
					setTimeout(function() {
								// 每次数据加载完，必须重置
								me.resetload();
							}, 500);
					if (r.code === 0) {
						if (r.page.list == null || r.page.list.length < 1) {
							// 锁定
							me.lock();
							// 无数据
							me.noData();
							return;
						}
						$.each(r.page.list, function(index, content) {
							var flag = '<div class="on_d" data-href="'
									+ hqc.base + 'wx/order/detail?id='
									+ content.id + '"><p class="bh">订单编号：'
									+ content.orderNo + '<span>';
							var orderStatus = "";
							if (content.status === 1) {
								orderStatus = "已付款";
							} else if (content.status === 2) {
								orderStatus = "支付失败";
							} else if (content.status === 3) {
								orderStatus = "待付款";
							} else if (content.status === 4) {
								orderStatus = "已关闭";
							} else if (content.status === 5) {
								orderStatus = "退款";
							}
							flag += orderStatus;
							if (content.status === 3) {
								flag += '<img src="'
										+ hqc.base
										+ 'statics/images/wx/user/clock.png" height="20px"/>'
										+ gotoStamp(content.createTime, index
														+ 100);
							}
							flag += '</span></p><div class="sp_pr"><a href="javascript:void(0);">'
									+ '<img src="'
									+ hqc.base
									+ (content.mpTicketEntity == null
											? ""
											: content.mpTicketEntity.thumbUrl)
									+ ' " /><div class="text_p"><p>'
									+ (content.mpTicketEntity == null
											? ""
											: content.mpTicketEntity.ticketName)
									+ '</p><span>共有'
									+ content.ticketCount
									+ '张</span><span class="yue">有效期：'
									+ formatDate(new Date(content.startTime))
									+ " - "
									+ formatDate(new Date(content.endTime))
									+ '</span><span class="yue">下单时间：'
									+ formatDateTime(new Date(content.createTime))
									+ '</span></div></a></div><div class="button"> 订单总金额<span>￥'
									+ content.totalFee + '</span>';
							if (content.status === 1) {
								flag += '<a href="javascript:void(0);" onclick="delOrder(this)" class="del">'
										+ '删除订单</a><input type="hidden" value="'
										+ content.id + '"/>';
							} else if (content.status === 2) {
								flag += '<a href="javascript:void(0);" onclick="delOrder(this)" class="del">'
										+ '删除订单</a><input type="hidden" value="'
										+ content.id
										+ '"/><a href="javascript:void(0);" onclick="canOrder(this)" class="qu">取消订单</a><input type="hidden" value="'
										+ content.id + '"/>';
							} else if (content.status === 3) {
								flag += '<a href="javascript:void(0);" onclick="goPay(this)" class="liji">立即付款</a>'
										+ '<a href="javascript:void(0);" onclick="canOrder(this)" class="qu">取消订单</a><input type="hidden" value="'
										+ content.id
										+ '"/>'
										+ '<a href="javascript:void(0);" onclick="delOrder(this)" class="del">'
										+ '删除订单</a><input type="hidden" value="'
										+ content.id + '"/>';
							} else if (content.status === 4) {
								flag += '<a href="javascript:void(0);" onclick="delOrder(this)" class="del">'
										+ '删除订单</a><input type="hidden" value="'
										+ content.id + '"/>';
							} else if (content.status === 5) {
								flag += '<a href="javascript:void(0);" onclick="delOrder(this)" class="del">'
										+ '删除订单</a><input type="hidden" value="'
										+ content.id + '"/>';
							}
							$(".dropload-down").before(flag);
						});
						toDetail();
					} else {
						AMUI.dialog.alert({
									content : r.msg,
									onConfirm : function() {
										// console.log('close');
									}
								});
						if (r.code === 100) { // 先登录
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
				error : function(xhr, type) {
					AMUI.dialog.alert({
								content : '系统错误，请联系管理员!',
								onConfirm : function() {
									// console.log('close');
								}
							});
					// 即使加载出错，也得重置
					me.resetload();
				}
			});
		},
		threshold : 50
	});
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
function goPay(e) {
	$("#modal-alert").text('正在开发中....');
	$('#delOrder-alert').modal({});
	// js阻止事件冒泡
	window.event.stopPropagation();
}

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
										window.location.href = location.href
												+ '&time='
												+ ((new Date()).getTime());
									} else {
										window.location.href = location.href
												+ '?time='
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