function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}

function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../mp/autoreply/list',
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
					var date = formatDate(new Date(content.createTime * 1000));
					var responseType = "";
					if (content.responseType === 1) {
						responseType = "<span class='label label-success'>关注公众号</span>";
					} else if (content.responseType === 2) {
						responseType = "<span class='label label-success'>关键词回复</span>";
					}
					var replyType = "";
					if (content.replyType === 1) {
						replyType = "<span class='label label-success'>文字</span>";
					} else if (content.replyType === 2) {
						replyType = "<span class='label label-success'>图片</span>";
					} else if (content.replyType === 3) {
						replyType = "<span class='label label-success'>语音</span>";
					} else if (content.replyType === 4) {
						replyType = "<span class='label label-success'>视频</span>";
					} else if (content.replyType === 5) {
						replyType = "<span class='label label-success'>图文</span>";
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td><td>"
									+ responseType
									+ "</td><td>"
									+ replyType
									+ "</td><td>"
									+ (content.replyText == null
											? ""
											: content.replyText.length > 60
													? content.replyText
															.substring(0, 60)
															+ "......"
													: content.replyText)
									+ "</td><td>"
									+ (content.mediaId == null
											? ""
											: content.mediaId)
									+ "</td><td>"
									+ (content.keywords == null
											? ""
											: content.keywords)
									+ "</td><td>"
									+ date + "</td></tr>")
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

function clickpage(event) {
	$("#page").val(event);
	queryList();
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
	var replyId = $("#id").val() == "" ? null : $("#id").val();
	var url = replyId == null
			? "../mp/autoreply/save"
			: "../mp/autoreply/update";
	$.ajax({
				type : "POST",
				url : url,
				data : $("#autoReply").serialize(),
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
							url : "../mp/autoreply/delete",
							data : {
								replyId : ids
							},
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
			});
}

function add() {
	$("#mediaId").hide();
	$("#replyText").show();
	$("#keywords").show();

	// 清空
	/** 是否是修改 */
	$("#id").val("");
	$("input[name='keywords']").val("");
	$("#replyTextForm").val("");
	$("input[name='mediaId']").val("");

	$("input[id='subscribe']").prop("checked", false);
	$("input[id='tagsReply']").prop("checked", true);

	$("input[id='text']").prop("checked", true);
	$("input[id='image']").prop("checked", false);
	$("input[id='voice']").prop("checked", false);
	$("input[id='movie']").prop("checked", false);
	$("input[id='graphic']").prop("checked", false);
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

/**
 * 
 * 更新
 * 
 * @return {Boolean}
 */
function update() {
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
	$("#id").val("");
	$("input[name='keywords']").val("");
	$("#replyTextForm").val("");
	$("input[name='mediaId']").val("");
	$("input[id='subscribe']").prop("checked", false);
	$("input[id='tagsReply']").prop("checked", false);

	$("input[id='text']").prop("checked", false);
	$("input[id='image']").prop("checked", false);
	$("input[id='voice']").prop("checked", false);
	$("input[id='movie']").prop("checked", false);
	$("input[id='graphic']").prop("checked", false);
	$.get("../mp/autoreply/info/" + ids, function(r) {
				if (r.code === 0) {
					/** 是否是修改 */
					$("#id").val(r.entity.id);
					$("input[name='keywords']").val(r.entity.keywords);
					$("#replyTextForm").val(r.entity.replyText);
					$("input[name='mediaId']").val(r.entity.mediaId);
					if (r.entity.responseType === 1) { // 响应类型(1关注公众号 2关键词回复)
						$("input[id='subscribe']").prop("checked", true);
						$("#keywords").hide();
					} else if (r.entity.responseType === 2) {
						$("input[id='tagsReply']").prop("checked", true);
						$("#keywords").show();
					}
					if (r.entity.replyType === 1) { // 回复类型(1文字 2图片 3语音 4视频 5图文)
						$("input[id='text']").prop("checked", true);
						$("#mediaId").hide();
						$("#replyText").show();
					} else if (r.entity.replyType === 2) {
						$("input[id='image']").prop("checked", true);
						$("#mediaId").show();
						$("#replyText").hide();
					} else if (r.entity.replyType === 3) {
						$("input[id='voice']").prop("checked", true);
						$("#mediaId").show();
						$("#replyText").hide();
					} else if (r.entity.replyType === 4) {
						$("input[id='movie']").prop("checked", true);
						$("#mediaId").show();
						$("#replyText").hide();
					} else if (r.entity.replyType === 5) {
						$("input[id='graphic']").prop("checked", true);
						$("#mediaId").show();
						$("#replyText").hide();
					}
				} else {

					layer.msg(r.msg);
				}
			});
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
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