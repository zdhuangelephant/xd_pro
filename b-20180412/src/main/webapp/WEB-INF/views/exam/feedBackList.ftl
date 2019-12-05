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
        错题反馈
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            错题反馈列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th >题干</th>
        <th >错误信息</th>
        <th >简述</th>
    </tr>
    </thead>
    <tbody>
        <#list wrongQuesList.result as wrongQues>
        <tr>
            <td>${wrongQues.id}</td>
            <td>${wrongQues.mdesc}</td>
            <td>${wrongQues.errDesc}</td>
            <td>${wrongQues.wrongMsg}</td>
        </tr>
        </#list>
    </tbody>
</table>
 <@page totalCount="${wrongQuesList.totalCount}" pageNo="${wrongQuesList.pageNo}" totalPage="${wrongQuesList.totalPage}" url="">
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
</script>

</@htmlBody>
