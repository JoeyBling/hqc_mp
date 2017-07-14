<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>门票管理</title>
   <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
<link href="${base}/statics/css/octopus/styleCopy.css" rel="stylesheet" type="text/css" />
<link href="${base}/statics/plugins/summernote/summernote.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${base}/statics/css/bootstrap-datetimepicker.min.css">
<script src="${base}/statics/libs/jquery.min.js"></script>
<script src="${base}/statics/libs/bootstrap.min.js"></script>
<script src="${base}/statics/plugins/layer/layer.js"></script>
<script src="${base}/statics/plugins/layui/layui.js"></script>
<script src="${base}/statics/js/common.js"></script>
<script src="${base}/statics/libs/ajaxfileupload.js"></script>
<script src="${base}/statics/plugins/summernote/summernote.js"></script>
<script src="${base}/statics/plugins/summernote/summernote-zh-CN.js"></script>
<script src="${base}/statics/libs/bootstrap-datetimepicker.min.js"></script>
<script src="${base}/statics/libs/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">门票管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">  
                    <div class="tools">
                    	<div class="row">
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="qticketName" placeholder="门票名">
                    </div>
                    <div class="col-xs-2">
                        <input type="number" class="form-control" id="minPrice" placeholder="最低价" >
                    </div>
                    <div class="col-xs-2">
                        <input type="number" class="form-control" id="maxPrice" placeholder="最高价">
                    </div>
                    <div class="col-xs-2">
                        <select class="form-control" name="qstatus" id="qstatus">
                            <option value=0>状态</option>
                            <option value=1>上架</option>
                            <option value=2>下架</option>  
                        </select>
                    </div>
                    
                        <ul class="toolbar"> 
                     
                         [@shiro.hasPermission name="ticket:ticket:list"]
                        	  <li class="click" onclick="queryList(null,0)"><span><img
                                    src="${base}/statics/images/octopus/find024.png"></span>查询
                            </li>
                             [/@shiro.hasPermission]
                            <li class="click" onclick="resetQuery()"><span><img
                                    src="${base}/statics/images/octopus/reset024.png"></span>重置
                            </li>
                             [@shiro.hasPermission name="ticket:ticket:save"]
                        	<li class="click" onclick="openAdd()"><span><img 
                        			src="${base}/statics/images/octopus/t01.png"/></span>添加
	           				</li>
	           				   [/@shiro.hasPermission]
	           				   [@shiro.hasPermission name="ticket:ticket:update"]
           					 <li class="click" onclick="openUpdate()"><span><img
	                    				src="${base}/statics/images/octopus/t02.png"/></span>修改
	            			</li>
	            			 [/@shiro.hasPermission]
	            			 [@shiro.hasPermission name="ticket:ticket:del"]
	        				 <li class="click" onclick="del()"><span><img
                                    src="${base}/statics/images/octopus/t03.png"></span>删除
                            </li>
                             [/@shiro.hasPermission]
                        </ul> 
        </div>
</div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input id="selectAll" type="checkbox"/></th>
                <th>名称</th>
                <th>单价</th>
                <th>折扣</th>
                <th>已售数量</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="list">
            </tbody>
        </table>
        <div class="pagin">
            <div class="message">共<i class="total"></i>条记录，&nbsp&nbsp共<i class="pagetoal"></i>页&nbsp&nbsp当前显示第&nbsp;<i
                    class="page" style="margin-left:-0.1px"></i>
                页&nbsp;&nbsp;每页显示&nbsp;&nbsp;
                <select onchange="selectByLimit()" style="border:1px solid  black" id="limit">
                    <option value="10">10</option>
                    <option value="20">20</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </select>
                条
            </div>
            <ul class="paginList" style="margin-right:300px">
            </ul>
        </div>
    </div>
</div>

