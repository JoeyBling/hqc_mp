<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会员积分收支记录表</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">会员积分收支记录</a></li>
    </ul>
</div>


<div class="rightinfo" >
<div id="rightbody">
    <div class="tools">
             <div class="col-xs-2">
                        <select class="form-control" name="integralType" id="integralStauts">
                            <option value="0">状态</option>
                            <option value="1">进账</option>
                            <option value="2">出账</option>
                        </select>
                    </div>
        <ul class="toolbar">
         <li class="click" onclick="queryList(null,0)"><span><img
                                    src="/hqc_mp/statics/images/octopus/find024.png"></span>查询
                            </li>
        </ul>
    </div>

    <input type='hidden' value='1' id='page'/>
    <table class="tablelist">
        <thead>
        <tr>
            <th><input type="checkbox" id="selectAll"/></th>
            <th>id</th>
            <th>会员姓名</th>
            <th>积分异动数量</th>
            <th>状态</th>
            <th>积分变更说明</th>
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
<div id="add" style="display:none;margin-top:10px;">
    <form id="addForm" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-2 control-label">积分异动数量</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="integral" name="integral" placeholder="积分异动数量"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">积分变更说明</div>
            <div class="col-sm-3">
                <textarea class="form-control" name="about" id="about" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <input type="button" class="btn btn-primary" onclick="addRecord(this)" value="确定"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="integralRecord(this)" value="返回"/>
        </div>
    </form>
</div>

<div id="update" style="display:none;margin-top:10px;">
    <form id="updateForm" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-2 control-label">积分异动数量</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="integral" name="integral" placeholder="积分异动数量"/>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 control-label">积分变更说明</div>
            <div class="col-sm-3">
                <textarea class="form-control" name="about" id="about" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">积分变更类型</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="integralType" name="integralType" placeholder="积分变更说明"/>
            </div>
        </div>
        <input type="hidden" class="form-control" id="id" name="id" placeholder="id"/>

        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <input type="button" class="btn btn-primary" onclick="updateRecord(this)" value="确定"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="dumpRecord(this)" value="返回"/>
        </div>
    </form>
</div>
<script src="${base}/statics/js/octopus/service/mpIntegralRecord.js"></script>
</body>
</html>