window.onload = function() {
	$.ajax({
				type : "POST",
				url : hqc.base + "wx/member/info",
				dataType : "json",
				success : function(data) {

					showMember(data)
					if (data.updateVipStatus != null
							&& data.updateVipStatus === 1) {
						$('#updateVip').modal({
									relatedTarget : this,
									onConfirm : function() {
										updateVip();
									},
									onCancel : function() {
									}
								});
					}

				},
				error : function(data) {
					layer.msg('未知异常，请联系管理员', {
								icon : 2
							});
					window.location.href = location.href + '?time='
							+ ((new Date()).getTime());
				}
			});
}

/**
 * 修改会员等级
 */
function updateVip() {
	$.ajax({
				type : "POST",
				url : hqc.base + "wx/member/updateVip",
				dataType : "json",
				success : function(data) {
					showMember(data);
				},

				error : function(data) {
					layer.msg('未知异常，请联系管理员', {
								icon : 2
							});
					window.location.href = location.href + '?time='
							+ ((new Date()).getTime());
				}
			});
}

/**
 * 显示会员信息
 */
function showMember(data) {
	if (data.code == 0) {
		if (data.member != null) {
			// 默认找不到头像图片时显示路径
			var errorImg = '"' + hqc.base
					+ 'statics/images/wx/user/user_avatar.png"';
			// 获取相关数据；
			var userAvatar = "<img src='" + hqc.base + data.member.avatar
					+ "' onerror='src=" + errorImg
					+ "'  class='img-circle avatar-img' />";
			var userIntegral = "积分：" + data.member.integral;

			// 默认找不到会员等级图片时显示路径
			var errorLevelImg = '"' + hqc.base
					+ 'statics/images/wx/user/icon/chengzhu.png"';
			var vipName = "无等级"
			if (data.member.vipLevelEntity != null
					&& data.member.vipLevelEntity.vipName != null) {
				vipName = data.member.vipLevelEntity.vipName;
			}
			var memberLevel = "<img src='"
					+ hqc.base
					+ data.member.vipLevelEntity.iconUrl
					+ "' width ='28' height='28' class='img-circle avatar-img'/>&nbsp;"
					+ vipName;
			if (data.member.cardNo == null) {
				var memberCard = "会员卡号：无";
			} else {
				var memberCard = "会员卡号：" + data.member.cardNo;
			}

			$("#avatar").html(userAvatar);
			$("#userName").html(data.member.trueName);
			$("#userIntegral").html(userIntegral);
			$("#memberLevel").html(memberLevel);
			$("#memberCard").html(memberCard);
		}
	} else if (data.code == 100) {
		layer.msg(data.msg, {
			icon : 5,
			time : 2000
				// 2秒关闭（如果不配置，默认是3秒）
			}, function() {
			parent.location.href = hqc.base + 'wx/user/login.html';
		});
	} else {
		layer.msg(data.msg, {
					icon : 5
				});
	}
}