"use strict"
var config = require('../config');

function createNode(id, name, isParent, doCheck, status) {
    this.id = id;
    this.name = name;
    this.open = false;
    this.isParent = isParent;
    this.parent = null;
    this.isRoot = false;
    this.level = 0;
    this.prefix = '';
    this.url = null;
    this.srcUrl = null;
    this.target = "_blank";
    this.click = null;
    this.childrenCount = 0;
    this.children = [];
    this.unusedChildrenResourceState = [];
    this.doCheck = doCheck;
    this.checked = null;
    this.resourceState = null;
    this.status = status;
    this.addChild = function (node) {
        node.parent = this;
        if (parseInt(node.status) == config.show_status.INUSE) {
            this.children.push(node);
        }else{
            this.unusedChildrenResourceState.push(node.resourceState);
        }
        this.childrenCount++;
        changeResourceState(this);
    };
    this.removeParent = function () {
        this.parent = null;
        if (this.children) {
            for (var i in this.children)
                this.children[i].removeParent();
        }
    }
    function changeResourceState(parent) {
        if (parent.childrenCount > 0) {
            var total = 0;
            var child_resource_state = {SYNC: 0, UNSYNC: 0, NOVERSION: 0, EMPTY: 0};
            for (var i in parent.children) {
                total++;
                if (parent.children[i].resourceState == null || typeof(parent.children[i].resourceState) == 'undefined')
                    child_resource_state.EMPTY++;
                else
                    child_resource_state[parent.children[i].resourceState]++;
            }
            for (var j in parent.unusedChildrenResourceState) {
                total++;
                if (parent.unusedChildrenResourceState[j] == null || typeof(parent.unusedChildrenResourceState[j]) == 'undefined')
                    child_resource_state.EMPTY++;
                else
                    child_resource_state[parent.unusedChildrenResourceState[j]]++;
            }
            for (var k in child_resource_state) {
                if (total == child_resource_state[k])
                    parent.resourceState = k;
                else if (child_resource_state[k] > 0)
                    parent.resourceState = config.resource_state.UNSYNC;
            }
        }
        if (parent.parent)
            changeResourceState(parent.parent);
    }
}

exports.createNode = function (id, name) {
    return new createNode(id, name, false, true, null);
}

exports.createParent = function (id, name) {
    return new createNode(id, name, true, false, config.show_status.INUSE);
}