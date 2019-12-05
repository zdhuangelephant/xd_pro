<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
  //String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			//request.setAttribute("error", error);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>小逗网络数据库管理系统</title>
<meta name="Keywords" content="数据库管理系统">
<meta name="Description" content="数据库管理系统">
<script src="${ctx}/static/plugins/easyui/jquery/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/bglogin.css" />
<link rel="icon" href="${ctx}/favicon.ico" mce_href="${ctx}/favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="${ctx}/favicon.ico"
	mce_href="${ctx}/favicon.ico" type="image/x-icon">
<script>
	var captcha;
	function refreshCaptcha(){  
	   //document.getElementById("img_captcha").src="${ctx}/static/images/kaptcha.jpg?t=" + Math.random();  
		document.getElementById("img_captcha").src="${ctx}/static/images/securityCode.jpg?t=" + Math.random();  
	}
</script>
</head>
<body>
	<div>
		<form id="loginForm" action="${ctx}/work-platform/loginVaildate"
			method="post">
			<div class="login_top">
				<div class="login_title">&nbsp; &nbsp;小逗网络数据库管理系统</div>
			</div>
			<div style="float:left;width:100%;">
				<div class="login_main">
					<div class="login_main_top"></div>
					<div class="login_main_errortip">&nbsp; ${message}</div>
					<div class="login_main_ln">
						<input type="text" id="username" name="username" />
					</div>
					<div class="login_main_pw">
						<input type="password" id="password" name="password" />
					</div>
					<div class="login_main_remb">
						<input id="rm" name="rememberMe" type="hidden" />
						<!-- <label for="rm"><span>记住我</span></label> -->
					</div>


					<div class="login_main_submit">
						<input type="submit" value="" />


					</div>
				</div>
			</div>
		</form>
	</div>

</body>
</html>
