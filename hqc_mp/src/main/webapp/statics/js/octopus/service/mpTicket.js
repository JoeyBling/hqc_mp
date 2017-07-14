function selectByLimit(){
	$("#page").val("1");
	queryList();
}
var addAbout = '<p><span style="background-color: rgb(255, 255, 255);"><font color="#ff0000">如需退订、退款，请联系客服电话：4000 6911 11进行受理；也可以在官网的“我的订单”里面主动发起退订。</font></span></p><ul><span style="font-size: 24px;">★</span>购票流程：1、选择游玩日期 2、选择数量 3、预（点击查看详细购取票流程）<li><span style="font-size: 24px;">★</span>此票仅限成人或身高超过1.5米的儿童使用；每人 &nbsp;限购5张；</li><li><span style="font-size: 24px;">★</span>景区开放时间：平日09：30-18：00、节假日09：00-18：00</li><li>取票地点：茶溪谷/大侠谷票房（凭身份证或短信）</li></ul><p>入园凭证：凭纸质门票入园</p><p>备注：景区门票1.2米以下儿童免票、1.2-1.5米之间的儿童半价，长者65-69岁半价，70岁以上免票</p>';
/**
 * 重置
 */
function resetQuery() {
	$("#qticketName").val("");
	$("#minPrice").val("");
	$("#qstatus").val(0);
	$("#maxPrice").val("");
}

