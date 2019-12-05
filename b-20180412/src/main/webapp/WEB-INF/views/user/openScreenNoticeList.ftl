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
        开屏通知列表
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            开屏通知
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="isVisible" class="form-control">
            <#if isVisible = 0>
            <option value="">--请选择--</option>
           	<option value="0" selected>不显示 </option>
           	<option value="1">显示</option>
           	<#elseif isVisible==''>
           	<option value="" selected>--请选择--</option>
           	<option value="0">不显示</option>
           	<option value="1">显示 </option>
           	<#elseif isVisible==1>
           	<option value="" >--请选择--</option>
           	<option value="0">不显示</option>
           	<option value="1" selected>显示 </option>
           	</#if>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addNotice()">添加</a>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th >标题</th>
        <th >链接设置</th>
        <th >图片</th>
        <th >显示频次</th>
        <th >更新时间</th>
        <th >所属模块</th>
        <th >是否显示</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list data.result as blankNotice>
        <tr>
            <td>${blankNotice.id}</td>
            <td>${blankNotice.title}</td>
            <td><a href='${blankNotice.jumpUrl}'>${blankNotice.jumpUrl}</a></td>
             <td>
             <#if blankNotice.image??><img src="${blankNotice.image}" style="width: 50px; height: 50px;" ><#else>暂无图片</#if>
             </td>
            <td><#if blankNotice.type==0>
            		每次首次
            	<#elseif blankNotice.type==1> 
            		每次启动
            	<#elseif blankNotice.type==2>
            		一次性	
            	</#if>
            </td>
            <td>${blankNotice.updateTime}</td>
            <td>${blankNotice.moduleName}</td>
            
            <td>
            	<#if blankNotice.isVisible==0>
            		不显示
            	<#elseif blankNotice.isVisible==1> 
            		显示	
            	</#if>
            </td>
            <td><a onclick="editFeedBack('${blankNotice.id}')">编辑</a>
            	<a style="padding: 5px;">|</a>
                <a onclick="delFeedBack('${blankNotice.id}','该条信息')">删除</a>
            </td>  
        </tr>
        </#list>
    </tbody>
</table>
<@page totalCount="${data.totalCount}" pageNo="${data.pageNo}" totalPage="${data.totalPage}" url="">
    </@page>
<script>
    /**
     * 编辑用户反馈
     * @param catId
     */
    function editFeedBack(catId){
        art.dialog.open('/userNotice/blankNoteceEdit?id='+catId,{
            title: '编辑开屏通知',
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
        art.dialog.open('/userNotice/add',{
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
    function delFeedBack(id,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/userNotice/blankNoteceDelete", { id:id},
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
     * 添加
     */
    function addNotice(){
        art.dialog.open('/userNotice/openScreenNoticeAdd',{
            title: '添加',
            width:500,
            height:550,
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
</script>

</@htmlBody>
