"use strict"

function createNode(id, name, isParent, doCheck){
    this.id = id;
    this.name = name;
    this.open = false;
    this.isParent = isParent;
    this.isRoot = false;
    this.level = 0;
    this.prefix = '';
    this.url = null;
    this.srcUrl = null;
    this.target = "_blank";
    this.click = null;
    this.children = [];
    this.doCheck = doCheck;
    this.checked = null;
    this.addChild = function(node){
        this.children.push(node);
    }
}

exports.createNode = function(id, name){
    return new createNode(id,name,false,true);
}

exports.createParent = function(id, name){
    return new createNode(id,name,true,false);
}