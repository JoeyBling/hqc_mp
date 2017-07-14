$(function() {
			$.ajax({
						url : hqc.base + 'wx/config/getWxConfig',
						datatype : 'json',
						type : 'post',
						success : function(r) {
							if (r.code == 0) {
								$("#appid").val(r.appid);
								$("#appsecret").val(r.appsecret);
								$("#token").val(r.token);
								$("#aeskey").val(r.aeskey);
								$("#partener_id").val(r.partener_id);
								$("#partener_key").val(r.partener_key);
								$("#wxurl").html(r.url);
							} else {
								layer.msg(r.msg);
							}
						},
						error : function() {
							layer.msg("系统出现出现错误，请联系管理员!");
						}
					});
		});
function updateWxConfig() {
	$.ajax({
				cache : true,
				type : "POST",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				url : hqc.base + "wx/config/updateWxConfig",
				data : $('#wxConfigForm').serialize(),
				dataType : "json",
				async : false,
				success : function(result) {
					if (result.code === 0) {
						layer.alert("修改成功！请手动重启服务器!");
					} else {
						layer.alert(r.msg);
					}
				},
				error : function(result) {
					layer.alert("修改失败！");
					location.reload();
				}
			});

}