<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 签到活动</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
       [#include "/wx/common_css.ftl"]
   
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
      <link href="${base}/statics/css/wx/user/userSign.css?v=21" rel="stylesheet" />
      [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script type="text/javascript" src="${base}/statics/js/wx/user/userSign.js"></script>
    <style type="text/css">
        body {
            background: #F7F4F6;
        }
    </style>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--header 开始-->
[#assign pageName="签到活动" ]
[#include "/wx/header.ftl"]
<!--header 结束-->

[#-- Model Loading --]
[#assign loadingId="my-modal-loading" ]
[#assign showLoading="正在载入..." ]
[#include "/wx/loading.ftl"]

<div id="signList" ></div>
<div id="doSign">
	<button class="ui-btn-lg ui-btn-primary" onclick="doSign()" data-loading-text="加载中...">
                    签到
                </button>
</div>
[#--是否显示页脚--]
[#assign showFooter=false]
[#--是否显示工具栏--]
[#assign showNavbar=true]
[#include "/wx/footer.ftl"]
</body>
</html>
