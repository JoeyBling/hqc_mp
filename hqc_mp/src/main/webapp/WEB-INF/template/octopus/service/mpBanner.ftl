<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会员等级管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
     <script src="${base}/statics/js/common.js"></script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">横幅图片管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
        	 <div class="row">
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="qtitle" placeholder="横幅标题">
                    </div>
                    <div class="col-xs-2">
                        <select class="form-control" name="qstatus" id="qstatus">
                            <option value=0>状态</option>
                            <option value=1>启用</option>
                            <option value=2>禁用</option>  
                        </select>
                    </div>
                
        	 
        	 
        	 
	            <ul class="toolbar">
	             		[@shiro.hasPermission name="banner:banner:list"]
	           			 <li class="click" onclick="queryList(null,0)"><span><img
                                    src="${base}/statics/images/octopus/find024.png"></span>查询
                        </li>
                        [/@shiro.hasPermission]
                        <li class="click" onclick="resetQuery()"><span><img
                                    src="${base}/statics/images/octopus/reset024.png"></span>重置
                        </li>
                         [@shiro.hasPermission name="banner:banner:add"]
	                 	<li class="click" onclick="openAdd()"><span><img 
	                 		src="${base}/statics/images/octopus/t01.png"/></span>添加</li>
	                 		 [/@shiro.hasPermission]
	                 		 [@shiro.hasPermission name="banner:banner:update"]
	                 	<li class="click" onclick="openUpdate()"><span><img
	                        src="${base}/statics/images/octopus/t02.png"/></span>修改</li>
	                         [/@shiro.hasPermission]
	                         [@shiro.hasPermission name="banner:banner:del"]
	                 	<li class="click" onclick="del()"><span><img
	                        src="${base}/statics/images/octopus/t03.png"/></span>删除</li>
	                         [/@shiro.hasPermission]
	            </ul>
	    	</div> 
        </div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>横幅标题</th>
                <th>横幅图片指向地址</th>
                <th>状态</th>
                 <th>操作</th>
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
        <form id="myform" method="post" class="form-horizontal">
            <input type="hidden" name="id" id="id"/>

            <div class="form-group">
                <div class="col-sm-2 control-label">横幅标题</div>
                <div class="col-sm-3">
                    <input type="text" name="title" id="title" class="form-control" placeholder="横幅标题"/>
                </div>
            </div>
             <div class="form-group">
            <div class="col-sm-2 control-label">图片上传</div>
            <div class="col-sm-3">
                <input type="file" id="pic" onchange="upload(this)" name="pic" class="form-control"/>
            </div>
          
            <input type="hidden" id="thumbUrl" name="thumbUrl" value="">
        </div>
		<div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-3" id="bannerImg">
            </div>
        </div>
        <div class="form-group">
                <div class="col-sm-2 control-label">链接地址</div>
                <div class="col-sm-3">
                    <input type="text" name="url" id="url" class="form-control" placeholder="链接地址"/>
                </div>
            </div>
             <div class="form-group">
                <div class="col-sm-2 control-label">状态</div>
	                <div class="col-sm-3">
	                   <label class="radio-inline">
	                       <input type="radio" id="disable" name="status" value="1"/> 启用
	                   </label>
	                   <label class="radio-inline">
	                       <input type="radio" id="normal" name="status" value="2"/> 停用
	                   </label>
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
    <script src="${base}/statics/js/octopus/service/mpBanner.js"></script>

</body>
</html>