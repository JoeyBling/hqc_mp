var i = "0";
function query(n, c) {
	if (null != c && c === 1) {

	}
	$.ajax({
		url : '../user/mpintegralrecord/queryList',
		type : 'post',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : n == null ? 1 : n,
			goodsType : c == null ? 2 : c
		},
		success : function(data) {
			if (data.code == 0) {
				$("#recordList").html("");
				if (c == null || c == 2) {
					$("#goodsType").val("2");
					$("#ticket").addClass("am-active")
					$("#coupon").removeClass("am-active")
				} else {
					$("#goodsType").val("1");
					$("#coupon").addClass("am-active")
					$("#ticket").removeClass("am-active")
				}
				if (data.page.list.length == 0) {
					$("#recordList")
							.html("<img style='margin-top:35%;margin-left:20%' width='200px' src='"
									+ hqc.base
									+ "statics/images/wx/user/NotData.png'/>")
					return false;
				}
				$("#page").val(data.page.currPage);
				$.each(data.page.list, function(index, content) {
					i++;
					var foot = "";
					var time = "";
					if (content.use_status == 1) {
						time = "<div class='col-xs-3 time'>已使用</div>";
					} else if (!timeCompare(formatDate(new Date(content.use_time
							* 1000)))) {
						time = "<div class='col-xs-3 time'>已过期</div>";
					} else {
						if(content.goods_type == 2){
							time = "<div class='col-xs-6 time'>使用时间:"
								+ formatDate(new Date(content.use_time
										* 1000)) + "</div>";
						}else{
							
							time = "<div class='col-xs-6 time'>有效时间:"
								+ formatDate(new Date(content.use_time
										* 1000)) + "</div>";
						}
						
					}
				if (content.goods_type == 2) {
					foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
							+ content.exchange_code
							+ "</div>"
							+ time + "</div>";
				} else {
					foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
							+ content.exchange_code
							+ "</div>"+ time + "</div>";
				}
					var flag = "";
					var img = "";
					if (content.use_status == 1) {
						img = "<img id=" + i
								+ " width='100%' class='Gray' src='" + hqc.base
								+ content.goods_thumb + "'/>";
					} else if (!timeCompare(formatDate(new Date(content.use_time
							* 1000)))) {
						img = "<img id=" + i
								+ " width='100%' class='Gray' src='" + hqc.base
								+ content.goods_thumb + "'/>";
					} else {
						img = "<img id=" + i + " width='100%' src='" + hqc.base
								+ content.goods_thumb + "'/>";
					}
					var integralType = "<span class='label label-warning'>消耗</span>";
					flag = "<div class='listItem'><ul class='row' style='margin-right:0px;'><li class='col-xs-5  ticket'>"
							+ content.goods_name
							+ "</li><li class='col-xs-4 integralType'>"
							+ integralType
							+ "&nbsp;"
							+ content.integral
							+ "</li></ul>"
							+ "<ul class='row' style='margin-right:0px;'><li class='col-xs-12 about'>"
							+ img + "" + foot + "</li></ul></div>";
					$("#recordList").append(flag);

				})
				init();
			} else if (data.code == 100) {
				layer.msg(data.msg, {
					icon : 5,
					time : 2000
						// 2秒关闭（如果不配置，默认是3秒）
					}, function() {
					if (data.code === 100) { // 先登录
						var url = location.href; // 获取当前url地址
						var paraString = url.replace(/\//g, "%2F").replace(
								/:/g, "%3A").replace(/=/g, "%3D").replace(
								/\?/g, "%3F");
						parent.location.href = hqc.base
								+ 'wx/user/login.html?returnUrl=' + paraString;
					}
				});
			} else {
				layer.msg(data.msg);
			}
		},
		error : function() {
			layer.msg('未知异常，请联系管理员', {
						icon : 2
					});
			if (location.href.indexOf("?") > 0) {
				window.location.href = location.href + '&time='
						+ ((new Date()).getTime());
			} else {
				window.location.href = location.href + '?time='
						+ ((new Date()).getTime());
			}
		}
	})
}
$(function() {
			query(topRequest.QueryString("page"), topRequest.QueryString("c")); // 获取所有最新动态
		})
