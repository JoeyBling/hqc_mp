<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>导航菜单</title>
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${base}/statics/css/font-awesome.min.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/router.js"></script>
    <script src="${base}/statics/js/octopus/sys/index.js"></script>
    <script type="text/javascript">
    
        //导航切换
        function navigation(e){
        	$(".menuson li.active").removeClass("active");
            $(e).addClass("active");
        };
          
        //展开 
        function upDown(e){
            var $ul = $(e).next('ul');
            $('dd').find('ul').slideUp();
            if ($ul.is(':visible')) {
                $(e).next('ul').slideUp();
            } else {
                $(e).next('ul').slideDown();
            }
        };
    </script>
    <style>
    dl,dt,dd,span{margin:0;padding:0;display:block;}
    </style>
</head>
<body style="background:#f0f9fd;">

<dl class="leftmenu">
</dl>
</body>
</html>