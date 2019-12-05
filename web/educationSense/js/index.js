/**
 * Created by Administrator on 2015/6/3.
 */
function playAndStop(str){
    window.js2Android.playAndStopAudio(str)
}
 $(document).ready(function(){
     //var height = window.innerHeight;
     //var width = window.innerWidth;
     //var scale =window.innerHeight / window.innerWidth;
     //if( scale >  1.777778){
     //    var aa=$(".body");
     //    height = width * 1.777778;
     //    aa.height(height);
     //    var topDistance = (window.innerHeight-height)/2;
     //    aa.css("top",topDistance+'px');
     //}
     //else{
     //    width = height / 1.777778;
     //    $(".body").width(width);
     //}
     var backMusic1 = new Object();
     backMusic1.src="myAudio/back1.mp3";
     backMusic1.loop=true;

     var backMusic2 = new Object();
     backMusic2.src="myaudio/back2.mp3";
     backMusic1.loop=true;

     var backMusic3 = new Object();
     backMusic3.src="myaudio/back3.mp3";
     backMusic1.loop=true;

     var page1 = new Object();
     page1.src="myAudio/page1.mp3";

     var page2 = new Object();
     page2.src="myAudio/page2.mp3";

     var page3 = new Object();
     page3.src="myAudio/page3.mp3";
     //backMusic1.loop=true;
     //========================教育动画
     var slideInBottom = {"top": "0", "animation": "slideInBottom 1s", "-moz-animation": "slideInBottom 1s", "-webkit-animation": "slideInBottom 1s", "-o-animation": "slideInBottom1s"};
     var slideOutTop = {"top": "-100%", "animation": "slideOutTop 1s", "-moz-animation": "slideOutTop 1s", "-webkit-animation": "slideOutTop 1s", "-o-animation": "slideOutTop 1s"};
     var slideOutBottom = {"top": "100%", "animation": "slideOutBottom 1s", "-moz-animation": "slideOutBottom 1s", "-webkit-animation": "slideOutBottom 1s", "-o-animation": "slideOutBottom 1s"};
     var slideInTop = {"top": "0", "animation": "slideInTop 1s", "-moz-animation": "slideInTop 1s", "-webkit-animation": "slideInTop 1s", "-o-animation": "slideInTop 1s"};
     $(".page0").touchwipe({
         wipeUp: function () {
             $(".page0").css(slideOutTop);
             $(".page1").css(slideInBottom);
             //document.getElementById("demo").innerHTML = y;
             //document.getElementById("page1Audio").currentTime = 0;
             //document.getElementById("page1Audio").load();
             //document.getElementById("page1Audio").play();
             var obj = {};
             obj.play = [];
             obj.play.push(page1);
             obj.play.push(backMusic1);
             window.js2Android.playAndStopAudio(JSON.stringify(obj))
         }
     });
     $(".page1").touchwipe({
         wipeDown: function () {
             $(".page1").css(slideOutBottom);
             $(".page0").css(slideInTop);
             //document.getElementById("page1Audio").pause();
             var obj = {};
             obj.stop = [];
             obj.stop.push(page1);
             obj.stop.push(backMusic1);
             playAndStop(JSON.stringify(obj))
         }
         ,wipeUp: function () {
             $(".page1").css(slideOutTop);
             $(".page2").css(slideInBottom);
             //alert("aaa");
             //document.getElementById("page1Audio").pause();
             //document.getElementById("page2Audio").currentTime = 0;
             //document.getElementById("page2Audio").load();
             //document.getElementById("page2Audio").play();
             var obj = {};
             obj.play = [];
             obj.stop = [];
             obj.stop.push(page1);
             obj.stop.push(backMusic1);
             obj.play.push(page2);
             obj.play.push(backMusic2);
             window.js2Android.playAndStopAudio(JSON.stringify(obj))
         }
     });
     $(".page2").touchwipe({
         wipeDown: function () {
             $(".page2").css(slideOutBottom);
             $(".page1").css(slideInTop);
             //document.getElementById("page2Audio").pause();
             //document.getElementById("page1Audio").currentTime = 0;
             //document.getElementById("page2Audio").load();
             //document.getElementById("page1Audio").play();
             var obj = {};
             obj.stop = [];
             obj.play = [];
             obj.stop.push(page2);
             obj.stop.push(backMusic2);
             obj.play.push(page1);
             obj.play.push(backMusic1);
             playAndStop(JSON.stringify(obj))
         },wipeUp: function () {
             $(".page2").css(slideOutTop);
             $(".page3").css(slideInBottom);
             //document.getElementById("page2Audio").pause();
             //document.getElementById("page3Audio").currentTime = 0;
             //document.getElementById("page3Audio").load();
             //document.getElementById("page3Audio").play();
             var obj = {};
             obj.stop = [];
             obj.play = [];
             obj.stop.push(page2);
             obj.stop.push(backMusic2);
             obj.play.push(page3);
             obj.play.push(backMusic3);
             playAndStop(JSON.stringify(obj))
         }
     });
     $(".page3").touchwipe({
         wipeDown: function () {
             $(".page3").css(slideOutBottom);
             $(".page2").css(slideInTop);
             //document.getElementById("page3Audio").pause();
             //document.getElementById("page2Audio").currentTime = 0;
             //document.getElementById("page2Audio").load();
             //document.getElementById("page2Audio").play();
             var obj = {};
             obj.stop = [];
             obj.play = [];
             obj.stop.push(page3);
             obj.stop.push(backMusic3);
             obj.play.push(page2);
             obj.play.push(backMusic2);
             playAndStop(JSON.stringify(obj))
         },wipeUp: function () {
         $(".page3").css(slideOutTop);
         $(".page4").css(slideInBottom);
             //document.getElementById("page3Audio").pause();
             var obj = {};
             obj.stop = [];
             obj.stop.push(page3);
             obj.stop.push(backMusic3);
             playAndStop(JSON.stringify(obj))
         }
     });
     $(".page4").touchwipe({
         wipeDown: function () {
             $(".page4").css(slideOutBottom);
             $(".page3").css(slideInTop);
             //document.getElementById("page3Audio").currentTime = 0;
             //document.getElementById("page3Audio").load();
             //document.getElementById("page3Audio").play();
             var obj = {};
             obj.play = [];
             obj.play.push(page3);
             obj.play.push(backMusic3);
             playAndStop(JSON.stringify(obj))

         },wipeUp: function () {
         $(".page4").css(slideOutTop);
         $(".page5").css(slideInBottom);
     }
     });
     $(".policy").click(function(){
         $(".policyContent").fadeIn(500);
     });
     $(".policyClose").click(function(){
         $(".policyContent").fadeOut(500);
     });
     $(".teaching").click(function(){
         $(".teachingContent").fadeIn(500);
     });
     $(".teachingClose").click(function(){
         $(".teachingContent").fadeOut(500);
     });
     $(".teacher").click(function(){
         $(".teacherContent").fadeIn(500);
     });
     $(".teacherClose").click(function(){
         $(".teacherContent").fadeOut(500);
     });
     $(".page5").touchwipe({
         wipeDown: function () {
             $(".page5").css(slideOutBottom);
             $(".page4").css(slideInTop);
         },wipeUp: function () {
             $(".page5").css(slideOutTop);
             $(".page6").css(slideInBottom);
         }
     });
     $(".page6").touchwipe({
         wipeDown: function () {
             $(".page6").css(slideOutBottom);
             $(".page5").css(slideInTop);
         }
     });
     $(".life").click(function(){
         $(".lifeContent").fadeIn(500);
     });
     $(".lifeClose").click(function(){
         $(".lifeContent").fadeOut(500);
     });
     $(".latent").click(function(){
         $(".latentContent").fadeIn(500);
     });
     $(".diamondClose").click(function(){
         $(".latentContent").fadeOut(500);
     });
     $(".experience").click(function(){
         $(".experienceContent").fadeIn(500);
     });
     $(".experienceClose").click(function(){
         $(".experienceContent").fadeOut(500);
     });
     $(".game").click(function(){
         $(".gameContent").fadeIn(500);
     });
     $(".gameClose").click(function(){
         $(".gameContent").fadeOut(500);
     });
     $(".develop").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".developCenter").fadeOut(500);
         }
         else{
             $(".developCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
     $(".overall").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".overallCenter").fadeOut(500);
         }
         else{
             $(".overallCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
     $(".subject").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".subjectCenter").fadeOut(500);
         }
         else{
             $(".subjectCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
     $(".basic").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".basicCenter").fadeOut(500);
         }
         else{
             $(".basicCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
     $(".open").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".openCenter").fadeOut(500);
         }
         else{
             $(".openCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
     $(".all").click(function(){
         $(".center").children("div").fadeOut(500);
         if($(this).hasClass("hide")){
             $(this).removeClass("hide");
             $(".allCenter").fadeOut(500);
         }
         else{
             $(".allCenter").fadeIn(500);
             $(this).addClass("hide");
         }
     });
 });
