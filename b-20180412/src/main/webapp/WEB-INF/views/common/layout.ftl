<#assign IMG_PATH="${rc.contextPath}/resources/images/" />
<#assign CSS_PATH="${rc.contextPath}/resources/css/" />
<#assign JS_PATH="${rc.contextPath}/resources/js/" />

<#include "/common/form.ftl">

<#--网站head-->
<#macro htmlHead title >
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>${title}</title>
    <meta name="description" content="Static &amp; Dynamic Tables"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/font-awesome.min.css"/>
    <!-- page specific plugin styles -->
    <!-- text fonts -->
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/ace-fonts.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/ace.min.css" id="main-ace-style"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/ace-part2.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/ace-ie.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/css/validform/validform.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/mycss/ace.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/supershopui.common.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/mycss/font-awesome.min.css"/>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="${rc.contextPath}/resources/assets/js/html5shiv.min.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/respond.min.js"></script>
    <![endif]-->
    <script src="${rc.contextPath}/resources/js/jquery-1.10.2.js"></script>
    <script src="${rc.contextPath}/resources/js/common-1.0.0.js"></script>
    <script src="${rc.contextPath}/resources/js/util.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/bootstrap.min.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/bootstrap-typeahead.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/ace.min.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/ace-elements.min.js"></script>
    <script src="${rc.contextPath}/resources/js/validform/validform_5.3.2_min.js"></script>
    <script src="${rc.contextPath}/resources/js/validform/validform_datatype.js"></script>
    <script src="${rc.contextPath}/resources/js/artdialog/artDialog.js?skin=blue"></script>
    <script src="${rc.contextPath}/resources/js/artdialog/plugins/iframeTools.js"></script>
    <#nested>
</head>
</#macro>

<#--网站body-->
<#macro htmlBody bodyclass scroll>
<body class="${bodyclass}" ${scroll}>
<#nested>
</body>
</html>
</#macro>

<#--分页-->
<#macro page totalCount pageNo totalPage url>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px">
    <div class="col-md-6 col-xs-6" style="padding-left: 0px;">
        <p  style="margin-top: 20px; margin-bottom: 20px; line-height: 1.5; padding-top: 6px; padding-bottom: 6px;">
            共<font color="red">${totalCount}</font>条记录，当前显示第&nbsp;<font color="red">${pageNo}</font>&nbsp;页,共&nbsp;<font color="red">${totalPage}&nbsp;</font>页
        </p>
    </div>
    <div class="col-md-6 col-xs-6" style="padding-right: 0px;">
        <nav class="pull-right">
            <ul class="pagination">
                
        		<!--上一页-->
                <li <#if pageNo==1> class="disabled"</#if>>
                    <a href="#" aria-label="Previous" <#if pageNo!=1>onclick="toPage(${pageNo?number-1})"</#if>  >
                        <span aria-hidden="true">上一页</span>
                    </a>
                </li>
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

                <!--省略号-->
                <#if (begin>1) >
                <li><a href="javascript:;">...</a>
                </#if>

                <!--显示页码-->
                <#list begin..end as x>
                    <li class="<#if x==pageNo>active</#if>"><a href="javascript:;" onclick="toPage(${x})" >${x}</a></li>
                </#list>

                <!--省略号-->
                <#if (end<totalPage?number) >
                <li><a href="javascript:;">...</a>
                </#if>

                <!--下一页-->
                <li <#if pageNo==totalPage> class="disabled"</#if>>
                    <a href="#" aria-label="Next" <#if pageNo!=totalPage> onclick="toPage(${pageNo?number+1})" </#if> >
                        <span aria-hidden="true">下一页</span>
                    </a>
                </li>
              <li>
                      <a href="javascript:;"  aria-label="Previous"   >
                      <span aria-hidden="true">到</span>
                      <input type="text"  style="width:30px;height:20px" id="JumpToPage" >
                      	<span aria-hidden="true">页</span>
                      	<a href="javascript:;"  aria-label="Previous" onclick="subPage(${totalPage})" >确定</a>  
                      </a>                                                   
                </li>

        
            </ul>
        </nav>
    </div>
</div>
<script>
	function subPage(totalPage){
		var num=$("#JumpToPage").val();
		if(num==null||num>totalPage){
			alert("页码输入不正确");
			return
		}
		toPage(num);
	}
</script>
</#macro>
