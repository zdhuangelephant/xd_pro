<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        资源分类
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            资源分类列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
   <!-- <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>-->
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 2px;" onclick="addCategory()" >添加</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 2px;" onclick="importResource(1)" >一键导入目录</a>
    
    
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>描述</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
    ${tableTree}
    </tbody>
</table>


    <script src="${rc.contextPath}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script>
    <script src="${rc.contextPath}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/bootstrap-typeahead.js"></script>
    <script src="${rc.contextPath}/resources/js/jqexcel/excel.js?1"></script>
    <script src="${rc.contextPath}/resources/dist/layui.js?${timeStamp}"></script>
    <script src="${rc.contextPath}/resources/exam/common.js"></script>
    <script src="${rc.contextPath}/resources/js/course/resourseImport.js?122${timeStamp}"></script>
    
    
    <#include "/course/importResource.ftl"/>
    <#include "/common/form/file.ftl"> 
<script>

    function importResource(type){
    	$('#importResource').modal();
    	$("#importType").val(type);
    }

    /**
     * 编辑栏目
     * @param catId
     */
    function editCategory(catId){
        art.dialog.open('/courseCategory/edit?categoryId='+catId,{
            title: '编辑栏目',
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
     * 添加栏目
     */
    function addCategory(){
        art.dialog.open('/courseCategory/add',{
            title: '添加栏目',
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
     * 添加栏目
     * @param catId
     */
    function addChildCategory(catId){
        art.dialog.open('/courseCategory/add?parentId='+catId,{
            title: '添加栏目',
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
     * 删除栏目
     * @param catId
     */
    function delCategory(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/courseCategory/delete", { catId:catId},
                    function(data){
                    data=JSON.parse(data);
                        if(data.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    }
</script>

</@htmlBody>
<@fileUpload></@fileUpload>