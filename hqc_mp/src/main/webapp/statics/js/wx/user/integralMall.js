
/**
 * 商品列表
 */
$(function() {
	queryGoods();
	getmemberInfo();
		});
function getmemberInfo(){
	$("#Integerinfo").text("");
	$.ajax({
		url : hqc.base+"wx/integralMall/getMemberInfo",
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$("#Integerinfo").text(r.memberEntity.integral);
			  }else if(r.code===100){
				  layer.msg(r.msg, {
						icon : 5,
						time : 2000
							// 2秒关闭（如果不配置，默认是3秒）
						}, function() {
						parent.location.href = hqc.base + 'wx/user/login.html';
					});
			  }else{
					layer.msg(r.msg);
				}
		},
		
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
       });
}
function queryGoods(){
	$("#goodsType2").children('li').remove();
	$("#goodsType3").children('li').remove();
	$("#picDiv").children('div').remove()
	$.ajax({
		url : hqc.base+"wx/integralMall/getGoods",
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				var goodsList=r.goodsList;
				
				for(var i=0;i<goodsList.length;i++){
					if(i==0){
						$("#picDiv").append(
						   "<div class='item active'>"+
						   "    <img src='"+hqc.base + goodsList[i].goods_thumb+"' />"+
						   "</div>"
								)
					}else{
						$("#picDiv").append(
							"<div class='item'>"+
							"   <img src='"+hqc.base + goodsList[i].goods_thumb+"' />"+
							"</div>"
								)
					}
					if(goodsList[i].type==2||goodsList[i].type=='2'){
						var goodHtml="";
						if(goodsList[i].repertory==null||goodsList[i].repertory==0){
							goodHtml= "<li class='row'>" +
							   "    <div class='col-xs-5 col-md-5 item-img'>"+
							   "       <a href='#'><img src='"+hqc.base + goodsList[i].goods_thumb+"' /></a>"+
							   "    </div>"+
							   "    <div class='col-xs-7 col-md-7 item-content'>"+
							   "       <em class='title'>"+goodsList[i].goods_name+"</em>"+
							  /* "       <div class='about'>"+goodsList[i].about.substring(0,60)+"...</div>"+*/
							  
							   "       <div class='info'>"+
							   "            <input type='button' class='btn change' value='立即兑换' onclick='exchangeGoods("+goodsList[i].id+",2)'  disabled='disabled'/><span>"+goodsList[i].integral+"积分</span>"+
							   "       </div>"+
							   "    </div>"+
							   "</li>"		
						}else{
							goodHtml= "<li class='row'>" +
							   "    <div class='col-xs-5 col-md-5 item-img'>"+
							   "       <a href='#'><img src='"+hqc.base + goodsList[i].goods_thumb+"' /></a>"+
							   "    </div>"+
							   "    <div class='col-xs-7 col-md-7 item-content'>"+
							   "       <em class='title'>"+goodsList[i].goods_name+"</em>"+
							 /*  "       <div class='about'>"+goodsList[i].about.substring(0,60)+"...</div>"+*/
							  
							   "       <div class='info'>"+
							   "            <input type='button' class='btn change' value='立即兑换' onclick='exchangeGoods("+goodsList[i].id+",2)'  /><span>"+goodsList[i].integral+"积分</span>"+
							   "       </div>"+
							   "    </div>"+
							   "</li>"		
						}
						$("#goodsType2").append(goodHtml)
						
						
					}else{
						var cashHtml="";
						if(goodsList[i].repertory==null||goodsList[i].repertory==0){
							cashHtml= "<li class='row'>" +
							   "    <div class='col-xs-5 col-md-5 item-img'>"+
							   "       <a href='#'><img src='"+hqc.base + goodsList[i].goods_thumb+"' /></a>"+
							   "    </div>"+
							   "    <div class='col-xs-7 col-md-7 item-content'>"+
							   "       <em class='title'>"+goodsList[i].goods_name+"</em>"+
							  /* "       <div class='about'>"+goodsList[i].notice.substring(0,60)+"...</div>"+*/
							  
							   "       <div class='info'>"+
							   "            <input type='button' class='btn change' value='立即兑换' onclick='exchangeGoods("+goodsList[i].id+",1)'  disabled='disabled'/><span>"+goodsList[i].integral+"积分</span>"+
							   "       </div>"+
							   "    </div>"+
							   "</li>"		
						}else{
							cashHtml= "<li class='row'>" +
							   "    <div class='col-xs-5 col-md-5 item-img'>"+
							   "       <a href='#'><img src='"+hqc.base + goodsList[i].goods_thumb+"' /></a>"+
							   "    </div>"+
							   "    <div class='col-xs-7 col-md-7 item-content'>"+
							   "       <em class='title'>"+goodsList[i].goods_name+"</em>"+
							  /* "       <div class='about'>"+goodsList[i].notice.substring(0,60)+"...</div>"+*/
							  
							   "       <div class='info'>"+
							   "            <input type='button' class='btn change' value='立即兑换' onclick='exchangeGoods("+goodsList[i].id+",1)'  /><span>"+goodsList[i].integral+"积分</span>"+
							   "       </div>"+
							   "    </div>"+
							   "</li>"		
						}
						$("#goodsType3").append(cashHtml)
					}
					
				}
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
	});
}

