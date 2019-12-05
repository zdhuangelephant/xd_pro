<div class="modal fade" id="save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增班级</h4>
            </div>
        <section class="content">
        <div class="row">
            <!-- right column -->
            <div class="col-md-12">
                <!-- general form elements disabled -->
                <div class="box box-warning">
                    <!-- /.box-header -->
                    <div class="box-body">
                        <form role="form" id="saveForm" method="post" action="/class/save_class">
                            <!-- input states -->
                            <label class="control-label">
                            	班级名称
                            </label>
                            <input type="text" class="form-control" id="save_className" name="className" placeholder="">
                            <label class="control-label" for="inputWarning">
                                <i class="fa fa-bell-o"></i>
                                                                                         说明:
                            </label>
                            <input type="text" class="form-control" id="save_description" name="description" placeholder="">
                            <div class="modal-footer">
				                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				                <button type="button" id="saveClass" class="btn btn-primary" >确定</button>
            				</div>
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
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>