"use strict"
String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    var result = reg.test(this);
    return result;
}

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
}

String.prototype.replaceAll = function(s1,s2) {
    return this.replace(new RegExp(s1,"gm"),s2);
}