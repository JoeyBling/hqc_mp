<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>景区项目类型管理</title>
<link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
<link href="${base}/statics/css/octopus/styleCopy.css" rel="stylesheet" type="text/css" />
<link href="${base}/statics/plugins/summernote/summernote.css" rel="stylesheet" type="text/css"/>
<script src="${base}/statics/libs/jquery.min.js"></script>
<script src="${base}/statics/libs/bootstrap.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
<script src="${base}/statics/js/common.js"></script>
<script src="${base}/statics/libs/ajaxfileupload.js"></script>
<script src="${base}/statics/plugins/summernote/summernote.js"></script>
<script src="${base}/statics/plugins/summernote/summernote-zh-CN.js"></script>
<script src="${base}/statics/js/octopus/service/projectCategory.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">景区项目类型管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools"> 
            <div class="row">
        	<div class="col-xs-2">  
				 <input type="text" class="form-control" id="categoryName" placeholder="项目类型">
			 </div>
			           
            <ul class="toolbar">	
            [@shiro.hasPermission name="project:category:list"]
	    		<li class="click" onclick="queryList()"><span><img
                        src="${base}/statics/images/octopus/find024.png"></span>查询
                </li>
            [/@shiro.hasPermission]     
	        [@shiro.hasPermission name="project:category:save"]
                <li class="click" onclick="openAdd()" id="lia"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
                </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="project:category:update"]
                <li class="click" onclick="update()"><span><img src="${base}/statics/images/octopus/t02.png"/></span>修改
                </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="project:category:delete"]
                <li class="click" onclick="queryNext()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除
                </li>
            [/@shiro.hasPermission]
            </ul>
        </div>
 		</div>
    	<input type='hidden' value='1' id='page'/>
	    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input name="" type="checkbox" id="checkedAll" /></th>				        
				        <th>项目类型</th>				        
				        <th id="ths">是否系统内置分类</th>
				        <th>上级名称</th>				       		        
			        </tr>
		        </thead>
		        <tbody id="list" >
		        </tbody >
	      </table >
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
       
    <!----------添加---------->
    <div id="add" style="display:none">
        <form id="categoryForm" method="post" class="form-horizontal">
            <input type="hidden" name="id" id="id" class="form-control"/>
            <div class="form-group">
                <div class="col-sm-2 control-label">项目类型</div>
                <div class="col-sm-3">
                    <input type="text" id="categoryNames" name="categoryName" class="form-control" placeholder="项目类型"  onblur ="verify()" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 control-label">父类项目</div>
                <div class="col-sm-3">
                    <select name="categoryId" id="categoryId" class="form-control">
                    </select>

                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 control-label">是否系统内置分类</div>
                <div class="col-sm-3">
                    <label class="radio-inline">
                        <input type="radio" name="isSystem" id="normal" value="1"/> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="isSystem" id="disable" value="0"/> 否
                    </label>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
                    <input type="button" id="ok" class="btn btn-primary" onclick="saveOrUpdate()" value="确定"/>
                    &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>

                    <div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>