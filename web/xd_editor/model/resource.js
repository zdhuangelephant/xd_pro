"use strict"

function create(fileUrl, url) {
    function resource() {
        this.fileUrl = null;
        this.url = null;
    }

    if (fileUrl && url) {
        var model = new resource();
        model.fileUrl = fileUrl;
        model.url = url;
        return model;
    }
    throw new Error("数据错误！");
}

exports.createOne = create;