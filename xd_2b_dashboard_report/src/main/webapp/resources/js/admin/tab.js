/**
 * Created by zyc on 2017/4/7.
 */
window.onload = function () {
    var oBox = document.getElementById('box'),
        oTabox = document.getElementById('tab-box'),
        aBtn = oTabox.getElementsByTagName('a'),
        aDiv = oBox.getElementsByTagName('div');

    for (var i = 0; i < aBtn.length; i ++) {
        aBtn[i].index = i;
        aBtn[i].onclick = function () {
            for (var i = 0; i < aBtn.length; i ++) {
                aBtn[i].className = '';
                aDiv[i].style.display = 'none';
            }
            this.className = 'active';
            aDiv[this.index].style.display = 'block';
        }
    }
};