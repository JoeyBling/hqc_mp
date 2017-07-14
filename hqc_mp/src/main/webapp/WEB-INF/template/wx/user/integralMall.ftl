<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 积分商城</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
     
    <script src="${base}/statics/libs/jquery-2.1.1.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script> 
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/libs/jquery.mobile-1.0a4.1.min.js"></script>
  
  
    [#include "/wx/common_css.ftl"]
    <link href="${base}/statics/css/wx/integralMall/integralMall.css?v=12" rel="stylesheet" />
    [#include "/wx/common_js.ftl"]
    <style type="text/css">
        body {
            background: #F7F4F6;
            text-align: center;
        }
     

    

</style>

</head>
<body>
	<div class="container">
	  <div id="dataList">
        <!-- 顶部信息展示模块 -->
        <div class="topContainer" >
            <a href="${base}/wx/user/integralRecordList.html" class="lnk-record">兑换记录</a><span class="sp-score" id="Integerinfo">9000</span>&nbsp;积分
        </div>

        <!-- 幻灯片模块 begin -->
        <div class="carousel slide" data-ride="carousel">
            <!-- 幻灯控制圆点 -->
            <ol class="carousel-indicators">
                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>

            <!-- 幻灯图片列表 -->
            <div class="carousel-inner" role="listbox" id="picDiv">
         
            </div>
            <!-- 左右图片切换按钮 -->
            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <!-- 幻灯片模块 end -->

        <!-- 功能按钮 begin -->
        <div class="menuList">
            <ul>
                <li>
                    <a href="${base}/wx/user/memberIntegralrecord.html">
                        <b class="score-icon"></b>
                        <span>我的积分</span>
                    </a>
                </li>
                <li>
                    <a href="${base}/wx/user/sign.html">
                        <b class="sign-icon"></b>
                        <span>签到活动</span>
                    </a>
                </li>
                <li>
                    <a href="${base}/wx/user/integralRecordList.html">
                        <b class="record-icon"></b>
                        <span>兑换记录</span>
                    </a>
                </li>
                <li>
                    <a href="${base}/wx/user/integralRule.html">
                        <b class="rule-icon"></b>
                        <span>积分规则</span>
                    </a>
                </li>
            </ul>
            <div class="clearfix"></div>
        </div>
        <!-- 功能按钮 begin -->
        <div  class="tabbed_content">
            <div class="tabs">
               <div class="moving_bg">&nbsp;</div>
                 <span class="tab_item">兑换门票</span> <span class="tab_item">兑换代金卷</span> 
             
              </div>
             <div class="slide_content">
               <div class="tabslider" > 
                   <div class="dataList" id="tickData">
                      <ul id="goodsType2">  </ul>
                       <ul id="goodsType3">   </ul>
                     <div class="clearfix"></div>
				  </div>
          
              </div>
            </div>
            </div>
           
        </div>
		
       
        </div>
     



    <script type="text/javascript" src="${base}/statics/js/wx/user/integralMall.js"></script>
 
</body>
</html>
