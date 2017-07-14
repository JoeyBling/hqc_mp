<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>代金券管理</title>
     <link rel="stylesheet" href="${base}/statics/css/bootstrap.min.css">
     <link href="${base}/statics/css/octopus/styleCopy.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${base}/statics/plugins/ztree/css/metroStyle/metroStyle.css">
    <link href="${base}/statics/plugins/summernote/summernote.css" rel="stylesheet" type="text/css"/>
    <script src="${base}/statics/libs/jquery.min.js"></script>
    <script src="${base}/statics/libs/bootstrap.min.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote.js"></script>
    <script src="${base}/statics/plugins/summernote/summernote-zh-CN.js"></script>
    <script src="${base}/statics/plugins/layer/layer.js"></script>
    <script src="${base}/statics/plugins/layui/layui.js"></script>
    <script src="${base}/statics/js/common.js"></script>
    <script src="${base}/statics/libs/ajaxfileupload.js"></script>

    <style>
    </style>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:void(0);">业务模块</a></li>
        <li><a href="javascript:void(0);">代金券管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div id="rightbody">
        <div class="tools">
            <div class="row">
                <div class="row col-xs-9">
                    <div class="col-xs-2">
                        <input type="text" class="form-control" id="cashCouponName1" name="cashCouponName1" placeholder="代金卷名称">
                    </div>
                    <div class="col-xs-2">
                        <select class="form-control" name="status1" id="status1">
                            <option value=''>状态</option>
                            <option value='1'>上架 </option>
                            <option value='2'>下架</option>
                        </select>
                    </div>
                    <div class="col-xs-5">
                        <div class="tools">
                            <ul class="toolbar">
	    					[@shiro.hasPermission name="goods:cashCoupon:list"]
                              <li class="click" onclick="queryList()"><span><img
                                src="${base}/statics/images/octopus/find024.png"></span>查询
                              </li>
                            [/@shiro.hasPermission]
                              <li class="click" onclick="resetQuery()"><span><img
                                 src="${base}/statics/images/octopus/reset024.png"></span>重置
                              </li>
		    	            [@shiro.hasPermission name="goods:cashCoupon:save"]
	                           <li class="click" onclick="openAddCash()"><span><img src="${base}/statics/images/octopus/t01.png"/></span>添加
	                           </li>
                            [/@shiro.hasPermission]
		    	            [@shiro.hasPermission name="goods:cashCoupon:update"]
	                           <li class="click" onclick="openUpdateCash()"><span><img
	                             src="${base}/statics/images/octopus/t02.png"/></span>修改
	                           </li>
                            [/@shiro.hasPermission]
	    		            [@shiro.hasPermission name="goods:cashCoupon:delete"]
	                           <li class="click" onclick="deleteCash()"><span><img
	                             src="${base}/statics/images/octopus/t03.png"/></span>删除
	                           </li>
                            [/@shiro.hasPermission]
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <input type='hidden' value='1' id='page'/>
        <table class="tablelist">
            <thead>
            <tr>
                <th><input type="checkbox" id="selectAll"/></th>
                <th>代金卷id</th>
                <th>代金卷名称</th>
                <th>代金卷面值</th>
                <th>所需积分</th>
                <th>最大允许兑换数量</th>
                <th>每日允许兑换数量</th>
                <th>状态 </th>
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
        <div id="addCash" style="display:none;width:980px">
        <form id="cashForm" method="post" class="form-horizontal">
            <input type="hidden" name="id" id="id"/>

            <div class="form-group">
                <div class="col-sm-2 control-label">代金卷名称</div>
                <div class="col-sm-3">
                    <input type='text' name='cashCouponName' class='form-control' id="cashCouponName" placeholder="代金卷名称"/>
                </div>
                <div class="col-sm-2 control-label">代金券面值</div>
                <div class="col-sm-3">
                    <input type='number' name='faceValue' class='form-control' id="faceValue" placeholder="代金券面值"/>
                </div>
            </div>


            <div class="form-group">
                <div class="col-sm-2 control-label">所需积分</div>
                <div class="col-sm-3">
                    <input type="number" name="integral" class="form-control" placeholder="所需积分" id="integral"  onchange="validValue(this.id)" />
                </div>
                <div class="col-sm-2 control-label">最大允许兑换数量</div>
                <div class="col-sm-3">
                    <input type="number" name="maxExchange" class="form-control" id="maxExchange"
                           onchange="validValue(this.id)" placeholder="最大允许兑换数量" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">每日允许兑换数量</div>
                <div class="col-xs-3 input-append date form_datetime">
                    <input type="number" name="dayExchange" class="form-control" id="dayExchange" placeholder="每日允许兑换数量"  onchange="validValue(this.id)" /> 
                </div>
               

                <div class="col-sm-2 control-label">状态</div>
                <div class="col-sm-3">
                     <label class="radio-inline">
                        <input type="radio" name="status" value="1"/> 上架
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="status" value="2"/> 下架
                    </label>
                </div>
            </div>
            <div class="form-group">
             <div class="col-sm-2 control-label">库存数量</div>
              <div class="col-sm-3">
                    <input type='number' name='repertory' class='form-control' id="repertory" onchange="validValue(this.id)"/>
               </div>
             <div class="col-sm-2 control-label">缩略图</div>
              <div class="col-sm-3">
                    <input type='hidden' name='cashThumb' class='form-control' id="cashThumb"/>
                   <div id="fileinput"> <input type="file" class="form-control" onchange="upload(this)" name="pic" id="pic" value=""/></div>
                 	<div id="showUploadFile">已上传文件：<a onclick="downFile()" href="javascript:void(0);" id="fileName"></a></div><div id="imgclick"><a href="javascript:void(0)" onclick="showImg()" style="color:blue">查看图片</a></div>
              </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">代金券详细介绍</div>
                <div class="col-sm-8">
                    <textarea name="about" id="about" 
                            ></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <div class="col-sm-3">
                    <input type="button" name="btn1" class="btn btn-primary" onclick="saveOrUpdate()" style="width:90px"
                           value="确定"/>
                    &nbsp;&nbsp;<input type="button" name="btn1" class="btn btn-warning" onclick="reload()"
                                       style="width:90px" value="返回"/>

                    <div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div id="showImg" style="display:none">
    	
    </div>
    <script src="${base}/statics/js/octopus/service/cashCoupon.js"></script>
       <script type="text/javascript">
     function validValue(id) {
            var value = document.getElementById(id).value;
            var r = /^\+?[1-9][0-9]*$/;
            if (!r.test(value)) {
                alert("请输入正整数");
                document.getElementById(id).value = "";
            }
        }
  
     //加载编辑器  
       $(document).ready(function () {
           $('textarea').summernote({
               height: 400,
               minHeight: 300,
               maxHeight: 500,
               focus: true,
               lang: 'zh-CN',
               // 重写图片上传  
               callbacks: {
                   onImageUpload: function (files) { //the onImageUpload API  
                       img = sendFile(files[0]);
                   }
               }
           });
       });

       function sendFile(file) {
           var data = new FormData();
           data.append("file", file);
           data.append("name", "file");
           data.append("uploadType", "2");
           console.log(data);
           $.ajax({
               data: data,
               type: "POST",
               url: "${base}/file/upload",
               cache: false,
               contentType: false,
               processData: false,
               success: function (result) {
                   $("textarea").summernote('insertImage', "${base}/" + result.filePath, 'img'); // the insertImage API 
               }
           });
       }
    </script>
</body>
</html>