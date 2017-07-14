function recentHotel(n) {
	$.ajax({
		url : '../list/hotelfood',
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
							+ "<br/><center>当前没有最新动态</center>");
					return;
				}
				$.each(r.page.list, function(index, content) {
					var flag = '<div class="pageplay" style="clear: both; margin-top: 20px; padding: 10px 10px;'
							+ ' overflow: hidden; border: 1px solid rgb(223, 223, 223); background-color:'
							+ ' rgb(255, 255, 255);"onclick="location.href=\'../list/detail?id='
							+ content.id
							+ '\';"><a href="../list/detail?id='
							+ content.id
							+ '" style="font-size:42px; color:#00baf7;"><div style='
							+ '"font-size:18px; padding:10px 0 20px 0; color:#565656; font-weight:bold;">'
							+ content.title + '</div></a>';

					flag += '<div><a href="../list/detail?id='
							+ content.id
							+ '" style="font-size:42px; color:#00baf7;">'
							+ '</a><a href="../list/detail?id='
							+ content.id
							+ '"><img '
							+ 'src="'
							+ hqc.base
							+ content.thumb
							+ '" '
							+ 'width="100%"></a></div><div style="color:#666; font-size:14px; padding:20px 0;'
							+ 'text-overflow:ellipsis;height:140px;white-space:nowrap;overflow:hidden; '
							+ ' overflow: hidden;white-space: nowrap;text-overflow:ellipsis; -webkit-line-clamp: 2; '
							+ '">'
							+ content.content
							+ '</div><div style="text-align:right;">了解详情&gt;&gt;</div></div>';
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
			recentHotel(topRequest.QueryString("page")); // 获取所有酒店餐饮
		});

function init() {
	var pageNum = 2;
	// dropload
	$('#content').dropload({
		scrollArea : window,
		domUp : {
			domClass : 'dropload-up',
			domRefresh : '<div class="dropload-refresh">↓下拉刷新-酒店餐饮</div>',
			domUpdate : '<div class="dropload-update">↑释放更新-酒店餐饮</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-酒店餐饮...</div>'
		},
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-酒店餐饮</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-酒店餐饮...</div>',
			domNoData : '<div class="dropload-noData">暂无数据-酒店餐饮</div>'
		},
		loadUpFn : function(me) {
			$.ajax({
				type : 'POST',
				url : '../list/hotelfood',
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
									+ "<br/><center>当前没有最新动态</center>");
							return;
						}
						$.each(r.page.list, function(index, content) {
							var flag = '<div class="pageplay" style="clear: both; margin-top: 20px; padding: 10px 10px;'
									+ ' overflow: hidden; border: 1px solid rgb(223, 223, 223); background-color:'
									+ ' rgb(255, 255, 255);"onclick="location.href=\'../list/detail?id='
									+ content.id
									+ '\';"><a href="../list/detail?id='
									+ content.id
									+ '" style="font-size:42px; color:#00baf7;"><div style='
									+ '"font-size:18px; padding:10px 0 20px 0; color:#565656; font-weight:bold;">'
									+ content.title + '</div></a>';

							flag += '<div><a href="../list/detail?id='
									+ content.id
									+ '" style="font-size:42px; color:#00baf7;">'
									+ '</a><a href="../list/detail?id='
									+ content.id
									+ '"><img '
									+ 'src="'
									+ hqc.base
									+ content.thumb
									+ '" '
									+ 'width="100%"></a></div><div style="color:#666; font-size:14px; padding:20px 0;'
									+ 'text-overflow:ellipsis;height:140px;white-space:nowrap;overflow:hidden; '
									+ ' overflow: hidden;white-space: nowrap;text-overflow:ellipsis; -webkit-line-clamp: 2; '
									+ '">'
									+ content.content
									+ '</div><div style="text-align:right;">了解详情&gt;&gt;</div></div>';
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
				url : '../list/hotelfood',
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
							var flag = '<div class="pageplay" style="clear: both; margin-top: 20px; padding: 10px 10px;'
									+ ' overflow: hidden; border: 1px solid rgb(223, 223, 223); background-color:'
									+ ' rgb(255, 255, 255);"onclick="location.href=\'../list/detail?id='
									+ content.id
									+ '\';"><a href="../list/detail?id='
									+ content.id
									+ '" style="font-size:42px; color:#00baf7;"><div style='
									+ '"font-size:18px; padding:10px 0 20px 0; color:#565656; font-weight:bold;">'
									+ content.title + '</div></a>';

							flag += '<div><a href="../list/detail?id='
									+ content.id
									+ '" style="font-size:42px; color:#00baf7;">'
									+ '</a><a href="../list/detail?id='
									+ content.id
									+ '"><img '
									+ 'src="'
									+ hqc.base
									+ content.thumb
									+ '" '
									+ 'width="100%"></a></div><div style="color:#666; font-size:14px; padding:20px 0;'
									+ 'text-overflow:ellipsis;height:140px;white-space:nowrap;overflow:hidden; '
									+ ' overflow: hidden;white-space: nowrap;text-overflow:ellipsis; -webkit-line-clamp: 2; '
									+ '">'
									+ content.content
									+ '</div><div style="text-align:right;">了解详情&gt;&gt;</div></div>';
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