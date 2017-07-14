<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 我的订单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/dropload.css" />
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <link href="${base}/statics/css/wx/user/order.css" rel="stylesheet" type="text/css"/>
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    [#-- 上拉刷新下拉加载更多 --]
    <script src="${base}/statics/libs/dropload.min.js"></script>
    <style>
        body {
            background: #FFF;
        }

        .titll .col-xs-4, .titll .col-xs-2 {
            margin: 0.571em 0;
            padding: 0;
            border-right: 1px solid #ececec;
            width: 19.94666667%;
            text-align: center;
            font-size: 1em;
            color: #666;
        }

        .nodata .tipImg {
            margin-top: 8.6rem;
            margin-bottom: 1.2rem;
            width: 100vw;
            height: 10.7rem;
            background: url("${base}/statics/images/wx/ticket/no-order.png") no-repeat 50%;
            background-size: 12.5rem 10.7rem
        }

        .nodata .opt, .nodata .reason {
            width: 100vw;
            font-size: 1.4rem;
            color: #666;
            text-align: center;
            height: 1.4rem;
            line-height: 1.4rem;
            margin-bottom: .7rem
        }

        .nodata .opt {
            color: #999;
            margin-bottom: 1.8rem
        }

        .nodata .btn {
            width: 100vw;
            height: 4.1rem;
            display: flex;
            justify-content: center
        }

        .nodata .optBtn {
            width: 11.6rem;
            height: 4.1rem;
            border: 1px solid #b5b5b5;
            border-radius: .3rem;
            color: #333;
            font-size: 1.4rem;
            display: block;
            line-height: 4.1rem;
            text-align: center
        }
    </style>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--top-->
<div class="top_c">
    <a href="javascript:void(0);" class="iconfont icon-jiantou-copy-copy"></a>

    <p class="titi">我的订单</p>
</div>

<!--头部-->
<div class="pos">
    <div class="container">
        <div class="row titll">
            <a href="${base}/wx/order/order?">
                <div class="col-xs-2" [#if !type??] style="color:#246fc0;" [/#if]>全部</div>
        <a href="${base}/wx/order/order?type=1">
            <div class="col-xs-2" [#if type?? && type==1] style="color:#246fc0;" [/#if]>已付款</div>
    </a>
    <a href="${base}/wx/order/order?type=3">
        <div class="col-xs-2" [#if type?? && type==3] style="color:#246fc0;" [/#if]>待付款</div>
</a>
<a href="${base}/wx/order/order?type=4">
    <div class="col-xs-2" [#if type?? && type==4] style="color:#246fc0;" [/#if]>已关闭</div></a>
<a href="${base}/wx/order/order?type=5">
    <div class="col-xs-2" [#if type?? && type==5] style="color:#246fc0;" [/#if]>退款</div></a>
</div>
</div>
</div>

[#if (list?size>0)]
<script>
	$(function() {
			/**
			 * 初始化控件
			 */
			init();
		});
</script>
[#else]
<div node-name="listelem">
    <div class="nodata orderlist-nodata">
        <div class="tipImg"></div>
        <p class="reason">没有订单</p>
        <p class="opt">去购票页面看看吧</p>
        <div class="btn">
            <a href="${base}/wx/ticket/ticketList.html" class="optBtn">立即去</a>
        </div>
    </div>
</div>
[/#if]

<!--列表-->
<div class="ding_d">
    [#--我的订单--]
    [#list list as flag]
    <div class="on_d" data-href="${base}/wx/order/detail?id=${flag.id}">
        <p class="bh">订单编号：${flag.orderNo}<span>
           [#if flag.status?? && flag.status==1]已付款[/#if]
           [#if flag.status?? && flag.status==2]支付失败[/#if]
           [#if flag.status?? && flag.status==3]待付款[/#if]
           [#if flag.status?? && flag.status==4]已关闭[/#if]
           [#if flag.status?? && flag.status==5]退款[/#if]
		   [#if flag.status?? && flag.status==3]
			   	<img src="${base}/statics/images/wx/user/clock.png" height="20px"/>
			   	[#-- 我的订单倒计时 --]
			   	[@order unix="${flag.createTime?c}" index="${flag_index}"] [/@order]
		   [/#if]
	   	</span></p>

        <div class="sp_pr">
            <a href="javascript:void(0);">
                <img src="${base}/[#if flag.mpTicketEntity??]${flag.mpTicketEntity.thumbUrl}[/#if]">
                <div class="text_p">
                    <p>[#if flag.mpTicketEntity??]${flag.mpTicketEntity.ticketName}[/#if]</p>
                    <span>
                        共有${flag.ticketCount?c}张
                    </span>
                    <span class="yue">有效期：
						[#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
						[@formatTime unix="${flag.startTime?c}" pattern="yyyy年MM月dd日"] [/@formatTime]-
						[#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
						[@formatTime unix="${flag.endTime?c}" pattern="yyyy年MM月dd日"] [/@formatTime]
					</span>
                    <span class="yue">下单时间：
						[#-- 自定义标签格式化时间戳 ?c防止时间戳有,逗号 --]
						[@formatTime unix="${flag.createTime?c}" pattern="yyyy.MM.dd HH.mm.ss"] [/@formatTime]
					</span>
                </div>
            </a>
        </div>
        <div class="button">
        	订单总金额<span>￥${flag.totalFee?c}</span>
        [#if flag.status?? && flag.status==1]
        	<a href="javascript:void(0);" onclick="delOrder(this,${flag_index})" class="del">删除订单</a>
            <input type='hidden' value="${flag.id}"/>
        [/#if]
        [#if flag.status?? && flag.status==2]
        	<a href="javascript:void(0);" onclick="delOrder(this,${flag_index})" class="del">删除订单</a>
            <input type='hidden' value="${flag.id}"/>
            <a href="javascript:void(0);" onclick="canOrder(this)" class="qu">取消订单</a>
            <input type='hidden' value="${flag.id}"/>
        [/#if]
        [#if flag.status?? && flag.status==3]
            <a href="javascript:void(0);" onclick="goPay(this)" class="liji">立即付款</a>
            <a href="javascript:void(0);" onclick="canOrder(this)" class="qu">取消订单</a>
            <input type='hidden' value="${flag.id}"/>
        	<a href="javascript:void(0);" onclick="delOrder(this,${flag_index})" class="del">删除订单</a>
            <input type='hidden' value="${flag.id}"/>
        [/#if]
        [#if flag.status?? && flag.status==4]
        	<a href="javascript:void(0);" onclick="delOrder(this,${flag_index})" class="del">删除订单</a>
            <input type='hidden' value="${flag.id}"/>
        [/#if]
        [#if flag.status?? && flag.status==5]
        	<a href="javascript:void(0);" onclick="delOrder(this,${flag_index})" class="del">删除订单</a>
            <input type='hidden' value="${flag.id}"/>
        [/#if]
        </div>
    </div>
    [/#list]
</div>

[#-- Confirm UI --]
[#assign confirmId="delOrder-confirm"]
[#assign confirmTitle="温馨提示"]
[#assign confirmContent="删除订单后不可恢复，确定删除吗?"]
[#include "/wx/confirmUI.ftl"]


[#-- Confirm UI --]
[#assign confirmId="canOrder-confirm"]
[#assign confirmTitle="温馨提示"]
[#assign confirmContent="确定要取消此订单吗?"]
[#include "/wx/confirmUI.ftl"]

[#-- Alert UI --]
<div class="am-modal am-modal-alert" tabindex="-1" id="delOrder-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">提示</div>
        <div class="am-modal-bd" id="modal-alert">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>
</body>
<script src="${base}/statics/js/wx/user/order.js"></script>
</html>
<script>
</script>