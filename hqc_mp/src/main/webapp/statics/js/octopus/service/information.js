function selectByLimit() {
	$("#page").val("1");
	queryList();

}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var author = $("#author").val();
	var title = $("#title").val();
	var categoryName = $("#categoryName").val();
	var limit = $("#limit").val();
	var status = "";
	$.ajax({
		url : '../sys/infomation/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			author : author,
			title : title,
			categoryName : categoryName,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var date = formatDate(new Date(content.createTime * 1000));
					if (content.status == 0) {
						status = "<span class='label label-warning'>未审核</span>"
					}
					if (content.status == 1) {
						status = "<span class='label label-success'>正常</span>"
					}
					if (content.status == 2) {
						status = "<span class='label label-danger'>审核不通过</span>"
					}
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
											+ content.id
											+ "'/></td>"
											+ "<td>"
											+ content.author
											+ "</td><td>"
											+ (content.title == null
													? ''
													: content.title)
											+ "</td><td>"
											+ (content.likeCount == null
													? ''
													: content.likeCount)
											+ "</td><td>"
											+ (content.readCount == null
													? ''
													: content.readCount)
											+ "</td>"
											+ "<td>"
											+ (content.comeFrom == null
													? ''
													: content.comeFrom)
											+ "</td><td>"
											+ status
											+ "</td><td>"
											+ date
											+ "</td><td>"
											+ (content.mpArticleCategoryEntity.categoryName == null
													? ''
													: content.mpArticleCategoryEntity.categoryName)
											+ "</td></tr>")
				})
				$("#page").val(r.page.currPage)
				var lastpage = "";
				var nextpage = "";
				$(".paginList").html("")
				if (r.page.currPage != 1) {
					$(".paginList")
							.html(	"<li class='paginItem' ><a href='javascript:void(0);' onclick='queryList(1)'><span class='pagepre'></span></a></li>");

				}
				$(".total").html(r.page.totalCount)
				$(".page")
						.html(	"<input type='text' readonly='readonly' style='width:20px' value='"
										+ r.page.currPage + "'/>")
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
						.html(	"<div class='page_curr'>"
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

		});

/**
 * 保存或更新操作
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function saveOrUpdate(event) {
	var categoryId = $("#categoryId").val();
	if (categoryId == null || categoryId == "") {
		layer.msg("请先添加图文信息类别");
		return false;
	}
	var title = $("#titled").val();
	if (title == null || title == "") {
		layer.msg("标题不能为空");
		return false;
	}
	var author = $("#authord").val();
	if (author == null || author == "") {
		layer.msg("作者名称不能为空");
		return false;
	}
	var comeFrom = $("#comeFrom").val();
	var thumb = $("#thumbUrl").val();
	var content = $('#content').summernote('code');
	var articleId = $("#id").val() == "" ? null : $("#id").val();
	var categoryId = $("#categoryId").val()
	var url = articleId == null
			? "../sys/infomation/save"
			: "../sys/infomation/update";
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
					title : title,
					author : author,
					comeFrom : comeFrom,
					content : content,
					id : articleId,
					thumb : thumb,
					categoryId : categoryId
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
	$("input[name='id']").val("");
	$("input[name='titled']").val("");
	$("input[name='authord']").val("");
	$("input[name='comeFrom']").val("");
	$("#thumbUrl").val("");
	$("#thumbf").children().remove();
	$("#pic").val("");
	$('#content').summernote('code', "")
	$("#fileName").html("");
	$("input[name='thumbUrl']").val("");
	$("[name='categoryId']").children('option').remove();
	$.get("../sys/infomation/info/" + ids, function(r) {
				console.log(r.entity);
				/** 是否是修改 */
				var htmlImage = null;
				$("input[name='id']").val(r.entity.id);
				$("input[name='titled']").val(r.entity.title);
				$("input[name='authord']").val(r.entity.author);
				$("input[name='comeFrom']").val(r.entity.comeFrom);
				if (r.entity.thumb != null && r.entity.thumb != "") {
					htmlImage = "<img width='250px' height='250px' id='lend' src='"
							+ hqc.base
							+ r.entity.thumb
							+ " '  alt='图片不能显示！' /> ";
				}
				$("input[name='thumbUrl']").val(r.entity.thumb);
				$("#thumbf").html(htmlImage);
				$('#content').summernote('code',
						r.entity.content == null ? "" : r.entity.content)
				$("[name='categoryId']")
						.append("<option selected=selected value='"
										+ r.entity.categoryId
										+ "'>"
										+ r.entity.mpArticleCategoryEntity.categoryName
										+ " </option>");
				getCategory();
			});
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block");

}

