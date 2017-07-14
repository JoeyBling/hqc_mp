<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>首页</title>
	<link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
   <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css" />
	<script src="${base}/statics/libs/jquery.min.js"></script>
	<script src="${base}/statics/libs/bootstrap.min.js"></script>
	<script src="${base}/statics/plugins/layer/layer.js"></script>
	<style>
	
	</style>
</head>
<body>

<div class="place">
    <span>位置：</span>
    	<ul class="placeul">
        	<li><a href="javascript:void(0);">首页</a></li>
        	<li><a href="javascript:void(0);">控制台</a></li>     
    	</ul> 		 
</div>


    
 <div class="tabbable">
	<div class="itab">
	 	<ul> 
    		<li><a href="javascript:void(0);" id="tab1a" class="selected" onclick = "show1()">个人信息</a></li> 
    		<li><a href="javascript:void(0);" id="tab2a" onclick = "show2()">修改密码</a></li> 
  		</ul>
  	</div>

  	

     <div class="tabson" id="tab1">
     	 <form id="updateUser">
		    <ul class="forminfo">
			    <li><label>用户名</label><input style="padding:0px" name="username" id="username" type="text" class="dfinput form-control"  disabled/></li>
			    <li><label>邮&nbsp;箱</label><input name="email" id="email" type="text" class="dfinput" /></li>
			    <li><label>手机号</label><input name="mobile" id="mobile" type="text" class="dfinput"  /></li>
			   	<li><label>当前角色</label><cite><span id="roles"></span></cite></li>
			    <li><label>创建时间</label><cite><span id="createTime"></span></cite></li>
			    <li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" onclick="updateUser();" value="确认保存"/></li>
		    </ul>
		    </form>
    	  </div>
    		 <div  class="tabson"  id="tab2">
    		 <form id="updatePassword">
	    		  <ul class="forminfo">
	    		    <li><label>原密码</label><input name="oldPassword" id="oldPassword" type="password" class="dfinput"  /></li>
	    		    <li><label>新密码</label><input name="newPassword" id="newPassword" type="password" class="dfinput" /></li>
	    		    <li><label>确认密码</label><input name="secPassword" id="secPassword" type="password" class="dfinput" /></li>
	    		     <li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" onclick="updatePassword();"  value="确认修改"/></li>
	    		</ul>
	    	</form>
    	 </div>
      </div>
</div>
<script type="text/javascript" src="${base}/statics/js/octopus/sys/updateUser.js"></script>
</body>
</html>