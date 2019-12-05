<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="no-skin objbody" scroll="scroll='no'" >
<style>
    .objbody{overflow:hidden}
    .f{color:red}
    #leftNavBar {
    	overflow-y: auto;
    }
   	.sidebar.menu-min .nav-list>li>a>.menu-text {
   		z-index: 9999;
   	}
</style>
<!--头部-->
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>
    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="#" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    小逗网络
                </small>
            </a>
            <!-- /section:basics/navbar.layout.brand -->
            <!-- #section:basics/navbar.toggle -->
            <!-- /section:basics/navbar.toggle -->
        </div>
        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo"
                             src="${rc.contextPath}/resources/assets/avatars/user.jpg"
                             alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎,</small><#if admin.realName?? >${admin.realName}<#else>${admin.userName}</#if>
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="/admin/changePassword" target="myframe">
                                <i class="ace-icon fa fa-cog"></i>修改密码
                            </a>
                        </li>
                        <li>
                            <a href="/admin/editInfo" target="myframe">
                                <i class="ace-icon fa fa-user"></i>个人信息
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="/admin/loginOut">
                                <i class="ace-icon fa fa-power-off"></i>退出
                            </a>
                        </li>
                    </ul>
                </li>
                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>
        <!-- /section:basics/navbar.dropdown -->
    </div>
    <!-- /.navbar-container -->
</div>
<!-- /section:basics/navbar.layout -->
<!--头部结束-->

<!--主体开始-->
<div class="main-container" id="main-container">
<script type="text/javascript">
    try {
        ace.settings.check('main-container', 'fixed')
    } catch (e) {
    }
</script>

<!--左侧导航开始-->
<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar responsive">
    <script type="text/javascript">
        try {
            ace.settings.check('sidebar', 'fixed')
        } catch (e) {
        }
    </script>
        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <a class="btn btn-success" href="/user/list" target="myframe">
                <i class="ace-icon fa fa-signal"></i>
            </a>
            <a class="btn btn-info" href="/user/list" target="myframe">
                <i class="ace-icon fa fa-pencil"></i>
            </a>
            <!-- #section:basics/sidebar.layout.shortcuts -->
            <a class="btn btn-warning" href="/user/list" target="myframe">
                <i class="ace-icon fa fa-users"></i>
            </a>
            <a class="btn btn-danger" href="/user/list" target="myframe">
                <i class="ace-icon fa fa-cogs"></i>
            </a>
            <!-- /section:basics/sidebar.layout.shortcuts -->
        </div>
        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>
            <span class="btn btn-info"></span>
            <span class="btn btn-warning"></span>
            <span class="btn btn-danger"></span>
        </div>
    </div>
    <!-- /.sidebar-shortcuts -->
    <ul class="nav nav-list" id="leftNavBar">
        <#list privileges as privilege>
        <li class="">
            <a target="myframe" onclick="menuSelect(this, ['${privilege.adminPrivilege.name}'])" href="javascript:void(0)" toUrl="<#if privilege.nodeList?size == 0 >${privilege.adminPrivilege.url}<#else>#</#if>" class="<#if privilege.nodeList?size gt 0 >dropdown-toggle</#if>">
                <i class="menu-icon fa fa-desktop"></i>
                <span class="menu-text"> ${privilege.adminPrivilege.name} </span>
                <#if privilege.nodeList?size gt 0 >
                    <b class="arrow fa fa-angle-down"></b>
                </#if>
            </a>
            <b class="arrow"></b>
            <#if privilege.nodeList?size gt 0 >
            <ul class="submenu">
                <#list privilege.nodeList as subPrivilege>
                    <li class="">
                        <a target="myframe" onclick="menuSelect(this, ['${privilege.adminPrivilege.name}', '${subPrivilege.adminPrivilege.name}'])" href="javascript:void(0)" toUrl="<#if subPrivilege.nodeList?size == 0 >${subPrivilege.adminPrivilege.url}<#else>#</#if>" class="<#if subPrivilege.nodeList?size gt 0 >dropdown-toggle</#if>">
                            <i class="menu-icon fa fa-caret-right"></i>
                            ${subPrivilege.adminPrivilege.name}
                            <#if subPrivilege.nodeList?size gt 0 >
                            <b class="arrow fa fa-angle-down"></b>
                            </#if>
                        </a>
                        <b class="arrow"></b>

                    <#if subPrivilege.nodeList?size gt 0 >
                    <ul class="submenu">
                        <#list subPrivilege.nodeList as subSubPrivilege>
                        <li class="">
                            <a target="myframe" onclick="menuSelect(this, ['${privilege.adminPrivilege.name}', '${subSubPrivilege.adminPrivilege.name}'])" href="javascript:void(0)" toUrl="<#if subSubPrivilege.nodeList?size == 0 >${subSubPrivilege.adminPrivilege.url}<#else>#</#if>" class="<#if subSubPrivilege.nodeList?size gt 0 >dropdown-toggle</#if>">
                                <i class="menu-icon fa fa-caret-right"></i>
                            ${subSubPrivilege.adminPrivilege.name}
                                <#if subSubPrivilege.nodeList?size gt 0 >
                                    <b class="arrow fa fa-angle-down"></b>
                                </#if>
                            </a>
                            <b class="arrow"></b>

                            <#if subSubPrivilege.nodeList?size gt 0 >
                            <ul class="submenu">
                                <#list subSubPrivilege.nodeList as subSubSubPrivilege>
                                    <li class="">
                                        <a target="myframe" onclick="menuSelect(this, ['${privilege.adminPrivilege.name}', '${subSubPrivilege.adminPrivilege.name}', '${subSubSubPrivilege.adminPrivilege.name}'])" href="javascript:void(0)" toUrl="${subSubSubPrivilege.adminPrivilege.url}">
                                            <i class="menu-icon fa fa-caret-right"></i>
                                            ${subSubSubPrivilege.adminPrivilege.name}

                                        </a>
                                        <b class="arrow"></b>
                                    </li>
                                </#list>
                            </ul>
                            </#if>

                        </li>
                        </#list>
                    </ul>
                    </#if>


                    </li>
                </#list>
                
            </ul>
            </#if>
        </li>
        </#list>
    </ul>
    <!-- /.nav-list -->
   <div class="sidebar-collapse" id="sidebar-collapse">
						<i id="change" onclick="changeMenu()" class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
