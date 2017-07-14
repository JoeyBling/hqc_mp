<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 修改头像</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <link href="${base}/statics/css/wx/user/style.css" rel="stylesheet" type="text/css">
    [#include "/wx/common_css.ftl"]
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${base}/statics/js/wx/user/userHeadPort.js"></script>
<body style="background:#000">
<!--头部-->
<div id="user_head">
    <a id="left_ico" href="javascript:history.go(-1);">
        <i class="icon iconfont">&#xe645;</i>
    </a>
    <span>个人头像</span>
    <a id="s_dpage" href="javascript:void(0);">
        <i class="icon iconfont">&#xe633;</i>
    </a>
</div>

<a href="javascript:void(0);" class="logoBox" id="logoBox">
</a>

<div id="dataURL" style="display:none"></div>
<div class="htmleaf-container">
    <div id="clipArea"></div>
    <div id="view"></div>
</div>
<div class="btn-1">
    <button onclick="update()">确认更换</button>
</div>
<div id="dpage">
    <a href="javascript:void(0);"><input type="button" name="file" class="button" value="选取照片">
        <input id="file" type="file" onchange="setImagePreview()" accept="image/*" multiple/></a>
    <a href="javascript:void(0);" class="qx">
        <button id="clipBtn">截取图片</button>
    </a>
</div>
<script>window.jQuery || document.write('<script src="${base}/statics/libs/jquery-2.1.1.min.js"><\/script>')</script>
<script src="${base}/statics/libs/iscroll-zoom.js"></script>
<script src="${base}/statics/libs/hammer.js"></script>
<script src="${base}/statics/libs/jquery.photoClip.js"></script>
<script>
    var obUrl = ''
    //document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    $("#clipArea").photoClip({
        width: 280,
        height: 280,
        file: "#file",
        view: "#view",
        ok: "#clipBtn",
        loadStart: function () {
            console.log("照片读取中");
        },
        loadComplete: function () {
            console.log("照片读取完成");
        },
        clipFinish: function (dataURL) {
            $("#dataURL").html(dataURL);
        }
    });
</script>
<script>
    $(function () {
        $("#logoBox,#s_dpage").click(function () {
            $(".htmleaf-container").fadeIn(300);
            $("#dpage").addClass("show");
        })
        $("#clipBtn").click(function () {
            $("#logoBox").empty();
            $('#logoBox').append('<img src="' + imgsource + '" align="absmiddle" style=" width:100%;">');
            $(".htmleaf-container").hide();
            $("#dpage").removeClass("show");
        })
    });
</script>
<script type="text/javascript">
    $(function () {
        jQuery.divselect = function (divselectid, inputselectid) {
            var inputselect = $(inputselectid);
            $(divselectid + " small").click(function () {
                $("#divselect ul").toggle();
                $(".mask").show();
            });
            $(divselectid + " ul li a").click(function () {
                var txt = $(this).text();
                $(divselectid + " small").html(txt);
                var value = $(this).attr("selectid");
                inputselect.val(value);
                $(divselectid + " ul").hide();
                $(".mask").hide();
                $("#divselect small").css("color", "#333")
            });
        };
        $.divselect("#divselect", "#inputselect");
    });
</script>
<script type="text/javascript">
    $(function () {
        jQuery.divselectx = function (divselectxid, inputselectxid) {
            var inputselectx = $(inputselectxid);
            $(divselectxid + " small").click(function () {
                $("#divselectx ul").toggle();
                $(".mask").show();
            });
            $(divselectxid + " ul li a").click(function () {
                var txt = $(this).text();
                $(divselectxid + " small").html(txt);
                var value = $(this).attr("selectidx");
                inputselectx.val(value);
                $(divselectxid + " ul").hide();
                $(".mask").hide();
                $("#divselectx small").css("color", "#333")
            });
        };
        $.divselectx("#divselectx", "#inputselectx");
    });
</script>
<script type="text/javascript">
    $(function () {
        jQuery.divselecty = function (divselectyid, inputselectyid) {
            var inputselecty = $(inputselectyid);
            $(divselectyid + " small").click(function () {
                $("#divselecty ul").toggle();
                $(".mask").show();
            });
            $(divselectyid + " ul li a").click(function () {
                var txt = $(this).text();
                $(divselectyid + " small").html(txt);
                var value = $(this).attr("selectyid");
                inputselecty.val(value);
                $(divselectyid + " ul").hide();
                $(".mask").hide();
                $("#divselecty small").css("color", "#333")
            });
        };
        $.divselecty("#divselecty", "#inputselecty");
    });
</script>
<script type="text/javascript">
    $(function () {
        $(".mask").click(function () {
            $(".mask").hide();
            $(".all").hide();
        })
        $(".right input").blur(function () {
            if ($.trim($(this).val()) == '') {
                $(this).addClass("place").html();
            }
            else {
                $(this).removeClass("place");
            }
        })
    });
</script>
<script>
    $("#file0").change(function () {
        var objUrl = getObjectURL(this.files[0]);
        obUrl = objUrl;
        console.log("objUrl = " + objUrl);
        if (objUrl) {
            $("#img0").attr("src", objUrl).show();
        }
        else {
            $("#img0").hide();
        }
    });
    function qd() {
        var objUrl = getObjectURL(this.files[0]);
        obUrl = objUrl;
        console.log("objUrl = " + objUrl);
        if (objUrl) {
            $("#img0").attr("src", objUrl).show();
        }
        else {
            $("#img0").hide();
        }
    }
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
</script>
<script type="text/javascript">
    function setImagePreview() {
        var preview, img_txt, localImag, file_head = document.getElementById("file"),
                picture = file_head.value;
        if (!picture.match(/.jpg|.gif|.png|.bmp/i)) {
            layer.msg("您上传的图片格式不正确，请重新选择！");
        }
    }
</script>
</body>
</html>