<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		短信 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			记录列表
		</small>
	</h1>
</div>

<link rel="stylesheet" type="text/css"
	href="${rc.contextPath}/resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${rc.contextPath}/resources/css/laydateplus.css" />
<script
	src="${rc.contextPath}/resources/js/laydate/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/laydate/jquery-ui.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/laydate/jquery-ui-slide.min.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/laydate/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript"
	src="${rc.contextPath}/resources/js/laydate/jquery-ui-timepicker-zh-CN.js"></script>

<form class="form-inline" style="margin-bottom: 10px;">
	<div class="form-group">
		<input type="text" datatype="s" style="width: auto;" sucmsg="haha"
			id="mobile" name="mobile"  value="${request.mobile }"
			placeholder="手机号" class="col-xs-10 col-sm-5" />
	</div>
	<div class="form-group">
		<div class="col-sm-9">
			<input id="beginDate" type="text" style="width: auto;" datatype="s"
				sucmsg="haha" name="beginDate" value="${request.beginDate }"
				placeholder="开始时间" class="col-xs-10 col-sm-5" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-9">
			<input id="endDate" type="text" style="width: auto;" datatype="s"
				sucmsg="haha" name="endDate" value="${request.endDate }"
				placeholder="结束时间" class="col-xs-10 col-sm-5" />
		</div>
	</div>
	</div>
	<button type="submit" class="btn btn-primary"
		style="border: 0px; width: 90px;">搜索</button>
</form>



<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 50px;">编号</th>
			<th>手机号码</th>
			<th>短信内容</th>
			<th>发送状态</th>
			<th>发送时间</th>
			<th style="width: 250px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list smsLogListPage.result as smsLog>
		<tr>
			<td>${smsLog.id}</td>
			<td>${smsLog.mobile}</td>
			<td>${smsLog.message}</td>
			<td>${smsLog.sendStatus}</td>
			<td>${smsLog.createTime}</td>
			<!--             <td><#if channel.status==1><font color="red">√</font><#else><font color="red">X</font></#if></td> -->
			<td><a onclick="delSmsLog(${smsLog.id})">删除</a></td>
		</tr>
		</#list>
	</tbody>
</table>
<@page totalCount="${smsLogListPage.totalCount}"
pageNo="${smsLogListPage.pageNo}"
totalPage="${smsLogListPage.totalPage}" url=""> </@page>
<script>
    /**
     * 编辑通道
     * @param catId
     */
    function editChannel(catId){
        art.dialog.open('/smsChannel/edit?id='+catId,{
            title: '编辑通道',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 添加通道
     */
    function addChannel(){
        art.dialog.open('/smsChannel/add',{
            title: '添加通道',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }


    /**
     * 删除通道
     * @param catId
     */
    function delChannel(catId){
        if (confirm("确认要删除该条信息吗 ?")) {
            $.post("/smsLog/delete", { "id":catId},
	            function(data){
	                if(data.retCode==0){
	                    alert("删除失败");
	                } else {
	                    alert("删除成功");
	                }
	                location.reload();
	            }
            );
        }
    }
    function toPage(page){
    	var endDate = $("#endDate").val();
    	var beginDate = $("#beginDate").val();
    	var mobile = $("#mobile").val();
        var url = window.location.pathname+"?page="+page+"&mobile="+mobile+"&beginDate="+beginDate+"&endDate="+endDate;
        self.location=url;
    }
    $(function(){
    	$('#beginDate').datetimepicker({
    		showHour: false,
    		showMinute: false,
    		showSecond: false,
    		showTime: false,
			timeFormat: 'hh:mm:ss'
		 });
		 $('#endDate').datetimepicker({
// 			   showSecond: true,
// 			   timeFormat: 'hh:mm:ss'
			showHour: false,
    		showMinute: false,
    		showSecond: false,
    		showTime: false,
			timeFormat: 'hh:mm:ss'
		 });
    	
    })
</script>

</@htmlBody>
