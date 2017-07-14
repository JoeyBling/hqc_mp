<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 停车收费</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    [#include "/wx/common_css.ftl"]
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <style type="text/css">
        body {
            background: #F7F4F6;
        }

        #font_lend {
            font-size: 15px;
            color: #fff
        }
    </style>
</head>
<body>
<!--header 开始-->
[#assign  pageName="停车收费" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
<div data-am-widget="list_news" class="am-list-news am-list-news-default">
    <!--列表标题-->
    <div class="am-list-news-hd am-cf">
        <!--带更多链接-->
        <a href="javascript:void(0);" style="color:#337ab7;">
            <h2>输入车牌号查询停车账单</h2>
            <span id="spcialCarN" class="am-list-news-more am-fr">特殊车牌点这里 </span>
        </a>
    </div>
    <form action="${base}/wx/car/toPay" method="post">
        <div class="am-list-news-bd" style="margin-top:20px">
            <ul class="am-list">
                <li class="am-g am-list-item-dated">
                    <select id="selectID" name="platehead"
                            style="width:15%;height:50px;color:#00c3a5;padding-left:5px;font-size:25px">
                        <option value="京 ">京</option>
                        <option value="津 ">津</option>
                        <option value="沪">沪</option>
                        <option value="渝">渝</option>
                        <option value="冀">冀</option>
                        <option value="晋">晋</option>
                        <option value="辽">辽</option>
                        <option value="吉">吉</option>
                        <option value="黑">黑</option>
                        <option value="苏">苏</option>
                        <option value="浙">浙</option>
                        <option value="皖">皖</option>
                        <option value="闽">闽</option>
                        <option value="赣">赣</option>
                        <option value="鲁">鲁</option>
                        <option value="豫">豫</option>
                        <option value="鄂">鄂</option>
                        <option value="湘">湘</option>
                        <option value="粤" selected>粤</option>
                        <option value="琼">琼</option>
                        <option value="川">川</option>
                        <option value="贵">贵</option>
                        <option value="云">云</option>
                        <option value="陕">陕</option>
                        <option value="甘">甘</option>
                        <option value="青">青</option>
                        <option value="藏">藏</option>
                        <option value="桂">桂</option>
                        <option value="蒙">蒙</option>
                        <option value="宁">宁</option>
                        <option value="新">新</option>
                        <option value="澳">宁</option>
                        <option value="台">台</option>
                        <option value="港">港</option>
                    </select>
                    <input type="text" id="CarId" name="platefoot" style="width:68.7%;height:50px;"
                           placeholder="请输入您的车牌号" required/>
                </li>
            </ul>
            <div style="text-align:center"><font color="red">${resMsg!""}</font></div>
            <button id="sub" style="margin-top:15px" class="btn btn-success btn-block" type="submit">下一步</button>
        </div>

    </form>
</div>
</body>
<script>
    $(function () {
        $("#sub").click(function () {
            var carid = $("#CarId").val();
            if (carid == "" || carid == null) {
                layer.msg("请填写您的车牌号");
                return false;
            }
        });

		//特殊车牌切换
        $("#spcialCarN").click(function () {
            var f = $("#selectID").is(":hidden");
            if (f) {
            	$("#selectID").removeAttr("disabled","disabled");
                $("#selectID").show();
                $("#CarId").css("width", "68.7%");
            }
            else {
            	$("#selectID").attr("disabled","disabled");
                $("#selectID").hide();
                $("#CarId").css("width", "100%");
            }
        });
    });
</script>
</html>