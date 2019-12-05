<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

</styel>
<div class="page-header">
    <h1>
        产品
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            产品列表
        </small>
    </h1>
</div>
<script src="${rc.contextPath}/resources/dist/layui.js?${timeStamp}"></script>

<form class="form-inline" style="margin-bottom: 10px;" accept-charset="utf-8">
    <div class="form-group">
        <select id="module" name="module" class="form-control">
            <option value="">全部地域</option>
            <#list regionList as regionEntity>
                <option value=${regionEntity.module} <#if module==regionEntity.module>selected</#if>>${regionEntity.moduleName}</option>
            </#list>
        </select>
    </div>
   
     <input type="hidden" id="isExpired" value="${isExpired}"/>
 
     <div class="form-group">
        <select name="showStatus" id="showStatus" class="form-control">
            <#if showStatus == ''>
                <option value="" selected>请选择发布状态</option>
                <option value="1" >已发布</option>
                <option value="0">未发布</option>
            <#elseif showStatus=='1'>
                <option value="">请选择发布状态</option>
                <option value="1" selected>已发布</option>
                <option value="0">未发布</option>
            <#elseif showStatus=='0'>
                <option value="">请选择发布状态</option>
                <option value="1">已发布</option>
                <option value="0" selected>未发布</option>
            </#if>
        </select>
    </div>
    <div class="form-group">
        <input type="text" id="courseCode" class="form-control" name="courseCode" placeholder="请输入课程代码" value="${courseCode}"/>
    </div>
    <div class="form-group">
        <input type="text" id="courseName" class="form-control" name="name" placeholder="请输入课程名称" value="${name}"/>
    </div>
    <div class="form-group">
        <input type="text" id="courseId" class="form-control" name="courseId" placeholder="请输入课程ID" value="${courseId}"/>
    </div>
 
    <a  class="btn btn-primary" style="border: 0px; width: 90px;" id= "searchLesson">搜索</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addProduct('${courseCode}')" >新增课程</a>
    <a class="btn btn-primary" id="expiredLesson" style="border: 0px; width: 120px; margin-left: 10px;">查看过期课程</a>

</form>
 
 
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">课程ID</th>
        <th style="width: 60px;">课程代码</th>
        <th>课程考期</th>
        <th style="width: 100px;">课程名称</th>
        <th style="width: 150px;">开课与结课时间</th>
        <!--<th>结课时间</th> -->
        <th>性质</th>
        <th>所属地域</th>
        <th style="width: 160px;">简介</th>
        <th style="width: 50px;">报名人数</th>
        <th style="width: 50px;">是否发布</th>
        <th>原价</th>
        <th>优惠价</th>
        <th>封面</th>
        <th style="width: 50px;">计分规则</th>
        <th style="width:120px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list subjectList.result as subject>
        <tr>
            <td>${subject.id}</td>
            <td>${subject.courseCode}</td>
            <td>${subject.examDate}</td>
            <td>${subject.name}</td>
            <td>${subject.beginApplyTime}  ${subject.endApplyTime}</td>
            <td>${subject.majorCourseInfo.courseType}</td>
            <td>${subject.moduleName}</td>
            <td>${subject.briefInfo}</td>
            <!-- <td>${subject.beginApplyTime}</td>
            <td>${subject.endApplyTime}</td> -->
            <td>${subject.currApplyCount}</td>
            <td>
                <#if subject.showStatus==1>已发布<#else><span style="color: red;">未发布</span></#if>
            </td>
            <td>￥${subject.originalAmount}</td>
            <td>￥${subject.payAmount}</td>
            <td>
               <#if subject.imageUrl!'暂无图片'><img src="${subject.imageUrl}" style="width: 50px; height: 50px;" ></#if>
           </td>
           <td>  
	           <#if subject.ruleId==''>
	           		<a  onclick="addScoreRule('${subject.id}','${subject.ruleId}','${subject.name}','1')">添加计分规则
          		<#else>
	           		<a  onclick="editScoreRule('${subject.id}','${subject.ruleId}','${subject.name}','1')">计分规则 
          		</#if>
           </td>
           <td>
                   <div class="btn-group">
                        <div class="dropdown">
                            <button class="btn  btn-primary dropdown-toggle" style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    操作
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                <li><a style="cursor: pointer" onclick="editCourse(${subject.id})">修改</a></li>
                                <li><a style="cursor: pointer" onclick="delCourse(${subject.id},'${subject.name}')">删除</a></li>
                                <li><a style="cursor: pointer" href="/productItem/list?productId=${subject.id}&isExpired=${isExpired}"><#if isExpired=='1'><font style="color: red">内容预览</font><#else>  内容管理</#if></a></li>
                                <li><a style="cursor: pointer" onclick="quickCopyProduct('${subject.id}')">一键复制</a></li>
                                <li><a style="cursor: pointer" href="/product/release?courseId=${subject.id}">发布</a></li>
                            </ul>
                        </div>
                    </div>
                </td>
        </tr>
    </#list>
    </tbody>
