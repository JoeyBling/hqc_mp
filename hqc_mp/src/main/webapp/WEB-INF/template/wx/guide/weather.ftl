<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 天气预报</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);
        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/guide/weather.css" type="text/css" media="all"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
	<script src="${base}/statics/js/common.js"></script>
	<script type="text/javascript" src="${base}/statics/js/wx/guide/weather.js"></script>
	<script src="${base}/statics/libs/amazeui.min.js"></script>
	<script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
</head>
<body onload="startTime()">
<div class="container">
    <div class="city">
        <div class="title">
            <h2 id="province"></h2>
            <h3 id="city"></h3>
        </div>
        <div class="date-time">
            <div class="dmy">
                <div id="txt"></div>
                <div class="date">
                </div>
            </div>
            <div class="temperature">
                <p id="now"></p>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="forecast">
        <div class="forecast-icon">
        </div>
        <div class="today-weather">
            <h3 id="nwoHc"></h3>
            <ul>
                <li>今天 <span id="nowTC"></span></li>
                <li id="tomorrow"></li>
                <li id="afterTomorrow"></li>
                <li id="afterSecondDays"></li>
                <li id="afterThreeDays"></li>
            </ul>
        </div>
    </div>
    <div class="clear"></div>
</div>
<script>
    function startTime() {
        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        m = checkTime(m);
        s = checkTime(s);
        document.getElementById('txt').innerHTML =
                h + ":" + m + ":" + s;
        var t = setTimeout(startTime, 500);
    }
    function checkTime(i) {
        if (i < 10) {
            i = "0" + i
        }
        ; // add zero in front of numbers < 10
        return i;
    }
</script>
<script type="text/javascript">
    var mydate = new Date();
    var year = mydate.getYear();
    if (year < 1000);
    year += 1900;
    var day = mydate.getDay();
    var month = mydate.getMonth();
    var daym = mydate.getDate();

    if (daym < 10) {
        daym = "0" + daym;
    }
    var dayarray = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    var montharray = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    //		document.write(""+dayarray[day]+", "+montharray[month]+" "+daym+", "+year+"");
    document.write("" + year + "/" + montharray[month] + "/" + daym + "&nbsp;&nbsp;&nbsp;" + dayarray[day]);
</script>
</body>
</html>