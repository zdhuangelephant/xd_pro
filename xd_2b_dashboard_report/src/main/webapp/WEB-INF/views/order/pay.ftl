<div class="modal fade" id="pay" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">支付方式</h4>
            </div>
		    <section class="table-well">
		        <div class="row">
		            <div class="col-md-12">
		                <!-- BEGIN SAMPLE TABLE PORTLET-->
		                <div class="box">
		                    <div class="box-body">
		                        <div class="col-md-3 col-sm-6 col-xs-12">
		                        	<img onclick="pay(this);" id="ali" class="pay-method active" src="${baseOP}/image/order/alipay.jpg"/>
		                        </div>
		                        <div class="col-md-3 col-sm-6 col-xs-12">
		                        	<img onclick="pay(this);" id="wx" class="pay-method" src="${baseOP}/image/order/wxpay.jpg"/>
		                        </div>
		                        <!--
		                        <div class="pay-method " data-pay="ALI_WEB">
		                        	
		                        </div>
		                        <div class="pay-method wx" data-pay="WX_NATIVE">
		                        </div>
		                        -->
		                    </div>
		                </div>
		                <!-- END SAMPLE TABLE PORTLET-->
		            </div>
		        </div>
		    </section>
		    <div class="pull-left">
		       	 <h2 style="color:red;">付款：￥${orderDO.totalAmount}</h2>
			</div>
		    <div class="modal-footer">
		        <button type="button" class="btn btn-danger"  data-toggle="modal" onclick="toPay();">确认付款</button>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
