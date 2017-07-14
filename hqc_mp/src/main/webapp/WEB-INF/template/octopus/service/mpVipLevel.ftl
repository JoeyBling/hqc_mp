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
        <li><a href="javascript:void(0);">会员等级管理</a></li>
    </ul>
</div>

<div class="rightinfo" >
<div id="rightbody">
    <div class="tools">
     <div class="col-xs-2">
                <input type="text" class="form-control" id="mpVipName" name="mpVipName" placeholder="请输入会员等级"/>
            </div>
        <ul class="toolbar">
        [@shiro.hasPermission name="member:member:list"]
		          <li class="click" onclick="queryList(null,0)"><span><img
		                                    src="/hqc_mp/statics/images/octopus/find024.png"></span>查询
		                            </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="member:level:save"]
	            <li class="click" onclick="addMpVipLevel()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	            </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="member:level:update"]
	            <li class="click" onclick="UpdateMpVipLevel()"><span><img
	                    src="${base}/statics/images/octopus/t02.png"/></span>修改
	            </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="member:level:delete"]
	            <li class="click" onclick="deleteMpVipLevel()"><span><img
	                    src="${base}/statics/images/octopus/t03.png"/></span>删除
	            </li>
            [/@shiro.hasPermission]
        </ul>
    </div>

    <input type='hidden' value='1' id='page'/>
    <table class="tablelist">
        <thead>
        <tr>
            <th><input type="checkbox" id="selectAll"/></th>
            <th>id</th>
            <th>等级名称</th>
            <th>最小积分</th>
            <th>最大积分</th>
            <th>常规日积分比例</th>
            <th>特别日积分比例</th>
            <th>积分系数</th>
            <th>描述</th>
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
<div id="add" style="display:none;margin-top:10px;">
    <form id="addForm" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-2 control-label">等级名称</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" onblur="openLevel(this)" id="vipName" name="vipName"
                       placeholder="等级名称"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 control-label">最小积分</div>
            <div class="col-sm-3">
                <input type="number" step="100" class="form-control" id="minIntegral" name="minIntegral" placeholder="最小积分"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 control-label">最大积分</div>
            <div class="col-sm-3">
                <input type="number" step="100" class="form-control" id="maxIntegral" name="maxIntegral" placeholder="最大积分"/>
            </div>
        </div>
		 <div class="form-group">
            <div class="col-sm-2 control-label">常规日积分</div>
            <div class="col-sm-3">
                <input type="number" step="0.1" class="form-control" id="normalIntegralRule" name="normalIntegralRule" placeholder="常规日积分"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">特别日积分</div>
            <div class="col-sm-3">
                <input type="number" step="0.1" class="form-control" id="specialIntegralRule" name="specialIntegralRule" placeholder="特别日积分"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">积分系数</div>
            <div class="col-sm-3">
                <input type="number" step="0.1" class="form-control" id="integralCoefficient" name="integralCoefficient" placeholder="积分系数"/>
            </div>
        </div>
          <div class="form-group">
            <div class="col-sm-2 control-label">会员等级图标</div>
            <div class="col-sm-3">
                <input type="file" id="pic" onchange="upload(this)" name="pic" class="form-control"/>
            </div>
            <input type="hidden" id="iconUrl" name="iconUrl" value="">
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-3" id="iconImg">
               
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">描述</div>
            <div class="col-sm-3">
                <textarea class="form-control" name="about" id="about" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="确定"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
        </div>
          <input type="hidden" class="form-control" id="id" name="id"/>
    </form>
</div>

<script src="${base}/statics/js/octopus/service/mpVipLevel.js"></script>
</body>
</html>