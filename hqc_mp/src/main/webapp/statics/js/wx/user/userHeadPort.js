$(function() {
	$.ajax({
		url : '../user/headPort/queryMember',
		type : 'post',
		datatype : 'json',
		success : function(data) {
			if (data.code == 0) {
				if (data.member.avatar == null
						|| data.member.avatar.length == 0) {
					$("#logoBox")
							.html("<img id='bgl' src='"
									+ hqc.base
									+ "statics/images/wx/user/user_avatar.png' width='100%'>");
				} else {
					$("#logoBox").html("<img id='bgl' src='" + hqc.base
							+ data.member.avatar + "' width='100%'>");
				}
			} else {
				layer.msg(data.msg, {
					icon : 5,
					time : 2000
						// 2秒关闭（如果不配置，默认是3秒）
					}, function() {
					if (data.code === 100) { // 先登录
						var url = location.href; // 获取当前url地址
						var paraString = url.replace(/\//g, "%2F").replace(
								/:/g, "%3A").replace(/=/g, "%3D").replace(
								/\?/g, "%3F");
						parent.location.href = hqc.base
								+ 'wx/user/login.html?returnUrl=' + paraString;
					}
				});
			}
		},
		error : function() {
			layer.msg("系统错误");
		}
	})

})
// 将base64数据转换为图片格式
function update() {
	var base64Data = $("#dataURL").html();
	var blob = dataURItoBlob(base64Data); // 上一步中的函数
	var canvas = document.createElement('canvas');
	var dataURL = canvas.toDataURL('image/jpeg', 0.5);
	var fd = new FormData(document.forms[0]);
	fd.append("file", blob);
	fd.append("name", "file");
	fd.append("uploadType", "0");
	$.ajax({
		url : "../user/headPort/updateHeadPort",
		method : 'POST',
		cache : false,
		processData : false, // 必须
		contentType : false, // 必须
		data : fd,
		success : function(result) {
			if (result.code == 0) {
				layer.msg("头像修改成功");
				parent.location.href = hqc.base + 'wx/user/userCenter.html';
			} else {
				layer.msg(result.msg, {
					icon : 5,
					time : 2000
						// 2秒关闭（如果不配置，默认是3秒）
					}, function() {
					if (data.code === 100) {
						var url = location.href; // 获取当前url地址
						var paraString = url.replace(/\//g, "%2F").replace(
								/:/g, "%3A").replace(/=/g, "%3D").replace(
								/\?/g, "%3F");
						parent.location.href = hqc.base
								+ 'wx/user/login.html?returnUrl=' + paraString;
					}
				});
			}
		},
		error : function() {
			layer.msg("系统错误");
		}
	});
}

function dataURItoBlob(base64Data) {
	var byteString;
	if (base64Data.split(',')[0].indexOf('base64') >= 0)
		byteString = atob(base64Data.split(',')[1]);
	else
		byteString = unescape(base64Data.split(',')[1]);
	var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
	var ia = new Uint8Array(byteString.length);
	for (var i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}
	return new Blob([ia], {
				type : mimeString
			});
}
