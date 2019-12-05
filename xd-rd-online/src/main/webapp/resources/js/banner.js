window.onload = function() {
	function tab(id1, id2, style1, style2) {
		var oBtn = document.getElementById(id1), aBtn = oBtn
				.getElementsByTagName('li'), oItem = document
				.getElementById(id2), aItem = oItem.getElementsByTagName('li');
		for (var i = 0; i < aBtn.length; i++) {
			aBtn[i].index = i;
			aBtn[i].onmouseover = function() {
				for (var i = 0; i < aBtn.length; i++) {
					aBtn[i].className = style1;
					aItem[i].style.display = 'none';
				}
				this.className = style2;
				aItem[this.index].style.display = 'block';
			}
		}
	}
	// 专业课程tab
	tab('course-btn-item1', 'course-list1', 'course-btn', 'active2');
	// 校历tab
	tab('course-btn-item', 'calendar-list', 'course-btn', 'active3');
	// 课程特色tab
	tab('course-chara', 'chara-list', 'chara-btn', 'chara-active');
};
