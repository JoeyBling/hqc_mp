var i = "0";
function query(n) {

	$.ajax({
		url : '../user/memberIntegralrecord/queryList',
		type : 'post',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : n == null ? 1 : n
		},
		success : function(data) {
			if (data.code == 0) {
				$("#recordList").html("");
				$("#page").val(data.page.currPage);
				$.each(data.page.list, function(index, content) {
					
				   var typeFlag=""
				   if(content.integralType==1||content.integralType=='1'){
					   typeFlag="<span class='label label-primary'>进账</span>"
				   }
				   if(content.integralType==2||content.integralType=='2'){
					   typeFlag="<span class='label label-warning'>出账</span>"
				   }
				   var createTime=""
				  if(content.integralType!=null&&content.integralType!=""){
				  var createTime=formatDate(new Date(content.createTime*1000) );
				  }
					flag = "<div class='col-xs-12'><ul class='row'><li class='littleInfo'>"
							+ createTime.substring(0,9)
							+ "</li><li class='middleInfo1'>"
							+ typeFlag
							+ "</li >" 
							+"<li class='middleInfo2'>"+content.integral+"</li>"
							+"<li class='bigInfo'>"+content.about+"</li>"
						    +"</ul>"
							+"</div>";
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
			layer.msg('未知异常，请联系管理员');
			/*window.location.href = location.href + '?time='
					+ ((new Date()).getTime());*/
		}
	})
}
$(function() {
			query(topRequest.QueryString("page")); // 获取所有最新动态
		})
function init() {
	var pageNum = 2;
	// dropload
	$('#recordList').dropload({
		scrollArea : window,
		domUp : {
			domClass : 'dropload-up',
			domRefresh : '<div class="dropload-refresh">↓下拉刷新-我的积分</div>',
			domUpdate : '<div class="dropload-update">↑释放更新-我的积分</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-我的积分...</div>'
		},
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-积分兑换信息</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-我的积分...</div>',
			domNoData : '<div ></div>'
		},
		loadUpFn : function(me) {
			
			$.ajax({
				url : '../user/memberIntegralrecord/queryList',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					page : "1"
				
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
							   var typeFlag=""
								   if(content.integralType==1||content.integralType=='1'){
									   typeFlag="<span class='label label-primary'>进账</span>"
								   }
								   if(content.integralType==2||content.integralType=='2'){
									   typeFlag="<span class='label label-warning'>出账</span>"
								   }
								   var createTime=""
								  if(content.integralType!=null&&content.integralType!=""){
								  var createTime=formatDate(new Date(content.createTime*1000) );
								  }
								   flag = "<div class='col-xs-12'><ul class='row'><li class='littleInfo'>"
									   + createTime.substring(0,9)
										+ "</li><li class='middleInfo1'>"
										+ typeFlag
										+ "</li >" 
										+"<li class='middleInfo2'>"+content.integral+"</li>"
										+"<li class='bigInfo'>"+content.about+"</li>"
									    +"</ul>"
										+"</div>";
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
		
			$.ajax({
				url : '../user/memberIntegralrecord/queryList',
				type : 'post',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					page : pageNum
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
							   var typeFlag=""
								   if(content.integralType==1||content.integralType=='1'){
									   typeFlag="<span class='label label-primary'>进账</span>"
								   }
								   if(content.integralType==2||content.integralType=='2'){
									   typeFlag="<span class='label label-warning'>出账</span>"
								   }
								   var createTime=""
								  if(content.integralType!=null&&content.integralType!=""){
								  var createTime=formatDate(new Date(content.createTime*1000) );
								  }
								   flag = "<div class='col-xs-12'><ul class='row'><li class='littleInfo'>"
									   + createTime.substring(0,9)
										+ "</li><li class='middleInfo1'>"
										+ typeFlag
										+ "</li >" 
										+"<li class='middleInfo2'>"+content.integral+"</li>"
										+"<li class='bigInfo'>"+content.about+"</li>"
									    +"</ul>"
										+"</div>";
									$("#recordList").append(flag);
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
	var d2 = new Date();// 取今天的日期
	var d1 = new Date(Date.parse(yourtime));
	if (d2 > d1) {
		return false;
	} else {
		return true;
	}
}
/**
 * 时间转换
 * @param now
 * @returns {String}
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