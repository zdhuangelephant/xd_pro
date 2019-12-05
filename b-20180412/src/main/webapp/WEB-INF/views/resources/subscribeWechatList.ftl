<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<style>
	.ht{
		color: red
	}
</style>
<div class="page-header">
    <h1>
       订阅
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
          订阅列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;" onsubmit="return check(this)">
    <div class="form-group">
        <select id="platformType" name="platformType" class="form-control">
            <option value="-1" <#if platformType == '-1'>selected</#if>>请选择平台</option>
            <option value="3" <#if platformType == '3'>selected</#if>>知乎专栏</option>
            <option value="1" <#if platformType == '1'>selected</#if>>微信公众号</option>
            <option value="2" <#if platformType == '2'>selected</#if>>头条</option>
        </select>
        <input type="text" class="form-control" id="keyword" name="keyword"
			placeholder="专栏号/公众号/头条号" value="${keyword}" />
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
</form>



<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>公众号</th>
       <!--  <th>微信号</th>
        <th>月发文</th> -->
        <th>功能介绍</th>
        <th>微信认证</th>  
        <th>最近文章</th>  
        <th>最近发文时间</th>  
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list dataObjList as dataObj>
        <tr>
            <td><a href="${dataObj.linkAddr}" target="_blank">${dataObj.officialAccount}</a></td>
            <!-- <td>${dataObj.officialAccountNO}</td>
            <td>${dataObj.repostsOfMonths}</td> -->
            <td>${dataObj.profile}</td>
            <td>${dataObj.authenticate}</td>
            <td><a href="${dataObj.recentArticleLinkAddr}" target="_blank">${dataObj.recentArticle}</a></td>
            <td>${dataObj.publishTime}</td>
            <td>
	            <#if dataObj.isSubscribe == '0'>
	            	<a onclick="subscribe('0')">订阅</a>
	            <#else>
	            	<a onclick="subscribe('1')">取消订阅</a>
	            </#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
<script>
function subscribe(state){
	// 订阅
	if(state == '0'){
		
	}else{
		// 取消订阅
		
	}
	
}

function check(obj){  
	var platform = $("#platformType").val();
	if(platform == '-1') {
		alert("请选择平台");
		return false;
	}
	
	var keyword = $("#keyword").val();
	if(/^\s*$/.test(keyword)){
		alert("请输入专栏号/公众号/头条号");
		return false;
	}
	return true;
}  

    /**
     * 编辑动态任务
     * @param catId
     */
    function editMission(id){
        art.dialog.open('/mission/dynamicMissionEditPage?id='+id,{
            title: '编辑动态任务',
            width:500,
            height:700,
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
     * 添加任务
     */
    function addMission(){
        art.dialog.open('/mission/dynamicAddPage',{
            title: '添加动态任务',
            width:500,
            height:700,
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
     * 删除任务
     * @param Id
     */
    function delMission(id,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/mission/delete", { id:id},
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
</script>

</@htmlBody>
