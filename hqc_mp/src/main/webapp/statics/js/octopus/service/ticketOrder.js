function selectByLimit(){
	$("#page").val("1");
	queryList();	
}

function queryList(n, page1) {
	$("#list").html("");
	var page
	if (page1 == 0) {
		page = 1;
	} else {
		page = $("#page").val();
	}
	var limit = $("#limit").val();
	var orderNo = $("#orderNo").val();
	var ticketName = $("#ticketName").val();
	var orderPhone = $("#orderPhone").val();
	var status = $("#status").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	$.ajax({
		url : hqc.base + 'octopus/ticketOrder/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			limit : limit,
			checkpage : n,
			endTime:endTime,
			startTime:startTime,
			status:status,
			orderPhone:orderPhone,
			ticketName:ticketName,
			orderNo:orderNo
			},
			type : 'post',
			success : function(r) {
				if (r.code === 0) {
					$.each(r.page.list, function(index, content) {
						var orderNo = "<td>"+content.orderNo+"</td>";
						if(content.mpMemberEntity != null){
							var trueName = "<td>"+content.mpMemberEntity.trueName+"</td>";
						} else {
							var trueName = "<td><span class='label label-danger'>无用户</span></td>";
						}
						
						
						if(content.mpTicketEntity != null){
							var ticketName = "<td>"+content.mpTicketEntity.ticketName+"</td>";
						} else {
							var ticketName = "<td><span class='label label-danger'>门票被删除</span></td>";
						}
						
						var ticketCount = "<td>"+content.ticketCount+"</td>";
						var totalFee = "<td>￥"+content.totalFee+"&nbsp;元</td>";
						var startTime = formatDate(new Date(content.startTime*1000));
						var endTime = formatDate(new Date(content.endTime*1000));
						var activTime = "<td>"+startTime+"&nbsp;&nbsp;--&nbsp;&nbsp;"+endTime+"</td>";
						var orderPhone = "<td>"+content.orderPhone+"</td>";
						var createTime = "<td>"+formatDate(new Date(content.createTime*1000))+"</td>";
						
						var status;
						var checkboxdis = "";
						var trch = "";
						if(content.status === 1){
							trch = "<tr><td>";
							checkboxdis = "<input  type='checkbox' value=' ' disabled/>";
							status	 = '<td><span class="label label-success">已支付</span></td>';
						   
						}else if(content.status === 2){
							trch = "<tr name='tru'><td>";
							checkboxdis = "<input name='ids' type='checkbox' value='"
									+ content.id + "'/>";
								 status	 = '<td><span class="label label-warning">支付失败</span></td>';
						   
						}else if(content.status === 3){
							trch = "<tr><td>";
							checkboxdis = "<input  type='checkbox' value=' ' disabled/>";
							 status	 = '<td><span class="label label-primary">待支付</span></td>';
						   
						}else{
							trch = "<tr name='tru'><td>";
							checkboxdis = "<input name='ids' type='checkbox' value='"
									+ content.id + "'/>";
							status	 = '<td><span class="label label-danger">已关闭</span></td>';
						   
						}
					//	var query = "<a class='label label-success'  onclick='queryView("+content.id+")' >&nbsp;详情</a>";
						$("#list").append(trch+checkboxdis+ "</td>"+orderNo+trueName+ticketName
																+ticketCount+totalFee+activTime+orderPhone+createTime
																+status+"</tr>");
					});
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
							if (null == ids) {
								layer.msg("该条数据不能操作");
								return;
							}
							ids.checked = true;
							$(e.currentTarget).css("background", "#e5ebee");
						}
					});
				}else{
					layer.msg(r.msg);
				}
			},
			error : function() {
				layer.alert("系统异常")
			}
	})
}

/**
 * 重新查询
 */
function reload() {
	queryList();	
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
							url : hqc.base + "octopus/ticketOrder/del",
							data : {
								ids : ids
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
									reload();
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


$(function() {
	queryList();
	
	/**
	 * 全选
	 */
	$("#selectAll").click(function() {
		$('input[name="ids"]').prop("checked", this.checked);
		if (this.checked) {
			$(".tablelist tr[name='tru'] ")
					.css("background", "#e5ebee");
		} else {
			$('input[name="ids"]')
			$(".tablelist tr:gt(0)").css("background", "");
		}
	});
});

/**
 * 重置查询输入框
 */
function resetQuery() {
	$("#orderNo").val("");
	$("#ticketName").val("");
	$("#orderPhone").val("");
	$("#status").val(0);
	$("#startTime").val("");
	$("#endTime").val("");
}

//翻页
function clickpage(event) {
	$("#page").val(event)
	queryList();
}

//获取选中的多选框的值
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
	return year + "-" + month + "-" + date + "&nbsp;" + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}