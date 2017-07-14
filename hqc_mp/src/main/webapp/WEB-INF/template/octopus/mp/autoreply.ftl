<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>自动回复管理</title>
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
        <li><a href="javascript:void(0);">自动回复管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <ul class="toolbar">
                [@shiro.hasPermission name="mp:autoreply:save"]
	                <li class="click" onclick="add()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:autoreply:update"]
	                <li class="click" onclick="update()"><span><img src="${base}/statics/images/octopus/t02.png"/></span>修改
	                </li>
                [/@shiro.hasPermission]
                [@shiro.hasPermission name="mp:autoreply:delete"]
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
                <th>响应类型</th>
                <th>回复类型</th>
                <th>回复内容</th>
                <th>媒体ID</th>
                <th>关键词</th>
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
</div>
</div>

<div id="add" style="display:none">
    <form id="autoReply" class="form-horizontal">
        <input type="hidden" class="form-control" name="id" id="id"/>

        <div class="form-group">
            <div class="col-sm-2 control-label">响应类型</div>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input type="radio" name="responseType" id="subscribe" onclick="javascript:$('#keywords').hide();"
                           value="1"/>关注公众号
                </label>
                <label class="radio-inline">
                    <input type="radio" name="responseType" id="tagsReply" onclick="javascript:$('#keywords').show();"
                           value="2" checked="checked"/>关键词回复
                </label>
            </div>
        </div>
        <div class="form-group" id='keywords'>
            <div class="col-sm-2 control-label">关键词</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="keywords" placeholder="(多个使用英文逗号隔开)"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">回复类型</div>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input type="radio" name="replyType"
                           onclick="javascript:$('#mediaId').hide();$('#replyText').show();" id='text' value="1"
                           checked="checked"/>文字
                </label>
                <label class="radio-inline">
                    <input type="radio" name="replyType"
                           onclick="javascript:$('#replyText').hide();$('#mediaId').show();" id="image" value="2"/>图片
                </label>
                <label class="radio-inline">
                    <input type="radio" name="replyType"
                           onclick="javascript:$('#replyText').hide();$('#mediaId').show();" id="voice" value="3"/>语音
                </label>
                <label class="radio-inline">
                    <input type="radio" name="replyType"
                           onclick="javascript:$('#replyText').hide();$('#mediaId').show();" id="movie" value="4"/>视频
                </label>
                <label class="radio-inline">
                    <input type="radio" name="replyType"
                           onclick="javascript:$('#replyText').hide();$('#mediaId').show();" id="graphic" value="5"/>图文
                </label>
            </div>
        </div>
        <div class="form-group" id='replyText'>
            <div class="col-sm-2 control-label">回复内容</div>
            <div class="col-sm-3">
				   		<textarea class="form-control" rows="10" id='replyTextForm' cols="20" name="replyText"
                                  placeholder="回复内容">
				   		
				   		</textarea>
            </div>
        </div>
        <div class="form-group" id='mediaId'>
            <div class="col-sm-2 control-label">媒体ID</div>
            <div class="col-sm-3">
                <input type="text" onclick="showMedia()" class="form-control" name="mediaId" readonly="readonly"
                       placeholder=""/>
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
<script src="${base}/statics/js/octopus/mp/autoreply.js"></script>
</body>
</html>