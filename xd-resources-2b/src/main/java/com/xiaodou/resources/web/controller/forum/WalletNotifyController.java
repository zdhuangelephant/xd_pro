package com.xiaodou.resources.web.controller.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.resources.service.reward.RewardService;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.wallet.agent.request.WalletNotifyRequest;

/**
 * @name @see com.xiaodou.forum.web.controller.WalletNotifyController.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description wallet回调控制器
 * @version 1.0
 */
@RequestMapping(value = "wallet_notify", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
@Controller("walletNotifyController")
public class WalletNotifyController extends BaseController {

  private static final String SUCCESS = "\"success\"";

  @Resource
  RewardService rewardService;

  @RequestMapping("notify")
  @ResponseBody
  public String _notify(@RequestBody WalletNotifyRequest request) {
    try {
      rewardService._nofity(request);
    } catch (Exception e) {}
    return SUCCESS;
  }

}
