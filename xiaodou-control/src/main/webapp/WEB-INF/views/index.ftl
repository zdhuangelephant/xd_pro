<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include "/common/title.ftl"/>
    <script src="/resources/js/js/jquery.js"></script>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css?12">
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.css">
    <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${baseOP}/css/dashboard.css?3">
    <style type="text/css">
         html {
            overflow: hidden;
        }
    </style>
</head>
<script>
		$.post("/serverGroupType/indexList", {},
                    function(data){
                    	var u=JSON.parse(data);
                    	var groupTypeArray=u.groupTypeList;
                    	var superAdmin=u.superAdmin;
                    	$("#demo").append(" <li class='treeview' data-level='0'><a class='nav-link' onclick=addTabs({id:'20320',title:'主机管理',close:true,url:'/baseNode/list'})><i class='fa fa-area-chart'></i><span class=title menu-text>主机管理</span><span></span></a></li>");
                    	if(superAdmin=="true"){
                    	    $("#demo").append("<li class='treeview' data-level='0'><a class='nav-link'><i class='fa fa-users'></i><span class='title menu-text'>基础功能</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a><ul class='treeview-menu' style='display: none;'><li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'20327',title:'服务组类型管理',close:true,url:'serverGroupType/list'})><i class='fa fa-list-alt'></i><span class='title menu-text'>服务组类型管理</span><span></span></a></li><li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'20328',title:'基础服务管理',close:true,url:'baseServer/list'})><i class='fa fa-list-alt'></i><span class='title menu-text'>基础服务管理</span><span></span></a></li></ul></li>");
                    		$("#demo").append("<li class='treeview' data-level='0'><a class='nav-link'><i class='fa fa-users'></i><span class='title menu-text'>权限管理</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a><ul class='treeview-menu' style='display: none;'><li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'20330',title:'用户管理',close:true,url:'admin/list'})><i class='fa fa-list-alt'></i><span class='title menu-text'>用户管理</span><span></span></a></li><li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'20331',title:'角色管理',close:true,url:'role/list'})><i class='fa fa-list-alt'></i><span class='title menu-text'>角色管理</span><span></span></a></li></ul></li>");
        					$("#demo").append("<li class='treeview' data-level='0'><a class='nav-link' onclick=addTabs({id:'20332',title:'服务组管理',close:true,url:'serverGroup/list'})><i class='fa fa-users'></i><span class='title menu-text'>服务组管理</span><span></span></a></li></ul></li>");
                    		}
                        $("#demo").append("<li class='treeview' data-level='0'><a class='nav-link'><i class='fa fa-users'></i><span class='title menu-text'>拓展功能</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a><ul class='treeview-menu' style='display: none;'><li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'20340',title:'日志收集管理',close:true,url:'logMonitor/list'})><i class='fa fa-list-alt'></i><span class='title menu-text'>日志收集管理</span><span></span></a></li></ul></li>");
	                    for(var i=0;i<groupTypeArray.length;i++){
	                            var ulHtml="<li class='treeview' data-level='0'><a class='nav-link'><i class='fa fa-users'></i><span class='title menu-text'>"+groupTypeArray[i].name+"</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a><ul class='treeview-menu' style='display: none;'>";
								for(var j=0;j<groupTypeArray[i].serverGroupList.length;j++){
									ulHtml+="<li class='treeview' data-level='1'><a style='padding-left:20px' class='nav-link' onclick=addTabs({id:'"+groupTypeArray[i].serverGroupList[j].groupId+"',title:'"+groupTypeArray[i].serverGroupList[j].groupName+"',close:true,url:'serverGroupDetail/detail?groupId="+groupTypeArray[i].serverGroupList[j].groupId+"&groupName="+groupTypeArray[i].serverGroupList[j].groupName+"'})><i class='fa fa-list-alt'></i><span class='title menu-text'>"+groupTypeArray[i].serverGroupList[j].groupName+"</span><span></span></a></li>";
								}
								ulHtml+="</ul></li>";
								$("#demo").append(ulHtml);
						}             
                    });	 
