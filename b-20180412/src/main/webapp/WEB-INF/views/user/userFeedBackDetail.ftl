<#include "/common/layout.ftl" />
<@htmlHead title="首页">
    <style type="text/css"> 
    .test-textarea {
        width: 70%;
        min-height: 26px;
        line-height: 20px;
        /* max-height: 150px;*/
        margin-left: auto;
        margin-right: auto;
        padding: 3px;
        outline: 0;
        border: 1px solid #ccc;
        font-size: 12px;
        word-wrap: break-word;
        overflow-x: hidden;
        overflow-y: auto;
        -webkit-user-modify: read-write-plaintext-only;
        border-radius: 4px;
        }
    </style> 

</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/userFeedBack/doEdit" >
   <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 主键 </label>
        <div class="col-xs-9 col-xs-9">
            <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="number" name="number" value="${userFeedBack.id}" placeholder=""/>
        </div>
    </div>
     <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3">  用户ID </label>
        <div class="col-xs-9 col-xs-9">
            <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="number" name="number" value="${userFeedBack.userId}" placeholder=""/>
        </div>
    </div>
     <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> APP版本 </label>
        <div class="col-xs-9 col-xs-9">
            <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="number" name="number" value="${userFeedBack.appVersion}" placeholder=""/>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 电话号码 </label>
        <div class="col-xs-9 col-xs-9">
            <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="number" name="number" value="${userFeedBack.number}" placeholder=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 内容 </label>
        <div class="col-sm-9 col-xs-9">
            <textarea  disabled="disabled" name="content" class="form-control" style="width: 100%;overflow-y:visible;height: 160px;">${userFeedBack.content}</textarea>
        </div>
    </div>
    
     <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 系统版本 </label>
        <div class="col-sm-9 col-xs-9">
         <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="osVersion" name="osVersion" value="${userFeedBack.osVersion}" placeholder=""/>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 设备类型 </label>
        <div class="col-sm-9 col-xs-9">
        <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="deviceType" name="deviceType" value="${userFeedBack.deviceType}" placeholder=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 错误类别 </label>
        <div class="col-sm-9 col-xs-9">
         <textarea  disabled="disabled" name="categoryDescs" class="form-control" style="width: 100%;overflow-y:visible;">${userFeedBack.categoryDescs}</textarea>
        </div>
    </div>
	<div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 反馈图片 </label>
        <div class="col-xs-9 col-xs-9">
        	 <#list userFeedBack.tmpImageUrl as igs>
        	<img style="width: 100px; height: auto;" class="portrait" src="${igs}" alt="正文图片">
        	</#list>
        </div>
    </div>		
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 创建时间 </label>
        <div class="col-sm-9 col-xs-9">
        <input disabled="disabled" type="text" datatype="s" sucmsg="haha" id="createTime" name="createTime" value="${userFeedBack.createTime}" placeholder=""/>
        </div>
    </div>
    
    <input type="hidden" name="id" value="${userFeedBack.id}" />
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 处理状态 </label>
        <div class="col-xs-9 col-xs-9">
          <select name="handleStatus" style="width:159px" onblur="saveStatus()">
            <option value="1"<#if userFeedBack.handleStatus==1> selected="selected"</#if>>未处理</option>
            <option value="2"<#if userFeedBack.handleStatus==2> selected="selected"</#if>>待跟进</option>
            <option value="3"<#if userFeedBack.handleStatus==3> selected="selected"</#if>>已处理</option>
            <option value="4"<#if userFeedBack.handleStatus==4> selected="selected"</#if>>已关闭</option>
          </select>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 处理记录 </label>
        <div class="col-sm-9 col-xs-9">
            <textarea name="handleNote" class="form-control" style="width: 100%;overflow-y:visible;height: 160px;">${userFeedBack.handleNote}</textarea>
        </div>
    </div>


    
</form>

<script>
    $("#editForm").Validform({ 
        tiptype: 2,
        postonce: true
    });
    
    $(function () {
      // 为每一个textarea绑定事件使其高度自适应
      $.each($("textarea"), function(i, n){
          autoTextarea($(n)[0]);
      });
    })
      /**
       * 文本框根据输入内容自适应高度
       * {HTMLElement}   输入框元素
       * {Number}        设置光标与输入框保持的距离(默认0)
       * {Number}        设置最大高度(可选)
       */
      var autoTextarea = function (elem, extra, maxHeight) {
          extra = extra || 0;
          var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
          isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
              addEvent = function (type, callback) {
                  elem.addEventListener ?
                      elem.addEventListener(type, callback, false) :
                      elem.attachEvent('on' + type, callback);
              },
              getStyle = elem.currentStyle ? 
              function (name) {
                  var val = elem.currentStyle[name];
                  if (name === 'height' && val.search(/px/i) !== 1) {
                      var rect = elem.getBoundingClientRect();
                      return rect.bottom - rect.top -
                             parseFloat(getStyle('paddingTop')) -
                             parseFloat(getStyle('paddingBottom')) + 'px';       
                  };
                  return val;
              } : function (name) {
                  return getComputedStyle(elem, null)[name];
              },
          minHeight = parseFloat(getStyle('height'));
          elem.style.resize = 'both';//如果不希望使用者可以自由的伸展textarea的高宽可以设置其他值
    
          var change = function () {
              var scrollTop, height,
                  padding = 0,
                  style = elem.style;
    
              if (elem._length === elem.value.length) return;
              elem._length = elem.value.length;
    
              if (!isFirefox && !isOpera) {
                  padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
              };
              scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    
              elem.style.height = minHeight + 'px';
              if (elem.scrollHeight > minHeight) {
                  if (maxHeight && elem.scrollHeight > maxHeight) {
                      height = maxHeight - padding;
                      style.overflowY = 'auto';
                  } else {
                      height = elem.scrollHeight - padding;
                      style.overflowY = 'hidden';
                  };
                  style.height = height + extra + 'px';
                  scrollTop += parseInt(style.height) - elem.currHeight;
                  document.body.scrollTop = scrollTop;
                  document.documentElement.scrollTop = scrollTop;
                  elem.currHeight = parseInt(style.height);
              };
          };
    
          addEvent('propertychange', change);
          addEvent('input', change);
          addEvent('focus', change);
          change();
      };
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
