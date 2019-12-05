$(function() {
	var role = $.cookie('cookie_role');
	var login_page = $.cookie('cookie_login_page');
	if (!isEmpty(login_page)) {
		if ("manage_login" == login_page) {
			$.cookie('cookie_login_page', null, {
				path : '/'
			});
			location.href = "/admin/manage_login";
		} else if (!isEmpty(role) && (role == 1 || role == 2 || role == 3)
				&& location.href.indexOf("manage_login") < 0) {
			$("#tab-box a").removeClass("active");
			$("#btn" + role).addClass("active");
			$(".tab-cont").hide();
			$("#div" + role).show();
		}
	}
	if (parent.location.href.indexOf("login") < 0
			&& parent.location.href.indexOf("xyz=") < 0) {
		if (parent.location.href.indexOf("fail=") < 0) {
			parent.location.href = parent.location.href + "?xyz="
					+ Math.random();
		} else {
			parent.location.href = parent.location.href + "&xyz="
					+ Math.random();
		}
	}
	
	
	// 第一级单位login
	var zikaoban_login = document.getElementById('zikaoban_login');
	zikaoban_login.addEventListener('click',function(){
        submit(1);
    },false)
    // 第二级单位login
    var masterUniversity_login = document.getElementById('masterUniversity_login');
	masterUniversity_login.addEventListener('click',function(){
		submit(2);
	},false)
	// 第三级单位login
	var pilotUnit_login = document.getElementById('pilotUnit_login');
	pilotUnit_login.addEventListener('click',function(){
		submit(3);
	},false)
});

function popup_msg(msg) {
	$(".popup").html("" + msg + "");
	$(".popupDom").animate({
		"top" : "0px"
	}, 400);
	setTimeout(function() {
		$(".popupDom").animate({
			"top" : "-40px"
		}, 400);
	}, 2000);
}

function submit(role) {
	$.cookie('cookie_role', role, {
		expires : 7,
		path : '/'
	});
	$.cookie('cookie_login_page', "login", {
		expires : 7,
		path : '/'
	});
	
	
	if (1 == role) {
		var tmp = $("#stUserName").val();
		$("#sUserName").val(role + ":" + tmp);
		$("#SELF_TAUGHT_FORM").submit();
		
	} else if (2 == role) {
		var tmp = $("#cnUserName").val();
		$("#cUserName").val(role + ":" + tmp);
		$("#CHIEF_UNIT_FORM").submit();
		
	} else if (3 == role) {
		var tmp = $("#puUserName").val();
		$("#pUserName").val(role + ":" + tmp);
		$("#POILT_UNIT_FORM").submit();
		
	} else if (4 == role) {
		$.cookie('cookie_login_page', "manage_login", {
			expires : 7,
			path : '/'
		});
		var tmp = $("#maUserName").val();
		$("#mUserName").val(role + ":" + tmp);
		$("#MANAGE_FORM").submit();
		
	}
}

/***
 * 获取浏览器版本和其对应的版本号
 * @returns
 */
function getExplorerInfo() {
	 var explorer = window.navigator.userAgent.toLowerCase() ;
	 //ie 
	 if (explorer.indexOf("msie") >= 0) {
		var ver=explorer.match(/msie ([\d.]+)/)[1];
		return {type:"IE",version:ver};
	 }
	 //firefox 
	 else if (explorer.indexOf("firefox") >= 0) {
		var ver=explorer.match(/firefox\/([\d.]+)/)[1];
		return {type:"Firefox",version:ver};
	 }
	 //Chrome
	 else if(explorer.indexOf("chrome") >= 0){
		var ver=explorer.match(/chrome\/([\d.]+)/)[1];
		var first = ver.split(".");
		var fn = Number(first[0]);
		if(fn<32){
			return {type:"Chrome",version:false};
		}else{
			return {type:"Chrome",version:ver};
		}
	 }
	 //Opera
	 else if(explorer.indexOf("opera") >= 0){
		 var ver=explorer.match(/opera.([\d.]+)/)[1];
		 return {type:"Opera",version:ver};
	 }
	 //Safari
	 else if(explorer.indexOf("Safari") >= 0){
		 var ver=explorer.match(/version\/([\d.]+)/)[1];
		 return {type:"Safari",version:ver};
	 }
}
	//弹出类型和版本号
//	 alert("type:"+getExplorerInfo().type+"\nversion:"+getExplorerInfo().version);













$(document).keydown(function(event) {
	if (event.keyCode == 13) {
		var role = $(".active").attr("role");
		submit(role);
	}

});