</script>

<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-black sidebar-mini fixed">
<div class="wrapper">
<!-- Main Header -->
<header class="main-header">
	<input type="hidden" value='${menuTree}' id="menuTree"/>
    <!-- Logo -->
    <a href="" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <!-- logo for regular state and mobile devices -->
        <span class="logo-mini">
        <img style="width:auto;height:30px;" src="${baseOP}/image/common/small-logo.png"></img>
        </span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
		<img style="width:auto;height:40px;" src="${baseOP}/image/common/big-logo.png"></img>   
		</span> 
    </a>
   
    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">切换导航</span>
        </a>
       	<div class="collapse navbar-collapse pull-left title-style">
       		<h3>AOS</h3>
       	</div>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
            	<li class="dropdown notifications-menu"> 
                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle title-height" data-toggle="dropdown">
                        <!-- The user image in the navbar-->
                        <i class="fa fa-gears"></i>设置
                    </a>
                    <ul class="dropdown-menu top-line" id="dropdown-index">
						<li><a href="/admin/loginOut"> 
						<i class="ace-icon fa fa-power-off"></i>
						退出登陆
						</a></li>
                    </ul>
                </li>
                <!-- Control Sidebar Toggle Button 
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                </li>
                -->
            </ul>
        </div>
    </nav>
</header>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
               <h3 style="color:#FFFFFF">AOS</h3>
            </div> 
        </div>
        <!-- search form (Optional) 
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                            <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
            </div>
        </form>
        -->
        <!-- /.search form -->
        <!-- Sidebar Menu -->
      <ul id="demo" class="sidebar-menu">
       
       
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper"  id="content-wrapper">
    <div class="content-tabs">
        <button class="roll-nav roll-left tabLeft" onclick="scrollTabLeft()">
            <i class="fa fa-backward"></i>
        </button>
        <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">
            <div class="page-tabs-content" style="margin-left: 0px;">

            </div>
        </nav>
        <button class="roll-nav roll-right tabRight" onclick="scrollTabRight()">
            <i class="fa fa-forward" style="margin-left: 3px;"></i>
        </button>
        <div class="btn-group roll-nav roll-right">
            <button class="dropdown tabClose" data-toggle="dropdown">
                页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
            </button>
            <ul class="dropdown-menu dropdown-menu-right" style="min-width: 128px;">
                <li><a class="tabReload" href="javascript:refreshTab();">刷新当前</a></li>
                <li><a class="tabCloseCurrent" href="javascript:closeCurrentTab();">关闭当前</a></li>
                <li><a class="tabCloseAll" href="javascript:closeOtherTabs(true);">全部关闭</a></li>
                <li><a class="tabCloseOther" href="javascript:closeOtherTabs();">除此之外全部关闭</a></li>
            </ul>
        </div>
        <button class="roll-nav roll-right fullscreen" ><i class="fa fa-arrows-alt"></i></button>
    </div>
    <div class="content-iframe " style="background-color: #ffffff; ">
        <div class="tab-content " id="tab-content">

        </div>
    </div>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
<!-- Main Footer -->
<footer class="main-footer">
    <!-- To the right -->
    <div class="pull-right hidden-xs">
       	小逗网络
    </div>
    <!-- Default to the left -->
	    版权所有 ©XIAODOU NETWORK TECHNOLOGY 
	 <!--2015-2025 &nbsp;&nbsp;&nbsp;&nbsp; 
	    京ICP备16024545号-1-->
