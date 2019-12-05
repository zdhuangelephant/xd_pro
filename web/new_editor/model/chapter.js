"use strict"

function create(row){
    function chaprter(){
        this.id = null;
        this.name = null;
        this.prefix = null;
        this.level = null;
        this.status = null;
    }
    if(row) {
        var model = new chaprter();
        model.id = row['id'];
        model.name = row['name'];
        model.prefix = row['prefix'];
        model.level = row['level'];
        model.status = row['status'];
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
exports.createHash = function(results){
    if(results) {
        var hash = {};
        for ( var row in results){
            var chapter = create(results[row]);
            var list = hash[chapter.prefix];
            if(!list || typeof(list) == 'undefined' || list =='null'){
                list = [];
                hash[chapter.prefix] = list;
            }
            list.push(chapter);
        }
        return hash;
    }
    throw new Error("数据错误！");
}