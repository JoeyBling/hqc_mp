<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 微信分享</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${base}/statics/css/style-simple-follow.css"/>
    <script type="text/javascript" src="${base}/statics/js/wx/jweixin-1.0.0.js"></script>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
</head>
<body class="mod-simple-follow">
<div id="body" class="mod-simple-follow-page">
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
        <p class="mod-simple-follow-page__attention-txt">欢迎使用微信分享</p>
        <a class="mod-simple-follow-page__attention-btn" id="share">一键打开微信分享</a>
        <a class="mod-simple-follow-page__attention-btn" id="chooseImage">选择图片</a>
    </div>
</div>

<div id='shareit' style='display:none;'>
	<img width="100%" height="100%" src="${base}/statics/images/wx/shareit.png"/>
</div>
</body>
<script type="text/javascript">
    $(function () {
        ajaxConfig();
    });
    
    wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});

    function ajaxConfig() {
        $.ajax({
            type: "post",
            dataType: "json",
            data: {
                url: location.href.split('#')[0]
            },
            url: "${base}/wx/getConfig",
            success: function (obj) {
                if (obj.result == "success") {
                    //微信注入权限接口
                    wx.config({
                        debug: true,//开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: obj.appId,// 必填，公众号的唯一标识
                        timestamp: obj.timestamp,// 必填，生成签名的时间戳
                        nonceStr: obj.nonceStr,// 必填，生成签名的随机串
                        signature: obj.signature,// 必填，签名，见附录1
                        jsApiList: [// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                            'onMenuShareTimeline',  //分享到朋友圈
                            'checkJsApi',
				            'onMenuShareAppMessage',	//分享给朋友
				            'onMenuShareQQ',	//QQ
				            'onMenuShareWeibo',	//微博
				            'onMenuShareQZone',	//分享QQ空间
				            'chooseWXPay'		//发起一个微信支付请求
                        ]
                    });
                } else {
                    alert("加载数据错误");
                }
            },
            error: function () {
                alert("系统请求异常！");
            }
        });
    }
    
    wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		init();
	});
    
    function init(){
    	wx.onMenuShareTimeline({
	        title: '分享朋友圈测试',// 分享标题
	        link: 'http://0dc41230.ngrok.io/hqc_mp/wx/index.html',// 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        imgUrl: 'https://static.oschina.net/uploads/space/2016/1212/174725_BuUt_2738789.png',// 分享图标
	        trigger: function (res) {  
	        	//点击分享的按钮事件
	            alert('分享朋友圈');
	        },
	        success: function (res) { // 用户确认分享后执行的回调函数
	        	//分享成功的事件
	            alert('分享朋友圈成功');
	            //window.location.href = "toIndex.shtml?tag4=" + 4;
	        },
	        cancel: function (res) {// 用户取消分享后执行的回调函数
	        	//取消分享的事件
	            alert('分享朋友圈已取消');
	            //window.location.href = "toIndex.shtml";
	        },
	        fail: function (res) {
	        	//分享失败的事件
	            alert(JSON.stringify(res));
	        }
	    });
	        
	    wx.onMenuShareAppMessage({
		    title: '分享给朋友测试', // 分享标题
		    desc: '分享的描述我可以选择不写吗', // 分享描述
		    link: 'http://0dc41230.ngrok.io/hqc_mp/wx/index.html', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: 'http://0dc41230.ngrok.io/hqc_mp/statics/images/octopus/t03.png', // 分享图标
		    type: 'link', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    trigger: function (res) {  
	        	//点击分享的按钮事件
	            alert('分享给朋友');
	        },
		    success: function () { 
		        // 用户确认分享后执行的回调函数
	            alert('分享给朋友成功');
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
	            alert('分享给朋友取消');
		    },
	        fail: function (res) {
	        	//分享失败的事件
	            alert(JSON.stringify(res));
	        }
		});
	    
	    wx.onMenuShareQQ({	//分享到QQ
		    title: '分享到QQ测试分享标题', // 分享标题
		    desc: '分享到QQ测试分享描述', // 分享描述
		    link: 'http://0dc41230.ngrok.io/hqc_mp/wx/index.html', // 分享链接
		    imgUrl: 'http://0dc41230.ngrok.io/hqc_mp/statics/images/octopus/t03.png', // 分享图标
		    trigger: function (res) {  
	        	//点击分享的按钮事件
	            alert('分享到QQ');
	        },
	        success: function () { 
		       // 用户确认分享后执行的回调函数 
		       alert('分享到QQ成功');
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
	            alert('分享到QQ取消');
		    },
	        fail: function (res) {
	        	//分享失败的事件
	            alert(JSON.stringify(res));
	        }
		});
		
		wx.onMenuShareWeibo({
		    title: '分享到微博测试分享标题', // 分享标题
		    desc: '分享到微博测试分享描述', // 分享描述
		    link: 'http://0dc41230.ngrok.io/hqc_mp/wx/index.html', // 分享链接
		    imgUrl: 'http://0dc41230.ngrok.io/hqc_mp/statics/images/octopus/t03.png', // 分享图标
	        trigger: function (res) {  
	        	//点击分享的按钮事件
	            alert('分享微博');
	        },
		    success: function () { 
		       // 用户确认分享后执行的回调函数
	            alert('分享到微博成功');
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
	            alert('分享到微博取消');
		    },
	        fail: function (res) {
	        	//分享失败的事件
	            alert(JSON.stringify(res));
	        }
		});
		
		wx.onMenuShareQZone({
		    title: '分享到QQ空间测试分享标题', // 分享标题
		    desc: '分享到QQ空间测试分享描述', // 分享描述
		    link: 'http://0dc41230.ngrok.io/hqc_mp/wx/index.html', // 分享链接
		    imgUrl: 'http://0dc41230.ngrok.io/hqc_mp/statics/images/octopus/t03.png', // 分享图标
	        trigger: function (res) {  
	        	//点击分享的按钮事件
	            alert('分享QQ空间');
	        },
		    success: function () { 
		       // 用户确认分享后执行的回调函数
	            alert('分享到QQ空间成功');
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
	            alert('分享到QQ空间取消');
		    },
	        fail: function (res) {
	        	//分享失败的事件
	            alert(JSON.stringify(res));
	        }
		});
		
		
		//获取网络状态接口
		wx.getNetworkType({
		    success: function (res) {
		        var networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
		        //alert(networkType);
		    }
		});
		
		
		//批量隐藏功能按钮接口
		wx.hideMenuItems({		//permission denied该公众号没有权限使用这个JSAPI
			// 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		    menuList: ["menuItem:share:QZone"]
			,success:function(res){
                        alert("功能按钮已隐藏");
                    },
	        fail: function (res) {
	        	//分享失败的事件
	            //alert(JSON.stringify(res));
	        }
		});
		
		//隐藏所有非基础按钮接口  //permission denied该公众号没有权限使用这个JSAPI
		wx.hideAllNonBaseMenuItem();
		
		wx.scanQRCode({
		    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		    	var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		    	alert(result);
			}
		});
    }
    
	 // 5.1 拍照、本地选图
	 var images = {
	    localId: [],
	    serverId: []
	 };
	 $('#chooseImage').click(function(){
	    wx.chooseImage({
		    success: function (res) {
	 	alert(1);
		        images.localId = res.localIds;
		        alert('已选择 ' + res.localIds.length + ' 张图片');
		    }
	    });
	 });


    $("#share").on("click", function () {
        $("#shareit").show();
        $("#body").hide();
    });
    $("#shareit").on("click", function () {
        $("#shareit").hide();
        $("#body").show();
    })
</script>
</html>