function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function selectByname(){
	$("#page").val("1");
	queryList();
}

function queryList(n) {
	var limit = $("#limit").val();
	$("#list").html("");
	var page = $("#page").val();
	$.ajax({
		url : '../sys/user/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			username:$("#username").val(),
			page : page,
			checkpage : n,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var date = formatDate(new Date(content.createTime * 1000));
					var status = ""
					if (content.status == 0) {
						status = "<span class='label label-danger'>禁用</span>";
					}
					if (content.status == 1) {
						status = "<span class='label label-success'>正常</span>";
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.userId
									+ "'/></td>"
									+ "<td>"
									+ content.userId
									+ "</td><td>"
									+ content.username
									+ "</td>"
									+ "<td>"
									+ content.email
									+ "</td><td>"
									+ content.mobile
									+ "</td>"
									+ "<td>"
									+ status
									+ "</td><td>"
									+ date
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

$(function() {
			queryList();
		})
function clickpage(event) {
	$("#page").val(event);
	queryList();
}
$(function() {
	$.get("../sys/role/select", function(r) {
		if (r.code === 0) {
			var flag = '';
			$.each(r.list, function(i) {
				flag += '<label class="checkbox-inline"><input name="role" type="checkbox" value="'
						+ r.list[i].roleId + '" />' + r.list[i].roleName;
				flag += '</label>';
			});
			$("#rolelist").append(flag);
		} else {
			layer.msg(r.msg);
		}
	});

	/**
	 * 全选
	 */
	$("#selectAll").click(function() {
				$('input[name="ids"]').prop("checked", this.checked);
				if (this.checked) {
					$(".tablelist tr:gt(0)").css("background", "#e5ebee");
				} else {
					$('input[name="ids"]')
					$(".tablelist tr:gt(0)").css("background", "");
				}
			});
});

function openAddUser() {
	// 清空
	/** 是否是修改 */
	$("#userId").val("");
	$("input[name='username']").val("");
	$("input[name='password']").val("");
	$("input[name='email']").val("");
	$("input[name='mobile']").val("");
	$("input[id='disable']").prop("checked", false);
	$("input[id='normal']").prop("checked", true);
	$("input[name='role']").prop("checked", false);
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

/**
 * 保存或更新操作
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function saveOrUpdate(event) {
	var userId = $("#userId").val() == "" ? null : $("#userId").val();
	var roleList = $("input[name='role']:checked");
	if (roleList.length < 1) {
		layer.msg("请为用户赋予至少一个权限")
		return false;
	}
	var url = userId == null ? "../sys/user/save" : "../sys/user/update";
	$.ajax({
				type : "POST",
				url : url,
				data : $("#userForm").serialize(),
				success : function(r) {
					if (r.code === 0) {
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
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../sys/user/delete",
							data : {
								userId : ids
							},
							success : function(r) {
								if (r.code == 0) {
									layer.alert('操作成功', function(index) {
												$("#page").val("1");
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
 * 
 * 更新
 * 
 * @return {Boolean}
 */
function openUpdate() {
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
	$("input[name='username']").val("");
	$("input[name='password']").val("");
	$("input[name='email']").val("");
	$("input[name='mobile']").val("");
	$("input[id='disable']").prop("checked", false);
	$("input[id='normal']").prop("checked", true);
	$("input[name='role']").prop("checked", false);
	$.get("../sys/user/info/" + ids, function(r) {
				/** 是否是修改 */
				$("#userId").val(r.user.userId);
				$("input[name='username']").val(r.user.username);
				$("input[name='email']").val(r.user.email);
				$("input[name='mobile']").val(r.user.mobile);
				if (r.user.status === 0) {
					$("input[id='disable']").prop("checked", true);
					$("input[id='normal']").prop("checked", false);
				} else if (r.user.status === 1) {
					$("input[id='disable']").prop("checked", false);
					$("input[id='normal']").prop("checked", true);
				}
				$.each(r.user.roleIdList, function(i) {
							$("input[name='role'][value='"
									+ r.user.roleIdList[i] + "']").prop(
									"checked", true);
						});
			});
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
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