<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>商品兑换管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap-datetimepicker.min.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/plugins/ztree/jquery.ztree.all.min.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/bootstrap-datetimepicker.min.js"></script>
    <script src="${base}/statics/libs/bootstrap-datetimepicker.zh-CN.js"></script>
    <style>
    </style>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">商品兑换管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <div class="row">
                <div class="row col-xs-8">
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="personPhone"  name="personPhone" placeholder="手机号码" />
                    </div>
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="goodsName" name="goodsName"  placeholder="兑换商品名称" />
                    </div>
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="trueName" name="trueName" placeholder="会员名称" />
                    </div>
                    <div class="col-xs-2">
                        <select class="form-control" name="useStatus" id="useStatus">
                            <option value=''>使用状态</option>
                            <option value='1'>已使用</option>
                            <option value='0'>未使用</option>
                        </select>
                    </div>
                    <div class="col-xs-3">
                        <div class="tools">
                            <ul class="toolbar">
	    					[@shiro.hasPermission name="goods:exchange:list"]
                                <li class="click" onclick="queryList()"><span><img
                                        src="${base}/statics/images/octopus/find024.png"></span>查询
                                </li>
	    					[/@shiro.hasPermission]
                                <li class="click" onclick="resetQuery()"><span><img
                                        src="${base}/statics/images/octopus/reset024.png"></span>重置
                                </li>
	    					[@shiro.hasPermission name="goods:exchange:delete"]
                                <li class="click" onclick="delGoodsExchange()"><span><img
                                        src="${base}/statics/images/octopus/t03.png"></span>删除
                                </li>
                            [/@shiro.hasPermission]
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
                <th><input type="checkbox" id="selectAll"/></th>
                <th>会员姓名</th>
                <th>兑换商品类型</th>
                <th>兑换商品名称</th>
                <th>兑换时间</th>
                <th>兑换码</th>
                <th>兑换数量</th>
                <th>兑换所用积分</th>
                <th>兑换人手机号码</th>
                <th>使用状态</th>
                <th>使用日期</th>
                
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
    <script src="${base}/statics/js/octopus/service/exchange.js"></script>
</body>
</html>