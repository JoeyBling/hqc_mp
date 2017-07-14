function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	var categoryName =$("#categoryName").val();
	$.ajax({
		url : "../category/list",
		datatype : "json",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			limit : limit,
			categoryName : categoryName
		},
		type : 'post',
		success : function(r) {
			$.each(r.page.list, function(index, content) {
						var isSystem = "";
						var parentName = "";
						if (content.parentId === 0) {
							parentName = "无";
						} else {
							parentName = content.parentName == null
									? "<span class='label label-danger'>已删除</span>"
									: content.parentName;
						}
						if (content.isSystem == 1) {
							isSystem = "<span class='label label-danger'>是</span>";
						}
						if (content.isSystem == 0) {
							isSystem = "<span class='label label-success'>否</span>";
						}
						$("#list")
								.append("<tr>"
										+ "<td><input name='ids' type='checkbox' value='"
										+ content.id
										+ "'/></td>"
										+ "<td>"
										+ (content.categoryName == null
												? ''
												: content.categoryName)
										+ "</td>" + "<td>" + isSystem + "</td>"
										+ "<td>" + parentName + "</td>"
										+ "</tr>")
					})
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
			if (r.page.currPage < r.page.totalPage && r.page.totalPage !== 0) {
				$(".paginList")
						.append("<li style='margin-left:0.5px' class='paginItem'><a href='javascript:void(0);' onclick='queryList(2)'><span class='pagenxt'></span></a></li>");
			}
			$(".tablelist tr:gt(0)").on("click", function(e) {
						var ids = $(e.currentTarget)
								.find("td input[name='ids']")[0];
						if (null != ids && ids.checked) {
							ids.checked = false;
							$(e.currentTarget).css("background", "");
						} else {
							ids.checked = true;
							$(e.currentTarget).css("background", "#e5ebee");
						}
					});
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
	$("#page").val(event)
	queryList();
}
function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}
$(function() {
			// 全选
			$("#checkedAll").click(function() {
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
// 获取选中的多选框的值
function checkValue() {
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
/**
 * 删除
 * 
 * @returns {Boolean}
 */
function del() {
	var ids = checkValue();	
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../category/delete",
							data : {
								id : ids
							},
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
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
 * 删除前判断是否有子项
 * @returns {Boolean}
 */
function queryNext(){
	var ids = checkValue();	
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	$.ajax({
		type: "POST",
	    url: "../category/infoCategory",
	    dataType: "json",
	    headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
	    data : {ids : ids},
	    success: function(r){					
				if(r.code != 0){
					layer.alert(r.msg);					
				}else{	
					del();
				}	    	
		},
		error : function() {
			//layer.msg("");
		}
	});
}
function openAdd() {
	// 清空
	$("#id").val("");
	$("input[name='categoryName']").val("");
	$("select[name='categoryId']").html("");
	$("select[name='categoryId']").append('<option  value="0">无</option>');
	$("input[name='isSystem'][value=0]").prop("checked", true);
	getproCategory();
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}
////验证名称是否相同
function verify(){
	var categoryNames = $("#categoryNames").val();
		$.ajax({
			type: "POST",
		    url: "../category/infoName",
		    dataType: "json",
		    headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
		    data : {categoryName : categoryNames},
		    success: function(r){					
					if(r.code != 0){
						layer.alert(r.msg);						
					}else{

					}	    	
			},
			error : function() {
				//layer.msg("");
			}
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
	var id = $("#id").val() == "" ? null : $("#id").val();
	var categoryName = $("#categoryNames").val();
	if(categoryName == null || categoryName == ""){
		layer.msg("请填写项目类型");
		return false;
	}
	var url = id == null ? "../category/save" : "../category/update";
	$.ajax({
				type : "POST",
				url : url,
				data : $("#categoryForm").serialize(),
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
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
 * 修改
 * 
 * @returns {Boolean}
 */
function update() {
	var ids = checkValue();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");

		return false;
	}
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	var id = ids;
	$.get("../category/info/" + id, function(r) {
				$("input[name='id']").val(r.entity.id);
				$("input[name='categoryName']").val(r.entity.categoryName);
				$("select[name='categoryId']").html("");
				if (r.entity.parentId == 0) {
					$("select[name='categoryId']")
							.append("<option value='0'>无</option>");
				} else {
					$("select[name='categoryId']")
							.append("<option value='0'>无</option>");
				}
				getproCategory(r.entity.parentId);

				if (r.entity.isSystem === 1) {
					$("#normal").prop("checked", "checked");
				} else {
					$("#disable").prop("checked", "checked");
				}
			});
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
}
/**
 * 查询父类项目
 */
function getproCategory(id) {
	$.get("../category/getproCategory", function(r) {
				for (var i = 0; i < r.proCategoryList.length; i++) {
					if (id === r.proCategoryList[i].id) {
						$("select[name='categoryId']").append("<option value='"
								+ r.proCategoryList[i].id + "' selected>"
								+ r.proCategoryList[i].categoryName
								+ " </option>")
					} else {
						$("select[name='categoryId']").append("<option value='"
								+ r.proCategoryList[i].id + "'>"
								+ r.proCategoryList[i].categoryName
								+ " </option>");
					}
				}
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