function init() {
	var pageNum = 2;
	// dropload
	$('#recordList').dropload({
		scrollArea : window,
		domUp : {
			domClass : 'dropload-up',
			domRefresh : '<div class="dropload-refresh">↓下拉刷新-积分兑换信息</div>',
			domUpdate : '<div class="dropload-update">↑释放更新-积分兑换信息</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-积分兑换信息...</div>'
		},
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-积分兑换信息</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-积分兑换信息...</div>',
			domNoData : '<div class="dropload-noData">暂无数据-积分兑换信息</div>'
		},
		loadUpFn : function(me) {
			var c = $("#goodsType").val();
			$.ajax({
				url : '../user/mpintegralrecord/queryList',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					page : "1",
					goodsType : c == null ? 2 : c
				},
				success : function(r) {
					// 为了测试，延迟1秒加载
					setTimeout(function() {
								// 每次数据加载完，必须重置
								me.resetload();
							}, 500);
					if (r.code === 0) {
						$("#recordList").html("");
						if (r.page.list == null || r.page.list.length < 1) {
							$("#recordList").append("<img src='" + hqc.base
									+ "statics/images/no.png' width='100%'/>"
									+ "<br/><center>当前没有最新动态</center>");
							return;
						}
						$.each(r.page.list, function(index, content) {
							i++;
							var foot = "";
							var time = "";
							if (content.use_status == 1) {
								time = "<div class='col-xs-3 time'>已使用</div>";
							} else if (!timeCompare(formatDate(new Date(content.use_time
									* 1000)))) {
								time = "<div class='col-xs-3 time'>已过期</div>";
							} else {
								if(content.goods_type == 2){
									time = "<div class='col-xs-6 time'>使用时间:"
										+ formatDate(new Date(content.use_time
												* 1000)) + "</div>";
								}else{
									time = "<div class='col-xs-6 time'>有效时间:"
										+ formatDate(new Date(content.use_time
												* 1000)) + "</div>";
								}
								
							}
						if (content.goods_type == 2) {
							foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
									+ content.exchange_code
									+ "</div>"
									+ time + "</div>";
						} else {
							foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
									+ content.exchange_code
									+ "</div>"+ time + "</div>";
						}
							var flag = "";
							var img = "";
							if (content.use_status == 1) {
								img = "<img id=" + i
										+ " width='100%' class='Gray' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							} else if (!timeCompare(formatDate(new Date(content.use_time
									* 1000)))) {
								img = "<img id=" + i
										+ " width='100%' class='Gray' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							} else {
								img = "<img id=" + i + " width='100%' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							}
							var integralType = "<span class='label label-warning'>消耗</span>";
							flag = "<div class='listItem'><ul class='row' style='margin-right:0px;'><li class='col-xs-5  ticket'>"
									+ content.goods_name
									+ "</li><li class='col-xs-4 integralType'>"
									+ integralType
									+ "&nbsp;"
									+ content.integral
									+ "</li></ul>"
									+ "<ul class='row' style='margin-right:0px;'><li class='col-xs-12 about'>"
									+ img + "" + foot + "</li></ul></div>";
							$("#recordList").append(flag);
						});
						init();
					} else {
						AMUI.dialog.alert({
									content : r.msg,
									onConfirm : function() {
										// console.log('close');
									}
								});
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
		loadDownFn : function(me) {
			var c = $("#goodsType").val();
			$.ajax({
				url : '../user/mpintegralrecord/queryList',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					page : pageNum,
					goodsType : c == null ? 2 : c
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
							i++;
							var foot = "";
							var time = "";
								if (content.use_status == 1) {
									time = "<div class='col-xs-3 time'>已使用</div>";
								} else if (!timeCompare(formatDate(new Date(content.use_time
										* 1000)))) {
									time = "<div class='col-xs-3 time'>已过期</div>";
								} else {
									if(content.goods_type == 2){
										time = "<div class='col-xs-6 time'>使用时间:"
											+ formatDate(new Date(content.use_time
													* 1000)) + "</div>";
									}else{
										time = "<div class='col-xs-6 time'>有效时间:"
											+ formatDate(new Date(content.use_time
													* 1000)) + "</div>";
									}
									
								}
							if (content.goods_type == 2) {
								foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
										+ content.exchange_code
										+ "</div>"
										+ time + "</div>";
							} else {
								foot = "<div class='row' style='margin-right:0px;'><div class='col-xs-6 redeemcode'>兑换码:"
										+ content.exchange_code
										+ "</div>"+ time + "</div>";
							}
							var flag = "";
							var img = "";
							if (content.use_status == 1) {
								img = "<img id=" + i
										+ " width='100%' class='Gray' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							} else if (!timeCompare(formatDate(new Date(content.use_time
									* 1000)))) {
								img = "<img id=" + i
										+ " width='100%' class='Gray' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							} else {
								img = "<img id=" + i + " width='100%' src='"
										+ hqc.base + content.goods_thumb
										+ "'/>";
							}
							var integralType = "<span class='label label-warning'>消耗</span>";
							flag = "<div class='listItem'><ul class='row' style='margin-right:0px;'><li class='col-xs-5  ticket'>"
									+ content.goods_name
									+ "</li><li class='col-xs-4 integralType'>"
									+ integralType
									+ "&nbsp;"
									+ content.integral
									+ "</li></ul>"
									+ "<ul class='row' style='margin-right:0px;'><li class='col-xs-12 about'>"
									+ img + "" + foot + "</li></ul></div>";
							$(".dropload-down").before(flag);
						});
					} else {
						AMUI.dialog.alert({
									content : r.msg,
									onConfirm : function() {
										// console.log('close');
									}
								});
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

function timeCompare(event) {
	var yourtime = event;
	yourtime = yourtime.replace("-", "/");// 替换字符，变成标准格式
	var d2 =new Date(Date.parse(formatDate(new Date())));// 取今天的日期
	var d1 = new Date(Date.parse(yourtime));
	if (d2 > d1) {
		return false;
	} else {
		return true;
	}
}

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
	return year + "-" + month + "-" + date ;
}