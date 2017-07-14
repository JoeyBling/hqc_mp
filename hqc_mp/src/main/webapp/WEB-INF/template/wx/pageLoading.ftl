<!--加载样式-->
<script>
    $(window).load(function () {
        $("#status").fadeOut();
        $("#preloader").delay(${milli!350}).fadeOut("slow");

    });
</script>

<!--页面加载 开始-->
<div id="preloader">
    <div id="status">
        <p class="center-text">加载中…<em>网速有点不给力哦!</em></p>
    </div>
</div>
<!--页面加载 结束-->