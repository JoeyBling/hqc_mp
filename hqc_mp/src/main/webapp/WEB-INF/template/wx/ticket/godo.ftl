<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>${hqc} - 在线购票</title>
    <link rel="stylesheet" href="${base}/statics/css/wx/ticket/orderpay.css">
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/user/basic.css">
    <link rel="stylesheet" href="${base}/statics/css/wx/ticket/toBuyTicket.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1,user-scalable=no">
</head>
<body>
<div class="booked" node-name="booked">
    <div class="wrap-box">
        <div class="goods-info">
            <div class="title">${ticket.ticketName!''}</div>
            <div class="price"><em>¥</em><span id="price" node-name="unitprice">${ticket.price!''}</span></div>
        </div>
        <div class="use-date" node-name="usedate">
            <div class="label">使用日期</div>
            <div class="datelist adaptive" node-name="datelist">
                <div class="date-item" id="todate">
                    <span class="date-text">${one}</span>
                    <span id="todate_one" class="date-num">${oneDate}</span>
                    <input id='onePrice' value="${onePrice}" type="hidden"/>
                </div>
                [#if two!="不能购买"]
                <div class="date-item" id="totodate">
                    <span class="date-text">${two}</span>
                    <span id="totodate_one" class="date-num">${twoDate}</span>
                    <input id='twoPrice' value="${twoPrice}" type="hidden"/>
                </div>
                [/#if]
                <div class="date-item datemore" id="moredate">
                    <span class="date-text">更多日期</span>
                </div>
            </div>
        </div>
        <div class="buynumber" node-name="buynumber">
            <div class="label adaptive">购买数量 <span class="limit">(同一用户每日限购5张)</span></div>
            <div class="quantity-wrap">
                <div class="quantity-form" node-name="spinner" data-event="bind" data-trigger="spinner">
                    <a href="javascript: ;" class="num-btn increment disabled" id="jian">
                        <img src="">
                    </a>
                    <input type="text" readonly="readonly" autocomplete="off" id="num" class="quantity" id="firstTicket"
                           value="1" node-name="quantity" data-ruler="quantity" unselectable="on">
                    <a href="javascript:;" class="num-btn decrement" id="jia"></a>
                </div>
            </div>
        </div>
    </div>
    <div class="wrap-box contact-info">
        <form id="info_form">
            <div class="form-group" node-name="mobileRow">
                <div class="label">手机号</div>
                <div class="item-feild adaptive">
                    <input type="tel" class="form-control empty shake" value="" placeholder="接收凭证短信" name="mobile"
                           tabindex="1" maxlength="11">
                </div>
                <!--<div class="extra disabled" node-name="contact">-->
                <!--<i class="icon-contact"></i>-->
                <!--</div>-->
            </div>
        </form>
    </div>
	[#if (cashList?size>0 ) ]
    <div class="wrap-box couponbox" onclick="openCashList()">
        <div class="label">
            <i class="icon-coupon"></i>
            <span>优惠券</span>
        </div>
        <div class="output">
            <span class="no-coupon">点击使用优惠卷</span>
        </div>
    </div>
	[#else]
		<div class="wrap-box couponbox">
        <div class="label">
            <i class="icon-coupon"></i>
            <span>优惠券</span>
        </div>
        <div class="output">
            <span class="no-coupon">暂无优惠卷</span>
        </div>
    </div>
	[/#if]
    <div class="detailtab" node-name="detailtab">
        <div class="tab-menu" node-name="tabMenu">
            <div class="menu-item active" data-index="0" id="yuding">预定须知</div>
            <div class="menu-item" data-index="1" id="xiangqing">商品详情</div>
            <!--	<div class="swiper-scrollbar-drag" id="show_" node-name="navbar" style="transform: translate3d(83.5125px, 0px, 0px); visibility: visible;"></div>-->
            <!--	<div class="swiper-scrollbar-drag" id="hide_" node-name="navbar" style="transform: translateX(255px); visibility: visible;display:none"></div>-->
        </div>
        <div class="tab-content" node-name="tabContent">
            <div class="swiper-container tab-container swiper-container-horizontal swiper-container-fade swiper-container-android"
                 id="detailSwiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide swiper-slide-active" id="about" style="width: 100%;  z-index: 2; ">
                        <div class="article">
                            ${ticket.about!''}
                        </div>
                    </div>
                    <div class="swiper-slide swiper-slide-next" id="ticketContent" style="width: 100%;   display:none">
                        <div class="article">
                            ${ticket.ticketContent!''}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="checkout">
    <div class="terms" node-name="protocol" id="checkbox">
        <div class="pact-check"><i class="pact-checkbox"></i></div>
        <div class="pact-intro">
            <h2>我已阅读并同意“预订须知”条款</h2>
            <p node-name="validtips"></p>
        </div>
    </div>
    <div class="sheet">
        <div class="total"><span><span class="price">
				<em>¥</em>
					<span id="allprice">${ticket.price!''}</span>
						</span>
							<span class="units">(<em id="zhang" node-name="unitary">1</em>张)</span>
								</span>
        </div>
        <div class="submitbtn" node-name="submit" id="submitbtn"><span>提交订单</span></div>
    </div>
</div>

<!--弹窗：我的优惠劵-->
<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">
    <div class="am-modal-dialog">
        <div class="am-modal-hd" style="font-size:16px">我的优惠劵</div>
        <div class="am-modal-bd">
            <ul class="am-list menuList">
                [#list cashList as x]
                <li class="content" name="content">
                    <div class="left">
                        <p>${x.cashCouponName}</p>
                    </div>
                    <div class="right"><input type="radio" value="${x.id}" name="cashCoupon">

                        <div class="bottomDiv">
								<span>有效时间至:[@formatTime unix="${(x.createTime+2592000)?c}" pattern="yyyy-MM-dd"] [/@formatTime]
							</span></div>
                    </div>
                    <input type="hidden" name="faceValue" value="${x.faceValue}">
                </li>
                <div class="clearfix"></div>
                [/#list]
            </ul>
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
    </div>
</div>

[#-- Confirm UI --]
<div class="am-modal am-modal-confirm" tabindex="-1" id="agree">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">${confirmTitle!"提示"}</div>
        <div class="am-modal-bd">
            请先阅读预定须知，并确认您已同意
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>我知道了</span>
        </div>
    </div>
</div>

[#-- 购票日期价格Model --]
<div class="view view-main">
    <form>
        <div class="page" data-page="index">
        </div>
    </form>
</div>
<script type="text/template7" id="calendarDom">
    <section class="calendar-modal">
        <div class="calendar-mask complete-center"></div>
        <!-- 遮罩层 -->
        <div class="calendar-content complete-center">
            <section class="box-header-navbar border-bottom-solid">
                <div class="left icon" data-func="back">
                    <i class="icon iconfont icon-iosarrowleft"></i>
                </div>
                <h3 class="center">选择日期</h3>

                <div class="right" data-func="complete">
                    <span class="link-title">完成</span>
                </div>
            </section>
            <section class="calendar-info">
                <div class="calendar-title clearfix border-bottom-solid box-sizing">
                    <section class="weekend">日</section>
                    <section>一</section>
                    <section>二</section>
                    <section>三</section>
                    <section>四</section>
                    <section>五</section>
                    <section class="weekend">六</section>
                </div>
            </section>
            <div id="calendar" class="calendar-box"></div>
        </div>
    </section>
</script>
<!-- 加载 Framework7 脚本-->
<script type="text/javascript" src="${base}/statics/libs/framework7.min.js"></script>
<!-- 加载 velocity 动画序列库-->
<script type="text/javascript" src="${base}/statics/libs/velocity.min.js"></script>
<script type="text/javascript" src="${base}/statics/libs/velocity.ui.min.js"></script>
<script type="text/javascript" src="${base}/statics/js/wx/ticket/calendar-ticket.js"></script>
<script type="text/javascript">
    /**
     * 将Date转为yyyy-MM-dd时间格式
     *
     * @param {}
     *            now
     * @return {}
     */
    function formatDateTime(now) {
        var year = now.getFullYear();
        var month = now.getMonth() + 1;
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        var date = now.getDate();
        if (date >= 1 && date <= 9) {
            date = "0" + date;
        }
        return year + "-" + month + "-" + date;
    }


    var dayPricemMap;
    $.ajax({
        url: '${base}/wx/ticket/price?id=${ticket.id}',
        rtype: 'json',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        data: {},
        type: 'post',
        success: function (r) {
            if (r.code === 0) {
                dayPricemMap = r.dayPricemMap;
                init();
            } else {
                AMUI.dialog.alert({
                    content: r.msg,
                    onConfirm: function () {
                        // console.log('close');
                    }
                });
                if (r.code === 100) { // 先登录
                    setTimeout(function () {
                        var url = location.href; // 获取当前url地址
                        var paraString = url.replace(/\//g, "%2F").replace(
                                /:/g, "%3A").replace(/=/g, "%3D").replace(
                                /\?/g, "%3F");
                        parent.location.href = '${base}/wx/user/login.html?returnUrl=' + paraString;
                    }, 2000);
                }
            }
        },
        error: function () {
            AMUI.dialog.alert({
                content: '系统异常,请联系管理员!',
                onConfirm: function () {
                    // console.log('close');
                }
            });
        }
    });


    function init() {
        // 初始化 应用程序 framework7
        var myApp = new Framework7({
            init: false
        });

        // 出口选择器引擎定义
        var $$ = Dom7;

        //当前门票价格
        var nowPrice;

        myApp.onPageInit('index', function (page) {
            var $$el,
                    $$addSubDom,             // 加减区的Dom
                    calendarTemp,            // 日历的template
                    $c_modalDom,             // 日历的模态层Dom
                    $c_maskDom,              // 日历的遮罩层Dom
                    $c_contentDom,           // 日历的内容层Dom
                    $c_backLink,             // 日历头部的返回链接
                    $c_completeLink,         // 日历头部的完成链接
                    oSelectDate,             // 选择的时间值
                    calendarIns,             // 日历对象
                    calendarDom;             // 日历列表的容器Dom

            $$el = $$(page.container);
            $$addSubDom = $$el.find("#adder-subtractor");

            // 今日明日选中高亮切换
            $(".time-block:not(.more)").on("touchstart", function () {
                $(this).addClass("active").siblings(".time-block:not(.more)").removeClass("active");
            });

            // 初始化给页面增加日历的DOM
            calendarTemp = $('script#calendarDom').html();
            $("body").append(calendarTemp);

            $c_modalDom = $('.calendar-modal');
            $c_maskDom = $c_modalDom.find('.calendar-mask');
            $c_contentDom = $c_modalDom.find('.calendar-content');
            $c_backLink = $c_modalDom.find('[data-func=back]');
            $c_completeLink = $c_modalDom.find('[data-func=complete]');

            oSelectDate = formatDateTime(new Date(${startDate?c}));
            calendarIns = new calendar.calendar({
                el: '#calendar',
                count: ${count},
                selectDate: new Date(${startDate?c}),
                selectDateName: '购票',
                minDate: new Date(${startDate?c}),
                maxDate: new Date(${endDate?c}),
                isShowHoliday: true,
                isShowWeek: false,
                isShowTicketPrice: true,
                dayPriceData: dayPricemMap,
                noTicketText: '<span class="no-ticket">无票</span>'
            });

            $c_completeLink.on("touchstart", function () {
                nowPrice = dayPricemMap[oSelectDate];
                //重新计算价格
                $("#price").text(nowPrice);
                var num = $("#num").val();
                var price = $("#price").text();
                var f = parseInt(num);
                $("#allprice").text(parseInt(price) * parseInt(f));

                var _dateVal = oSelectDate.replace(/^\d{4}-{1}/, '');
                $("#totodate").css({"background": "#fff", "color": "black"})
                $("#totodate_one").css("color", "#fc7700");
                $("#todate").css({"background": "#fff", "color": "black"})
                $("#todate_one").css("color", "#fc7700");

                var _tplDom = '<span class="date-text creat-block">已选</span>' +
                        '<span id="selectdate_one" class="date-num">' + _dateVal + '</span>';
                $(".date-item.datemore").html(_tplDom);
                $(".date-item.datemore").css({"background": "#fc7700", "color": "#fff"});
                $("#selectdate_one").css("color", "#fff");

                // 今日明日选中高亮切换
                $(".time-block:not(.more)").on("touchstart", function () {
                    $(this).addClass("active").siblings(".time-block:not(.more)").removeClass("active");
                });
                // 给选择的日期高亮
                $("section.time-selection-box div.creat-block").addClass("active").siblings(".time-block:not(.more)").removeClass("active");
                // 关闭日历
                closeCalendar();
            });

            // 定义打开日历的序列函数方法
            var calendarOpen = [
                {e: $c_modalDom, p: {opacity: 1}, o: {delay: 0, duration: 0, display: "block"}},
                {
                    e: $c_maskDom,
                    p: {opacity: 1},
                    o: {delay: 0, duration: 400, easing: [.2, 1.14, .65, 1.5], display: "block", sequenceQueue: false}
                },
                {
                    e: $c_contentDom,
                    p: {translateX: "100%"},
                    o: {delay: 0, duration: 0, display: "block", sequenceQueue: false}
                },
                {
                    e: $c_contentDom,
                    p: {translateX: 0},
                    o: {delay: 0, duration: 400, easing: [0, 0, 0.2, 1], sequenceQueue: false}
                },
            ];

            // 定义关闭日历的序列函数方法
            var calendarClose = [
                {
                    e: $c_contentDom,
                    p: "reverse",
                    o: {delay: 0, duration: 300, easing: [.2, 1.14, .65, 1.5], display: "none", sequenceQueue: false}
                },
                {
                    e: $c_maskDom,
                    p: "reverse",
                    o: {delay: 0, duration: 300, easing: [.2, 1.14, .65, 1.5], display: "none", sequenceQueue: false}
                },
                {
                    e: $c_modalDom,
                    p: "reverse",
                    o: {delay: 0, duration: 300, easing: [.2, 1.14, .65, 1.5], display: "none"}
                },
            ];

            // 更多日期 || 打开日历
            $(".date-item.datemore").on("touchstart", function () {
                openCalendar();
            });

            // 日历页的返回按钮
            $c_backLink.on("touchstart", function () {
                closeCalendar();
            });

            calendarDom = $((calendarIns.el.selector).toString());
            calendarDom.bind('afterSelectDate', function (e, val) {
                var curItem = val.curItem,
                        date = val.date,
                        dateName = val.dateName;

                oSelectDate = date;
            });

            // 将$ .Velocity.mock设置为任意乘数，以加快或减慢页面上的所有动画
            $.Velocity.mock = 1;

            // 启用日期
            function openCalendar() {
                $.Velocity.RunSequence(calendarOpen);
            }

            // 关闭日历
            function closeCalendar() {
                $.Velocity.RunSequence(calendarClose);
            }

        });
        //初始化app
        myApp.init();
    }
</script>
</body>
<script type="text/javascript" src="${base}/statics/js/wx/ticket/toBuyTicket.js"></script>
</html>