function queryList(n, page1) {
	$("#list").html("");
	var page
	if (page1 == 0) {
		page = 1;
	} else {
		page = $("#page").val();
	}
	var limit = $("#limit").val();
	var ticketName = $("#qticketName").val();
	var minPrice =$("#minPrice").val();
	var maxPrice =$("#maxPrice").val();
	var status =$("#qstatus").val();
	$.ajax({
		url : hqc.base + 'octopus/ticket/list',
		datatype : 'json',
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {page : page,
			limit : limit,
			checkpage : n,
			ticketName:ticketName,
			minPrice:minPrice,
			maxPrice:maxPrice,
			status:status
			},
			type : 'post',
			success : function(r) {
				if (r.code === 0) {
					$.each(r.page.list, function(index, content) {
						var ticketName = "<td>"+content.ticketName+"</td>";
						var price = "<td>￥"+content.price+"&nbsp;元</td>";
						var discount = "<td>"+content.discount+"</td>";
						var saleCount = "<td>"+content.saleCount+"</td>";
						var status;
						if(content.status === 1){
							var	 status	 = '<td><font color="green">上架</font></td>';
						    var statusImge="<a class='label label-warning'  onclick='auditStatus("+content.id+",2)' >&nbsp;下架</a>";
						}else{
							var	 status	 = '<td><font color="#A9A9A9">下架</font></td>';
						    var statusImge="<a class='label label-primary'  onclick='auditStatus("+content.id+",1)' >&nbsp;上架</a>";
						}
					//	var query = "<a class='label label-success'  onclick='queryView("+content.id+")' >&nbsp;详情</a>";
						$("#list").append("<tr><td><input name='ids' type='checkbox' value='"
																+ content.id+ "'/></td>"+ticketName+price+discount+saleCount+status+"<td>"
																+"&nbsp;&nbsp;"+statusImge+"</td></tr>");
					});
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
									+ r.page.currPage + "'/>");
					$(".pagetoal").html(r.page.totalPage);
					for (var i = r.page.currPage - 3; i < r.page.totalPage; i++) {
						if (r.page.totalPage < 2) {

						} else {
							if (i < r.page.currPage + 3 && i > -1) {
								$(".paginList")
										.append(" <div  id='pageint"
												+ (i + 1)
												+ "' class='paginItem'><a href='javascript:void(0)' onclick='clickpage("
												+ (i + 1) + ")'>" + (i + 1)
												+ "</a></div>")
							}
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
				}else{
					layer.msg(r.msg);
				}
			},
			error : function() {
				layer.alert("系统异常")
			}
	})
}
/**
 * 打开添加页面
 */
function openAdd(){
	$("#id").val("");
	$("#ticketContent").summernote('code', "");
	$("#weekendPrice").val("");
	$("#weekendPrice").val("");
	$("#startBuyDate").val("");
	$("#saleDate").val("");
	$("#endBuyDate").val("");
	$("#about").summernote('code',addAbout);
	$("#about").val("");
	$("#ticketImg").html("");
	$("input[name='thumbUrl']").val("");
	$("input[name='pic']").val("");
	$("input[name='marketPrice']").val("");
	$("input[name='price']").val("");
	$("input[name='ticketName']").val("");
	$("input[name='status'][value=1]").prop("checked", true);
	$("input[name='advance'][value=0]").prop("checked", true);
	$("input[name='weekendType'][value=0]").prop("checked", true);
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
}

/**
 * 打开修改页面
 * @returns {Boolean}
 */
function openUpdate(){
	var ids = checkData();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	var id=ids;
	$.get(hqc.base+"octopus/ticket/info/" + id, function(r) {
		if (r.ticket.status == 2) {
			$("input[id='disable']").prop("checked", false);
			$("input[id='normal']").prop("checked", true);
		} else if (r.ticket.status == 1) {
			$("input[id='disable']").prop("checked", true);
			$("input[id='normal']").prop("checked", false);
		}	
		if (r.ticket.advance == 1) {
			$("input[id='disable2']").prop("checked", true);
			$("input[id='normal2']").prop("checked", false);
		} else {
			$("input[id='disable2']").prop("checked", false);
			$("input[id='normal2']").prop("checked", true);
		}
		if (r.ticket.weekendType == 1) {
			$("input[id='disableWeek']").prop("checked", true);
			$("input[id='normalWeek']").prop("checked", false);
			$("#weekendPriceDiv").show();
			$("#weekendPrice").val(r.ticket.weekendPrice);
		} else {
			$("input[id='disableWeek']").prop("checked", false);
			$("input[id='normalWeek']").prop("checked", true);
			$("#weekendPriceDiv").hide();
			$("#weekendPrice").val("");
		}
		
		$("#saleDate").val(formatDate(new Date(r.ticket.saleDate*1000)));
		$("#startBuyDate").val(formatDate(new Date(r.ticket.startBuyDate*1000)));
		$("#endBuyDate").val(formatDate(new Date(r.ticket.endBuyDate*1000)));
		$("input[name='id']").val(r.ticket.id);
		$("input[name='ticketName']").val(r.ticket.ticketName);
		$("input[name='price']").val(r.ticket.price);
		$("input[name='marketPrice']").val(r.ticket.marketPrice);
		$("input[name='pic']").val(r.ticket.pic);
		$("input[name='thumbUrl']").val(r.ticket.thumbUrl);
		
		$("#about").summernote('code',r.ticket.about == null ? "" : r.ticket.about);
		$('#ticketContent').summernote('code',r.ticket.ticketContent == null ? "" : r.ticket.ticketContent);
		var thumbUrl = r.ticket.thumbUrl;
		if (null != thumbUrl && thumbUrl != "") {
			thumbUrl = encodeURI(thumbUrl);
			thumbUrl = encodeURI(thumbUrl);
			thumbUrl = "<a target='_blank' href='" + hqc.base
					+ "file/download?name=" + thumbUrl
					+ "'><img width='250px' height='250px' src='" + hqc.base
					+ r.ticket.thumbUrl.replace(/\\/g, "/")
					+ " '  alt='图片不存在！'/> </a>";
		} else {
			thumbUrl = "<font color=red>图片未保存！</font>";
		}
		$("#thumb").html(thumbUrl);
	});
$("#rightbody").css("display", "none")
$("#add").css("display", "block")
}

/**
 * 保存
 * @returns {Boolean}
 */
function saveOrUpdate() {
	var id = $("#id").val() == "" ? null : $("#id").val();
	var ticketName = $("#ticketName").val();
	var price = $("#price").val();
	var marketPrice = $("#marketPrice").val();
	var ticketContent = $("#ticketContent").summernote('code');
	var thumbUrl = $("#thumbUrl").val();
	var about = $("#about").summernote('code');
	var endBuyDate = $("#endBuyDate").val();
	var startBuyDate = $("#startBuyDate").val();
	var saleDate = $("#saleDate").val();
	var status = $("input[name=status]:checked").val();
	var advance = $("input[name=advance]:checked").val();
	var weekendType = $("input[name=weekendType]:checked").val();
	var weekendPrice =0;
	if(weekendType == 1){
		var weekendPrice = $("#weekendPrice").val();
	}
	if(ticketName == null || ticketName ==""){
		layer.msg("请填写门票名称");
		return false;
	}
	if(price == null || price ==""){
		layer.msg("请填写单价");
		return false;
	}
	if(weekendType == 1 && (weekendPrice == null || weekendPrice == 0)){
		layer.msg("请正确填写周末票价");
		return false;
	}
	if(marketPrice == null || marketPrice ==""){
		layer.msg("请正确填写市场价");
		return false;
	}	
	if(thumbUrl == null || thumbUrl ==""){
		layer.msg("请上传门票图片");
		return false;
	}
	if(null == saleDate || saleDate == ""){
		layer.msg("请选择开售时间");
		return false;
	}
	if(null == startBuyDate || startBuyDate == "" || null == endBuyDate || endBuyDate == ""){
		layer.msg("请选择门票使用时间");
		return false;
	}
	if(new Date(saleDate) > new Date(startBuyDate)){
		layer.msg("使用时间必须大于开售时间");
		return false;
	}
	if(new Date(startBuyDate) > new Date(endBuyDate)){
		layer.msg("结束时间必须大于开始时间");
		return false;
	}
	if(about == null || about ==""){
		layer.msg("请填写门票简介");
		return false;
	}
	if(ticketContent == null || ticketContent ==""){
		layer.msg("请填写门票详细介绍");
		return false;
	}
	var url = id == null ? hqc.base+"octopus/ticket/save" : hqc.base+"octopus/ticket/update";
	$.ajax({
		type : "POST",
		url : url,
		data : {
			id : id,
			ticketName : ticketName,
			price : price,
			marketPrice : marketPrice,
			about : about,
			thumbUrl : thumbUrl,
			ticketContent : ticketContent,
			status : status,
			advance:advance,
			startBuyDateString:startBuyDate,
			endBuyDateString:endBuyDate,
			saleDateString:saleDate,
			weekendPrice:weekendPrice,
			weekendType:weekendType
		},
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
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
				location.reload();
			});
		}
	});
}

