$(function() {
	addTabs(({
		id : '10008',
		title : '首页',
		close : false,
		url : 'baseNode/list'
	}));
	App.fixIframeCotent();
	var menus_3 = [ {
		id : "10010",
		text : "我的工作台",
		isHeader : true
	},
	{
		id : "10004",
		text : "学习监督",
		url : "/session/category_list",
		targetType : "iframe-tab",
		icon : "fa fa-area-chart"
	}, {
		id : "10026",
		text : "成绩管理",
		targetType : "iframe-tab",
		url : "/score/score_list",
		icon : "fa fa-list-alt"
	}, {
		id : "10208",
		text : "学籍管理",
		icon : "fa fa-users",
		children : [ {
			id : "10211",
			text : "班级管理",
			targetType : "iframe-tab",
			url : "/class/class_list",
			icon : "fa fa-circle-o"
		}, {
			id : "10213",
			text : "学生管理",
			targetType : "iframe-tab",
			url : "/student/student_list",
			icon : "fa fa-circle-o"
		} ]
	}, {
		id : "10209",
		text : "报名缴费",
		// isOpen: false,
		icon : "fa fa-inbox",
		children : [ {
			id : "10214",
			text : "报名管理",
			targetType : "iframe-tab",
			url : "/apply/apply_list",
			icon : "fa fa-circle-o"
		}, {
			id : "10215",
			text : "订单管理",
			targetType : "iframe-tab",
			url : "/order/order_list",
			icon : "fa fa-circle-o",
		} ]
	}, {
		id : "20214",
		text : "预警管理",
		targetType : "iframe-tab",
		url : "/alarm/alarm_list",
		icon : "fa fa-warning"
	} 
	];
	var menus = [ {
		id : "10010",
		text : "我的工作台",
		isHeader : true
	}, {
		id : "10004",
		text : "学习监督",
		url : "/session/category_list",
		targetType : "iframe-tab",
		icon : "fa fa-area-chart"
	}, {
		id : "10026",
		text : "成绩管理",
		targetType : "iframe-tab",
		url : "/score/score_list",
		icon : "fa fa-list-alt"
	}, {
		id : "20214",
		text : "预警管理",
		targetType : "iframe-tab",
		url : "/alarm/alarm_list",
		icon : "fa fa-warning"
	}
	];
	
	var menus_4 = [ {
		id : "10010",
		text : "我的工作台",
		isHeader : true
	}, {
		id : "10004",
		text : "学习监督",
		url : "/session/category_list",
		targetType : "iframe-tab",
		icon : "fa fa-area-chart"
	}, {
		id : "10026",
		text : "成绩管理",
		targetType : "iframe-tab",
		url : "/score/score_list",
		icon : "fa fa-list-alt"
	}, {
		id : "20214",
		text : "预警管理",
		targetType : "iframe-tab",
		url : "/alarm/alarm_list",
		icon : "fa fa-warning"
	},
	{
		id : "20301",
		text : "人员管理",
		// isOpen: false,
		icon : "fa fa-inbox",
		children : [ {
			id : "10311",
			text : "角色管理",
			targetType : "iframe-tab",
			url : "/manage/role_list",
			icon : "fa fa-circle-o"
		}, {
			id : "10312",
			text : "单位管理",
			targetType : "iframe-tab",
			url : "/manage/unit_list",
			icon : "fa fa-circle-o",
		}, {
			id : "10313",
			text : "账号管理",
			targetType : "iframe-tab",
			url : "/manage/admin_list",
			icon : "fa fa-circle-o",
		}, {
			id : "10314",
			text : "权限管理",
			targetType : "iframe-tab",
			url : "/manage/privilege_list",
			icon : "fa fa-circle-o",
		} ]
	}
	];
	
//	var role = $("#role").val();
//	if (role == 3) {
//		$('.sidebar-menu').sidebarMenu({
//			data : menus_3,
//			param : {
//				strUser : 'admin'
//			}
//		});
//	}else if(role == 4){
//		$('.sidebar-menu').sidebarMenu({
//			data : menus_4,
//			param : {
//				strUser : 'manage'
//			}
//		});
//	} else {
//		$('.sidebar-menu').sidebarMenu({
//			data : menus,
//			param : {
//				strUser : 'admin'
//			}
//		});
//	}
	
	var menus = $("#menuTree").val();
	$('.sidebar-menu').sidebarMenu({
		data : eval(menus),
		param : {
			strUser : 'admin'
		}
	});
	$(".sidebar-menu .header").attr("style","color: #b8c7ce");
	
	var goEasySubscribekey = $("#goEasySubscribekey").val();
	//alert(goEasySubscribekey);
	var goEasy = new GoEasy({
		appkey : goEasySubscribekey
	});
	var unitId = $("#unitId").val();
	goEasy.subscribe({
		channel : "alarm_channel_"+unitId,
		onMessage : function(message) {
			var productJson = JSON.parse(message.content);
			var html ="" ;
			$.each(productJson.listVO,function(key, val) {
				html += "<a href='javascript:;' onclick='rawDataList("+val.id+");'>" +
						val.alarmTime+" "+val.alarmLevel+"</a>";
                
			});
			var totalCount = productJson.totalCount;
			if(totalCount != 0){
				$("#alarm_message").html("<i class='fa fa-bell-o'></i><span class='label label-warning'>"+totalCount+"</span> 预警");
				$("#alarm_header").html("有"+totalCount+"条报警未读消息");
			}else{
				$("#alarm_message").html("<i class='fa fa-bell-o'></i> 预警");
				$("#alarm_header").html("无报警未读消息");
			}
			$("#alarm_un_read").html(html);
		},
		onSuccess : function() {
			// alert("Channel订阅成功。");
		},
		onFailed : function(error) {
			// alert("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" +
			// error.content)
		}
	});
});

function alarmList() {
	addNewTabs({
		id : '20214',
		title : '预警管理列表',
		close : true,
		url : '/alarm/alarm_list'
	});
}

function rawDataList(alarmId) {
	addNewTabs({
		id : '20213',
		title : '报告详情',
		close : true,
		url : '/alarm/raw_data_list?alarmId=' + alarmId
	});
}