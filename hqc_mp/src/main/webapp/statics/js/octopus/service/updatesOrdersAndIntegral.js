
function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}


function queryList(n){
	$("#list").html("");
	var page = $("#page").val();
	var itemCode = $("#itemCode").val();
	var limit = $("#limit").val();
	if(itemCode==null||itemCode==''){
		layer.msg("请输入兑换码!");
		return false
	}
	$.ajax({
		url : hqc.base+'octopus/orderUpdate/updatesOrdersAndIntegral/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			itemCode : itemCode,
			limit:limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var createDate = formatDate(new Date(content.create_time* 1000)) ;
					var useTime = formatDate(new Date(content.use_time* 1000)) ;
					var nowTime = formatDate(new Date()) ;
					var statusType = "";
					var auditType = "";
					var idsType="";
					if (content.status == 0) {
						statusType = '<span class="label label-primary">未使用</span>';
						//判断是否已超过有效期
						if(parseInt(nowTime)>parseInt(useTime) ){
							statusType = "<span class='label label-danger'>已过有效期</span>";
						}else{
						   //判断订单类型 并根据订单类型修改订单状态
						  if(content.type==3||content.type==2){
					
							auditType ="<a class='label label-success'  onclick='updateAudit("+content.id+","+content.order_no+","+content.type+",1)' >兑换</a>";
						  }else if(content.type==1){
							auditType ="<a class='label label-success'  onclick='updateAudit("+content.id+","+content.order_no+","+content.type+",2)' >取票</a>";
						  }
						}
						idsType="<input name='ids' type='checkbox' value='"+ content.id+ "'/>";
					
					}

					if (content.status == 1) {
						statusType = '<span class="label label-warning">已兑换</span>';
						idsType="<input name='ids' type='checkbox' value='"+ content.id+ "'/>";
					}
					if (content.status == 2) {
						statusType = '<span class="label label-warning">已取票</span>';
						idsType="<input name='ids' type='checkbox' value='"+ content.id+ "'/>";
					}
                   
				    
					$("#list")
							.append("<tr><td style='display:none'>"+idsType+"</td>"
									+ "<td>"
									+ content.id		
									+ "</td><td>"
									+ (content.goods_name == null
											? ''
											: content.goods_name)
									+ "</td><td>"
									+ (content.item_code == null ? '' : content.item_code)
									+ "</td><td>"
									+ (content.phone == null ? '' : content.phone)
									+ "</td>"
									+ "<td>"
									+ (content.order_no == null? '': content.order_no)
									+ "</td><td>"
									+ statusType
									+ "</td><td>"
									+ (createDate == null ? '' : createDate.substring(0, 10))
									+ "</td><td>"
									+ (content.memberPhone == null? '': content.memberPhone)
									+ "</td><td>"
									+(content.true_name == null? '': content.true_name)
									+ "</td><td>"
									+(useTime == null ? '' : useTime.substring(0, 10))
									+ "</td><td>"
                           
									+ auditType
								    +"</td></tr>")
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
			//queryList();
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
// 重置
function resetQuery() {
	document.getElementById("itemCode").value = "";
    queryList();
}
//修改状态
function updateAudit(id,orderNo,type,status){

	var url = hqc.base+'octopus/orderUpdate/updatesOrdersAndIntegral/update';
	
	$.ajax({
				cache : true,
				type : "POST",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				dataType : "json",
				async : false,
				url : url,
				data : {id:id,orderNo:orderNo,type:type,status:status},
				success : function(r) {
					if (r.code === 0) {
						layer.alert('操作成功', function(index) {
									layer.close(index);
									 queryList();
								});
					} else {
						layer.msg(r.msg);
					}
				},
				error : function() {
					layer.alert("系统异常", function() {
						 queryList();
							});
				}
			});

}