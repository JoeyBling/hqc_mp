<!DOCTYPE HTML>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <title>${hqc} - 个人信息</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${base}/statics/css/amazeui.min.css">
    <link rel="stylesheet" href="${base}/statics/css/mobiscroll_date.css">
    [#include "/wx/common_css.ftl"]
    [#include "/wx/common_js.ftl"]
    <script src="${base}/statics/libs/mobiscroll_date.js" charset="gb2312"></script>
    <script src="${base}/statics/libs/mobiscroll.js"></script>

    <script src="${base}/statics/libs/amazeui.min.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/libs/amazeui.dialog.min.js"></script>
    <style type="text/css">
        .menuList {
            background: #FFF;
            margin-top: 5px;
            padding-left: 15px;
        }

        .menuList div li {
            list-style: none;
            height: 50px;
            line-height: 60px;
            font-size: 17px;
            color: #282828;
            font-family: "方正姚体";
            border-bottom: solid 1px #C3C3C3;
        }

        .can-up {
            background: url(${base}/statics/images/wx/user/icon/jiantou.png) no-repeat 95%;
            background-size: 8px;
        }

        li div {
            float: right;
            margin-right: 5%;
            color: #808080;
        }

        .can-up div {
            margin-right: 10%;
        }

        #avatar {
            list-style: none;
            height: 100px;
            line-height: 100px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var currYear = (new Date()).getFullYear();
            var opt = {};
            opt.date = {preset: 'date'};
            opt.datetime = {preset: 'datetime'};
            opt.time = {preset: 'time'};
            opt.default = {
                theme: 'android-ics light', //皮肤样式
                display: 'modal', //显示方式 
                mode: 'scroller', //日期选择模式
                dateFormat: 'yyyy-mm-dd',
                lang: 'zh',
                showNow: true,
                nowText: "今天",
                startYear: currYear - 30, //开始年份
                endYear: currYear, //结束年份
                onSelect: function (valueText, inst) {
                    //点击确定以后的结果
                    $.ajax({
                        type: "POST",
                        url: "${base}/wx/member/birthday",
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        data: {
                            birthday: valueText
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result.code === 0) {// 更新成功
                                AMUI.dialog.alert({
                                    content: "修改成功",
                                    onConfirm: function () {
                                        // console.log('close');
                                    }
                                });
                                window.location.href = location.href + '?time='
									+ ((new Date()).getTime());
                            } else {
                                AMUI.dialog.alert({
                                    content: result.msg,
                                    onConfirm: function () {
                                        // console.log('close');
                                    }
                                });
                                if (data.code == 1) {
                                    window.location.href = "${base}/wx/user/login.html";
                                }
                            }
                        },
                        error: function () {
                            $("#my-modal-loading").modal('close');
                            AMUI.dialog.alert({
                                content: '系统出现异常!',
                                onConfirm: function () {
                                    window.location.href = location.href + '?time='
									+ ((new Date()).getTime())
                                    // console.log('close');
                                }
                            });
                        }
                    });

                }
            };

            $("#USER_AGE").mobiscroll($.extend(opt['date'], opt['default']));

        });
        
		/**
		 * 更改会员生日
		 * 
		 * @param {}
		 *            e
		 */
		function changeBirthday(e) {
			[#if !birthday??]
			 $('#my-confirm').modal({
		        relatedTarget: this,
		        onConfirm: function(options) {
						$('#USER_AGE').mobiscroll('show');
		        },
		        onCancel: function() {
		        }
		      });
		   	[#else]
		      	layer.msg('不能再次更改会员生日', {
						icon : 5
					});
			[/#if]
		}
    </script>
</head>
<body>
[#-- Confirm UI --]
[#assign confirmId="my-confirm"]
[#assign confirmTitle="提示"]
[#assign confirmContent="会员生日只能修改一次，修改后不能再次修改"]
[#include "/wx/confirmUI.ftl"]
<ul class="menuList">
    <div>
        <a href="${base}/wx/user/userHeadPort.html">
            <li class="can-up" id="avatar">头像
                <div>
                    <image src="${base}/${member.avatar!""}" onerror="src='${base}/statics/images/wx/user/user_avatar.png'" class="am-circle" height="60px"
                    width="60px"/>
                </div>
            </li>
        </a>
    </div>
    <div class="clearfix"></div>
    <div><a href="javascript:;" onclick="updateName();">
        <li class="can-up">会员名
            <div>${member.trueName!""}</div>
        </li>
    </a></div>
    <div class="clearfix"></div>
    <div><a href="javascript:;" onclick="validateOldPhone();">
        <li class="can-up">手机号码
            <div>${member.phone!""}</div>
        </li>
    </a></div>
    <div class="clearfix"></div>
    <div>
        <li>会员卡号
            <div>${member.cardNo!""}</div>
        </li>
    </div>
    <div class="clearfix"></div>
    <div>
        <li>会员等级
            <div>${member.vipLevelEntity.vipName!""}</div>
        </li>
    </div>
    <div class="clearfix"></div>
    <div>
        <li>总积分
            <div>${member.integral!""}</div>
        </li>
    </div>
    <div class="clearfix"></div>
    <div>
        <li>注册时间
            <div>${createTime!""}</div>
        </li>
    </div>
    <div class="clearfix"></div>
    <div>
        <li onclick="changeBirthday(this);">会员生日
            <div id="birthday">${birthday!""}</div>
            <input type="hidden"
                   name="USER_AGE"
                   id="USER_AGE" readonly
                   value="${birthday!""}"/>
        </li>
    </div>
    <div class="clearfix"></div>
   [#-- <div><a href="javascript:;" onclick="updatePassword();">
        <li class="can-up">修改密码
            <div></div>
        </li>
    </a></div>--]
    <div class="clearfix"></div>
</ul>

<div class="am-modal am-modal-prompt" tabindex="-1" id="updateName">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">会员名修改</div>
        <div class="am-modal-bd">
            <input type="text" class="am-modal-prompt-input" id="trueName" value="${member.trueName!""}">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="updatePassword">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">修改密码</div>
        <div class="am-modal-bd">

            <input type="password" class="am-modal-prompt-input" id="oldPassword" placeholder="原密码">
            <input type="password" class="am-modal-prompt-input" id="newPassword" placeholder="新密码">
            <input type="password" class="am-modal-prompt-input" id="secPassword" placeholder="确认密码">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="codeOldPhone">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">验证原手机号码</div>
        <div class="am-modal-bd">

            <input type="text" class="am-modal-prompt-input" id="oldphone" value="${member.phone!''}" disabled>
            <input type="text" class="am-modal-prompt-input" id="oldCode" placeholder="验证码"
                   autocomplete="off" maxlength="5" size="11">

            <div id="phoneCodeSendSuccess" class="err-tips" style="color: #3c763d;display:none"> 验证码发送成功，请注意查收</div>
            <button id="oldGetPhoneCode" class="am-modal-prompt-input am-btn-success" onclick="getPhoneCode(this,1)"
                    type="button"
                    class="btn btn-success btn-block">获取验证码
            </button>
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="codeNewPhone">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">更换新手机号</div>
        <div class="am-modal-bd">
            <input type="text" class="am-modal-prompt-input" id="newphone" placeholder="手机号">
            <input type="text" class="am-modal-prompt-input" id="newCode" placeholder="验证码"
                   autocomplete="off" maxlength="5" size="11">

            <div id="newPhoneCodeSendSuccess" class="err-tips" style="color: #3c763d;display:none"> 验证码发送成功，请注意查收
            </div>
            <button id="newGetPhoneCode" class="am-modal-prompt-input am-btn-success" onclick="getPhoneCode(this,2)"
                    type="button"
                    class="btn btn-success btn-block">获取验证码
            </button>
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>提交</span>
        </div>
        <input type="hidden" id="loginmsg" value="${msg!""}"/>
	</div>
</div>        
</body>
<script type="text/javascript" src="${base}/statics/js/wx/user/userInfo.js"></script>
</html>