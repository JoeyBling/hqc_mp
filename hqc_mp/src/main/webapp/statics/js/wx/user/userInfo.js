// 修改名字
function updateName() {
	$('#updateName').modal({
		closeOnConfirm : false,
		relatedTarget : this,
		onConfirm : function(e) {
			var trueName = $("#trueName").val();
			if (null == trueName || trueName == "") {
				layer.msg('名字不能为空', {
							icon : 5
						});
			}
			$.ajax({
						type : "POST",
						dataType : "json",
						url : hqc.base + "wx/member/update",
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded'
						},
						data : {
							trueName : trueName
						},

						success : function(data) {
							if (data.code === 0) {
								layer.msg('修改成功！', {
											icon : 6
										});
								window.location.href = location.href + '?time='
										+ ((new Date()).getTime());
							} else {
								layer.msg(data.msg, {
											icon : 5
										});
							}
							if (data.code === 100) {
								window.location.href = hqc.base
										+ "wx/user/login.html";
							}
						},
						error : function(data) {
							layer.msg('未知异常，请联系管理员', {
										icon : 2
									});
							window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
						}
					})
		},
		onCancel : function(e) {
		}
	});
}
// 修改密码
function updatePassword() {
	$("#oldPassword").val("");
	$("#newPassword").val("");
	$("#secPassword").val("");
	$('#updatePassword').modal({
		closeOnConfirm : false,
		relatedTarget : this,
		onConfirm : function(e) {
			var oldPassword = $("#oldPassword").val();
			var newPassword = $("#newPassword").val();
			var secPassword = $("#secPassword").val();
			if (null == oldPassword || oldPassword == "") {
				layer.msg("请填写原密码", {
							icon : 5
						});
				return false;
			}
			if (null == newPassword || newPassword == "") {
				layer.msg("请填写新密码", {
							icon : 5
						});
				return false;
			}
			if (newPassword.length < 6) {
				layer.msg("密码长度不能小于6位", {
							icon : 5
						});
				return false;
			}
			if (null == secPassword || secPassword == "") {
				layer.msg("请确认密码", {
							icon : 5
						});
				return false;
			}
			if (newPassword != secPassword) {
				layer.msg("两次密码输入不同", {
							icon : 5
						});
				return false;
			}
			$.ajax({
						type : "POST",
						dataType : "json",
						url : hqc.base + "wx/member/updatePassword",
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded'
						},
						data : {
							oldPassword : oldPassword,
							newPassword : newPassword,
							secPassword : secPassword
						},
						success : function(data) {
							if (data.code == 0) {
								layer.msg('密码修改成功！请重新登录', {
											icon : 6
										});
								window.location.href = hqc.base
										+ "wx/user/login.html";
							} else {
								layer.msg(data.msg, {
											icon : 5
										});
							}
							if (data.code == 100) {
								window.location.href = hqc.base
										+ "wx/user/login.html";
							}
						},
						error : function(data) {
							layer.msg('未知异常，请联系管理员', {
										icon : 2
									});
							window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
						}
					})
		},
		onCancel : function(e) {
		}
	});
}

// 验证旧手机号码
function validateOldPhone() {
	var oldPhone = $("#oldphone").val();
	if(oldPhone == null || oldPhone == "" ){
		updatePhone(0);
		return;
	}
	
	$("#oldCode").val("");
	$("#phoneCodeSendSuccess").css("display", "none");
	$('#codeOldPhone').modal({
		closeOnConfirm : false,
		relatedTarget : this,
		onConfirm : function(e) {
			var oldCode = $("#oldCode").val();
			if (null == oldCode || oldCode == "") {
				layer.msg("请输入验证码", {
							icon : 2
						});
				return false;
			}
			$.ajax({
						type : "POST",
						dataType : "json",
						url : hqc.base + "wx/member/validateOldCode",
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded'
						},
						data : {
							oldCode : oldCode,
							oldPhone:oldPhone
						},
						success : function(data) {
							if (data.code == 0) {
								layer.msg('验证成功！', {
											icon : 6
										});
								updatePhone();
							} else {
								layer.msg(data.msg, {
											icon : 5
										});
							}
							if (data.code == 100) {
								window.location.href = hqc.base
										+ "wx/user/login.html";
							}
						},
						error : function() {
							layer.msg('未知异常，请联系管理员', {
										icon : 2
									});
							window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
						}
					});
		},
		onCancel : function(e) {
		}
	});
}

