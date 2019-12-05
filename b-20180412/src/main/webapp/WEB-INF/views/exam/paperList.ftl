<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<div>
    <#-- <a style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#settings" aria-controls="settings"
       onclick="verify()" class="pull-right">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        审核通过
    </a> -->
</div>
<div style="height: 15px;"></div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <#-- <th>
            <input type="checkbox" id="checkAll" />
        </th> -->
        <th class="center">
            ID
        </th>
        <th>名称</th>
        <th>状态</th>
        <th>题目数</th>
        <th>难度</th>
        <th>描述</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

        <#list paperList as paper>
        <tr>
            <#-- <td><input type="checkbox" name="ids" value="${paper.id}" /></td> -->
            <td>${paper.id}</td>
            <td>${paper.examName}</td>
            <td>
                <#if paper.status==99>通过<#else >未通过</#if>
            </td>
            <td>
                ${paper.quesNum}
            </td>
            <td>
            ${paper.diffculty}
            </td>
            <td>
            ${paper.mdesc}
            </td>
            <td>
            <a style="cursor: pointer;" onclick="delPaper('${paper.id}','${paper.examName}')">删除</a><a style="cursor: pointer;padding-left: 10px;" onclick="editPaper('${paper.id}')">修改</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

<script>

    $(document).ready(function(){
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $("input[type='checkbox'][name='ids']").prop("checked", true);
            } else {
                $("input[type='checkbox'][name='ids']").prop("checked", false);
            }
        });
    });

    function verify(){
        var ids = "";
        $("input[type='checkbox'][name='ids']:checked").each(function(){
            ids = ids + $(this).val()+",";
        });
        if(ids!=""){
            $.post("/examPaper/verify", { ids:ids },
                    function(data){
                        alert("成功");
                        location.reload();
                    });
        } else {
            alert("请选择试题");
        }
    }

    /**
     * 编辑栏目
     * @param catId
     */
    function editPaper(id){
        art.dialog.open('/examPaper/edit?id='+id,{
            title: '编辑试卷',
            width:1000,
            height:500,
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
     * 删除栏目
     * @param catId
     */
    function delPaper(id,name){
        if (confirm("确认要删除"+name+"?")) {
			$.ajax({
		        type: "POST",
		        url: "/examPaper/delete",
		        data: {id:id},
		        dataType: "json",
		        success: function (data) {
		            if (data.retcode == '0') {
		            	alert("删除成功");
		            }else{
		            	alert("删除失败");
		            }
		            location.reload();
		        }
			 })
		}
    }
</script>

</@htmlBody>
