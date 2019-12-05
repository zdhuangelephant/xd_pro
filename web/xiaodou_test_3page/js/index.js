/**
 * Created by Administrator on 2015/6/3.
 */
function playAndStop(str){
    window.js2Android.playAndStopAudio(str)

}
var isPolityClick = 0;
var isEconomyClick = 0;
 $(document).ready(function(){
    // playAndStop("test123")

     //背景音乐
     var backMusic = new Object();
     backMusic.src="myaudio/backMusic.mp3";
     backMusic.loop=true;

     var myAudio1 = new Object();
     myAudio1.src="myaudio/relation.mp3";

     var myAudio2 = new Object();
     myAudio2.src="myaudio/eduAndPolity.mp3";

     var myAudio3 = new Object();
     myAudio3.src="myaudio/eduAndEconomic.mp3";

     var concept = new Object();
     concept.src="myaudio/concept.mp3";


     $("#polity").click(function(){
        isPolityClick = 1;
         $(".educationAndPolity").show();
         var trans={"animation":"rotateinRB 0.8s","-webkit-animation":"rotateinRB 0.8s","-moz-animation":"rotateinRB 0.8s","-ms-animation":"rotateinRB 0.8s"};
         $("#PolityBox").css(trans);
        //document.getElementById("myAudio2").currentTime = 0;
        //document.getElementById("myAudio2").play();
        //document.getElementById("demo").innerHTML = x;

        var obj = {};
        obj.play = [];
        obj.play.push(myAudio2);
        window.js2Android.playAndStopAudio(JSON.stringify(obj))
     });
     $("#economy").click(function(){
         isEconomyClick = 1;
         $(".educationAndEconomy").show();
         var trans1={"animation":"rotateinRB 0.8s","-webkit-animation":"rotateinRB 0.8s","-moz-animation":"rotateinRB 0.8s","-ms-animation":"rotateinRB 0.8s"};
         $("#economyBox").css(trans1);
         //document.getElementById("myAudio3").currentTime = 0;
         //document.getElementById("myAudio3").play();
         //document.getElementById("demo").innerHTML = x;

         var obj = {};
         obj.play = [];
         obj.play.push(myAudio3);
         playAndStop(JSON.stringify(obj))
     });
     $("#polityCancel").click(function(){
         if(isPolityClick==1 && isEconomyClick==1){
             var t={"opacity":"1"};
             $(".education img").addClass("addAnimate");
             $(".addAnimate").css(t);
         }
         var trans={"animation":"rrotateoutBR 0.3s","-webkit-animation":"rotateoutBR 0.3s","-moz-animation":"rotateoutBR 0.3s","-ms-animation":"rotateoutBR 0.3s"};
         $("#PolityBox").css(trans);
             $(".educationAndPolity").fadeOut("fast");
         //document.getElementById("myAudio2").currentTime = 0;
         //document.getElementById("myAudio2").pause();
         //document.getElementById("demo").innerHTML = x;

         var obj = {};
         obj.stop = [];
         obj.stop.push(myAudio2);
         playAndStop(JSON.stringify(obj))
     });
     $("#economyCancel").click(function(){
         if(isPolityClick==1 && isEconomyClick==1){
             var t={"opacity":"1"};
             $(".education img").addClass("addAnimate");
             $(".addAnimate").css(t);
         }
         var trans={"animation":"rrotateoutBR 0.3s","-webkit-animation":"rotateoutBR 0.3s","-moz-animation":"rotateoutBR 0.3s","-ms-animation":"rotateoutBR 0.3s"};
         $("#economyBox").css(trans);
         $(".educationAndEconomy").fadeOut("fast");
         //document.getElementById("myAudio3").pause();

         var obj = {};
         obj.stop = [];
         obj.stop.push(myAudio3);
         playAndStop(JSON.stringify(obj))

     });
     //========================教育动画
     $("#add1").click(function(){
         var name = this.className;
         //转圈淡出
         //alert(name);
         var str1 = {"opacity":"0","animation":"bigCircle1 1s","-moz-animation":"bigCircle1 1s","-webkit-animation":"bigCircle1 1s","-o-animation":"bigCircle1 1s"};
         //转圈淡入
         var str2 = {"opacity":"1","animation":"bigCircle2 1s","-moz-animation":"bigCircle2 1s","-webkit-animation":"bigCircle2 1s","-o-animation":"bigCircle2 1s"};
         //四个小球弹出
         var str3={"opacity":"1","-webkit-animation":"bouncein 1s","-moz-animation":"bouncein 1s","-ms-animation":"bouncein 1s","animation":"bouncein 1s"};
         //四个小球弹入
         var str4={"opacity":"0","-webkit-animation":"bouncein1 1s","-moz-animation":"bouncein1 1s","-ms-animation":"bouncein1 1s","animation":"bouncein1 1s"};
        //四条线
         var line1={"opacity":"1","-webkit-animation":"line1 1s","-moz-animation":"line1 1s","-ms-animation":"line1 1s","animation":"line1 1s"};
         var line2={"-webkit-animation":"line2 1s","-moz-animation":"line2 1s","-ms-animation":"line2 1s","animation":"line2 1s","opacity":"1"};
         var line3={"opacity":"1","-webkit-animation":"line3 1s","-moz-animation":"line3 1s","-ms-animation":"line3 1s","animation":"line3 1s"};
         var line4={"opacity":"1","-webkit-animation":"line4 1s","-moz-animation":"line4 1s","-ms-animation":"line4 1s","animation":"line4 1s"};
         var line2out={"opacity":"0","-webkit-animation":"line2out 1s","-moz-animation":"line2out 1s","-ms-animation":"line2out 1s","animation":"line2out 1s"};
         var line4out={"opacity":"0","-webkit-animation":"line4out 1s","-moz-animation":"line4out 1s","-ms-animation":"line4out 1s","animation":"line4out 1s"};
         var line1out={"opacity":"0","-webkit-animation":"line1out 1s","-moz-animation":"line1out 1s","-ms-animation":"line1out 1s","animation":"line1out 1s"};
         var line3out={"opacity":"0","-webkit-animation":"line3out 1s","-moz-animation":"line3out 1s","-ms-animation":"line3out 1s","animation":"line3out 1s"};
         //字体
         var content={"opacity":"0","-webkit-animation":"bounceout 1s","-moz-animation":"bounceout 1s","-ms-animation":"bounceout 1s","animation":"bounceout 1s"};
         var content1={"opacity":"1","-webkit-animation":"bounceout1 1s","-moz-animation":"bounceout1 1s","-ms-animation":"bounceout1 1s","animation":"bounceout1 1s"};
        //加号
         var add1={"-webkit-animation":"add1 1s","-moz-animation":"add1 1s","-ms-animation":"add1 1s","animation":"add1 1s","transform":"rotate(45deg)"};
         var add1back={"-webkit-animation":"add1back 1s","-moz-animation":"add1back 1s","-ms-animation":"add1back 1s","animation":"add1back 1s","transform":"rotate(0deg)"};

         if(name == "animate"){
             $(".bigCircle").css(str2);
             $(".text1").css(str4);
             $(".text2").css(str4);
             $(".text3").css(str4);
             $(".text4").css(str4);
             $(".line2").css(line2out);
             $(".line4").css(line4out);
             $(".line1").css(line1out);
             $(".line3").css(line3out);
             $("#contentOut").css(content1);
             $("#add1").css(add1back);
             $("#add1").attr("class","");

             var obj = {};
             obj.stop = [];
             obj.stop.push(myAudio1);
             playAndStop(JSON.stringify(obj))
         }
         else{
             $(".bigCircle").css(str1);
             $(".text1").css(str3);
             $(".text2").css(str3);
             $(".text3").css(str3);
             $(".text4").css(str3);
             $(".line1").css(line1);
             $(".line2").css(line2);
             $(".line3").css(line3);
             $(".line4").css(line4);
             $("#contentOut").css(content);
             $("#add1").css(add1);
             //$(".aa:odd").removeClass().addClass("BB");
             $("#add1").attr("class","animate");

             var obj = {};
             obj.play = [];
             obj.play.push(myAudio1);
             playAndStop(JSON.stringify(obj))
         }
     });
     var slideInBottom = {"top": "0", "animation": "slideInBottom 1s", "-moz-animation": "slideInBottom 1s", "-webkit-animation": "slideInBottom 1s", "-o-animation": "slideInBottom1s"};
     var slideOutTop = {"top": "-100%", "animation": "slideOutTop 1s", "-moz-animation": "slideOutTop 1s", "-webkit-animation": "slideOutTop 1s", "-o-animation": "slideOutTop 1s"};
     var slideOutBottom = {"top": "100%", "animation": "slideOutBottom 1s", "-moz-animation": "slideOutBottom 1s", "-webkit-animation": "slideOutBottom 1s", "-o-animation": "slideOutBottom 1s"};
     var slideInTop = {"top": "0", "animation": "slideInTop 1s", "-moz-animation": "slideInTop 1s", "-webkit-animation": "slideInTop 1s", "-o-animation": "slideInTop 1s"};

     $(".page1").touchwipe({
         wipeUp: function () {
             $(".page1").css(slideOutTop);
             $(".page2").css(slideInBottom);

             var obj = {};
             obj.play = [];
             obj.play.push(concept);
             obj.play.push(backMusic);
             playAndStop(JSON.stringify(obj))
         }
     });
     $(".page2").touchwipe({
         wipeUp: function () {
             $(".page2").css(slideOutTop);
             $(".page3").css(slideInBottom);
             setTimeout(function () {
                 $(".bigCircle").addClass("bigCircle animate0");
                 $(".back1").addClass("back1 animate1");
                 $(".back2").addClass("back2 animate2");
                 $(".back3").addClass("back3 animate3");
                 $(".back12").addClass("back12 animate12");
                 $(".back22").addClass("back22 animate22");
                 $(".back32").addClass("back32 animate32");
                 $(".add2 img").addClass("addAnimate");
                 $(".add3 img").addClass("addAnimate");
             }, 250);

             var obj = {};
             obj.stop = [];
             obj.stop.push(concept);
             playAndStop(JSON.stringify(obj))
         },
         wipeDown: function () {
             $(".page2").css(slideOutBottom);
             $(".page1").css(slideInTop);
             //document.getElementById("concept").pause();
             //document.getElementById("backMusic").pause();

             var obj = {};
             obj.stop = [];
             obj.stop.push(concept);
             obj.stop.push(backMusic);
             playAndStop(JSON.stringify(obj))
         }
     });
     $(".page3").touchwipe({
         wipeDown: function () {
             $(".page3").css(slideOutBottom);
             $(".page2").css(slideInTop);
             setTimeout(function () {
                 $(".bigCircle").removeClass("animate0");
                 $(".back1").removeClass("animate1");
                 $(".back2").removeClass("animate2");
                 $(".back3").removeClass("animate3");
                 $(".back12").removeClass("animate12");
                 $(".back22").removeClass("animate22");
                 $(".back32").removeClass("animate32");
                 $(".education img").removeClass("addAnimate");
                 $(".add2 img").removeClass("addAnimate");
                 $(".add3 img").removeClass("addAnimate");
                 //document.getElementById("backMusic").pause();
             }, 200);
             //document.getElementById("myAudio1").pause();
             //document.getElementById("concept").currentTime = 0;
             //document.getElementById("concept").play();


             var obj = {};
             obj.stop = [];
             obj.stop.push(myAudio1);
             obj.play=[];
             obj.play.push(concept);
             playAndStop(JSON.stringify(obj))
         }
     });
     //*********************page2动画
     $(".generalized").click(function(){
         $(".generalizedContent").fadeIn(500);
     });
     $("#generalizedClose").click(function(){
         $(".generalizedContent").fadeOut(500);
     });
     $(".narrowSense").click(function(){
         $(".narrowSenseContent").fadeIn(500);
     });
     $("#narrowSenseClose").click(function(){
         $(".narrowSenseContent").fadeOut(500);
     });




 });
