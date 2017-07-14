<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 门票列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
        [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/dropload.css" />
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/ticket/ticketList.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    [#-- 上拉刷新下拉加载更多 --]
    <script src="${base}/statics/libs/dropload.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
	<script src="${base}/statics/plugins/layui/layui.js"></script>

    </style>
</head>

<body>
	<div class="page-index fixed-menu" node-name="index">
		<header class="m-index-flash" node-name="flash"><div id="my-top"></div>
			<div class="am-slider am-slider-default" id="demo-slider-0" data-am-flexslider >
  				<ul class="am-slides" id="banneres">
    				<li id="demo1"><img data-href="javascript:void(0)" class="swiper-lazy swiper-lazy-loaded" alt="" src="${base}/statics/images/wx/ticket/14773666234379.jpg" height='180px'>
					</li>
					<li id="demo2"><img data-href="javascript:void(0)" class="swiper-lazy swiper-lazy-loaded" alt="" src="${base}/statics/images/wx/ticket/14944111986785.jpg" height='180px'>
					</li>
  				</ul>
			</div>		
		</header>
		<div id="loca"></div>
		<section class="category-nav" node-name="category" id="category">	
			<div class="swiper-container nav-swiper swiper-container-horizontal swiper-container-android" id="nav-swiper">			
				<div class="swiper-wrapper">			
					<div class="swiper-slide active-nav swiper-slide-visible "  style="width:50%;" onclick="query(1)" id="queryTicket">
						<span>门票</span>
					</div>				  
					<div class="swiper-slide swiper-slide-visible "  style="width:50%;" onclick="query(2)" id="queryAll">
						<span>全部商品</span>
					</div>	
				</div>
			</div>	
		</section>  
		<section class="m-index-category">     
			 <div>    
  				<div class="swiper-container tab-container goods-swiper swiper-container-horizontal swiper-container-autoheight swiper-container-fade swiper-container-android" id="goods-swiper">       
  				    <div class="swiper-wrapper" node-name="buylist" >			        
  				    	<div class="swiper-slide scrollable swiper-slide-active"   style="width: 100%;  z-index: 2;" id="tickets">            
					
						</div>			
					</div>   
				</div>      
			</div>   
		</section>
	</div>

	[#-- <footer class="tools-menu" node-name="footer">
		<div class="inner-menu">
			<div class="flex-item active" data-href="/weixin/?r=index/index">
				<i class="icon-wx-purchase"></i>
				<span>微信购票</span>
			</div>
			<div class="flex-item" data-href="/weixin/?r=order/list">
				<div class="dots" node-name="orderdots">
				</div>
				<i class="icon-my-order"></i>
				<span>我的订单</span>
			</div>
			<div class="flex-item" data-href="/weixin/?r=card/package">
				<div class="dots" node-name="cashdots"></div>
				<i class="icon-card-package"></i>
				<span>卡券包</span>
			</div>
		</div>
	</footer> --]
	<div id="selectDate" class="m-datepicker" data-time="2017-6-10009" node-name="datepicker" style="display:none">
		<div class="mask" node-name="mask"></div>
		<input type="hidden" node-name="selectday">
		<div class="datepicker" node-name="datebox">
		</div>
	</div>              	    	
	<div style="display: none"></div>
</body>
<script type="text/javascript" src="${base}/statics/js/wx/ticket/ticketList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var loca = $("#loca");
	var menu = $(".category-nav") ;
	$("div").scroll(function() {
	
		var height = loca.offset().top;
		if(height < 0){
			menu.addClass("top");
		}else{
			menu.removeClass("top");
		}		
	});
})
</script>
</html>
		 