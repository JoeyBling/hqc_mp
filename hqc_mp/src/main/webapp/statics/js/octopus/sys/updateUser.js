$(function() {
	$("#tab2").hide();
	$
			.ajax({
				url : "../sys/user/view",
				datatype : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				type : 'post',
				success : function(r) {
					if (r.code === 0) {
						$("#username").val(r.user.username);
						$("#email").val(r.user.email);
						$("#mobile").val(r.user.mobile);
						var createTime = formatDate(new Date(r.user.createTime*1000));
						$("#createTime").html(createTime);
						var roleNames = ' ';
						if (r.roleNames.length != 0) {
							$
									.each(
											r.roleNames,
											function(index, content) {
												roleNames += "<span class='label label-success'>"
														+ content + "</span>&nbsp;&nbsp;";
											});
						} else {
							roleNames += "<span class='label label-success'>待任命</span>";
						}
						$("#roles").html(roleNames);
					} else {
						layer.alert(r.msg);
					}
				},
				error : function() {
					layer.alert('系统出现异常!', function() {
						location.reload();
					});
				}
			})
})

// 修改个人信息
function updateUser() {
	var email = $("#email").val();
	var mobile = $("#mobile").val();
	if (mobile == null || mobile == "") {
		layer.msg('请输入手机号！');
		return;
	}
	if (email == null || email == "") {
		layer.msg('请输入邮箱！');
		return;
	}
	layer.confirm('确认修改信息？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url : "../sys/user/updateView",
			data : $('#updateUser').serialize(),
			dataType : "json",
			async : false,
			success : function(result) {
				if (result.code == 0) {
					layer.alert('修改成功！', function(index) {
						location.reload();
					});
				} else {
					layer.msg(result.msg);
				}
			},
			error : function() {
				layer.confirm('修改失败！', {
					btn : [ '确定', '取消' ]
				// 按钮
				}, function() {
					location.reload();
				}, function() {
					location.reload();
				});
			}
		});
	}, function() {

	});
}

// 修改个人密码
function updatePassword() {
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	var secPassword = $("#secPassword").val();
	if (oldPassword == null || oldPassword == "") {
		layer.msg('请输入原密码！');
		return;
	}
	if (newPassword == null || newPassword == "") {
		layer.msg('请输入新密码！');
		return;
	}
	if (secPassword == null || secPassword == "") {
		layer.msg('请确认新密码！');
		return;
	}
	if (newPassword != secPassword) {
		layer.msg('两次密码输入不同！');
		return;
	}
	layer.confirm('确认修改信息？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			url : "../sys/user/updateSelfPassword",
			data : $('#updatePassword').serialize(),
			dataType : "json",
			async : false,
			success : function(result) {
				if (result.code == 0) {
					layer.alert('修改成功！', function(index) {
						location.reload();
					});
				} else {
					layer.msg(result.msg);
				}
			},
			error : function() {
				layer.confirm('修改失败！', {
					btn : [ '确定', '取消' ]
				// 按钮
				}, function() {
					location.reload();
				}, function() {
					location.reload();
				});
			}
		});
	}, function() {

	});
}

// 时间转换
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

// 设置tab链接页面
function show1() {
	$("#tab1a").addClass("selected");
	$("#tab2a").removeClass("selected");
	$("#tab2").hide();
	$("#tab1").show();
}
function show2() {
	$("#tab2a").addClass("selected");
	$("#tab1a").removeClass("selected");
	$("#tab1").hide();
	$("#tab2").show();
}