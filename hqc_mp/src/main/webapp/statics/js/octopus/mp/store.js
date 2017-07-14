/**
 * 今日可同步数据的剩余机会
 * 
 * @type
 */
var remoppo;

function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../mp/store/list',
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
				remoppo = r.remoppo;
				$.each(r.page.list, function(index, content) {
					content.province = content.province == null
							? ""
							: content.province;
					content.city = content.city == null ? "" : content.city;
					content.district = content.district == null
							? ""
							: content.district;
					content.address = content.address == null
							? ""
							: content.address;
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.poiId
									+ "'/></td><td>"
									+ content.businessName
									+ "</td><td>"
									+ content.branchName
									+ "</td><td>"
									+ content.categories
									+ "</td><td>"
									+ (content.telephone == null
											? ""
											: content.telephone)
									+ "</td><td>"
									+ (content.province + content.city
											+ content.district + content.address)
									+ "</td></tr>")
				});
				$("#page").val(r.page.currPage)
				var lastpage = "";
				var nextpage = "";
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
											+ "</a></div>")
						}
					}
				}
				$("#pageint" + r.page.currPage + "")
						.html("<div class='page_curr'>"
								+ r.page.currPage + "</div>")
				if (r.page.currPage < r.page.totalPage
						&& r.page.totalPage !== 0) {
					$(".paginList")
							.append("<li style='margin-left:0.5px' class='paginItem'><a href='javascript:void(0);' onclick='queryList(2)'><span class='pagenxt'></span></a></li>");

				}

				$(".tablelist tr:gt(0)").on("click", function(e) {
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

var gobalMap;
/**
 * 初始化腾讯地图
 */
function initMap() {
	var container = document.getElementById("container");
	var map = new qq.maps.Map(container, {
				zoom : 10
			}), label = new qq.maps.Label({
				map : map,
				offset : new qq.maps.Size(15, -12),
				draggable : false,
				clickable : false
			}), markerArray = [], url, query_city, cityservice = new qq.maps.CityService(
			{
				complete : function(result) {
					map.setCenter(result.detail.latLng);
				}
			});
	cityservice.searchLocalCity();
	map.setOptions({
				draggableCursor : "crosshair"
			});
	gobalMap = map;
	qq.maps.event.addListener(map, "mousemove", function(e) {
				var latlng = e.latLng;
				label.setPosition(latlng);
				label.setContent(latlng.getLat().toFixed(6) + ","
						+ latlng.getLng().toFixed(6));
			});

	qq.maps.event.addListener(map, "click", function(e) {
				var latlng = e.latLng;
				$("input[name='location']").val(latlng.getLat().toFixed(6)
						+ "," + latlng.getLng().toFixed(6));
			});

}
$(function() {

			initMap();
			queryList();

			getCateGories();

			/**
			 * 全选
			 */
			$("#selectAll").click(function() {
						$('input[name="ids"]').prop("checked", this.checked);
						if (this.checked) {
							$(".tablelist tr:gt(0)").css("background",
									"#e5ebee");
						} else {
							$('input[name="ids"]')
							$(".tablelist tr:gt(0)").css("background", "");
						}
					});
		})

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
 * 所有门店类目信息
 */
function getCateGories() {
	$.ajax({
				url : '../mp/store/categories',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				type : 'post',
				success : function(r) {
					if (r.code == 0) {
						var op = "";
						$("#cateTypes").find("option").empty();
						$.each(r.list, function(index, content) {
									op += "<option>" + content + "</option>"

								});
						if ("" != op)
							$("#cateTypes").append(op);
					} else {
						layer.msg(r.msg);
					}
				},
				error : function() {
					layer.msg("系统异常,请联系管理员!");
				}
			});
}

/**
 * 
 * 更新
 * 
 * @return {Boolean}
 */
function update() {
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		ids = "";
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	// 清空
	$("input[name='poiId']").val("");
	$("input[name='businessName']").val("");
	$("input[name='branchName']").val("");
	$("input[name='telephone']").val("");
	$("input[name='province']").val("");
	$("input[name='city']").val("");
	$("input[name='district']").val("");
	$("input[name='address']").val("");
	$("input[name='location']").val("");

	$.get("../mp/store/info/" + ids, function(r) {
				if (r.code === 0) {
					/** 是否是修改 */
					$("input[name='poiId']").val(r.info.poiId);
					$("input[name='businessName']").val(r.info.businessName);
					$("input[name='branchName']").val(r.info.branchName);
					$("input[name='telephone']").val(r.info.telephone);
					$("input[name='province']").val(r.info.province);
					$("input[name='city']").val(r.info.city);
					$("input[name='district']").val(r.info.district);
					$("input[name='address']").val(r.info.address);
					$("input[name='location']").val(r.info.location);
					$("#cateTypes").val(r.info.categories.replace(/^\{/gi, "")
							.replace(/\}/gi, ""));
					var flag = r.info.location.split(",");
					gobalMap.panTo(new qq.maps.LatLng(flag[0], flag[1]));
				} else {
					layer.msg(r.msg);
				}
			});
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
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
	layer.confirm('确定要删除选中的门店管理？', function() {
				// 加载层
				var loading = layer.load(2, {
							shade : false
						}); // 0代表加载的风格，支持0-2
				$.ajax({
							type : "POST",
							url : "../mp/store/delete",
							data : {
								poiId : ids
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
function saveOrUpdate(event) {
	var poiId = $("#poiId").val() == "" ? null : $("#poiId").val();
	var url = poiId == null ? "../mp/store/save" : "../mp/store/update";
	// 加载层
	var loading = layer.load(2, {
				shade : false
			}); // 0代表加载的风格，支持0-2
	$.ajax({
				type : "POST",
				url : url,
				data : $("#storeForm").serialize(),
				success : function(r) {
					layer.close(loading);
					if (r.code === 0) {
						layer.alert('操作成功,请等待微信端审核!', function(index) {
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

/**
 * 和微信端同步数据
 */
function syn() {
	layer.confirm('确定要和微信端同步数据？(注：今日剩余' + remoppo + '次机会)', function() {
				// 加载层
				var loading = layer.load(2, {
							shade : false
						}); // 0代表加载的风格，支持0-2
				$.ajax({
							type : "POST",
							url : "../mp/store/syn",
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

function add() {
	// 清空
	$("input[name='poiId']").val("");
	$("input[name='businessName']").val("");
	$("input[name='branchName']").val("");
	$("input[name='telephone']").val("");
	$("input[name='province']").val("");
	$("input[name='city']").val("");
	$("input[name='district']").val("");
	$("input[name='address']").val("");
	$("input[name='location']").val("");
	initMap();
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

// 获取选中的多选框的值
function checkData() {
	var ids = "";
	$("#list td").each(function() {
		if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
			if (ids.length == 0) {
				ids = $(this).find("input[type=checkbox]:checked").val()
			} else {
				ids = ids + ","
						+ $(this).find("input[type=checkbox]:checked").val();
			}
		}
	});
	return ids;
}
