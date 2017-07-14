$(function() {
	queryInfo(); 
		});
function queryInfo(){
	var goodsId=$("#goodsId").val();
	var type=$("#type").val();
	if(type==2||type=='2'){
		exchangeGoods(goodsId,type)
	}else{
		exchangeCash(goodsId,type)
	}
}
		

/**
 * 兑换页面
 */
function exchangeGoods(id,type){
	$("#goodsId").val("");
	$("#daysLimit").val("");
	$("#endTime").val("");
	$("#repertory").val("");
	$("#integral").val("");
	$("#maxExchange").val("");
	$("#dayExchange").val("");
	$("#exchangeCount").val(1);
	$("#goodsName").text("");
	$("#integralDiv").text("");
	$("#notice").text("");
	$("#about").text("");
	 $("#temDayExchange").text("");
	 $("#exchangeCode").val("");
	 $("#exchangeCodeVid").val("");
	 $("#finalIntegral").val("");
	 $("#type").val("");
	 $("#bianSpan").text("");
	  $("#bianSpan1").text("");
	
	$("#dateUl").children("li").remove();
	

	$.get(hqc.base+"wx/integralMall/getGoodInfo/" + id, function(r) {

		$("#goodsId").val(r.entity.id);
		$("#daysLimit").val(r.entity.daysLimit);
		$("#endTime").val(r.entity.endTime);
		$("#repertory").val(r.entity.repertory);
		$("#integral").val(r.entity.integral);
		$("#maxExchange").val(r.entity.maxExchange);
		$("#dayExchange").val(r.entity.dayExchange);
		 $("#finalIntegral").val(r.entity.integral);
		 $("#type").val(type);
		 
		 
		var date=formatDate(new Date(r.entity.endTime*1000))
		$("#goodsName").text(r.entity.goodsName);
	    $("#integralDiv").text(r.entity.integral+" 积分");
	    if(r.entity.dayExchange==0||r.entity.dayExchange=="0"){
	    	 $("#temDayExchange").text("(不限制兑换数量)");
	    }else{
	    	$("#temDayExchange").text("(同一用户每日限兑"+r.entity.dayExchange+"张)")
	    }
	    $("#notice").append(r.entity.notice);
	    $("#about").append(r.entity.about);	
	   // getMonthDay(r.entity.daysLimit);
		$("#bianSpan").text(r.entity.integral+"分");
		$("#bianSpan1").text("（1张）");
		
		getafterDate(r.entity.daysLimit,r.entity.endTime,r.limitDate);
	});
	
	
}

/**
 * 时间转化
 */
