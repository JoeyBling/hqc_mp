function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../goods/category/list',
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
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td>"
									+ "<td>"
									+ content.id
									+ "</td><td>"
									+ (content.categoryName == null
											? ''
											: content.categoryName)
									+ "</td><td>"
									+ (content.about == null
											? ''
											: content.about) + "</td></tr>")
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

function openAdd() {
	$("input[name='id']").val("")
	$("input[name='categoryName']").val("");
	$("input[name='about']").val("");
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")

}

function saveOrUpdate() {
	var url = "";
	var id = $("input[name='id']").val();
	if (id === "") {
		url = "../goods/category/save";
	} else {
		url = "../goods/category/update";
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
				data : $('#categoryForm').serialize(),
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
					layer.alert(r.msg, function() {
								location.reload();
							});
				}
			});
	ids = "";
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
	var id = ids;
	$.get("../goods/category/info/" + id, function(r) {
				$("input[name='id']").val(r.entity.id);
				$("input[name='categoryName']").val(r.entity.categoryName)
				$("input[name='about']").val(r.entity.about)

			});

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

function deleteCategory() {
	var ids = checkData();
	if (ids.length == 0 || ids.length == null || ids.length == "") {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('删除选中的商品类别同时会将该类中的商品删除，确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../goods/category/delete",
							data : {
								ids : ids
							},
							success : function(r) {
								if (r.code === 0) {
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
								layer.alert("操作失败", function() {
											reload();
										});
							}
						});
			});
}