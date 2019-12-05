function FileUpload(selecter, uploadUrl, setting) {
    var btnCust = '<button type="button" class="btn btn-default" title="Add picture tags" ' +
        'onclick="alert(\'Call your custom code here.\')">' +
        '<i class="glyphicon glyphicon-tag"></i>' +
        '</button>';
    if (!selecter)return null;
    if (!uploadUrl)
        uploadUrl = "#";
    var DEFAULT_SETTING = {
        language: 'zh', //设置语言
        uploadUrl: uploadUrl, //上传的地址
        enctype: 'multipart/form-data',
        uploadAsync: true,
        showPreview: true,
        showRemove: false,//是否显示删除按钮
        showCaption: true,//是否显示输入框
        showCancel: true,
        dropZoneEnabled: false,
        browseClass: "btn btn-primary", //按钮样式
        allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        initialPreview: [],
        initialPreviewAsData: true,
        initialPreviewConfig: [],
        fileActionSettings: {
            showZoom: false, //是否显示预览
            showDrag: true
        },
        initialPreviewShowDelete: false,
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 3,
        initialCaption: "请选择图片...",
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    };
    if (!setting)
        setting = DEFAULT_SETTING;

    function initPlugin0() {
        $(selecter).fileinput(setting);
        return this;
    };
    function initPreview0(image_list) {
        if (image_list && image_list.length > 0) {
            for (var i in image_list) {
                setting.initialPreview.push(image_list[i].src);
                var previewConfig = {caption: image_list[i].name, width: "120px"};
                setting.initialPreviewConfig.push(previewConfig);
            }
        }
    };
    this.initPlugin = initPlugin0;
    this.initPreview = initPreview0;
    this.initPluginWithPreview = function (image_list) {
        initPreview0(image_list);
        initPlugin0();
    };
};