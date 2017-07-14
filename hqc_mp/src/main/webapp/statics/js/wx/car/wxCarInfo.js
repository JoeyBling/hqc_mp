function recentHotel(n) {
	$.ajax({
		url : '../car/chargelist',
		rtype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : n == null ? 1 : n
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$("#content").html("");
				if (r.page.list == null || r.page.list.length < 1) {
					$("#content").append("<img src='" + hqc.base
							+ "statics/images/no.png' width='100%'/>"
							+ "<br/><center>当前没有停车记录</center>");
					return;
				}
				$.each(r.page.list, function(index, content) {
					var flag = '<div style="width:97%;margin-left:1.5%;background:#fff;margin-top:10px">'
							+ '<div class="small_header" style="border:0.5px solid #BBE4FF;border-bottom:none">'
							+ '<img src="'
							+ hqc.base
							+ 'statics/images/wx/zzz.png" style="width:26px;height:26px">'
							+ '<div class="carNum" style="font-size:14px;margin-top:15px;display:inline">'
							+ content.plate
							+ '</div></div><div class="small_body" style="background-color:#77C9FF;height:80px;">'
							+ '<div style="height:30%;width:80%;float:left;font-size:12px;color:#fff;margin-top:10px">'
							+ ' <lable style="padding-left:10px">驶入时间:</lable> <span>'
							+ formatDate(new Date(content.startTime * 1000))
							+ '</span></div>'
							+ '<div style="width:19%;float:right;height:100%;font-size:12px;color:#fff;'
							+ 'font-weight:bold;padding-top:43px"><lable stylle="margin-left:-20px">';
					if (content.status === 1) {
						flag += "已付款";
					} else if (content.status === 2) {
						flag += "未支付成功";
					} else if (content.status === 2) {
						flag += "未支付";
					} else if (content.status === 2) {
						flag += "已退款";
					}
					flag += '</lable></div><div style="height:30%;width:80%;'
							+ 'float:left;font-size:12px;color:#fff;margin-top:10px">'
							+ '<lable style="padding-left:10px">驶出时间:</lable> <span>'
							+ formatDate(new Date(content.startTime * 1000))
							+ '</span> </div><div style="clear:both"></div> </div>'
							+ '<div class="small_footer" style="border:0.5px solid #BBE4FF;border-top:none">'
							+ '订单号:'
							+ '<div style="font-size:8px;margin-top:15px;display:inline">'
							+ content.orderNo
							+ '</div><div style="margin-left:17%;display:inline;font-weight:bold">'
							+ '<lable style="font-size:14px;font-weight:bold">';
					if (content.status === 1) {
						flag += "已付款";
					} else if (content.status === 2) {
						flag += "未支付成功";
					} else if (content.status === 2) {
						flag += "未支付";
					} else if (content.status === 2) {
						flag += "已退款";
					}
					flag += ':</lable><span style="font-size:14px;color:red;font-weight:bold">'
							+ content.totalFee + '元</span></div></div></div>';
					$("#content").append(flag);
				});
				init();
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
						var paraString = url.replace(/\//g, "%2F").replace(
								/:/g, "%3A").replace(/=/g, "%3D").replace(
								/\?/g, "%3F");
						parent.location.href = hqc.base
								+ 'wx/user/login.html?returnUrl=' + paraString;
					}, 2000);
				}
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
			recentHotel(); // 获取停车付费信息
		});

