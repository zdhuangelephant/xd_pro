<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品分类
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            产品分类列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group" >
        <select name="module" id="productLine" class="form-control" style="width:180px">
            <option value="" >全部地域</option>
            <#list regionList as regionEntity>
              <option value=${regionEntity.module} <#if module==regionEntity.module>selected</#if>>${regionEntity.moduleName}</option>
  	        </#list>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="typeCode" id="typeCode" placeholder="请输入专业代码" value="${typeCode}"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="majorName" id="majorName" placeholder="请输入专业名称" value="${majorName}"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="examSchool" id="examSchool" placeholder="请输入主考院校" value="${examSchool}"/>
    </div>
    <button  class="btn btn-primary" style="border: 0px; width: 90px;" onclick="return searchCategory()">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addCategory()" >添加</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 80px;">专业代码</th>
        <th style="width: 130px;">专业名称</th>
        <th>所属地域</th>
        <th>主考院校</th>
        <th>专业层次</th>
        <th>封面图片</th>
        <th>深度学习封面</th>
        <th>专业状态</th>
        <th>是否合作专业</th>
        <th>是否同步云测评</th>
        <th>是否可购买</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
     <tbody>
      <#list subjectList.result as subject>
        <tr>
            <td>${subject.id}</td>
            <td>${subject.typeCode}</td>
            <td>${subject.majorName}</td>
            <td>${subject.moduleName}</td>
            <td>${subject.majorInfoModel.examSchool}</td>
            <td>${subject.majorInfoModel.degree}</td>
            <td><#if subject.pictureUrl!'暂无图片'><img src="${subject.pictureUrl}" style="width: 60px; height: 60px;" ></#if></td>
            <td>
            	<#if subject.showCover!'暂无图片'><img src="${subject.showCover}" style="width: 60px; height: 60px;" ></#if>
            </td>
            
            <td>
                <#if subject.showStatus==1>显示<#else><span style="color: red;">不显示</span></#if>
            </td>
            <td>
                <#if subject.isCooperation==1>是<#else><span>否</span></#if>
            </td>
            <td>
                <#if subject.isSync==1>是<#else><span>否</span></#if>
            </td>
            <td>
                <#if subject.isBuy==1>是<#else><span>否</span></#if>
            </td>
            
            <td><a style="cursor: pointer" onclick="editCategory(${subject.id})">修改</a>
                <a style="padding-left: 5px;cursor: pointer;" onclick="delCategory(${subject.id},'${subject.majorName}')">删除</a>
                <a style="padding-left: 5px;cursor: pointer;" href="/product/list_by_cat?catId=${subject.id}&isExpired=0">考试课程管理</a></td>
    	    </tr>
    </#list>
    </tbody>
</table>
<@page totalCount="${subjectList.totalCount}"
pageNo="${subjectList.pageNo}"
totalPage="${subjectList.totalPage}" url=""> </@page>
<script>
      /**
       * 编辑栏目
       */
      function searchCategory() {
        var url = window.location.pathname;
        var productLine = $("#productLine").val();
        var typeCode = $("#typeCode").val();
        var majorName = $("#majorName").val();
        var examSchool = $("#examSchool").val();
        url+="?module="+productLine
            + "&typeCode="+base64encode(utf16to8(encodeURIComponent(typeCode)))
            + "&majorName="+base64encode(utf16to8(encodeURIComponent(majorName)))
            + "&examSchool="+base64encode(utf16to8(encodeURIComponent(examSchool)));
        self.location=url;
        return false;
      }
    /**
     * 编辑栏目
     * @param catId
     */
    function editCategory(catId){
        art.dialog.open('/productCategory/edit?categoryId='+catId,{
            title: '编辑专业',
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
    
    	var module = $("#productLine").val();
    
        art.dialog.open('/productCategory/add?classify=${classify}&module='+module,{
            title: '新增专业',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(form.module.value == ''){
                    alert("地域不允许为空")
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

    /**
     * 添加栏目
     * @param catId
     */
    function addChildCategory(catId){
        art.dialog.open('/productCategory/add?parentId='+catId,{
            title: '添加专业',
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
            $.post("/productCategory/delete", { catId:catId},
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
    
    function toPage(page) {
		var url = window.location.pathname;
        var productLine = $("#productLine").val();
        var typeCode = $("#typeCode").val();
        var majorName = $("#majorName").val();
        var examSchool = $("#examSchool").val();
        url+="?module="+productLine
            + "&typeCode="+base64encode(utf16to8(encodeURIComponent(typeCode)))
            + "&majorName="+base64encode(utf16to8(encodeURIComponent(majorName)))
            + "&examSchool="+base64encode(utf16to8(encodeURIComponent(examSchool)))
            + "&page=" + page;
        self.location=url;
        return false;
	}
</script>

</@htmlBody>
