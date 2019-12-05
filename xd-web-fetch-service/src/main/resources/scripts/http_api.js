try{
	/**
	* Base64 encode / decode
	*/
	function Base64() {
	 // private property
	 _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	 // public method for encoding
	 this.encode = function (input) {
	  var output = "";
	  var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
	  var i = 0;
	  input = _utf8_encode(input);
	  while (i < input.length) {
	   chr1 = input.charCodeAt(i++);
	   chr2 = input.charCodeAt(i++);
	   chr3 = input.charCodeAt(i++);
	   enc1 = chr1 >> 2;
	   enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
	   enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
	   enc4 = chr3 & 63;
	   if (isNaN(chr2)) {
	    enc3 = enc4 = 64;
	   } else if (isNaN(chr3)) {
	    enc4 = 64;
	   }
	   output = output +
	   _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
	   _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
	  }
	  return output;
	 }
	 // public method for decoding
	 this.decode = function (input) {
	  var output = "";
	  var chr1, chr2, chr3;
	  var enc1, enc2, enc3, enc4;
	  var i = 0;
	  input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
	  while (i < input.length) {
	   enc1 = _keyStr.indexOf(input.charAt(i++));
	   enc2 = _keyStr.indexOf(input.charAt(i++));
	   enc3 = _keyStr.indexOf(input.charAt(i++));
	   enc4 = _keyStr.indexOf(input.charAt(i++));
	   chr1 = (enc1 << 2) | (enc2 >> 4);
	   chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
	   chr3 = ((enc3 & 3) << 6) | enc4;
	   output = output + String.fromCharCode(chr1);
	   if (enc3 != 64) {
	    output = output + String.fromCharCode(chr2);
	   }
	   if (enc4 != 64) {
	    output = output + String.fromCharCode(chr3);
	   }
	  }
	  output = _utf8_decode(output);
	  return output;
	 }
	 // private method for UTF-8 encoding
	 _utf8_encode = function (string) {
	  string = string.replace(/\r\n/g,"\n");
	  var utftext = "";
	  for (var n = 0; n < string.length; n++) {
	   var c = string.charCodeAt(n);
	   if (c < 128) {
	    utftext += String.fromCharCode(c);
	   } else if((c > 127) && (c < 2048)) {
	    utftext += String.fromCharCode((c >> 6) | 192);
	    utftext += String.fromCharCode((c & 63) | 128);
	   } else {
	    utftext += String.fromCharCode((c >> 12) | 224);
	    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
	    utftext += String.fromCharCode((c & 63) | 128);
	   }
	  }
	  return utftext;
	 }
	 // private method for UTF-8 decoding
	 _utf8_decode = function (utftext) {
	  var string = "";
	  var i = 0;
	  var c = c1 = c2 = 0;
	  while ( i < utftext.length ) {
	   c = utftext.charCodeAt(i);
	   if (c < 128) {
	    string += String.fromCharCode(c);
	    i++;
	   } else if((c > 191) && (c < 224)) {
	    c2 = utftext.charCodeAt(i+1);
	    string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
	    i += 2;
	   } else {
	    c2 = utftext.charCodeAt(i+1);
	    c3 = utftext.charCodeAt(i+2);
	    string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
	    i += 3;
	   }
	  }
	  return string;
	 }
	}
	var page = require('webpage').create(),
	  system = require('system'),
	  t, json;
	 
	if (system.args.length === 1) {
	  console.log('{"retcode":"00001","retdesc":"Usage: speed.js <some URL>."}');
	  phantom.exit();
	}
	page.settings.loadImages = false;  // 为了提升加载速度，不加载图片
	page.settings.resourceTimeout = 10000;// 超过10秒放弃加载
	// 此处是用来设置截图的参数。不截图没啥用
	page.viewportSize = {
	  width: 1280,
	  height: 800
	};
	block_urls = ['baidu.com'];// 为了提升速度，屏蔽一些需要时间长的。比如百度广告
	page.onResourceRequested = function(requestData, request){
	    for(url in block_urls) {
	        if(requestData.url.indexOf(block_urls[url]) !== -1) {
	            request.abort();
	            return;
	        }
	    }
	}
	start = Date.now();
	json = JSON.parse(system.args[1]);
	page.open(json.url, function(status) {
		try{
			if (status !== 'success') {
			console.log('{"retcode":"-1","retdesc":"Load Fail."}');
		} else {
			cost = Date.now() - start;
			console.log('{"retcode":"200","retdesc":"Success.","cost":"'+ cost +'", "content":"'+ new Base64().encode(page.content) +'"}');
		}
		}catch(err){
			console.log('{"retcode":"-1","retdesc":"'+ err.name +'", "message":"'+ err.message +'"}');
		}finally{
			phantom.exit();
		}
	});
}catch(err){
	console.log('{"retcode":"-1","retdesc":"'+ err.name +'", "message":"'+ err.message +'"}');
	phantom.exit();
}