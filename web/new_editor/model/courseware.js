"use strict"

function create(row){
    function courseware(){
        this.id = null;
        this.name = null;
        this.prefix = null;
        this.filename = null;
        this.dfilename = null;
        this.show_url = null;
        this.edit_url = null;
        this.download_url = null;
    }
    if(row) {
        var model = new courseware();
        model.id = row['id'];
        model.name = row['name'];
        model.prefix = row['prefix'];
        model.filename = row['filename'];
        model.dfilename = row['dfilename'];
        model.show_url = row['show_url'];
        model.edit_url = row['edit_url'];
        model.download_url = row['download_url'];
        model.orders = row['orders'];
        return model;
    }
    throw new Error("数据错误！");
}

exports.createOne = create;
exports.createList = function(results){
    if(results) {
        var list = [];
        for ( var row in results){
            list.push(create(results[row]));
        }
        return list;
    }
    throw new Error("数据错误！");
}