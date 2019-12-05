<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>自动测试框架</title>

		<link href="${baseCssOP }/bootstrap.min.css" rel="stylesheet">
		<link href="${baseCssOP }/frame-main.css" rel="stylesheet">
		<link href="${baseCssOP }/plugins/morris.css" rel="stylesheet">
		<link href="${baseCssOP }/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		    
    
	    <script src="${baseJsOP }/jquery.js"></script>
		<script src="${baseJsOP }/jquery.form.js"></script>
		<script src="${baseJsOP }/bootstrap.min.js"></script>

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">自动测试框架</a>
            </div>
            <!-- Top Menu Items -->
          <ul class="nav navbar-right top-nav">
            <li class="dropdown">
              <a href="/admin/loginOut" target="_parent" ><i class="fa fa-user"></i> 退出 </a>
            </li>
          </ul>
        </nav>
        <div id="page-wrapper">
			<#include "/overview/index.ftl"/>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <script src="${baseJsOP }/index/frame-main.js"></script>
</body>
</html>
