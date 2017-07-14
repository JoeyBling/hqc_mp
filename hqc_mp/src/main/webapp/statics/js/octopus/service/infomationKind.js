function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	var status = "";
	$.ajax({
		url : '../sys/infomationKind/list',
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
				var categoryType = "";
				var isSystem = "";
				var  parentName = "";
				var lianjie = "";
				$.each(r.page.list, function(index, content) {
					if (content.categoryType == 1) {
						categoryType = '栏目页';
					}
					if (content.categoryType == 2) {
						categoryType = '单页';
					}
					if(content.parentId === 0 ){
						parentName = "无";
					}else{
						parentName =content.parentName ;
						if(parentName == null){
							parentName = "<span class='label label-danger'>已删除</span>";
						}
					}
					if (content.isSystem == 0) {
						isSystem = "<span class='label label-success'>非内置项</span>";
						lianjie = "<input name='ids' type='checkbox' value='"
								+ content.id + "'/>"
					}
					if (content.isSystem == 1) {
						isSystem = "<span class='label label-danger'>内置项</span>";
						lianjie = "<input name='ids' type='checkbox' value='"
								+ content.id + "'/>"
					}
					$("#list").append("<tr><td>" + lianjie + "</td>" + "<td>"
							+ content.categoryName + "</td><td>"
							+ (categoryType == null ? '' : categoryType)
							+ "</td><td>" +parentName+"</td><td>" + (isSystem == null ? '' : isSystem)
							+ "</td></tr>")
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

$(function() {
			queryList();
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

function deleteIMK() {
	var ids = checkData();
	if (ids.length == 0 || ids.length == null || ids.length == "") {
		layer.msg("请选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../sys/infomationKind/delete",
							data : {
								infoId : ids
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
	$(".forminfo :input").val("");
	$.get("../sys/infomationKind/info/" + ids, function(r) {
				$("#id").val(r.entity.id);
				//判断等级
				if(r.entity.parentId === 0 ){
					$("#mulu").prop("checked", "checked");
					$("#parentId_this").val(0);
					$("#hidden").hide();
				}else{
					jiazai(r.entity.parentId);
					$("#menu").prop("checked", "checked");
					$("#parentId_this").val(r.entity.parentId);
					$("#hidden").show();
				}
				//判断文章类别种类
				if(r.entity.categoryType === 1 ){
					$("#categoryType1").prop("checked", "checked");
				}else{
					$("#categoryType2").prop("checked", "checked");
				}
				$("input[name='categoryName']").val(r.entity.categoryName);
			});
	$("#rightbody").css("display", 'none');
	$("#add").css("display", 'block');
}

/**
 * 保存或更新操作
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function saveOrUpdate(event) {
	var articleId = $("#id").val() == "" ? null : $("#id").val();
	var url = articleId == null
			? "../sys/infomationKind/save"
			: "../sys/infomationKind/update";
	if (fc == 0) {
		$("#parentId").val("0");
	}
	$.ajax({
				type : "POST",
				url : url,
				data : $('#autoReply').serialize(),
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

function jiazai(parentId) {
	$("#parentId").html("");
	var viewId = $("#id").val();
	if(null == viewId){
		viewId = 0;
	}
	$.get("../sys/infomationKind/xiala", function(i) {
				$.each(i, function(index, content) {
							$.each(content, function(p, k) {
								if(viewId != k.id){
									if(k.id === parentId){
										$("#parentId").append("<option value="
												+ k.id + " selected>" + k.categoryName
												+ "</option>")
									}else{
										$("#parentId").append("<option value="
												+ k.id + ">" + k.categoryName
												+ "</option>")
									}
								}
								
									});
						});
			})
}
 
function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

function openAdd() {
	jiazai();
	$("#id").val(null);
	$("#categoryName").val("");
	$("#hidden").hide();
	$("#mulu").prop("checked", "checked");
	$("#categoryType1").prop("checked", "checked");
	$("#rightbody").css("display", 'none');
	$("#add").css("display", 'block');
}
var fc = 0
function checkRadio(event) {
	if (event == "catalog") {
		$("#mulu").prop("checked", "checked");
		$("#parentId").val("0");
		$("#hidden").hide();
		fc = 0;
	}
	if (event == "menu") {
		$("#menu").prop("checked", "checked")
		$("#hidden").show();
		jiazai();
		fc = 1;
	}
}