</footer>
<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
        <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
        <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <!-- Home tab content -->
        <div class="tab-pane" id="control-sidebar-home-tab">
            <h3 class="control-sidebar-heading">最近活动</h3>
            <ul class="control-sidebar-menu">
                <li>
                    <a href="javascript:void(0)">
                        <i class="menu-icon fa fa-birthday-cake bg-red"></i>
                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
                            <p>Will be 23 on April 24th</p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <i class="menu-icon fa fa-user bg-yellow"></i>
                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>
                            <p>New phone +1(800)555-1234</p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>
                            <p>nora@example.com</p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <i class="menu-icon fa fa-file-code-o bg-green"></i>
                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>
                            <p>Execution time 5 seconds</p>
                        </div>
                    </a>
                </li>
            </ul>
            <!-- /.control-sidebar-menu -->
            <h3 class="control-sidebar-heading">Tasks Progress</h3>
            <ul class="control-sidebar-menu">
                <li>
                    <a href="javascript:void(0)">
                        <h4 class="control-sidebar-subheading">
                            Custom Template Design
                            <span class="label label-danger pull-right">70%</span>
                        </h4>
                        <div class="progress progress-xxs">
                            <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <h4 class="control-sidebar-subheading">
                            Update Resume
                            <span class="label label-success pull-right">95%</span>
                        </h4>
                        <div class="progress progress-xxs">
                            <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <h4 class="control-sidebar-subheading">
                            Laravel Integration
                            <span class="label label-warning pull-right">50%</span>
                        </h4>
                        <div class="progress progress-xxs">
                            <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <h4 class="control-sidebar-subheading">
                            Back End Framework
                            <span class="label label-primary pull-right">68%</span>
                        </h4>
                        <div class="progress progress-xxs">
                            <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                        </div>
                    </a>
                </li>
            </ul>
            <!-- /.control-sidebar-menu -->
        </div>
        <!-- /.tab-pane -->
        <!-- Stats tab content -->
        <div class="tab-pane" id="control-sidebar-stats-tab">状态面板设置</div>
        <!-- /.tab-pane -->
        <!-- Settings tab content -->
        <div class="tab-pane" id="control-sidebar-settings-tab">
            <form method="post">
                <h3 class="control-sidebar-heading">常规设置</h3>
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Report panel usage
                        <input type="checkbox" class="pull-right" checked>
                    </label>
                    <p>
                        Some information about this general settings option
                    </p>
                </div>
                <!-- /.form-group -->
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Allow mail redirect
                        <input type="checkbox" class="pull-right" checked>
                    </label>
                    <p>
                        Other sets of options are available
                    </p>
                </div>
                <!-- /.form-group -->
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Expose author name in posts
                        <input type="checkbox" class="pull-right" checked>
                    </label>
                    <p>
                        Allow the user to show his name in blog posts
                    </p>
                </div>
                <!-- /.form-group -->
                <h3 class="control-sidebar-heading">Chat Settings</h3>
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Show me as online
                        <input type="checkbox" class="pull-right" checked>
                    </label>
                </div>
                <!-- /.form-group -->
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Turn off notifications
                        <input type="checkbox" class="pull-right">
                    </label>
                </div>
                <!-- /.form-group -->
                <div class="form-group">
                    <label class="control-sidebar-subheading">
                        Delete chat history
                        <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                    </label>
                </div>
                <!-- /.form-group -->
            </form>
        </div>
        <!-- /.tab-pane -->
    </div>
</aside>
<!-- /.control-sidebar -->
<!-- Add the sidebar's background. This div must be placed
immediately after the control sidebar -->
<div class="control-sidebar-bg"></div>
<input type="hidden" value="${goEasySubscribekey}" id="goEasySubscribekey">
</div>
    <!-- ./wrapper -->
    <!-- REQUIRED JS SCRIPTS -->
    <!-- jQuery 2.2.3 -->
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
	<script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
	<script src="${baseOP}/js/index.js?13${timeStamp}"></script>
	<script type="text/javascript" src="${baseOP}/js/goeasy.js"></script>
  <script type="text/javascript">
  </script>
</body>
</html>
