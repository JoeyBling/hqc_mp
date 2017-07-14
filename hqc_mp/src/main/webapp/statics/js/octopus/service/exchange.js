function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var trueName1 = $("#trueName").val();
	var goodsName1 = $("#goodsName").val();
	var useStatus = $("#useStatus").val();
	var personPhone = $("#personPhone").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../goods/exchange/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			trueName : trueName1,
			goodsName : goodsName1,
			useStatus : useStatus,
			personPhone:personPhone,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					// var date = formatDate(new Date(content.createTime));
					var statusType = ""
					var createDate = formatDate(new Date(content.create_time
							* 1000))
					var useDate = formatDate(new Date(content.use_time * 1000))
					if (content.use_status === 1) {
						statusType = '<span class="label label-primary">已使用</span>';
					}

					if (content.use_status === 0) {
						statusType = '<span class="label label-warning">未使用</span>';
					}
					var goodsType=""
					if(content.goods_type == 2){
						goodsType= '<span>普通商品</span>'
					}
				
					if(content.goods_type == 1){
						goodsType= '<span>代金卷</span>'
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td>"
									+ "<td>"
									+ (content.true_name == null
											? ''
											: content.true_name)
									+ "</td><td>"
									+goodsType
									+"</td><td>"
									+ (content.goods_name == null
											? ''
											: content.goods_name)
									+ "</td><td>"
									+ (createDate == null ? '' : createDate.substring(0, 10))
									+ "</td><td>"
									+ (content.exchange_code == null
											? ''
											: content.exchange_code)
									+ "</td>"
									+ "<td>"
									+ (content.exchange_count == null
											? ''
											: content.exchange_count)
									+ "</td><td>"
									+ (content.integral == null
											? ''
											: content.integral)
									+ "</td><td>"
									+ (content.person_phone == null
											? ''
											: content.person_phone)
									+ "</td><td>"
									+ statusType
									+ "</td><td>"
									+ (useDate == null ? '' : useDate.substring(0, 10)) + "</td><td>" 
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
	document.getElementById("trueName").value = "";
	document.getElementById("goodsName").value = "";
	document.getElementById("useStatus").value = "";

	queryList();
}

function delGoodsExchange() {
	var ids = checkData();
	if (ids.length == 0 || ids.length == null || ids.length == "") {
		layer.msg("请至少选择一条记录");
		return false;
	}

	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../goods/exchange/delete",
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
							},
							dataType : "json",
							async : false,
							data : {
								ids : ids
							},
							success : function(r) {
								if (r.code === 0) {
									layer.alert('操作成功', function(index) {
										$("#page").val("1");
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
			});
}
