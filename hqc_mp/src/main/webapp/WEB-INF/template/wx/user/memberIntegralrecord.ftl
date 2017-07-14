<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 我的积分</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <link rel="stylesheet" href="${base}/statics/css/dropload.css" />
     <link href="${base}/statics/css/wx/integralMall/memberIntegralrecord.css?v=12" rel="stylesheet" />
    [#include "/wx/common_js.ftl"]
     <script src="${base}/statics/plugins/layer/layer.js"></script>
	<script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    [#-- 上拉刷新下拉加载更多 --]
        <script  src="${base}/statics/libs/dropload.min.js"></script>
        <script src="${base}/statics/js/common.js"></script>
</head>
<body>
 [#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]

[#assign pageName="我的积分" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
    
     <div class="titleRecord col-xs-12">
          <ul class="row">
             <li class="titlefirst">时间</li>
             <li class="titleSecond">类型</li>
             <li class="titleThird">数量</li>
             <li class="titleLast">备注</li>
          </ul>
    
     </div>
	<div id="recordList" class="recordList" >
	   
	</div>
	<input type="hidden" id="page" value="1"/>
[#--是否显示页脚--]
[#assign showFooter=false]
[#--是否显示工具栏--]
[#assign showNavbar=true]
[#include "/wx/footer.ftl"]
</body>
    <script type="text/javascript" src="${base}/statics/js/wx/user/memberIntegralrecord.js"></script>
</html>