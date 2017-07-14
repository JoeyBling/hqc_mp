<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>公众号菜单管理</title>
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
        <li><a href="javascript:void(0);">公众号管理</a></li>
        <li><a href="javascript:void(0);">公众号菜单管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
                [@shiro.hasPermission name="mp:menu:save"]
	                <li class="click" onclick="openAddMpMenu()"><span><img
	                        src="${base}/statics/images/octopus/t01.png"/></span>添加
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:menu:save"]
	                <li class="click" onclick="openUpdate()"><span><img
	                        src="${base}/statics/images/octopus/t02.png"/></span>修改
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:menu:delete"]
	                <li class="click" onclick="del()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除
	                </li>
                [/@shiro.hasPermission]
            </ul>
        </div>

        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>菜单序号</th>
                <th>菜单名称</th>
                <th>菜单内容</th>
                <th>是否含有子菜单</th>
                <th>子菜单数</th>
            </tr>
            </thead>
            <tbody id="list">

            </tbody>
        </table>
    </div>

    <div id="add" style="display:none">
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-2">
                    <a href="javascript:void(0)" onclick="addNewMenu()" style="color:blue">添加新的菜单栏</a>
                </div>
            </div>
            <div id="mpmenulist">
                <div class="form-group">
                    <div class="col-sm-2 control-label">菜单名称</div>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="Menuname" placeholder="菜单名称"/>
                    </div>

                </div>
                <div id="showMenu"></div>
                <div id="showfristMenu"></div>
                <div id="addfristmenu"></div>
            </div>
            <div id="Menulist"></div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
                    <input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="保存并发布"/>
                    &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
                </div>
            </div>
        </form>
    </div>
    <div>
        <form class="form-horizontal">
            <div id="updateList">
            </div>
        </form>
    </div>
    <form id="" class="form-horizontal">

    </form>
    <div id="showChildMenu" style="display:none">
        <table id="showtable" class="table table-condensed">
            <thead>
            <tr>
                <td>子菜单序号</td>
                <td>子菜单名称</td>
                <td>所属菜单</td>
                <td>子菜单内容</td>
            </tr>
            </thead>
            <tbody id="childMenulist">
            </tbody>
        </table>
    </div>
    <script src="${base}/statics/js/octopus/mp/mpMenu.js"></script>
</body>
</html>