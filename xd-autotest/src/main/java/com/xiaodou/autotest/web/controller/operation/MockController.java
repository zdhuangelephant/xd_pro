package com.xiaodou.autotest.web.controller.operation;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;

/**
 * Created by zhouhuan on 17.08.15.
 */
@Controller("MockController")
@RequestMapping("/mock")
public class MockController extends BaseController {
	/**
	 * RUNNER获取case列表
	 * @author zhouhuan
	 * @return
	 */
	@Resource 
	RequestServiceFacade requestServiceFacade;
	@RequestMapping("/**")
	@ResponseBody
	public String runner(HttpServletRequest request, HttpServletResponse response) {
		try{
			String []urls=request.getPathInfo().split("/");
			//检测url规范
			if(urls.length>=4){
				if(StringUtils.isNotBlank(urls[2])){
					Map<String, Object> cond =Maps.newConcurrentMap();
					cond.put("docId", urls[2]);
					List<DocRequest> docRequestList=requestServiceFacade.getDocRequestModelByCond(cond);
					if(docRequestList!=null){
						//取出url正文地址
						String trueUrl=request.getPathInfo().split("/mock/"+urls[2]+"/")[1];
						for(DocRequest docRequest:docRequestList){
							//取出requestUrl正文地址
							String requestTrueUrl=this.getRequestTrueUrl(docRequest.getUrl());
							if(requestTrueUrl.equals(trueUrl)){
								return URLDecoder.decode(new String(Base64Utils.decode(docRequest.getOutParams())), "utf-8");
							}
						}
					}else{
						return "404";
					}
				}		
			}else{
				return "404";
			}	
		    return "404";
		}catch(Exception e){
			return e.toString();
		}
	}
	
	public String getRequestTrueUrl(String url){
		String []urls;
		if(url.startsWith("http://")||url.startsWith("https://")){
			urls=url.split("/", 4);
		}else{
			urls=url.split("/", 2);
		}
		if(urls.length==4){
			return urls[3];
		}if(urls.length==2){
			return urls[1];
		}else
		{
			return "";
		}
	}
	
	
}
