<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 会员登录</title>
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
[#assign pageName="会员登录" ]
[#include "/wx/header.ftl"]
<!--header 结束-->
<div class="page">
    <div class="main">
        <form id="user_login" method="post">
            <div class="item item-username">
                <input id="username" class="txt-input txt-username" type="text" placeholder="请输入手机号" value=""
                       name="username">
                <b class="input-close" style="display: none;"></b></div>
            <div class="item item-password">
                <input id="password" class="txt-input txt-password ciphertext" type="password" placeholder="请输入密码"
                       name="password" style="display: inline;">
                <input id="ptext" class="txt-input txt-password plaintext" type="text" placeholder="请输入密码"
                       style="display: none;" name="ptext">
                <b class="tp-btn btn-off"></b></div>
            <div class="item item-captcha">
                <div class="input-info">
                    <input id="validateCode" class="txt-input txt-captcha" type="text" placeholder="验证码"
                           autocomplete="off" maxlength="6" size="11">
                    <span id="captcha-img"> <img id="code" src="${base}/wx/captcha.jpg?type=1"
                                                 style="width:63px;height:25px;"
                                                 onclick="closeAndFlush(this);"> </span></div>
            </div>
            <div class="item item-login-option"><span class="retrieve-password"> <a class=""
                                                                                    href="${base}/wx/user/findPwd.html">找回密码</a> </span>

                <div class="clr"></div>
            </div>
            <div class="ui-btn-wrap">
                <button class="ui-btn-lg ui-btn-primary" onclick="javascript:login(this)" data-loading-text="加载中...">
                    用户登录
                </button>
            </div>
            <div class="ui-btn-wrap"><a class="ui-btn-lg ui-btn-danger" href="${base}/wx/user/reg.html">没有账号？立即注册</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
	//直接跳转微信授权登录
	location.href = "${base}/wx/oauthLogin";
	
	
	//登录成功后跳转的URL
    var returnUrl = topRequest.QueryString("returnUrl");
    $(function () {
        $(".input-close").hide();
        displayPwd();
        displayClearBtn();
        setTimeout(displayClearBtn, 200); //延迟显示,应对浏览器记住密码
    });

    //刷新验证码 
    function closeAndFlush(e) {
        $(e).attr("src", "${base}/wx/captcha.jpg?type=1&" + Math.random());
    }

    //是否显示清除按钮
    function displayClearBtn() {
        if (document.getElementById("username").value != '') {
            $("#username").siblings(".input-close").show();
        }
        if (document.getElementById("password").value != '') {
            $(".ciphertext").siblings(".input-close").show();
        }
        if ($("#codeLevel").val() != "" && $("#codeLevel").val() != '0') {
            if ($("#validateCode").val() != "") {
                $("#validateCode").siblings(".input-close").show();
            }
        }
    }

    //清除input内容
    $('.input-close').click(function (e) {
        $(e.target).parent().find(":input").val("");
        $(e.target).hide();
        $($(e.target).parent().find(":input")).each(function (i) {
            if (this.id == "ptext" || this.id == "password") {
                $("#password").val('');
                $("#ptext").val('');
            }
        });
    });

    //设置password字段的值
    $('.txt-password').bind('input', function () {
        $('#password').val($(this).val());
    });

    //显隐密码切换
    function displayPwd() {
        $(".tp-btn").toggle(
                function () {
                    $(this).addClass("btn-on");
                    var textInput = $(this).siblings(".plaintext");
                    var pwdInput = $(this).siblings(".ciphertext");
                    pwdInput.hide();
                    textInput.val(pwdInput.val()).show();
                },
                function () {
                    $(this).removeClass("btn-on");
                    var textInput = $(this).siblings(".plaintext ");
                    var pwdInput = $(this).siblings(".ciphertext");
                    textInput.hide();
                    pwdInput.val(textInput.val()).show();
                }
        );
    }

    //监控用户输入
    $(":input").bind('input propertychange', function () {
        if ($(this).val() != "") {
            $(this).siblings(".input-close").show();
        } else {
            $(this).siblings(".input-close").hide();
        }
    });


    //会员登录
    function login(e) {
        loadingButton(e);
        var $username = $("#username").val();	//用户名
        var $password = $("#password").val();	//密码
        var $validateCode = $("#validateCode").val();	//验证码
        if (null == $username || "" === $username || "" === $username.trim()) {
            AMUI.dialog.alert({
                content: "请输入手机号",
                onConfirm: function () {
                    // console.log('close');
                }
            });
            return false;
        }
        if (null == $password || "" === $password || "" === $password.trim()) {
            AMUI.dialog.alert({
                content: "请输入密码",
                onConfirm: function () {
                    // console.log('close');
                }
            });
            return false;
        }
        if (null == $validateCode || "" === $validateCode || "" === $validateCode.trim()) {
            AMUI.dialog.alert({
                content: "请输入验证码",
                onConfirm: function () {
                    // console.log('close');
                }
            });
            return false;
        }
        $.ajax({
            type: "POST",
            url: "${base}/wx/user/login",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: {
                username: $username,
                password: $password,
                validateCode: $validateCode
            },
            dataType: "json",
            success: function (result) {
                if (result.code === 0) {//登录成功	
                    layer.msg("登录成功", {icon: 1});
                    if (null != returnUrl && "" != returnUrl && "" != returnUrl.trim()) {
                        parent.location.href = returnUrl;
                    } else {
                        parent.location.href = '${base}/wx/user/userCenter.html';
                    }
                } else {
                    AMUI.dialog.alert({
                        content: result.msg,
                        onConfirm: function () {
                            // console.log('close');
                        }
                    });
                    if (result.code === 4) {
                        //刷新验证码
                        closeAndFlush($("#code"));
                    }
                    if (result.code === 100) {	//进行授权登录
                        setTimeout(function() {
                        	location.href = '${base}/wx/oauthLogin';
						}, 2000);
                    }
                }
            },
            error: function () {
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
    }
</script>
</body>
</html>
<script>
</script>