function init() {
	var pageNum = 2;
	// dropload
	$('#content').dropload({
		scrollArea : window,
		domUp : {
			domClass : 'dropload-up',
			domRefresh : '<div class="dropload-refresh">↓下拉刷新-停车付费</div>',
			domUpdate : '<div class="dropload-update">↑释放更新-停车付费</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-停车付费...</div>'
		},
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-停车付费</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-停车付费...</div>',
			domNoData : '<div class="dropload-noData">暂无数据-停车付费</div>'
		},
		loadUpFn : function(me) {
			$.ajax({
				type : 'POST',
				url : '../car/chargelist',
				dataType : 'json',
				data : {
					page : 1
				},
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				success : function(r) {
					// 为了测试，延迟1秒加载
					setTimeout(function() {
								// 每次数据加载完，必须重置
								me.resetload();
							}, 500);
					if (r.code === 0) {
						$("#content").html("");
						if (r.page.list == null || r.page.list.length < 1) {
							$("#content").append("<img src='" + hqc.base
									+ "statics/images/no.png' width='100%'/>"
									+ "<br/><center>当前没有停车记录</center>");
							return;
						}
						$.each(r.page.list, function(index, content) {
							var flag = '<div style="width:97%;margin-left:1.5%;background:#fff;margin-top:10px">'
									+ '<div class="small_header" style="border:0.5px solid #BBE4FF;border-bottom:none">'
									+ '<img src="'
									+ hqc.base
									+ 'statics/images/wx/zzz.png" style="width:26px;height:26px">'
									+ '<div class="carNum" style="font-size:14px;margin-top:15px;display:inline">'
									+ content.plate
									+ '</div></div><div class="small_body" style="background-color:#77C9FF;height:80px;">'
									+ '<div style="height:30%;width:80%;float:left;font-size:12px;color:#fff;margin-top:10px">'
									+ ' <lable style="padding-left:10px">驶入时间:</lable> <span>'
									+ formatDate(new Date(content.startTime
											* 1000))
									+ '</span></div>'
									+ '<div style="width:19%;float:right;height:100%;font-size:12px;color:#fff;'
									+ 'font-weight:bold;padding-top:43px"><lable stylle="margin-left:-20px">';
							if (content.status === 1) {
								flag += "已付款";
							} else if (content.status === 2) {
								flag += "未支付成功";
							} else if (content.status === 2) {
								flag += "未支付";
							} else if (content.status === 2) {
								flag += "已退款";
							}
							flag += '</lable></div><div style="height:30%;width:80%;'
									+ 'float:left;font-size:12px;color:#fff;margin-top:10px">'
									+ '<lable style="padding-left:10px">驶出时间:</lable> <span>'
									+ formatDate(new Date(content.startTime
											* 1000))
									+ '</span> </div><div style="clear:both"></div> </div>'
									+ '<div class="small_footer" style="border:0.5px solid #BBE4FF;border-top:none">'
									+ '订单号:'
									+ '<div style="font-size:8px;margin-top:15px;display:inline">'
									+ content.orderNo
									+ '</div><div style="margin-left:17%;display:inline;font-weight:bold">'
									+ '<lable style="font-size:14px;font-weight:bold">';
							if (content.status === 1) {
								flag += "已付款";
							} else if (content.status === 2) {
								flag += "未支付成功";
							} else if (content.status === 2) {
								flag += "未支付";
							} else if (content.status === 2) {
								flag += "已退款";
							}
							flag += ':</lable><span style="font-size:14px;color:red;font-weight:bold">'
									+ content.totalFee
									+ '元</span></div></div></div>';
							$("#content").append(flag);
						});
						init();
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
		loadDownFn : function(me) {
			$.ajax({
				type : 'POST',
				url : '../car/chargelist',
				dataType : 'json',
				data : {
					page : pageNum
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
							var flag = '<div style="width:97%;margin-left:1.5%;background:#fff;margin-top:10px">'
									+ '<div class="small_header" style="border:0.5px solid #BBE4FF;border-bottom:none">'
									+ '<img src="'
									+ hqc.base
									+ 'statics/images/wx/zzz.png" style="width:26px;height:26px">'
									+ '<div class="carNum" style="font-size:14px;margin-top:15px;display:inline">'
									+ content.plate
									+ '</div></div><div class="small_body" style="background-color:#77C9FF;height:80px;">'
									+ '<div style="height:30%;width:80%;float:left;font-size:12px;color:#fff;margin-top:10px">'
									+ ' <lable style="padding-left:10px">驶入时间:</lable> <span>'
									+ formatDate(new Date(content.startTime
											* 1000))
									+ '</span></div>'
									+ '<div style="width:19%;float:right;height:100%;font-size:12px;color:#fff;'
									+ 'font-weight:bold;padding-top:43px"><lable stylle="margin-left:-20px">';
							if (content.status === 1) {
								flag += "已付款";
							} else if (content.status === 2) {
								flag += "未支付成功";
							} else if (content.status === 2) {
								flag += "未支付";
							} else if (content.status === 2) {
								flag += "已退款";
							}
							flag += '</lable></div><div style="height:30%;width:80%;'
									+ 'float:left;font-size:12px;color:#fff;margin-top:10px">'
									+ '<lable style="padding-left:10px">驶出时间:</lable> <span>'
									+ formatDate(new Date(content.startTime
											* 1000))
									+ '</span> </div><div style="clear:both"></div> </div>'
									+ '<div class="small_footer" style="border:0.5px solid #BBE4FF;border-top:none">'
									+ '订单号:'
									+ '<div style="font-size:8px;margin-top:15px;display:inline">'
									+ content.orderNo
									+ '</div><div style="margin-left:17%;display:inline;font-weight:bold">'
									+ '<lable style="font-size:14px;font-weight:bold">';
							if (content.status === 1) {
								flag += "已付款";
							} else if (content.status === 2) {
								flag += "未支付成功";
							} else if (content.status === 2) {
								flag += "未支付";
							} else if (content.status === 2) {
								flag += "已退款";
							}
							flag += ':</lable><span style="font-size:14px;color:red;font-weight:bold">'
									+ content.totalFee
									+ '元</span></div></div></div>';
							$(".dropload-down").before(flag);
						});
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