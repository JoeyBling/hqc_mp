<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 积分商品兑换记录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <link rel="stylesheet" href="${base}/statics/css/dropload.css" />
     <link href="${base}/statics/css/wx/user/integralRecordList.css?v=21" rel="stylesheet" />
    [#include "/wx/common_js.ftl"]
     <script src="${base}/statics/plugins/layer/layer.js"></script>
	<script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    [#-- 上拉刷新下拉加载更多 --]
        <script  src="${base}/statics/libs/dropload.min.js"></script>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--header 开始-->
[#assign pageName="积分商品兑换记录" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
	<ul class="am-nav am-nav-tabs">
	  <li id="ticket" class="am-active"><a href="${base}/wx/user/integralRecordList.html?c=2">门票</a></li>
	  <li id="coupon"><a href="${base}/wx/user/integralRecordList.html?c=1">代金券</a></li>
	</ul>
	<div id="recordList" class="recordList" style=" background-color:#f3f3f3; overflow:hidden;"></div>
	<input type="hidden" id="page" value="1"/>
	<input type="hidden" id="goodsType" value="2"/>
[#--是否显示页脚--]
[#assign showFooter=false]
[#--是否显示工具栏--]
[#assign showNavbar=true]
[#include "/wx/footer.ftl"]
</body>
  <script type="text/javascript" src="${base}/statics/js/wx/user/integralRecordList.js"></script>
</html>