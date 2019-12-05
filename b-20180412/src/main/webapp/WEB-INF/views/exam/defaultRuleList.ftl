<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            默认命题蓝图列表
        </small>
    </h1>
</div>

<div>
    <a style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#settings" aria-controls="settings"
       onclick="addDefaultRule()" class="pull-right">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        添加默认蓝图
    </a>
	
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
        <th>练习类型</th>
        <th>名称</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

    <#list rules as rule>
        <tr>
            <#-- <td><input type="checkbox" name="ids" value="${rule.id}" /></td> -->
            <td>${rule.id}</td>
            <td>${rule.examTypeName}</td>
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

    function addDefaultRule(){
        art.dialog.open('/examRule/add_default_rule',{
            title: '添加默认蓝图',
            width:1500,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(form.ruleType.value == '-1'){
                	alert("请选择练习类型")
            		return false;
        		}
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
        art.dialog.open('/examRule/edit_default_rule?ruleId='+id,{
            title: '编辑默认蓝图',
            width:1500,
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
