package com.xiaodou.ms.web.controller.user;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.user.UserFeedBackModel;
import com.xiaodou.ms.service.user.UserFeedBackService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.user.UserFeedbackEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;


/**
 * @name @see com.xiaodou.ms.web.controller.user.UserFeedBackController.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2017年11月09日
 * @description 用户反馈Controller
 * @version 1.0
 */
@Controller("userFeedBackController")
@RequestMapping("/userFeedBack")
public class UserFeedBackController extends BaseController {
	@Resource
	UserFeedBackService userFeedBackService;

	/**
	 * 反馈列表，查询
	 */
	@RequestMapping("/list")
	public ModelAndView resourceMerchant(Integer page,String appVersion,String handleStatus) {
		try {
			page = (null == page || 0 == page) ? 1 : page;
			ModelAndView modelAndView = new ModelAndView("/user/feedBackList");
//			Page<UserFeedBackModel> feedBackLists = userFeedBackService.queryAllUserFeedBack(page,appVersion);
			Page<UserFeedBackModel> feedBackLists = userFeedBackService.queryFeedBackWithPhone(page, appVersion, handleStatus);
			modelAndView.addObject("feedBackLists", feedBackLists);
			
			Set<String> serach_appVersion_list = userFeedBackService.findAllAppVersion();
			modelAndView.addObject("serach_appVersion_list",serach_appVersion_list);
			if(StringUtils.isNotBlank(appVersion)) {
			  modelAndView.addObject("appVersion", appVersion);
			}
			// 获取所有的版本号
			return modelAndView;
		} catch (Exception e) {
			LoggerUtil.error("列表异常", e);
			throw e;
		}
	}
	
	
	
	// detail
	@RequestMapping("/detail")
	public ModelAndView feedBackDetail(Integer id) {
		try {
			ModelAndView modelAndView = new ModelAndView("/user/userFeedBackDetail");
			UserFeedBackModel userFeedBack = userFeedBackService.findUserById(id);
			if(userFeedBack != null) {
				List<String> lists = Lists.newArrayList();
				List<String> image_lists = Lists.newArrayList();
				if(StringUtils.isNotBlank(userFeedBack.getCategoryDescs())) {
					try {
						lists = FastJsonUtil.fromJsons(userFeedBack.getCategoryDescs(), new TypeReference<List<String>>() {});
						userFeedBack.setCategoryDescs(lists.toString());
					} catch (Exception e) {
						userFeedBack.setCategoryDescs(StringUtils.EMPTY);
					}
				}
				if(StringUtils.isNotBlank(userFeedBack.getImageUrls())) {
					try {
						image_lists = FastJsonUtil.fromJsons(userFeedBack.getImageUrls(), new TypeReference<List<String>>() {});;
						userFeedBack.setTmpImageUrl(image_lists);
					} catch (Exception e) {
						userFeedBack.setTmpImageUrl(null);
					}
				}
			}
			modelAndView.addObject("userFeedBack", userFeedBack);
			return modelAndView;
		} catch (Exception e) {
			LoggerUtil.error("详情列表异常", e);
			throw e;
		}
	}
	
	   /**
     * 反馈列表，查询
     */
    @RequestMapping("/doEdit")
    public ModelAndView doEdit(UserFeedBackModel model,UserFeedbackEditRequest request, Errors errors) {

        try {
          BaseResponse response = null;
          errors = request.validate();
          if (errors.hasErrors()) {
            response = new BaseResponse(ResultType.VALID_FAIL);
            response.setRetDesc(this.getErrMsg(errors));
          } else {
            response = userFeedBackService.editFeedback(request);
          }
          if (response.getRetCode() == 0) {
            return this.showMessage("修改成功", "", "/userFeedBack/detail?id=" + request.getId(), true);
          } else {
            return this.showMessage("修改失败", response.getRetDesc(),
                "/userFeedBack/detail?id=" + request.getId(), true);
          }
        } catch (Exception e) {
          LoggerUtil.error("保存用户反馈信息异常", e);
          throw e;
        }
    }
	

}
