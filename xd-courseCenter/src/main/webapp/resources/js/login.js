$(document).ready(function(){
    $(".close").click(function(){
        $(".notice").hide();
    })
	// 捕捉链接的点击事件
	$(".login").click(function(){
		if(!checkInfo())return false;
		// 取得要提交的参数
		var name = $.trim($("#name").val());
		var zjhm = $.trim($("#zjhm").val());
		var yzm = $.trim($("#yzm").val());
	    // 取得要提交页面的URL
	    var action = "/product/queryScoreResult";
	    // 创建Form
	    var form = $('<form></form>');
	    // 设置属性
	    form.attr('action', action);
	    form.attr('method', 'post');
	    // form的target属性决定form在哪个页面提交
	    // _self -> 当前页面 _blank -> 新页面
	    form.attr('target', '_self');
	    // 创建Input
	    var i_name = $('<input type="text" name="name" />');
	    i_name.attr('value', name);
	    var i_zjhm = $('<input type="text" name="zjhm" />');
	    i_zjhm.attr('value', zjhm);
	    var i_yzm = $('<input type="text" name="yzm" />');
	    i_yzm.attr('value', yzm);
	    // 附加到Form
	    form.append(i_name);
	    form.append(i_zjhm);
	    form.append(i_yzm);
	    // 提交表单
	    form.submit();
	    // 注意return false取消链接的默认动作
	    return false;
	});
	function checkInfo(){
		var empty_info = "";
		var invalid_info = "";
		var name_val = $("#name").val();
		if(!name_val || name_val == "" || typeof(name_val) == "undefine")
			empty_info+="[姓名]"; 
		var zjhm_val = $("#zjhm").val();
		if(!zjhm_val || zjhm_val == "" || typeof(zjhm_val) == "undefine")
			empty_info+="[证件号]";
		else if(!/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/g.test(zjhm_val))
			invalid_info+="[证件号]";
		var yzm_val = $("#yzm").val();
		if(!yzm_val || yzm_val == "" || typeof(yzm_val) == "undefine")
			empty_info+="[验证码]";
		else if(yzm_val.length != 4)
			invalid_info+="[验证码]";
		if(empty_info.length == 0 && invalid_info.length == 0)return true;
		if(empty_info.length > 0){
			empty_info+="不能为空.";
		}
		if(invalid_info.length > 0){
			invalid_info="请输入有效的"+invalid_info+".";
		}
		$(".info").html(empty_info + invalid_info);
		return false;
	}
	$(".reset").click(function(){
		$("#name").val("");
		$("#zjhm").val("");
		$("#yzm").val("");
		$(".info").html("");
	});
	$(".renovate").click(function(){
		$("#checkcode").attr("src","http://7xlos1.com1.z0.glb.clouddn.com/loading.png");
		$.ajax({
		  type:"post",
		  url : "/product/queryScoreImage",
		  timeout : 30000,
		  dataType: "json",
		  success: function (data){
			  if(data.retcode=="0"){
				  $("#checkcode").attr("src","data:image/jepg;base64,"+data.image);
			  }else{
				  $("#checkcode").attr("src","http://7xlos1.com1.z0.glb.clouddn.com/loadfail.png");
			  }
		  }
		});
	})
});