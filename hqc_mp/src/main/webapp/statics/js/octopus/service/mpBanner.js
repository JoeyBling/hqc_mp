function selectByLimit(){
	$("#page").val("1");
	queryList();
}

//重置查询条件
function resetQuery() {
	$("#qtitle").val("");
	$("#qstatus").val(0);
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
	var title = $("#qtitle").val();
	var status = $("#qstatus").val();
	$.ajax({
		url : hqc.base + 'octopus/banner/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			limit : limit,
			checkpage : n,
			title:title,
			status:status
			
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var title ="<td>"+ content.title+"</td>";
					var url ="<td><a href='"+content.url+"' target='_blank'>"+ content.url+"</a></td>";
					var status;
					var trch= "<tr name='tru'>";
					var	checkboxdis = "<td><input name='ids' type='checkbox' value='"
						+ content.id + "'/></td>";
					if(content.status == 1){
						status = "<td><font color='green'>启用</font></td>";
						 var statusImge="<td><a class='label label-danger'  onclick='auditStatus("+content.id+",2)' >&nbsp;禁用</a></td>";
					} else {
						status = "<td><font color = 'red'>禁用</font></td>";
						 var statusImge="<td><a class='label label-success'  onclick='auditStatus("+content.id+",1)' >&nbsp;启用</a></td>";
					}
					$("#list").append(trch+checkboxdis+title+url+status+statusImge+"</tr>");
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
								+ r.page.currPage + "'/>")
				$(".pagetoal").html(r.page.totalPage)
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
							return;
						}
						ids.checked = true;
						$(e.currentTarget).css("background", "#e5ebee");
					}
				});
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.alert("系统异常")
		}
	});
}

/**
 * 打开添加页面
 */
function openAdd(){
	$("#id").val("");
	$("input[name='title']").val("");
	$("input[name='pic']").val("");
	$("#bannerImg").html("");
	$("input[name='thumbUrl']").val("");
	$("input[name='url']").val("");
	$("input[name='status'][value=1]").prop("checked", true);
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

/**
 * 打开修改页面
 * @returns {Boolean}
 */
function openUpdate(){
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	var id=ids;
	$.get(hqc.base+"octopus/banner/info/" + id, function(r) {
		
		$("input[name='title']").val(r.banner.title);
		$("input[name='url']").val(r.banner.url);
		
		if (r.banner.status == 2) {
			$("input[id='disable']").prop("checked", false);
			$("input[id='normal']").prop("checked", true);
		} else if (r.banner.status == 1) {
			$("input[id='disable']").prop("checked", true);
			$("input[id='normal']").prop("checked", false);
		}	
		$("input[name='id']").val(r.banner.id);
		$("input[name='pic']").val(r.banner.pic);
		$("input[name='thumbUrl']").val(r.banner.thumbUrl);
		var thumbUrl = r.banner.thumbUrl;
		if (null != thumbUrl && thumbUrl != "") {
			thumbUrl = "<a target='_blank' href='" 
					+thumbUrl
					+ "'><img width='250px' height='250px' src='" 
					+ r.banner.thumbUrl
					+ " '  alt='图片不存在！'/> </a>";
		} else {
			thumbUrl = "<font color=red>图片未保存！</font>";
		}
		$("#bannerImg").html(thumbUrl);
	});
$("#rightbody").css("display", "none")
$("#add").css("display", "block")
}

/**
 * 保存
 * @returns {Boolean}
 */
function saveOrUpdate() {
	var id = $("#id").val() == "" ? null : $("#id").val();
	var title = $("#title").val();
	var thumbUrl = $("#thumbUrl").val();
	var imgurl = $("#url").val();


	var status = $("input[type=radio]:checked").val();
	if(title == null || title ==""){
		layer.msg("请填写图片标题");
		return false;
	}
	if(thumbUrl == null || thumbUrl ==""){
		layer.msg("请上传图片");
		return false;
	}
	if(imgurl == null || imgurl ==""){
		layer.msg("请填写链接");
		return false;
	}	
	
	var url = id == null ? hqc.base+"octopus/banner/save" : hqc.base+"octopus/banner/update";
	$.ajax({
		type : "POST",
		url : url,
		data : {
			id : id,
			title : title,
			thumbUrl : thumbUrl,
			url : imgurl,
			status : status
		},
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
 * 修改上、下架状态
 * @param id
 * @param status
 */
function auditStatus(id,status){
	layer.confirm('确定要更改选中的记录状态？', function() {
		$.ajax({
			type : "POST",
			url : hqc.base+"octopus/banner/auditStatus",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				id : id,
				status : status
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
							url : hqc.base + "octopus/banner/del",
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
})

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
// 翻页
function clickpage(event) {
	$("#page").val(event)
	queryList();
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

function upload(event) {
	//空值的话不上传
	if (event.files == null || event.files.length < 1) {
		return false;
	}
	// 判断上传的是否是图片
	if (!/\.(jpg|jpeg|png|GIF|JPG|PNG)$/.test(event.value)) {
		layer.alert("上传图片失败,请重新选择,图片类型必须是jpeg,jpg,png中的一种");
		return false;
	}
	var name = event.name;
	var uploadType = 7; // 上传类型 6为横幅图片
	if (name == "pic") { // 横幅图片
		uploadType = 7;
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
				// style="word-wrap:break-word;white-space:prewrap;"></pre>
				data = (new Function("return " + data))();
			} catch (err) {
				data = dataCopy;
				data = (new Function("return " + data))();
				// 在这里处理错误
			}
			if (data.code == 0) {
				

				if (name == "pic") {// 横幅图片

					var htmlImage = "<img width='400px' height='250px' src='"
							+ hqc.base + data.filePath.replace(/\\/g, "/")
							+ " '  alt='图片不能显示'/> ";
					$("#bannerImg").html(htmlImage);

					$("input[name='thumbUrl']").val(data.filePath);
				//	var fileName1 = getFileName(data.filePath).substring(13);
				//	$("#fileName").html(fileName1);
					layer.alert("上传成功");
				}
			} else {
				alert(data.msg);
			}
		},
		error : function(data, status, e) {// 服务器响应失败处理函数
			alert("系统出错,请联系管理员");
		}
	});
}