/**
 * 删除
 * @returns {Boolean}
 */
function del() {
	var ids = checkData();
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	layer.confirm('确定要删除选中的记录？', function() {
		$.ajax({
			type : "POST",
			url : hqc.base+"octopus/ticket/delete",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				ids : ids
			},
			success : function(r) {
				if (r.code == 0) {
					layer.alert('操作成功', function(index) {
						$("#page").val("1");
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

/**
 * 修改上、下架状态
 * @param id
 * @param status
 */
function auditStatus(id,status){
	layer.confirm('确定要更改选中的记录状态？', function() {
		$.ajax({
			type : "POST",
			url : hqc.base+"octopus/ticket/auditStatus",
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data : {
				id : id,
				status : status
			},
			success : function(r) {
				if (r.code == 0) {
					layer.alert('操作成功', function(index) {
						$("#page").val("1");
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

$(function() {
	queryList();
	
	/**
	 * 启用周末票价
	 */
	$("#disableWeek").click(function() {
		$("#weekendPriceDiv").show();	
	});
	$("#normalWeek").click(function() {
		$("#weekendPriceDiv").hide();	
	});
	/**
	 * 全选
	 */
	$("#selectAll").click(function() {
		$('input[name="ids"]').prop("checked", this.checked);
		if (this.checked) {
			$(".tablelist tr[name='tru'] ")
					.css("background", "#e5ebee");
		} else {
			$('input[name="ids"]')
			$(".tablelist tr:gt(0)").css("background", "");
		}
	});
});


//获取选中的多选框的值
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

//翻页
function clickpage(event) {
	$("#page").val(event)
	queryList();
}

function reload() {
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}


function upload(event) {
	if (event.files == null || event.files.length < 1) {
		return false;
	}
	// 判断上传的是否是图片
	if (!/\.(jpg|jpeg|png|GIF|JPG|PNG)$/.test(event.value)) {
		layer.alert("上传图片失败,请重新选择,图片类型必须是jpeg,jpg,png中的一种");
		return false;
	}
	var name = event.name;
	var uploadType = 6; // 上传类型 6为门票图片
	if (name == "pic") { // 门票图片
		uploadType = 6;
	}
	
	$.ajaxFileUpload({
		url : hqc.base + 'file/upload?uploadType=' + uploadType + '&name='
				+ name, // 用于文件上传的服务器端请求地址
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
				

				if (name == "pic") {// 会员头像

					var htmlImage = "<img width='400px' height='250px' src='"
							+ hqc.base + data.filePath.replace(/\\/g, "/")
							+ " '  alt='图片不能显示'/> ";
					$("#ticketImg").html(htmlImage);

					$("input[name='thumbUrl']").val(data.filePath);
				//	var fileName1 = getFileName(data.filePath).substring(13);
				//	$("#fileName").html(fileName1);
					layer.alert("上传成功");
				}
			} else {
				alert(data.msg);
			}
		},
		error : function(data, status, e) {// 服务器响应失败处理函数
			alert("系统出错,请联系管理员");
		}
	});
}

/**
 * 文本编辑器
 */
$(document).ready(function () {
    $('#ticketContent').summernote({
        height: 400,
        minHeight: 300,
        maxHeight: 500,
        focus: true,
        lang: 'zh-CN',
        // 重写图片上传  
        callbacks: {
            onImageUpload: function (files) { //the onImageUpload API  
                img = sendFile(files[0],1);
            }
        }
    });
    $('#about').summernote({
        height: 400,
        minHeight: 300,
        maxHeight: 500,
        focus: true,
        lang: 'zh-CN',
        // 重写图片上传  
        callbacks: {
            onImageUpload: function (files) { //the onImageUpload API  
                img = sendFile(files[0],2);
            }
        }
    });
});

function sendFile(file,n) {
    var data = new FormData();
    data.append("file", file);
    data.append("name", "file");
    data.append("uploadType", "2");
    console.log(data);
    $.ajax({
        data: data,
        type: "POST",
        url:hqc.base+ "file/upload",
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
        	if(n ==1){
        	      $("#ticketContent").summernote('insertImage', hqc.base+ result.filePath.replace(/\\/g, "/"), 'img'); // the insertImage API 
        	}
        	if(n ==2){
        	      $("#about").summernote('insertImage', hqc.base+ result.filePath.replace(/\\/g, "/"), 'img'); // the insertImage API 
        	}
       }
    });
}
//格式化时间 yyyy-mm-dd
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();

	return year + "-" + month + "-" + date;
}