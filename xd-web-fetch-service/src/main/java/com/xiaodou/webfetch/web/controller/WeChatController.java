package com.xiaodou.webfetch.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.webfetch.web.domain.OfficialAccount;
import com.xiaodou.webfetch.web.service.OfficialAccountService;

/**
 * @name WeChatController 
 * CopyRright (c) 2018 by <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2018年1月17日
 * @description TODO
 * @version 1.0
 */
@Controller("weChatController")
@RequestMapping("weChat")
public class WeChatController extends BaseController {
  @Resource
  OfficialAccountService officialService;
  
  @RequestMapping("execute")
  @ResponseBody
  public String execute(String signature) {
    if (StringUtils.isBlank(signature)) return new ResultInfo(ResultType.VALFAIL).toString0();
    try {
      // 获取所有的文章列表
      List<OfficialAccount> linkLists = officialService.getSuitableArticle();
      
      // 对其文章进行jsoup解析和入库
      officialService.analyizeArticle(linkLists);
      return new ResultInfo(ResultType.SUCCESS).toString0();
    } catch (Exception e) {
      return new ResultInfo(ResultType.SYSFAIL).toString0();
    }
  }
}
