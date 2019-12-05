<div class="container-fluid">

  <!-- Page Heading -->
  <div class="row">
    <div class="col-lg-12">
      <h1 class="page-header">
        概览 <small>统计分析概览</small>
      </h1>
      <ol class="breadcrumb">
        <li class="active">
          <i class="fa fa-dashboard"></i> 概览
        </li>
      </ol>
    </div>
  </div>
  <!-- /.row -->

  <div class="row">
    <div class="col-lg-3 col-md-6">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-bar-chart-o fa-5x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">26</div>
              <div>文档</div>
            </div>
          </div>
        </div>
        <a href="javascript:;" onclick="window.open('/doc/postman')">
          <div class="panel-footer">
            <span class="pull-left">View Details</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
    <div class="col-lg-3 col-md-6">
      <div class="panel panel-green">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-table fa-5x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">12</div>
              <div>用例</div>
            </div>
          </div>
        </div>
        <a href="javascript:;" onclick="window.open('/case/testcase')">
          <div class="panel-footer">
            <span class="pull-left">View Details</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
    <div class="col-lg-3 col-md-6">
      <div class="panel panel-yellow">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-tasks fa-5x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">124</div>
              <div>RUNNER</div>
            </div>
          </div>
        </div>
        <a href="javascript:;" onclick="window.open('/run/runner')">
          <div class="panel-footer">
            <span class="pull-left">View Details</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
    <div class="col-lg-3 col-md-6">
      <div class="panel panel-red">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-desktop fa-5x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">13</div>
              <div>调度策略</div>
            </div>
          </div>
        </div>
         <a href="javascript:;" onclick="window.open('/run/strategyList')">
          <div class="panel-footer">
            <span class="pull-left">View Details</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
    
     <div class="col-lg-3 col-md-6">
      <div class="panel panel-yellow">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-tasks fa-5x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">124</div>
              <div>数据源</div>
            </div>
          </div>
        </div>
        <a href="javascript:;" onclick="window.open('/dataSource/list')">
          <div class="panel-footer">
            <span class="pull-left">View Details</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
    
    
  </div>
  <!-- /.row -->

  <div class="row">
    <div class="col-lg-12">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Area Chart</h3>
        </div>
        <div class="panel-body">
          <div id="morris-area-chart"></div>
        </div>
      </div>
    </div>
  </div>
  <!-- /.row -->
</div>
<!-- /.container-fluid -->

<script src="${baseJsOP }/plugins/morris/raphael.min.js"></script>
<script src="${baseJsOP }/plugins/morris/morris.min.js"></script>

<script src="${baseJsOP }/index/morris-data.js"></script>
