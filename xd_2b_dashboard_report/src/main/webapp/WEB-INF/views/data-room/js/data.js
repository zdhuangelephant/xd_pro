
var timestamp = Date.parse(new Date());
num = (timestamp / 1000).toString().substring(2, 10);

var oTime = document.getElementById('time');
var oCounter = document.getElementById('counter');
var numArr = num.toString().split('');

for (var i = 0; i < numArr.length; i++) {
    var newImg = document.createElement('img');
    newImg.src = 'images/' + numArr[i] + '.png';
    oCounter.appendChild(newImg);
}

//counter
function startCount() {
    var timestamp = Date.parse(new Date());
    num = (timestamp / 1000).toString().substring(2, 10);
    var timeRandom = parseInt(Math.random() * 5) * 1000;
    var oImg = oCounter.getElementsByTagName('img');
    /*var newNum = Number(num) + timeRandom;
    alert(newNum);*/
    newNum = num.toString().split('');

    for (var i = 0; i < oImg.length; i++) {
        for (var j = 0; j < newNum.length; j++) {
            oImg[i].src = 'images/' + newNum[i] + '.png';
        }
    }
    setTimeout("startCount()", timeRandom);
}

startCount();

//time
function showLeftTime() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    var day = now.getDate();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    oTime.innerHTML = year + "年" + month + "月" + day + "日 " + hours + ":" + minutes + ":" + seconds;
    var timeID = setTimeout(showLeftTime, 1000);
}

showLeftTime();
