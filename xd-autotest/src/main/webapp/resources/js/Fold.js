(function($) {
	$.fn.Fold = function(options) {
		// 默认参数设置
		var settings = {
			speed : 200
		// 折叠速度(值越大越慢)
		};

		// 不为空则合并参数
		if (options)
			$.extend(settings, options);

		// 为每个p元素绑定点击事件
		$("> p", this).bind("click", function (e) {
			//e.stopPropagation();
			$(this).children(".r-option-wrap").show();
			$(this).next("ul").slideToggle('fast');
			$(this).toggleClass('green' ? 'red' : 'green');
		})

		// 鼠标经过 按钮 显示隐藏
		$("> ul>div", this).bind({
			click : function(e) {
				e.stopPropagation();
			},
			mouseover : function() {
				$(this).find('.more-option').show();
				$(this).css('background-color', '#fafafa')
			},
			mouseout : function() {
				$(this).find('.more-option').hide();
				$(this).css('background-color', '#fff')
			}
		})

		/*
		 * more-option 按钮 按钮开关 点击展开 点击合住
		 * 
		 */
		var onOff = true;
		$("> ul>div", this).children('.more-option').click(function(e) {
			if (onOff) {
				$(this).next('.option-list-wrap').show();
				$(".blue").unbind();
				onOff = false;
			} else {
				$(this).next('.option-list-wrap').hide();
				onOff = true;
				$(".blue").bind({
					mouseover : function() {
						$(this).find('.more-option').show();
						$(this).css('background-color', '#fafafa')
					},
					mouseout : function() {
						$(this).find('.more-option').hide();
						$(this).css('background-color', '#fff')
					}
				})
			}
			// 阻止冒泡
			e.stopPropagation();
		});
		$(".blue").children('.req-name').click(function () {
			$(this).css({"font-weight":"bold", "font-size": "14px"}).parent().parent().siblings().find(".req-name-display").css({"font-weight":"normal", "font-size": "12px"});
			$(this).css({"font-weight":"bold", "font-size": "14px"}).parent().siblings().children(".req-name").css({"font-weight":"normal", "font-size": "12px"});
		
		});

		/*
		 * 点击其他地方 option list合住
		 */
		$(document).on("click", function() {
					$('.option-list-wrap').hide();
					onOff = true;
					$(".blue").find('.more-option').hide();
					$(".blue").css('background-color', '#fff')
					$(".blue").bind({
						mouseover : function() {
							$(this).find('.more-option').show();
							$(this).css('background-color', '#fafafa')
						},
						mouseout : function() {
							$(this).find('.more-option').hide();
							$(this).css('background-color', '#fff')
						}
					})

					$('.green').find('.r-more-option').css('background-color', 'rgba(0,0,0,0)');
					$('.green').find('.r-more-option').bind({
						mouseover : function() {
							$(this).css('background-color', '#dcdcdc')
						},
						mouseout : function() {
							$(this).css('background-color', 'rgba(0,0,0,0)')
						}
					})

					$('.green').css('background-color', '#fff');
					$('.green').children('.r-option-wrap').hide();
					$('.green').bind("mouseover", function () {
						$(this).children('.r-option-wrap').show();
						$(this).css('background-color', '#fafafa');
					}).bind("mouseout", function () {
						$(this).children('.r-option-wrap').hide();
						$(this).css('background-color', '#fff');
					})
				});
		
		//一级p
		$('>p', this).bind("mouseover", function () {
			$(this).children('.r-option-wrap').show();
			$(this).css('background-color', '#fafafa');
		}).bind("mouseout", function () {
			$(this).children('.r-option-wrap').hide();
			$(this).css('background-color', '#fff');
		})
		
		//列表开关按钮 
		$('>p', this).children('.r-option-wrap').click(
				function(e) {
					//ture 展开
					if (onOff) {
						$(this).find('.option-list-wrap').show();
						$(this).find('r-more-option').css('background-color','#dcdcdc');
						//展开取消p的hover事件
						//$('.green').unbind();
						$('.green').unbind("mouseover");
						$('.green').unbind("mouseout");
						$('.green').find('.r-more-option').unbind();
						onOff = false;
					} else {	//false合上
						$(this).find('.option-list-wrap').hide();
						onOff = true;

						$('.green').find('.r-more-option').bind({
								mouseover : function() {$(this).css('background-color','#dcdcdc')},
								mouseout : function() {$(this).css('background-color','rgba(0,0,0,0)')}
							})

						$('.green').bind("mouseover", function () {
							$(this).children('.r-option-wrap').show();
							$(this).css('background-color', '#fafafa');
						}).bind("mouseout", function () {
							$(this).children('.r-option-wrap').hide();
							$(this).css('background-color', '#fff');
						})
					}
					e.stopPropagation();
				})
		
		/*$('>p', this).bind({
			//滑过 右侧显示 背景颜色改变
			mouseover : function() {
				$(this).children('.r-option-wrap').show();
				$(this).css('background-color', '#fafafa');
			},
			//滑出 右侧隐藏 背景颜色恢复
			mouseout : function() {
				$(this).children('.r-option-wrap').hide();
				$(this).css('background-color', '#fff');
			}
		})*/

		//一级功能列表
		$('>p', this).find('.r-more-option').bind({
			//滑过变色
			mouseover : function() {
				$(this).css('background-color', '#dcdcdc')
			},
			//移开透明背景
			mouseout : function() {
				$(this).css('background-color', 'rgba(0,0,0,0)')
			}
		})

		//生成新版本按钮 列表框隐藏
		$('.new-verson').click(function() {
			$(this).parents('.collection-box').find('.option-list-wrap').hide();
		})

		// 默认折叠
		$("> ul", this).hide();

		// 遵循链式原则
		return this.each(function() {
		});
	}
})(jQuery);