/**
 * 下载联网二维码
 */
function downloadWifi(shop_id, ssid) {
	layer.open({
		type : 1,
		skin : 'layui-layer-demo', // 样式类名
		closeBtn : 2, // 显示关闭按钮
		anim : 2,
		title : "下载联网二维码",
		shadeClose : true, // 开启遮罩关闭
		content : '<div class="grid-btn"><div style="padding:20px;"><a class="btn btn-info" '
				+ 'onclick="javascript:goD(\''
				+ shop_id
				+ '\',\''
				+ ssid
				+ '\',0);\"><i class="fa fa-mail-reply">'
				+ '</i>&nbsp;纯二维码</a><a class="btn btn-info" '
				+ 'onclick="javascript:goD(\''
				+ shop_id
				+ '\',\''
				+ ssid
				+ '\',1);\"><i class="fa fa-mail-reply">'
				+ '</i>&nbsp;二维码物料</a></div></div>'
	});
}

function goD(shop_id, ssid, type) {
	$.ajax({
				url : '../mp/wifi/download?shopid=' + shop_id + '&ssid=' + ssid
						+ '&type=' + type,
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				type : 'get',
				success : function(r) {
					if (r.code == 0) {
						window.open(r.qrcode.data.qrcodeUrl);
					} else {
						layer.msg(r.msg);
					}
				},
				error : function() {
					layer.msg("系统异常,请联系管理员!");
				}
			});
}

function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../mp/wifi/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code == 0) {
				$.each(r.page.list, function(index, content) {
					var protocolName = "";
					if (content.protocolType === 0) {
						protocolName = "未添加设备";
					} else if (content.protocolType === 4) {
						protocolName = "密码型设备";
					} else if (content.protocolType === 31) {
						protocolName = "portal型设备";
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.bssid
									+ "'/></td><td>"
									+ content.shopId
									+ "</td><td>"
									+ content.ssid
									+ "</td><td>"
									+ content.bssid
									+ "</td><td>"
									+ protocolName
									+ "</td><td><a class='label label-success' onclick=\"downloadWifi('"
									+ content.shopId
									+ "','"
									+ content.ssid
									+ "');\">&nbsp;下载二维码</a></td></tr>");
				});
				$("#page").val(r.page.currPage);
				$(".paginList").html("");
				if (r.page.currPage != 1) {
					$(".paginList")
							.html("<li class='paginItem' ><a href='javascript:void(0);' onclick='queryList(1)'><span class='pagepre'></span></a></li>");

				}
				$(".total").html(r.page.totalCount);
				$(".page")
						.html("<input type='text' readonly='readonly' style='width:20px' value='"
								+ r.page.currPage + "'/>");
				$(".pagetoal").html(r.page.totalPage);
				for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
					if (r.page.totalPage < 2) {

					} else {
						if (i < r.page.currPage + 3 && i > -1) {
							$(".paginList")
									.append(" <div  id='pageint"
											+ (i + 1)
											+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
											+ (i + 1) + ")'>" + (i + 1)
											+ "</a></div>");
						}
					}
				}
				$("#pageint" + r.page.currPage + "")
						.html("<div class='page_curr'>"
								+ r.page.currPage + "</div>");
				if (r.page.currPage < r.page.totalPage
						&& r.page.totalPage !== 0) {
					$(".paginList")
							.append("<li style='margin-left:0.5px' class='paginItem'><a href='javascript:void(0);' onclick='queryList(2)'><span class='pagenxt'></span></a></li>");

				}

				$("#rightbody .tablelist tr:gt(0)").on("click", function(e) {
					var ids = $(e.currentTarget).find("td input[name='ids']")[0];
					if (null != ids && ids.checked) {
						ids.checked = false;
						$(e.currentTarget).css("background", "");
					} else {
						ids.checked = true;
						$(e.currentTarget).css("background", "#e5ebee");
					}
				});
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
	});
}

