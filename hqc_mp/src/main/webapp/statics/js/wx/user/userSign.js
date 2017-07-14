function week() {
	var objDate = new Date();
	var week = objDate.getDay();
	switch (week) {
		case 0 :
			week = "周日";
			break;
		case 1 :
			week = "周一";
			break;
		case 2 :
			week = "周二";
			break;
		case 3 :
			week = "周三";
			break;
		case 4 :
			week = "周四";
			break;
		case 5 :
			week = "周五";
			break;
		case 6 :
			week = "周六";
			break;
	}
}

$(function() {
			sign();
		});

function sign() {
	$("#my-modal-loading").modal();
	var current = new Date();
	var s = this;
	$.ajax({
		url : hqc.base + "wx/user/sign/selectSign",
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 0) {
				$("#my-modal-loading").modal('close');
				var signList = data.signList;
				$(s).addClass("current");
				var str = drawCal(current.getFullYear(),
						current.getMonth() + 1, signList);
				$("#signList").html(str);
				if (data.result == 0) {
					
				} else {
					$("#doSign").html("	<button class='ui-btn-lg ui-btn-danger'> 已签到  </button>");
				}
			} else if (data.code == 100) {
					layer.msg(data.msg, {
						icon : 5,
						time : 2000
							// 2秒关闭（如果不配置，默认是3秒）
						}, function() {
						parent.location.href = hqc.base + 'wx/user/login.html';
					});
				}else {
				$("#my-modal-loading").modal('close');
				layer.msg(data.msg);
			}
		},
		error : function() {
			$("#my-modal-loading").modal('close');
			AMUI.dialog.alert({
						content : '系统出现异常!',
						onConfirm : function() {
							window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
							// console.log('close');
						}
					})
		}
	});
};

function doSign(){
	$.ajax({
		url : hqc.base + 'wx/user/sign/doSign',
		type : "POST",
		dataType : "json",
		success : function(result) {
			if (result.code == 0) {
				layer
						.msg(
								"签到成功,用户积分+"
										+ result.result,
								{
									icon : 6
								});
				sign();
			} else {
				layer.msg(result.msg);
				layer.close(index);
			}
		},
		error : function() {
			layer.msg("系统错误");
			layer.close(index);
		}
	})
}

function getDaysInmonth(iMonth, iYear) {
	var dPrevDate = new Date(iYear, iMonth, 0);
	return dPrevDate.getDate();
}
function bulidCal(iYear, iMonth) {
	var aMonth = new Array();
	aMonth[0] = new Array(7);
	aMonth[1] = new Array(7);
	aMonth[2] = new Array(7);
	aMonth[3] = new Array(7);
	aMonth[4] = new Array(7);
	aMonth[5] = new Array(7);
	aMonth[6] = new Array(7);
	var dCalDate = new Date(iYear, iMonth - 1, 1);
	var iDayOfFirst = dCalDate.getDay();
	var iDaysInMonth = getDaysInmonth(iMonth, iYear);
	var iVarDate = 1;
	var d, w;
	aMonth[0][0] = "日";
	aMonth[0][1] = "一";
	aMonth[0][2] = "二";
	aMonth[0][3] = "三";
	aMonth[0][4] = "四";
	aMonth[0][5] = "五";
	aMonth[0][6] = "六";
	for (d = iDayOfFirst; d < 7; d++) {
		aMonth[1][d] = iVarDate;
		iVarDate++;
	}
	for (w = 2; w < 7; w++) {
		for (d = 0; d < 7; d++) {
			if (iVarDate <= iDaysInMonth) {
				aMonth[w][d] = iVarDate;
				iVarDate++;
			}
		}
	}
	return aMonth;
}

function ifHasSigned(signList, day) {
	var signed = false;
	$.each(signList, function(index, item) {
				var date = (new Date(item.createTime * 1000)).getDate();
				if (date == day) {
					signed = true;
					return false;
				}
			});
	return signed;
}

function drawCal(iYear, iMonth, signList) {
	var myMonth = bulidCal(iYear, iMonth);
	var htmls = new Array();
	htmls.push("<div  id='sign_layer'  style='margin-right:0px;'>");
	htmls.push("<div class='sign_succ_calendar_title'>");
	// htmls.push("<div class='calendar_month_next'> </div>");
	// htmls.push("<div class='calendar_month_prev'> </div>");
	htmls.push("<div class='col-xs-12 calendar_month_span'>" + iYear + "年"
			+ iMonth + "月</div>");
	htmls.push("</div>");
	htmls.push("<div class='usersign' id='sign_cal'>");
	htmls.push("<table>");
	htmls.push("<tr class='row' style='margin-right:0px;'>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][0] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][1] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][2] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][3] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][4] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][5] + "</th>");
	htmls.push("<th class='col-xs-1'>" + myMonth[0][6] + "</th>");
	htmls.push("</tr>");
	var d, w;
	for (w = 1; w < 7; w++) {
		htmls.push("<tr class='row' style='margin-right:0px;'>");
		for (d = 0; d < 7; d++) {
			var ifHasSigned = this.ifHasSigned(signList, myMonth[w][d]);
			console.log(ifHasSigned);
			if (ifHasSigned) {
				htmls.push("<td class='col-xs-1 on'>"
						+ (!isNaN(myMonth[w][d]) ? myMonth[w][d] : " ")
						+ "</td>");
			} else {
				if (!isNaN(myMonth[w][d]) || w < 6) {
					if (myMonth[w][d] == new Date().getDate()) {
						htmls
								.push("<td class='col-xs-1' style='background-color:#D1EEEE'>"
										+ (!isNaN(myMonth[w][d])
												? myMonth[w][d]
												: " ") + "</td>");
					} else {
						htmls.push("<td class='col-xs-1'>"
								+ (!isNaN(myMonth[w][d]) ? myMonth[w][d] : " ")
								+ "</td>");
					}
				}
			}
		}
		htmls.push("</tr>");
	}
	htmls.push("</table>");
	htmls.push("<div>（周一~周六每天5积分，周日10积分）</div>");
	htmls.push("</div>");
	htmls.push("</div>");
	return htmls.join('');
}