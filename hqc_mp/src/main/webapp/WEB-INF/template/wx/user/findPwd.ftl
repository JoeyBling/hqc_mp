<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 找回密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <style type="text/css">
        body {
            background: #F7F4F6;
        }
    </style>
</head>
<body>
[#-- 页面加载 --]
[#-- 加载的毫秒 --]
[#assign milli=350?c]
[#include "/wx/pageLoading.ftl"]
<!--header 开始-->
[#assign pageName="找回密码" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
<div class="page">
    <div class="main">
        <form id="frm_login" method="post" action="">
            <div class="item item-username">
                <input id="phone" class="txt-input txt-username" type="text" placeholder="注册手机号" value=""
                       name="username">
                <b class="input-close" style="display: none;"></b></div>
            <div class="item item-captcha">
                <div class="input-info">
                    <input id="phoneCode" class="txt-input txt-captcha" type="text" placeholder="验证码" autocomplete="off"
                           maxlength="6" size="11">
                    <span id="getPhoneCode"><button id="butGetPhoneCode" onclick="getPhoneCode(this)" type="button"
                                                    class="btn btn-success btn-block">获取验证码
                    </button>   </span></div>
                <div id="phoneCodeSendSuccess" class="err-tips" style="color: #3c763d;display:none"> 验证码发送成功，请注意查收</div>
            </div>
            <div class="item item-password">
                <input id="password" class="txt-input txt-password ciphertext" type="password" placeholder="新登录密码"
                       name="password" style="display: inline;">
            </div>
            <div class="ui-btn-wrap">
                <button type="button" id="findPwd" class="ui-btn-lg ui-btn-primary" data-loading-text="加载中..."
                        href="javascript:void(0);">找回密码
                </button>
            </div>
        </form>
    </div>
</div>

[#-- Model Loading --]
[#assign loadingId="my-modal-loading" ]
[#assign showLoading="正在载入..." ]
[#include "/wx/loading.ftl"]
<script type="text/javascript">
    $(function () {
        $(".input-close").hide();

        //找回密码
        $('#findPwd').click(function () {
            loadingButton($(this));
            var $phone = $("#phone").val();		//注册手机号
            var $phoneCode = $("#phoneCode").val();	//手机验证码
            var $password = $("#password").val();		//新登录密码
            if (null == $phone || "" === $phone || "" === $phone.trim() || !/^(13|14|15|17|18)[0-9]{8}[0-9]$/.test($phone)) {
                AMUI.dialog.alert({
                    content: "未输入手机号或手机号格式有误",
                    onConfirm: function () {
                        // console.log('close');
                    }
                });
                return false;
            }
            if (null == $phoneCode || "" === $phoneCode || "" === $phoneCode.trim()) {
                AMUI.dialog.alert({
                    content: "请填写手机验证码",
                    onConfirm: function () {
                        // console.log('close');
                    }
                });
                return false;
            }
            if (null == $password || "" === $password || "" === $password.trim()) {
                AMUI.dialog.alert({
                    content: "请填写新登录密码",
                    onConfirm: function () {
                        // console.log('close');
                    }
                });
                return false;
            }
            $("#my-modal-loading").modal();
            $.ajax({
                type: "POST",
                url: "${base}/wx/user/rePwd",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: {
                    phone: $phone,
                    phoneCode: $phoneCode,
                    password: $password
                },
                dataType: "json",
                success: function (result) {
                    $("#my-modal-loading").modal('close');
                    if (result.code === 0) {//找回密码成功
                        layer.msg("找回密码成功,3秒后跳转登录页面", {icon: 1});
                        setTimeout(function () {
                            location.href = '${base}/wx/user/login.html';
                        }, 3000);
                    } else {
                        AMUI.dialog.alert({
                            content: result.msg,
                            onConfirm: function () {
                                // console.log('close');
                            }
                        });
                    }
                },
                error: function () {
                    $("#my-modal-loading").modal('close');
                    AMUI.dialog.alert({
                        content: '系统出现异常!',
                        onConfirm: function () {
                            window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
                            // console.log('close');
                        }
                    });
                }
            });
        });
    });

    //发送手机验证码
    function getPhoneCode(e) {
        $("#my-modal-loading").modal();
        if ($(e).hasClass("disabled")) {
            $("#my-modal-loading").modal('close');
            return false;
        }
        var $phone = $("#phone").val();	//手机号码
        var mobileReg = /^(13|14|15|17|18)[0-9]{8}[0-9]$/;
        if (!mobileReg.test($phone)) {
            $("#my-modal-loading").modal('close');
            layer.msg("未输入手机号或手机号格式有误");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "${base}/wx/phoneCode?type=2",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: {
                phone: $phone
            },
            dataType: "json",
            success: function (result) {
                $("#my-modal-loading").modal('close');
                var countdown = 59;
                var o = $("#butGetPhoneCode");
                var iCount = setInterval(function () {
                    o.text(countdown-- + "s后重试");
                    o.addClass("disabled");
                    if (countdown == 0) {
                        o.removeClass("disabled");
                        o.text("获取验证码");
                        clearInterval(iCount);//清除倒计时
                        countdown = 60;//设置倒计时时间
                    }
                    ;
                }, 1000);
                if (result.code === 0) {//获取成功
                    $("#phoneCodeSendSuccess").show();
                    layer.msg("获取成功", {icon: 1});
                } else {
                    AMUI.dialog.alert({
                        content: result.msg,
                        onConfirm: function () {
                            // console.log('close');
                        }
                    });
                }
            },
            error: function () {
                $("#my-modal-loading").modal('close');
                AMUI.dialog.alert({
                    content: '系统出现异常!',
                    onConfirm: function () {
                        window.location.href = location.href + '?time='
									+ ((new Date()).getTime())
                        // console.log('close');
                    }
                });
            }
        });
    }
</script>
</body>
</html>
<script>
</script>