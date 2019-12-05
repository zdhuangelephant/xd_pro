<#include "/common/style.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<link href="${rc.contextPath}/resources/css/fileCss/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
 <input type="hidden" id="groupServiceTypeName" value="${groupServiceTypeName}">
<input id="address" type="hidden" value="${rc.contextPath}/resources/downFile/"/>
	<!-- BEGIN CONTAINER -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->  
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							   ${groupName}
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								   ${groupServiceTypeName}${type}
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>

				<!-- END PAGE HEADER-->
				
				
				<!-- BEGIN PAGE CONTENT-->

                <!-- Page Heading -->
               <#if type==1>                     
                    <#include "/serverGroup/serverApps/server.ftl">
                <#elseif type==2>
                    <#include "/serverGroup/serverApps/middleServer.ftl">
               </#if>
              <#include "/serverGroup/serverApps/hostList.ftl">
              <#include "/serverGroup/serverApps/modal.ftl">		
            </div>
            <!-- /.container-fluid -->

        </div>
			<!-- END PAGE CONTAINER-->

<!-- END BODY -->
</body> 

<script>

</script>
<script src="${rc.contextPath}/resources/js/fileJs/fileinput.js" type="text/javascript"></script>
<script src="${rc.contextPath}/resources/js/fileJs/fileinput_locale_zh.js" type="text/javascript"></script>
<html>