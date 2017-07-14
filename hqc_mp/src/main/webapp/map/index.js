var _base =hqc.base ;

//右键选择起点位置
var layer_right = null; 
//当规划路径后用户的位置
var user_address=null;
//是否进行路径规划，用于判断是否进行路径上用户定位显示
var flag="false";
//获取终点位置的坐标
var endlat;
var ending
//是否进行二次路线规划
var flagagain="false";
var circle;
var projectId;
//路线
var polyline = null;//115.05719,29.049928
//var southWest = L.latLng(22.6020463673827,114.267425537109),110.334442,21.456649
//northEast = L.latLng(22.6468088856252,114.313044548035),117.058075,29.251419
var southWest = L.latLng(21.456649,110.334442),
northEast = L.latLng(29.251419,117.058075),
bounds = L.latLngBounds(southWest, northEast);
var markers;
var map = L.map('mapcontainer', {
scrollWheelZoom: true,
zoomControl: false,
attributionControl: false,
doubleClickZoom:true,
worldCopyJump: true,
maxBounds: bounds
}).setView(bounds.getCenter(), 14);

$(".zoomin").click(function() {
map.zoomIn();
});
$(".zoomout").click(function() {
map.zoomOut();
});
L.tileLayer(_base+'/map/tiles/{z}/{x}/{y}.png', {
maxZoom: 18,
minZoom: 14,
tileSize: 256,
continuousWorld: true
}).addTo(map);
map.on("click", function(e) {
console && console.log(e);
});
//起点图标
var startIcon = L.icon({
iconUrl: _base+'/map/images/start_icon.png',
iconSize: [21, 31],
iconAnchor: [21, 31],
popupAnchor: [-3, -31],
shadowUrl:  _base+'/map/images/start_icon_shadow.png',
shadowSize: [21, 31],
shadowAnchor: [21, 31]
});
//地图位置图标
var icon = L.icon({
iconUrl:  _base+'/map/images/icon.png',
iconSize: [21, 31],
iconAnchor: [21, 31],
popupAnchor: [-3, -31],
shadowUrl:  _base+'/map/images/icon_shadow.png',
shadowSize: [21, 31],
shadowAnchor: [21, 31]
});
//用户位置图片

var userIcon = L.icon({
	iconUrl: _base+'/map/images/mapPeople.png',
	iconSize: [21, 31],
	iconAnchor: [21, 31],
	popupAnchor: [-3, -31],
	shadowUrl: _base+'/map/images/mapPeopleShow.png',
	shadowSize: [21, 31],
	shadowAnchor: [21, 31]
	});

//终点图标
var endIcon = L.icon({
iconUrl: _base+'/map/images/end_icon.png',
iconSize: [21, 31],
iconAnchor: [21, 31],
popupAnchor: [-3, -31],
shadowUrl: _base+'/map/images/end_icon_shadow.png',
shadowSize: [21, 31],
shadowAnchor: [21, 31]
});
//给对应的景点标点 TODO

var style_station = {
	    radius: 12,
	    stroke:false,
	    weight: 1,
	    opacity: 1,
	    fillOpacity: 1,
	    // color: '#00ABDC',
	};

