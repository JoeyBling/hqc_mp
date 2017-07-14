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
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/ticket/ticketChoose.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/plugins/layer/layer.js"></script>
	<script src="${base}/statics/plugins/layui/layui.js"></script>
    <style type="text/css">
        body {
            background: #F7F4F6;
        }
    </style>
</head>
<body>
	<div class="titlehead">
		<ul>
			<li class="title">大峡谷</li>
			<li class="headcenter">营业时间:&nbsp;&nbsp;9:30—18:00</li>
			<li class="headcenter">(节假日提前半小时开放)</li>
		</ul>
	</div>
	<div class="chooseTime">
		<ul class="list-inline">
			<li><div class="week">周六</div><div class="time">明天</div></li>
			<li><div class="week">周五</div><div class="time">今天</div></li>
			<li><div class="week  choosed">周日</div><div class="time choosetime">05月21日</div></li>
			<li><div class="week">周一</div><div class="time">05月22日</div></li>
			<li><div class="week">周二</div><div class="time">05月23日</div></li>
		</ul>
	</div>
	<div class="ticketListTitle">
		<span class="listTitle">门票列表</span>
	</div>
	<div class="ticketList">
		<ul>
			<li class="listul">
				<ul class="list-inline">
					<li class="ticketContent">大峡谷单谷门票</li>
					<li class="ticketRight">
						<div class="contentRight">
							<ul class="list-inline">
								<li class="pirce">￥200</li>
								<li class="buyButton"><div class="buyBtn">购买</div></li>
							</ul>
						</div>
					</li>
				</ul>
			</li>
			<li class="listul">
				<ul class="list-inline">
					<li class="ticketContent">大峡谷单谷门票</li>
					<li class="ticketRight">
						<div class="contentRight">
							<ul class="list-inline">
								<li class="pirce">￥300</li>
								<li class="buyButton"><div class="buyBtn">购买</div></li>
							</ul>
						</div>
					</li>
				</ul>
			</li>
			<li></li>
			<li></li>
		<ul>
	</div>
</body>
</html>
