package com.xiaodou.dashboard.web.controller.trace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dashboard.model.trace.TraceModel;
import com.xiaodou.dashboard.service.trace.TraceService;

/**
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2018年4月26日
 * @description 业务流controller
 * @version 1.0
 */
@Controller
@RequestMapping("/trace")
public class TraceController {
	@Resource
	TraceService traceService;

	
	/**
	 * 主页
	 * 
	 * @param pojo
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
	    Map<String, Object> map=new HashMap<String, Object>();
	    ModelAndView modelAndView=new ModelAndView("/trace/traceChart");
	    return modelAndView;
	}
	
	/**
	 * 构图接口
	 * 
	 * @param pojo
	 * @return
	 */
	@RequestMapping("/traceChart")
	@ResponseBody
	public String traceChart(String traceId) {
	    Map<String, Object> map=new HashMap<String, Object>();
	    try{
		    if(StringUtils.isBlank(traceId)){
			    map.put("state", "fail");
			    map.put("data", "traceId不能为空");
			    return FastJsonUtil.toJson(map);
		    }
		    List<TraceModel> traceList=traceService.getTraceList(traceId);
		    map.put("state", "success");
		    map.put("traceList", traceList);
		    map.put("traceId", traceId);
	    }catch(Exception e){
	    	map.put("state", "fail");
	    	map.put("data", e.toString());
		    return FastJsonUtil.toJson(map);
	    }
	    return FastJsonUtil.toJson(map);
	}

}
