function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
$(function() {
	$.getJSON("../sys/user/info?_" + $.now(), function(r) {
		user = r.user;
		rightFrame = parent.window.frames["topFrame"];
		rightFrame.document.getElementById("userName").innerText = user.username; // 显示用户名
	});

	$.getJSON("../sys/menu/user?_" + Math.random(), function(r) {
		if (r.code === 0) {
			menuList = r.menuList;
			$.each(r.menuList, function(i) {
				if (r.menuList[i].parentId == 0) { // 一级菜单
					var flag = "";
					flag += '<dd><div class="title" onclick="upDown(this)"><span><i class="'
							+ r.menuList[i].icon
							+ '"></i></span>'
							+ r.menuList[i].name + '</div>';
					if (r.menuList[i].list != null
							&& r.menuList[i].list.length > 0) {
						flag += '<ul class="menuson">';
					} else {
						return true; // $.each return true跳出循环 return
						// false终止循环
					}
					$.each(r.menuList[i].list, function(y) {
						flag += '<li onclick="navigation(this);"><cite></cite><a href="#'
								+ r.menuList[i].list[y].url
								+ '">'
								+ r.menuList[i].list[y].name
								+ '</a><i></i></li>';
					});
					flag += "</ul></dd>";
					$(".leftmenu").append(flag);
				}
			});
		} else {
			layer.msg(r.msg);
		}
		// 路由
		var router = new Router();
		routerList(router, menuList);
		router.start();
	});
});

function repwd() {
	layer.open({
		type : 1,
		skin : 'layui-layer-molv',
		title : "修改密码",
		area : ['550px', '270px'],
		shadeClose : false,
		content : jQuery("#passwordLayer"),
		btn : ['修改', '取消'],
		btn1 : function(index) {
			var $password = $("input[name='password']").val();
			var $newPassword = $("input[name='newPassword']").val();
			if (null == $password || "" == $password) {
				layer.alert('原密码不能为空');
				return false;
			}
			if (null == $newPassword || "" == $newPassword) {
				layer.alert('新密码不能为空');
				return false;
			}
			var data = "password=" + $password + "&newPassword=" + $newPassword;
			$.ajax({
						type : "POST",
						url : "sys/user/password",
						data : data,
						dataType : "json",
						success : function(result) {
							if (result.code == 0) {
								layer.close(index);
								layer.alert('修改成功', function(index) {
											location.reload();
										});
							} else {
								layer.alert(result.msg);
							}
						},
						error : function() {
							layer.alert('系统出现异常!', function() {
										location.reload();
									});
						}
					});
		}
	});
}

// 注册路由
function routerList(router, menuList) {
	for (var key in menuList) {
		var menu = menuList[key];
		var url = window.location.hash;
		// if ("#sys/project/index.html?projectId" == url.substring(0, url
		// .lastIndexOf("="))) { // 个人自定义临时使用
		// // 替换iframe的url
		// main = url.replace('#', '');
		// // 导航菜单展开
		// $(".treeview-menu li").removeClass("active");
		// }
		if (menu.type == 0) {
			routerList(router, menu.list);
		} else if (menu.type == 1) {
			router.add('#' + menu.url, function() {
						var url = window.location.hash;
						rightFrame = parent.window.frames["rightFrame"];
						// 替换iframe的url
						main = url.replace('#', '');
						rightFrame.location.href = '../' + main;
					});
		}
	}
}