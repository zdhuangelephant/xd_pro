
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
                    $("#fileBox").hide();
                    $("#buttonContainer").show();
                    $(".errTip").text(errTip).show();
                    $(".tip").hide();
                },
                'Key': function(up, file) {
	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
	                // 该配置必须要在unique_names: false，save_key: false时才生效 (默认false)
	                alert("key"+file.name);
	                var ext = Qiniu.getFileExtension(file.name);
	                var folder = $("#savepath").val();
	                if(folder === '' || typeof(folder) === 'undefined'){
	                    folder='default/';
	                }
	                var key = folder+file.id+"."+ext;
	                // do something with key here
	                return key;
	            }
                
            }
        });
    });