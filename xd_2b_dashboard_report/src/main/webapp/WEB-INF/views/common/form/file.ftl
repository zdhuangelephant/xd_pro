
<#macro fileUpload >
    <script>
        function fileUpload(inputId,scope,maxFileSize,mimeTypes){
        	fileUploadCallBack(function(url){
        		$("#"+inputId).val(url);
        	},scope,maxFileSize,mimeTypes);
        }
        
        //新增
		function fileUploadCallBack(callback,scope,maxFileSize,mimeTypes) {
		    $.fn.modalOpen({
		        id: "Form",
		        title: '上传',
		        url: '/upload/file?scope='+scope+'&mimeTypes='+mimeTypes+'&maxFileSize='+maxFileSize,
		        width: "470px",
		        height: "380px",
		        callBack: function (iframeId) {
		            //top.frames[iframeId].AcceptClick();
                    if(!isEmpty(callback)){
		           	 	var url = $(window.frames[0].document).find('#fileUrl').val();
		           	 	if(isEmpty(url)){
		           	 		layer.msg('请选择文件！', {
								time : 2000, // 2s后自动关闭
								icon : 1
							});
							return;
		           	 	}
                    	callback(url);
                    }
                    $(".layui-layer-btn1").click();
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
		        btn: ['确认', '关闭'],
		        btnclass: ['btn btn-primary', 'btn btn-danger'],
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
    </script>
</#macro>

<#macro file scope maxFileSize='200' mimeTypes=''>
<input type="hidden" id="domain" value="http://resource.51xiaodou.com/">
<input type="hidden" id="uptoken_url" value="/qiniu/uptoken?scope=picture">
<input type="hidden" id="fileUrl" value="">
<input type="hidden" id="savepath" value="user/excel/">
<style>
    .box{
        border: 3px dashed #e6e6e6;
        width: 430px;
        height: 350px;
        margin: auto;
    }
    #buttonContainer{
        text-align: center;
        margin-top: 120px;
    }
    #buttonContainer p {
        height: 30px;
        line-height: 30px;
    }
    #buttonContainer .tip{
        color: #808080;
    }
    #buttonContainer .errTip{
        color: red;
    }
    #fileBox{
        display: none;
        margin-top: 50px;
    }
    #fileBox img{
        width: 132px;
        height: 164px;
    }
    .errTip{
        text-align: center;
        color: red;
        display: none;
    }
</style>

<div class="box">

    <div id="buttonContainer">
        <button type="button" class="btn btn-primary" id="pickfiles" style="border: 0px; border-radius: 4px;">
            <i class="glyphicon glyphicon-plus"></i>
            <sapn>选择文件</sapn>
        </button>
        <p class="errTip"></p>
        <p class="tip">提示:文件不能大于${maxFileSize}MB</p>
        <p><#if mimeTypes!=''>${mimeTypes}</#if></p>
    </div>

    <div id="fileBox">
        <p style="text-align: center">
            <img id="fileImg" src="/resources/image/file.png">
        </p>
        <div class="progress" style="width: 200px; margin: auto;">
            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                <span></span>
            </div>
        </div>
        <p class="fileInfo" style="text-align: center; display: none;">
            <span></span><a onclick="reset()" style="cursor: pointer;">[删除]</a>
        </p>
    </div>

</div>


<script>

    function reset(){
        $(".progress-bar").attr("aria-valuenow",0).css('width', '0%');
        $(".progress-bar").find("span").text('');
        $(".progress").show();
        $(".fileInfo").find("span").text('');
        $("#fileImg").attr("src","/resources/image/file.png");
        $(".fileInfo").hide();
        $("#buttonContainer").show();
        $("#fileBox").hide();
        $("#fileUrl").val("");
    }

    function isImage(url) {
        var res, suffix = "";
        var imageSuffixes = ["png", "jpg", "jpeg", "gif", "bmp"];
        var suffixMatch = /\.([a-zA-Z0-9]+)(\?|\@|$)/;

        if (!url || !suffixMatch.test(url)) {
            return false;
        }
        res = suffixMatch.exec(url);
        suffix = res[1].toLowerCase();
        for (var i = 0, l = imageSuffixes.length; i < l; i++) {
            if (suffix === imageSuffixes[i]) {
                return true;
            }
        }
        return false;
    }

    $(function() {
        var uploader = Qiniu.uploader({
            runtimes: 'html5,flash,html4',
            browse_button: 'pickfiles',
            container: 'buttonContainer',
            drop_element: 'buttonContainer',
            max_file_size: '${maxFileSize}mb',
            flash_swf_url: 'js/plupload/Moxie.swf',
            dragdrop: true,
            filters:{
                mime_types : [
                        <#if mimeTypes!=''>{ title : "文件", extensions : "${mimeTypes}" }</#if>
                ]
            },
            multi_selection:false,
            chunk_size: '4mb',
            uptoken_url: $('#uptoken_url').val(),
            domain: $('#domain').val(),
            auto_start: true,
            init: {
                'FilesAdded': function(up, files) {
                    $("#buttonContainer").hide();
                    $("#fileBox").show();
                },
                'UploadProgress': function(up, file) {
                    var uploaded = file.loaded;
                    var size = plupload.formatSize(uploaded).toUpperCase();
                    var formatSpeed = plupload.formatSize(up.total.bytesPerSec).toUpperCase();
                    var percentage = file.percent;
                    if (file.status !== plupload.DONE && percentage === 100) {
                        percentage = 99;
                    }
                    $(".progress-bar").attr("aria-valuenow",percentage).css('width', percentage + '%');
                    $(".progress-bar").find("span").text(percentage + '%');
                },
                'UploadComplete': function() {
                },
                'FileUploaded': function(up, file, info) {
                    $(".fileInfo").find("span").text(file.name);
                    $(".progress").hide();
                    $(".fileInfo").show();
                    var res = $.parseJSON(info);
                    var url;
                    if (res.url) {
                        url = res.url;
                    } else {
                        var domain = up.getOption('domain');
                        url = domain + encodeURI(res.key);
                    }
                    if(isImage(url)){
                        $("#fileImg").attr("src","${IMG_PATH}loading.gif");
                        $("#fileImg").attr("src",url);
                    }
                    $("#fileUrl").val(url);
                },
                'Error': function(up, err, errTip) {
                	errTip = "最大可上传12MB文件！";
                    $("#fileBox").hide();
                    $("#buttonContainer").show();
                    $(".errTip").text(errTip).show();
                    $(".tip").hide();
                },
                'Key': function(up, file) {
	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
	                // 该配置必须要在unique_names: false，save_key: false时才生效 (默认false)
	                var ext = Qiniu.getFileExtension(file.name);
	                var folder = $("#savepath").val();
	                if(folder === '' || typeof(folder) === 'undefined'){
	                    folder='default/';
	                }
	                var key = folder+file.id+"."+ext;
	                // do something with key here
	                return key
	            }
                
            }
        });
    });
</script>

    <#if UPLOAD_INIT??>
    <#else>
    <script type="text/javascript" src="${baseJsOP}/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${baseJsOP}/plupload/i18n/zh_CN.js"></script>
    <script type="text/javascript" src="${baseJsOP}/qiniu/qiniu.min.js?123"></script>
    <#assign EDITOR_INIT="true" />
    </#if>


</#macro>
