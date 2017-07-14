function selectByLimit(){
	$("#page").val("1");
	queryList();
	
}
function queryList(n) {
	$("#list").html("");
	var page = $("#page").val();
	var limit = $("#limit").val();
	var scenicName = $("#scenicName").val();
	$.ajax({
		url :  "../scenicSpot/scenic/list",
		datatype : "json",
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			page : page,
			checkpage : n,
			limit : limit,
			scenicName : scenicName
		},
		type : 'post',
		success : function (r){
			$.each(r.page.list, function(index, content) {						
				var thumbUrl = content.thumbUrl;
				if(null != thumbUrl && thumbUrl != ""){
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = "<a target='_blank' href='"+hqc.base+"file/download?name="+thumbUrl+"'>下载</a>";
				}else{
					thumbUrl = "<font color=red>图片未保存！</font>";
				}
				
				$("#list")
						.append("<tr>" +
								"<td><input name='ids' type='checkbox' value='" + content.id + "'/></td>" +
								"<td>" + (content.scenicName == null ? '' : content.scenicName) + "</td>" +								
								"<td>" + formatDate(new Date(content.updateTime*1000)) + "</td>" +
								"</tr>")
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
				$(".page").html("<input type='text' readonly='readonly' style='width:20px' value='" + r.page.currPage + "'/>")
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
				if (r.page.currPage < r.page.totalPage && r.page.totalPage !== 0) {
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
		},
		error : function() {
			layer.msg("系统异常,请联系管理员!");
		}	
	});
}
$(function() {
	queryList();
})
function clickpage(event) {
	$("#page").val(event)
	queryList();
}
function reload(){
	queryList();
	$("#rightbody").css("display", "block");
	$("#add").css("display", "none");
}
$(function() {	
	// 全选	 
	$("#checkedAll").click(function() {
		$('input[name="ids"]').prop("checked", this.checked);
				if (this.checked) {
					$(".tablelist tr:gt(0)").css("background","#e5ebee");
				} else {
					$('input[name="ids"]')
					$(".tablelist tr:gt(0)").css("background", "");
				}
			});
});
//获取选中的多选框的值
function checkValue() {
	var ids = "";
	$("#list td").each(function() {
		if ($(this).find("input[type=checkbox]:checked").val() != undefined) {
			if (ids.length == 0) {
				ids = $(this).find("input[type=checkbox]:checked").val()
			} else {
				ids = ids + "," + $(this).find("input[type=checkbox]:checked").val();
			}
		}
	});
	return ids;
}
/**
 * 删除
 * @returns {Boolean}
 */
function del() {	
	var ids = checkValue();		
	layer.confirm('确定要删除选中的记录？', function() {
				$.ajax({
							type : "POST",
							url : "../scenicSpot/scenic/delete",
							data : {
								id : ids
							},
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded'
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
 * 删除前判断是否存在子项
 * @returns {Boolean}
 */
function queryNext(){
	var ids = checkValue();	
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	$.ajax({
		type: "POST",
	    url: "../scenicSpot/scenic/infoScenery",
	    dataType: "json",
	    headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
	    data : {ids : ids},
	    success: function(r){					
				if(r.code != 0){
					layer.alert(r.msg);					
				}else{	
					del();
				}	    	
		},
		error : function() {
			//layer.msg("");
		}
	});
}
function openAdd() {
	// 清空
	/** 是否是修改 */
	$("#id").val("");
	$("input[name='scenicName']").val("");	
	$("input[name='thumbUrl']").val("");
	$("textarea[name='about']").val("");	
	$("input[name='pic']").val("");
	$("#thumb").html("");
	$("#content").summernote('code', "");
	$("[name=like_count]").attr("readonly", "readonly");
	$("#rightbody").css("display", "none");
	$("#add").css("display", "block");
		
}
function verify(){
	var scenicNames = $("#scenicNames").val();
		$.ajax({
			type: "POST",
		    url: "../scenicSpot/scenic/infoName",
		    dataType: "json",
		    headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
		    data : {scenicName : scenicNames},
		    success: function(r){					
					if(r.code != 0){
						layer.alert(r.msg);
					}else{
					}	    	
			},
			error : function() {
				//layer.msg("");
			}
		});	
}
/**
 * 保存
 * @param event
 */
function saveAdd() {
	
	var id =  $("#id").val() == "" ? null : $("#id").val();
	var scenicNames = $("#scenicNames").val();
	var thumbUrl = $("#thumbUrl").val();
	var about = $("#about").val();
	var content = $("#content").summernote('code');
	if(scenicNames ==null || scenicNames ==""){
		layer.msg("请填写景区名称");
		return false;
	}
	if(about ==null || about ==""){
		layer.msg("请填写景区简介");
		return false;
	}
	if(content ==null || content ==""){
		layer.msg("请填写景区详细介绍");
		return false;
	}
	var url = id == null ? "../scenicSpot/scenic/save" : "../scenicSpot/scenic/update";
	$.ajax({
				type : "POST",
				url : url,
				data : {
					id : id,
					scenicName : scenicNames,
					thumbUrl : thumbUrl,
					about : about,
					content : content
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
 * 修改
 * @returns {Boolean}
 */
function update(){
	 var ids = checkValue();
	if (ids.indexOf(",") >= 0) {
		layer.msg("只能选择一条记录");
		
		return false;
	}
	if (ids.length == 0) {
		layer.msg("请至少选择一条记录");
		return false;
	}
	var id=ids;
	$.get("../scenicSpot/scenic/info/" + id, function(r) {
				$("input[name='id']").val(r.entity.id);				
				$("input[name='scenicName']").val(r.entity.scenicName);	
				$("input[name='thumbUrl']").val(r.entity.thumbUrl);					
				$('#content').summernote('code',r.entity.content == null ? "" : r.entity.content);
				$("textarea[name='about']").val(r.entity.about);
				$("#pic").val("");
				var thumbUrl = r.entity.thumbUrl;
				if(null != thumbUrl && thumbUrl != ""){
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = encodeURI(thumbUrl);
					thumbUrl = "<a target='_blank' href='"+hqc.base+"file/download?name="+thumbUrl+"'><img width='250px' height='250px' src='"+hqc.base+r.entity.thumbUrl+" '  alt='图片不能显示！'/> </a>";
				}else{
					thumbUrl = "<font color=red>图片未保存！</font>";
				}
			
				$("#thumb").html(thumbUrl);
				
			});
	$("#rightbody").css("display", "none")
	$("#add").css("display", "block")
}

//上传文件
function upload(event) {
	// 判断上传的是否是图片
	if (!/\.(gif|jpg|jpeg|png|bmp|JPEG|BMP|GIF|JPG|PNG)$/.test(event.value)) {
		layer.msg("上传图片失败,请重新选择,图片类型必须是.gif,jpeg,jpg,png,bmp中的一种");
		return false;
	}
	var name = event.name;
	var uploadType = 1; // 上传类型1 景区缩略图

	if (event.files == null || event.files.length < 1) {
		return false; // 未选择文件
	}
	
	$.ajaxFileUpload({
				url : '/hqc_mp/file/upload?uploadType=' + uploadType + '&name=' + name, // 用于文件上传的服务器端请求地址
				secureuri : false, 
				fileElementId : event.id,
				type : 'post',
				dataType : 'jsonp', 
				success : function(data, status) {// 服务器成功响应处理函数
					var dataCopy = data;
					try {
						var reg = /<pre.+?>(.+)<\/pre>/g;
						data = data.match(reg);
						data = RegExp.$1; // 解决上传文件 返回值带 <prestyle="word-wrap:break-word;white-space:prewrap;"></pre>
						data = (new Function("return " + data))();
					} catch (err) {
						data = dataCopy;
						data = (new Function("return " + data))();
						// 在这里处理错误
					}
					if (data.code == 0) {
						layer.alert("上传成功");
						if (name == "pic") {// 缩略图
							$("input[name='thumbUrl']")
									.val(data.filePath);
							var htmlImage = "<img width='250px' height='250px' src='"+hqc.base+data.filePath.replace(/\\/g, "/") +" '  alt='图片存在！' /> ";
							$("#thumb").html(htmlImage);
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