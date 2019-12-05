Raphael(function () {
    var hldr = document.getElementById("holder"),
        text = hldr.innerHTML.replace(/^\s+|\s+$|<[^>]+>/g, "");
    hldr.innerHTML = "";
    var R = Raphael("holder", 640, 480),
        txt = [],
        attr = {font: "50px Helvetica", opacity: 0.5};
    txt[0] = R.text(320, 240, text).attr(attr).attr({fill: "#0f0"});
    var mouse = null, rot = 0;
    document.ondrag = function (e) {
        e = e || window.event;
        if (mouse == null) {
            mouse = e.clientX;
            return;
        }
        rot += e.clientX - mouse;
        txt[0].attr({transform: "r" + rot});
        mouse = e.pageX;
        R.safari();
    };
});