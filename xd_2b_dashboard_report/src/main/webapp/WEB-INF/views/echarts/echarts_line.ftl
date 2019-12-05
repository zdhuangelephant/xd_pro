<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE 2 | ChartJS</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
    <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet" />
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-md-12">
            <!-- AREA CHART -->
            <div class="box box-primary">
                <div class="box-body">
                    <div class="chart">
                    	<input id="dformat" type="hidden" value="${dformat}">
                    	<input id="timeStamp" type="hidden" value="${timeStamp}">
                        <div id="main" style="width: 100%; height: 400px;"></div>
                    </div>
                   
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </div>

    </div>
    <!-- /.row -->
</section>
    <!-- jQuery 2.2.3 -->
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseOP}/content/min/js/supershopui.common.js"></script>

    <script src="//cdn.bootcss.com/echarts/3.3.2/echarts.min.js" charset="utf-8"></script>
    <!-- page script -->
    <script src="${baseOP}/js/dashboard.js"></script>
</body>
</html>
