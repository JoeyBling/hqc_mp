<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>520活动列表</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">活动管理</a></li>
        <li><a href="javascript:void(0);">配对活动</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
                <div class="row">
                    <div class="col-xs-3">
                        <input type="text" class="form-control" id="manNickName" placeholder="男方微信昵称">
                    </div>
                    <div class="col-xs-3">
                        <input type="text" class="form-control" id="womanNickName" placeholder="女方微信昵称">
                    </div>
                    <div class="col-xs-3">
                        <input type="text" class="form-control" id="randomNumber" placeholder="活动生成号码">
                    </div>
	    		 	[@shiro.hasPermission name="active:520:list"]
	                    	<li class="click" onclick="queryList()"><span><img src="${base}/statics/images/octopus/find024.png"></span>查询</li>
                    [/@shiro.hasPermission]
                </div>
            </ul>
        </div>

        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th>序号</th>
                <th>男方微信昵称</th>
                <th>女方微信昵称</th>
                <th>活动生成号码</th>
                <th>生成时间</th>
            </tr>
            </thead>
            <tbody id="list">
            </tbody>
        </table>
        <div class="pagin">
            <div class="message">共<i class="total"></i>条记录，&nbsp&nbsp共<i class="pagetoal"></i>页&nbsp&nbsp当前显示第&nbsp;<i
                    class="page" style="margin-left:-0.1px"></i>页&nbsp&nbsp每页显示&nbsp&nbsp
                <select onchange="queryList()" style="border:1px solid  black" id="limit">
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
<script src="${base}/statics/js/octopus/activity/mp520.js"></script>
</body>
</html>