<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/statics/plugins/ztree/css/metroStyle/metroStyle.css">
<link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${base}/statics/css/font-awesome.min.css">
<script src="${base}/statics/libs/jquery.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
<script src="${base}/statics/plugins/ztree/jquery.ztree.all.min.js"></script>
<style>
</style>
</head>

<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
	    <li><a href="javascript:void(0);">系统管理</a></li>
	    <li><a href="javascript:void(0);">菜单管理</a></li>
    </ul>
    </div>
    <div class="rightinfo" >
    <div id="rightbody">
	    <div class="tools">
	    	<ul class="toolbar">
	    	 [@shiro.hasPermission name="sys:menu:save"]
	        	<li class="click" onclick="openAddUser()"><span><img src="${base}/statics/images/octopus/t01.png" /></span>添加</li>
	      	 [/@shiro.hasPermission]
	      	 [@shiro.hasPermission name="sys:menu:update"]
	        	<li class="click" onclick="openUpdate()"><span><img src="${base}/statics/images/octopus/t02.png" /></span>修改</li>
	     	 [/@shiro.hasPermission]
	       	 [@shiro.hasPermission name="sys:menu:delete"]
	        	<li class="click" onclick="deleteMenu()"><span><img src="${base}/statics/images/octopus/t03.png" /></span>删除</li>
	         [/@shiro.hasPermission]
	        </ul>
	    </div>
    	 <input type='hidden' value='1' id='page'/>
	    <table class="tablelist" >
	    	<thead>
	    	<tr>
		        <th><input type="checkbox" id="selectAll"/></th>
		        <th>菜单ID</th>
		        <th>菜单名称</th>
		        <th>上级菜单</th>
		        <th>菜单图标</th>
		        <th>菜单URL</th>
		        <th>授权标识</th>
		        <th>类型</th>
		        <th>排序号</th>
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
            <form id="menuForm" method="post" class="form-horizontal">
            <input type="hidden" name="menuId"/>
				<div class="form-group">
					<div class="col-sm-2 control-label">类型</div> 
					<div class="col-sm-3">
						<label class="radio-inline">
						  <input type="radio" name="type" value="0" onclick="checkRadio('catalog')"/> 目录
						</label>
						<label class="radio-inline">
						  <input type="radio" name="type" value="1" onclick="checkRadio('menu')"/> 菜单
						</label>
						<label class="radio-inline">
						  <input type="radio" name="type" value="2" onclick="checkRadio('button')"/> 按钮
						</label>
					</div>
				</div>
				<div class="form-group">
				   	<div class="col-sm-2 control-label">菜单名称</div>
				   	<div class="col-sm-3">
				      <input type="text" name="name" class="form-control" placeholder="菜单名称或按钮名称"/>
				    </div>
				</div>
				<input type="hidden" name="menuId"/>
				<input type="hidden" name="parentId"/>
				<div class="form-group">
					<div class="col-sm-2 control-label">上级菜单</div>
				   	<div class="col-sm-3">
				       <input type="text" class="form-control" style="cursor:pointer;" name="parentName"  onclick="menuTree()" readonly="readonly" placeholder="一级菜单"/>
				    </div>
				</div>
				<div id="menuTable" style="display:none">
					<div class="form-group">
					   	<div class="col-sm-2 control-label">菜单URL</div>
					   	<div class="col-sm-3">
					      <input type="text" name="url" class="form-control"  placeholder="菜单URL"/>
					    </div>
					</div>
				</div>
				<div id="buttonTable" class="form-group">
					   	<div class="col-sm-2 control-label">授权标识</div>
					   	<div class="col-sm-3">
					      <input type="text" name="perms" class="form-control"  placeholder="多个用逗号分隔，如：user:list,user:create"/>
					    </div>
				</div>
				<div id="catalogTable">
					<div  class="form-group">
					   	<div class="col-sm-2 control-label">排序号</div>
					   	<div class="col-sm-3">
					      <input type="number" name="orderNum" class="form-control"  placeholder="排序号"/>
					    </div>
					</div>
					<div  class="form-group">
					   	<div class="col-sm-2 control-label">图标</div>
					   	<div class="col-sm-3">
					      <input type="text" name="icon" class="form-control"  placeholder="菜单图标"/>
					      <code style="margin-top:4px;display: block;">获取图标：http://fontawesome.io/icons/</code>
					    </div>
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
			<div id="menuLayer" style="display: none;padding:10px;">
				<ul id="menuTree" class="ztree"></ul>
			</div>
        </div>
        <!-- 选择菜单 -->
<script src="${base}/statics/js/octopus/sys/menu.js"></script>
</body>

</html>