/**
 * 兑换页面
 */
function exchangeGoods(id,type){
	
	window.location=hqc.base+"wx/integralMall/toIntegralMallExchange?goodsId="+id+"&type="+type;
	
}
/**
 * 时间转换
 * @param now
 * @returns {String}
 */
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
	if (second >= 1 && second <= 9) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + date + " " + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}

function reload(){
	window.location.href = location.href + '?time='
	+ ((new Date()).getTime())
}

var TabbedContent = {
		init: function() {	
			$(".tab_item").mouseover(function() {
				$(".hidden").hide();
			
				var background = $(this).parent().find(".moving_bg");
				
				$(background).stop().animate({
					left: $(this).position()['left']
				}, {
					duration: 300
				});
				
				TabbedContent.slideContent($(this));			
			});
		},
		init2: function() {	
			$(".tabslider ul").bind("swipeleft",function(){
				 TabbedContent.slideContent2($(this));
			});
			
		},
		init3: function() {	
			$(".tabslider ul").bind("swiperight",function(){
				 TabbedContent.slideContent3($(this));
			});		
		},
		
		slideContent: function(obj) {
			
			var margin = $(obj).parent().parent().find(".slide_content").width();
			margin = margin * ($(obj).prevAll().size() - 1);
			margin = margin * -1;
			
			
			$(obj).parent().parent().find(".tabslider").stop().animate({
				marginLeft: margin + "px"
			}, {
				duration: 300
			});
		},
		slideContent2: function(obj) {
			var margin = $(obj).parent().parent().width();			
			margin = margin * ($(obj).prevAll().size());
			
			//求出一共有几个滑动区块		
			var len=($(obj).siblings().size()-1);
			var wid=$(obj).parent().parent().width();		
			var totalwidth=len * wid;
			
				
			margin = margin * -1;
			
			if(margin>=-totalwidth){
				$(obj).parent().parent().find(".tabslider").stop().animate({
					marginLeft: margin + "px"
					
				}, {
					duration: 300
				});
			}
			var index=$(obj).index();		
			
			
			var background =$(obj).parent().parent().parent().find(".moving_bg");
			var tabs=$(obj).parent().parent().parent().find(".tab_item");
			
			$(background).stop().animate({
				left: tabs.eq(index).position()['left']
			}, {
				duration: 300
			});
		},
		
		slideContent3: function(obj) {
			var margin = $(obj).parent().parent().width();			
			margin = margin * ($(obj).prevAll().size()-1);
			
			//求出一共有几个滑动区块		
			var len=($(obj).siblings().size()-1);
			var wid=$(obj).parent().parent().width();		
			var totalwidth=len * wid;
			
				
			margin = -(margin-wid);
			
			
			if(margin <= 0){
				$(obj).parent().parent().find(".tabslider").stop().animate({
					marginLeft: margin + "px"
					
				}, {
					duration: 300
				});
			}
			var index=$(obj).index();
			if(index == 1){
				index=2;
			}
		
			var background =$(obj).parent().parent().parent().find(".moving_bg");
			var tabs=$(obj).parent().parent().parent().find(".tab_item");
			
			
			$(background).stop().animate({
				left: tabs.eq(index-2).position()['left']
			}, {
				duration: 300
			});
		}
	}

	$(document).ready(function() {
		TabbedContent.init();
		TabbedContent.init2();
		TabbedContent.init3();
	});















