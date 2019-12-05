/**
 * Created by Administrator on 2015/8/24.
 */
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}