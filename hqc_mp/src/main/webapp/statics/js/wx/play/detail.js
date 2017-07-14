var flag = false;

function addlike(e) {
	if (flag) { // 已点赞
		return false;
	}
	flag = true;
	$("#flag").removeClass("glyphicon");
	$("#flag").removeClass("glyphicon-thumbs-up");
	$("#flag").addClass("am-icon-thumbs-up");
	var id = $("#like").val();
	$.ajax({
				url : '../list/addLike',
				dataType : 'json',
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				data : {
					articleId : id
				},
				type : 'post'
			});
	$("#likeCount").text(parseInt($("input[name='likeCount']").val()) + 1);
}