<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>图文信息分类管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">图文信息分类管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
		        [@shiro.hasPermission name="article:category:save"]
	                <li class="click" onclick="openAdd()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	                </li>[/@shiro.hasPermission]
		        [@shiro.hasPermission name="article:category:update"]
	                <li class="click" onclick="openUpdate()"><span><img
	                        src="${base}/statics/images/octopus/t02.png"/></span>修改
	                </li>
	            [/@shiro.hasPermission]
		        [@shiro.hasPermission name="article:category:delete"]
	                <li class="click" onclick="deleteIMK()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除
	                </li>
                [/@shiro.hasPermission]
            </ul>
        </div>

        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>分类名称</th>
                <th>分类类别</th>
                 <th>上级类别名称</th>
                <th>权限类型</th>
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
        <form id="autoReply" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2 control-label">分类级别</div>
                <div class="col-sm-3">
                    <label class="radio-inline">
                        <input type="radio" name="type" id="mulu" value="0" onclick="checkRadio('catalog')"/> 一级分类
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="type" id="menu" value="1" onclick="checkRadio('menu')"/> 二级分类
                    </label>
                </div>
            </div>
            <input type="hidden" class="form-control" name="id" id="id"/>
		
            <div class="form-group">
                <div class="col-sm-2 control-label">分类名称</div>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="categoryName" name="categoryName" placeholder="分类名称"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">文章类别种类</div>
                <div class="col-sm-2">
                	 <label class="radio-inline">
                        <input type="radio" name="categoryType" id="categoryType1" value="1" /> 栏目页
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="categoryType" id="categoryType2" value="2" /> 单页
                    </label>
                </div>
            </div>
            <div class="form-group" id="hidden" style="display:none">
                <div class="col-sm-2 control-label">上级类别</div>
                <div class="col-sm-2">
                    <select class="form-control" id="parentId" name="parentId">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-6">
                    <input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="确定"/>
                    &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
                </div>
            </div>
        </form>
    </div>
    <script src="${base}/statics/js/octopus/service/infomationKind.js"></script>
</body>
</html>