<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Wi-Fi设备管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <style>
        #container {
            width: 621px;
            height: 520px;
            border: 5px solid #fff;
        }
    </style>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">公众号管理</a></li>
        <li><a href="javascript:void(0);">Wi-Fi设备管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
                [@shiro.hasPermission name="mp:wifi:save"]
	                <li class="click" onclick="add()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:wifi:delete"]
                	<li onclick="del()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除</li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:wifi:syn"]
                	<li onclick="syn()"><span><img src="${base}/statics/images/octopus/resizeApi.png"/></span>同步数据</li>
                [/@shiro.hasPermission]
            </ul>
        </div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>门店Id</th>
                <th>连网设备ssid</th>
                <th>无线MAC地址</th>
                <th>设备类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="list">
            </tbody>
        </table>
        <div class="pagin">
            <div class="message">共<i class="total"></i>条记录，&nbsp&nbsp共<i class="pagetoal"></i>页&nbsp&nbsp当前显示第&nbsp;<i
                    class="page" style="margin-left:-0.1px"></i>页
            </div>
            <ul class="paginList" style="margin-right:300px">
            </ul>
        </div>
    </div>

    <div id="add" style="display:none">
        <form id="wifi" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2 control-label">门店名称</div>
                <div class="col-sm-3">
                    <input type="text" onclick="showBu(this)" readonly="readonly" class="form-control" name="businessName" placeholder="门店名称"/>
               		<input type="hidden" name="shopId"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">设备类型</div>
                <div class="col-sm-3">
                	<label class="radio-inline">
                   		<input type="radio" name="protocolType" value="4" checked="checked" /> 密码型设备
					</label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">网络名(SSID)</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="ssid" placeholder="必须以大写字母“WX”开头，示例： WXtencent"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">设备密码</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="password" placeholder="设备密码"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
                    <input type="button" class="btn btn-primary" onclick="save(this)" value="确定"/>
                    &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
                </div>
            </div>
        </form>
    </div>
    
    [#-- 选择微信门店 --]
    <div id="showBu" style="display:none;">
        	<table class="tablelist" style="padding:5px;">
            <thead>
            <tr>
                <th>门店名称</th>
            </tr>
            </thead>
            <tbody id="buList">
            </tbody>
        </table>
    </div>
    <script src="${base}/statics/js/octopus/mp/wifi.js"></script>
</body>
</html>