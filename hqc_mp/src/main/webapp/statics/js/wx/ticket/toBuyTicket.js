$(function() {
	$("#jian").click(function() {
				var num = $("#num").val();
				var price = $("#price").text();
				if (num == 1) {
					$("#num").val(1);
				} else {
					var f = parseInt(num) - 1;
					$("#num").val(f);
					$("#zhang").text(f);
					$("#allprice").text(parseInt(price) * parseInt(f));
				}
			});

	$("#jia").click(function() {
				var num = $("#num").val();
				var price = $("#price").text();
				if (num == 5) {
					$("#num").val(5);
				} else {
					var f = parseInt(num) + 1;
					$("#num").val(f);
					$("#zhang").text(f);
					$("#allprice").text(parseInt(price) * parseInt(f));
				}
			});
	$("#todate").click(function() {
				// 重新计算价格
				$("#price").text($(this).find("input").val());
				var num = $("#num").val();
				var price = $("#price").text();
				var f = parseInt(num);
				$("#allprice").text(parseInt(price) * parseInt(f));

				$("#totodate").css({
							"background" : "#fff",
							"color" : "black"
						})
				$("#totodate_one").css("color", "#fc7700");
				$("#todate").css({
							"background" : "#fc7700",
							"color" : "#fff"
						})
				$("#todate_one").css("color", "#fff");

				$("#moredate").css({
							"background" : "#fff",
							"color" : "black"
						})
				$("#selectdate_one").css("color", "#fc7700");
			})

	$("#totodate").click(function() {
				// 重新计算价格
				$("#price").text($(this).find("input").val());
				var num = $("#num").val();
				var price = $("#price").text();
				var f = parseInt(num);
				$("#allprice").text(parseInt(price) * parseInt(f));

				$("#todate").css({
							"background" : "#fff",
							"color" : "black"
						})
				$("#todate_one").css("color", "#fc7700");
				$("#totodate").css({
							"background" : "#fc7700",
							"color" : "#fff"
						})
				$("#totodate_one").css("color", "#fff");

				$("#moredate").css({
							"background" : "#fff",
							"color" : "black"
						})
				$("#selectdate_one").css("color", "#fc7700");
			})

	$("#xiangqing").click(function() {
				$("#yuding").css("color", "#333");
				$("#xiangqing").css("color", "#00c267");
				$("#show_").hide();
				$("#hide_").show();
				$("#about").hide();
				$("#ticketContent").show();

			})
	$("#yuding").click(function() {
				$("#xiangqing").css("color", "#333");
				$("#yuding").css("color", "#00c267");
				$("#show_").show();
				$("#hide_").hide();
				$("#ticketContent").hide();
				$("#about").show();
			})
	var lend = 0;
	var Ycheck = false;
	$("#checkbox").click(function() {
		if (lend == 1) {
			lend = 0;
			Ycheck = false;
			$(".pact-checkbox").css("background-position", "-23.85rem -8.2rem");
		} else {
			lend = 1;
			Ycheck = true;
			$(".pact-checkbox").css("background-position", "-23.85rem -6.3rem");
		}
	});

	$("#submitbtn").click(function() {
				// 判断勾选框是否已勾选
				if (Ycheck == false) {
					$('#agree').modal({
								closeViaDimmer : false
							});
				}
			})
});

$("li[name='content']").click(function(e) {
	var cashCouponId = $(e.currentTarget).find("div input[name='cashCoupon']")[0];
	var cashvarue = $(e.currentTarget).find("input[name='faceValue']")[0];
	var priceValue = cashvarue.attributes.value.nodeValue;
	var id = cashCouponId.attributes.value.nodeValue
	if (!cashCouponId.checked) {
		cashCouponId.checked = true;
		alert("id:" + id + "价值：" + priceValue)

	}
});

function openCashList() {
	$('#my-prompt').modal({
				relatedTarget : this,
				onConfirm : function(e) {
				},
				onCancel : function(e) {
				}
			});
}