</table>
<@page totalCount="${subjectList.totalCount}"
pageNo="${subjectList.pageNo}"
totalPage="${subjectList.totalPage}" url=""> </@page>
<script>
        //搜索
        function searchProduct(isExpired,page) {
          console.log("isExpired" + isExpired);
          var url = window.location.pathname;
          var showStatus = $("#showStatus").val();
          var courseCode = $("#courseCode").val();
          var courseName = $("#courseName").val();
          var courseId = $('#courseId').val();
          var module = $("#module").val();
          url+="?showStatus="+showStatus
              + "&courseCode="+base64encode(utf16to8(encodeURIComponent(courseCode)))
              + "&name="+base64encode(utf16to8(encodeURIComponent(courseName)))
              + "&module="+module
              + "&courseId="+courseId
              + "&isExpired="+isExpired
              + "&page=" + page;
          self.location=url;
          return false;
        }
        $("#searchLesson").click(function(){
          searchProduct("",1) //未过期课程
        });
        $("#expiredLesson").click(function(){
          searchProduct(1,1) //查看过期课程
        });
        
	    function toPage(page) {
	    	 var isExpired = $("#isExpired").val();
	         searchProduct(isExpired,page);
	    }
        
    /**
     * 编辑栏目
     * @param catId
     */
    function editCourse(id){
        art.dialog.open('/product/edit?id='+id,{
            title: '编辑课程',
            width:400,
            height:600,
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
    function addProduct(courseCode){
        art.dialog.open('/product/add?courseCode=' + courseCode,{
            title: '添加课程',
            width:400,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                     // name和examDate不为空
                if(form.module.value == ''){
                    alert("地域不允许为空")
                    return false;
                }
                if(form.examDate.value == ''){
                    alert("课程考期不允许为空")
                    return false;
                }
                if(form.name.value == ''){
                    alert("课程名称不允许为空")
                    return false;
                }
                if(form.originalAmount.value=='' || Number(form.originalAmount.value) < 0){
                    alert("价格不允许为空或负数")
                    return false;
                }
                
                chileloading();
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                childcloseloading();
                location.reload();
            }
        });
    }

    /**
     * 删除栏目
     * @param catId
     */
    function delCourse(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/product/delete", {id:id},
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
    
    
    /**
     * 一键复制
     */
    function quickCopyProduct(productId){
        art.dialog.open('/product/quickCopy?productId=' + productId,{
            title: '复制产品',
            width:400,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('quickCopyForm');
                // name和examDate不为空
                if(form.module.value == ''){
                    alert("地域不允许为空")
                    return false;
                }
                if(form.examDate.value == ''){
                    alert("课程考期不允许为空")
                    return false;
                }
                if(form.name.value == ''){
                    alert("课程名称不允许为空")
                    return false;
                }
                if(form.originalAmount.value=='' || Number(form.originalAmount.value) < 0){
                    alert("价格不允许为空或负数")
                    return false;
                }
                chileloading();
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                childcloseloading();
                location.reload();
            }
        });
    }


    /**
     * 遮罩层
     * 
     */
       function chileloading(){
         var sWidth,sHeight;  
         sWidth=$(window.parent.document)[0].body.offsetWidth;//浏览器工作区域内页面宽度
         sHeight=screen.height;//屏幕高度（垂直分辨率）
         var bgObj=$(window.parent.document)[0].createElement("div");//创建一个div对象（背景层）
         bgObj.setAttribute('id','bgDiv');
         bgObj.style.position="absolute";
         bgObj.style.top="0";
         bgObj.style.background="#000";
         bgObj.style.filter="Alpha(opacity=30)";
         bgObj.style.opacity="0.3";
         bgObj.style.left="0";
         bgObj.style.width=sWidth + "px";
         bgObj.style.height=sHeight + "px";
         bgObj.style.zIndex = "10000";
         var chidDiv=document.createElement("div");//创建一个div对象（背景层）
         chidDiv.setAttribute('id','chidDiv');
         chidDiv.style.position="absolute";
         chidDiv.style.top="40%";
         chidDiv.style.background="#000";
         chidDiv.style.left="50%";
         chidDiv.style.width=100 + "px";
         chidDiv.style.height=100 + "px";
         var bigImg = document.createElement("img");   
         bigImg.src="${rc.contextPath}/resources/images/loading.gif"; 
         chidDiv.appendChild(bigImg);
         bgObj.appendChild(chidDiv);
         
         $(window.parent.document)[0].body.appendChild(bgObj);    
     }
    
      /**
     *关闭遮罩层
     */
    function childcloseloading(){
          $(window.parent.document).find("#bgDiv").remove();
     }
    
    /**
     * 查看计分规则
     * type 指的是课程规则还是 地域规则
     */
    function editScoreRule(courseId,ruleId ,moduleName,type){
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
                $.post("/scoreRule/orderNew", { courseId:courseId,weights: request,id:ruleId,orderString:orderString,ruleDesc:ruleDesc},
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
     * 给本课程添加一条积分规则
     */
    function addScoreRule(courseId){
       
            $.post("/product/addScoreRule", {courseId:courseId},
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
    
    
    
    
</script>

</@htmlBody>
