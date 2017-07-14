function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../sys/menu/list',
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
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var date = formatDate(new Date(content.createTime));
					var menuType = ""

					if (content.type === 0) {
						menuType = '<span class="label label-primary">目录</span>';
					}
					if (content.type === 1) {
						menuType = '<span class="label label-success">菜单</span>';
					}
					if (content.type === 2) {
						menuType = '<span class="label label-warning">按钮</span>';
					}

					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.menuId
									+ "'/></td>"
									+ "<td>"
									+ content.menuId
									+ "</td><td>"
									+ (content.name == null ? '' : content.name)
									+ "</td><td>"
									+ (content.parentName == null
											? ''
											: content.parentName)
									+ "</td>"
									+ "<td>"
									+ (content.icon == null ? '' : '<i class="'
											+ content.icon + ' fa-lg"></i>')
									+ "</td><td>"
									+ (content.url == null ? '' : content.url)
									+ "</td>"
									+ "<td>"
									+ (content.perms == null
											? ''
											: content.perms)
									+ "</td><td>"
									+ menuType
									+ "</td><td>"
									+ (content.orderNum == null
											? ''
											: content.orderNum) + "</td></tr>")
				})
				$("#page").val(r.page.currPage)
				var lastpage = "";
				var nextpage = "";
				$(".paginList").html("")
				if (r.page.currPage != 1) {
					$(".paginList")
							.html("<li class='paginItem' ><a href='javascript:void(0);' onclick='queryList(1)'><span class='pagepre'></span></a></li>");

				}
				$(".total").html(r.page.totalCount)
				$(".page")
						.html("<input type='text' readonly='readonly' style='width:20px' value='"
								+ r.page.currPage + "'/>")
				$(".pagetoal").html(r.page.totalPage)
				for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
					if (i < r.page.currPage + 3 && i > -1) {
						$(".paginList")
								.append(" <div  id='pageint"
										+ (i + 1)
										+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
										+ (i + 1) + ")'>" + (i + 1)
										+ "</a></div>")
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

function clickpage(event) {
	$("#page").val(event)
	queryList();
}

$(function() {
			queryList();
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
		});
function menuTree() {
	layer.open({
				type : 1,
				offset : '50px',
				skin : 'layui-layer-molv',
				title : "选择菜单",
				area : ['300px', '450px'],
				shade : 0,
				shadeClose : false,
				content : jQuery("#menuLayer"),
				btn : ['确定', '取消'],
				btn1 : function(index) {
					var node = ztree.getSelectedNodes();
					// 选择上级菜单
					$("input[name='parentName']").val(node[0].name)
					$("input[name='parentId']").val(node[0].menuId)
					layer.close(index);
				}
			});
}
var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}
	}
};
var ztree;
function getMenu(id) {
	// 加载菜单树
	$.get("../sys/menu/select", function(r) {
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				var node = ztree.getNodeByParam("menuId", id);
				ztree.selectNode(node);
			});
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
	return year + "-" + month + "-" + date + " " + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}

function openAddUser() {
	$("input[name='menuId']").val(" ");
	$("input[name='name']").val("")
	$("input[name='parentName']").val("");
	$("input[name='url']").val("");
	$("input[name='perms']").val(" ");
	$("input[name='orderNum']").val("");
	$("input[name='icon']").val(" ");
	$("input[name='parentId']").val("");
	$("input[name='type'][value=0]").prop("checked", true);
	$("input[name='menuId']").val("");
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
	checkRadio("catalog");
	getMenu();
}

function checkRadio(event) {
	if (event == "catalog") {
		$("#catalogTable").css("display", "block");
		$("#buttonTable").css("display", "none");
		$("#menuTable").css("display", "none");
	}
	if (event == "button") {
		$("#catalogTable").css("display", "none");
		$("#buttonTable").css("display", "block");
		$("#menuTable").css("display", "none");
	}
	if (event == "menu") {
		$("#catalogTable").css("display", "block");
		$("#buttonTable").css("display", "block");
		$("#menuTable").css("display", "block");
	}
}

function saveOrUpdate() {
	var id=$("input[name='menuId']").val();
	var ids = checkData();
	var url = "";
	if (id.length == 0) {
		url = "../sys/menu/save";
	} else {
		url = "../sys/menu/update";
	}
	$.ajax({
				cache : true,
				type : "POST",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				dataType : "json",
				async : false,
				url : url,
				data : $('#menuForm').serialize(),
				success : function(r) {
					if (r.code === 0) {
						layer.alert('操作成功', function(index) {
									layer.close(index);
									reload();
								});
					} else {
						layer.msg(r.msg);
					}
				},
				error : function() {
					layer.alert("系统异常", function() {
								reload();
							});
				}
			});
}

function openUpdate() {
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$.get("../sys/menu/info/" + ids, function(r) {
				$("input[name='menuId']").val(r.menu.menuId);
				$("input[name='name']").val(r.menu.name)
				$("input[name='parentName']").val(r.menu.parentName)
				$("input[name='url']").val(r.menu.url)
				$("input[name='perms']").val(r.menu.perms)
				$("input[name='orderNum']").val(r.menu.orderNum)
				$("input[name='icon']").val(r.menu.icon)
				$("input[name='parentId']").val(r.menu.parentId)
				$("input[name='type'][value=" + r.menu.type + "]").prop(
						"checked", true);
				if (r.menu.type == "0") {
					checkRadio("catalog");
				}
				if (r.menu.type == "1") {
					checkRadio("menu");
				}
				if (r.menu.type == "2") {
					checkRadio("button");
				}
				$.get("../sys/menu/info/" + $("input[name='parentId']").val(),
						function(r) {
							$("input[name='parentName']").val(r.menu.name)
						});

			});
	getMenu(ids)
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")

}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
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

function deleteMenu() {
	var ids = checkData();
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../sys/menu/delete",
							data : {
								menuIds : ids
							},
							success : function(r) {
								if (r.code === 0) {
									layer.alert('操作成功', function(index) {
												$("#page").val("1");
												layer.close(index);
												reload();
											});
								} else {
									layer.msg(r.msg);
								}
							},
							error : function() {
								layer.alert("系统异常", function() {
											reload();
										});
							}
						});
			});
}