<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <#include "/common/title.ftl"/>
    <meta name="keywords" content="supershopui框架" />
    <meta name="description" content="supershopui框架" />
    <link href="${baseOP}/content/ui/css/layout.css" rel="stylesheet" />
    <link href="${baseOP}/css/admin/style.css?1" rel="stylesheet" type="text/css">
    <script src="${baseOP}/js/admin/tab.js"></script>
</head>
<body>
    <div class="backgroung-img">
        <div class="login">
            <div class="title">北京市高等教育自学考试<br>学习综合评价平台</div>
            <div class="login-box" id="box">
                <p class="login-box-tab" id="tab-box">
                    <a href="javascript:volid(0);" id="btn4" role="4" class="active">超级管理员</a>
                </p>
                <form id="MANAGE_FORM" action="/login" method="post" autocomplete="off">
                    <div class="tab-cont" id="div4" style="display: block;">
                        <input type="text" placeholder="用户名" id="maUserName" datatype="*" nullmsg="请填写帐号信息" />
                        <input type="hidden" name="userName" id="mUserName">
                        <input type="password" placeholder="密码" name="password" datatype="*" nullmsg="请填写帐号密码" />
                    	<a href="javascript:;" onclick="submit(4);">超管 登陆</a>
                    </div>
                </form>
            </div>
            <div class="bottom-mes">版权所有：北京小逗网络科技有限公司</div>
        </div>
        <div class="popupDom">
            <div class="popup text-default"></div>
        </div>
    </div>
</body>
<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
<script src="${baseOP}/content/ui/global/jQuery/jquery.cookie.js"></script>
<script src="${baseOP}/js/common.js"></script>
<script src="${baseOP}/js/admin/login.js?13334${timeStamp}"></script>
<script type="text/javascript">
    /*动画（注册）*/
    $(document).ready(function (e) {
     	<#if fail=="true">
    		popup_msg("账号或者密码错误");
	 	</#if>
    });
</script>
</html>