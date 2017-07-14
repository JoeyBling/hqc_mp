<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>顶部</title>
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            //顶部导航切换
            $(".nav li a").click(function () {
                $(".nav li a.selected").removeClass("selected")
                $(this).addClass("selected");
            })
        })
    </script>
</head>
<body style="background:url(${base}/statics/images/octopus/topbg.gif) repeat-x;">
<div class="topleft">
    <a href="${base}/octopus/" target="_parent">
        <div class="logo">华侨城管理后台</div>
    </a>
</div>
<div class="topright">
    <ul>
        <li><a  href="${base}/octopus/sys/logout" target="_parent">退出登录</a></li>
    </ul>
    <div class="user">
        <span><a href="${base}/octopus/sys/right.html" target="rightFrame" id="userName" style="color:#FFF"></a></span>
    </div>
</div>
</body>
</html>