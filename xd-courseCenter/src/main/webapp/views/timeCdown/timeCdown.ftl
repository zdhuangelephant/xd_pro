<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="full-screen" content="yes" />
<meta name="screen-orientation" content="portrait" />
<meta name="x5-fullscreen" content="true" />
<meta name="360-fullscreen" content="true" />
<title>倒计时</title>
<link href="${baseCssOP}/tdcss.css?${timeStamp}" rel="stylesheet" type="text/css"/>
<link href="${baseCssOP}/tdstyle.css?${timeStamp}" rel="stylesheet" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Atomic+Age'
	rel='stylesheet' type='text/css'>
</head>
<body >
	<div class="main" style="background: url('${baseImgOP}/tdback.jpg');">
		<div id="text" class="text">距离考试还有</div>
		<input id="yearTime" type="hidden" value="${yearTime}">
		<input id="monthTime" type="hidden" value="${monthTime}">
		<div class="box">

		<div class="EightycloudsFlipTimer">
			<div class="block days">
				<div class="block_left">
					<div class="block_left_top">
						<div class="block_left_top_count">8</div>
					</div>
					<div class="block_middle_separator" style="background-image:url('${baseImgOP}/middle_separator.png');"></div>
					<div class="block_left_bottom">
						<div class="block_left_bottom_count">8</div>
					</div>
					<div class="block_effect1 left"></div>
					<div class="block_effect2"></div>
					<div class="block_effect3"></div>
					<div class="block_effect4"></div>
					<div class="block_effect5 left"></div>
					<div class="block_effect6 left"></div>
				</div>
				<div class="block_mid">
					<div class="block_mid_top">
						<div class="block_mid_top_count">8</div>
					</div>
					<div class="block_middle_separator" style="background-image:url('${baseImgOP}/middle_separator.png');"></div>
					<div class="block_mid_bottom">
						<div class="block_mid_bottom_count">8</div>
					</div>
					<div class="block_effect1"></div>
					<div class="block_effect2"></div>
					<div class="block_effect3"></div>
					<div class="block_effect4"></div>
					<div class="block_effect5 mid" ></div>
					<div class="block_effect6 mid" ></div>
				</div>
				<div class="block_right">
					<div class="block_right_top">
						<div class="block_right_top_count">8</div>
					</div>
					<div class="block_middle_separator" style="background-image:url('${baseImgOP}/middle_separator.png');"></div>
					<div class="block_right_bottom">
						<div class="block_right_bottom_count">8</div>
					</div>
					<div class="block_effect1"></div>
					<div class="block_effect2"></div>
					<div class="block_effect3"></div>
					<div class="block_effect4"></div>
					<div class="block_effect5 mid" ></div>
					<div class="block_effect6 mid" ></div>
					<!-- <div class="block_effect5 right" style="display: none; top: 3px; height: 60px; left: 3px;"></div>
					<div class="block_effect6 right" style="display: none; height: 0px; left: 10px;"></div> -->
				</div>
				
			</div>
		</div>


			<div class="day">天</div>
		</div>
		<div class="time" >
			<div class="hour">88</div>
			<span>时</span>
			<div class="minute">88</div>
			<span>分</span>
			<div class="second">88</div>
			<span>秒</span>
		</div>
	</div>

	<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
	<script type="text/javascript" src="${baseJsOP}/dateUtil.js?${timeStamp}"></script>
	<script type="text/javascript" src="${baseJsOP}/timeCountDown.js?${timeStamp}"></script>

</body>
</html>