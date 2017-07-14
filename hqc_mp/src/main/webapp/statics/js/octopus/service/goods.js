function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	var categoryName1 = $("#categoryName1").val();
	var goodsName1 = $("#goodsName1").val();
	$.ajax({
		url : '../goods/goods/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			categoryName:categoryName1,
			goodsName:goodsName1,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					//var date = formatDate(new Date(content.createTime));
					var statusType = ""
                    var statusImge=""
					if (content.status === 1) {
						statusType = '<span class="label label-primary">上架</span>';
						statusImge="<a class='label label-warning'  onclick='auditStatus("+content.id+",2)' >&nbsp;下架</a>"
					}
					
					if (content.status === 2) {
						statusType = '<span class="label label-warning">下架</span>';
						statusImge="<a class='label label-primary'  onclick='auditStatus("+content.id+",1)' >&nbsp;上架</a>"
					}
					

					$("#list")
							.append("<tr><td><input name='ids' type='checkbox' value='"
									+ content.id
									+ "'/></td>"
									+ "<td>"
									+ content.id
									+ "</td><td>"
									+ (content.mpGoodsCategoryEntity.categoryName == null ? '' : content.mpGoodsCategoryEntity.categoryName)
									+ "</td><td>"
									+ (content.goodsName == null	? '': content.goodsName)
									+ "</td><td>"
									+ (content.integral == null ? '' : content.integral)
									+ "</td>"
									+ "<td>"
									+ (content.marketPrice == null? '': content.marketPrice)
									+ "</td><td>"
									+ (content.maxExchange == null ? '' : content.maxExchange)
									+ "</td><td>"
									+ (content.price == null? '': content.price) 
									+ "</td><td>" 
									+ (content.repertory == null ? '' : content.repertory)
									+"</td><td>"
									+statusType
								    +"</td><td>"
								    +"<a class='label label-success'  onclick='seeRecord("+content.id+")' >&nbsp;详细</a>&nbsp&nbsp"
								    +statusImge
								    +		"</td></tr>")
				})
				$("#page").val(r.page.currPage)
				var lastpage = "";
				var nextpage = "";
				$(".paginList").html("")
				if (r.page.currPage != 1) {
					$(".paginList")
							.html("<li class='paginItem' ><a href='javascript:void(0);' onclick='queryList(1)'><span class='pagepre'></span></a></li>");

				}
				$(".total").html(r.page.totalCount)
				$(".page")
						.html("<input type='text' readonly='readonly' style='width:20px' value='"
								+ r.page.currPage + "'/>")
				$(".pagetoal").html(r.page.totalPage)
				for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
					if (i < r.page.currPage + 3 && i > -1) {
						$(".paginList")
								.append(" <div  id='pageint"
										+ (i + 1)
										+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
										+ (i + 1) + ")'>" + (i + 1)
										+ "</a></div>")
					}
				}
				$("#pageint" + r.page.currPage + "")
						.html("<div class='page_curr'>"
								+ r.page.currPage + "</div>")
				if (r.page.currPage < r.page.totalPage
						&& r.page.totalPage !== 0) {
					$(".paginList")
							.append("<li style='margin-left:0.5px' class='paginItem'><a href='javascript:void(0);' onclick='queryList(2)'><span class='pagenxt'></span></a></li>");

				}

				$(".tablelist tr:gt(0)").on("click", function(e) {
					var ids = $(e.currentTarget).find("td input[name='ids']")[0];
					if (null != ids && ids.checked) {
						ids.checked = false;
						$(e.currentTarget).css("background", "");
					} else {
						ids.checked = true;
						$(e.currentTarget).css("background", "#e5ebee");
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

function clickpage(event) {
	$("#page").val(event)
	queryList();
}
function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
$(function() {
			queryList();
			/**
			 * 全选
			 */
			$("#selectAll").click(function() {
						$('input[name="ids"]').prop("checked", this.checked);
						if (this.checked) {
							$(".tablelist tr:gt(0)").css("background",
									"#e5ebee");
						} else {
							$('input[name="ids"]')
							$(".tablelist tr:gt(0)").css("background", "");
						}
					});
		});


function upload(event){
	var name = event.name;
	var uploadType = 5;  //商品缩略图

	//为空的时候不上传
	if (event.files == null || event.files.length < 1) {
		return false; // 未选择文件
	}
	$.ajaxFileUpload({
				url : hqc.base + 'file/upload?uploadType=' + uploadType
						+ '&name=' + name, // 用于文件上传的服务器端请求地址
				secureuri : false, // 一般设置为false
				fileElementId : event.id,
				type : 'post',
				dataType : 'jsonp', // 返回值类型 一般设置为json
				success : function(data, status) // 服务器成功响应处理函数
				{
					var dataCopy = data;
					try {
						var reg = /<pre.+?>(.+)<\/pre>/g;
						data = data.match(reg);
						data = RegExp.$1; // 解决上传文件 返回值带 <pre
						// style="word-wrap:break-word;white-space:prewrap;"></pre>
						data = (new Function("return " + data))();
					} catch (err) {
						data = dataCopy;
						data = (new Function("return " + data))();
						// 在这里处理错误
					}
					if (data.code == 0) {
						layer.alert("上传成功");
						if (name == "pic") {// 商品缩略图
							$("input[name='goodsThumb']")
									.val(data.filePath);
							var fileName1 = getFileName(data.filePath)
									.substring(13);
							$("#fileName").html(fileName1);
							$("#showImg").html("<img src='"+hqc.base + data.filePath+"'/>")
						}
					} else {
						layer.alert(data.msg);
					}
				},
				error : function(data, status, e) {// 服务器响应失败处理函数
					alert("系统出错,请联系管理员");
				}
			});
}
//提取文件名
function getFileName(o) {
	var pos = o.lastIndexOf("\\");
	return o.substring(pos + 1);
}
//时间转换
function toLong(id){
	var time=document.getElementById(id).value;
	
	
	var   tempTime=new   Date(Date.parse(time .replace(/-/g,"/")));
	var   curDate=new   Date();
	
	
	if(tempTime <=curDate){
		layer.msg("有效日期不能小于等于今天");
		document.getElementById(id).value="";
		return false;
	}else{
		$.get("../goods/goods/toLong/" + time, function(r) {
			 
			$("input[name='endTime']").val(r.LongData)
		});
	}
	
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


function getCategory(){
	$.get("../goods/goods/getCategory/" , function(r) {
		for (var i = 0; i < r.categoryList.length; i++) {
			
			$("select[name='categoryId']").append("<option value='"+r.categoryList[i].id+"'>"+r.categoryList[i].categoryName+" </option>")
		}
	});
}
function openAdd(){
	 resetInput()
	$("#imgclick").html("");
	$("#showUploadFile").css("display","block")
	$("input[name='goodsName']").val("")
	$("input[name='daysLimit']").val("");
	$("input[name='repertory']").val("");
	$("input[name='marketPrice']").val(" ");
	$("input[name='price']").val("");
	$("input[name='integral']").val(" ");
	$("input[name='dayExchange']").val("");
	$("input[name='status'][value=1]").prop("checked", true);
	$("input[name='id']").val("");
	$("input[name='maxExchange']").val("");
	//$("input[name='notice']").val("");
	$("input[name='goodsThumb']").val("");
	$("input[name='pic']").val("");
	$("select[name='categoryId']").children('option').remove();
	//$("textarea[name='about']").val("");
	//$("#about").val("")
	$('#notice').summernote('code',"")
	$('#about').summernote('code', "" )
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
	getCategory();
	$("input[name='btn1']").show();
	$("input[name='pic']").show();
	$("input[name='endTime']").val("");
	$("input[name='datetime']").val("");
	$("#fileName").html("");
}



function saveOrUpdate() {
	var ids = checkData();
	var url = "";
	var id=$("#id").val();
	if (id==null||id==0||id=='') {
		url = "../goods/goods/save";
	} else {
		url = "../goods/goods/update";
	}
	$.ajax({
				cache : true,
				type : "POST",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				dataType : "json",
				async : false,
				url : url,
				data : $('#goodsForm').serialize(),
				success : function(r) {
					if (r.code === 0) {
						layer.alert('操作成功', function(index) {
									layer.close(index);
									reload();
								});
					} else {
						layer.msg(r.msg);
					}
				},
				error : function() {
					layer.alert("系统异常", function() {
								reload();
							});
				}
			});
}

function openUpdate() {
	 resetInput();
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$("#showImg").html("");
	
	$.get("../goods/goods/info/" + ids, function(r) {
				$("#imgclick").html("<a href='javascript:void(0)' onclick='showImg()' style='color:blue'>查看图片</a>");
				$("#fileinput").html("<input type='file' class='form-control' onchange='upload(this)' name='pic' id='pic' value=''/>")
				$("#showUploadFile").css("display","none")
				$("input[name='id']").val(r.entity.id);
				$("input[name='goodsName']").val(r.entity.goodsName);
				$("input[name='daysLimit']").val(r.entity.daysLimit);
				$("input[name='endTime']").val(r.entity.endTime);
				$("input[name='repertory']").val(r.entity.repertory);
				$("input[name='marketPrice']").val(r.entity.marketPrice);
				$("input[name='price']").val(r.entity.price);
				$("input[name='integral']").val(r.entity.integral);
				$("input[name='dayExchange']").val(r.entity.dayExchange);
			    $("input[name='maxExchange']").val(r.entity.maxExchange);
				//$("#notice").val(r.entity.notice);
				//$("textarea[name='about']").text(r.entity.about);
				$('#notice').summernote('code',
						r.entity.notice == null ? "" : r.entity.notice)
				$('#about').summernote('code',
						r.entity.about == null ? "" : r.entity.about)
				//$("#about").val(r.entity.about)
				$("input[name='status'][value=" + r.entity.status + "]").prop(
						"checked", true);
				if(r.entity.goodsThumb!=null&&r.entity.goodsThumb!=""){
				$("#showImg").html("<img src='"+hqc.base + r.entity.goodsThumb+"'/>")
				}
				$("#fileName").html(r.entity.goodsThumb);
				$("input[name='goodsThumb']").val(r.entity.goodsThumb);
				
				$("select[name='categoryId']").children('option').remove();
				$("select[name='categoryId']").append("<option value='"+r.entity.mpGoodsCategoryEntity.id+"'>"+r.entity.mpGoodsCategoryEntity.categoryName+" </option>");
				getCategory();
				var endTime=r.entity.endTime*1000;
				var date = formatDate(new Date(endTime));
				$("input[name='datetime']").val(date);
			});

	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
	$("input[name='btn1']").show();
	$("input[name='pic']").show();

}
function showImg(){
	var value=$("#showImg").html();
	
	if(value==null||value==''){
		layer.msg("图片暂未上传");
		return false;
	}
	layer.open({
		type : 1,
		skin : 'layui-layer-molv',
		title : "查看图片",
		area : ['400px', '400px'],
		shadeClose : true,
		content : jQuery("#showImg"),
		align:"center",
		btn : ['取消']
	});
	
	
}
function reload() {
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
	$("#view").css("display", "none");
	queryList();
	
}
function reload2() {
	$("#rightbody").css("display", "block");

	$("#view").css("display", "none");
	queryList();
	
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
//重置
function resetQuery(){
	document.getElementById("categoryName1").value="";
	document.getElementById("goodsName1").value="";
	
	queryList();
}

function deleteGoods(){
	var ids = checkData();
	if (ids.length == 0||ids.length==null||ids.length=="") {
		layer.msg("请至少选择一条记录");
		return false;
	}

	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../goods/goods/delete",
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
							},
							dataType : "json",
							async : false,
							data : {ids:ids},
							success : function(r) {
								if (r.code === 0) {
									layer.alert('操作成功', function(index) {
										$("#page").val("1");
												layer.close(index);
												reload();
											});
								} else {
									layer.msg(r.msg);
								}
							},
							error : function() {
								layer.alert("系统异常", function() {
											reload();
										});
							}
						});
			});
}
function seeRecord(id){
	$("input[name='goodsName']").prop("readonly",true);
	$("input[name='daysLimit']").prop("readonly",true);
	$("input[name='repertory']").prop("readonly",true);
	$("input[name='marketPrice']").prop("readonly",true);
	$("input[name='price']").prop("readonly",true);
	$("input[name='integral']").prop("readonly",true);
	$("input[name='dayExchange']").prop("readonly",true);
	$("input[name='id']").prop("readonly",true);
	$("input[name='maxExchange']").prop("readonly",true);
	$("textarea[name='notice']").prop("readonly",true);
	$("input[name='goodsThumb']").prop("readonly",true);
	$("input[name='pic']").prop("readonly",true);
	$("textarea[name='about']").prop("readonly",true);
	$("input[name='endTime']").prop("readonly",true);
	$("input[name='datetime']").prop("readonly",true);
	$("#showUploadFile").css("display","none");
	$("#showImg").html("");
	$.ajax({
		type : "POST",
		url : "../goods/goods/info/" + id,
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		dataType : "json",
		success : function(r) {
			if (r.code == 0) {
				$('#notice').summernote('code',
						r.entity.notice == null ? "" : r.entity.notice)
				$('#about').summernote('code',
						r.entity.about == null ? "" : r.entity.about)
				$("input[name='goodsName']").val(r.entity.goodsName);
				$("input[name='daysLimit']").val(r.entity.daysLimit);
				$("input[name='endTime']").val(r.entity.endTime);
				$("input[name='repertory']").val(r.entity.repertory);
				$("input[name='marketPrice']").val(r.entity.marketPrice);
				$("input[name='price']").val(r.entity.price);
				$("input[name='integral']").val(r.entity.integral);
				$("input[name='dayExchange']").val(r.entity.dayExchange);
			    $("input[name='maxExchange']").val(r.entity.maxExchange);
				/*$("textarea[name='notice']").val(r.entity.notice);
				$("textarea[name='about']").val(r.entity.about);*/
				$("input[name='status'][value=" + r.entity.status + "]").prop(
						"checked", true);
				$("#fileName").html(r.entity.goodsThumb);
				$("input[name='goodsThumb']").val(r.entity.goodsThumb);
				$("#add").css("margin-top","40px");
				$("select[name='categoryId']").children('option').remove();
				$("select[name='categoryId']").append("<option value='"+r.entity.mpGoodsCategoryEntity.id+"'>"+r.entity.mpGoodsCategoryEntity.categoryName+" </option>");
				var endTime=r.entity.endTime*1000;
				var date = formatDate(new Date(endTime));
				$("input[name='datetime']").val(date);
				$("input[name='btn1']").hide();
				$("input[name='pic']").hide();
				$("#imgclick").html("<a href='javascript:void(0)' onclick='showImg()' style='color:blue'>查看图片</a>");
				if(r.entity.goodsThumb!=null&&r.entity.goodsThumb!=''){
					$("#showImg").html("<img src='"+hqc.base + r.entity.goodsThumb+"'/>");
				}
				
				layer.open({
							type : 1,
							skin : 'layui-layer-molv',
							title : "详细信息",
							area : ['1050px', '650px'],
							shadeClose : true,
							content : jQuery("#add"),
							align:"center",
							btn : ['取消']
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
}
function downFile() {
	var name = $("#goodsThumb").val();
	name = encodeURI(name);
	name = encodeURI(name);
	if (null != name && "null" != name) {
		location.href = hqc.base + "file/download?name=" + name;
	}
}
function auditStatus(id,status){

	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		dataType : "json",
		async : false,
		url : "../goods/goods/auitStatus/",
		data : {id:id,status:status},
		success : function(r) {
			if (r.code === 0) {
				layer.alert('操作成功', function(index) {
							layer.close(index);
							reload();
						});
			} else {
				layer.msg(r.msg);
			}
		},
		error : function() {
			layer.alert("系统异常", function() {
						reload();
					});
		}
	});
}

function resetInput(){
	$("input[name='goodsName']").prop("readonly",false);
	$("input[name='daysLimit']").prop("readonly",false);
	$("input[name='repertory']").prop("readonly",false);
	$("input[name='marketPrice']").prop("readonly",false);
	$("input[name='price']").prop("readonly",false);
	$("input[name='integral']").prop("readonly",false);
	$("input[name='dayExchange']").prop("readonly",false);
	$("input[name='id']").prop("readonly",false);
	$("input[name='maxExchange']").prop("readonly",false);
	$("textarea[name='notice']").prop("readonly",false);
	$("input[name='goodsThumb']").prop("readonly",false);
	$("input[name='pic']").prop("readonly",false);
	$("textarea[name='about']").prop("readonly",false);
	$("input[name='endTime']").prop("readonly",false);
	$("input[name='datetime']").prop("readonly",false);
}