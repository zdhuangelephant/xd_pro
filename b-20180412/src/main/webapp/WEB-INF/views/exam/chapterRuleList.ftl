<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<div>
    <a style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#settings" aria-controls="settings"
       onclick="verify()" class="pull-right">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        审核通过
    </a>
</div>
<div style="height: 15px;"></div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>
            <input type="checkbox" id="checkAll" />
        </th>
        <th class="center">
            ID
        </th>
        <th>名称</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

        <#list rules as rule>
        <tr>
            <td><input type="checkbox" name="ids" value="${rule.id}" /></td>
            <td>${rule.id}</td>
            <td>${rule.name}</td>
            <td>
                <#if rule.status=99>通过<#else><span style="color: red;">审核中</span></#if>
            </td>
            <td>
                <a onclick="delRule(${rule.id},'${rule.name}')" style="cursor: pointer;">删除</a>
                <a onclick="editRule(${rule.id})" style="cursor: pointer; padding-left: 10px;">修改</a>
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
            $.post("/examRule/verify", { ids:ids },
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
    function editRule(id){
        art.dialog.open('/examRule/editChapterRule?ruleId='+id,{
            title: '编辑规则',
            width:1000,
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
    /* function delRule(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/examRule/delete", { id:id},
                    function(data){
                        if(data.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    } */
    function delRule(id,name){
        if (confirm("确认要删除"+name+"?")) {
			$.ajax({
			        type: "POST",
			        url: "/examRule/delete",
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
