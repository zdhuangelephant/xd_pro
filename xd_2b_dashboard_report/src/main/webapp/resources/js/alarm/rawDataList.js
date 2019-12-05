$(function(){
	$("#tab_20213", parent.document).addClass("active");
});

//新增
function modalOpen(alarmId) {
    $.fn.modalOpen({
        id: "Form",
        title: '浏览全部照片',
        url: 'slide?alarmId='+alarmId,
        width: "750px",
        height: "550px",
        callBack: function (iframeId) {
            top.frames[iframeId].AcceptClick();
        }
    });
};

$.fn.modalOpen = function (options) {
    var defaults = {
        id: null,
        title: '系统窗口',
        width: "100px",
        height: "100px",
        url: '',
        shade: 0.3,
        //btn: ['确认', '关闭'],
        //btnclass: ['btn btn-primary', 'btn btn-danger'],
        callBack: null
    };
    var options = $.extend(defaults, options);
    var _width = top.$(window).width() > parseInt(options.width.replace('px', '')) ? options.width : top.$(window).width() + 'px';
    var _height = top.$(window).height() > parseInt(options.height.replace('px', '')) ? options.height : top.$(window).height() + 'px';
    layer.open({
        id: options.id,
        type: 2,
        shade: options.shade,
        title: options.title,
        fix: false,
        area: [_width, _height],
        content: options.url,
        btn: options.btn,
        btnclass: options.btnclass,
        yes: function () {
            options.callBack(options.id);
        }, cancel: function () {
            return true;
        }
    });
};