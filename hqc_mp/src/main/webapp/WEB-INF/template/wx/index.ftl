<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 微信连Wi-Fi</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript">
        /**
         * 微信连Wi-Fi协议3.1供运营商portal呼起微信浏览器使用
         */
        var loadIframe = null;
        var noResponse = null;
        function createIframe() {
            var iframe = document.createElement("iframe");
            iframe.style.cssText = "display:none;width:0px;height:0px;";
            document.body.appendChild(iframe);
            loadIframe = iframe;
        }
        //注册回调函数
        function jsonpCallback(result) {
            if (result && result.success) {
                var ua = navigator.userAgent;
                if (ua.indexOf("iPhone") != -1 || ua.indexOf("iPod") != -1 || ua.indexOf("iPad") != -1) {   //iPhone             
                    document.location = result.data;
                } else {
                    createIframe();
                    loadIframe.src = result.data;
                    document.location = result.data;
                    noResponse = setTimeout(function () {
                        errorJump();
                    }, 3000);
                }
            } else if (result && !result.success) {
                alert(result.data);
            }
        }

        function Wechat_GotoRedirect(appId, extend, timestamp, sign, shopId, authUrl, mac, ssid, bssid) {

            //将回调函数名称带到服务器端
            var url = "https://wifi.weixin.qq.com/operator/callWechatBrowser.xhtml?appId=" + appId
                    + "&extend=" + extend
                    + "&timestamp=" + timestamp
                    + "&sign=" + sign;

            //如果sign后面的参数有值，则是新3.1发起的流程
            if (authUrl && shopId) {
                url = "https://wifi.weixin.qq.com/operator/callWechat.xhtml?appId=" + appId
                + "&extend=" + extend
                + "&timestamp=" + timestamp
                + "&sign=" + sign
                + "&shopId=" + shopId
                + "&authUrl=" + encodeURIComponent(authUrl)
                + "&mac=" + mac
                + "&ssid=" + ssid
                + "&bssid=" + bssid;

            }

            //通过dom操作创建script节点实现异步请求  
            var script = document.createElement('script');
            script.setAttribute('src', url);
            document.getElementsByTagName('head')[0].appendChild(script);
        }
        function putNoResponse(ev) {
            clearTimeout(noResponse);
        }

        function errorJump() {
            alert('您的手机浏览器可能无法跳转到微信，如果已跳转请忽略此提示');
        }

        myHandler = function (error) {
            errorJump();
        };

    </script>
    <link rel="stylesheet" href="${base}/statics/css/style-simple-follow.css"/>
</head>
<body class="mod-simple-follow">
<div class="mod-simple-follow-page">
    <div class="mod-simple-follow-page__banner">
        <img class="mod-simple-follow-page__banner-bg" src="${base}/statics/images/background.jpg"
             alt=""/>

        <div class="mod-simple-follow-page__img-shadow"></div>
        <div class="mod-simple-follow-page__logo">
            <img class="mod-simple-follow-page__logo-img"
                 src="${base}/statics/images/t.weixin.logo.png" alt=""/>

            <p class="mod-simple-follow-page__logo-name"></p>

            <p class="mod-simple-follow-page__logo-welcome">欢迎您</p>
        </div>
    </div>
    <div class="mod-simple-follow-page__attention">
        <p class="mod-simple-follow-page__attention-txt">欢迎使用微信连Wi-Fi</p>
        <a class="mod-simple-follow-page__attention-btn" onclick="callWechatBrowser()">一键打开微信连Wi-Fi</a>
    </div>
</div>
</body>
<script type="text/javascript" src="${base}/statics/libs/md5.js"></script>
<script type="text/javascript">
    var appId = "wx062df5bece8a8b21";//微信开发凭证的基本参数  
    var secretkey = "90a0c6d0c861d9d8ff36fe7ad2e0a5f3";//微信开发凭证的基本参数  
    var extend = "sc";       //开发者自定义参数集合
    var timestamp = new Date().getTime() - 600000;    //时间戳(毫秒)
    var shop_id = "3316163";               //AP设备所在门店的ID
    var authUrl = "http://61.235.13.252/hqc_mp/wx/wifi";        //这个链接其实是路由器放行上网的链接，有硬件开发的提供链接  
    var mac = "3c:91:57:c5:cc:af";     //用户手机mac地址 安卓设备必需
    var ssid = "A01-S001-R044";         //AP设备信号名称，非必须
    var bssid = "00:e0:61:4c:a7:c5";     //AP设备mac地址，非必须
    function callWechatBrowser() {
        var sign = hex_md5(appId + extend + timestamp + shop_id + authUrl + mac + ssid + bssid + secretkey);
        Wechat_GotoRedirect(appId, extend, timestamp, sign, shop_id, authUrl, mac, ssid, bssid);
    }
</script>
<script type="text/javascript">
    document.addEventListener('visibilitychange', putNoResponse, false);
</script>
</html>