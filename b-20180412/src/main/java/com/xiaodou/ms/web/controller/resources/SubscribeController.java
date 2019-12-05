package com.xiaodou.ms.web.controller.resources;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.enums.Platform;
import com.xiaodou.ms.model.knowledge.ForumAccount;
import com.xiaodou.ms.service.resources.ForumAccountService;
import com.xiaodou.ms.service.resources.SubscribeService;
import com.xiaodou.ms.vo.resources.ColumnSearchVO.DataObj;
import com.xiaodou.ms.vo.resources.WechatOfficialAccountVO;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 资源订阅
 * 
 * @name SubscribeController CopyRright (c) 2018 by
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月23日
 * @description TODO
 * @version 1.0
 */
@Controller("subscribeController")
@RequestMapping("/subscribe")
public class SubscribeController extends BaseController {

  @Resource
  SubscribeService subscribeService;

  @Resource
  ForumAccountService forumAccountService;


  @RequestMapping("/subscribeList")
  public ModelAndView subscribeList(String platformType, String keyword) {
    ModelAndView mv = new ModelAndView("resources/subscribeColumnList");
    if (StringUtils.isBlank(platformType) || platformType.equals(Platform.UNKNOWN.getCode())) {
      return mv;
    }

    if (platformType.equals(Platform.COLUMN_ZHIHU.getCode())) {
      List<DataObj> dataObjList = subscribeService.searchColumn(keyword);
      mv.addObject("dataObjList", dataObjList);
      mv.addObject("platformType", platformType);
      mv.addObject("keyword", keyword);
      return mv;
    }

    if (platformType.equals(Platform.WECHAT_OFFICIAL_ACCOUNT.getCode())) {
      mv.setViewName("resources/subscribeWechatList");
      List<WechatOfficialAccountVO> dataObjList =
          subscribeService.searchWechatOfficialAccount(keyword);
      subscribeService.formatDataObj(dataObjList, keyword);
      mv.addObject("dataObjList", dataObjList);
      mv.addObject("platformType", platformType);
      mv.addObject("keyword", keyword);
      return mv;
    }
    return mv;
  }


  /**
   * 订阅知乎
   * 
   * @param signature
   * @return
   */
  @RequestMapping("doSubscribe")
  @ResponseBody
  public String doSubscribe(String signature, String platformType) {
    BaseResponse response = new BaseResponse();
    try {
      if (StringUtils.isBlank(signature) || StringUtils.isBlank(platformType)) {
        response.setRetCode(-1);
        response.setRetDesc("订阅失败, 签名为空");
        return response.toString();
      } else {
        Page<ForumAccount> pageList = forumAccountService.findEntityByCond(signature, platformType);
        if (pageList == null || pageList.getResult().size() == 0) {
          subscribeService.doSubscribe(signature,platformType);
          response.setRetCode(1);
          response.setRetDesc("订阅成功");
          return response.toString();
        } else {
          response.setRetCode(-1);
          response.setRetDesc("该专栏号已经被订阅，不可重复订阅");
          return response.toString();
        }
      }
    } catch (Exception e) {
      throw e;
    }
  }

  
//  cancelSubscribe
  @RequestMapping("cancelSubscribe")
  @ResponseBody
  public String cancelSubscribe(String signature, String platformType) {
    BaseResponse response = new BaseResponse();
    try {
        Page<ForumAccount> pageList = forumAccountService.findEntityByCond(signature, platformType);
        if (pageList != null && pageList.getResult().size() != 0) {
          if(subscribeService.cancelSubscribe(pageList.getResult().get(0))) {
            response.setRetCode(1);
            response.setRetDesc("已经取消订阅，操作成功");
            return response.toString();
          }else {
            response.setRetCode(-1);
            response.setRetDesc("操作失败");
            return response.toString();
          }
        } else {
          response.setRetCode(-1);
          response.setRetDesc("未订阅过该资源， 操作失败");
          return response.toString();
        }
      
    } catch (Exception e) {
      throw e;
    }
  }


}
