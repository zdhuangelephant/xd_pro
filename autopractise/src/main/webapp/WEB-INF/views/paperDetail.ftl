<!--2015/7/8-->
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>试卷作答</title>
	<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
</head>
<body>
	<form action="/submit/${paperId}" method="post" name="paperForm">
		${pageInfo}
	</form>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url: location.origin + '/fetch_ques/${paperId}',
			success: function(data) {
				if(data){
					data = JSON.parse(data);
					if(data.status == "Fail!") {
						return;
					} else {
						$('input[name="id"]').each(function () {
							var ques = data[$(this).val()];
							if(!ques.quesValue) {
								return;
							}
							if('radio' == ques.quesType){
								$('input[name="'+ques.quesId+'"][value="'+ques.quesValue+'"]').attr("checked",true); 
							} else if('checkbox' == ques.quesType){
								var values = ques.quesValue.split(",");
								for (var i=0;i<values.length ;i++ ) 
								{ 
									$('input[name="'+ques.quesId+'"][value="'+values[i]+'"]').attr("checked",true); 
								}
							} else {
								$('textarea[name="'+ques.quesId+'"]').val(ques.quesValue);
							}
						});
					}
				}
			}
		})
	});
</script>
</html>