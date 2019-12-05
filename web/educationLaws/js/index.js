/**
 * Created by Administrator on 2015/6/3.
 */
 $(document).ready(function(){
     var height = window.innerHeight;
     var width = window.innerWidth;
     var scale =window.innerHeight / window.innerWidth;
     if( scale >  1.777778){
         var aa=$(".body");
         height = width * 1.777778;
         //var mainHeight = {"height":"height","margin-top":"height/2"};
         aa.height(height);
         var topDistance = (window.innerHeight-height)/2;
        aa.css("top",topDistance+'px');
        // aa.style.top=20;
     }
     else{
         width = height / 1.777778;
         $(".body").width(width);
     }
    $(".add").click(function(){
        var obj = $(this);
        obj.addClass("addCurrent");
        var str = obj.parent().attr('class');
        var on={"-webkit-animation":"add 1s","-moz-animation":"add 1s","-ms-animation":"add 1s","animation":"add 1s","transform":"rotate(45deg)"};
        var addBack={"-webkit-animation":"addBack 1s","-moz-animation":"addBack 1s","-ms-animation":"addBack 1s","animation":"addBack 1s","transform":"rotate(0deg)"};
       if(str=="list"){
           $(this).parent().removeClass("list").addClass("current");
           $(".addCurrent").css(on);
           $(".list").fadeOut();
           $(".next").fadeOut();

           setTimeout(function () {
               console.log($(this).next());
               obj.next().slideDown(300);
           }, 700);
       }
       else{
           setTimeout(function () {
               $(".list").fadeIn();
               $(".next").fadeIn();
           }, 700);
           $(".addCurrent").css(addBack);
           obj.next().slideUp(300);
           obj.parent().removeClass("current").addClass("list");
       }
    });

 });
