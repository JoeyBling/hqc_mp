function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	var projectName = $("#projectName").val();
	var status = $("#status").val();
	$.ajax({
		url : "../projectsss/pro/list",
		datatype : "json",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			limit : limit,
			projectName : projectName,
			status : status
		},
		type : 'post',
		success : function (r){
		  $.each(r.page.list, function(index, content) {
			  var status = ""
					if (content.status == 0) {
						status = "<span class='label label-danger'>停用</span>";
					}
					if (content.status == 1) {
						status = "<span class='label label-success'>启用</span>";
					}
					
					var thumbUrl = content.thumbUrl;
					if (null != thumbUrl && thumbUrl != "") {
						thumbUrl = encodeURI(thumbUrl);
						thumbUrl = "<a target='_blank' href='"
								+ hqc.base
								+ "file/download?name="
								+ thumbUrl + "'>下载</a>";
					} else {
						thumbUrl = "<font color=red>图片未保存！</font>";
					}
			$("#list")
				.append("<tr>" +
						"<td><input name='ids' type='checkbox' value='" + content.id + "'/></td>" +
						"<td>" + (content.projectName == null ? '' : content.projectName) + "</td>" +								
						"<td>" + (content.mpProjectCategoryEntity.categoryName == null ? '' : content.mpProjectCategoryEntity.categoryName) + "</td>" +
						"<td>" + (content.mpScenerySpotEntity.sceneryName == null ? '' : content.mpScenerySpotEntity.sceneryName) + "</td>" +
						"<td>" + (content.lat == null ? '' : content.lat) + "</td>" +
						"<td>" + (content.lng == null ? '' : content.lng) + "</td>" +
						"<td>" + status + "</td>" +								
						"</tr>")
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
			$(".page").html("<input type='text' readonly='readonly' style='width:20px' value='" + r.page.currPage + "'/>");
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
			$("#pageint" + r.page.currPage + "").html("<div class='page_curr'>" + r.page.currPage + "</div>")
			if (r.page.currPage < r.page.totalPage && r.page.totalPage !== 0) {
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
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}	
	});
}
$(function() {
	queryList();
})
$(function() {	
	// 全选	 
	$("#checkedAll").click(function() {
		$('input[name="ids"]').prop("checked", this.checked);
				if (this.checked) {
					$(".tablelist tr:gt(0)").css("background","#e5ebee");
				} else {
					$('input[name="ids"]')
					$(".tablelist tr:gt(0)").css("background", "");
				}
			});
});
function clickpage(event) {
	$("#page").val(event)
	queryList();
}
function reload(){
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}
function openAdd() {
	// 清空
	/** 是否是修改 */
	$("#id").val("");
	$("input[name='projectName']").val("");
	$("select[name='categoryId']").children('option').remove();
	$("select[name='sceneryId']").children('option').remove();
	$("input[name='lat']").val("");
	$("input[name='lng']").val("");
	$("input[name='thumbUrl']").val("");
	$("textarea[name='about']").val("");
	$("#projectContent").summernote('code', "");
	$("input[name='pic']").val("");
	$("#thumb").html("");
	$("[name=like_count]").attr("readonly", "readonly");
	$("input[name='status'][value=0]").prop("checked", true);
	getcategory();
	getScenery();
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");

}
//验证名称是否相同
function verify(){
	var projectNames = $("#projectNames").val();
		$.ajax({
			type: "POST",
		    url: "../projectsss/pro/infoName",
		    dataType: "json",
		    headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
		    data : {projectName : projectNames},
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
 * 保存
 * 
 * @param event
 */
function saveOrUpdate() {
	var id = $("#id").val() == "" ? null : $("#id").val();
	var projectNames = $("#projectNames").val();
	var lat = $("#lat").val();
	var lng = $("#lng").val();
	var projectContent = $("#projectContent").summernote('code');
	var about = $("#about").val();
	var categoryId = $("#categoryId").val();
	var sceneryId = $("#sceneryId").val();
	var thumbUrl = $("#thumbUrl").val();
	var status = $("input[type=radio]:checked").val();
	if(projectNames == null || projectNames ==""){
		layer.msg("请填写项目名称");
		return false;
	}
	if(lat == null || lat ==""){
		layer.msg("请填写纬度");
		return false;
	}
	if(lng == null || lng ==""){
		layer.msg("请填写经度");
		return false;
	}	
	if(about == null || about ==""){
		layer.msg("请填写项目简介");
		return false;
	}
	if(projectContent == null || projectContent ==""){
		layer.msg("请填写项目详细介绍");
		return false;
	}
	var url = id == null ? "../projectsss/pro/save" : "../projectsss/pro/update";
	$.ajax({
		type : "POST",
		url : url,
		data : {
			id : id,
			projectName : projectNames,
			lat : lat,
			lng : lng,
			about : about,
			thumbUrl : thumbUrl,
			sceneryId : sceneryId,
			categoryId : categoryId,
			projectContent : projectContent,
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
 * 修改时得到项目类型
 */
function getcategory() {
	$.get("../projectsss/pro/getcategory", function(r) {
		for (var i = 0; i < r.categoryList.length; i++) {
			$("select[name='categoryId']").append("<option value='" + r.categoryList[i].id + "'>" + r.categoryList[i].categoryName + " </option>")
		}
	});
}
/**
 * 修改时得到景点名称
 */
function getScenery() {
	$.get("../projectsss/pro/getscenery", function(r) {
		for (var i = 0; i < r.sceneryList.length; i++) {
			$("select[name='sceneryId']").append("<option value='" + r.sceneryList[i].id + "'>" + r.sceneryList[i].sceneryName + " </option>")
		}
	});
}
/**
 * 修改
 * @returns {Boolean}
 */
function update(){
	 var ids = checkValue();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	var id=ids;
	$.get("../projectsss/pro/info/" + id, function(r) {
				$("input[name='id']").val(r.entity.id);				
				$("input[name='projectName']").val(r.entity.projectName);
				$("input[name='lat']").val(r.entity.lat);
				$("input[name='lng']").val(r.entity.lng);
				$("textarea[name='about']").val(r.entity.about);
				$('#projectContent').summernote('code',r.entity.projectContent == null ? "" : r.entity.projectContent);
				$("input[name='thumbUrl']").val(r.entity.thumbUrl);
				$("select[name='sceneryId']").children('option').remove();
				$("select[name='sceneryId']").append("<option value='"+r.entity.mpScenerySpotEntity.id+"'>"+r.entity.mpScenerySpotEntity.sceneryName+" </option>");
				$("select[name='categoryId']").children('option').remove();
				$("select[name='categoryId']").append("<option value='"+r.entity.mpProjectCategoryEntity.id+"'>"+r.entity.mpProjectCategoryEntity.categoryName+" </option>");
				getcategory();
				getScenery();
				$("#pic").val("");
				var thumbUrl = r.entity.thumbUrl;
				if (null != thumbUrl && thumbUrl != "") {
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = "<a target='_blank' href='" + hqc.base
							+ "file/download?name=" + thumbUrl
							+ "'><img width='250px' height='250px' src='" + hqc.base
							+ r.entity.thumbUrl.replace(/\\/g, "/")
							+ " '  alt='图片不存在！'/> </a>";
				} else {
					thumbUrl = "<font color=red>图片未保存！</font>";
				}
				$("#thumb").html(thumbUrl);
				if (r.entity.status == 0) {
					$("input[id='disable']").prop("checked", false);
					$("input[id='normal']").prop("checked", true);
				} else if (r.entity.status == 1) {
					$("input[id='disable']").prop("checked", true);
					$("input[id='normal']").prop("checked", false);
				}		
			});
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
}
/**
 * 删除
 * 
 * @returns {Boolean}
 */
function del() {
	var ids = checkValue();
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
		$.ajax({
			type : "POST",
			url : "../projectsss/pro/delete",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				id : ids
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
//上传缩略图
function upload(event) {
	var name = event.name;
	var uploadType = 1; // 上传类型 1 景区缩略图

	if (event.files == null || event.files.length < 1) {
		return false; // 未选择文件
	}
	// 判断上传的是否是图片
	if (!/\.(gif|jpg|jpeg|png|bmp|JPEG|BMP|GIF|JPG|PNG)$/.test(event.value)) {
		layer.msg("上传图片失败,请重新选择,图片类型必须是.gif,jpeg,jpg,png,bmp中的一种");
		return false;
	}

	$.ajaxFileUpload({
		url : '/hqc_mp/file/upload?uploadType=' + uploadType + '&name=' + name, // 用于文件上传的服务器端请求地址
		secureuri : false,
		fileElementId : event.id,
		type : 'post',
		dataType : 'jsonp', // 返回值类型 一般设置为json
		success : function(data, status) {// 服务器成功响应处理函数
			var dataCopy = data;
			try {
				var reg = /<pre.+?>(.+)<\/pre>/g;
				data = data.match(reg);
				data = RegExp.$1; // 解决上传文件 返回值带
				// <prestyle="word-wrap:break-word;white-space:prewrap;"></pre>
				data = (new Function("return " + data))();
			} catch (err) {
				data = dataCopy;
				data = (new Function("return " + data))();
				// 在这里处理错误
			}
			if (data.code == 0) {
				layer.alert("上传成功");
				if (name == "pic") {// 缩略图
					$("input[name='thumbUrl']").val(data.filePath);
					var htmlImage = "<img width='250px' height='250px' src='"
							+ hqc.base + data.filePath.replace(/\\/g, "/")
							+ " '  alt='图片不能显示！' /> ";
					$("#thumb").html(htmlImage);
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
//获取选中的多选框的值
function checkValue() {
	var ids = "";
	$("#list td").each(function() {
						if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
							if (ids.length == 0) {
								ids = $(this).find("input[type=checkbox]:checked").val()
							} else {
								ids = ids + "," + $(this).find("input[type=checkbox]:checked").val();
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