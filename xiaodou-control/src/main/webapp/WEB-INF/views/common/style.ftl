<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->

<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

	<meta charset="utf-8" />

	<title>Metronic | Admin Dashboard Template</title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	 <!-- Bootstrap Core CSS -->
    <link href="${rc.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

	<link href="${rc.contextPath}/media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/style-metro.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/style.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/style-responsive.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>

	<link href="${rc.contextPath}/media/css/uniform.default.css" rel="stylesheet" type="text/css"/>

	<!-- END GLOBAL MANDATORY STYLES -->

	<!-- BEGIN PAGE LEVEL STYLES --> 

	<link href="${rc.contextPath}/media/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/daterangepicker.css" rel="stylesheet" type="text/css" />

	<link href="${rc.contextPath}/media/css/fullcalendar.css" rel="stylesheet" type="text/css"/>

	<link href="${rc.contextPath}/media/css/jqvmap.css" rel="stylesheet" type="text/css" media="screen"/>

	<link href="${rc.contextPath}/media/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>

	<link href="${rc.contextPath}/media/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>

	<link href="${rc.contextPath}/media/css/uniform.default.css" rel="stylesheet" type="text/css"/>

	<link rel="stylesheet" type="text/css" href="${rc.contextPath}/media/css/select2_metro.css" />

	<link rel="stylesheet" href="${rc.contextPath}/media/css/DT_bootstrap.css" />





	<style type="text/css">
	.table th, .table td { 
	text-align: center;
	vertical-align: middle!important;
	 }
	</style>

	
	<!-- END PAGE LEVEL STYLES -->

	<link rel="shortcut icon" href="${rc.contextPath}/media/image/favicon.ico" />


	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

	<!-- BEGIN CORE PLUGINS -->

	<script src="${rc.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>

	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

	<script src="${rc.contextPath}/media/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>      

	<script src="${rc.contextPath}/media/js/bootstrap.min.js" type="text/javascript"></script>

	<!--[if lt IE 9]>

	<script src="media/js/excanvas.min.js"></script>

	<script src="media/js/respond.min.js"></script>  

	<![endif]-->   

	<script src="${rc.contextPath}/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.blockui.min.js" type="text/javascript"></script>  

	<script src="${rc.contextPath}/media/js/jquery.cookie.min.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.uniform.min.js" type="text/javascript" ></script>

	<!-- END CORE PLUGINS -->

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script src="${rc.contextPath}/media/js/jquery.vmap.js" type="text/javascript"></script>   

	<script src="${rc.contextPath}/media/js/jquery.vmap.russia.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.vmap.world.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.vmap.europe.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.vmap.germany.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.vmap.usa.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.vmap.sampledata.js" type="text/javascript"></script>  

	<script src="${rc.contextPath}/media/js/jquery.flot.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.flot.resize.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.pulsate.min.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/date.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/daterangepicker.js" type="text/javascript"></script>     

	<script src="${rc.contextPath}/media/js/jquery.gritter.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/fullcalendar.min.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.easy-pie-chart.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/jquery.sparkline.min.js" type="text/javascript"></script>  
	
	<script src="${rc.contextPath}/media/js/highcharts.js" type="text/javascript"></script> 
	
	<script src="${rc.contextPath}/media/js/exporting.js" type="text/javascript"></script> 

	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->

	<script src="${rc.contextPath}/media/js/app.js" type="text/javascript"></script>

	<script src="${rc.contextPath}/media/js/index.js" type="text/javascript"></script>        


	<script type="text/javascript" src="${rc.contextPath}/media/js/select2.min.js"></script>

	<script type="text/javascript" src="${rc.contextPath}/media/js/jquery.dataTables.js"></script>

	<script type="text/javascript" src="${rc.contextPath}/media/js/DT_bootstrap.js"></script>

	<script src="${rc.contextPath}/media/js/table-advanced.js"></script>     
	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->

    <script src="${rc.contextPath}/resources/content/min/js/supershopui.common.js?1"></script>

	<!-- END JAVASCRIPTS -->

<script type="text/javascript">  var _gaq = _gaq || [];  _gaq.push(['_setAccount', 'UA-37564768-1']);  _gaq.push(['_setDomainName', 'keenthemes.com']);  _gaq.push(['_setAllowLinker', true]);  _gaq.push(['_trackPageview']);  (function() {    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);  })();</script></body>

</head>

</html>
