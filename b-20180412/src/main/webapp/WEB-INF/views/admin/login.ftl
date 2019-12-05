<#include "/common/layout.ftl" />

<@htmlHead title="登陆">
</@htmlHead>

<@htmlBody bodyclass="login-layout light-login">

<script language="JavaScript">
    <!--
    if(top!=self)
        if(self!=top) top.location=self.location;
    //-->
</script>


<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="space-6" style="margin-top:30px;margin-bottom:30px;"></div>
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i>
                            <span class="red">小逗</span>
                            <span class="grey" id="id-text2">网络</span>
                        </h1>
                    </div>
                    <div class="space-6" style="margin-top:30px;margin-bottom:30px;"></div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger"
                                        style="border-bottom:0px;">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        登陆
                                    </h4>
                                    <div class="space-6"></div>
                                    <form action="/j_spring_security_check" method="post">
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="user_name"
                                                                   class="form-control" value=""
                                                                   placeholder="用户名"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                            </label>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="password"
                                                                   class="form-control" value=""
                                                                   placeholder="密码"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                            </label>
                                            <div class="space"></div>
                                            <div class="clearfix">
                                                <button type="submit"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">登陆</span>
                                                </button>
                                            </div>
                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>

                                </div>
                                <!-- /.widget-main -->
                                <#if fail=="true">
                                <div class="toolbar clearfix">
                                    <div style="color: white;font-weight: bold;padding-left: 10px;">
                                        <span class="glyphicon glyphicon-exclamation-sign red" aria-hidden="true"></span></i><span style="padding-left: 5px;">账号或者密码错误</span>
                                    </div>
                                </div>
                                </#if>
                            </div>
                            <!-- /.widget-body -->
                        </div>
                        <!-- /.login-box -->

                    </div>
                    <!-- /.position-relative -->


                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.main-content -->
</div><!-- /.main-container -->
</@htmlBody>
