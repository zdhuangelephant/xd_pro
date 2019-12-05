<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <head>
    <meta charset="UTF-8">
	<!-- jq -->
    <script src="${baseJsOP}/jquery-1.7.min.js"></script>
    <script src="${baseJsOP}/autosize.js"></script>
    

	<!-- css -->
    <link rel="stylesheet" href="${baseCssOP}/post-style.css" type="text/css">
    <link rel="stylesheet" href="${baseCssOP}/dropdownlist.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="${baseCssOP}/easyui.css">
    
    <script type="text/javascript" src="${baseJsOP}/Fold.js"></script>
 	<script src="${baseJsOP}/tab.js"></script>
 	<script>
 		window.onload = function () {
 			autosize(document.querySelectorAll('textarea'));
 		}
 		window.onresize = function () {
			autosize(document.querySelectorAll('textarea'));
		}
 		
 		//全局的ajax访问，处理ajax清求时sesion超时  
        $.ajaxSetup({  
            complete: function(xhr, status) {  
					if(String.prototype.indexOf.call(xhr.responseText, "name=\"userName\"") != -1) {
						window.location = "/admin/login";
				    }
             }  
   		});   
 	   
 	</script>
    <style>
        .tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner {
            background-color: #FFF;
        }
        .tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner {
            border-color: #000;
        }
        .tabs-header {
            border-color: #fff;
        }
    </style>
    <!----------添加---------->
    
</head>
</html>