</div>
<!-- /section:basics/sidebar -->
<!--左侧导航结束-->

<div class="main-content">
    <!--面包屑导航开始-->
    <!-- #section:basics/content.breadcrumbs -->
    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {
            }
        </script>
        <ul class="breadcrumb">
            <i class="ace-icon fa fa-home home-icon"></i>
            <li class="active" id="child">首页</li>
        </ul>
                <a style="float:right;margin-right:20px;" onclick="refresh()"><i class="ace-icon fa fa-refresh">刷新本页</i></a>
        <!-- /.breadcrumb -->
    </div>
    <!-- /section:basics/content.breadcrumbs -->
    <!--面包屑导航结束-->

    <!--内容开始-->
    <div class="page-content" style="padding:0px;">
        <div style="  position: relative;overflow: hidden;">
            <iframe id="myframe" name="myframe" frameborder="false" allowtransparency="true" width="100%"  scrolling="auto" style="border: none;" src="/admin/showInfo"></iframe>
        </div>
        <!-- /.page-content-area -->
    </div>
    <!-- /.page-content -->
    <!--内容结束-->

</div>
<!-- /.main-content -->

</div><!-- /.main-container -->

<script>
    if(!Array.prototype.map)
        Array.prototype.map = function(fn,scope) {
            var result = [],ri = 0;
            for (var i = 0,n = this.length; i < n; i++){
                if(i in this){
                    result[ri++]  = fn.call(scope ,this[i],i,this);
                }
            }
            return result;
        };
    var getWindowSize = function(){
        return ["Height","Width"].map(function(name){
            return window["inner"+name] ||
                    document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ]
        });
    }
    window.onload = function (){
        if(!+"\v1" && !document.querySelector) { // for IE6 IE7
            document.body.onresize = resize;
        } else {
            window.onresize = resize;
        }
        function resize() {
            wSize();
            return false;
        }
    }
    function wSize(){
        //这是一字符串
        var str=getWindowSize();
        var strs= new Array(); //定义一数组
        strs=str.toString().split(","); //字符分割
        var heights = strs[0]-86,Body = $('body');
        $("#main-container").height(strs[0]-45);
        $("#myframe").height(strs[0]-95);
    }
    wSize();

</script>

<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
    function menuSelect(obj, args){
        if($(obj) && $(obj).attr("toUrl") && $(obj).attr("toUrl")!="#")
        	$("#"+$(obj).attr("target")).attr("src",$(obj).attr("toUrl"));
        if(args && typeof(args) != 'undefined' && args.length > 0){
            var html = '<i class="ace-icon fa fa-home home-icon"></i><li><a href="javascript:window.location.reload();">首页</a></li>';
        	for(var i = 0; i < args.length; i++){
        		if(i == args.length - 1)
        			html += '<li><a class="active" href="#">'+args[i]+'</a></li>';
        		else
        			html += '<li><a href="#">'+args[i]+'</a></li>';
        	}
	        $(".breadcrumb").html(html);
        }
        }

    function changeMenu(){ 
        if($("#change").hasClass("icon-double-angle-right")){
        	 $("#sidebar").removeClass("menu-min"); 
        	 $("#change").removeClass("icon-double-angle-right");
    		$("#change").addClass("icon-double-angle-left");
        }else{
    		$("#sidebar").addClass("menu-min");	
    		$("#change").removeClass("icon-double-angle-left");
    		$("#change").addClass("icon-double-angle-right");
    	}
    }
    function refresh(){
    	  document.getElementById('myframe').contentWindow.location.reload(true);
    	  
    }
    $("ul .submenu li").click(function(){
   		if($(this).hasClass("hsub")){
   			return;
   		}else{
   			$("ul .submenu li").removeClass("active");
	    	$(this).addClass("active");
   		}
    });
    var navBarHeight = $(window).height() - 117;
    $('#leftNavBar').height(navBarHeight);
    $(window).resize(function () {
    	var navBarHeight = $(window).height() - 117;
    	$('#leftNavBar').height(navBarHeight);
    })
</script>

</@htmlBody>
