
function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}


function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var cashCouponName = $("#cashCouponName1").val();
	var status = $("#status1").val();
	var limit = $("#limit").val();
	$.ajax({
		url : '../goods/cashCoupon/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			cashCouponName : cashCouponName,
			status : status,
			limit : limit
		},
		type : 'post',
		success : function(r) {
			if (r.code === 0) {
				$.each(r.page.list, function(index, content) {
					var statusType = ""
					if (content.status === 1) {
						statusType = '<span class="label label-primary">上架</span>';
					}

					if (content.status === 2) {
						statusType = '<span class="label label-warning">下架</span>';
					}
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
									+ (content.cashCouponName == null
											? ''
											: content.cashCouponName)
									+ "</td><td>"
									+ (content.faceValue == null ? '' : content.faceValue)
									+ "</td><td>"
									+ (content.integral == null
											? ''
											: content.integral)
									+ "</td>"
									+ "<td>"
									+ (content.maxExchange == null
											? ''
											: content.maxExchange)
									+ "</td><td>"
									+ (content.dayExchange == null
											? ''
											: content.dayExchange)
									+ "</td><td>"
									+ statusType
									+ "</td><td>"
									+ "<a class='label label-success'  onclick='getCash("+content.id+")' >&nbsp;详细</a>&nbsp&nbsp"
								    +statusImge + "</td></tr>")
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
 * 返回
 */
function reload(){
	$("#rightbody").css("display", "block");
	$("#addCash").css("display", "none");
	queryList();
}
/**
 * 添加readonly属性
 */
function resetInput(){
	$("input[name='cashCouponName']").prop("readonly",false);
	$("input[name='faceValue']").prop("readonly",false);
	$("input[name='integral']").prop("readonly",false);
	$("input[name='maxExchange']").prop("readonly",false);
	$("input[name='dayExchange']").prop("readonly",false);
	$("input[name='id']").prop("readonly",false);
	$("textarea[name='about']").prop("readonly",false);
	$("input[name='repertory']").prop("readonly",false);
}
/**
 * 打开增加页面
 */
function openAddCash(){
	resetInput();
	$('#about').summernote('code',""  );
	$("#imgclick").html("");
	$("#showUploadFile").css("display","block")
	$("input[name='status'][value=1]").prop("checked", true);
	$("#fileName").html("");
	$("input[name='cashThumb']").val("");
	$("input[name='pic']").val("");
	$("input[name='id']").val("");
	$("input[name='cashCouponName']").val("");
	$("input[name='faceValue']").val("");
	$("input[name='maxExchange']").val("");
	$("input[name='dayExchange']").val("");
	$("input[name='integral']").val("");
	$("input[name='repertory']").val("");
	/*$("textarea[name='about']").val("");*/


	$("#rightbody").css("display", "none");
	$("#addCash").css("display", "block");
	$("input[name='btn1']").show();
	$("input[name='pic']").show();
}
/**
 * 打开修改页面
 */
function openUpdateCash(){
	 resetInput();
	$("#showImg").html("");
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请选择一条记录");
		return false;
	}
	$.get("../goods/cashCoupon/info/" + ids, function(r) {
	    	$('#about').summernote('code', 
				r.entity.about == null ? "" : r.entity.about );
		       $("#imgclick").html("<a href='javascript:void(0)' onclick='showImg()' style='color:blue'>查看图片</a>");
		      $("#fileinput").html("<input type='file' class='form-control' onchange='upload(this)' name='pic' id='pic' value=''/>")
	           $("#showUploadFile").css("display","none")
				$("input[name='id']").val(r.entity.id);
				$("input[name='cashCouponName']").val(r.entity.cashCouponName);
				$("input[name='faceValue']").val(r.entity.faceValue);
				$("input[name='maxExchange']").val(r.entity.maxExchange);
				$("input[name='dayExchange']").val(r.entity.dayExchange);
				$("input[name='integral']").val(r.entity.integral);
				$("input[name='repertory']").val(r.entity.repertory);
				if(r.entity.cashThumb!=null&&r.entity.cashThumb!=''){
				$("#showImg").html("<img src='"+hqc.base + r.entity.cashThumb+"'/>")
				}
				
				$("#fileName").html(r.entity.cashThumb);
				$("input[name='cashThumb']").val(r.entity.cashThumb);
				//$("textarea[name='about']").val(r.entity.about);
				/*$("#about").val(r.entity.about)*/
				$("input[name='status'][value=" + r.entity.status + "]").prop(
						"checked", true);
			});

	$("#rightbody").css("display", "none")
	$("#addCash").css("display", "block")
	$("input[name='btn1']").show();
	$("input[name='pic']").show();
}
/**
 * 添加或修改操作
 * @returns {String}
 */
function saveOrUpdate(){
	var ids = checkData();
	var url = "";
	var id=$("#id").val();
	if (id== null||id==0||id=="") {
		url = "../goods/cashCoupon/save";
	} else {
		url = "../goods/cashCoupon/update";
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
				data : $('#cashForm').serialize(),
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
/**
 * 查看
 * @returns {String}
 */
function getCash(id){
	$("input[name='cashCouponName']").prop("readonly",true);
	$("input[name='integral']").prop("readonly",true);
	$("input[name='dayExchange']").prop("readonly",true);
	$("input[name='id']").prop("readonly",true);
	$("input[name='maxExchange']").prop("readonly",true);
	$("input[name='faceValue']").prop("readonly",true);
	$("input[name='repertory']").prop("readonly",true);
	$("textarea[name='about']").prop("readonly",true);
	$("#showUploadFile").css("display","none")
	$("#pic").css("display","none")
	$("#showImg").html("");
     $("input[name='cashThumb']").prop("readonly",true);
	$.ajax({
		type : "POST",
		url : "../goods/cashCoupon/info/" + id,
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		dataType : "json",
		success : function(r) {
			if (r.code == 0) {
				$("#fileName").html(r.entity.cashThumb);
				$("input[name='cashThumb']").val(r.entity.cashThumb);
				$("input[name='cashCouponName']").val(r.entity.cashCouponName);
				$("input[name='integral']").val(r.entity.integral);
				$("input[name='dayExchange']").val(r.entity.dayExchange);
				$("input[name='maxExchange']").val(r.entity.maxExchange);
				$("input[name='faceValue']").val(r.entity.faceValue);
				$("input[name='repertory']").val(r.entity.repertory);
				$('#about').summernote('code', 
						r.entity.about == null ? "" : r.entity.about );
			/*	$("#about").val(r.entity.about);*/
				$("input[name='status'][value=" + r.entity.status + "]").prop(
						"checked", true);
				$("#addCash").css("margin-top","40px");
				$("input[name='pic']").hide();
				$("#imgclick").html("<a href='javascript:void(0)' onclick='showImg()' style='color:blue;margin-top:20px'>查看图片</a>");
				if(r.entity.cashThumb!=null&&r.entity.cashThumb!=''){
				$("#showImg").html("<img src='"+hqc.base + r.entity.cashThumb+"'/>")
				}
				$("input[name='btn1']").hide();
				layer.open({
							type : 1,
							skin : 'layui-layer-molv',
							title : "详细信息",
							area : ['1050px', '650px'],
							shadeClose : true,
							content : jQuery("#addCash"),
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
/**
 * 上架 下架
 * @returns {String}
 */
function auditStatus(id,status){
	$.ajax({
		cache : true,
		type : "POST",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		dataType : "json",
		async : false,
		url : "../goods/cashCoupon/auitStatus",
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
// 重置
function resetQuery() {
	document.getElementById("cashCouponName1").value = "";
	document.getElementById("status1").value = "";
    queryList();
}

function deleteCash(){
	var ids = checkData();
	if (ids.length == 0 || ids.length == null || ids.length == "") {
		layer.msg("请至少选择一条记录");
		return false;
	}

	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../goods/cashCoupon/delete",
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
							},
							dataType : "json",
							async : false,
							data : {
								ids : ids
							},
							success : function(r) {
								if (r.code === 0) {
									layer.alert('操作成功', function(index) {
										$("#page").val("1");
												layer.close(index);
												queryList();
											});
								} else {
									layer.msg(r.msg);
								}
							},
							error : function() {
								layer.alert("系统异常", function() {
											queryList();
										});
							}
						});
			});
}
//上传文件
function upload(event){
	var name = event.name;
	var uploadType = 8; 
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
							$("input[name='cashThumb']")
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
function downFile() {
	var name = $("#cashThumb").val();
	name = encodeURI(name);
	name = encodeURI(name);
	if (null != name && "null" != name) {
		location.href = hqc.base + "file/download?name=" + name;
	}
}
/**
 * 查看图片
 */
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
