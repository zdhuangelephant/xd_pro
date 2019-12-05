$(function () {
    // Grab the data
    var data = [],
        axisx = [],
        axisy = [],
        table = $("#for-chart");
    //$("tbody td", table).each(function (i) {
    //    data.push(parseFloat($(this).text(), 10));
    //});
    table.hide();
    $("tbody th", table).each(function () {
        axisy.push($(this).text());
    });
    $("thead th", table).each(function () {
        axisx.push($(this).text());
    });
    // Draw
    var width =800,
        height = 300,
        leftgutter = 10,
        topgutter = 0,
        r = Raphael("chart", width, height),
        txt = {"font": '10px Fontin-Sans, Arial', stroke: "none", fill: "#fff"},
        X = (width - leftgutter) / axisx.length,
        //X=15;
        //Y=15;
        Y = (height - topgutter) / axisy.length,
        color = $("#chart").css("color");
    max = Math.round(X / 2) - 1;
    r.path(0,0,300,0);
    // r.rect(0, 0, width, height, 5).attr({fill: "#000", stroke: "none"});
    for (var i = 0, ii = axisx.length; i < ii; i++) {
        r.text(leftgutter + X * (i + .5), 10, axisx[i]).attr(txt);
    }
    for (var i = 0, ii = axisy.length; i < ii; i++) {
        r.text(10, Y * (i + .5), axisy[i]).attr(txt);
    }

});