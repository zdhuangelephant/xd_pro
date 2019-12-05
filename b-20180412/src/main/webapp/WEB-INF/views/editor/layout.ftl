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
    <script src="${rc.contextPath}/resources/assets/js/ace.min.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/ace-elements.min.js"></script>
    <script src="${rc.contextPath}/resources/js/validform/validform.js"></script>
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
