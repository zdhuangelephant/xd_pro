package com.xiaodou.resources.web.controller.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.request.reward.H5OrderGiftRequest;
import com.xiaodou.resources.request.reward.RewardResourcesRequest;
import com.xiaodou.resources.service.reward.RewardService;
import com.xiaodou.summer.web.BaseController;

/**
 * @name @see com.xiaodou.forum.web.controller.RewardController.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description 赞赏控制器
 * @version 1.0
 */
@Controller("rewardController")
@RequestMapping("/reward")
public class RewardController extends BaseController {

  @Resource
  RewardService rewardService;

  /**
   * 生成赞赏订单
   * 
   * @param pojo
   * @return
   */
  @RequestMapping("reward_order")
  @ResponseBody
  public String rewardOrder(H5OrderGiftRequest pojo) {
    return rewardService.rewardOrder(pojo).toString0();
  }

  /**
   * 赞赏资源记录查询
   * 
   * @param body
   * @return
   */
  @RequestMapping("/detail_reward_resources")
  public @ResponseBody
  String detailRewardResources(RewardResourcesRequest pojo) {
    return FastJsonUtil.toJson(rewardService.detailRewardResources(pojo));
  }

}
