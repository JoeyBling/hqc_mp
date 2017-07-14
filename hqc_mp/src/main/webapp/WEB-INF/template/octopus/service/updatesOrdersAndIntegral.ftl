<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>代金券管理</title>
     <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
     <link href="${base}/statics/css/octopus/styleCopy.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${base}/statics/plugins/ztree/css/metroStyle/metroStyle.css">
    <link href="${base}/statics/plugins/summernote/summernote.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote-zh-CN.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>

    <style>
    </style>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">订单状态修改</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <div class="row">
                <div class="row col-xs-9">
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="itemCode" name="itemCode" placeholder="兑换码" />
                    </div>
                    
                    <div class="col-xs-5">
                        <div class="tools">
                            <ul class="toolbar">
	    					[@shiro.hasPermission name="orderUpdate:updatesOrdersAndIntegral:list"]
                              <li class="click" onclick="queryList()"><span><img src="${base}/statics/images/octopus/find024.png"
                              /></span>查询
                              </li>
                            [/@shiro.hasPermission]
                              <li class="click" onclick="resetQuery()"><span><img
                                 src="${base}/statics/images/octopus/reset024.png" /></span>重置
                              </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="display:none"><input type="checkbox" id="selectAll"/></th>
                <th>订单id</th>
                <th>商品名称</th>
                <th>兑换码</th>
                <th>兑换手机号</th>
                <th>订单编号</th>
                <th>订单状态 </th>
                <th>创建时间</th>
                <th>会员号</th>
                <th>会员名称</th>
                <th>订单有效期</th>
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
  
  
    	
    </div>
    <script src="${base}/statics/js/octopus/service/updatesOrdersAndIntegral.js"></script>
</body>
</html>