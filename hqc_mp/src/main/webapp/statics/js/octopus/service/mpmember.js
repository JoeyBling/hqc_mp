function queryList(n, page1) {
	$("#list").html("");
	var page
	if (page1 == 0) {
		page = 1;
	} else {
		page = $("#page").val();
	}
	var phone2 = $("#phone2").val();
	var status = $("#status").val();
	var cardNo = $("#cardNo").val();
	var limit = $("#limit").val();
	$
			.ajax({
				url : '../sys/MpMember/list',
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					phone2 : phone2,
					cardNo : cardNo,
					status : status,
					page : page,
					checkpage : n,
					limit : limit
				},
				type : 'post',
				success : function(r) {
					$
							.each(
									r.page.list,
									function(index, content) {
										var date = formatDate(new Date(
												content.createTime * 1000));
										var changeDate = formatDate(new Date(
												content.updateTime * 1000));
										var status = "";
										if (content.status === 1) {
											status = '<span class="label label-primary">启用</span>';
										}
										if (content.status === 2) {
											status = '<span class="label label-danger">禁用</span>';
										}
										$("#list")
												.append(
														"<tr><td><input name='ids' type='checkbox' value='"
																+ content.id
																+ "'/></td>"
																+ "<td>"
																+ content.id
																+ "</td><td>"
																+ (content.phone == null ? ''
																		: content.phone)
																+ "</td><td>"
																+ (content.cardNo == null ? ''
																		: content.cardNo)
																+ "</td>"
																+ "<td>"
																+ (content.vipLevelEntity.vipName == null ? ''
																		: content.vipLevelEntity.vipName)
																+ "</td>"
																+ "<td>"
																+ content.trueName
																+ "</td><td>"
																+ (content.integral == null ? ''
																		: +content.integral)
																+ "</td><td>"
																+ date
																+ "</td><td>"
																+ changeDate
																+ "</td><td>"
																+ status
																+ "</td>"
																+ "<td>"
																+ '<a href="javascript:void(0)" onclick="selectMember('
																+ content.id
																+ ')"><span style=" font-size:14px;color: #00F;">详情</span></a>&nbsp&nbsp&nbsp&nbsp'
																+ '<a href="javascript:void(0)" onclick="openUpdatepwd('
																+ content.id
																+ ')"><span style=" font-size:14px;color: #00F;">修改密码</span></a>'
																+ "</td>"
																+ "</tr>")
									})
					$("#page").val(r.page.currPage)
					var lastpage = "";
					var nextpage = "";
					$(".paginList").html("")
					if (r.page.currPage != 1) {
						$(".paginList")
								.html(
										"<li class='paginItem' ><a href='javascript:void(0);' onclick='queryList(1)'><span class='pagepre'></span></a></li>");

					}
					$(".total").html(r.page.totalCount)
					$(".page").html(
							"<input type='text' readonly='readonly' style='width:20px' value='"
									+ r.page.currPage + "'/>")
					$(".pagetoal").html(r.page.totalPage)
					for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
						if (r.page.totalPage < 2) {

						} else {
							if (i < r.page.currPage + 3 && i > -1) {
								$(".paginList")
										.append(
												" <div  id='pageint"
														+ (i + 1)
														+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
														+ (i + 1) + ")'>"
														+ (i + 1)
														+ "</a></div>")
							}
						}
					}
					$("#pageint" + r.page.currPage + "").html(
							"<div class='page_curr'>"
									+ r.page.currPage + "</div>")
					if (r.page.currPage < r.page.totalPage
							&& r.page.totalPage !== 0) {
						$(".paginList")
								.append(
										"<li style='margin-left:0.5px' class='paginItem'><a href='javascript:void(0);' onclick='queryList(2)'><span class='pagenxt'></span></a></li>");

					}
					$(".tablelist tr:gt(0)").on(
							"click",
							function(e) {
								var ids = $(e.currentTarget).find(
										"td input[name='ids']")[0];
								if (null != ids && ids.checked) {
									ids.checked = false;
									$(e.currentTarget).css("background", "");
								} else {
									ids.checked = true;
									$(e.currentTarget).css("background",
											"#e5ebee");
								}
							});
				},
				error : function() {
					layer.msg("系统出现异常")
				}
			});
}
function clickpage(event) {
	$("#page").val(event)
	queryList();
}
function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
	$("#update").css("display", "none");
	$("#tab2").css("display", "none");
}
$(function() {
	queryList();
	/**
	 * 全选
	 */
	$("#selectAll").click(function() {
		$('input[name="ids"]').prop("checked", this.checked);
		if (this.checked) {
			$(".tablelist tr:gt(0)").css("background", "#e5ebee");
		} else {
			$('input[name="ids"]')
			$(".tablelist tr:gt(0)").css("background", "");
		}
	});
});

