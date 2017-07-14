function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}

function selectByname(){
	$("#page").val("1");
	queryList();
}


function queryList(n) {
	$("#list").html("");
	var limit = $("#limit").val();
	var page = $("#page").val();
	$.ajax({
		url : '../sys/role/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			roleName:$("#roleName").val(),
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var date = formatDate(new Date(content.createTime * 1000));

					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.roleId
									+ "'/></td><td>"
									+ content.roleId
									+ "</td><td>"
									+ content.roleName
									+ "</td><td>"
									+ (content.remark == null
											? ''
											: content.remark)
									+ "</td><td>"
									+ date + "</td></tr>")
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
			layer.msg("系统异常")
		}
	});
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
		})

function openAddRole() {
	$("input[name='roleId']").val("");
	$("input[name='roleName']").val("");
	$("input[name='remark']").val("");
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
	getMenuTree(null);
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
	},
	check : {
		enable : true,
		nocheckInherit : true
	}
};
var ztree;
function getRole(roleId) {
	$.get("../sys/role/info/" + roleId, function(r) {

				// 勾选角色所拥有的菜单
				$("input[name='roleName']").val(r.role.roleName);
				$("input[name='remark']").val(r.role.remark);
				var menuIds = r.role.menuIdList;
				for (var i = 0; i < menuIds.length; i++) {
					var node = ztree.getNodeByParam("menuId", menuIds[i], null);
					ztree.checkNode(node, true, false);
				}
			});
}
function openUpdateRole() {
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
	 $("input[name='roleId']").val(ids);
	getMenuTree(ids);
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
}

function saveOrUpdate(event) {
	// 获取选择的菜单
	var ids = checkData();
	var url = "";
	var id= $("input[name='roleId']").val();
	if (id.length == 0) {
		url = "../sys/role/save";
	} else {
		url = "../sys/role/update";
	}
	var nodes = ztree.getCheckedNodes(true);
	var menuids = "";
	for (var i = 0; i < nodes.length; i++) {
		if (i == 0) {
			menuids = nodes[i].menuId
		} else {
			menuids = menuids + "," + nodes[i].menuId
		}
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
				data : {
					roleName : $("input[name='roleName']").val(),
					remark : $("input[name='remark']").val(),
					menuIds : menuids,
					roleId :  $("input[name='roleId']").val()
				},
				success : function(r) {
					if (r.code === 0) {
						layer.alert('操作成功', function(index) {
									layer.close(index);
									reload();
								});
					} else {
						layer.msg(r.msg)
					}
				},
				error : function() {
					layer.alert("系统异常,请联系管理员!", function() {
								reload();
							});
				}
			});
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

function getMenuTree(roleId) {
	// 加载菜单树
	$.get("../sys/menu/perms", function(r) {
				ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
				// 展开所有节点
				ztree.expandAll(true);

				if (roleId != null) {
					getRole(roleId);
				}
			});
}

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
	})
	return ids;
}

function deleteRole() {
	var roleIds = checkData();
	if (roleIds.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../sys/role/delete",
							data : {
								roleIds : roleIds
							},
							success : function(r) {
								if (r.code == 0) {
									layer.alert('操作成功', function(index) {
												layer.close(index);
												$("#page").val("1");
												reload();
											});
								} else {
									layer.msg(r.msg);
								}
							},
							error : function() {
								layer.alert('系统出现异常!', function() {
											reload();
										});
							}
						});
			});
}