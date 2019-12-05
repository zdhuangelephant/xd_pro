<div class="builder-content-r w70 scrollbar" id="scroll-style">
	<div class="force-overflow"></div>
	<ul class="nav nav-tabs tab-left" id="myTab">
		<a class="add-tab-style" type="button" onclick="addTab('')">+</a>
	</ul>
	<div class="tab-content" id="tabContent"></div>
</div>

<script>
	$(function() {
		var divId=addTab("");
		 $("#button" + divId.substring(11, 26)).click();
	})
	function addTab(name) {
	    var tabContent = document.getElementById("tabContent")//定位ul
	    if(tabContent.children.length>=8){
	    	alert("最多允许8个请求框");
	    	return;
	    }
		var ul = document.getElementById("myTab")//定位ul
		var li = document.createElement("li"); //创建li
		li.className = "active";
		li.setAttribute("name", "parentLi");
		var base = RndNum(10);
		var divId = "tabTemplate" + base;
		var buttonId = "button" + base;
		var href_button = document.createElement("span"); //创建li中的链接a
		/* ---close newTab begin--- */
		var liClose = document.createElement("a");
		liClose.innerHTML = "×";
		liClose.id = buttonId;
		liClose.onclick = function () {
			var myTab = document.getElementById("myTab");
			var myTabLi = myTab.getElementsByTagName("li");
			if (myTabLi.length > 1) {
				document.getElementById("tabTemplate" + this.id.substring(6, 20)).remove();
				
				if (this.parentNode.nextSibling ==  null) {
					this.parentNode.previousSibling.firstChild.click();
					this.parentNode.remove();
				} else {
					this.parentNode.nextSibling.firstChild.click();
					this.parentNode.remove();
				}
			}
		}
		/* ----close newTab end---- */
		if (name == "") {
			href_button.innerHTML = "New Tab"; //链接显示文本（相当于标签标题）
		} else {
			href_button.innerHTML = name;
		}
		href_button.id = buttonId;

		href_button.onclick = function() {
			var allDiv = document.getElementsByTagName("div");
			for (i = 0; i < allDiv.length; i++) {
				if (allDiv[i].id.indexOf("tabTemplate") == 0) {
					if (allDiv[i].id.substring(11, 26) == this.id.substring(6,
							20)) {
						allDiv[i].style.display = "block";
						this.style.color = "#f47023";
						this.style.fontWeight = 'bold';
					} else {
						allDiv[i].style.display = "none";
						document.getElementById("button" + allDiv[i].id.substring(11, 26)).style.color = "black";
						document.getElementById("button" + allDiv[i].id.substring(11, 26)).style.fontWeight = "normal";
					}
				}
			}
		}
		
		//双击删除
		href_button.ondblclick = function() {
			var myTab = document.getElementById("myTab");
			var myTabLi = myTab.getElementsByTagName("li");
			if (myTabLi.length > 1) {
				document.getElementById("tabTemplate" + this.id.substring(6, 20)).remove();
				
				if (this.parentNode.nextSibling ==  null) {
					this.parentNode.previousSibling.firstChild.click();
					this.parentNode.remove();
				} else {
					this.parentNode.nextSibling.firstChild.click();
					this.parentNode.remove();
				}
			}
		}
		//href_button hover 显示title
		href_button.onmouseover = function () {
			this.title = this.innerHTML;
		}
		
		li.appendChild(href_button);//将button添加到li 
		li.appendChild(liClose);
		ul.appendChild(li); //将li添加到ul
		
		var ulWidth = ul.offsetWidth;
		li.style.width = (ulWidth - 94) / 8 + 'px';
		var liWidth = li.style.width = (ulWidth - 94) / 8;
		href_button.style.width = (liWidth - 32) + 'px';
		

		var template = document.getElementById("template"); //获取模版
		var sonDiv = document.createElement("div"); //创建li中的链接a
		sonDiv.innerHTML = template.innerHTML;
		sonDiv.id = divId;
		sonDiv.className = "tab-pane active";
		sonDiv.setAttribute("name", "parentDiv");
		tabContent.appendChild(sonDiv);
		$(sonDiv).find("#formData")[0].checked="checked";
		//显示当前添加的tab
		var myTab = document.getElementById("myTab");
		var allLi = myTab.getElementsByTagName("li");
		var lastLi = allLi[allLi.length - 1];
		lastLi.firstChild.click();
		return divId;
		
	}

	function RndNum(n) {
		var rnd = "";
		for (var i = 0; i < n; i++) {
			rnd += Math.floor(Math.random() * 10);
		}
		return rnd;
	}
</script>