// 修改手机号码
function updatePhone(n) {
	$('#codeOldPhone').modal("close");
	$("#newphone").val("");
	$("#newCode").val("");

	$('#codeNewPhone').modal({
		closeOnConfirm : false,
		relatedTarget : this,
		onConfirm : function(e) {
			var newphone = $("#newphone").val();
			var newCode = $("#newCode").val();
			if (null == newphone || newphone == "") {
				layer.msg("请输入手机号", {
							icon : 2
						});
				return false;
			}
			if (null == newCode || newCode == "") {
				layer.msg("请输入验证码", {
							icon : 2
						});
				return false;
			}
			$.ajax({
						type : "POST",
						dataType : "json",
						url : hqc.base + "wx/member/updatePhone",
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded'
						},
						data : {
							newphone : newphone,
							newCode : newCode,
							n:n
						},
						success : function(data) {
							if (data.code == 0) {
								layer.msg('修改成功！', {
											icon : 6
										});
								window.location.href = location.href + '?time='
										+ ((new Date()).getTime());
							} else {
								layer.msg(data.msg, {
											icon : 2
										});
							}
							if (data.code == 100) {
								window.location.href = hqc.base
										+ "wx/user/login.html";
							}
						},
						error : function() {
							layer.msg('未知异常，请联系管理员', {
										icon : 2
									});
							window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
						}
					});
		},
		onCancel : function(e) {
		}
	});
}

function saveNewPhone() {
	var $modal = $('#codeNewPhone');
	var newphone = $("#newphone").val();
	var newCode = $("#newCode").val();
	if (null == newphone || newphone == "") {
		layer.msg("请输入手机号", {
					icon : 2
				});
		return false;
	}
	if (null == newCode || newCode == "") {
		layer.msg("请输入验证码", {
					icon : 2
				});
		return false;
	}
	$.ajax({
				type : "POST",
				dataType : "json",
				url : hqc.base + "wx/member/updatePhone",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					newphone : newphone,
					newCode : newCode
				},
				success : function(data) {
					if (data.code == 0) {
						layer.msg('修改成功！', {
									icon : 6
								});
						window.location.href = location.href + '?time='
								+ ((new Date()).getTime());
					} else {
						layer.msg(data.msg, {
									icon : 2
								});
					}
					if (data.code == 100) {
						window.location.href = hqc.base + "wx/user/login.html";
					}
				},
				error : function() {
					layer.msg('未知异常，请联系管理员', {
								icon : 2
							});
					window.location.href = location.href + '?time='
							+ ((new Date()).getTime());
				}
			});
}

// 获取手机验证码
function getPhoneCode(e, kind) {
	if ($(e).hasClass("disabled")) {
		return false;
	}
	var type;
	if (kind === 1) {
		var $phone = $("#oldphone").val(); // 原手机号码
		type = 4;
	} else {
		var $phone = $("#newphone").val(); // 新手机号码
		type = 3;
	}

	var mobileReg = /^(13|14|15|17|18)[0-9]{8}[0-9]$/;
	if (!mobileReg.test($phone)) {
		layer.msg("未输入手机号或手机号格式有误");
		return false;
	}

	$.ajax({
				type : "POST",
				url : hqc.base + "wx/phoneCode?type=" + type,
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					phone : $phone
				},
				dataType : "json",
				success : function(result) {
					$("#my-modal-loading").modal('close');
					var countdown = 59;
					var o = kind === 1
							? $("#oldGetPhoneCode")
							: $("#newGetPhoneCode");
					var iCount = setInterval(function() {
								o.text(countdown-- + "s后重试");
								o.addClass("disabled");
								if (countdown == 0) {
									o.removeClass("disabled");
									o.text("获取验证码");
									clearInterval(iCount);// 清除倒计时
									countdown = 60;// 设置倒计时时间
								};
							}, 1000);
					if (result.code === 0) {// 获取成功
						if (type == 4) {
							$("#phoneCodeSendSuccess").show();
						} else {
							$("#newPhoneCodeSendSuccess").show();
						}
					} else {
						layer.msg(result.msg, {
									icon : 5
								});
					}
				},
				error : function() {
					$("#my-modal-loading").modal('close');
					AMUI.dialog.alert({
								content : '系统出现异常!',
								onConfirm : function() {
									window.location.href = location.href
											+ '?time='
											+ ((new Date()).getTime());
									// console.log('close');
								}
							});
				}
			});
}