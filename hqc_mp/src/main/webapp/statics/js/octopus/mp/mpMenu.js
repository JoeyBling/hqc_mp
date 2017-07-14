function queryList(n) {
	$("#list").html("");
	$.ajax({
		url : '../mpMenu/list',
		datatype : 'json',
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				if(r.menu!=null){
					$.each(r.menu.menu.buttons, function(index, content) {
						if(content.subButtons.length==0){
							var type="";
							if(content.type=="view"){
								type="<span class='label label-primary'>跳转页面</span>";
							}
							if(content.type=="click"){
								type="<span class='label label-primary'>发送消息</span>";
							}
							if(content.type=="miniprogram"){
								type="<span class='label label-primary'>跳转小程序</span>";
							}
							$("#list").append("<tr><td><input name='ids' type='checkbox'/></td>" +
									"<td>"+(index+1)+"</td>" +
									"<td>"+content.name+"</td>" +
									"<td>"+type+"</td>"+
									"<td><span class='label label-info'>不含有</span></td>" +
									"<td><span class='label label-info'>0</span></td></tr>");
						}else{
							$("#list").append("<tr><td><input name='ids' type='checkbox'/></td>" +
									"<td>"+(index+1)+"</td>" +
									"<td>"+content.name+"</td>" +
									"<td><a href='javascript:void(0)' onclick='selectChildMenu("+index+")' style='color:blue'>查看子菜单</a></td>"+
									"<td><span class='label label-info'>含有</span></td>" +
									"<td><span class='label label-info'>"+content.subButtons.length+"</span></td></tr>");
							}
					});
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
function selectChildMenu(event){
	$("#childMenulist").html("");
	$.ajax({
		url : '../mpMenu/list',
		datatype : 'json',
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				
				$.each(r.menu.menu.buttons, function(index, content) {
					if(content.subButtons.length>0&&event==index){
						for(var i=0;i<content.subButtons.length;i++){
							var type="";
							if(content.subButtons[i].type=="view"){
								type="<span class='label label-primary'>跳转页面</span>";
							}
							if(content.subButtons[i].type=="click"){
								type="<span class='label label-primary'>发送消息</span>";
							}
							if(content.subButtons[i].type=="miniprogram"){
								type="<span class='label label-primary'>跳转小程序</span>";
							}
							$("#childMenulist").append("<tr><td>"+(i+1)+"</td><td>"+content.subButtons[i].name+"</td><td>"+content.name+"</td><td>"+type+"</td></tr>")
						}
					}
				});
				queryChildMenu()
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
	});
}
function queryChildMenu(){
		layer.open({
			type : 1,
			offset : '50px',
			skin : 'layui-layer-molv',
			title : '查看子菜单',
			area : ['795px', '430px'],
			shade : 0,
			shadeClose : false,
			content : jQuery("#showChildMenu"),
			btn : ['关闭'],
			btn1 : function(index) {
				layer.close(index)
				
				
			}

		});
		
	
}
$(function() {
			queryList();
		})
function clickpage(event) {
	$("#page").val(event);
	queryList();
}


	/**
	 * 全选
	 */
	$("#selectAll").click(function() {
				$('input[name="ids"]').prop("checked", this.checked);
				if (this.checked) {
					$(".tablelist tr:gt(0)").css("background", "#e5ebee");
				} else {
					$('input[name="ids"]')
					$(".tablelist tr:gt(0)").css("background", "");
				}
			});

function openAddMpMenu() {
	// 清空
	/** 是否是修改 */
	$("#showMenu").html("");
	 array = new Array();
	 Menu=new Array();
	 Menunum="0";
	 menu0num="0";
	 menu1num="100";
	 menu2num="200";
	 menu0=new Array();
	 menu1=new Array();
	 menu2=new Array();
	openupdate="false";
	Menunum=parseInt(Menunum)+parseInt("0");
	Menu.push({
		Menuid:Menunum
	});
	$("#Menulist").html("");
	$("input[name='Menuname']").val("");
	$("input[name='url']").val("");
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
	$("#addnew0").html("")
	$("#showMenuType0").css("display","block")
	$("#mpmenulist").css("display","block");
	$("#addfristmenu").html("")
	$("#showfristMenu").html("");
	$("#addfristmenu").append("<div id='showChildMenu0' style='display:none'><div class='form-group'><div class='col-sm-2'></div><div class='col-sm-2' id='addnewchildmenu0'><a href='javascript:void(0)' onclick='addChildMenu(0)' style='color:blue'>添加新的子菜单栏</a></div></div></div>")
	$("#showfristMenu").append("<div id='showMenuType0'><div class='form-group'><div class='col-sm-2 control-label'>菜单内容</div> <div class='col-sm-3'><label class='radio-inline'><input type='radio' name='type'  value='0' onclick='checkRadio('news')'/> 发送消息</label><label class='radio-inline'><input type='radio' name='type' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label><label class='radio-inline'><input type='radio' name='type' value='2' onclick='checkRadio('program')'/> 跳转小程序</label><label class='radio-inline'><a style='color:blue' href='javascript:void(0)' onclick='addChildMenu(0)'>添加子菜单</a></label></div></div><div class='form-group'><div class='col-sm-2 '></div> <div class='col-sm-3'>订阅者点击该菜单会跳到以下链接</div></div><div class='form-group'><div class='col-sm-2 control-label'>页面地址</div> <div class='col-sm-3'> <input type='text' class='form-control' name='url' placeholder='地址'/></div></div></div>")
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}

/**
 * 保存或更新操作
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function saveOrUpdate(event) {
	var mpMenuName="";
	var type="";
	var url="";
	if(openupdate=="false"){
		var mpMenuName=$("input[name='Menuname']").val();
		var type=$("input[name='type']:checked").val();
		var url=$("input[name='url']").val();
	}
	
	if(Menu.length>0){
		for(var i=0;i<Menu.length;i++){
			if(openupdate=="false"&&i==0){
				
			}else{
				if($("input[name='url"+Menu[i].Menuid+"']").val()==""){
					$("input[name='url"+Menu[i].Menuid+"']").val("undefined");
				}
				if(mpMenuName.length==0&&openupdate=="true"){
						mpMenuName=$("input[name='Menuname"+Menu[i].Menuid+"']").val()
						type=$("input[name='type"+Menu[i].Menuid+"']:checked").val();
						url=$("input[name='url"+Menu[i].Menuid+"']").val();
				}else{
					mpMenuName=mpMenuName+","+$("input[name='Menuname"+Menu[i].Menuid+"']").val()
					type=type+","+$("input[name='type"+Menu[i].Menuid+"']:checked").val();
					url=url+","+$("input[name='url"+Menu[i].Menuid+"']").val();
				}
			}
		}
	}
	var menu0Name="";
	var menu0Type="";
	var menu0Url="";
	var menu1Name="";
	var menu1Type="";
	var menu1Url="";
	var menu2Name="";
	var menu2Type="";
	var menu2Url="";
	if(array.length>0){
		if(array[0].menu0!==undefined&&array[0].menu0.length>0){
			for(var i=0;i<menu0.length;i++){
				var number=menu0[i].menu0id;
				if(number===undefined){
					number=menu0[i].menu1id
				}
				if(number===undefined){
					number=menu0[i].menu2id
				}
				if(i==0){
					menu0Name=$("input[name='childMenuMenuname"+number+"']").val();
					menu0Type=$("input[name='childMenutype"+number+"']:checked").val();
					menu0Url=$("input[name='childMenuUrl"+number+"']").val();
				}else{
					menu0Name=menu0Name+","+$("input[name='childMenuMenuname"+number+"']").val();
					menu0Type=menu0Type+","+$("input[name='childMenutype"+number+"']:checked").val();
					menu0Url=menu0Url+","+$("input[name='childMenuUrl"+number+"']").val();
				}
			}
		}
		if(array[0].menu1!==undefined&&array[0].menu1.length>0){
			for(var i=0;i<menu1.length;i++){
				
				var number=menu1[i].menu0id;
				if(number===undefined){
					number=menu1[i].menu1id
				}
				if(number===undefined){
					number=menu1[i].menu2id
				}
				if(i==0){
					menu1Name=$("input[name='childMenuMenuname"+number+"']").val();
					menu1Type=$("input[name='childMenutype"+number+"']:checked").val();
					menu1Url=$("input[name='childMenuUrl"+number+"']").val();
				}else{
					menu1Name=menu1Name+","+$("input[name='childMenuMenuname"+number+"']").val();
					menu1Type=menu1Type+","+$("input[name='childMenutype"+number+"']:checked").val();
					menu1Url=menu1Url+","+$("input[name='childMenuUrl"+number+"']").val();
				}
			}
		}
		
		if(array[0].menu2!==undefined&&array[0].menu2.length>0){
			for(var i=0;i<menu2.length;i++){
				var number=menu2[i].menu0id;
				if(number===undefined){
					number=menu2[i].menu1id
				}
				if(number===undefined){
					number=menu2[i].menu2id
				}
				if(i==0){
					menu2Name=$("input[name='childMenuMenuname"+number+"']").val();
					menu2Type=$("input[name='childMenutype"+number+"']:checked").val();
					menu2Url=$("input[name='childMenuUrl"+number+"']").val();
				}else{
					menu2Name=menu2Name+","+$("input[name='childMenuMenuname"+number+"']").val();
					menu2Type=menu2Type+","+$("input[name='childMenutype"+number+"']:checked").val();
					menu2Url=menu2Url+","+$("input[name='childMenuUrl"+number+"']").val();
				}
			}
		}
	}
	layer.confirm('发布成功后会覆盖原版本，且将在24小时内对所有用户生效，确认发布？？', function() {
	$.ajax({
				type : "POST",
				url : "../mpMenu/save",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
						mpMenuName:mpMenuName,
						type:type,
						url:url,	
						 menu0Name:menu0Name,
						 menu0Type:menu0Type,
						 menu0Url:menu0Url,
						 menu1Name:menu1Name,
						 menu1Type:menu1Type,
						 menu1Url:menu1Url,
						 menu2Name:menu2Name,
						 menu2Type:menu2Type,
						 menu2Url:menu2Url
				},
				success : function(r) {
					if (r.code === 0) {
						layer.alert('操作成功', function(index) {
									layer.close(index);
									reload();
								});
					} else {
						layer.alert(r.msg);
					}
				},
				error : function() {
					layer.alert('系统出现异常!', function() {
								reload();
							});
				}
			});
	});
}

/**
 * 删除操作
 */
function del() {
	layer.confirm('确定要删除菜单？', function() {
				$.ajax({
							type : "POST",
							url : "../mpMenu/delete",
							success : function(r) {
								if (r.code == 0) {
									layer.alert('操作成功', function(index) {
												layer.close(index);
												reload();
											});
								} else {
									layer.alert(r.msg);
								}
							},
							error : function() {
								layer.alert('系统出现异常!', function() {
											location.reload();
										});
							}
						});
			});
}


// 获取选中的多选框的值
function checkData() {
	var ids = "";
	$("#list td").each(function() {
		if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
			if (ids.length == 0) {
				ids = $(this).find("input[type=checkbox]:checked").val()
			} else {
				ids = ids + ","
						+ $(this).find("input[type=checkbox]:checked").val();
			}
		}
	});
	return ids;
}

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
var usefrist="false";
var usesecond="false";
var Menu=new Array();
var Menunum="0";
function addNewMenu(){
	 var size="";
	 if(Menu.length>2){
			layer.alert("菜单数目不能超过三条");
			return false;
		}
	 	Menunum=parseInt(Menunum)+parseInt("1");
			size=Menunum;
			Menu.push({
				Menuid:Menunum
			});
	$("#Menulist").append("<div style='margin-top:70px' class='newmpmenulist"+size+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单名称</div>" +
			"					<div class='col-sm-3'><input type='text' class='form-control' name='Menuname"+size+"' placeholder='菜单名称'/></div>" +
			"				<div><a href='javascript:void(0)' onclick='deleteMenu("+size+")' style='color:blue'>删除该菜单栏</a></div></div><div>" +
			"				<div id='showMenuType"+size+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单内容</div> " +
			"					<div class='col-sm-3'>" +
			"					<label class='radio-inline'><input type='radio' name='type"+size+"'  value='0' onclick='checkRadio('news')'/> 发送消息</label>" +
			"				<label class='radio-inline'><input type='radio' name='type"+size+"' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label>" +
			"				<label class='radio-inline'> <input type='radio' name='type"+size+"' value='2' onclick='checkRadio('program')'/> 跳转小程序</label><label class='radio-inline'>" +
			"				<a style='color:blue' href='javascript:void(0)' onclick='addChildMenu("+size+")'>添加子菜单</a></label></div></div>" +
			"				<div><div class='form-group'><div class='col-sm-2 '></div> <div class='col-sm-3'>订阅者点击该子菜单会跳到以下链接</div></div>" +
			"				<div class='form-group'><div class='col-sm-2 control-label'>页面地址</div> <div class='col-sm-3'> <input type='text' class='form-control' name='url"+size+"' placeholder='地址'/</div>" +
			"				</div></div></div></div><div style='display:none' id='showChildMenu"+size+"'><div class='form-group'><div class='col-sm-2'></div><div class='col-sm-2' id='addnewchildmenu"+size+"'><a href='javascript:void(0)' onclick='addChildMenu("+size+")' style='color:blue'>添加新的子菜单栏</a> </div></div></div>");
	
	
}
function deleteMenu(event){
	array=new Array();
	
	Array.prototype.baoremove = function(dx)
	　{
	　　if(isNaN(dx)||dx>this.length){return false;}
	　　this.splice(dx,1);
	　}
		for(var i=0;i<Menu.length;i++){
			if(Menu[i].Menuid==event){
				Menu.baoremove(i);
				if(i=="0"){
					menu0=menu1;
					menu1=menu2;
					array.push({
						menu0:menu1,
						menu1:menu2
					})
					menu2=new Array();
				}
				if(i=="1"){
					menu1=menu2;
					array.push({
						menu0:menu0,
						menu1:menu2
					})
					menu2=new Array();
				}
				if(i=="2"){
					menu2=new Array();
					array.push({
						menu0:menu0,
						menu1:menu1
					})
				}
			}
		}
	$(".newmpmenulist"+event+"").html("");
}
var array = new Array();
var menu0num="0";
var menu1num="100";
var menu2num="200";
var menu0=new Array();
var menu1=new Array();
var menu2=new Array();
function addChildMenu(num){
	if(num==0&&openupdate=="false"){
		$("input:radio[name='type']").attr("checked",false);
	}else{
		$("input:radio[name='type"+num+"']").attr("checked",false);
	}
	 array = new Array();
	var event="";
	for(var i=0;i<Menu.length;i++){
		if(Menu[i].Menuid==num){
			if(i=="0"){
				if(menu0.length>4){
					 layer.alert("子菜单最多只能添加5条");
					 return false;
				 }
				menu0num=parseInt(menu0num)+parseInt("1");
				event=menu0num;
				menu0.push({
					menu0id:menu0num
				});
			}
			if(i=="1"){
				if(menu1.length>4){
					 layer.alert("子菜单最多只能添加5条");
					 return false;
				 }
				menu1num=parseInt(menu1num)+parseInt("1");
				event=menu1num;
				menu1.push({
					menu1id:menu1num
				});
			}
			if(i=="2"){
				if(menu2.length>4){
					 layer.alert("子菜单最多只能添加5条");
					 return false;
				 }
				menu2num=parseInt(menu2num)+parseInt("1");
				event=menu2num;
				menu2.push({
					menu2id:menu2num
				});
			}
		}
	}
	array.push({
		menu0:menu0,
		menu1:menu1,
		menu2:menu2
	})
		$("#addnewchildmenu"+num+"").css("display","block");
	$("#showMenuType"+num+"").css("display","none");
	$("input[name='url"+num+"']").val("");
	$("#showChildMenu"+num+"").css("display","block");
	$("#showChildMenu"+num+"").append("<div id='addnew"+num+"'><div id='childMpMenu"+event+"'><div class='form-group'><div class='col-sm-2 control-label'>子菜单名称</div><div class='col-sm-3'> <input type='text' class='form-control' name='childMenuMenuname"+event+"' placeholder='子菜单名称'/></div><div class='col-sm-2'><a href='javascript:void(0)' onclick='delChildMenu("+event+","+num+")'><img src='/hqc_mp/statics/images/octopus/t03.png' /></span></a></div> </div><div class='form-group'><div class='col-sm-2 control-label'>子菜单内容</div> <div class='col-sm-3'><label class='radio-inline'><input type='radio' name='childMenutype"+event+"'  value='0' onclick='checkRadio('news')'/> 发送消息</label><label class='radio-inline'><input type='radio' name='childMenutype"+event+"' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label><label class='radio-inline'><input type='radio' name='childMenutype"+event+"' value='2' onclick='checkRadio('program')'/> 跳转小程序</label></div></div><div class='form-group'><div class='col-sm-2 '></div><div class='col-sm-3'>订阅者点击该子菜单会跳到以下链接</div></div><div class='form-group'><div class='col-sm-2 control-label'>页面地址</div><div class='col-sm-3'><input type='text' class='form-control' name='childMenuUrl"+event+"' placeholder='地址'/></div></div></div></div>")
}
function delChildMenu(event,num){
	var menuappend="<div id='showMenuType"+num+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单内容</div> " +
	"					<div class='col-sm-3'>" +
	"					<label class='radio-inline'><input type='radio' name='type"+num+"'  value='0' onclick='checkRadio('news')'/> 发送消息</label>" +
	"				<label class='radio-inline'><input type='radio' name='type"+num+"' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label>" +
	"				<label class='radio-inline'> <input type='radio' name='type"+num+"' value='2' onclick='checkRadio('program')'/> 跳转小程序</label><label class='radio-inline'>" +
	"				<a style='color:blue' href='javascript:void(0)' onclick='addChildMenu("+num+")'>添加子菜单</a></label></div></div>" +
	"				<div><div class='form-group'><div class='col-sm-2 '></div> <div class='col-sm-3'>订阅者点击该子菜单会跳到以下链接</div></div>" +
	"				<div class='form-group'><div class='col-sm-2 control-label'>页面地址</div> <div class='col-sm-3'> <input type='text' class='form-control' name='url"+num+"' placeholder='地址'/</div>" +
	"				</div></div></div></div>"
	Array.prototype.baoremove = function(dx)
	　{
	　　if(isNaN(dx)||dx>this.length){return false;}
	　　this.splice(dx,1);
	　}
	for(var j=0;j<Menu.length;j++){
		if(j=="0"&&num==Menu[j].Menuid){
			for(var i=0;i<menu0.length;i++){
				if(menu0[i].menu0id!=undefined&&menu0[i].menu0id==event){
					menu0.baoremove(i);
				}else	if(menu0[i].menu1id!=undefined&&menu0[i].menu1id==event){
					menu0.baoremove(i);
				}else	if(menu0[i].menu2id!=undefined&&menu0[i].menu2id==event){
					menu0.baoremove(i);
				}
			}
			if(menu0.length==0){
				$("#showMenuType"+num+"").css("display","block");
				$("#showMenu"+num+"").html(menuappend);
				$("#addnewchildmenu"+num+"").css("display","none");

			}
		}else if(j=="1"&&num==Menu[j].Menuid){
			for(var i=0;i<menu1.length;i++){
				if(menu1[i].menu0id!=undefined&&menu1[i].menu0id==event){
					menu1.baoremove(i);
				}else	if(menu1[i].menu1id!=undefined&&menu1[i].menu1id==event){
					menu1.baoremove(i);
				}else	if(menu1[i].menu2id!=undefined&&menu1[i].menu2id==event){
					menu1.baoremove(i);
				}
			}
			if(menu1.length==0){
				$("#showMenu"+num+"").html(menuappend);
				$("#showMenuType"+num+"").css("display","block");
				$("#addnewchildmenu"+num+"").css("display","none");

			}
		}else if(j=="2"&&num==Menu[j].Menuid){
			for(var i=0;i<menu2.length;i++){
				if(menu2[i].menu0id!=undefined&&menu2[i].menu0id==event){
					menu2.baoremove(i);
				}else	if(menu2[i].menu1id!=undefined&&menu2[i].menu1id==event){
					menu2.baoremove(i);
				}else	if(menu2[i].menu2id!=undefined&&menu2[i].menu2id==event){
					menu2.baoremove(i);
				}
			}
			if(menu2.length==0){
				$("#showMenu"+num+"").html(menuappend);
				$("#showMenuType"+num+"").css("display","block");
				$("#addnewchildmenu"+num+"").css("display","none");

			}
		}
	}
	array=new Array();
	array.push({
		menu0:menu0,
		menu1:menu1,
		menu2:menu2
	})
	$("#childMpMenu"+event+"").html("");
}
var openupdate="false";
function openUpdate(){
	$("#showMenu").html("");
	$("#showfristMenu").html("");
	openupdate="true";
	$("#addfristmenu").html("")
	Menu= new Array();
	 menu0=new Array();
	 menu1=new Array();
	 menu2=new Array();
	Menunum="-1";
	 menu0num="0";
	 menu1num="100";
	 menu2num="200";
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
	$("#mpmenulist").css("display","none")
	$("#Menulist").html("")
	$.ajax({
		url : '../mpMenu/list',
		datatype : 'json',
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.menu.menu.buttons, function(index, content) {
					var size="";
					var addNewMenu=""
					if(content.subButtons.length==0){
					 	Menunum=parseInt(Menunum)+parseInt("1");
							size=Menunum;
							Menu.push({
								Menuid:Menunum
							});
						$("#Menulist").append("<div style='padding-bottom:70px' class='newmpmenulist"+size+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单名称</div>" +
								"					<div class='col-sm-3'><input type='text' class='form-control' name='Menuname"+size+"' value='"+content.name+"' placeholder='菜单名称'/></div>" +
								"				<div><a href='javascript:void(0)' onclick='deleteMenu("+size+")' style='color:blue'>删除该菜单栏</a></div></div><div>" +
								"				<div id='showMenuType"+size+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单内容</div> " +
								"					<div class='col-sm-3'>" +
								"					<label class='radio-inline'><input type='radio' name='type"+size+"'  value='0' onclick='checkRadio('news')'/> 发送消息</label>" +
								"				<label class='radio-inline'><input type='radio' name='type"+size+"' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label>" +
								"				<label class='radio-inline'> <input type='radio' name='type"+size+"' value='2' onclick='checkRadio('program')'/> 跳转小程序</label><label class='radio-inline'>" +
								"				<a style='color:blue' href='javascript:void(0)' onclick='addChildMenu("+size+")'>添加子菜单</a></label></div></div>" +
								"				<div><div class='form-group'><div class='col-sm-2 '></div> <div class='col-sm-3'>订阅者点击该子菜单会跳到以下链接</div></div>" +
								"				<div class='form-group'><div class='col-sm-2 control-label'>页面地址</div> <div class='col-sm-3'> <input type='text' class='form-control' name='url"+size+"' value='"+(content.url==null?'':content.url)+"' placeholder='地址'/</div>" +
								"				</div></div></div></div><div style='display:none' id='showChildMenu"+size+"'><div class='form-group'><div class='col-sm-2'></div><div class='col-sm-2' id='addnewchildmenu"+size+"'><a href='javascript:void(0)' onclick='addChildMenu("+size+")' style='color:blue'>添加新的子菜单栏</a> </div></div></div>");
						var typevalue="";
						if(content.type=="view"){
							typevalue="1";
						}
						if(content.type=="click"){
							typevalue="0";
						}
						if(content.type=="miniprogram"){
							typevalue="2";
						}
						$("input[name='type"+size+"'][value="+typevalue+"]").prop("checked", true);
					}else{
						Menunum=parseInt(Menunum)+parseInt("1");
						size=Menunum;
						Menu.push({
							Menuid:Menunum
						});
						$("#Menulist").append("<div  style='padding-bottom:70px'  class='newmpmenulist"+size+"'><div class='form-group'><div class='col-sm-2 control-label'>菜单名称</div>" +
								"					<div class='col-sm-3'><input type='text' class='form-control' name='Menuname"+size+"' value='"+content.name+"' placeholder='菜单名称'/></div>" +
								"				<div class='col-sm-2'><a href='javascript:void(0)' onclick='deleteMenu("+size+")' style='color:blue'>删除该菜单栏</a></div></div><div id=''></div>" +

										"	<div id='showMenu"+size+"'></div>	<div  id='showChildMenu"+size+"'><div class='form-group'><div class='col-sm-2'></div><div class='col-sm-2' id='addnewchildmenu"+size+"'><a href='javascript:void(0)' onclick='addChildMenu("+size+")' style='color:blue'>添加新的子菜单栏</a> </div></div></div></div>"
										);
						
						$.each(content.subButtons, function(i, value) {
							var event="";
							if(index==1){
								menu1num=parseInt(menu1num)+parseInt("1");
								event=menu1num;
								menu1.push({
									menu1id:menu1num
								});
							}
							if(index==0){
								menu0num=parseInt(menu0num)+parseInt("1");
								event=menu0num;
								menu0.push({
									menu0id:menu0num
								});
							}
							if(index==2){
								menu2num=parseInt(menu2num)+parseInt("1");
								event=menu2num;
								menu2.push({
									menu2id:menu2num
								});
							}
							array.push({
								menu0:menu0,
								menu1:menu1,
								menu2:menu2
							})
							var childtype="";
							if(value.type=="view"){
								childtype="1";
							}
							if(value.type=="click"){
								childtype="0";
							}
							if(value.type=="miniprogram"){
								childtype="2";
							}
							$("input[name='type"+event+"'][value="+childtype+"]").prop("checked", true);
							$("#showChildMenu"+size+"").append("<div id='addnew"+size+"'><div id='childMpMenu"+event+"'><div class='form-group'><div class='col-sm-2 control-label'>子菜单名称</div><div class='col-sm-3'>" +
									" <input type='text' class='form-control' value='"+value.name+"' name='childMenuMenuname"+event+"' placeholder='子菜单名称'/></div>" +
									"<div class='col-sm-2'><a href='javascript:void(0)' onclick='delChildMenu("+event+","+size+")'><img src='/hqc_mp/statics/images/octopus/t03.png' /></span></a></div> </div>" +
											"<div class='form-group'><div class='col-sm-2 control-label'>子菜单内容</div> <div class='col-sm-3'>" +
											"<label class='radio-inline'><input type='radio' name='childMenutype"+event+"'  value='0' onclick='checkRadio('news')'/> 发送消息</label>" +
											"<label class='radio-inline'><input type='radio' name='childMenutype"+event+"' value='1'  checked='checked' onclick='checkRadio('page')'/> 跳转网页</label>" +
											"<label class='radio-inline'><input type='radio' name='childMenutype"+event+"' value='2' onclick='checkRadio('program')'/> 跳转小程序</label></div></div><div class='form-group'><div class='col-sm-2 '></div>" +
											"<div class='col-sm-3'>订阅者点击该子菜单会跳到以下链接</div></div><div class='form-group'><div class='col-sm-2 control-label'>页面地址</div>" +
											"<div class='col-sm-3'><input type='text' class='form-control' value='"+(value.url==null?'':value.url)+"' name='childMenuUrl"+event+"' placeholder='地址'/></div></div></div></div>");
						})
					}
				
				
				});
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}
	});
	
	
}