<#include "/common/layout.ftl" />
<@htmlHead title="首页">
<script src="${JS_PATH}artdialog/artDialog.js?skin=blue"></script>
<script src="${JS_PATH}artdialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $(".click").click(function () {
            $(".tip").fadeIn(200);
        });

        $(".tiptop a").click(function () {
            $(".tip").fadeOut(200);
        });

        $(".sure").click(function () {
            $(".tip").fadeOut(100);
        });

        $(".cancel").click(function () {
            $(".tip").fadeOut(100);
        });
        
        

    });
</script>
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        用户反馈
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            用户反馈列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select id="appVersion" name="appVersion" class="form-control">
            <option value="0">请选择版本</option>
            <#list serach_appVersion_list as appV>
                <option value="${appV}" <#if appV == appVersion>selected</#if> >${appV}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
</form>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th >反馈内容</th>
        <th >注册电话</th>
        <th >app版本</th>
        <th >联系电话</th>
        <th >处理状态</th>
        <th style="width: 150px;">创建时间</th>
    </tr>
    </thead>
    <tbody>
        <#list feedBackLists.result as feedBack>
        <tr>
            <td> <a style="cursor: pointer;" onclick="detailFeedBack(${feedBack.id})">${feedBack.id}</a></td>
            <td>${(feedBack.content)!'UNKNOW'}</td>
            <td>${(feedBack.telephone)!'UNKNOW'}</td>
            <td>${(feedBack.appVersion)!'UNKNOW'}</td>
            <td>${(feedBack.number)!'UNKNOW'}</td>
            
            <#if (feedBack.handleStatus == 1)>
                <td>未处理</td>
            <#elseif (feedBack.handleStatus == 2)>  
                <td>待跟进</td>
            <#elseif (feedBack.handleStatus == 3)>
                <td>已处理</td>
            <#elseif (feedBack.handleStatus == 4)>
                <td>已处理</td>
            <#else>
                <td>UNKNOW</td>
            </#if>

            <td>${feedBack.createTime}</td>
        </tr>
        </#list>
    </tbody>
</table>
<@page totalCount="${feedBackLists.totalCount}" pageNo="${feedBackLists.pageNo}" totalPage="${feedBackLists.totalPage}" url="">
    </@page>
<script>
    /**
     * 编辑用户反馈
     * @param catId
     */
    function editFeedBack(catId){
        art.dialog.open('/userFeedBack/edit?categoryId='+catId,{
            title: '编辑用户反馈',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 添加用户反馈
     */
    function addFeedBack(){
        art.dialog.open('/userFeedBack/add',{
            title: '添加用户反馈',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }


    /**
     * 删除用户反馈
     * @param catId
     */
    function delFeedBack(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/userFeedBack/delete", { catId:catId},
                    function(data){
                        if(data.retCode==0){
                            alert("删除失败");
                        } else {
                            alert("删除成功");
                        }
                        location.reload();
                    }
            );
        }
    }
    
    function toPage(page){
        var url = window.location.pathname+"?page="+page;
        self.location=url;
    }
    
    /**
     * 查看反馈详情
     * @param catId
     */
    function detailFeedBack(id){
        art.dialog.open('/userFeedBack/detail?id='+id,{
            title: '查看',
            width:600,
            height:600,
            ok: function () {
              var iframe = this.iframe.contentWindow;
              if (!iframe.document.body) {
                  alert('iframe还没加载完毕呢')
                  return false;
              };
              console.log("zhushiba");
              var form = iframe.document.getElementById('editForm');
              // 进行非空判断
              if(!myCheck(iframe)){
                  return false;
              };
              form.submit();
              return false;
            },
            
            cancelVal: '关闭',
            cancel: true
        });
    }
    
    function myCheck(iframe){
      var flag = true;
      $("input[name]",iframe.document).each(function(){
          if($(this).attr("name")=="handleNote") return true;
          if($(this).val()==""){
              alert("表单不允许有空选项,请填写完整");
              $(this).focus();
              flag= false;
              return flag;
          }
      });
     return flag;
    } 
    
    
</script>

</@htmlBody>
