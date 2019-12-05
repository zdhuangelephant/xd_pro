<#macro files scope>

<style>
    .fileProgress{
        height: 40px;
        line-height: 40px;
        border-top: 1px solid #e4e5e9;
    }
    .fileProgress table{
        width: 100%;
        position: absolute;
    }
    .fileProgress table tr{
        height: 40px;
        line-height: 40px;
    }
    .fileProgress table tr td{
        font-weight: bold;
    }
    .progress{
        margin-bottom: 0px;
        height:40px;
        line-height: 40px;
        background-color: #f5f5f5;
    }
    .progress .progress-bar{
        height: 39px;
        line-height: 39px;
        background-color: #abd7fb;
        min-width: 0px;
    }
</style>
<input type="hidden" id="domain" value="http://resource.51xiaodou.com/">
<input type="hidden" id="uptoken_url" value="/qiniu/uptoken?scope=picture">

<p id="container">
    <button type="button" class="btn btn-primary" id="pickfiles" style="border: 0px; border-radius: 4px;">
        <i class="glyphicon glyphicon-plus"></i>
        <sapn>选择文件</sapn>
    </button>
    <span style="margin-left: 20px;">文件类型，最大文件</span>
</p>



<div class="alert alert-success" id="success" style="border-radius: 4px;margin-top: 10px; padding: 10px; display: none;" role="alert">
    队列全部文件处理完毕
</div>

<div id="fsUploadProgress" class="progressContainer">
</div>


<script>
    $(function() {
        var uploader = Qiniu.uploader({
            runtimes: 'html5,flash,html4',
            browse_button: 'pickfiles',
            container: 'container',
            drop_element: 'container',
            max_file_size: '200mb',
            flash_swf_url: 'js/plupload/Moxie.swf',
            dragdrop: true,
            chunk_size: '4mb',
            uptoken_url: $('#uptoken_url').val(),
            domain: $('#domain').val(),
            auto_start: true,
            init: {
                'FilesAdded': function(up, files) {
                    //队列处理成功隐藏
                    $('#success').hide();
                    plupload.each(files, function(file) {
                        var progress = new FileProgress(file, 'fsUploadProgress');
                        progress.setStatus("等待...");
                    });
                },
                'UploadProgress': function(up, file) {
                    var progress = new FileProgress(file, 'fsUploadProgress');
                    progress.setProgress(file.percent + "%", up.total.bytesPerSec);
                    progress.setStatus("上传中");
                },
                'UploadComplete': function() {
                    $('#success').show();
                },
                'FileUploaded': function(up, file, info) {
                    var progress = new FileProgress(file, 'fsUploadProgress');
                    progress.setComplete(up, info);
                },
                'Error': function(up, err, errTip) {
                    var progress = new FileProgress(err.file, 'fsUploadProgress');
                    progress.setStatus(errTip);
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
    <script type="text/javascript" src="${JS_PATH}plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${JS_PATH}plupload/i18n/zh_CN.js"></script>
    <script type="text/javascript" src="${JS_PATH}qiniu/ui.js"></script>
    <script type="text/javascript" src="${JS_PATH}qiniu/qiniu.min.js"></script>
        <#assign EDITOR_INIT="true" />
    </#if>


</#macro>
