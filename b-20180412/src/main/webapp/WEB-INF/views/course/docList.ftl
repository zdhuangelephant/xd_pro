<#include "/common/layout.ftl" /> <#include
"/course/chapterListFrame.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content"> <@chapterframe>

<ul class="nav nav-tabs">

	<#if courseId?if_exists> <#if courseId!=0> <#if chapterId!=0>
	<li class="pull-right"><a
		style="background: none; border: none; font-weight: bold; color: #2679b5;"
		href="#settings" aria-controls="settings"
		onclick="addDoc(${courseId},${chapterId})"> <span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加
	</a></li> </#if> </#if> </#if>


    <#include "/exam/importQuestions.ftl"/>
    <#include "/common/form/file.ftl"> 
    
    
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<#-- <th><input type="checkbox" id="checkAll" /></th> -->
				<th class="center" style="width: 50px;">ID</th>
				<th style="width: 150px;">章节</th>
				<th style="width: 150px;">名称</th>
				<th style="width: 300px;">URL</th>
				<th>描述</th>
				<th style="width: 50px;">状态</th>
				<th style="width: 110px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<#list pageList.result as doc>
			<tr>
				<#-- <td><input class="inputcheckbox " name="ids" value="${doc.id}"
					type="checkbox"></td> -->
				<td>${doc.id}</td>
				<td>${doc.chapterName}</td>
				<td>${doc.name}</td>
				<td
					style="width: 300px; word-break: break-all; word-wrap: break-word;">${doc.url}</td>
				<td>${doc.detail}</td>
				<td><#if doc.status==99>通过<#else><a style="color: red;">待审核</a></#if>
				</td>
				<td>
					<div class="btn-group">
						<div class="dropdown">
							<button class="btn  btn-primary dropdown-toggle"
								style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;"
								type="button" id="dropdownMenu2" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="true">
								操作 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
								<li><a style="cursor: pointer;"
									onclick="delDoc(${doc.id},'${doc.name}')">删除</a></li>
								<li><a style="cursor: pointer;"
									onclick="editDoc(${doc.id},${doc.courseId})">修改</a></li>
								<li><a style="cursor: pointer;"
									onclick="setKeywords(${doc.id},${doc.courseId},${doc.chapterId})">关联关键词</a></li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>



	<#-- <li class="pull-right"><a
		style="padding: 5px 12px 15px; background: none; border: none; font-weight: bold; color: #2679b5;"
		href="#settings" aria-controls="settings" onclick="verify()"> <span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> 审核通过
	</a></li> -->
</ul>

<@page totalCount="${pageList.totalCount}" pageNo="${pageList.pageNo}"
totalPage="${pageList.totalPage}" url=""> </@page>

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

    /**
     * 编辑栏目
     * @param catId
     */
    function editDoc(id,courseId){
        art.dialog.open('/courseDoc/edit?docId='+id+'&courseId='+courseId,{
            title: '编辑',
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
    function addDoc(courseId,chapterId) {
        art.dialog.open('/courseDoc/add?courseId='+courseId+'&chapterId='+chapterId, {
            title: '添加文档',
            width: 600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                ;
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
    function delDoc(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/courseDoc/delete", { id:id},
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

    function toPage(page){
        var url = window.location.pathname+"?courseId=${courseId}&chapterId=${chapterId}&page="+page;
        self.location=url;
    }

    function verify(){
        var ids = "";
        $("input[name='ids']:checked").each(function() {
            ids = ids + $(this).val() + ";";
        });
        if(ids!=""){
            $.post("/courseDoc/verify", { ids:ids },
                    function(data){
                        alert("成功");
                        location.reload();
                    });
        } else {
            alert("请选择试题");
        }
    }

    /**
     * 设置关联关键词
     * @param resourceId
     * @param courseId
     */
    function setKeywords(resourceId,courseId,chapterId){
        art.dialog.open('/courseKeyword/keywordsMap?courseId='+courseId+'&chapterId='+chapterId+'&resourceType=3&resourceId='+resourceId, {
            title: '关联关键词',
            width: 600,
            height:400,
            cancelVal: '关闭',
            cancel: true
        });
    }
    
 // 点击批量导入按钮， 触发模态框，导入之前须选择课程
    function validatedIsChooseCourse(){
        var value = $("select[name='courseId']").val();
        if(value == '0'){
            layui.use('layer', function(){
              var layer = layui.layer;
              layer.msg('导入之前请先确定并选择左边的课程');
            });              
            return false;
        }else{
            $("#batchImport").attr("data-course",value);
            $('#importQuestions').modal();
        }
        
    }
 
 
</script>

</@chapterframe> </@htmlBody>