<div id="add" style="display:none;margin-top:10px;">
    <form id="addForm" class="form-horizontal">
        <div class="form-group">
            <div class="col-sm-2 control-label">门票名称</div>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="ticketName" name="ticketName" placeholder="门票名称"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">门票单价</div>
            <div class="col-sm-3">
                <input type="number" step="10" class="form-control" id="price" name="price" placeholder="门票单价"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">市场单价</div>
            <div class="col-sm-3">
                <input type="number" step="10" class="form-control" id="marketPrice" name="marketPrice" placeholder="市场单价"/>
            </div>
        </div>
         
        <div class="form-group">
            <div class="col-sm-2 control-label">门票图片</div>
            <div class="col-sm-3">
                <input type="file" id="pic" onchange="upload(this)" name="pic" class="form-control"/>
            </div>
          
            <input type="hidden" id="thumbUrl" name="thumbUrl" value="">
        </div>
		<div class="form-group">
            <div class="col-sm-2 control-label"></div>
            <div class="col-sm-3" id="ticketImg">
            </div>
        </div>
        <div class="form-group">
                <div class="col-sm-2 control-label">状态</div>
	                <div class="col-sm-3">
	                    <label class="radio-inline">
	                        <input type="radio" id="disable" name="status" value="1"/> 上架
	                    </label>
	                    <label class="radio-inline">
	                        <input type="radio" id="normal" name="status" value="2"/> 下架
	                    </label>
	                </div>
            	</div>
           <div class="form-group">
                <div class="col-sm-2 control-label">提前购票</div>
	            <div class="col-sm-3">
	                <label class="radio-inline">
	                    <input type="radio" id="disable2" name="advance" value="1"/> 是
	                </label>
	                <label class="radio-inline">
	                    <input type="radio" id="normal2" name="advance" value="0"/> 否
	                </label>
	            </div>
          </div>
          <div class="form-group">
                <div class="col-sm-2 control-label">周末票价</div>
	                <div class="col-sm-3">
	                    <label class="radio-inline">
	                        <input type="radio" id="disableWeek" name="weekendType" value="1"/> 启用
	                    </label>
	                    <label class="radio-inline">
	                        <input type="radio" id="normalWeek" name="weekendType" value="0"/> 停用
	                    </label>
	                </div>
            	</div>
            <div class="form-group" id ="weekendPriceDiv" style="display:none">
            	<div class="col-sm-2 control-label">周末单价</div>
            	<div class="col-sm-3">
                	<input type="number" step="10" class="form-control" id="weekendPrice" name="weekendPrice" placeholder="周末票价"/>
           		 </div>
       	 </div>
            <div class="form-group">
            <div class="col-sm-2 control-label">开售时间</div>
            <div class="col-sm-3">
             <div class=" input-append date form_datetime" data-date-forma="yyyy-mm-dd">
                 <input type="text" class="form-control" id="saleDate" value="" readonly placeholder="下单开始时间"/>
                 <span class="add-on"><i class="icon-th"></i></span>
                 </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">使用时间</div>
            <div class="col-sm-3">
             <div class=" col-sm-5 input-append date form_datetime" data-date-forma="yyyy-mm-dd"  style="padding-left: 0px;">
                 <input type="text" class="form-control" id="startBuyDate" value="" readonly placeholder="开始时间"/>
                 <span class="add-on"><i class="icon-th"></i></span>
                 </div>
                 <div class="col-sm-2 ">
                 <div style="padding-top: 8px;">
                 				&nbsp;至
                 				</div>
                 </div>
                 <div class="col-sm-5 input-append date form_datetime" data-date-forma="yyyy-mm-dd" style="padding-right: 0px;">
	                 <input type="text" class="form-control" id="endBuyDate" value="" readonly placeholder="结束时间"/>
	                 <span class="add-on"><i class="icon-th"></i></span>
                 </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2 control-label">预定须知</div>
            <div class="col-sm-3">
                 <div id="about" name="about"></div>
            </div>
        </div>
        <div class="form-group">
				   	<div class="col-sm-2 control-label">门票详情</div>
				   	<div class="col-sm-3">
                   		 <div id="ticketContent" name="ticketContent"></div>
                	</div>
		</div>	
			
            	<input type = "hidden" name="id" id="id" value=""/>
            		<div class="form-group">
					<div class="col-sm-2 control-label"></div> 
					<div class="col-sm-3">
						<input type="button" id="ok" class="btn btn-primary" onclick="saveOrUpdate()" value="确定"/>
						&nbsp;&nbsp;<input type="button" class="btn btn-warning" onclick="reload()" value="返回"/>
					</div>
				</div>
    </form>
</div>
</body>
<script src="${base}/statics/js/octopus/service/mpTicket.js"></script>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
   		 format:"yyyy-mm-dd",
   		 autoclose:true,
        minView:2,
        language: 'zh-CN'
    });
</script>
</html>
