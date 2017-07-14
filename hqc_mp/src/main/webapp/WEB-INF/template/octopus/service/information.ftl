<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>信息资讯管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/styleCopy.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/statics/plugins/summernote/summernote.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote-zh-CN.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">信息资讯管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <div class="row">
            <div class="col-xs-2">
                <input type="text" class="form-control" id="author" placeholder="作者">
            </div>
            <div class="col-xs-2">
                <input type="text" class="form-control" id="title" placeholder="标题">
            </div>
            <div class="col-xs-2">
                <input type="text" class="form-control" id="categoryName" placeholder="资讯分类">
            </div>
            <ul class="toolbar">
	    	[@shiro.hasPermission name="article:info:list"]
                <li class="click" onclick="queryList(null,0)"><span><img
                        src="${base}/statics/images/octopus/find024.png"/></span>查询
                </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="article:info:save"]
                <li class="click" onclick="openAdd()"><span><img
                        src="${base}/statics/images/octopus/t01.png"/></span>添加
                </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="article:info:update"]
                <li class="click" onclick="openUpdate()"><span><img
                        src="${base}/statics/images/octopus/t02.png"/></span>修改
                </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="article:info:delete"]
                <li class="click" onclick="del()"><span><img
                        src="${base}/statics/images/octopus/t03.png"/></span>删除
                </li>
            [/@shiro.hasPermission]
            </ul>
        </div>
        </div>

        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>作者</th>
                <th>标题</th>
                <th>点赞数量</th>
                <th>阅读量</th>
                <th>来源</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>资讯分类</th>
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
        <form id="inform" class="form-horizontal">
            <input type="hidden" class="form-control" name="id" id="id"/>
            <div class="form-group">
                <div class="col-sm-2 control-label">信息类别</div>
                <div class="col-sm-6">
                    <select class="form-control" name="categoryId" id="categoryId">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">标题</div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="titled" placeholder="标题" id="titled"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">作者</div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="authord" placeholder="作者" id="authord"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">来源</div>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="comeFrom" placeholder="来源" id="comeFrom"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">资讯缩略图</div>
                <div class="col-sm-6">
                    <input type='hidden' name='thumb' class='form-control' id="thumb"/>
                    <input type="file" class="form-control" onchange="upload(this)" name="pic" id="pic" value=""/>
                    <input type="hidden" id="thumbUrl" name="thumbUrl"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3" id="thumbf"></div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">资讯内容</div>
                <div class="col-sm-6">
                    <div id="content" name="content"></div>
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
    <script src="${base}/statics/js/octopus/service/information.js"></script>
</body>
<script type="text/javascript">
    //加载编辑器  
    $(document).ready(function () {
        $('#content').summernote({
            height: 400,
            minHeight: 300,
            maxHeight: 500,
            focus: true,
            lang: 'zh-CN',
            // 重写图片上传  
            callbacks: {
                onImageUpload: function (files) { //the onImageUpload API  
                    img = sendFile(files[0]);
                }
            }
        });
    });

    function sendFile(file) {
        var data = new FormData();
        data.append("file", file);
        data.append("name", "file");
        data.append("uploadType", "2");
        console.log(data);
        $.ajax({
            data: data,
            type: "POST",
            url: "${base}/file/upload",
            cache: false,
            contentType: false,
            processData: false,
            success: function (result) {
                $("#content").summernote('insertImage', "${base}/" + result.filePath, 'img'); // the insertImage API 
            }
        });
    }
</script>
</html>