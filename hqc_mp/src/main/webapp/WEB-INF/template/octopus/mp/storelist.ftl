<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>门店管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/libs/getpoint.js"></script>
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
        <li><a href="javascript:void(0);">门店管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
                [@shiro.hasPermission name="mp:store:save"]
	                <li class="click" onclick="add()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:store:update"]
	                <li class="click" onclick="update()"><span><img src="${base}/statics/images/octopus/t02.png"/></span>修改
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:store:delete"]
                	<li onclick="del()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除</li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:store:syn"]
                	<li onclick="syn()"><span><img src="${base}/statics/images/octopus/resizeApi.png"/></span>同步数据</li>
                [/@shiro.hasPermission]
            </ul>
        </div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>门店名称</th>
                <th>分店名称</th>
                <th>类目</th>
                <th>联系电话</th>
                <th>地址</th>
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
        <form id="storeForm" class="form-horizontal">
            <input type="hidden" class="form-control" name="poiId" id="poiId"/>

            <div class="form-group">
                <div class="col-sm-2 control-label">门店名称</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="businessName" placeholder="门店名称"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">分店名称</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="branchName" placeholder="分店名称"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">类目</div>
                <div class="col-sm-3">
                    <select name="cateTypes" id="cateTypes" class="form-control">

                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">联系电话</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="telephone" placeholder="联系电话"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">地址</div>
                <div id="" class="col-sm-6">
                    <input type="text" class="form-control" style="width:21%;display:inline;" name="province"
                           placeholder="广东省"/>省
                    <input type="text" class="form-control" style="width:21%;display:inline;" name="city"
                           placeholder="深圳市"/>市
                    <input type="text" class="form-control" style="width:21%;display:inline;" name="district"
                           placeholder="盐田区"/>县/区
                    <input type="text" class="form-control" style="width:21%;display:inline;" name="address"
                           placeholder="街道"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">坐标</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="location"
                           readonly="readonly"/>
                    <div id="container"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
                    <input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="确定"/>
                    &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
                </div>
            </div>
        </form>
    </div>
    <script src="${base}/statics/js/octopus/mp/store.js"></script>
</body>
</html>