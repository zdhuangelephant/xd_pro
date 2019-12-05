<#include "/common/layout.ftl" /> <@htmlHead title="首页">
<link rel="stylesheet"
    href="${rc.contextPath}/resources/js/tip/css/simpletooltip.min.css" />
<script src="${rc.contextPath}/resources/js/tip/js/simpletooltip.min.js"></script>
</@htmlHead> <@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品内容管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
        <p style="visibility:visible">课程ID=${productId}</p>
        <p style="visibility:visible">课程名=${product.name}</p>
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <div class="btn-group">
        <#if isExpired=='1'>
        <a class="btn btn-primary" style="border: 0px; width: 180px; cursor: default;opacity: 0.7;" href ="javascript:return false;" onclick="return false;">按资源生成章节目录</a>
        <#else>
            <a class="btn btn-primary" style="border: 0px; width: 180px;"
                onclick="addChapterByCourse(${productId},'')">按资源生成章节目录</a>
        </#if>
    </div>
    <div class="btn-group">
    <#if isExpired=='1'>
        <a class="btn btn-primary"
            style="border: 0px; width: 90px; margin-left: 10px cursor: default;opacity: 0.7;" href ="javascript:return false;" onclick="return false;">添加章节</a>
    <#else>
            <a class="btn btn-primary"
            style="border: 0px; width: 90px; margin-left: 10px"
            onclick="addChapter(${productId},'')">添加章节</a>
    </#if>
    </div>


    <div class="btn-group">
        <div class="dropdown">
            <#if isExpired=='1'>
                <button class="btn  btn-primary dropdown-toggle"
                style="border: 0px; width: 90px; margin-left: 10px;" type="button"
                id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="true" disabled="disabled">
                题库 <span class="caret"></span>
            </button>
            <#else>
                <button class="btn  btn-primary dropdown-toggle"
                style="border: 0px; width: 90px; margin-left: 10px;" type="button"
                id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="true">
                题库 <span class="caret"></span>
            </button>
            </#if>
        
            
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                <li><a onclick="questionBankSetting(${productId})">题库设置</a></li>
                <li role="separator" class="divider"></li>
                <li><a onclick="productQuestion()">产品题库</a></li>
                <li role="separator" class="divider"></li>
                <li><a onclick="paperAdd()">添加试卷</a></li>
                <li><a onclick="paperList()">试卷列表</a></li>
                <li role="separator" class="divider"></li>
                <li><a onclick="addRule(${productId})">添加命题蓝图</a>
                </li>
                <li><a onclick="examRuleList(${productId})">命题蓝图列表</a></li>
                <li><a onclick="finalExamList(${productId})">期末测试列表</a></li>
            </ul>
        </div>
    </div>
</div>

<style>
.star-rating .caption {
    display: none !important;
}

.rating-xs {
    font-size: 1em !important;
}

.orderInput {
    width: 40px;
    text-align: center;
}

.taskRatio {
    width: 40px;
    text-align: center;
}
.weight {
    width: 40px;
    text-align: center;
}
</style>

<table id="itemTableTree"
    class="table table-striped table-bordered table-hover treetable">
    <thead>
        <tr>

            <th style="width: 150px;">章节</th>
            <th>ID</th>
            <th>封面图片</th>
            <th>排序</th>
            <th style="width: 150px;">名称</th>
            <th>重要程度</th>
            <th>权重</th>
            <th style="width: 100px;">章节关联课件</th>
            <th>类型</th>
            <th>关联题目数</th>
<!--            <th>是否显示</th> -->
            <th>是否免费</th>
            <th style="width: 90px;">操作</th>
        </tr>
    </thead>
    <tbody>${tableTree}
    </tbody>
</table>

<div style="margin-top: 10px;">
    <div class="btn-group">
        <#if isExpired=='1'>
            <a class="btn btn-primary" style="border: 0px; width: 90px;cursor: default;opacity: 0.7;" href ="javascript:return false;" onclick="return false;">排序</a>
        <#else>
            <a class="btn btn-primary" style="border: 0px; width: 90px;"
            onclick="order()">排序</a>
        </#if>
        
    </div>
    <div class="btn-group">
        
        <#if isExpired=='1'>
            <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px; cursor: default;opacity: 0.7;" href ="javascript:return false;" onclick="return false;">权重检测</a>
        <#else>
            <a class="btn btn-primary" 
            style="border: 0px; width: 90px; margin-left: 10px;"
            onclick="checkWeight()">权重检测</a>
        </#if>
    </div>
</div>

<link
    href="${rc.contextPath}/resources/js/jqueryTableTreeView/jquery.treetable.css"
    rel="stylesheet" type="text/css" />
<link
    href="${rc.contextPath}/resources/js/jqueryTableTreeView/jquery.treetable.theme.default.css"
    rel="stylesheet" type="text/css" />
<script
    src="${rc.contextPath}/resources/js/jqueryTableTreeView/jquery.treetable.js"></script>
