<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 微信缴费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    [#include "/wx/common_js.ftl"]
    <style type="text/css">
        body {
            background: #F7F4F6;

        }

        #font_lend {
            font-size: 15px;
            color: #fff
        }

        #headlend {
            width: 100%;
            height: 150px;
            background: -webkit-linear-gradient(#3cfad7, #02c8a0);
        }

        #payNum {
            font-size: 28px;
            margin-left: 30%;
            color: #fff;
        }

        #PayMoney {
            font-size: 60px;
            color: #fff;
        }

        #SY {
            font-size: 60px;
            color: #fff;
            margin-left: 15%
        }

        .lable_one {
            font-size: 18px;
        }

        #cicle {
            list-style: none;
            padding-left: 15px;
        }

        .span_one {
            text-align: right;
            padding-left: 25%
        }

        ul li {
            color: gray
        }

        #bodylend_two_one {
            color: gray
        }

        #bodylend_two_one lable {
            font-size: 15px
        }

        p {
            font-size: 12px
        }
    </style>
</head>
<body>
[#assign pageName="微信缴费" ]
[#include "/wx/header.ftl"]
<div id="headlend">
    <div style="height:20%;"></div>
    <div style="height:30%;">
        <lable id="payNum">支付金额</lable>
    </div>
    <div style="height:48%;"><span id="SY">￥</span>
        <lable id="PayMoney">${parkingInfo.payable/100}</lable>
    </div>
    <div style="height:2%;background-color:#02c8a0"></div>
</div>
<div id="bodylend" style="height:250px;background:#F7F4F6;">
    <div id="bodylend_one" style="height:20%"></div>
    <div id="bodylend_two" style="height:40%;">
        <ul id="cicle" style="margin-top:10px">
            <li>
                <lable class="lable_one">车牌</lable>
                <span class="span_one" style="padding-left:36%">${plate!''}</span></li>
            <li>
                <lable class="lable_one">停车时长</lable>
                <span class="span_one">${hour!'0'}小时${parkingInfo.elapsedTime%60}分钟</span></li>
            <li>
                <lable class="lable_one">入场时间</lable>
                <span class="span_one">${parkingInfo.entryTime!""}</span></li>
        </ul>
    </div>
    <div id="bodylend_two_one" style="height:30%;margin-top:10px;padding-left:25px">
        <lable>温馨提示:</lable>
        <p>如发现入场时间和支付费用不对，请勿缴费，可到</p>

        <p>出口缴费处缴费。</p>
    </div>
</div>
<div id="footer">
    <button id="sub" style="margin-top:20px;width:80%;margin-left:35px" class="btn btn-info btn-block"
            type="submit">立即支付
    </button>
</div>
</body>
</html>