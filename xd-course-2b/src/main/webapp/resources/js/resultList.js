$(document).ready(function(){
   $(".right").click(function(){
   if($(this).parent().parent().next().css('display')=="block"){
       $(this).parent().parent().next().slideUp();
       $(this).parent().css('border-bottom','1px solid #cdcdcd');
       $(this).parent().parent().css('border-bottom','none');
       $(this).parent().parent().next().next().css('border-top','none');
       $(this).children('img').attr('src','http://7xlos1.com1.z0.glb.clouddn.com/bottom_15.png');
   }
   else{
       $(this).parent().parent().next().slideDown();
       $(this).parent().css('border-bottom','none');
       $(this).parent().parent().css('border-bottom','1px solid #cdcdcd');
       $(this).parent().parent().next().next().css('border-top','1px solid #cdcdcd');
       $(this).children('img').attr('src','http://7xlos1.com1.z0.glb.clouddn.com/top_06.png');
       }

   })
   $(".notice").click(function(){
//	  window.location.href = "/product/queryScoreInit"; 
	  window.open("/product/queryScoreInit","_self");
   });
});      