function addMember() {
	 $("#id").val("");
	$("input[name='phone']").val("");
	$("input[name='trueName']").val("");
	$("input[name='password']").val("");
	$("input[name='avatar']").html("");
	$("input[name='pwd']").val("");
	$("input[name='birth']").val("");
	$("#avatarImg").html("");
	$("#rightbody").css("display", "none");
	$("#memberStatus").css("display", "none");
	$("#vipname").css("display", "none");
	$("#add").css("display", "block");
	$("#mppwd").css("display", "block");
	$("#mpbirth").css("display", "block");
	
	$("#pwd").css("display", "block");
	$("#mppwd").css("display", "block");
	var addPhone="<input type='text' class='form-control' id='phone' onblur='openPhone(this)' name='phone' placeholder='手机号码'/> ";
    $("#phone").html(addPhone);
}
// 点击修改，获取要修改的值
function openUpdateMember() {
	var id = checkData();
	if (id.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (id.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$.get("../sys/MpMember/info/" + id, function(r) {
		$("input[name='id']").val(r.mpMember.id);
		$("input[name='phone']").val(r.mpMember.phone);
		$("input[name='trueName']").val(r.mpMember.trueName);
		$("input[name='nickName']").val(r.mpMember.nickName);
		$("input[name='openId']").val(r.mpMember.openId);
		$("input[name='miniOpenId']").val(r.mpMember.miniOpenId);
		var bir = formatDate(new Date(
				r.mpMember.birthday * 1000));
		$("input[name='birth']").val(bir);
		$("input[name='password']").val(r.mpMember.password);
		$("input[name='pwd']").val(r.mpMember.password);
		$("input[name='integral']").val(r.mpMember.integral);
		$("input[name='vip']").val(r.mpMember.vipLevelEntity.vipName);
		if (r.mpMember.status === 1) { // 会员状态(1:启用 2:禁用)
			$("input[id='enable']").prop("checked", true);
		} else if (r.mpMember.status === 2) {
			$("input[id='disable']").prop("checked", true);
		}
		$("#avatarImg").html("");
		var imgHtml = "<a target='_blank' href='" + hqc.base
				+ "file/download?name=" + r.mpMember.avatar
				+ "'><img width='250px' height='250px' src='" + hqc.base
				+ r.mpMember.avatar + " '  alt='图片不存在'/> </a>";
		if (null != r.mpMember.avatar && r.mpMember.avatar != "") {
			$("#avatarImg").html(imgHtml);
			$("input[name='avatar']").val(r.mpMember.avatar);
		}
	});
	$("#pwd").css("display", "none")
	$("#rightbody").css("display", "none")
	$("#mppwd").css("display", "none")
	$("#mpbirth").css("display", "none");
	$("#mppssword").css("display", "none");
	$("#vipname").css("display", "block");
	$("#add").css("display", "block")
	$("#memberStatus").css("display", "block");
	var updatePhone="<input type='text' class='form-control' id='phone' name='phone' placeholder='手机号码'/> ";
    $("#phone").html(updatePhone);
}

// 获取选中的多选框的值
function checkData() {
	var id = "";
	$("#list td")
			.each(
					function() {
						if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
							if (id.length == 0) {
								id = $(this).find(
										"input[type=checkbox]:checked").val()
							} else {
								id = id
										+ ","
										+ $(this).find(
												"input[type=checkbox]:checked")
												.val();
							}
						}
					})
	return id;
}
// 做添加，并进行非空判断
function saveOrUpdate(index) {
	var phone = $("input[name='phone']").val();
	var birthday = $("input[name='birth']").val();
	var trueName = $("input[name='trueName']").val();
	var password = $("input[name='password']").val();
	var pwd = $("input[name='pwd']").val();
	var avatar = $("input[name='avatar']").val();
	var pwd = $("input[name='pwd']").val();
	var vip = $("input[name='vip']").val();
	var birth = $("input[name='birth']").val();
	/*alert(vip);
	return false;*/
	if (phone == null || phone == "") {
		layer.msg("请填写手机号");
		return false;
	}
	// 固定电话正则
	var regexp = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/
	// 手机号码正则
	var regexp2 = /^1[3|4|5|7|8][0-9]{9}$/
	if (!regexp.test(phone) && !regexp2.test(phone)) {
		layer.msg("请填写正确手机号");
		return false;
	}
	if (trueName == null || trueName == "") {
		layer.msg("请填写会员姓名");
		return false;
	}
	if (/\d/gi.test(trueName)) {
		layer.msg("请填写正确的会员姓名");
		return false;
	}
	if (avatar.length == 0 || avatar == "") {
		layer.msg("请上传会员头像");
		return false;
	}
	if (password == null || password == "") {
		layer.msg("请填写会员密码");
		return false;
	}
	 if(password.length < 6){
			layer.msg("会员密码的长度必须大于6");
			return false;
		}
	if (pwd == null || pwd == "") {
		layer.msg("请填写确认密码");
		return false;
	}
	 if(pwd.length < 6){
			layer.msg("确认密码的长度必须大于6");
			return false;
		}
	if (pwd != password) {
		layer.msg("两次输入密码不一致");
		return false;
	}
	if (birth == null || birth == "") {
		layer.msg("请填写会员生日");
		return false;
	}
	var id = $("#id").val();
	var url = "";
	if (id.length == 0) {
		url = "../sys/MpMember/save";
	} else {
		url = "../sys/MpMember/update";
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
			layer.msg('系统出现异常!', function() {
				reload();
			});
		}
	});
}

