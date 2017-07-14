function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n,page1) {
	$("#list").html("");
	var page
	if (page1 == 0) {
		page = 1;
	} else {
		page = $("#page").val();
	}
	var integralType = $("#integralStauts").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../sys/MpIntegralRecord/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			integralType : integralType,
			page : page,
			checkpage : n,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
				var date =formatDate(new Date(content.createTime*1000));
					var integralType="";
					if(content.integralType===1){
						integralType= '<span class="label label-primary">进账</span>';
					}else{
						integralType= '<span class="label label-warning">出账</span>';
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td>"
									+ "<td>"
									+ content.id
									+ "</td><td>"
									+ (content.memberEntity.trueName == null
											? ''
											: content.memberEntity.trueName)
									+ "</td>"
									+ "<td>"
									+ (content.integral == null ? '' : content.integral)
									+ "</td><td>"
									+ integralType
									+ "</td><td>"
									+ (content.about == null ? '' : content.about)
									+ "</td><td>"
									+ date
									+ "</td>"
									+ "<td>"
									+ "</tr>")
									
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
				
			} else {
				layer.msg(r.msg);
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
function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
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
//获取选中的多选框的值
function checkData() {
	var id = "";
	$("#list td").each(function() {
		if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
			if (id.length == 0) {
				id = $(this).find("input[type=checkbox]:checked").val()
			} else {
				id = id + ","
						+ $(this).find("input[type=checkbox]:checked").val();
			}
		}
	})
	return id;
}

function addRecord(){
	var integral=$("input[name='integral']").val();
	/*var about=document.getElementById("about").value;
	alert(about);
	return fasle;*/
	if (integral == null || integral == "") {
		layer.msg("积分异动数量不能为空");
		return false;
		}
	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		url :'../sys/MpIntegralRecord/save',  
		data : $('#addForm').serialize(),
		dataType : "json",
		async : false,
		success : function(r) {
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
}

function openAddLevel() {
	$("input[name='integral']").val("");
	$("textarea[name='about']").val("");
	$("#rightbody").css("display","none")
	$("#add").css("display","block")
}

function dumpRecord(){
	$("#rightbody").css("display","block")
	$("#update").css("display","none")
}

function integralRecord(){
	queryList();
	$("#rightbody").css("display","block")
	$("#add").css("display","none")
}

function openUpdateLevel() {
	var id=checkData();
	if (id.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (id.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$.get("../sys/MpIntegralRecord/info/" + id, function(r) {
		$("input[name='integral']").val(r.mpIntegralRecordEntity.integral);
		$("input[name='id']").val(r.mpIntegralRecordEntity.id);
		$("textarea[name='about']").val(r.mpIntegralRecordEntity.about);
		$("input[name='integralType']").val(r.mpIntegralRecordEntity.integralType);
	});	
	$("#rightbody").css("display","none")
	$("#update").css("display","block")
}

function updateRecord(){
	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		url : '../sys/MpIntegralRecord/update',  
		data :$('#updateForm').serialize(),
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.code == 0) {
				layer.alert('修改成功', function(index) {
					layer.close(index);
					reload();
				});
			} else {
				layer.alert(r.msg);
				reload();
			}
		},
		error : function() {
			layer.alert('系统出现异常!', function() {
						reload();
					});
		}
	});
}
function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
	$("#update").css("display","none")
}
function deleteLevel(){
	var id = checkData();
	if (id.length == 0) {
		layer.msg("请选择一条记录");
		return;
	}
		layer.confirm('确定要删除选中的记录？', function() {
			 $.ajax({
					cache : true,
					type : "POST",
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					},
					url : "../sys/MpIntegralRecord/delete",
					dataType : "json",
					data : {id:id},
					async : false,
					success : function(result) {
						if (result.code == 0) {
							layer.alert('删除成功', function(index) {
								$("#page").val("1");
								layer.close(index);
								reload();
							});
						} else {
							layer.alert(r.msg);
							reload();
						}
					},
					error : function() {
						layer.alert('系统出现异常!', function() {
									location.reload();
								});
					}
				});
		 })
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