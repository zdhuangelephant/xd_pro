/* ---------------------addinput begin ------------------ */
// header addInput
function headerAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="headerAddInput(this)"><td class="header-checkbox-td" onclick="stopProp(this)"><input class="header-checkbox" name="" type="checkbox" value="" /></td><td><input type="text" name="name" placeholder="参数名称"></td><td><input type="text" name="paramValue" placeholder="参数值"></td><td><input type="text" name="desc" placeholder="描述"></td><td style="display:none;"><input type="text" name="paramType" value="HeaderParam" placeholder="paramType" ></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}

function templateHeaderAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="templateHeaderAddInput(this)"><td><input type="text" name="key" placeholder="参数名称"></td><td><input type="text" name="value" placeholder="参数值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}

// param addInput
function paramAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="paramAddInput(this)"><td><input type="text" name="name" placeholder="参数名称"></td><td><input type="text" name="paramValue" placeholder="参数值"></td><td><input type="text" name="desc" placeholder="描述"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td style="display:none;"><input type="text" name="paramType" value="QueryParam" placeholder="paramType" ></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}

// perCondition addInput
function perAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="perAddInput(this)"><td><input name="key" type="text" placeholder="目标键"></td><td><select id="preConditionSel" class="select-style" name="condition"><option>eq</option><option>ge</option><option>le</option><option>gt</option><option>lt</option><option>in</option><option>notIn</option><option>isNull</option><option>isNotNull</option><option>isEmpty</option><option>isNotEmpty</option></select></td><td><input type="text" name="targetValue" placeholder="目标值"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}

// PreSet addInput
function perSetAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="perSetAddInput(this)"><td><select onchange="preSetChangeForm(this)" class="select-style" name="dataSource"><option value="Local">Local</option><option selected = "selected" value="Mysql">Mysql</option><option value="Redis">Redis</option><option value="Mongo">Mongo</option><option value="Rabbitmq">Rabbitmq</option><option value="Func">Func</option></select></td><td><select class="select-style" name="scope"><option value="part">局部</option><option value="global">全局</option></select></td><td><input type="text" name="key" placeholder="存储键"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="assignment" placeholder="赋值语句"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}

// TestCase addInput
function testCaseAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="testCaseAddInput(this)"><td><input type="text" name="name" placeholder="测试名称"></td><td><input type="text" name="testKey" placeholder="待测试键"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="testValue" placeholder="目标值"></td><td style="display:none"><input type="text" name="testResult" value="Unknown"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}
// AfterSet
function afterSetAddInput(obj) {
	var allTr = $(obj).parent().find("tr");
	var len = allTr.length;
	var lastTr = $(obj).parent().find("tr")[len - 1];
	var row = '<tr class="remove" onclick="afterSetAddInput(this)"><td><input name="resultKey" type="text" placeholder="结果键"></td><td><select class="select-style" name="dataType"><option value="sTring">String</option><option value="iNteger">Integer</option><option value="dOuble">Double</option><option value="lIst">List</option><option value="mAp">Map</option></select></td><td><input type="text" name="mappingKey" placeholder="映射键"></td><td class="remove-wrap"><a href="#" onclick="removeClass(this)" class="removeclass">×</a></td></tr>';

	if (obj.tagName == 'A') {
		$(obj).parent().children().children().children().append(row);
	}
	if (obj == lastTr) {
		$(obj).parent().append(row);
	}
}
/* ---------------------addinput end ------------------ */

/* ---------addtab begin --------- */
function headerOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(0).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(0).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(0).show().siblings().hide();
}

function paramOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(1).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(1).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(1).show().siblings().hide();
}

function preOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(2).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(2).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(2).show().siblings().hide();
}

function preSetOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(3).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(3).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(3).show().siblings().hide();
}

function testCaseOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(4).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(4).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(4).show().siblings().hide();
}

function afterSetOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(5).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(5).show().siblings()
			.hide();
	$('body').find('#keyValueScroll').children().eq(5).show().siblings().hide();
}

function outParamOn(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().parent().parent().next().children().eq(6).show().siblings()
			.hide();
	$(obj).parent().find('#headertemplate').children().eq(5).hide();
}

function removeClass(obj) {
	$(obj).parent().parent('.remove').remove();
}
/* ---------addtab begin --------- */

/* --dropdown begin -- */

function dropdownOn(obj) {
	var ul = $(obj).siblings();
	if (ul.css("display") == "none") {
		ul.slideDown("fast");
	} else {
		ul.slideUp("fast");
	}

	var dropA = $(obj).siblings().children().children();
	dropA.click(function() {
		var txt = $(this).text();
		$(obj).html(txt);
		var value = $(this).attr("rel");
		ul.hide();
	});
}
/* --dropdown end -- */

