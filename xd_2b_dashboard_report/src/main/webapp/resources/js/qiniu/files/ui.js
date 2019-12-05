var isImage = function(url) {
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
};

/*global plupload */
/*global qiniu */
function FileProgress(file, targetID) {
    this.fileProgressID = file.id;
    this.file = file;
    this.fileProgressWrapper = $('#' + this.fileProgressID);

    if (!this.fileProgressWrapper.length) {
        var fileSize = plupload.formatSize(file.size).toUpperCase();
        var isImg = isImage(file.name);
        var icon = '';
        if(isImg){
            icon = '<span style="padding-right: 5px; padding-left: 5px;" class="glyphicon glyphicon-picture" aria-hidden="true"></span>';
        } else {
            icon = '<span style="padding-right: 5px; padding-left: 5px;" class="glyphicon glyphicon-file" aria-hidden="true"></span>';
        }
        var fileProgress = '<div class="fileProgress" id="'+this.fileProgressID+'">'+
            '<div class="progress">'+
            '<table>'+
            '    <tr>'+
            '        <td style="width: 40%">' +
            icon+file.name+'</td>'+
            '        <td style="width: 20%">'+fileSize+'</td>'+
            '        <td style="width: 20%" class="jindu"></td>'+
            '        <td style="width: 20%" class="status"></td>'+
            '    </tr>'+
            '</table>'+
            '<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">'+
            '<span class="sr-only"></span>'+
            '</div>'+
        '</div>'+
        '</div>';
        $('#' + targetID).append(fileProgress);
    } else {
        this.reset();
    }
    this.setTimer(null);
}

FileProgress.prototype.setTimer = function(timer) {
    this.fileProgressWrapper.FP_TIMER = timer;
};

FileProgress.prototype.getTimer = function(timer) {
    return this.fileProgressWrapper.FP_TIMER || null;
};

FileProgress.prototype.reset = function() {
    this.appear();
};

FileProgress.prototype.setProgress = function(percentage, speed) {
    var file = this.file;
    var uploaded = file.loaded;
    var size = plupload.formatSize(uploaded).toUpperCase();
    var formatSpeed = plupload.formatSize(speed).toUpperCase();
    if (file.status !== plupload.DONE && percentage === 100) {
        percentage = 99;
    }
    //显示
    $("#"+file.id).find(".jindu").text(percentage+"("+formatSpeed + "/S)");
    percentage = parseInt(percentage, 10);
    //进度条
    $("#"+file.id).find(".progress-bar").attr('aria-valuenow',percentage).css('width', percentage + '%');
    this.appear();
};

FileProgress.prototype.setComplete = function(up, info) {
    var res = $.parseJSON(info);
    var url;
    alert(info);
};

FileProgress.prototype.setStatus = function(status, isUploading) {
    if (!isUploading) {
        var file = this.file;
        $("#"+file.id).find(".status").text(status);
    }
};


FileProgress.prototype.appear = function() {
};