// 做修改时，当点击返回
function updateDumpMember() {
	$("#rightbody").css("display", "block")
	$("#update").css("display", "none")
	
	
}

// 判断手机号是否已存在
function openPhone() {
	var phone = $("input[name='phone']").val();
	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		url : '../sys/MpMember/infoPhone',
		data : {
			'phone' : phone
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.code == 0) {
			} else {
				layer.msg('该手机号已被占用,请重新输入');
			}
		}
	});

}

// 文件上传
window.onload = function() {
	var fileName = $("#avatar").val();
	fileName = getFileName(fileName).substring(13);
	$("#fileName").html(fileName);
}

function getFileName(o) {
	var pos = o.lastIndexOf("\\");
	if (pos != null) {
		return o.substring(pos + 1);
	}
}
function upload(event) {
	// 判断上传的是否是图片
	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(event.value)) {
		layer.msg("上传图片失败,请重新选择,图片类型必须是.gif,jpeg,jpg,png,GIF,JPG,PNG中的一种");
		return false;
	}
	var name = event.name;
	var uploadType = 0; // 上传类型 0为会员头像
	if (name == "pic") { // 会员头像
		uploadType = 0;
	}
	if (event.files == null || event.files.length < 1) {
		if (name == "pic") { // 会员头像
			$("input[name='avatar']").val("");
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
				

				if (name == "pic") {// 会员头像

					var htmlImage = "<img width='250px' height='250px' src='"
							+ hqc.base + data.filePath.replace(/\\/g, "/")
							+ " '  alt='图片不能显示'/> ";
					$("#avatarImg").html(htmlImage);

					$("input[name='avatar']").val(data.filePath);
					var fileName1 = getFileName(data.filePath).substring(13);
					$("#fileName").html(fileName1);
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
// 删除
function deleteMember() {
	var id = checkData();
	if (id.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	/*$.get("../sys/MpMember/info/" + id, function(r){
		if (r.mpMember.status === 2){
			layer.msg("该数据无法删除");
			return false;
		}
	})*/
	layer.confirm('确定要删除选中的记录？', function() {
		$.ajax({
			cache : true,
			type : "POST",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url : "../sys/MpMember/delete",
			dataType : "json",
			data : {
				id : id
			},
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
				layer.msg('系统出现异常!', function() {
					reload();
				});
			}
		});
	});
}

// 查询详细信息
function selectMember(id) {
	$.get("../sys/MpMember/info/" + id, function(r) {
		$("#avatarImg1").html("");
		var imgHtml = "<a target='_blank' href='" + hqc.base
				+ "file/download?name=" + r.mpMember.avatar
				+ "'><img width='250px' height='250px' src='" + hqc.base
				+ r.mpMember.avatar + " ' alt= '图片不存在！' /> </a>";
		if (null != r.mpMember.avatar && r.mpMember.avatar != "") {
			$("#avatarImg1").html(imgHtml);
		}
		$("input[name='id']").val(r.mpMember.id);
		$("input[name='nickName']").val(r.mpMember.nickName);
		$("input[name='openId']").val(r.mpMember.openId);
		$("input[name='miniOpenId']").val(r.mpMember.miniOpenId);
		$("input[name='pass']").val(r.mpMember.password);
		$("input[name='avatar']").val(
				getFileName(r.mpMember.avatar).substring(13));
		$("input[name='lastYearIntegral']").val(r.mpMember.lastYearIntegral);
		$("input[name='currentYearIntegral']").val(
				r.mpMember.currentYearIntegral);
		$("input[name='integral']").val(r.mpMember.integral);
		var birth = formatDate(new Date(
				r.mpMember.birthday * 1000));
		$("input[name='birthday']").val(birth);
	});
	layer.open({
		type : 1,
		skin : 'layui-layer-molv',
		title : "查询详细信息",
		area : [ '700px', '720px' ],
		shadeClose : false,
		content : jQuery("#select"),
		btn : [ '关闭' ]

	});
}

 function openUpdatepwd(id){
	
	 $("#rightbody").css("display", "none")
	 $("#tab2").css("display", "block") 
 }
 //修改密码
 function updatePassword(){
	 var id = checkData();
	 var oldPassword = $("input[name='oldPassword']").val();
	 var newPassword = $("input[name='newPassword']").val();
	 var secPassword = $("input[name='secPassword']").val();
	 if (oldPassword == null || oldPassword == "") {
			layer.msg("请填写原密码");
			return false;
		}
	 if (newPassword == null || newPassword == "") {
			layer.msg("请填写新密码");
			return false;
		}
	 if(newPassword.length < 6){
			layer.msg("确认密码的长度必须大于6");
			return false;
		}
	 if (secPassword == null || secPassword == "") {
			layer.msg("请填写确认密码");
			return false;
		}
	 if(secPassword.length < 6){
			layer.msg("确认密码的长度必须大于6");
			return false;
		}
	 if(newPassword != secPassword){
		 layer.msg("两次填写密码不一致");
			return false;
	 }
	 $.ajax({
			cache : true,
			type : "POST",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url : "../sys/MpMember/updatePassword",
			data : {
				oldPassword : oldPassword,
				newPassword : newPassword,
				secPassword : secPassword,
				id : id
			},
			dataType : "json",
			async : false,
			success : function(result) {
				if (result.code == 0) {
					layer.alert('密码修改成功', function(index) {
						layer.close(index);
						reload();
					});
				} else {
					layer.msg(result.msg);
				}
			},
			error : function() {
				layer.msg('系统出现异常!', function() {
					reload();
				});
			}
		});
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