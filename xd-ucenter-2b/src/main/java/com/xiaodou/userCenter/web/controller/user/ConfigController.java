package com.xiaodou.userCenter.web.controller.user;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.userCenter.module.selfTaught.request.StConfigRequest;
import com.xiaodou.userCenter.module.selfTaught.service.StConfigService;
import com.xiaodou.userCenter.request.ConfigRequest;

/**
 * @name @see com.xiaodou.userCenter.web.controller.user.ConfigController.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年4月12日
 * @description
 * @version 1.0
 */
@Controller("configController")
@RequestMapping("/config")
public class ConfigController extends BaseController {
  @Resource
  StConfigService configService;

  /**
   * @description 前端每次都需调用的接口
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月12日
   * @param request
   * @return
   */
  @RequestMapping("/version")
  @ResponseBody
  public String configVersion(ConfigRequest request) {
    return configService.configVersion(request).toJsonString();
  }

  /**
   * @description 前端根据逻辑确定时候调用
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月12日
   * @param request
   * @return
   */
  @RequestMapping("/config_v_1_4_9")
  @ResponseBody
  public String config_v_1_4_9(StConfigRequest request) {
    String headVersion = "149";
    return configService.config(request, headVersion).toJsonString();
  }

  /**
   * @description
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年4月12日
   * @param request
   * @return
   */
  @RequestMapping()
  @ResponseBody
  public String config(StConfigRequest request) {
    String headVersion = StringUtils.EMPTY;
    return configService.config(request, headVersion).toJsonString();
  }

}
