package com.xiaodou.autotest.web.controller.dataSource;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.xiaodou.autotest.web.controller.BaseController;
import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.model.jsfunction.JSFunction;
import com.xiaodou.autotest.web.request.DataSourceRequest;
import com.xiaodou.autotest.web.request.JsFunctionRequest;
import com.xiaodou.autotest.web.service.datasource.DataSourceService;
import com.xiaodou.autotest.web.service.facade.RequestServiceFacade;
import com.xiaodou.autotest.web.service.jsfunction.JSFunctionService;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.login.model.Admin;
import com.xiaodou.login.service.AdminService;

/**
 * Created by zhouhuan on 17.08.15.
 */
@Controller("dataSourceController")
@RequestMapping("/dataSource")
public class DataSourceController extends BaseController {
	@Resource
	RequestServiceFacade requestServiceFacade;
	@Resource
	DataSourceService dataSourceService;
	@Resource
	JSFunctionService jSFunctionService;

	@Resource
	AdminService adminService;

	@RequestMapping("/list")
	public ModelAndView dataSourceList() {
		ModelAndView modelAndView = new ModelAndView("dataSource/dataSource");
		Map<String, Object> input = Maps.newHashMap();
		List<DataSourceData> dataSourceList = requestServiceFacade
				.getDataSourceDataByCond(input);
		List<JSFunction> jsFunList = jSFunctionService.findListByCond();
		if (null != jsFunList && jsFunList.size() > 0) {
			for (JSFunction jsFunction : jsFunList) {
				Admin admin = adminService.findAdminById(jsFunction.getUid());
				jsFunction.setUsername(admin.getRealName());
			}
		}
		modelAndView.addObject("dataSourceList", dataSourceList);
		modelAndView.addObject("jsFunList", jsFunList);
		return modelAndView;
	}

	/**
	 * 新增或者修改
	 * 
	 * @author zhouhuan
	 * @return
	 */
	@RequestMapping("/addOrUpdata")
	@ResponseBody
	public String addOrUpdata(DataSourceRequest data) {
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			if (StringUtils.isBlank(data.getId())) {
				dataSourceService.insertDataSource(data);
			} else {
				dataSourceService.updateDataSource(data);
			}
			dataSourceService.registDataSource(data);
			map.put("status", "success");
			return FastJsonUtil.toJson(map);
		} catch (Exception e) {
			map.put("status", "fail");
			map.put("data", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除
	 * 
	 * @author zhouhuan
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(DataSourceRequest data) {
		Map<String, Object> map = Maps.newLinkedHashMap();
		try {
			dataSourceService.removeDataSource(data.getId());
			map.put("status", "success");
			return FastJsonUtil.toJson(map);
		} catch (Exception e) {
			map.put("status", "fail");
			map.put("data", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}

	/****************************** JS-Functions *****************************/

	@RequestMapping("/jsFunclist")
	public ModelAndView jsFunclist() {
		ModelAndView modelAndView = new ModelAndView("dataSource/dataSource");
		Map<String, Object> input = Maps.newHashMap();
		List<DataSourceData> dataSourceList = requestServiceFacade
				.getDataSourceDataByCond(input);
		modelAndView.addObject("dataSourceList", dataSourceList);
		return modelAndView;
	}

	/**
	 * 新增或者修改
	 * 
	 * @author zhouhuan
	 * @return
	 */
	@RequestMapping("/jsFuncAddOrUpdata")
	@ResponseBody
	public String jsFuncAddOrUpdata(JsFunctionRequest request) {
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			if (null == request.getId()) {
				request.setUid(getUserId());
				jSFunctionService.insertJSFunction(request);
			} else {
				jSFunctionService.updateDataSource(request);
			}
			jSFunctionService.registJsFunctions(request);
			map.put("status", "1");
			return FastJsonUtil.toJson(map);
		} catch (Exception e) {
			map.put("status", "0");
			map.put("errmsg", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}

	/**
	 * 删除
	 * 
	 * @author zhouhuan
	 * @return
	 */
	@RequestMapping("/jsFuncDelete")
	@ResponseBody
	public String jsFuncDelete(Long id) {
		Map<String, Object> map = Maps.newConcurrentMap();
		try {
			jSFunctionService.removeJSFunctions(id);
			map.put("status", "1");
			return FastJsonUtil.toJson(map);
		} catch (Exception e) {
			map.put("status", "0");
			map.put("errmsg", e.toString());
		}
		return FastJsonUtil.toJson(map);
	}

	
	@RequestMapping("/getJSFuncsWithJson")
	@ResponseBody
    public String getJSFuncsWithJson() {
        List<JSFunction> lists = jSFunctionService.findListByCond();
        if(null != lists && lists.size() != 0) {
          return FastJsonUtil.toJson(lists);
        }
        return null;
    }
}
