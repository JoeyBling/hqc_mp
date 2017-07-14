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
	var limit = $("#limit").val();
	var mpVipName = $("#mpVipName").val();
	$.ajax({
		url : '../sys/MpVipLevel/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			mpVipName : mpVipName,
			page : page,
			checkpage : n,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td>"
									+ "<td>"
									+ content.id
									+ "</td><td>"
									+ content.vipName
									+ "</td><td>"
									+ (content.minIntegral == null
											? ''
											: content.minIntegral)
									+ "</td>"
									+ "<td>"
									+ (content.maxIntegral == null ? '' : content.maxIntegral)
									+ "</td><td>"
									+ (content.normalIntegralRule == null ? '' : content.normalIntegralRule)
									+ "</td><td>"
									+ (content.specialIntegralRule == null ? '' : content.specialIntegralRule)
									+ "</td><td>"
									+ (content.integralCoefficient == null ? '' : content.integralCoefficient)
									+ "</td><td>"
									+ (content.about == null ? '' : content.about)
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
								+ r.page.currPage + "'/>");
				$(".pagetoal").html(r.page.totalPage);
				for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
					if (i < r.page.currPage + 3 && i > -1) {
						$(".paginList")
								.append(" <div  id='pageint"
										+ (i + 1)
										+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
										+ (i + 1) + ")'>" + (i + 1)
										+ "</a></div>");
					}
				}
				$("#pageint" + r.page.currPage + "")
						.html("<div class='page_curr'>"
								+ r.page.currPage + "</div>");
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
	$("#page").val(event);
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
							$('input[name="ids"]');
							$(".tablelist tr:gt(0)").css("background", "");
						}
					});
		});

    //添加 
function saveOrUpdate(){
	var vipName=$("input[name='vipName']").val();
	var minIntegral=$("input[name='minIntegral']").val();
	var maxIntegral=$("input[name='maxIntegral']").val();
	var normalIntegralRule=$("input[name='normalIntegralRule']").val();
	var specialIntegralRule=$("input[name='specialIntegralRule']").val();
	var integralCoefficient=$("input[name='integralCoefficient']").val();
	var iconUrl=$("input[name='iconUrl']").val();
	if (vipName == null || vipName == "") {
		layer.msg("请填写会员等级");
		return false;
		}
	if (minIntegral == null || minIntegral == "") {
		layer.msg("请填写最少积分");
		return false;
		}
	if (minIntegral < 0) {
		layer.msg("最少积分必须大于或等于0");
		return false;
		}
	if (maxIntegral == null || maxIntegral == "") {
		layer.msg("请填写最大积分");
		return false;
		}
	if (maxIntegral <= 0) {
		layer.msg("最大积分必须大于0");
		return false;
	}
	if (normalIntegralRule == null || normalIntegralRule == "") {
		layer.msg("请填写常规日积分");
		return false;
		}
	if (normalIntegralRule <= 0) {
		layer.msg("常规日积分必须大于0");
		return false;
		}
	if (specialIntegralRule == null || specialIntegralRule == "") {
		layer.msg("请填写特别日积分");
		return false;
		}
	if (specialIntegralRule <= 0) {
		layer.msg("特别日积分必须大于0");
		return false;
		}
	if (integralCoefficient == null || integralCoefficient == "" ) {
		layer.msg("请填写积分系数");
		return false;
		}
	if (integralCoefficient <= 0) {
		layer.msg("积分系数必须大于0");
		return false;
		}
	
	if (iconUrl == null || iconUrl == "") {
		layer.msg("请上传会员图标");
		return false;
		}
	var id = $("#id").val();
	var url = "";
	if (id.length == 0) {
		url = "../sys/MpVipLevel/save";
	} else {
		url = "../sys/MpVipLevel/update";
	}
	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		url : url, 
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
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg('系统异常,请联系管理员!', function() {
						reload();
					});
		}
	});
	}

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

