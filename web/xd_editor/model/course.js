"use strict"

function create(row){
    function chaprter(){
        this.id = null;
        this.name = null;
    }
    if(row) {
        var model = new chaprter();
        model.id = row['id'];
        model.name = row['name'];
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