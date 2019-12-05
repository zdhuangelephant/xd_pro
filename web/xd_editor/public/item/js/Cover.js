/**
 * 封面
 */
var pageList = [];
var hash;
var len = $(".back").length;
var pageTouchMap = {};
var currentReadId;
var scrollLock = false;
var scrollOffset = 150;
//$(document).scroll(function () {
//    if (scrollLock) {
//        return;
//    }
//    setTimeout(function () {
//        if (currentReadId != null) {
//            var currentDiv = $("#read-" + currentReadId);
//            $('html,body').animate({scrollTop: currentDiv.offset().top - scrollOffset}, 600);
//        }
//        scrollLock = false;
//    }, 2000);
//    scrollLock = true;
//});
//localStorage.dayOrNight = "day"

function mode(type){//切换白天/夜间模式
    localStorage.dayOrNight = type;
    if (localStorage.dayOrNight =='night'){//黑夜
        $(".back").css('background','#1e1e1e');
        $(".point").css('color','#646464');
        $(".point hr").css('border-top','1px solid #1e1e1e');
    }
    else if(localStorage.dayOrNight =='day'){//白天
        $(".back").css('background','#f5f5f5');
        $(".point").css('color','#4b4b4b');
        $(".point hr").css('border-top','1px solid #cdcdcd');
    }
}

$(document).ready(function(){
    if (localStorage.dayOrNight =='night'){//黑夜
        $(".back").css('background','#1e1e1e');
        $(".point").css('color','#646464');
    }
    else if(localStorage.dayOrNight =='day'){//白天
        $(".back").css('background','#f5f5f5');
        $(".point").css('color','#4b4b4b');
    }
})
function nextSentenceForAndroid(currentId) {
    var obj = {};
    obj.id = currentId;
    nextSentence(obj);
}
function nextSentence(currentId) {

    //当前id 取消class reading不高亮
    if (currentId && currentId.id) {
        currentReadId = currentId.id;
        $(".reading").each(function () {
            $(this).removeClass("reading");
        });
        //下一个id 增加 div
        var nextDiv = $("#read-" + currentId.id);
        nextDiv.addClass("reading");
        $('html,body').animate({scrollTop: nextDiv.offset().top - scrollOffset}, 600);
    }
    return "success";
}
function nextPage() {
    pageTouchMap[currentPageId].wipeLeft();
}
function getText(_select, div, hash) {
    if (!hash && typeof(hash) == "undefined") {
        hash = new HashMap();
    }
    var index = 0;
    _select.children().each(function () {
        var html = $(this).html();
        var text = $(this).text();
        if (html == text) {
            text = text.toString().trim();
            $(this).empty();
            text = text.replaceAll("[\\s]+", "$。");
            text = text.replaceAll("[。]+", "$。");
            text = text.replaceAll("[：]+", "：。");
            text = text.replaceAll("[\:]+", "[\:]。");
            text = text.split("。");
            for (var i = 0; i < text.length; i++) {
                text[i] = text[i].trim();
                if (text[i].length > 1) {
                    var id = uuid();
                    text[i] = text[i].replaceAll('[.]+[$]+$', '.');
                    text[i] = text[i].replaceAll('[。]*[$]+$', '。');
                    var div = $("<span id='read-" + id + "'>" + text[i] + "</span>");
                    $(this).append(div);
                    hash.put(id, {id: id, text: text[i]});
                }
            }
        } else {
            var match = html.match(/[^&nbsp;\<\>\/\\s]+(<br>)?$/g);
            if (match && match.length > 0) {
                match = match.toString().replaceAll('<br>', '').trim();
                var info = match;
                info = info.replaceAll("[\\s]+", "$。");
                info = info.replaceAll("[。]+", "$。");
                info = info.replaceAll("[：]+", "：。");
                info = info.replaceAll("[\:]+", "[\:]。");
                info = info.split("。");
                var _html = "";
                for (var i = 0; i < info.length; i++) {
                    var _match = info[i].trim();
                    if (_match.length > 1) {
                        _match = _match.replaceAll('[.]+[$]+$', '.');
                        _match = _match.replaceAll('[。]*[$]+$', '。');
                        var div = "<span>" + _match + "</span>";
                        _html += div;
                    }
                }
                $(this).html(html.replaceAll(match, _html));
            }
            getText($(this), _select.children()[index], hash);
            index++;
        }
    });
    //console.log(hash.values());
    return hash;
}
var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');


function uuid(len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        // rfc4122, version 4 form
        var r;

        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('');
};
/**
 * *********  操作实例  **************
 *    var map = new HashMap();
 *    map.put("key1","Value1");
 *    map.put("key2","Value2");
 *    map.put("key3","Value3");
 *    map.put("key4","Value4");
 *    map.put("key5","Value5");
 *    alert("size："+map.size()+" key1："+map.get("key1"));
 *    map.remove("key1");
 *    map.put("key3","newValue");
 *    var values = map.values();
 *    for(var i in values){
*		document.write(i+"："+values[i]+"   ");
*	}
 *    document.write("<br>");
 *    var keySet = map.keySet();
 *    for(var i in keySet){
*		document.write(i+"："+keySet[i]+"  ");
*	}
 *    alert(map.isEmpty());
 */
function HashMap() {
    //定义长度
    var length = 0;
    //创建一个对象
    var obj = new Object();

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function () {
        return length == 0;
    };

    /**
     * 判断对象中是否包含给定Key
     */
    this.containsKey = function (key) {
        return (key in obj);
    };

    /**
     * 判断对象中是否包含给定的Value
     */
    this.containsValue = function (value) {
        for (var key in obj) {
            if (obj[key] == value) {
                return true;
            }
        }
        return false;
    };

    /**
     *向map中添加数据
     */
    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            length++;
        }
        obj[key] = value;
    };

    /**
     * 根据给定的Key获得Value
     */
    this.get = function (key) {
        return this.containsKey(key) ? obj[key] : null;
    };

    /**
     * 根据给定的Key删除一个值
     */
    this.remove = function (key) {
        if (this.containsKey(key) && (delete obj[key])) {
            length--;
        }
    };

    /**
     * 获得Map中的所有Value
     */
    this.values = function () {
        var _values = new Array();
        for (var key in obj) {
            _values.push(obj[key]);
        }
        return _values;
    };

    /**
     * 获得Map中的所有Key
     */
    this.keySet = function () {
        var _keys = new Array();
        for (var key in obj) {
            _keys.push(key);
        }
        return _keys;
    };

    /**
     * 获得Map的长度
     */
    this.size = function () {
        return length;
    };

    /**
     * 清空Map
     */
    this.clear = function () {
        length = 0;
        obj = new Object();
    };
}