//修改
function UpdateMpVipLevel() {
	var id=checkData();
	if (id.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (id.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$.get("../sys/MpVipLevel/info/" + id, function(r) {
		$("input[name='id']").val(r.mpMember.id);
		$("input[name='vipName']").val(r.mpMember.vipName);
		$("input[name='minIntegral']").val(r.mpMember.minIntegral);
		$("input[name='maxIntegral']").val(r.mpMember.maxIntegral);
		$("input[name='normalIntegralRule']").val(r.mpMember.normalIntegralRule);
		$("input[name='specialIntegralRule']").val(r.mpMember.specialIntegralRule);
		$("input[name='integralCoefficient']").val(r.mpMember.integralCoefficient);
		$("#about").val(r.mpMember.about);
		$("input[name='iconUrl']").val(r.mpMember.iconUrl);
		$("input[name='pic']").val("");
		var imgUrl = hqc.base+r.mpMember.iconUrl;
		var errorImg = '"'+hqc.base+'statics/images/wx/user/icon/chengzhu.png"';
		var imgHtml = "<img src='"+imgUrl
											+"'/>";
		$("#iconImg").html(imgHtml);
		$("#iconImg").val(r.mpMember.about);
		
	});
	
	$("#rightbody").css("display","none");
	$("#add").css("display","block");
}
//删除
 function deleteMpVipLevel(){
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
					url : "../sys/MpVipLevel/delete",
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
						}
					},
					error : function() {
						layer.msg('系统出错,请联系管理员', function() {
									location.reload();
								});
					}
				});
		 })
}

 //判断会员等级是否被占用
 function openLevel(){
	 var vipName=$("input[name='vipName']").val();
	 $.ajax({
			cache : true,
			type : "POST",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url :'../sys/MpVipLevel/infoName',  
			data : {vipName:vipName},
			dataType : "json",
			async : false,
			success : function(result) {
				if (result.code == 0) {
					
				} else {
					layer.msg('会员等级已被占用,请重新输入');
				}
			}
		});
	 
}

function addMpVipLevel() {
	//获取数据库中积分的最大值
	$.get("../sys/MpVipLevel/maxIngeral", function(r) {
		var max=(r.maxIngeral+1);
		$("input[name='minIntegral']").val(max);
	});
	$("input[name='vipName']").val("");
	$("input[name='minIntegral']").val("");
	$("input[name='maxIntegral']").val("");
	$("input[name='id']").val("");
	$("input[name='normalIntegralRule']").val("");
	$("input[name='specialIntegralRule']").val("");
	$("input[name='integralCoefficient']").val("");
	$("#about").val("");
	$("#iconImg").html("");
	$("#iconUrl").val("");
	$("#pic").val("");
	$("#rightbody").css("display","none");
	$("#add").css("display","block");
	
}
function reload() {
	queryList();
	$("#rightbody").css("display","block");
	$("#add").css("display","none");
	$("#update").css("display","none");
}
function upload(event) {
	// 判断上传的是否是图片
	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(event.value)) {
		layer.msg("上传图片失败,请重新选择,图片类型必须是.gif,jpeg,jpg,png中的一种");
		return false;
	}
	var name = event.name;
	var uploadType = 4; // 上传类型 4为会员等级图标
	if (name == "pic") { // 会员等级图标
		uploadType = 4;
	}
	if (event.files == null || event.files.length < 1) {
		if (name == "pic") { // 会员等级图标
			$("input[name='iconUrl']").val("");
		}
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
				

				if (name == "pic") {// 会员等级图标

					var htmlImage = "<img width='40px' height='40px' src='"
							+ hqc.base + data.filePath
							+ " '  alt='图片不能显示'/> ";
					$("#iconImg").html(htmlImage);

					$("input[name='iconUrl']").val(data.filePath);
					
					layer.msg("上传成功");
				}
			} else {
				alert(data.msg);
			}
		},
		error : function(data, status, e) {// 服务器响应失败处理函数
			layer.msg("系统出错,请联系管理员");
		}
	});
}

