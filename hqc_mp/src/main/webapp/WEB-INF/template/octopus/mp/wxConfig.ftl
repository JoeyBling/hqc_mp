<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>首页</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/js/common.js"></script>
</head>
<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">公众号管理</a></li>
        <li><a href="javascript:void(0);">公众号配置</a></li>
    </ul>
</div>

<div class="rightinfo">
    <div id="add">
        <form id="wxConfigForm" class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2 control-label">AppID(应用ID)</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="appid" id="appid" placeholder="appid"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">AppSecret(应用密钥)</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="appsecret" id="appsecret" placeholder="appsecret"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">Token(令牌)</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="token" id="token" placeholder="token"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">EncodingAESKey(消息加解密密钥)</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="aeskey" id="aeskey" placeholder="aeskey"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">微信支付商户号</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="partener_id" id="partener_id"
                           placeholder="partener_id"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">微信支付平台商户API密钥</div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="partener_key" id="partener_key"
                           placeholder="partener_key"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">服务器配置URL</div>
                <div class="col-sm-3" id="wxurl">

                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
	                [@shiro.hasPermission name="mp:config:update"]
	                    <input type="button" class="btn btn-primary" onclick="updateWxConfig()" value="修改"/>
	                [/@shiro.hasPermission]
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${base}/statics/js/octopus/mp/wxConfig.js"></script>
</body>
</html>