$(function() {
			queryList(1, 1);
		});
/**
 * 点击门票 或全部商品时执行
 */
function query(n) {
	var queryTicket = $("#queryTicket");
	var queryAll = $("#queryAll");

	if (n == 1) {
		queryTicket.addClass("active-nav");
		queryAll.removeClass("active-nav");
		window.location = "#my-top";
		queryList(1);
	} else {
		queryAll.addClass("active-nav");
		queryTicket.removeClass("active-nav");
		window.location = "#my-top";
		queryList(1);
	}
}
/**
 * 查询列表
 * 
 * @param n
 */
function queryList(n, v) {
	$.ajax({
		type : "POST",
		url : hqc.base + "wx/ticket/getTicketList",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		dataType : "json",
		data : {
			page : n == null ? 1 : n,
			queryBanner : v

		},
		success : function(result) {
			if (result.code == 0) {
				$("#tickets").html("");
				if (v != null && v == 1) {
					addBanner(result.banneres);
				}
				if (result.page.list == null || result.page.list.length < 1) {
					$("#tickets").append("<img src='" + hqc.base
							+ "statics/images/no.png' width='100%'/>"
							+ "<br/><center>票已售空！</center>");
					return;
				}

				$.each(result.page.list, function(index, content) {
					// 因为张票代码太长，所以分段写,不命名
					// 点击相关
					var ticket1 = "<div class='buyitem' onclick='queryinfo("
							+ content.id + ")'>" + "<div class='bdr-bom'>";
					var ticketName = content.ticketName == null
							? 0
							: content.ticketName;
					// 图片
					var ticket2 = '<div class="img"><img src="' + hqc.base
							+ content.thumbUrl + '" alt="' + ticketName
							+ '" class="swiper-lazy swiper-lazy-loaded">';
					// 折扣
					var ticket3 = '<div class="discount"><span>'
							+ (content.discount * 10).toFixed(1)
							+ '折</span></div></div>	';
					var advance = content.advance == 0
							? ''
							: "<span>提前购票</span>";
					// 标题
					var ticket4 = '<div class="item-info"><div class="solid-h"><p class="g-title">'
							+ ticketName
							+ '</p><p class="g-intro"></p><p class="g-tag">'
							+ advance + '</p></div>';

					var saleCount = content.saleCount == null
							? 0
							: content.saleCount;
					var price = content.price == null ? 0 : content.price;
					var marketPrice = content.marketPrice == null
							? 0
							: content.marketPrice;
					// 价格相关
					var ticket5 = '<p class="g-price vblock"><span class="s-price"><em>¥</em>'
							+ price
							+ '</span><del>¥'
							+ marketPrice
							+ '</del><span>已售'
							+ saleCount
							+ '</span></p></div></div></div>';
					$("#tickets").append(ticket1 + ticket2 + ticket3 + ticket4
							+ ticket5);
				});
				init();
			} else {
				AMUI.dialog.alert({
							content : result.msg,
							onConfirm : function() {
								// console.log('close');
							}
						});
				if (result.code === 100) { // 先登录
					setTimeout(function() {
								location.href = hqc.base + 'wx/oauthLogin';
							}, 2000);
				}
			}
		},
		error : function(result) {
			AMUI.dialog.alert({
						content : '系统异常,请联系管理员!',
						onConfirm : function() {
							// console.log('close');
						}
					});
		}
	});
}

function init() {
	var pageNum = 2;
	$('#tickets').dropload({
		scrollArea : window,
		domDown : {
			domClass : 'dropload-down',
			domRefresh : '<div class="dropload-refresh">↑上拉加载更多-门票列表</div>',
			domLoad : '<div class="dropload-load"><span class="loading"></span>加载中-门票列表...</div>',
			domNoData : '<div class="dropload-noData">暂无数据-门票列表</div>'
		},
		distance : 50,
		loadDownFn : function(me) {
			$.ajax({
				type : 'POST',
				url : hqc.base + "wx/ticket/getTicketList",
				dataType : 'json',
				data : {
					page : pageNum
				},
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				success : function(r) {
					pageNum++;
					// 为了测试，延迟1秒加载
					setTimeout(function() {
								// 每次数据加载完，必须重置
								me.resetload();
							}, 500);
					if (r.code === 0) {
						if (r.page.list == null || r.page.list.length < 1) {
							// 锁定
							me.lock();
							// 无数据
							me.noData();
							return;
						}
						$.each(r.page.list, function(index, content) {
							// 因为张票代码太长，所以分段写,不命名
							// 点击相关
							var ticket1 = "<div class='buyitem' onclick='queryinfo("
									+ content.id
									+ ")'>"
									+ "<div class='bdr-bom'>";
							// 图片
							var ticket2 = '<div class="img"><img src="'
									+ hqc.base
									+ content.thumbUrl
									+ '" alt="'
									+ content.ticketName
									+ '" class="swiper-lazy swiper-lazy-loaded">';
							// 折扣
							var ticket3 = '<div class="discount"><span>'
									+ (content.discount * 10).toFixed(1)
									+ '折</span></div></div>	';

							var advance = content.advance == 0
									? ''
									: "<span>提前购票</span>";
							// 标题
							var ticket4 = '<div class="item-info"><div class="solid-h"><p class="g-title">'
									+ content.ticketName
									+ '</p><p class="g-intro"></p><p class="g-tag">'
									+ advance + '</p></div>';
							// 价格相关
							var ticket5 = '<p class="g-price vblock"><span class="s-price"><em>¥</em>'
									+ content.price
									+ '</span><del>¥'
									+ content.marketPrice
									+ '</del><span>已售'
									+ content.saleCount
									+ '</span></p></div></div></div>';

							$(".dropload-down").before(ticket1 + ticket2
									+ ticket3 + ticket4 + ticket5);
						});

					} else {
						AMUI.dialog.alert({
									content : r.msg,
									onConfirm : function() {
										// console.log('close');
									}
								});
						if (result.code === 100) { // 先登录
							setTimeout(function() {
										location.href = hqc.base
												+ 'wx/oauthLogin';
									}, 2000);
						}
					}

				},
				error : function(r) {
					AMUI.dialog.alert({
								content : '系统错误，请联系管理员!',
								onConfirm : function() {
									// console.log('close');
								}
							});
					// 即使加载出错，也得重置
					me.resetload();
				}
			});
		},
		threshold : 50
	});
}
/**
 * 将集合里图片放到轮播中
 * 
 * @param banneres
 */
function addBanner(banneres) {
	if (banneres == null || banneres.length < 1) {
		return
	}

	var $slider = $('#demo-slider-0');
	$.each(banneres, function(index, content) {
				var banner = "<li><a href='"
						+ content.url
						+ "'><img  class='swiper-lazy swiper-lazy-loaded' alt='' src='"
						+ content.thumbUrl + "' height='180px'></a></li>";
				$slider.flexslider('addSlide', banner);
			});
	$slider.flexslider('removeSlide', $("#demo1"));
	$slider.flexslider('removeSlide', $("#demo2"));
}
/**
 * 获取当前门票详情
 * 
 * @param id
 */
function queryinfo(id) {
	parent.location.href = hqc.base + 'wx/ticket/ticketInfo?id=' + id;
}