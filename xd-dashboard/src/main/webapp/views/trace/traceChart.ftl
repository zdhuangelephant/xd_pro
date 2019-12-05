<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->
   <link href="https://cdn.bootcss.com/zTree.v3/3.5.29/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/zTree.v3/3.5.29/js/jquery.ztree.all.min.js"></script>
  <style>
    /*按钮*/
    .icon_div {
        display: inline-block;
        height: 25px;
        width: 35px;
        background: url(http://c.csdnimg.cn/public/common/toolbar/images/f_icon.png) no-repeat 12px -127px;
    }

    .icon_div a {
        display: inline-block;
        width: 27px;
        height: 20px;
        cursor: pointer;
    }

    /*end--按钮*/

    /*ztree表格*/
    .ztree {
        padding: 0;
        border: 2px solid #CDD6D5;
    }

    .ztree li a {
        vertical-align: middle;
        height: 30px;
    }

    .ztree li > a {
        width: 100%;
    }

    .ztree li > a,
    .ztree li a.curSelectedNode {
        padding-top: 0px;
        background: none;
        height: auto;
        border: none;
        cursor: default;
        opacity: 1;
    }

    .ztree li ul {
        padding-left: 0px
    }

    .ztree div.diy span {
        line-height: 30px;
        vertical-align: middle;
    }

 	.ztree div.diy1 {
        height: 100%;
        width: 50%;
        line-height: 30px;
        border-top: 1px dotted #ccc;
        border-left: 1px solid #eeeeee;
        text-align: left;
        display: inline-block;
        box-sizing: border-box;
        color: #6c6c6c;
        font-family: "SimSun";
        font-size: 12px;
        overflow: hidden;
    }

    .ztree div.diy {
        height: 100%;
        width: 10%;
        line-height: 30px;
        border-top: 1px dotted #ccc;
        border-left: 1px solid #eeeeee;
        text-align: center;
        display: inline-block;
        box-sizing: border-box;
        color: #6c6c6c;
        font-family: "SimSun";
        font-size: 12px;
        overflow: hidden;
    }
    
      .ztree div.diy2 {
        height: 100%;
        width: 20%;
        line-height: 30px;
        border-top: 1px dotted #ccc;
        border-left: 1px solid #eeeeee;
        text-align: center;
        display: inline-block;
        box-sizing: border-box;
        color: #6c6c6c;
        font-family: "SimSun";
        font-size: 12px;
        overflow: hidden;
    }

    .ztree div.diy:first-child {
        text-align: left;
        text-indent: 10px;
        border-left: none;
    }

    .ztree .head {
        background: #5787EB;
    }

    .ztree .head div.diy {
        border-top: none;
        border-right: 1px solid #CDD2D4;
        color: #fff;
        font-family: "Microsoft YaHei";
        font-size: 14px;
    }
</style>
<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<div class="page-container">
		<!-- BEGIN PAGE -->

		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">

				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">


						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							业务流

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">Trace</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>
								<a href="#">业务流图</a>
							</li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
 			<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>业务流模型图</div>

							</div>


							<div class="portlet-body">
								<div class="row-fluid">
									<input type="text" id="traceId" placeholder="traceId" value="server-21eca8f3-4fa3-44ca-86ee-4e25c46f9b91">&nbsp;
						        	<a href="#" class="btn blue" onclick="query()">Search</a>
								</div>
								
								<table class="table table-striped table-bordered table-hover table-full-width">
							
								</table>
								
								<div class="layer">
								    <div id="tableMain">
								        <ul id="dataTree" class="ztree">
								
								        </ul>
								    </div>
								</div>


						</div>
		
						

					</div>

					</div>

				</div>					
			<!-- END PAGE CONTAINER-->
		</div>          
                  
		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->
	
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" style="width:700px" id="crontMonitorModal" tabindex="-1" role="dialog" aria-labelledby="crontMonitorModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content" style="width:700px">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">日志详情</h4>
	            </div>
		        <form action="#"  class="form-horizontal" id="crontMonitorForm">
		            <div style="width:650px" class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="id" name="id"/>
		            	<div  class="control-group">
							<label class="control-label">方法名</label>
							<div class="controls">
								<input style="width:450px" type="text" id="excutePoint"   />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">记录时间</label>
							<div class="controls">
								<input style="width:450px" type="text" id="sLogTime" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">程序名称</label>
							<div class="controls">
								<input style="width:450px" type="text" id="projectName" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">traceId</label>
							<div class="controls">
								<input style="width:450px" type="text" id="traceIdView"  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">进程标识符</label>
							<div class="controls">
								<input style="width:450px" type="text" id="processId" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">方法消耗时间</label>
							<div class="controls">
								<input style="width:450px" type="text" id="executionTime"  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">入参</label>
							<div class="controls">
								<textarea style="width:450px" id="entryParameter" rows="5" ></textarea>

							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行结果</label>
							<div class="controls">
								<textarea style="width:450px"  rows="5"  id="excuteResult"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">错误信息</label>
							<div class="controls">
								<textarea style="width:450px"  rows="5"  id="errorMsg"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">方法链ID</label>
							<div class="controls">
								<input style="width:450px" type="text" id="myId"  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">方法链父ID</label>
							<div class="controls">
								<input style="width:450px" type="text" id="parentId"  />
							</div>
						</div>
			
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </form>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>

<!-- END BODY -->
</body>  
	<script>	
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
	
		
   
    var zTreeNodes;
    var setting = {
        view: {
            showLine: false,
            showIcon: false,
            addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    /**
     * 自定义DOM节点
     */
    function addDiyDom(treeId, treeNode) {
        var spaceWidth = 3;
        var liObj = $("#" + treeNode.tId);
        var aObj = $("#" + treeNode.tId + "_a");
        var switchObj = $("#" + treeNode.tId + "_switch");
        var icoObj = $("#" + treeNode.tId + "_ico");
        var spanObj = $("#" + treeNode.tId + "_span");
        aObj.attr('title', '');
        aObj.append('<div class="diy1 swich"></div>');
        var div = $(liObj).find('div').eq(0);
        switchObj.remove();
        spanObj.remove();
        icoObj.remove();
        div.append(switchObj);
        div.append(spanObj);
        var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
        switchObj.before(spaceStr);
        var editStr = '';
        if(treeNode.errorMsg!= undefined&&treeNode.errorMsg!=""&&treeNode.errorMsg!="null"){
	        editStr += '<div class="diy" style="color:red">' + (treeNode.executionTime == null ? '&nbsp;' : treeNode.executionTime) + '</div>';
	        var logTime = '<div style="color:red" title="' + treeNode.logTime + '">' + treeNode.logTime + '</div>';
	        editStr += '<div style="color:red" class="diy2">' + (treeNode.logTime == '-' ? '&nbsp;' : logTime ) + '</div>';
	        editStr += '<div style="color:red" class="diy">' + (treeNode.projectName == null ? '&nbsp;' : treeNode.projectName ) + '</div>';
	        editStr += '<div style="color:red" class="diy">' + formatHandle(treeNode) + '</div>';
	     }else{
	     	editStr += '<div class="diy">' + (treeNode.executionTime == null ? '&nbsp;' : treeNode.executionTime) + '</div>';
	        var logTime = '<div title="' + treeNode.logTime + '">' + treeNode.logTime + '</div>';
	        editStr += '<div class="diy2">' + (treeNode.logTime == '-' ? '&nbsp;' : logTime ) + '</div>';
	        editStr += '<div class="diy">' + (treeNode.projectName == null ? '&nbsp;' : treeNode.projectName ) + '</div>';
	        editStr += '<div class="diy">' + formatHandle(treeNode) + '</div>';
	     }
        aObj.append(editStr);
    }
    var traceList;
    /**
     * 查询数据
     */
    function query() {
    
    	var traceId = $("#traceId").val();
		$.post("/trace/traceChart", {traceId:traceId},
                    function(data){
                    	var u=JSON.parse(data);
                    	if(u.state=="fail"){
                    		alert(u.data);
                    	}else{
                    	    traceList=u.traceList;
					        //初始化树
					        for(var i=0;i<traceList.length;i++){
					        	traceList[i].id=traceList[i].myId;
					        	traceList[i].pId=traceList[i].parentId;
					        	traceList[i].name=traceList[i].excutePoint;
					        }
					        zTreeNodes = traceList;
					        $.fn.zTree.init($("#dataTree"), setting, zTreeNodes);
					        var treeObj = $.fn.zTree.getZTreeObj("dataTree");
					        treeObj.expandAll(true); 
					        //添加表头
					        var li_head = ' <li class="head"><a><div class="diy1">方法名称</div><div class="diy">执行耗时</div><div class="diy2">记录时间</div>' +
					            '<div class="diy">程序标志符</div><div class="diy">操作</div></a></li>';
					        var rows = $("#dataTree").find('li');
					        if (rows.length > 0) {
					            rows.eq(0).before(li_head)
					        } else {
					            $("#dataTree").append(li_head);
					            $("#dataTree").append('<li ><div style="text-align: center;line-height: 30px;" >无符合条件数据</div></li>')
					        }
                    	}
                    	
                });	  
    }
    /**
     * 根据权限展示功能按钮
     * @param treeNode
     * @returns {string}
     */
    function formatHandle(treeNode) {
        var htmlStr = '';
        htmlStr += '<div><button  onclick="view(\'' + treeNode.id + '\')" >查看详细数据</button></div>';
        return htmlStr;
    }
    
    function view(id){
    	 for(var i=0;i<traceList.length;i++){
	        	traceList[i].pId=traceList[i].parentId;
	        	if(traceList[i].id==id){
	        		var last=JSON.stringify(traceList[i]); //将JSON对象转化为JSON字符
					$("#sLogTime").val(traceList[i].logTime);
					$("#projectName").val(traceList[i].projectName);
					$("#excutePoint").val(traceList[i].excutePoint);
					$("#standBy").val(traceList[i].standBy);
					$("#traceIdView").val(traceList[i].traceId);
					$("#projectId").val(traceList[i].projectId);
					$("#processId").val(traceList[i].processId);
					$("#executionTime").val(traceList[i].executionTime);
					$("#personalityData").val(traceList[i].personalityData);
					$("#parentId").val(traceList[i].parentId);
					$("#myId").val(traceList[i].myId);
					$("#entryParameter").html(traceList[i].entryParameter);
					$("#excuteResult").html(traceList[i].excuteResult);
					$("#errorMsg").html(traceList[i].errorMsg);
	        		$('#crontMonitorModal').modal('show');
	        		return;
	        	}
		 }
    }


    
  
  </script>
  
  
  
<script>
</script>
</html>