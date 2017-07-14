<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>华侨城后台管理系统</title>
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/js/octopus/cloud.js" type="text/javascript"></script>
    <script src="${base}/statics/plugins/layer/layer.js" type="text/javascript"></script>
     <script src="${base}/statics/js/common.js" type="text/javascript"></script>
    <script language="javascript">
        $(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            $(window).resize(function(){
                $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            });
        });
    </script>
    <style>
    dl,dt,dd,span{margin:0;padding:0;display:block;}
    </style>
</head>
<body style="background-color:#1c77ac; background-image:url(${base}/statics/images/octopus/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>
<div class="logintop">
    <span>欢迎登录华侨城后台管理界面平台</span>
    <ul>
        <li><a href="${base}/octopus">回首页</a></li>
    </ul>
</div>
<div class="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox" style="position: absolute; left: 614px;">
        <ul>
            <li><input name="username" id="username" type="text" class="loginuser"  onclick="JavaScript:this.value=''"/></li>
            <li><input name="password" type="password" class="loginpwd" value="" onclick="JavaScript:this.value=''" onkeyup="if(event.keyCode==13) javascript:login();" /></li>
            <li><input type="button" class="loginbtn" value="登录"  onclick="javascript:login();" /><label><input id="rememberUser" type="checkbox"/>记住账号</label></li>
        </ul>
    </div>
</div>
<div class="loginbm">版权所有  2017 <a href="javascript:void(0);">东部华侨城</a></div>
</body>
</html>
<script type="text/javascript">
	window.onload=function(){ 
		if(self != top){
			top.location.href = self.location.href;
		} 
		
		//获取用户名
		$.ajax({
			type: "POST",
		    url: "${base}/octopus/sys/getCookie",
		    dataType: "json",
		    success: function(data){
				if (data.count != 1) {
						if (data.userName != null && data.userName != "") {
							$("#username").val(data.userName);
							$("#rememberUser").attr("checked", true);
						}
					}
			},
			error : function() {
				//layer.msg("获取用户异常！");
			}
		});
	}
	
	
	function login(){
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();
		var rememberUser = 0;
		if ($('#rememberUser').is(':checked')) {
			rememberUser = 1;
		}
		if(username==null || username==''){
			layer.msg("请输入用户名！");
			return;
		}
		if(password==null || password==''){
			layer.msg("请输入密码！");
			return;
		}
		$.ajax({
			type: "POST",
		    url: "${base}/octopus/sys/login",
		    headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
			},
		    data:{
		    username:username,
		    password:password,
		    rememberUser:rememberUser
		    },
		    dataType: "json",
		    success: function(result){
				if(result.code == 0){//登录成功	
					layer.msg("登录成功", {icon: 1});
					parent.location.href ='index.html';
				}else{
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function() {
				layer.alert('系统出现异常!', function() {
							location.reload();
						});
			}
		});
	}
</script>