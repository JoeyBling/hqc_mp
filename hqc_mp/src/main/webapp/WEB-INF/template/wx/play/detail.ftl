<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
	[#if entity.title?length gt 12][#assign pageName="${entity.title?substring(0, 12)}..."]
	[#else][#assign pageName="${entity.title}"][/#if]
    <title>${pageName!""}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/amazeui.min.js"></script>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--header 开始-->
[#include "/wx/header.ftl"]
<!--header 结束-->

<div style="clear: both; overflow: hidden; min-height: 500px; background-color: rgb(243, 243, 243);" class="pageplay2">
    <div style="font-size:25px; padding:10px 20px 10px 20px; color:#565656; font-weight:bold;">
        ${entity.title!""}
        <div style="font-size:16px; color:#c5c5c5; padding-top:5px;">发布时间：
		[#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
		[@formatTime unix="${entity.createTime?c}" pattern="yyyy-MM-dd HH:mm:ss"] [/@formatTime]</div>
    </div>
    <div style="color:#666; font-size:20px;padding:10px 20px 20px 20px;">${entity.content!""}
        <div style="text-align: center;">&nbsp;</div>
        <div style="text-align: right;">${entity.author!""}</div>
        <div>
        	<div style="float:left;margin-bottom:20px;font-size:15px;">阅读&nbsp;</div>
        	<div style="float:left;margin-bottom:20px;font-size:15px;">${entity.readCount?c!""}</div>
		    <div style="float:left;margin-bottom:20px;font-size:15px;" onclick="addlike(this)">&nbsp;&nbsp;&nbsp;<span id="flag" class="glyphicon glyphicon-thumbs-up"></span>&nbsp;</div>
		    <div style="float:left;margin-bottom:20px;font-size:15px;" id="likeCount" onclick="addlike(this)">${entity.likeCount?c!""}</div>
        	<input type="hidden" id="like" value="${entity.id}" />
        	<input type="hidden" name="likeCount" value="${entity.likeCount?c!''}" />
        </div>
    </div>

</div>

[#--是否显示页脚--]
[#assign showFooter=false]
[#--是否显示工具栏--]
[#assign showNavbar=true]
[#include "/wx/footer.ftl"]
</body>
<script type="text/javascript" src="${base}/statics/js/wx/play/detail.js"></script>
</html>