/* --editor begin -- */
function editorRequestBody(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().next().children().eq(0).show().siblings().hide();
}

function resultA(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().next().children().eq(1).show().siblings().hide();
}

function editorTestCaseA(obj) {
	$(obj).addClass('h-active').siblings().removeClass('h-active');
	$(obj).parent().next().children().eq(2).show().siblings().hide();
}

/* --editor end -- */

/* input onclick begin */
function xInputOn(obj) {
	$(obj).parent().next().children().eq(1).show().siblings().hide();
	$(obj).parent().find("#rawData")[0].checked = "";
	$(obj).checked = "checked";
}

function rInputOn(obj) {
	$(obj).parent().next().children().eq(2).show().siblings().hide();
	$(obj).parent().find("#formData")[0].checked = "";
	$(obj).checked = "checked";
}
/* input onclick end */
/* outparam textarea begin */

function textBtnOn(obj) {
	var outTextarea = $(obj).parents(".btn-wrap").next();
	var str = outTextarea.val().replace(/\s+/g, "");
	outTextarea.val(str);
}

function jsonBtnOn(obj) {
	var outTextarea = $(obj).parents(".btn-wrap").next();
	var tTextareaCont = outTextarea.val();
	var cstring = tTextareaCont.replace(/[\\]/g, '');
	if (cstring.substring(0, 1) == "\"") {
		// 字符串以"开头，去掉"
		cstring = cstring.substr(2, cstring.length - 1)
	}
	if (cstring.substring(cstring.length - 1) == "\"") {
		// 字符串以"结尾，去掉"
		cstring = cstring.substr(0, cstring.length - 2)
	}
	try {
		var jsonObj = JSON.parse(outTextarea.val());
		var strJson = JSON.stringify(jsonObj, null, 4);
		outTextarea.val(strJson);
	} catch (error) {
		outTextarea.val(cstring);
	}

}
/* outparam textarea end */
/* presets header begin */

$(document).on("click", function() {
	$("body").find(".preset-box").hide();
	flag = true;
})

var flag = true;
function presetBtnOn(obj) {
	stopProp(this);
	if (flag) {
		$(obj).children().css("display", "block");
		flag = false;
	} else {
		$(obj).children().css("display", "none");
		flag = true;
	}
	var url = "";
	var typeId = -1; // 未知
	if($(obj).attr("label")=='header'){
		typeId = 0;
	}
	if($(obj).attr("label")=='param'){
		typeId = 1;
	}
	if($(obj).attr("label")=='preCondition'){
		typeId = 2;
	}
	if($(obj).attr("label")=='preSet'){
		typeId = 3;
	}
	if($(obj).attr("label")=='testCase'){
		typeId = 4;
	}
	if($(obj).attr("label")=='afterSet'){
		typeId = 5;
	}
	
	// 获取UL的对象
	var $ul = $(obj).find("ul");

	// 获取所有的group
	$.ajax({
		type : "post",
		url : "/template/getGroup",
		data : {
			'id' : '','typeId' : typeId
		},
		dataType : "json",
		success : function(data) {
			if ('true' == data.status && null != data.msg && data.msg.length > 0) {
				$ul.html("");
				for ( var e in data.msg) {
					$ul.append('<li onclick=moveToRight(' + typeId + ','+ data.msg[e].id + ',this)>'+ data.msg[e].groupName+ '</li>');
				}
			}
		}
	});
		
}
/* presets header end */

document.getElementById('desc').onkeydown = function() {
	if (this.value.length >= 500) {
		this.value.length = this.value.length - 1;
		document.getElementById('dese-box').innerHTML = '最多可输入500个字符！';
	}
}

function stopProp(obj) {
	event.stopPropagation();
}

/**
 * 若data为Func;则需要调取获取js的接口进行赋值语句的填充
 * 
 * @returns
 */
function preSetChangeForm(data) {
	if (data.value == 'Func') {
		var s = document.createElement('select');
		s.setAttribute("name", "assignment");
		var promot = new Option("--请选择JS签名--", "-1");
		s.options.add(promot);

		// 获取所有的JS方法名
		$.ajax({
			type : "post",
			url : "/dataSource/getJSFuncsWithJson",
			dataType : "json",
			success : function(data) {
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						s.options[i + 1] = new Option(data[i].funSignature,
								data[i].funSignature)
					}
				}
			}
		});
		data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling
				.removeChild(data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.firstChild);
		data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.appendChild(s);
	} else {
		var s = document.createElement('input');
		s.setAttribute("name", "assignment");
		s.setAttribute("type", "text");
		s.setAttribute("placeholder", "赋值语句");
		data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling
				.removeChild(data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.firstChild);
		data.parentNode.nextSibling.nextSibling.nextSibling.nextSibling.appendChild(s);
	}
}