<link
    href="${rc.contextPath}/resources/js/starRating/star-rating.min.css"
    rel="stylesheet" type="text/css" />
<script
    src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
<script>
    $("#itemTableTree").treetable({ });
    $(function(){
        if(${isExpired} == "1"){
            $("#dropdownMenu2").attr('disabled',true); 
        }
    })
</script>

<script>
    /**
     * 编辑栏目
     * @param chapterId
     */
    function editChapter(chapterId){
        art.dialog.open('/productItem/edit?itemId='+chapterId,{
            title: '编辑章节',
            width:400,
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
     * 删除章节
     * @param chapterId
     */
    function deleteItem(itemId,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/productItem/delete", { id:itemId},
                    function(data){
                    var u=JSON.parse(data);
                        if(u.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    }

    /**
     * 添加章节
     * @param productId
     * @param parentId
     */
    function addChapter(productId,parentId){
        art.dialog.open('/productItem/addChapter?productId='+productId+'&selectId='+parentId,{
            title: '添加章节',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(null != form){
	                chileloading();
	                form.submit();
                }else{
                	alert("无可添加资源！");
                }
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
    
    
    
    function addVideo(productId,itemId){
        art.dialog.open('/courseResource/chapterFrame2?resourceType=2&productId=${productId}&chapterId='+itemId,{
            title: '添加视频',
            width:800,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(null != form){
	                chileloading();
	                form.submit();
                }else{
                	alert("无可添加资源！");
                }
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
    
    function addAudio(productId,itemId){
        art.dialog.open('/courseResource/chapterFrame2?resourceType=7&productId=${productId}&chapterId='+itemId,{
            title: '添加音频',
            width:800,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(null != form){
	                chileloading();
	                form.submit();
                }else{
                	alert("无可添加资源！");
                }
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
    
    function addDoc(productId,itemId){
        art.dialog.open('/courseResource/chapterFrame2?resourceType=3&productId=${productId}&chapterId='+itemId,{
            title: '添加文档',
            width:800,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(null != form){
	                chileloading();
	                form.submit();
                }else{
                	alert("无可添加资源！");
                }
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

    function addHtml5(productId,itemId){
        art.dialog.open('/courseResource/chapterFrame2?resourceType=4&productId=${productId}&chapterId='+itemId,{
            title: '添加HTML5',
            width:800,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                if(null != form){
	                chileloading();
	                form.submit();
                }else{
                	alert("无可添加资源！");
                }
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
     * 添加资源,关联题库
     * @param productId
     * @param parentId
     */
    function addResource(productId,parentId){
        art.dialog.open('/productItem/addResource?productId='+productId+'&selectId='+parentId,{
            title: '添加资源',
            width:400,
            height:500,
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
     * 题库设置
     * @param productId
     */
    function questionBankSetting(productId){
        art.dialog.open('/product/questionSetting?productId='+productId,{
            title: '题库设置',
            width:580,
            height:380,
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

            }
        });
    }

    function addExamRule(productId){
        art.dialog.open('/examRule/addRule?productId='+productId,{
            title: '命题规则',
            width:1000,
            height:500,
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

            }
        });
    }

    function addRule(productId){
        art.dialog.open('/examRule/addRule?productId='+productId,{
            title: '添加蓝图',
            width:1500,
            height:500,
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

    function examRuleList(productId){
        art.dialog.open('/examRule/list?productId='+productId,{
            title: '蓝图列表',
            width:600,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {

            }
        });
    }

    /**
     * 关联资源
     * @param chapterId
     * @param productId
     */
    function resourceRelation(chapterId,productId){
        art.dialog.open('/productItem/resourceRelation?productId='+productId+"&chapterId="+chapterId,{
            title: '关联题库',
            width:1000,
            height:500,
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
                window.location.reload();
            }
        });
    }

    function chapterRuleList(productId,chapterId){
        art.dialog.open('/examRule/chapterRuleList?productId='+productId+"&chapterId="+chapterId,{
            title: '规则列表',
            width:600,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {

            }
        });
    }

    /**
     * 产品题库
     */
    function productQuestion(){
        art.dialog.open('/productQuestion/index?productId='+${productId},{
            title: '产品题库',
            width:1000,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {
            }
        });
    }

    /**
     * 产品题库
     */
    function paperAdd(){
        art.dialog.open('/examPaper/add?productId='+${productId},{
            title: '添加试卷',
            width:1000,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {
            }
        });
    }

    /**
     * 试卷列表
     */
    function paperList(){
        art.dialog.open('/examPaper/list?productId='+${productId},{
            title: '真题列表',
            width:1000,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {
            }
        });
    }
    /**
     * 期末测试列表页
     */
    function finalExamList(){
        art.dialog.open('/finalExam/list?productId='+${productId},{
            title: '真题列表',
            width:1000,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {
            }
        });
    }
    function productQuestionAdd(){
        art.dialog.open('/productQuestion/questionAdd?productId='+${productId},{
            title: '产品题库',
            width:1000,
            height:500,
            cancelVal: '关闭',
            cancel: true,
            close: function () {
            }
        });
    }

    function relationItem(id){
        art.dialog.open('/productItem/itemRelation?itemId='+id,{
            title: '关联',
            width:400,
            height:500,
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
     * 排序
     */
    function order(){
        var request = '';
        $(".orderInput").each(function(i){
            var value = $(this).val();
            var id = $(this).attr("id");
            var preValue = $(this).attr("preValue");
            if(value!=preValue){
                request = request + id + ":" + value + ";";
            }
        });
        if(request==''){
            alert("排序成功");
        } else {
            $.post("/productItem/order", { orders: request},
                    function(data){
                        alert("排序成功");
                        location.reload();
                    });
        }
    }

    /**
     * 权重检测
     */
    function checkWeight(){
        var flag = 0;
        var totalWeight = parseFloat(0.00);
        var request = '';
        var len = $(".weight").length
        $(".weight").each(function(i){
            var value = $(this).val();
            if(value == ''){
                flag += 1;
            }
        });
        if(flag == len){
            alert('未设置权重， 将按章平均分计算')
            $(".weight").each(function(i){
                var value = $(this).val();
                var id = $(this).attr("data-id");
                request = request + id + ":" + 0 + ";";
            });
            $.post("/productItem/resetWeight", 
                        { orders: request},
                        function(data){
//                        alert("章权重置成功");
                          location.reload();
                        }
                );
            return;
        }
        $(".weight").each(function(i){
            var value = $(this).val();
            if(value < 0 || value > 1){
                alert('检测失败，章权重应为大于0小于1的两位小数，请重新设置')
                $(this).val($(this).attr("preValue"))
                return;
            }
            totalWeight += parseFloat(value);
        });
        if(totalWeight.toFixed(2)==1.00 || totalWeight.toFixed(2) ==1){
            $(".weight").each(function(i){
                var value = $(this).val();
                var id = $(this).attr("data-id");
                var preValue = $(this).attr("preValue");
                if(value!=preValue){
                    request = request + id + ":" + value + ";";
                } 
            });
            if(request==''){
                alert("章权重没有做任何修改");
            } else {
                $.post("/productItem/resetWeight", 
                        { orders: request},
                        function(data){
                          alert("章权重设置成功");
                          location.reload();
                        }
                );
            }
            return;
        }else{
            alert('检测失败，章权重之和应为1，请重新设置')
            $(".weight").each(function(i){
                $(this).val($(this).attr("preValue"))
            });
            return;
        }
    }

    function addChapterExamRule(productId,chapterId){
        art.dialog.open('/examRule/addChapterRule?productId='+productId+'&chapterId='+chapterId,{
            title: '命题规则',
            width:1000,
            height:500,
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

            }
        });
    }

    $('.rating').rating({
        showClear:false,
        disabled:true
    });

    jQuery(document).ready(function($) {
        $('.itemName').simpletooltip({
            position: 'right',
            border_width: 2
        });
    });

 /**
     * 添加章节按课程
     * @param productId
     * @param parentId
     */
    function addChapterByCourse(productId,parentId){
        art.dialog.open('/productItem/addChapterByCourse?productId='+productId+'&selectId='+parentId,{
            title: '添加章节',
            width:400,
            height:500,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                 var childArr=new Array();
                 var parentArr=new Array();
                var checkboxArr=iframe.document.getElementsByTagName("input");
                for(var i=0;i<checkboxArr.length;i++) {
                    if(checkboxArr[i].checked==true){
                        if(checkboxArr[i].name!=""){
                            var obj=new Object();
                            obj.parentId=checkboxArr[i].name;
                            var fs=checkboxArr[i].id.split(',');
                            obj.id=fs[1];
                            childArr.push(obj);
                            }
                      }
                    if(checkboxArr[i].name==""){
                        var obj=new Object();
                        var fs=checkboxArr[i].id.split(',');
                        obj.id=fs[0];
                        parentArr.push(obj);
                        }
                        
                  }
                  chileloading();
                   var parentJson=JSON.stringify(parentArr);
                    var childJson=JSON.stringify(childArr);
                    var productId=iframe.document.getElementById('productId').value;
                    $.ajax({
                      url:"/productItem/addChapterByChoose",
                      type:"POST",
                      dataType: "json",
                      data:{parentJson:parentJson,childJson:childJson,productId:productId },
                      timeout:10000000,
                      dataType:"json",
                      success:function(data){
                          if(data.retCode == '0'){
                            childcloseloading();
                            alert("成功");
                            location.reload();
                          }else{
                              childcloseloading();
                              alert("失败");
                          }
                      },
                      error:function(){
                          childcloseloading();
                      }
                    });
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

</script>


</@htmlBody>
