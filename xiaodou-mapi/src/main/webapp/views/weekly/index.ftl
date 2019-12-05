<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>学习周报</title>
    <link rel="stylesheet" type="text/css" href="${baseCssOP}/slidePage.css?${timeStamp}">
    <link rel="stylesheet" type="text/css" href="${baseCssOP}/page-animation.css?${timeStamp}">
    <link rel="stylesheet" type="text/css" href="${baseCssOP}/style.css?${timeStamp}">
</head>
<body>
<div class="slidePage-container">
    <div class="item">
        <div class="item1">
            <img class="f-stu" src="${baseOP}/image/niu.png" alt="牛荣博" width="89px" height="94.5px">
            <div class="f-name">牛荣博</div>
            <div class="f-data" id="lastTime"></div>
        </div>
<!--  	    <section class="u-arrow">
            <p class="css_sprite01"></p>
        </section> -->
    </div>
    <div class="item">
        <div class="item2"></div>
    </div>
    <div class="item">
        <div class="item3">
            <div class="box" id="main">
                <ul class="wrap">
                    <li class="f-li">
                        <a href="#" class="c1" style="border: 2px solid #fdb563;"></a>
                        <span class="select" style="display: block"></span>
                    </li>
                    <li>
                        <a href="#" class="c2"></a>
                        <span class="select"></span>
                    </li>
                    <li>
                        <a href="#" class="c3"></a>
                        <span class="select"></span>
                    </li>
                    <li class="l-li">
                        <a href="#" class="c4"></a>
                        <span class="select"></span>
                    </li>
                </ul>
            </div>
            <div class="c-cont" id="c-content">
                <div style="display: block;">
                    <p class="f-p"><span>16</span><span>135</span></p>
                    <p class="s-p"><span>42</span><span>65</span></p>
                </div>
                <div>
                    <p class="f-p o-color"><span>22</span><span>115</span></p>
                    <p class="s-p o-color"><span>49</span><span>68</span></p>
                </div>
                <div>
                    <p class="f-p"><span>55</span><span>142</span></p>
                    <p class="s-p"><span>32</span><span>71</span></p>
                </div>
                <div>
                    <p class="f-p o-color"><span>40</span><span>181</span></p>
                    <p class="s-p o-color"><span>12</span><span>85</span></p>
                </div>
            </div>
        </div>
    </div>
    <div class="item">
        <div class="item4"></div>
    </div>
</div>
<script type="text/javascript" src="${baseJsOP}/zepto.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/slidePage.js?${timeStamp}"></script>
<script src="${baseJsOP}/fastclick.js?${timeStamp}"></script>
<script src="${baseJsOP}/style.js?${timeStamp}"></script>
<script type="text/javascript">
    slidePage.init({
        'index': 1,
        'before': function (index) {
        },
        'after': function (index) {
        },
        'useArrow': true,
        'useMusic': false
    });
    window.addEventListener( "load", function() {
        FastClick.attach( document.body );
    }, false );
</script>
</body>
</html>