$(function() {
			queryList();

			initBu();

			/**
			 * 全选
			 */
			$("#selectAll").click(function() {
						$('input[name="ids"]').prop("checked", this.checked);
						if (this.checked) {
							$(".tablelist tr:gt(0)").css("background",
									"#e5ebee");
						} else {
							$('input[name="ids"]');
							$(".tablelist tr:gt(0)").css("background", "");
						}
					});
		});

function showBu(e) {
	layer.open({
				type : 1,
				offset : '50px',
				skin : 'layui-layer-molv',
				title : '选择门店',
				area : ['495px', '430px'],
				shade : 0.8,
				shadeClose : true,
				content : jQuery("#showBu"),
				btn : ['关闭'],
				btn1 : function(index) {
					layer.close(index);
				}
			});
}

function clickpage(event) {
	$("#page").val(event);
	queryList();
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

/**
 * 删除操作
 */
function del() {
	var ids = checkData();
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的WiFi设备？', function() {
				// 加载层
				var loading = layer.load(2, {
							shade : false
						}); // 0代表加载的风格，支持0-2
				$.ajax({
							type : "POST",
							url : "../mp/wifi/delete",
							data : {
								bssid : ids
							},
							success : function(r) {
								layer.close(loading); // 加载层去掉
								if (r.code == 0) {
									layer.alert('操作成功', function(index) {
												layer.close(index);
												reload();
											});
								} else {
									layer.alert(r.msg);
								}
							},
							error : function() {
								layer.alert('系统出现异常!', function() {
											location.reload();
										});
							}
						});
			});
}

/**
 * 保存或更新操作
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function save(event) {
	var url = "../mp/wifi/save";
	// 加载层
	var loading = layer.load(2, {
				shade : false
			}); // 0代表加载的风格，支持0-2
	$.ajax({
				type : "POST",
				url : url,
				data : $("#wifi").serialize(),
				success : function(r) {
					layer.close(loading);
					if (r.code === 0) {
						layer.alert(r.msg, function(index) {
									layer.close(index);
									reload();
								});
					} else {
						layer.alert(r.msg);
					}
				},
				error : function() {
					layer.alert('系统出现异常!', function() {
								location.reload();
							});
				}
			});
}

function add() {
	// 清空
	$("input[name='businessName']").val("");
	$("input[name='shopId']").val("");
	$("input[name='password']").val("");
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

// 获取选中的多选框的值
function checkData() {
	var ids = "";
	$("#list td").each(function() {
		if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
			if (ids.length == 0) {
				ids = $(this).find("input[type=checkbox]:checked").val();
			} else {
				ids = ids + ","
						+ $(this).find("input[type=checkbox]:checked").val();
			}
		}
	});
	return ids;
}

/**
 * 和微信端同步数据
 */
function syn() {
	layer.confirm('确定要和微信端同步数据？', function() {
				// 加载层
				var loading = layer.load(2, {
							shade : false
						}); // 0代表加载的风格，支持0-2
				$.ajax({
							type : "POST",
							url : "../mp/wifi/syn",
							success : function(r) {
								layer.close(loading); // 加载层去掉
								if (r.code == 0) {
									layer.alert('操作成功', function(index) {
												layer.close(index);
												reload();
											});
								} else {
									layer.alert(r.msg);
								}
							},
							error : function() {
								layer.alert('系统出现异常!', function() {
											location.reload();
										});
							}
						});
			});
}

function initBu() {
	$.ajax({
		url : '../mp/wifi/wifiShop',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : 1,
			limit : 20
		},
		type : 'post',
		success : function(r) {
			if (r.code == 0) {
				$.each(r.wifiShop.data.records, function(index, content) {
					$("#buList")
							.append("<tr><td><input name='ids' type='hidden' value='"
									+ content.shopId
									+ "'/>"
									+ content.shopName
									+ "</td></tr>");
				});
				$("#showBu .tablelist tr:gt(0)").on("click", function(e) {
					var ids = $(e.currentTarget).find("td input[name='ids']")
							.val();
					var name = $(e.currentTarget).find("td:eq(0)").text();
					$("input[name='businessName']").val(name);
					$("input[name='shopId']").val(ids);
					layer.closeAll();
				});
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
	});
}