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
     <link href="${base}/statics/css/wx/integralMall/integralRule.css?v=21" rel="stylesheet" />
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

[#assign pageName="积分规则" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
	
	<div id="recordList" class="container">
	   <div class="form-group col-xs-12 titleInfo" >积分获取聚到</div>
	   <div class="form-group col-xs-12 conInfo">1、购买门票可得积分.</div>
	   <div class="form-group col-xs-12 conInfo">2、积分商城签到可得积分.</div>
	   <div class="form-group col-xs-12 btnInfo">3、参与互动获赠积分。</div>
	   <div class="form-group col-xs-12 titleInfo">积分兑换设置</div>
	   <div class="form-group col-xs-12 conInfo">1、积分兑换代金券，购买门票时可立减；</div>
	   <div class="form-group col-xs-12 conInfo">2、积分可直接兑换免费门票；</div>
	   <div class="form-group col-xs-12 btnInfo">3、积分可兑换相应的商品及纪念品。</div>
	     <div class="form-group col-xs-12 titleInfo">积分兑换注意事项</div>
	   <div class="form-group col-xs-12 conInfo">1、会员卡内的积分不能转赠、合并；每兑换一次礼品卡内积分减自动扣减相应分值数额，一经兑换，礼品不可退换.</div>
	 
	   <div class="form-group col-xs-12 conInfo">2、积分越高，奖品越丰厚，累计的积分在次年12月31日前兑换有效，积分过期不兑换，视为自动放弃，当年的积分系统在12月31日清零。
	        达到一定的积分进款请在积分有效期内兑换；兑换礼品种类和所需的分值以现场公告为准。
	   </div>
	    <div class="form-group col-xs-12 conInfo">3、兑换的优惠券和门票使用时不参与积分累计。</div>
	    <div class="form-group col-xs-12 conInfo">4、积分奖品及奖项的设置由东部华侨城确定，每期礼品和分值可能会有所不同，以现场或东部华侨城官方网站公布的规则为准。</div>
	    <div class="form-group col-xs-12 btnInfo">5、兑换的代金券必须在一个月内使用，如不使用的话一个月后就过期了，最多只能使用一张；</div>
	</div>
[#--是否显示页脚--]
[#assign showFooter=false]
[#--是否显示工具栏--]
[#assign showNavbar=true]
[#include "/wx/footer.ftl"]
</body>
  
</html>