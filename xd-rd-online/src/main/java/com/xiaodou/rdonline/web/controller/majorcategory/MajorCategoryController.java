package com.xiaodou.rdonline.web.controller.majorcategory;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaodou.rdonline.service.facade.IRdServiceFacade;

/**
* @author zdh:
* @date 2017年6月13日
*
*/
@Controller("majorCategoryController")
@RequestMapping("/majorCategory")
public class MajorCategoryController {
	@Resource
	IRdServiceFacade rdServiceFacade;
	
}
