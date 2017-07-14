<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 在线购票</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    
    <script src="${base}/statics/js/wx/ticket/lCalendar.js"></script>
    <link rel="stylesheet" href="${base}/statics/css/wx/ticket/lCalendar.css">
    <style type="text/css">
        body {
            background: #F7F4F6;
        }
        .all{border:1px solid red;font-weight:bold;font-size:18px;width:30px;height:30px;float:left;text-align:center}
        #jia{margin-left:10px}
        #num{border-left:none;border-right:none}
        input {
				width: 50%;
				height: 30px;
				font-size: 18px;
				border: 1px solid #19b5ee;
				border-radius: 5px;
				margin: 0px 5% 0 5%;
				padding: 5px;
			}
		.ellipsis {
				overflow:hidden;
				text-overflow:ellipsis;
				display:-webkit-box;
				-webkit-line-clamp:2;
				-webkit-box-orient:vertical;
				padding-left:15px
			}
		
    </style>
</head>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--header 开始-->
[#assign  pageName="在线购票" ]
[#include "/wx/header.ftl"]
<body>
	<div id="father">
		<div id="header_one">
			<img src="../../statics/images/wx/ticket/ticket.png" style="width:100%;height:200px">
				<h1 style="font-size:18px;color:black;margin-top:5px;padding-left:15px">大峡谷门票</h1>
					<p style="margin-top:5px;padding-left:15px;font-size:16px;">
						 <span style="color:red;font-weight:bold">￥90</span>
							<span style="margin-left:57%;">已售86</span>张</p>
								<hr  style="height:3px;border:none;border-top:1px solid #A8BEDF;"/>
		</div>
		<div id="body_one">
				<p style="font-size:18px;;color:black;padding-left:15px;display:inline;float:left">数量</p>
					<div class="all" id="jia">+</div>
						<div class="all" id="num">1</div>
							<div class="all" id="jian">-</div>
								<div style="clear:both"></div>
					<p style="margin-top:15px;font-size:16px;text-align:center;margin-left:-8%">
						<lable>订购日期</lable><input id="demo1" type="text" readonly="" name="input_date" placeholder="请选择订购日期" data-lcalendar="2011-01-1,2019-12-31" />
					</p>
									<hr  style="height:3px;border:none;border-top:1px solid #A8BEDF;"/>
		</div>
		<div id="footer_one">
				<p style="font-size:18px;color:red;padding-left:15px">大峡谷探险乐园</p>
						<div class="ellipsis">
							俯瞰深圳东部黄金海岸线，集山地郊野公园和都市主题公园于一体，体现生态动感旅游文化，
							实现了自然景观、生态理念与娱乐体验、科普教育的创新结合，
							主要包括：水公园、海菲德小镇、峡湾森林、生态峡谷、云海高地等五大主题区。
						</div>
						
				  <div id="hide" style="display:none">	
					<p style="font-size:18px;color:red;padding-left:15px;margin-top:5px">海菲德小镇</p>
						<div class="ellipsis">
							意为“海边的小镇”，以浓郁而各具特色的世界葡萄酒文化为主题，原木与砖石相结合的建筑温馨质朴，
							系列铜雕展示了从葡萄采摘到红酒酿造的全过程，演绎了19世纪美国加州纳帕山谷的红酒小镇风情。
							主要包括天幕、海菲德剧场、红酒体验馆、自酿啤酒屋、湖畔美食廊、小镇客栈等特色项目，
							为游客精心打造了一个美洲主题小镇，一处与红酒约会的陶醉之乡。
						</div>
				 </div>
							<div id="allInfo" style="font-size:15px;color:gray;margin-top:8px;padding-left:35%">===更多说明===</div>
							
		</div>
	</div>
</body>
<script>
	$(function(){
		$("#jia").click(function(){
			var f=0;
			f=$("#num").text();
			$("#num").text(parseInt(f)+1);
			if(parseInt(f)==100)
			{
				$("#num").css("width","40px");
			}
		})
		
		$("#jian").click(function(){
			var f=0;
			f=$("#num").text();
			$("#num").text(parseInt(f)-1);
			if(parseInt(f)==1)
			{
				$("#num").text(1);
			}
		})
		
		$("#allInfo").click(function(){
			var hide= $("#hide").is(" :hidden");
			if(hide)
			{
				$("#hide").css("display","block");
			}
			else
			{
				$("#hide").css("display","none");
			}
		});
		
		    var calendar = new lCalendar();
			calendar.init({
				'trigger': '#demo1',
				'type': 'date'
			});

	});
</script>
</html>
