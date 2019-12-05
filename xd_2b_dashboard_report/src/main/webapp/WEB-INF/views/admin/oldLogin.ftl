<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <#include "/common/title.ftl"/>
    <meta name="keywords" content="supershopui框架" />
    <meta name="description" content="supershopui框架" />
    <link href="${baseOP}/content/ui/css/layout.css" rel="stylesheet" />
    <link href="${baseOP}/content/ui/css/login.css" rel="stylesheet"/>
    <style>
        .ibar {
            display: none;
        }
    </style>
</head>

<body class="login-bg">
    <div class="main ">
        <!--登录-->
        <div class="login-dom login-max">
            <div class="logo text-center">
                <a href="#">
                    <img src="${baseOP}/content/ui/img/logo.png" width="180px" height="180px" />
                </a>
            </div>
            <div class="login container " id="login">
                <p class="text-big text-center logo-color">
                    	逗是一种态度，认真是我们的习惯
                </p>

                <p class="text-center margin-small-top logo-color text-small">
                  		慕享云测评
                </p>
                <form class="login-form" action="/j_spring_security_check" method="post" autocomplete="off">
                    <div class="login-box border text-small" id="box">
                        <div class="name border-bottom">
                            <input type="text" placeholder="账号" id="userName" name="userName" datatype="*" nullmsg="请填写帐号信息" />
                        </div>
                        <div class="pwd">
                            <input type="password" placeholder="密码" datatype="*" id="password" name="password" nullmsg="请填写帐号密码" />
                        </div>
                    </div>
                    <input type="submit" class="btn text-center login-btn" value="立即登录" />
                </form>
                <div class="forget">
                    <a href="#" class="forget-pwd text-small fl"> 忘记登录密码？</a>
                    <a href="#" class="forget-new text-small fr" id="forget-new">注册账号</a>
                </div>
            </div>
        </div>
        <div class="footer text-center text-small ie">
            Copyright 2015-2025 版权所有 ©XIAODOU NETWORK TECHNOLOGY  2015-2025      
            <a href="#" target="_blank">京ICP备16024545号-1</a>
            <span class="margin-left margin-right">|</span>
            <script src="#" language="JavaScript"></script>
        </div>
        <div class="popupDom">
            <div class="popup text-default">
            </div>
        </div>
    </div>
</body>

<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
<script type="text/javascript">
    function popup_msg(msg) {
        $(".popup").html("" + msg + "");
        $(".popupDom").animate({
            "top": "0px"
        }, 400);
        setTimeout(function () {
            $(".popupDom").animate({
                "top": "-40px"
            }, 400);
        }, 2000);
    }

    /*动画（注册）*/
    $(document).ready(function (e) {
     	<#if fail=="true">
    		popup_msg("账号或者密码错误");
	 	</#if>
    });
    
    
    
    
     $(function(){
    	if(notEmpty($("iframe"))){
    		parent.location.reload();
    	}
    });
    window.onload=function(){
	  if (location.href.indexOf("?xyz=")<0){
		 location.href=location.href+"?xyz="+Math.random();
		}
	}
</script>
</html>