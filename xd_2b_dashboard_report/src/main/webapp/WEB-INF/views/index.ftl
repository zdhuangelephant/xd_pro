<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include "/common/title.ftl"/>
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
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
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
        <span class="logo-mini">
        <img style="width:auto;height:30px;" src="${baseOP}/image/common/small-logo.png"></img>
        </span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
        <input type="hidden" value="${adminUser.role}" id="role"/>
        <input type="hidden" value="${adminUser.unitId}" id="unitId">
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
       		<h3>北京市高等教育自学考试学习综合评价平台</h3>
       	</div>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
            	<li class="dropdown notifications-menu">
                    <!-- Menu toggle button -->
                    <a href="#" id="sys_message" class="dropdown-toggle title-height" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        		系统消息
                    </a>
                    <ul class="dropdown-menu box-width">
                        <li class="header" id="sys_header">
                        	无系统未读消息
                        </li>
                        <li>
                            <!-- Inner Menu: contains the notifications -->
                            <ul class="menu">
                                <li id="sys_un_read">
                                   
                                </li>
                                <!-- end notification -->
                            </ul>
                        </li>
                        <li class="footer"><a href="javascript:;" onclick="">查看所有</a></li>
                    </ul>
                </li>
                <!-- /.messages-menu -->
                <!-- Notifications Menu -->
                <li class="dropdown notifications-menu">
                    <!-- Menu toggle button -->
                    <a href="#" id="alarm_message" class="dropdown-toggle title-height" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <#if (listUnreadAlarmRecord?? && listUnreadAlarmRecord.result?? && listUnreadAlarmRecord.result?size>0)>
                        	<span class="label label-warning">N+</span>
                        </#if>
                        		预警
                    </a>
                    <ul class="dropdown-menu box-width">
                        <li class="header" id="alarm_header">
                        	<#if (listUnreadAlarmRecord.result?? && listUnreadAlarmRecord.result?? && listUnreadAlarmRecord.result?size>0)>
                        	<!-- 有${listUnreadAlarmRecord.result?size}条报警未读消息 -->
                        	<#else>无报警未读消息
                        	</#if>
                        </li> 
                        <li>
                            <!-- Inner Menu: contains the notifications -->
                            <ul class="menu">
                                <li id="alarm_un_read">
                                    <!-- start notification -->
                                    <#if listUnreadAlarmRecord.result??>
                                    <#list listUnreadAlarmRecord.result as alarm>
                                    <!--<a href="javascript:volid(0);">-->
                         			<a href="javascript:;" onclick="rawDataList(${alarm.id},${alarm.triggerType});">
                                        <!--<i class="fa fa-users text-aqua"></i> 新用户注册!-->
                                        ${alarm.alarmTime} ${alarm.alarmLevel.desc}
                                    </a>
                                    </#list>
                                    </#if>
                                </li>
                                <!-- end notification -->
                            </ul>
                        </li>
                        <li class="footer"><a href="javascript:;" onclick="alarmList();">查看所有</a></li>
                    </ul>
                </li>
                
                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle title-height" data-toggle="dropdown">
                        <!-- The user image in the navbar-->
                        <i class="fa fa-gears"></i>设置
                    </a>
                    <ul class="dropdown-menu top-line" id="dropdown-index">
						<li><a href="javascript:;" onclick="addTabs({id:'10022',title: '修改账号信息',close: true,url: '/admin/reset_user_detail'});">
						<i class="ace-icon fa fa-user"></i>
						修改账号信息
						</a></li>
						<li><a href="javascript:;" onclick="addTabs({id:'10023',title: '修改密码',close: true,url: '/admin/reset_password'});">
						<i class="ace-icon fa fa-cog"></i>
						修改密码
						</a></li>
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
                <img src="${adminUser.unitPortrait}" class="img-circle" alt="用户头像">
                <!--<#if adminUser.role ==1>
                <img src="${baseOP}/image/common/self-taught-portrait.png" class="img-circle" alt="用户头像">
                <#elseif adminUser.role ==2>
                <img src="${baseOP}/image/common/chief-unit-portrait.png" class="img-circle" alt="用户头像">
                <#elseif adminUser.role ==3>
                <img src="${baseOP}/image/common/plilt-unit-portrait.png" class="img-circle" alt="用户头像">
                </#if>-->
            </div>
            <div class="pull-left info">
                <p>${adminUser.realName!'慕享'}</p>
                <!--<a href="#"><i class="fa fa-circle text-success"></i> 在线</a>-->
                ${adminUser.unitName!'慕享'}
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
        <ul class="sidebar-menu">
           
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
	<script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
  <script type="text/javascript">
  </script>
</body>
</html>
