<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理员列表</title>
<link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
<link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css" />
<script src="${base}/statics/libs/jquery.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
	    <li><a href="javascript:void(0);">系统管理</a></li>
	    <li><a href="javascript:void(0);">管理员列表</a></li>
    </ul>	
    </div>
    <div class="rightinfo" >
    <div id="rightbody">
	    <div class="tools">
            <div class="row">
	      	<div class="col-xs-2">
			    <input type="text" class="form-control" id="username" placeholder="用户名">
			</div>
	    	<ul class="toolbar">
	    	[@shiro.hasPermission name="sys:user:list"]
	    	 <li class="click" onclick="selectByname()"><span><img src="${base}/statics/images/octopus/find024.png"></span>查询</li>
	        [/@shiro.hasPermission]
	        [@shiro.hasPermission name="sys:user:save"]
	        	<li class="click" onclick="openAddUser()"><span><img src="${base}/statics/images/octopus/t01.png" /></span>添加</li>
	        [/@shiro.hasPermission]
	        [@shiro.hasPermission name="sys:user:update"]
	        	<li class="click" onclick="openUpdate()"><span><img src="${base}/statics/images/octopus/t02.png" /></span>修改</li>
	        [/@shiro.hasPermission]
	        [@shiro.hasPermission name="sys:user:delete"]
	        	<li  onclick="del()"><span><img src="${base}/statics/images/octopus/t03.png" /></span>删除</li>
	        [/@shiro.hasPermission]
	        </ul>
	        </div>
	    </div>
   
    	<input type='hidden' value='1' id='page'/>
	    <table class="tablelist" >
	    	<thead>
	    	<tr>
		    <th><input type="checkbox" id="selectAll"/></th>
	        <th>用户ID</th>
	        <th>用户名</th>
	        <th>邮箱</th>
	        <th>手机号</th>
	        <th>状态</th>
	        <th>创建时间</th>
	        </tr>
	        </thead>
	         <tbody id="list">
	            </tbody>
	        </table>
	        <div class="pagin">
	              <div class="message">共<i class="total"></i>条记录，&nbsp&nbsp共<i class="pagetoal"></i>页&nbsp&nbsp当前显示第&nbsp;<i
                    class="page" style="margin-left:-0.1px"></i>页&nbsp&nbsp每页显示&nbsp&nbsp
                <select onchange="selectByLimit()" style="border:1px solid  black" id="limit">
	                <option value="10">10</option>
	                <option value="20">20</option>
	                <option value="50">50</option>
	                <option value="100">100</option>
            	</select>条
            </div>
	            <ul class="paginList" style="margin-right:300px">
	            </ul>
	        </div>
        </div>
        
          <div id="add" style="display:none">
            <form id="userForm" class="form-horizontal">
          	<input type="hidden" class="form-control" name="userId" id="userId"/>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户名</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="username" placeholder="登录账号"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">密码</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="password" placeholder="密码"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">邮箱</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="email" placeholder="邮箱"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">手机号</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="mobile" placeholder="手机号"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色</div>
			   	<div id="rolelist" class="col-sm-3">
				   	
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">状态</div> 
				<div class="col-sm-3">
					<label class="radio-inline">
					  <input type="radio" name="status" value="0" id="disable"/> 禁用
					</label>
					<label class="radio-inline">
					  <input type="radio" name="status" value="1" id="normal" checked="checked"/> 正常
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<div class="col-sm-3">
					<input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="确定"/>
					&nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
				</div>
			</div>
		</form>
        </div>
<script src="${base}/statics/js/octopus/sys/admin.js"></script>
</body>

</html>
