<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | General Form Elements</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
    <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
</head>
<body>
    <section class="content-header">
        <h1>
			新增班级
            <small></small>
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="row">
            <!-- right column -->
            <div class="col-md-12">
                <!-- general form elements disabled -->
                <div class="box box-warning">
                    <!-- /.box-header -->
                    <div class="box-body">
                        <form role="form" id="Form" method="post" action="/class/save_class">
                            <!-- input states -->
                            <label class="control-label">
                            	班级名称
                            </label>
                            <input type="text" class="form-control" id="inputSuccess" placeholder="${class.className}">
                            <label class="control-label" for="inputWarning">
                                <i class="fa fa-bell-o"></i>
                                                                                         说明:
                            </label>
                            <input type="text" class="form-control" id="inputWarning" placeholder="${class.description}">
                            <div class="form-group has-success">
                                <span class="help-block">Help block with success</span>
                            </div>
                            <div class="form-group has-warning">
                                <span class="help-block">Help block with warning</span>
                            </div>
                            <div class="form-group has-error">
                                <span class="help-block">Help block with error</span>
                            </div>
                            
                            <button class="btn btn-info pull-right" type="submit">确定</button>
                        </form>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
            <!--/.col (right) -->
        </div>
        <!-- /.row -->
    </section>
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
</body>
</html>
