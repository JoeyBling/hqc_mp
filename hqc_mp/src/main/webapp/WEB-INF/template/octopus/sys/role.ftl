<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
<link rel="stylesheet" href="${base}/statics/plugins/ztree/css/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
<link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css" />
<script src="${base}/statics/libs/jquery.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
<script src="${base}/statics/plugins/ztree/jquery.ztree.all.min.js"></script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
	    <li><a href="javascript:void(0);">系统管理</a></li>
	    <li><a href="javascript:void(0);">角色管理</a></li>
    </ul>
    </div>
    
    <div class="rightinfo" >
    <div id="rightbody">
	    <div class="tools">
            <div class="row">
	   	 <div class="col-xs-2">
			    <input type="text" class="form-control" id="roleName" placeholder="角色名称">
			</div>	
	    	<ul class="toolbar">
		    	[@shiro.hasPermission name="sys:role:list"]
		    	 	<li class="click" onclick="selectByname()"><span><img src="${base}/statics/images/octopus/find024.png"></span>查询</li>
		        [/@shiro.hasPermission]
		        [@shiro.hasPermission name="sys:role:save"]
		        	<li class="click" onclick="openAddRole()"><span><img src="${base}/statics/images/octopus/t01.png" /></span>添加</li>
		        [/@shiro.hasPermission]
		        [@shiro.hasPermission name="sys:role:update"]
		        	<li class="click" onclick="openUpdateRole()"><span><img src="${base}/statics/images/octopus/t02.png" /></span>修改</li>
		       	[/@shiro.hasPermission]
		       	[@shiro.hasPermission name="sys:role:delete"]
		        	<li class="click" onclick="deleteRole()"><span><img src="${base}/statics/images/octopus/t03.png" /></span>删除</li>
		        [/@shiro.hasPermission]
	        </ul>
	    </div>
	    </div>
   
    	 <input type='hidden' value='1' id='page'/>
	    <table class="tablelist" >
	    	<thead>
	    	<tr>
	        <th><input type="checkbox" id="selectAll"/></th>
	        <th>角色ID<i class="sort"><img src="${base}/statics/images/octopus/px.gif" /></i></th>
	        <th>角色名称</th>
	        <th>备注</th>
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
            <form id="roleForm" method="post" class="form-horizontal">
            <input type="hidden" name="roleId"/>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色名称</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="roleName" placeholder="角色名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-3">
			      <input type="text" class="form-control" name="remark" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">授权</div>
			   	<div class="col-sm-3">
			      <ul id="menuTree" class="ztree"></ul>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div>
				<div class="col-sm-3">
					<input type="button" class="btn btn-primary" onclick="saveOrUpdate()" value="确定"/>
					&nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
				<div>
			</div>
		</form>
        </div>
<script src="${base}/statics/js/octopus/sys/role.js"></script>
</body>

</html>