function getafterDate(daysLimit,endTime,limitDate){
	
	var nowDate=new Date();
	var month = nowDate.getMonth() + 1;
    var afterDate1 = nowDate.getDate()+1;
    var afterDate2 = nowDate.getDate()+2;
    var year=nowDate.getFullYear();
    if(daysLimit==1||daysLimit=='1'){
    	$("#afterTomorrowSpan").css("display", "none");
    	
    	$("#afterMoreSpan").css("display", "none");
    }else if(daysLimit==2||daysLimit=='2'){
    	$("#afterMoreSpan").css("display", "none");
    }else{
    		var maxDate = "2999-12-12";
    		if(limitDate != null && limitDate != 0){
    			maxDate = formatDateDay(new Date(limitDate*1000));
    		}
    		if(endTime != null && endTime != 0 ){
    			if(limitDate != null && limitDate != 0){
    				if(new Date(endTime) < new Date(limitDate) ){
    					maxDate = formatDateDay(new Date(endTime*1000));
    				}
    			}else{
    				maxDate = formatDateDay(new Date(endTime*1000));
    			}
    		} 
    		var tDate = new Date();
    		tDate.setDate(tDate.getDate() + 1);
    	    var c = new LCalendar();
    		c.init({
    			'trigger': '#openDate',//标签id
    		    'type': 'date',//date 调出日期选择 datetime 调出日期时间选择 time 调出时间选择 ym 调出年月选择
    		    'minDate':formatDateDay(tDate),//最小日期 注意：该值会覆盖标签内定义的日期范围
    		    'maxDate':maxDate//最大日期 注意：该值会覆盖标签内定义的日期范围
    		});
    }
    $("#tomorrow").text("");
    $("#tomorrowValue").val("");
    $("#afterTomorrow").text("");
    $("#afterTomorrowValue").val("");
    $("#tomorrow").text(month+"-"+afterDate1);
    $("#tomorrowValue").val(year+"-"+month+"-"+afterDate1);
    $("#afterTomorrow").text(month+"-"+afterDate2);
    $("#afterTomorrowValue").val(year+"-"+month+"-"+afterDate2);
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

/**
 * 格式化时间（yyyy-mm-dd）
 */
function formatDateDay(now) {
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
	return year + "-" + month + "-" + date;
}
/**
 * 增加兑换数量
 */

function addCount(){
	var tempCount=$("#TempxchangeCount").text();
	var integral=$("#finalIntegral").val();//积分
	var changCount=parseInt(tempCount)+1;
	var dayExchange=$("#dayExchange").val();
	if(dayExchange!=0||dayExchange!="0"){

		if(parseInt(dayExchange)>parseInt(tempCount)){
			$("#TempxchangeCount").text("");
			$("#TempxchangeCount").text(changCount);
            
			$("#exchangeCount").val("");
			$("#exchangeCount").val(changCount);
			$("#integral").val("");
			$("#integral").val(changCount*parseInt(integral));
			/*$("#integralDiv").text("")
			$("#integralDiv").text(changCount*parseInt(integral)+"积分")*/
			$("#bianSpan").text("");
			$("bianSpan1").text("");
			$("#bianSpan").text(changCount*parseInt(integral)+"分");
			$("#bianSpan1").text("（"+changCount+"张）");
			
		}
	}else{
		$("#TempxchangeCount").text("");
		$("#TempxchangeCount").text(changCount);
		$("#exchangeCount").val("");
		$("#exchangeCount").val(changCount);
		$("#integral").val("");
		$("#integral").val(changCount*parseInt(integral));
		/*$("#integralDiv").text("")
		$("#integralDiv").text(changCount*parseInt(integral)+"积分")*/
		$("#bianSpan").text("");
		$("bianSpan1").text("");
		$("#bianSpan").text(changCount*parseInt(integral)+"分");
		$("#bianSpan1").text("（"+changCount+"张）");
	}
	
}
/**
 * 减少兑换数量
 */
function miCount(){
	var integral=$("#finalIntegral").val();//积分
	var tempCount=$("#TempxchangeCount").text();
	if(tempCount==1||tempCount=="1"){
		$("#miBtn").attr("disable", "disable");
	}else{
		var changCount=parseInt(tempCount)-1;
		$("#TempxchangeCount").text("");
		$("#TempxchangeCount").text(changCount);
		$("#exchangeCount").val("");
		$("#exchangeCount").val(changCount);
		$("#integral").val("");
		$("#integral").val(changCount*parseInt(integral));
		/*$("#integralDiv").text("")
		$("#integralDiv").text(changCount*parseInt(integral)+"积分")*/
		$("#bianSpan").text("");
		$("bianSpan1").text("");
		$("#bianSpan").text(changCount*parseInt(integral)+"分");
		$("#bianSpan1").text("（"+changCount+"张）");
	}
	
	
}

/**
 * 兑换操作
 */
function saveOrUpdateGoddsIntegral(){
	 $("#saveOrUpdateBtn").prop("disabled","disabled" );
	var integral=$("#integral").val();
	var goodsId=$("#goodsId").val();
	var exchangeCount=$("#exchangeCount").val();
	var personPhone=$("#personPhone").val();
	var goodsType=$("#type").val();
	var tempDate=$("#tempDate").val();

	var reg = /^(13|14|15|17|18)[0-9]{8}[0-9]$/;
	if (!reg.test(personPhone)) {
        layer.msg("请输入正确的手机号");
        $("#saveOrUpdateBtn").prop("disabled",false );
        return false;
    }else if($("#yueCheck").get(0).checked==false){
		layer.msg("请勾选同意兑换须知");
		 $("#saveOrUpdateBtn").prop("disabled",false );
		return false;
	}else{
		var url;
		if(goodsType==2||goodsType=="2"){
			url= hqc.base+"wx/integralMall/addExchangeGoodsRecordIntegral";
			if(tempDate==null||tempDate==""){
				 $("#saveOrUpdateBtn").prop("disabled",false );
				layer.msg("请选择使用日期");
				return false;
			}
		}
		if(goodsType==1||goodsType=="1"){
			url= hqc.base+"wx/integralMall/addExchangeCashRecordIntegral";
		}
		$.ajax({
			url : url,
			datatype : 'json',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			 data: {
				 integral: integral,goodsId:goodsId,
				 exchangeCount:exchangeCount,
				 personPhone:personPhone,
				 goodsType:goodsType,
				 tempDate:tempDate
		        },
			type : 'post',
			success : function(r) {
				
				if (r.code === 0) {
					//layer.msg("操作成功");
					   AMUI.dialog.alert({
			                content: '操作成功!',
			                onConfirm: function () {
			                    window.location.href = hqc.base+"wx/user/integralMall.html" + '?time='
											+ ((new Date()).getTime())
			                }
			            });
	                   /* window.location.href = location.href + '?time='
									+ ((new Date()).getTime())*/
	                
				} else {
					
					 AMUI.dialog.alert({
			                content: r.msg,
			                onConfirm: function () {
			                	parent.location.href = hqc.base + '/wx/user/login.html';
			                }
			            });
	                   /* window.location.href = location.href + '?time='
									+ ((new Date()).getTime())*/
				}
			},
			error : function() {
				layer.msg("系统异常,请联系管理员!");
			}
		});
	}
	
	
}


function reload(){
	window.location.href = hqc.base+"wx/user/integralMall.html" + '?time='
	+ ((new Date()).getTime())
}
/**
 * 兑换代金卷
 */
function exchangeCash(id,type){
	$("#goodsId").val("");
	$("#daysLimit").val("");
	$("#endTime").val("");
	$("#repertory").val("");
	$("#integral").val("");
	$("#maxExchange").val("");
	$("#dayExchange").val("");
	$("#exchangeCount").val(1);
	$("#goodsName").text("");
	$("#integralDiv").text("");
	$("#notice").text("");
	$("#about").text("");
	 $("#temDayExchange").text("");
	 $("#exchangeCode").val("");
	 $("#exchangeCodeVid").val("");
	 $("#finalIntegral").val("");
	 $("#type").val("");
	 $("#bianSpan").text("");
	  $("bianSpan1").text("");
	/*$("#noticeOraboutDiv").remove();*/
	
	$("#aboutNav").remove();
	$("#noticeNav").css("width","100%");
	$("#noticeOraboutDiv").css("width","100%");
	 $("#dateDiv").remove();
	$.get(hqc.base+"wx/integralMall/getCashInfo/" + id, function(r) {
		$("#goodsId").val(r.entity.id);
		$("#repertory").val(r.entity.repertory);
		$("#integral").val(r.entity.integral);
		$("#maxExchange").val(r.entity.maxExchange);
		$("#dayExchange").val(r.entity.dayExchange);
		 $("#finalIntegral").val(r.entity.integral);
		 $("#type").val(type);
		
		$("#goodsName").text(r.entity.cashCouponName);
		
	    $("#integralDiv").text(r.entity.integral+" 积分");
	    if(r.entity.dayExchange==0||r.entity.dayExchange=="0"){
	    	 $("#temDayExchange").text("(不限制兑换数量)");
	    }else{
	    	$("#temDayExchange").text("(同一用户每日限兑"+r.entity.dayExchange+"张)")
	    }
	    $("#notice").append(r.entity.about);
	    $("#bianSpan").text(r.entity.integral+"分");
		$("#bianSpan1").text("（1张）");
		
	});
}

/**
 * 得到一月内的日期
 */
/*function getMonthDay(daysLimit){
	
	$("#morAndmorDate").children("li").remove();
	 var startDate = new Date();  
	 var startYear = startDate.getFullYear();
	 var starMonth = startDate.getMonth() + 1;
	 var startDay = startDate.getDate();
	 var start=startYear + "-" + starMonth + "-" + startDay;
	 //var endDate=new Date()+30; 
	 var tempDay=30;
	 if(daysLimit==0||daysLimit>30){
		 tempDay=30;
	 }else{
		 tempDay=daysLimit;
	 }
	 var endDate = new Date(startYear,starMonth,(parseInt(startDay)+parseInt(tempDay)));
	 var endYear = endDate.getFullYear();
	 var endMonth = endDate.getMonth() ;
	 var endDay = endDate.getDate();
	 var end=endYear + "-" + endMonth + "-" + endDay;
	 var beginDate = getDate(start)  
	 var endDate=getDate(end);
	    var i=1;
	while((endDate.getTime()-beginDate.getTime())>=0){  
	      var year = beginDate.getFullYear();  
	      var month = beginDate.getMonth().toString().length==1?"0"+beginDate.getMonth().toString():beginDate.getMonth();  
	      var day = beginDate.getDate().toString().length==1?"0"+beginDate.getDate():beginDate.getDate();  
	      beginDate.setDate(beginDate.getDate()+1); 
	  //"<button type='button'  class='date-item' value='"+year+"-"+month+"-"+day+"' name='dateBtn' id='"+btn+"' onclick='getDataValue(this.id)'>"+month+"-"+day+"</button>"+
	      var btn="h"+i;
	      var btnValue="h"+i+"1";
	      "<span  class='lineSpan'  id='"+btn+"' onclick='getDataValue(this.id)'>"+month+"-"+day+" " +
		  " </span>"+
		  " <input type='hidden' value='"+year+"-"+month+"-"+day+"' id='"+btnValue+"'>"+
	     //<span class="date-item">06-11</span><span class="date-item">06-11</span><span class="date-item">06-11</span> value='"+year+"-"+month+"-"+day+"'
	      $("#morAndmorDate").append("" +
	    		  "<li>"+
	    		  "<button type='button'  class='lineSpan' value='"+year+"-"+month+"-"+day+"' name='dateBtn' id='"+btn+"' onclick='getDataValue(this.id)'>"+month+"-"+day+"</button>"+
	      		  "</li>");
	       i++;
	    }  
	 
	
	
}*/

function getDate(datestr){  
    var temp = datestr.split("-");  
    var date = new Date(temp[0],temp[1],temp[2]);  
    return date;  
  }  

function getDataValue(id){
    var DateValue = document.getElementById(id).value;
    $("#tempDate").val("");
    $("#tempDate").val(DateValue);
    $("#checkDate").text("");
	$("#checkDate").text("当前已选日期："+DateValue);
	 //changeSpanColor();
	document.getElementById(id).style.backgroundColor  = 'rgb(252, 119, 0)';
	var obj= document.getElementsByTagName('button');
	for(var i=0;i<obj.length;i++){
		if(obj[i]!=document.getElementById(id)){
		obj[i].style.backgroundColor = 'white';
		}
		
	}
	 /*var len = document.getElementsByTagName('span').length;
		var obj;
		for( var i = 0; i < len; i ++) {
		  document.getElementsByTagName('span')[i].onclick = function() {
		   if(obj)obj.style.backgroundColor = 'white';
		    this.style.backgroundColor = 'red';
		obj=this;
		  };
		}*/
   
}
/**
 * 得到明天或者后天日期*/
function gettomorrowValue(){
	
	 var DateValue=$("#tomorrowValue").val();
	  $("#tempDate").val("");
	   $("#tempDate").val(DateValue);
	   $("#checkDate").text("");
	   $("#checkDate").text("当前已选日期："+DateValue);
}
function getafterTomorrowValue(){
	
	 var DateValue=$("#afterTomorrowValue").val();
	  $("#tempDate").val("");
	   $("#tempDate").val(DateValue);
	   $("#checkDate").text("");
	   $("#checkDate").text("当前已选日期："+DateValue);
}

/**
 * 打开选择更多日期页面
 */
function getDivDateValue(){
		
//	$("#moerDateDiv").css("display", "block");
//	var daysLimit=$("#daysLimit").val();
//	getMonthDay(daysLimit);
}
/**
 */
function closeMoerDateDiv(){
	$("#moerDateDiv").css("display", "none");
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

function changeSpanColor(){
	var len = document.getElementsByTagName('span').length;
	var obj;
	for( var i = 0; i < len; i ++) {
	  document.getElementsByTagName('span')[i].onclick = function() {
	   if(obj)obj.style.backgroundColor = 'white';
		   this.style.backgroundColor = 'rgb(252, 119, 0)';
	    obj=this;
	  };
	}
	}



