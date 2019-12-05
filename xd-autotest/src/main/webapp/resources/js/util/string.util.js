/**
 * Created by zhaodan on 2017/8/31
 */
(function (win) {
    var version = "1.0.0",
        stringUtil = function () {
            "use strict"
            var StringUtil = {
            	v: version,
                constructor: "StringUtil",
                isBlank: function (str) {
                    return str == null || typeof (str) == 'undefined' || str.constructor != String || str.length == 0;
                },

                startWith: function (srcStr, str) {
                    var reg = new RegExp("^" + str);
                    var result = reg.test(srcStr);
                    return result;
                },

                endWith: function (srcStr, str) {
                    var reg = new RegExp(str + "$");
                    return reg.test(srcStr);
                },
                
                contain: function (srcStr, str) {
                	var reg = new RegExp(str);
                    return reg.test(srcStr);
                },

                replaceAll: function (srcStr, s1, s2) {
                    return srcStr.replace(new RegExp(s1, "gm"), s2);
                },

                format: function (srcStr, args) {
                    var result = srcStr;
                    if (arguments.length > 0) {
                        if (arguments.length == 1 && typeof (args) == "object") {
                            for (var key in args) {
                                if (args[key] != undefined) {
                                    var reg = new RegExp("({" + key + "})", "g");
                                    result = result.replace(reg, args[key]);
                                }
                            }
                        }
                        else {
                            for (var i = 0; i < arguments.length; i++) {
                                if (arguments[i] != undefined) {
                                    var reg = new RegExp("({)" + i + "(})", "g");
                                    result = result.replace(reg, arguments[i]);
                                }
                            }
                        }
                    }
                    return result;
                }
            };
            return StringUtil;
        }
    win.StringUtil = win.su = win.$su = new stringUtil();
})(window);