function openAdd() {
	$("input[name='id']").val("");
	$("input[name='titled']").val("");
	$("input[name='authord']").val("");
	$("input[name='comeFrom']").val("");
	$("#thumbUrl").val("");
	$("#pic").val("");
	$("#fileName").html("");
	$("#lend").remove();
	$("select[name='categoryId']").children('option').remove();
	getCategory();
	$('#content').summernote('code', "");
	$("[name=like_count]").attr("readonly", "readonly");
	$("#rightbody").css("display", 'none');
	$("#add").css("display", 'block');
}

function Showhide() {
	$("#rightbody").css("display", 'block');
	$("#add").css("display", 'none');
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

function clickpage(event) {
	$("#page").val(event)
	queryList();
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

function del() {
	var ids = checkData();
	if (ids.length == 0 || ids.length == null || ids.length == "") {
		layer.msg("请选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../sys/infomation/delete",
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
							},
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
											reload();
										});
							}
						});
			});
}

function getall(obj) {

	var ofrm1 = document.getElementById("iframesum").document;
	if (ofrm1 == undefined) {
		ofrm1 = document.getElementById("iframesum").contentWindow.document;
		var ff = ofrm1.getElementById("editor").text;
	}

}

// 提取文件名
function getFileName(o) {
	var pos = o.lastIndexOf("\\");
	return o.substring(pos + 1);
}
// 文件上传
function upload(event) {
	var name = event.name;
	var uploadType = 3; // 信息资讯缩略图

	if (event.files == null || event.files.length < 1) {
		return false; // 未选择文件
	}
	// 验证上传文件格式；
	var FileName = new String(event.value);// 文件名
	var extension = new String(FileName.substring(
			FileName.lastIndexOf(".") + 1, FileName.length));// 文件扩展名
	// 文件格式
	if (extension == "jpg" || extension == "gif" || extension == "jpeg"
			|| extension == "bmp" || extension == "png") {

	} else {
		$("#pic").val("");
		layer.msg('缩略图图片格式不支持！');
		return false;
	}
	$.ajaxFileUpload({
		url : hqc.base + 'file/upload?uploadType=' + uploadType + '&name='
				+ name, // 用于文件上传的服务器端请求地址
		secureuri : false, // 一般设置为false
		fileElementId : event.id,
		type : 'post',
		dataType : 'jsonp', // 返回值类型 一般设置为json
		success : function(data, status) // 服务器成功响应处理函数
		{
			var dataCopy = data;
			try {
				var reg = /<pre.+?>(.+)<\/pre>/g;
				data = data.match(reg);
				data = RegExp.$1; // 解决上传文件 返回值带 <pre
				data = (new Function("return " + data))();
			} catch (err) {
				data = dataCopy;
				data = (new Function("return " + data))();
				// 在这里处理错误
			}
			if (data.code == 0) {
				layer.alert("上传成功");
				if (name == "pic") {
					$("input[name='thumbUrl']").val(data.filePath);
					var htmlImage = "<img width='250px' height='250px' id='lend' src='"
							+ hqc.base
							+ data.filePath
							+ " '  alt='图片不能显示！' /> ";
					$("#thumbf").html(htmlImage);
				}
			} else {
				layer.alert(data.msg);
			}
		},
		error : function(data, status, e) {// 服务器响应失败处理函数
			alert("系统出错,请联系管理员");
		}
	});
}

function downFile() {
	var name = $("#thumb").val();
	name = encodeURI(name);
	name = encodeURI(name);
	if (null != name && "null" != name) {
		location.href = hqc.base + "file/download?name=" + name;
	}
}

function getCategory() {
	$.get("../sys/infomation/getAllcategory", function(r) {
				for (var i = 0; i < r.categoryList.length; i++) {
					$("[name='categoryId']").append("<option value='"
									+ r.categoryList[i].id + "'>"
									+ r.categoryList[i].categoryName
									+ " </option>")
				}
			});
}
