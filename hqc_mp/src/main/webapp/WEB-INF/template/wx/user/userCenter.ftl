<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 会员中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]

    <link href="${base}/statics/css/wx/user/user_center.css?v=21" rel="stylesheet" />
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
	<script src="${base}/statics/plugins/layui/layui.js"></script>
    <script type="text/javascript" src="${base}/statics/js/wx/user/user_center.js"></script>
    <style type="text/css">
        body {
            background: #F7F4F6;
        }
    </style>
</head>
<body>
	<div class="container">
        <div class="banner">
            <div class="leftContainer">
                <div class="userInfo">
                    <div class="avatar" id="avatar">
                        <img src="${base}/statics/images/wx/user/user_avatar.png" class="img-circle avatar-img" />
                    </div>
                    <div class="content">
                        <div class="userName" id="userName"></div>
                        <div class="userIntegral" id="userIntegral"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="sign" ><a style="color:white" href="${base}/wx/user/sign.html">签到赚积分</a></div>
            </div>
            <div class="rightContainer">
                <div class="memberLevel" id="memberLevel">
                    
                </div>
                <div class="memberCard" id="memberCard">会员卡号：</div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div id="divCtrl">    
            <a href="${base}/wx/order/order"><div class="myOrder">我的订单</div></a>
            <a href="${base}/wx/user/integralMall.html"><div class="integralMall">积分商城</div></a>
            <a href="javascript:;"><div class="clearfix"></div></a>
        </div>
        <!-- 菜单列表 -->
        <ul class="menuList">
            <a href="${base}/wx/user/integralRecordList.html"><li class="integralBg"><div class="title">积分商品兑换记录</div></li></a>
            <a href="${base}/wx/car/wxCarInfo.html"><li class="parkingBg"><div class="title">停车消费记录</div></li></a>
            <a href="${base}/wx/list/detail?type=1"><li class="tourBg"><div class="title">旅游攻略</div></li></a>
          
            <a href="${base}/wx/member/getView"><li class="personBg"><div class="title">个人信息</div></li></a>
            <a href="${base}/wx/list/detail?type=3 "><li class="aboutBg"><div class="title">关于华侨城</div></li></a>
            [#--<a href="javascript:void(0);" onclick="logout();"><li class="logout"><div class="title">退出登录</div></li></a>--]
        </ul>
    </div>
    <div id="signList" style="display:none"></div>
    <div class="am-modal am-modal-confirm" tabindex="-1" id="updateVip">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">会员升级</div>
    <div class="am-modal-bd">
   <div>   当前用户Vip等级可以提升</div>
       <div>  是否升级？</div>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-cancel>否</span>
      <span class="am-modal-btn" data-am-modal-confirm>是</span>
    </div>
  </div>
</div>

[#-- Model Loading --]
[#assign loadingId="my-modal-loading" ]
[#assign showLoading="正在载入..." ]
[#include "/wx/loading.ftl"]

[#-- Confirm UI --]
[#assign confirmId="my-confirm"]
[#assign confirmTitle="温馨提示"]
[#assign confirmContent="确定要退出登录吗"]
[#include "/wx/confirmUI.ftl"]

</body>
</html>
<script>
	function logout(){
		 $('#my-confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
			    location.href='${base}/wx/user/logout';
	        },
	        onCancel: function() {
	        }
	      });
	}
</script>