$(function(){
	var url=location.href.split('#')[0];
	 $.ajax({
         type: "post",
         dataType: "json",
         data: {
             url: url
         },
         url: "../getConfig",
         success: function (obj) {
             if (obj.result == "success") {
                 //微信注入权限接口
            	 wx.config({  
            		    //debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
            		    appId: obj.appId,// 必填，公众号的唯一标识
                        timestamp: obj.timestamp,// 必填，生成签名的时间戳
                        nonceStr: obj.nonceStr,// 必填，生成签名的随机串
                        signature: obj.signature,// 必填，签名，见附录1
            		    jsApiList: ['checkJsApi',
            		                'chooseImage',
            		                'previewImage',
            		                 'uploadImage',
            		                 'downloadImage',
            		                  'getNetworkType',//网络状态接口
            		                  'openLocation',//使用微信内置地图查看地理位置接口
            		                  'getLocation' //获取地理位置接口
            		               ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
            		});
             } else {
                 alert("加载数据错误");
             }
         },
         error: function () {
             alert("系统请求异常！");
         }
     });
	 
});
var latitude;
var lngitude;


function getUserLatLng(){
	/*latitude="22.633364";
	 lngitude="114.280715";
		 showUserAddress(latitude,lngitude);
		setTimeout('getUserLatLng()',3000);*/
	wx.ready(function(){ 
		 wx.getLocation({  
		      success: function (res) {
			    	 latitude=res.latitude;
			    	 lngitude=res.longitude;
			    	 if(user_address!=null){
			    		 if(latitude!=user_address.getLatLng().lat||lngitude!=user_address.getLatLng().lng){//过了3秒的时候，如果用户移动了位置，就重新标点，不然不要重新标当前用户的位置.
			    			 showUserAddress(latitude,lngitude);
					    	 	 setTimeout('getUserLatLng()',3000); 
					    	 }else{
					    		 setTimeout('getUserLatLng()',3000); 
					    	 }
					 }else{
						 showUserAddress(latitude,lngitude);
						 setTimeout('getUserLatLng()',3000); 
					 }
			    	
		      },  
		      cancel: function (res) {  
		        alert('用户拒绝授权获取地理位置');  
		      }  
		    }); 
	});
}
function showUserAddress(latitude,lngitude){
	 var latlng = new Array();
		latlng.push({lat:parseFloat(latitude),lng:parseFloat(lngitude)});
		if (!bounds.contains(latlng[0])){
			alert('当前不在景区范围！');
			//setTimeout('getUserLatLng()',5000); 
		}
		else{
			var lat = latlng[0].lat,lng = latlng[0].lng;
			/*var latlng1 = new Array();
			 latitude1="22.632631";
			 lngitude1="114.280477";
			 latlng1.push({lat:parseFloat(latitude1),lng:parseFloat(lngitude1)});*/
			//if(flagagain=="false"){
			 if(flag=="false"){
				 if(layer_right!=null){
						layer_right.remove();
					}
				 if(polyline!=null){
						polyline.remove();
					}
				// L.circleMarker().setRadius(10);
				
				 layer_right = L.marker(latlng[0],{icon:startIcon}).addTo(map).bindPopup('<div class="text-inner">我在这里</div>').openPopup();
				 
			 }else{
				 if(user_address!=null){
					 user_address.remove();
				 }
				 user_address=L.marker(latlng[0],{icon:userIcon}).addTo(map).bindPopup('<div class="text-inner">现在的位置</div>').openPopup();
				
			 }
			 if(circle!=null){
				 circle.remove();
			 }
			 circle  = L.circle([latitude, lngitude], 100, {     color: '#FFF8DC',        fillColor: '#FFF8DC',     fillOpacity: 0   }).addTo(map)//对用户位置半径100米画圆
			//}
			//setTimeout('getUserLatLng()',5000); 
		}
}
function queryProject(event){
		getUserLatLng();
		 get_category();
	$.ajax({
		url:'../map/queryProject',
		type:'post',
		datatype:'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data:{id:event},
		success :function(result){
			if(result.code===0){
				$.each(result.list,function(index,content){
					L.marker([parseFloat(content.lat),parseFloat(content.lng)],{icon:get_icon(""+content.projectName+"")}).addTo(map).bindPopup(get_about(index,""+content.projectName+"",parseFloat(content.lat),parseFloat(content.lng),_base+content.thumbUrl,"",""));
				});
				$("#modalContent").html("");
				$.each(result.list,function(index,content){
					$("#modalContent").append("<div class='row line'>" +
							"<a href='#'>" +
						  "<div class='col-xs-4'>" +
						"	<a class='thumbnail'>" +
							"  <img src='"+_base+content.thumbUrl+"' alt='' class='img-circle'>" +
						"	</a>" +
						"  </div>" +
						"  <div class='col-xs-8'>" +
						"	<h5 class='list-group-item-heading'>"+content.projectName+"</h5>" +
						"	<p class='list-group-item-text'>"+content.about+"</p>" +
						"	<p class='list-group-item-text'><span><a href='javascript:leftclick("+content.lat+","+content.lng+","+content.id+",1);'>到这里去</a>" +
						"  </div>" +
						"</a>" +
					"</div>")
					
				});
			}else{
				
			}
		},
		error:function(){
		}
	})
	   
}
/*L.marker([22.633364,114.280715],{icon:get_icon('因特拉根兰花园')}).addTo(map).bindPopup(get_about(1,"因特拉根兰花园",22.633364,114.280715,_base+"\/map\/images\/1.png","",""));
L.marker([22.639355,114.272475],{icon:get_icon('西峡回廊')}).addTo(map).bindPopup(get_about(2,"西峡回廊",22.639355,114.272475,_base+"\/map\/images\/2.png","",""));
L.marker([22.636088,114.268184],{icon:get_icon('九曲十八弯')}).addTo(map).bindPopup(get_about(3,"九曲十八弯",22.636088,114.268184,_base+"\/map\/images\/1.png","",""));
L.marker([22.633493,114.275737],{icon:get_icon('大剧院')}).addTo(map).bindPopup(get_about(4,"大剧院",22.633493,114.275737,_base+"\/map\/images\/2.png","",""));
L.marker([22.631374,114.282174],{icon:get_icon('西峡谷')}).addTo(map).bindPopup(get_about(5,"西峡谷",22.631374,114.282174,_base+"\/map\/images\/1.png","",""));

*/
//路线坐标集合
var paths = new Array();

/*map.on('contextmenu', function(ev) {
	ev.latlng.lat=latitude;
	ev.latlng.lng=lngitude;
	var ev=new Array();
	var latlng=new Array();
	latlng.push({lat:"22.633364",lng:"114.280715"})
	//ev.push({ev:latlng});
	var latlng =ev.latlng;
	if (!bounds.contains(latlng)){
		alert('当前不在景区范围！');
	}
	else{
		if(layer_right!=null){
			layer_right.remove();
		}
		if(polyline!=null){
			polyline.remove();
		}
		var lat = ev.latlng.lat,lng = ev.latlng.lng;
		layer_right = L.marker(latlng,{icon:startIcon}).addTo(map).bindPopup('<div class="text-inner">我在这里</div>').openPopup();
	}

});*/
//点击弹窗时
map.on('popupopen', function(e) {
var lat = $(e.popup._content).find(".input_lat").val();
var lng = $(e.popup._content).find(".input_lng").val();
var id = $(e.popup._content).find(".input_id").val();
if(id!=null && id!="undefined"){
	leftclick(lat,lng,id,0);
}
});
window.onload = function() {
	get_category_id(topRequest.QueryString("id"));
};



//加载分类菜单
function get_category_id(id){
	if(id===null){
		$.ajax({
			type : "POST",
			url : _base + "/wx/map/category",
			dataType : "json",
			success : function(data) {
				if (data.code === 0) {
					$.each(data.list, function(index, content){
					    if(index==0){
					    	queryProject(content.id);
					    }
					});
					//queryProject($("#projectid").val());
				} else {
					layer.open({
						  content: '菜单加载有误，请刷新重试！'
						  ,time: 3
					});
				}
			}
		});
	}else{
		queryProject(id);
	}
}

function get_category(){
	$.ajax({
		type : "POST",
		url : _base + "/wx/map/category",
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				var strHtml = "";
				strHtml+="<input type='hidden' id='projectid'/>";
				$.each(data.list, function(index, content){
				    strHtml += '<div class=\"btn-group\" id=\"'+content.id+'\">'
								+'<a href=\"?id='+content.id+'\" class=\"btn btn-default\">'+ content.categoryName +'</a>'
								+'</div>';
				});
				$("#map_nav").html(strHtml);
			} else {
				layer.open({
					  content: '菜单加载有误，请刷新重试！'
					  ,time: 3
				});
			}
		}
	});
}
//景点，项目简介
function get_about(id,title,lat,lng,thumb,remark,desc){
	var str_html="<div class=\"popup-heading\">"+ title +"</div>";
		str_html+="<div class=\"popup-body\">";
		str_html+=" <a class=\"popup-thumbnail\"><img src=\""+thumb+"\"></a>";
		str_html+="</div>";
		str_html+="<div class=\"popup-footer\">";
		str_html+="<button class=\"btn btn-warning f_right\" onclick=\"map_polyline()\"><span class=\"glyphicon glyphicon-chevron-right\" aria-hidden=\"true\"></span> 到这里去</button>";
		str_html+="<p id=\"p_"+ id +"\" class=\"f_left\">&nbsp;</p>";
		str_html+="<input type=\"hidden\" class=\"input_lat\" value=\""+ lat +"\">";
		str_html+="<input type=\"hidden\" class=\"input_lng\" value=\""+ lng +"\">";
		str_html+="<input type=\"hidden\" class=\"input_id\" value=\""+ id +"\">";
		str_html+="</div>";
	return str_html;
}
//设置标记图标
function get_icon(title){
	var icon = L.divIcon({
		html:"<div class='biaoji_icon'>"+ title +"</div>",
		iconUrl: _base+'/map/images/icon.png',
		shadowUrl: _base+'/map/images/icon_shadow.png'
	});
	return icon
}
//确认导航动作 ispolyline是否立即画线
function leftclick(lat,lng,id,ispolyline){
	if(layer_right!=null){
		var start;
		if(flag=="false"){
			 start = layer_right.getLatLng().lng+","+layer_right.getLatLng().lat;
		}else{
			 start = user_address.getLatLng().lng+","+user_address.getLatLng().lat;
			if(layer_right!=null){
					layer_right.remove();
				}
		
			 flagagain="true";
		}
		var end = lng+","+lat;
		$("#p_"+ id).html("计算距离中…");
		start_end(start,end,id,ispolyline);
		 endlat=lat;
		 ending=lng;
	}
	else{
		if(ispolyline){
			alert("不在当前景区范围");
		}
	}
}

