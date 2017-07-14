<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
<meta charset="UTF-8">
<title>${hqc}- 积分商城</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
   <link rel="stylesheet" href="${base}/statics/css/wx/ticket/orderpay.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
<link rel="stylesheet" href="${base}/statics/css/amazeui.min.css"/>
<link rel="stylesheet" href="${base}/statics/css/wx/ticket/lCalendar.css"/>
<script src="${base}/statics/libs/jquery-2.1.1.min.js"></script>
<script src="${base}/statics/libs/bootstrap.min.js"></script>
<script src="${base}/statics/js/common.js"></script>
<script src="${base}/statics/libs/amazeui.min.js"></script>
<script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
<script src="${base}/statics/libs/jquery.mobile-1.0a4.1.min.js"></script>
<script src="${base}/statics/js/wx/ticket/lCalendar.js"></script>


[#include "/wx/common_css.ftl"]
<link href="${base}/statics/css/wx/integralMall/integralMallExchange.css?v=12"
	rel="stylesheet" />
[#include "/wx/common_js.ftl"]
<style type="text/css">
body {
	background: #F7F4F6;
	text-align: center;
}
</style>

</head>
<body onload="changeSpanColor()">

		<div id="dataList" class="container">
		
        <div id="exchange" >
     
         <!--header 结束-->
        <form id="categoryForm" method="post" class="form-horizontal">
            <input type="hidden" name="goodsId" id="goodsId" value="${goodsId}"/>
            <input type="hidden" name="daysLimit" id="daysLimit"/>
            <input type="hidden" name="endTime" id="endTime"/>
            <input type="hidden" name="repertory" id="repertory"/>
            <input type="hidden" name="integral" id="integral"/>
            <input type="hidden" name="maxExchange" id="maxExchange"/>
            <input type="hidden" name="dayExchange" id="dayExchange"/>
            <input type="hidden" name="exchangeCount" id="exchangeCount"/>
            <input type="hidden" name="finalIntegral" id="finalIntegral" />
            <input type="hidden" name="type" id="type"  value="${type}"/>
             <input type="hidden" name="tempDate" id="tempDate" />
             
            <div class="goodsInfoDiv">
               <div class="form-group goodsInfoTitle" style="margin-right:0px">
                  <div class="col-xs-6 control-label goodsInfoNames" id="goodsName" ></div>
                  <div class="col-xs-6 control-label integralInfo"  id="integralDiv"></div>
                  
                  <div class="topborder"> </div>
              </div>
               <div class="form-group goodsInfoTitle" id="dateDiv" style="margin-right:0px">
                 <div class="col-xs-4 control-label chooseTime"  >选择时间</div>
                <div class="col-xs-8 dateDiv"  id="inner-content" >
                   <span class="date-item" onclick="gettomorrowValue()" >
                      <z class="morroInfo" onclick="gettomorrowValue()">明天</z>
                      <z class="morroDate" id="tomorrow"  onclick="gettomorrowValue()"></z>
                       <input type="hidden"  id="tomorrowValue"/>
                   </span>
                     <span class="date-item" onclick="getafterTomorrowValue()" id="afterTomorrowSpan">
                        <z class="morroInfo" onclick="getafterTomorrowValue()">后天</z>
                        <z class="morroDate" id="afterTomorrow" onclick="getafterTomorrowValue()"></z>
                        <input type="hidden"  id="afterTomorrowValue"/>
                    </span>
                     <layer  id="afterMoreSpan" style="width:30%;float:right;margin:0px;padding:0px;">
                   <z  ><span class="date-item"   id="openDate" >更多日期</span></z>
                    </layer>
                 <div><em style="font-size:5px ;font-weight:normal" id="checkDate"></em></div>

                </div>
             </div>
              <div class="form-group addOrminiuteInfo" style="margin-right:0px">
                 <div class="col-xs-8 control-label goodsInfoNames " style="font-size:16px" >兑换数量<span style="font-size:5px ;font-weight:normal" id="temDayExchange"></span></div>
                 <div class="col-xs-4">
                 <a  class="count-item" onclick="miCount()" id="miBtn" >-</a>
                 <span id="TempxchangeCount"  style="text-align: center;" >1</span>
                 <a  onclick="addCount()" class="count-item">+</a>
                 </div>
                
              </div>
          </div>
             
           <div class="form-group" node-name="mobileRow" style="height:3em;padding-top:10px;margin-right:0px">
                 <div class="col-xs-4 mobileDiv" style="font-size:18px;color:#000;margin-left:20px">手机号</div>
                 <div class="col-xs-7 item-feild adaptive" style="margin-left:-30px;">
                 	<input type="tel" class="empty"  name="personPhone" id="personPhone" placeholder="接收凭证短信" tabindex="1" maxlength="11">
                 </div>
            </div>
        <div  class="tabbed_content">
            <div class="tabs">
               <div class="moving_bg"  id="noticeOraboutDiv">&nbsp;</div>
                 <z class="tab_item" id="noticeNav">兑换须知</z> <z class="tab_item" id="aboutNav">商品简介</z> 
             
              </div>
             <div class="slide_content">
               <div class="tabslider" > 
                   <div  id="tickData">
                      <ul  id="notice">  </ul>
                       <ul  id="about">   </ul>
                     <div class="clearfix"></div>
				  </div>
          
              </div>
            </div>
            </div>
            
           
        </form>


 

</div>


		</div>
		           
      <div class="bottomAD" id="bottomAD">
   
       <div class="bottom-con">

        <div class="bottom-pic " style="color:white">
           <div class="col-xs-1" style="margin-top:-3px"><input type="checkbox" id="yueCheck" /></div>
           <div class="col-xs-10" style="margin-left:-10px">我已阅读并同意“兑换须知”条款</div>
        </div>
       <div class="bottom-text" id="bottomText" ><span id="bianSpan" style="color:rgb(252, 119, 0);font-size:18px"></span><span id="bianSpan1" style="font-size:18px"></span></div>

        <button class="bottom-btn" style="background-color:#00c267;font-size:20px;" onclick="saveOrUpdateGoddsIntegral()" id="saveOrUpdateBtn">提交订单</button>
        </div>

    </div>



<script src="${base}/statics/libs/jquery.touchSwipe.min.js"></script>
<script type="text/javascript">


</script>

	<script type="text/javascript"
		src="${base}/statics/js/wx/user/integralMallExchange.js"></script>

</body>
</html>
