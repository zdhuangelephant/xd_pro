<#include "/common/style.ftl">

<#--分页-->
<#macro page totalCount pageNo totalPage>
<div class="container-fluid" style="padding-left: 0px; padding-right: 0px">
	<div class="row-fluid">
	    <div class="span6">
	        <div class="dataTables_info" id="sample_2_info">Showing 1 to 10 of ${totalCount} entries</div>
	    </div>
	    <div class="span6">
	        <div class="dataTables_paginate paging_bootstrap pagination">
	            <ul>
	                <li class="prev <#if pageNo==1>disabled</#if>"><a href="#" <#if pageNo!=1>onclick="toPage(${pageNo?number-1})"</#if>>← <span class="hidden-480">Previous</span></a></li>
	                <!--开始序号-->
	                <#assign begin=pageNo?number-3 />
	                <#if (begin <= 0) >
	                    <#assign begin=1 />
	                </#if>
	
	                <!--结束序号-->
	                <#assign end=pageNo?number+3 />
	                <#if (end>=totalPage?number) >
	                    <#assign end=totalPage?number />
	                </#if>
	
	                <!--显示页码-->
	                <#list begin..end as x>
	                    <li class="<#if x==pageNo>active</#if>"><a href="javascript:;" onclick="toPage(${x})" >${x}</a></li>
	                </#list>
	                
	                <li class="next <#if pageNo==totalPage>disabled</#if>"><a href="#"  <#if pageNo!=totalPage> onclick="toPage(${pageNo?number+1})" </#if>><span class="hidden-480">Next</span> → </a></li>
	            </ul>
	        </div>
	    </div>
	</div>
</div>
</#macro>

<!DOCTYPE html>
<!-- BEGIN HEAD -->

<head>

	<meta charset="utf-8" />

	<title>Metronic | Admin Dashboard Template</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />


</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN HEADER -->

	<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->

		<div class="navbar-inner">

			<div class="container-fluid">

				<!-- BEGIN LOGO -->

				<a class="brand" href="index.html">

				<img src="/media/image/logo.png" alt="logo"/>

				</a>

				<!-- END LOGO -->


				<!-- BEGIN TOP NAVIGATION MENU -->              

				<ul class="nav pull-right">	
		            <li class="dropdown">
		                <a href="/admin/loginOut" target="_parent" ><i class="fa fa-user"></i> 退出 </a>               
		            </li>
		        </ul>	

				</ul>

				<!-- END TOP NAVIGATION MENU --> 

			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>			
				<li class="start active ">
					<a href="/">
					<i class="icon-home"></i> 
					<span class="title">Dashboard</span>
					<span class="selected"></span>
					</a>
				</li>
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">JMSG</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
						<li class="open">
							<a href="javascript:;">
							配置中心
							<span class="arrow open"></span>
							</a>
							<ul class="sub-menu" style="display: block;">
								<li><a href="/jmsg/serverNodeList">节点配置</a></li>
								<li><a href="/jmsg/queueList">quene配置</a></li>	
							    <li><a href="/jmsg/configList">config配置</a></li>							
							</ul>
						</li>
				     	<li>
							<a href="/jmsg/logList">
							日志信息</a>
						</li>
					</ul>
				</li>
				
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">日志收集</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
				     	<li><a href="/log/project_excution_list">
							监控点列表</a></li>
						<li><a href="/log/project_error_excution_list">
							报错监控点列表</a></li>
						<li><a href="/log/log_list">
							监控日志列表</a></li>
					</ul>
				</li>
				
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">定时任务</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
				     	<li>
							<a href="/crontab/list">
							任务列表</a>
						</li>
					</ul>
				</li>
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">监控管理</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
				     	<li>
							<a href="/cront_monitor/list">
							定时监控列表</a>
						</li>
					</ul>
				</li>
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">报警管理</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
				     	<li><a href="/dashboard/alarmpolicy/list">
							策略中心</a></li>
						<li><a href="/dashboard/alarm_event/list">
							事件中心</a></li>
					</ul>
				</li>
				
				<li class="open">
					<a class="" href="javascript:;">
					<i class="icon-sitemap"></i> 
					<span class="title">业务流</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" style="display: block;">
				     	<li><a href="/trace/index">
							业务流查询</a></li>
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
	</div>
	<!-- END CONTAINER -->
<!-- END BODY -->

</html>