//导航计算  start=开始位置  end=结束位置
function start_end(start,end,id,ispolyline){
		$.ajax({
		url: "http://restapi.amap.com/v3/direction/driving?origin="+ start +"&destination="+ end +"&output=json&key=b68a241a46a361551410b26a89844363",
		type: 'GET',
		dataType: 'JSONP',//here
		success: function (data) {
			if(data.status=='1'){
				paths = new Array(); 
				projectId=id;
				var origin = data.route.origin;//起点
				var destination = data.route.destination;//终点
				var distance=data.route.paths[0].distance;//距离 单位米
				var duration=data.route.paths[0].duration;//预计耗时 单位秒
				var steps=data.route.paths[0].steps;//所有经过的路线信息
				$(steps).each(function (i){
				   var this_path = this.polyline.split(';');
				   for(j=0;j<this_path.length;j++){
						this_path[j] = this_path[j].split(',');
						this_path[j] = [this_path[j][1],this_path[j][0]];
				   }
				   paths[i] = this_path;
				});
				$("#p_"+ id).html("距离："+distance+"米");
				if(ispolyline){
					$('#myModal').modal('hide');
					
					map_polyline();
				}
			}
			else{
				alert(data.message);
			}
		}
	});
}
//路径规划
function map_polyline(){
	if(paths.length>0){
		
		if(polyline!=null){
			polyline.remove();
		}
		if(flagagain=="true"){
			 layer_right = L.marker(user_address.getLatLng(),{icon:startIcon}).addTo(map).bindPopup('<div class="text-inner">我在这里</div>').openPopup();
		}
		polyline = L.polyline(paths,{color:'#4DDC26',weight:6,opacity:0.8}).addTo(map);
		map.fitBounds(polyline.getBounds());
		flag="true";
	}
	else{
		alert("不在当前景区范围");
	}
}
//快速导航
function show_model(){
	$('#myModal').modal('show');
}

//setTimeout('getLatLng()',5000); 