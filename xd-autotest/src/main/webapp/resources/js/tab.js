$(function () {
	
    //获取样式方法
    function setCss (target, css, num) {
        $(target).css(css, ($(window).height() + num) + 'px');
    }
    $(window).load(function () {
        /*整个body #jsoneditor*/
        setCss(".body-wrap", "height", 0);
        /*tab 内容*/
        setCss(".tab-content-class", "min-height", -510);
        /*左整体*/
        setCss(".builder-content-l", "height", -56);
        setCss(".builder-content-r", "height", -56);
        /*左内容*/
        setCss(".l-con-con", "min-height", -510);
        setCss(".l-con-con .jsoneditor-box", "min-height", -550);
        setCss("#share-collections-content", "min-height", -509);
        setCss(".l-con-con-collection", "height", -174);
        /*ifream 内容*/
        setCss(".tabs-panels", "height", -130);
        /*iframe 获取高度*/
        setCss("#iframeFresh", "min-height", -120);
    });
    $(window).resize(function () {
        setCss(".body-wrap", "height", 0);
        /*tab 内容*/
        setCss(".tab-content-class", "min-height", -510);
        /*左整体*/
        setCss(".builder-content-l", "height", -56);
        setCss(".builder-content-r", "height", -56);
        /*左内容*/
        setCss(".l-con-con-collection", "height", -174);
        setCss(".l-con-con", "min-height", -510);
        setCss(".l-con-con .jsoneditor-box", "min-height", -550);
        /*ifream 内容*/
         setCss(".tabs-panels", "height", -130);
         /*iframe 获取高度*/
         setCss("#iframeFresh", "min-height", -120);
    });
    
    function changeTab (target, active, content) {
        $(target).click(function () {
            var i = $(this).index();//下标第一种写法
            $(this).addClass(active).siblings().removeClass(active);
            $(content).eq(i).show().siblings().hide();
        });
    };

    //top tab
    changeTab('#l-top-tab a', 'h-active', '#l-content-wrap .l-content');
    
    //runner tab header
    changeTab('.run-tab-header a', 'a-active', '.run-tab');

    //shadow close
    $('.close, .cancel').click(function () {
        $('#save-shadow-box').hide();
        $('#collection-shadow-box').hide();
        $('#current-shadow-box').hide();
        $('#generate-version').hide();
        $('#update-docReq').hide();
        $('#update-doc').hide();
        $('#case-shadow-box').hide();
        $('#import-shadow-box').hide();
        $('#eidtKeyValue-box').hide();
    });
    
    //manage presets shdow close
    $('.pre-cancel').click(function () {
    	$('#add-list-box').show();
    	$('#keyValue-box').hide();
    });
    $('.editPre-cancel').click(function () {  
    	$('#add-list-box').show();
    	$('#keyValue-box').hide();
    	$('#eidtKeyValue-box').hide();
    });
    
    $('.pre-close').click(function () {
    	$('#manage-presets-id').hide();
    	$('#add-list-box').show();
    	$('#keyValue-box').hide();
    	$('#eidtKeyValue-box').hide();
    })
    
    
    $('.add-collect').click(function () {
        $('#collection-shadow-box').show();
    });

    $('#currentId').click(function () {
        $('#current-shadow-box').show();
    });
    $('#preAddButton').click(function () {
    	$('#add-list-box').hide();
    	$('#keyValue-box').show();
    })
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){  
      	  window.opener=null;
      	  window.open('','_self');
      	  window.close(); 
        }            
    };
});

function editTab(event,obj) {
	    //support tab on textarea
	    if (event.keyCode == 9) { //tab was pressed
		var position=obj.selectionStart+2;//此处我用了两个空格表示缩进，其实无所谓几个，只要和下面保持一致就好了。
		obj.value=obj.value.substr(0,obj.selectionStart)+'  '+obj.value.substr(obj.selectionStart);
		obj.selectionStart=position;
		obj.selectionEnd=position;
		obj.focus();
		event.preventDefault();	
	  }
	}
