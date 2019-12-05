package com.xiaodou.resources.service.reward;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.async.toC.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.toC.model.AddGiftMessage;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.enums.forum.DigestType;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.reward.RewardRecord;
import com.xiaodou.resources.request.forum.ForumDetailForumRequest;
import com.xiaodou.resources.request.reward.H5OrderGiftRequest;
import com.xiaodou.resources.request.reward.RewardResourcesRequest;
import com.xiaodou.resources.response.forum.ForumDetailForumResponse;
import com.xiaodou.resources.service.forum.ForumDetailService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.wallet.agent.request.WalletNotifyRequest;
import com.xiaodou.wallet.agent.response.GiftOrderResponse;
import com.xiaodou.wallet.agent.service.WalletService;
import com.xiaodou.wallet.agent.util.WalletConfig;

/**
 * @name @see com.xiaodou.forum.service.reward.RewardService.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年9月18日
 * @description 赞赏Service
 * @version 1.0
 */
@Service("rewardService")
public class RewardService {

  @Resource
  ForumDetailService forumDetailService;

  @Resource
  ForumServiceFacade forumServiceFacade;

  /**
   * 生成赞赏账单
   * 
   * @param request
   * @return
   */
  public GiftOrderResponse rewardOrder(H5OrderGiftRequest pojo) {
    String callbackUrl = WalletConfig.getParams("gift.notifyUrl");
    RewardRecord record = forumServiceFacade.insertResourceReward(pojo);
    return WalletService.giftOrder(record.getId(), pojo.formatProductLine(), pojo.getUid(),
        pojo.getTargetUserId(), pojo.getGiftType(), pojo.getGiftMoney(), callbackUrl,
        pojo.getH5Type(), pojo.getWeixinName(), pojo.getWeixinPortrait(), pojo.getPayType(),pojo.getRate());
  }

  public void _nofity(WalletNotifyRequest pojo) {
    if (null == pojo || StringUtils.isBlank(pojo.getResourceId())
        || null == pojo.getOperationCode()) return;
    RewardRecord record = forumServiceFacade.quertResourceRewardById(pojo.getResourceId());
    if (null == record) return;
    RewardRecord newecord = new RewardRecord();
    newecord.setId(pojo.getResourceId());
    newecord.setOperateType(pojo.getOperationCode());
    newecord.setOperateDesc(pojo.getOperationDesc());
    forumServiceFacade.updateResourceRewardById(newecord);
    Map<String, Object> queryCond = Maps.newHashMap();
    queryCond.put("id", record.getRecordId());
    List<ForumModel> forumList =
        forumServiceFacade.queryForumList(queryCond, CommUtil.getBasicField(ForumModel.class));
    if (null == forumList || forumList.size() == 0) return;
    ForumModel model = forumList.get(0);
    AddGiftMessage message = new AddGiftMessage();
    message.setSrcUid(record.getUserId());
    message.setToUid(record.getTargetUserId());
    message.setModule(record.getModule());
    message.setRetCode(AsyncResInfo.AddGift.getRetCode());
    String digest = StringUtils.EMPTY;
    switch (model.getDigest()) {
      case ForumConstant.TALK:
        digest = DigestType.TALK.getDesc();
        break;
      case ForumConstant.ACTICLE:
        digest = DigestType.ACTICLE.getDesc();
        break;
      case ForumConstant.VIDEO:
        digest = DigestType.VIDEO.getDesc();
        break;
      default:
        break;
    }
    message.setRetDesc(String.format(AsyncResInfo.AddGift.getRetDesc(), digest, model.getTitle(),
        record.getGiftMoney()));
    message.setMessageBody(String.format(AsyncResInfo.AddGift.getMesTmp(), digest,
        model.getTitle(), record.getGiftMoney()));
    message.addCallBackContent("resourceId", model.getId());
    message.addCallBackContent("digestType", model.getDigest().toString());
    message.addCallBackContent("name", model.getTitle());
    message.send();
  }

  public ForumDetailForumResponse detailRewardResources(RewardResourcesRequest pojo) {
    RewardRecord record = forumServiceFacade.quertResourceRewardById(pojo.getRewardId());
    ForumDetailForumRequest request = new ForumDetailForumRequest();
    request.setModule(pojo.getModule());
    request.setUid(pojo.getUid());
    request.setResourcesId(record.getRecordId());
    return forumDetailService.forumContent(request);
  }

}
