<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        覆盖地区
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
          覆盖地区列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 2px;" onclick="addModule()" >添加</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 2px;" onclick="order()" >排序</a>
</div>

<table class="table table-striped table-bordered table-hover" id="">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 100px;">地区名称</th>
        <th style="width: 100px;">地区编码</th>
        <th style="width: 50px;">排序</th>
        <th style="width: 200px;">描述</th>
        <th style="width: 100px;">app端是否展示</th>
        <th style="width: 50px;">是否首选</th>
        <th style="width: 300px;">操作</th>
    </tr>
    </thead>
     <tbody>
        <#list regionList as region>
        <tr>
            <td>${region.id}</td>
            <td>${region.moduleName}</td>
            <td>${region.module}</td>
            <td>
            <input style="padding:0px;width:100px" name="listOrder" prevalue="${region.listOrder}" 
            	id="${region.id}" class="orderInput" type="number" oninput="if(value.length>5)value=value.slice(0,5)" value="${region.listOrder}">
            </td>
            <td>${region.detail}</td>
            <td><#if region.showStatus==1>显示<#else>隐藏</#if></td>
            <td>${region.firstChoice}</td>
            <td><a
                    onclick="editModule(${region.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    href="/productCategory/list?module=${region.module}&classify=1">产品分类</a><a
                    style="padding: 5px;">|</a>
                     <#if region.ruleId==''>
	           			<a  onclick="addScoreRule('${region.id}')">添加计分规则</a><a
                    style="padding: 5px;">|</a>
	          		<#else>
		           		<a  onclick="editScoreRule('${region.ruleId}','${region.name}')">计分规则 </a><a
                    style="padding: 5px;">|</a>
	          		</#if>
					<#if region.courseId==''> 
						<a onclick="addBeginner(${region.id})">添加新手课程</a>
					<#else>
                      	<a  href="/productItem/list?productId=${region.courseId}">新手课程查看</a></td>
                    </#if>

            </td>
        </tr>
        </#list>
    </tbody>
</table>	

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editModule(id){
        art.dialog.open('/region/edit?id='+id,{
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
    function addModule(){
        art.dialog.open('/region/add',{
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
     * 查看计分规则
     */
    function editScoreRule( ruleId ,moduleName){
        art.dialog.open('/scoreRule/edit?id='+ruleId,{
            id:"scoreRuleId",
            title: moduleName +'- 算分规则',
            width:1000,
            height:500,
            ok: function () {
              var request = '';
              var iframe = this.iframe.contentWindow;
              var array = new Array();
              var subscript = 0;
              if (!iframe.document.body) {
                  alert('iframe还没加载完毕呢')
                  return false;
              };
              //权重
              $('.orderWeight',iframe.document).each(function(){  
                var value = $(this).val();
                var index = $(this).attr("id");
                var preValue = $(this).attr("preValue");
                if(typeof(index) != 'undefined' && index != '' && value!=preValue){
                    request = request + index + ":" + value + ";";
                }
                array[subscript] = value;
                subscript++;
              });
              //排序
              var orderString ='';
              $('.orderList',iframe.document).each(function(){  
                var value = $(this).val();
                var index = $(this).attr("id");
                var preValue = $(this).attr("preValue");
                if(typeof(index) != 'undefined' && index != '' && value!=preValue){
                  orderString = orderString + index + ":" + value + ";";
                }
              });
              //规则描述
              var ruleDesc = $('#description',iframe.document).val();
              var totalWeight = 0;
              var flag = true;
              for(var i = 0;i<array.length-1;i++){
                if(array[i] < 0){
                  flag = false;
                }
                else{
                  totalWeight += Number(array[i]);
                }
              }
              if(totalWeight == 1 && flag){
                 $.post("/scoreRule/order", { weights: request,id:ruleId,orderString:orderString,ruleDesc:ruleDesc},
                      function(data){
                          if(data==true){
                            alert("设置成功");
                          }else{
                          	alert("设置失败");
                          }
                          location.reload();
                 });
              }else if(!flag){
                 alert("权重存在负数");
                 return;
              }else{
                 alert("权重和不等于1");
                 return;
              }
                           
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }  
    
        /**
	     * 添加计分规则
	     * 给地域添加一条默认积分规则
     	*/
	    function addScoreRule(regionId){
	            $.post("/region/addScoreRule", {regionId:regionId},
	                function(data){
	                    if(data.retCode==0){
	                        alert("添加成功");
	                    } else {
	                        alert("添加失败");
	                    }
	                    location.reload();
	                }
	            );
	    }  
	    
    
    
    
        /**
      * 排序
         */
          function order(){
            var request = '';
            $(".orderInput").each(function(i){
                var value = $(this).val();
                var id = $(this).attr("id");
                var preValue = $(this).attr("preValue");
                if(typeof(id) != 'undefined' && id != '' && value!=preValue){
                    request = request + id + ":" + value + ";";
                }
            });
            if(request==''){
                alert("排序成功");
            } else {
                $.post("/region/order", { orders: request},
                        function(data){
                            if(data==true){
                              alert("排序成功");
                            }
                            location.reload();
                        });
            }
        }

	/**
     * 添加新手课程
     */
    function addBeginner(moduleId){
        art.dialog.open('/product/addBeginner?moduleId='+moduleId,{
            title: '新增新手课程',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                // 
                if(form.beginApplyTime.value == ''){
                    alert("开始时间不能为空")
                    return false;
                }
                if(form.endApplyTime.value == ''){
                    alert("结束时间不能为空")
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


</script>

</@htmlBody>
