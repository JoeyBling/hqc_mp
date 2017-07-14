<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>会员用户管理</title>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
    <link href="${base}/statics/css/octopus/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${base}/statics/css/bootstrap-datetimepicker.min.css">
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/bootstrap-datetimepicker.min.js"></script>
    <script src="${base}/statics/libs/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">会员用户管理</a></li>
    </ul>
</div>


<div class="rightinfo" >
<div id="rightbody">
 <div class="tools">
    <div class="tools">
            <div class="row">
            <div class="col-xs-2">
                <input type="text" class="form-control" id="phone2" name="phone2" placeholder="请输入手机号"/>
            </div>
         <div class="col-xs-2">
                <input type="text" class="form-control" id="cardNo" name="cardNo" placeholder="请输入会员号"/>
            </div>
             <div class="col-xs-2">
                        <select class="form-control" name="status" id="status">
                            <option value=0>状态</option>
                            <option value=1>启用</option>
                            <option value=2>禁用</option>
                        </select>
                    </div>
        <ul class="toolbar">
	        [@shiro.hasPermission name="member:member:list"]
		          <li class="click" onclick="queryList(null,0)"><span><img
		                                    src="/hqc_mp/statics/images/octopus/find024.png"></span>查询
		                            </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="member:member:save"]
	            <li class="click" onclick="addMember()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	            </li>
            [/@shiro.hasPermission]
	        [@shiro.hasPermission name="member:member:update"]
	            <li class="click" onclick="openUpdateMember()"><span><img
	                    src="${base}/statics/images/octopus/t02.png"/></span>修改
	            </li>
            [/@shiro.hasPermission]
            
	        [@shiro.hasPermission name="member:member:delete"]
	            <li class="click" onclick="deleteMember()"><span><img src="${base}/statics/images/octopus/t03.png"/></span>删除
	            </li>
            [/@shiro.hasPermission]
        </ul>
    </div>
</div>
    <input type='hidden' value='1' id='page'/>
    <table class="tablelist">
        <thead>
        <tr>
            <th><input type="checkbox" id="selectAll"/></th>
            <th>id</th>
            <th>手机号码</th>
            <th>会员卡号</th>
            <th>会员等级</th>
            <th>会员姓名</th>
            <th>用户当前总积分</th>
            <th>会员注册时间</th>
            <th>会员更新时间</th>
            <th>状态</th>
            <th>操作</th>
             
        </tr>
        </thead>
        <tbody id="list">
        </tbody>
    </table>

    <div class="pagin">
        <div class="message">共<i class="total"></i>条记录，&nbsp&nbsp共<i class="pagetoal"></i>页&nbsp&nbsp当前显示第&nbsp;<i
                    class="page" style="margin-left:-0.1px"></i>页&nbsp&nbsp每页显示&nbsp&nbsp
                <select onchange="selectByLimit()" style="border:1px solid  black" id="limit">
	                <option value="10">10</option>
	                <option value="20">20</option>
	                <option value="50">50</option>
	                <option value="100">100</option>
            	</select>条
            </div>
        <ul class="paginList" style="margin-right:300px">
        </ul>
    </div>
    </div> 
</div>
       <div  class="tabson"  id="tab2" style="display:none;margin-top:10px;">
    		 <form id="updatePassword">
	    		  <ul class="forminfo">
	    		    <li><label>原密码</label><input name="oldPassword" id="oldPassword" type="password" class="dfinput"  /></li>
	    		    <li><label>新密码</label><input name="newPassword" id="newPassword" type="password" class="dfinput" /></li>
	    		    <li><label>确认密码</label><input name="secPassword" id="secPassword" type="password" class="dfinput" /></li>
	    		     <li><label>&nbsp;</label><input name="" type="button" class="btn btn-primary" onclick="updatePassword();"  value="确认修改"/></li>
	    		</ul>
	    	</form>
    	 </div>

    <div id="add" style="display:none;margin-top:10px;">
    <form id="addForm" class="form-horizontal">
        <div class="form-group" >
            <div class="col-sm-2 control-label">手机号码</div>
            <div class="col-sm-3" id="phone">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">会员姓名</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="trueName" name="trueName" placeholder="会员姓名"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">会员头像</div>
            <div class="col-sm-3">
                <input type="file" id="pic" onchange="upload(this)" name="pic" class="form-control"/>
            </div>
          
            <input type="hidden" id="avatar" name="avatar" value="">
        </div>
		<div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-3" id="avatarImg">
            </div>
        </div>
        <div class="form-group" id="mppwd">
            <div class="col-sm-2 control-label">会员密码</div>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="password" name="password" placeholder="会员密码"/>
            </div>
        </div>
         <div class="form-group" id="mppssword">
            <div class="col-sm-2 control-label">确认密码</div>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="pwd" name="pwd" placeholder="确认密码"/>
            </div>
        </div>
         
        <div class="form-group" >
            <div class="col-sm-2 control-label">会员生日</div>
             <div class="col-xs-3 input-append date form_datetime">
                        <input type="text" class="form-control" id="birth" name="birth" value="" readonly placeholder="会员生日"/>
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
        </div>
        <div class="form-group" id="vipname">
            <div class="col-sm-2 control-label">会员等级</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="vip" name="vip" placeholder="会员等级"/>
            </div>
        </div>
 <div class="form-group" id="memberStatus">
            <div class="col-sm-2 control-label">状态</div>
            <div class="col-sm-3">
                <label class="radio-inline">
                    <input type="radio" name="status" id="enable" 
                           value="1"/>启用
                </label>
                <label class="radio-inline">
                    <input type="radio" name="status" id="disable" 
                           value="2" checked="checked"/>禁用
                </label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <input type="button" class="btn btn-primary" onclick="saveOrUpdate(this)" value="确定"/>
            &nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload(this)" value="返回"/>
        </div>
          <input type="hidden" class="form-control" id="id" name="id" />
           <input type="hidden" class="form-control" id="integral" name="integral" />
    </form>
</div>

<div id="select" style="display:none;margin-top:20px;">
    <form id="selectForm" class="form-horizontal">

        <div class="form-group">
            <div class="col-sm-2 control-label">微信昵称</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="nickName" name="nickName" placeholder="微信昵称"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">公众号标识</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="openId" name="openId" placeholder="微信绑定公众号id"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">小程序</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="miniOpenId" name="miniOpenId" placeholder="小程序OPEN_ID"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">会员头像</div>
            <div class="col-sm-8">
                <input type="file" id="pic" onchange="upload(this)" name="pic" class="form-control"/>
            </div>
            <input type="hidden" id="avatar" name="avatar" value="">
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-3" id="avatarImg1">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">会员密码</div>
            <div class="col-sm-8">
                <input type="password" class="form-control" id="pass" name="pass" placeholder="会员密码"/>
            </div>
        </div>


        <div class="form-group">
            <div class="col-sm-2 control-label">上一年度积分</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="lastYearIntegral" name="lastYearIntegral"
                       placeholder="上一年度积分"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">本年度积分</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="currentYearIntegral" name="currentYearIntegral"
                       placeholder="本年度积分"/>
            </div>
        </div>
         <div class="form-group">
            <div class="col-sm-2 control-label">会员生日</div>
            <div class="col-sm-8">
                <input type="text" class="form-control" id="birthday" name="birthday"
                       placeholder="会员生日"/>
            </div>
        </div>
        <input type="hidden" name="id"/>
    </form>
    
    
    
</div>
<script src="${base}/statics/js/octopus/service/mpmember.js"></script>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        linkFormat: "yyyy-mm-dd hh:ii",
        autoclose:true,
        language: 'zh-CN'
    });
</script>
</body>
</html>