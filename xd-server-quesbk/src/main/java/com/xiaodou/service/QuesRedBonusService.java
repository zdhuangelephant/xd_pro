package com.xiaodou.service;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.util.BonusUtil;
import com.xiaodou.vo.mq.ReceiveAwardMessage;
import com.xiaodou.vo.request.GetAwardPojo;
import com.xiaodou.vo.request.InitAwardPojo;
import com.xiaodou.vo.response.GetAwardResponse;
import com.xiaodou.vo.response.InitAwardResponse;
import com.xiaodou.vo.task.ReceiveAward;

@Service("quesRedBonusService")
public class QuesRedBonusService extends AbstractQuesService {

  /**
   * 初始化红包奖励
   * 
   * @param pojo
   * @return
   */
  public InitAwardResponse initAward(InitAwardPojo pojo) {
    InitAwardResponse response = new InitAwardResponse(ResultType.SUCCESS);
    IQueryParam param = new QueryParam();
    param.addInput("userId", pojo.getUid());
    param.addInput("module", pojo.getModule());
    param.addInput("missionId", pojo.getMissionId());
    param.addSort("statue", Sort.ASC);
    param.addOutputs(CommUtil.getAllField(RedBonus.class));
    Page<RedBonus> redBonusPage = quesOperationFacade.queryRedBonusByCond(param);
    if (null == redBonusPage || null == redBonusPage.getResult()
        || redBonusPage.getResult().size() == 0) {
      receiveTaskAward(pojo.getModule(), pojo.getUid(), pojo.getMissionId(), null);
      return response;
    }
    RedBonus bonus = redBonusPage.getResult().get(0);
    bonus.setTypeCode(pojo.getTypeCode());
    if (QuesBaseConstant.RED_BONUS_STATUS_GET == bonus.getStatue())
      HIS: {
        if (StringUtils.isJsonBlank(bonus.getBonusDetail())) break HIS;
        CourseBonus courseBonus = FastJsonUtil.fromJson(bonus.getBonusDetail(), CourseBonus.class);
        if (null == courseBonus || null == courseBonus.getOriginalScore()) break HIS;
        if (courseBonus.getOriginalScore() < 100d) {
          response.setType(bonus.getRedBonusType());
          response.setBonusId(bonus.getId());
          response.setAward(courseBonus);
        }
        // 临时添加兼容逻辑:兼容积分消息
        else {
          receiveTaskAward(pojo.getModule(), pojo.getUid(), pojo.getMissionId(), null);
        }
      }
    else
      NEW: {
        CourseBonus courseBonus = BonusUtil.fillCourseBonus(bonus);
        if (null == courseBonus || null == courseBonus.getOriginalScore()) break NEW;
        if (courseBonus.getOriginalScore() < 100d) {
          response.setType(bonus.getRedBonusType());
          response.setBonusId(bonus.getId());
          response.setAward(courseBonus);
        } else {
          receiveTaskAward(pojo.getModule(), pojo.getUid(), pojo.getMissionId(), null);
        }
      }
    return response;
  }

  /**
   * 获取红包奖励
   * 
   * @param pojo
   * @return
   */
  public GetAwardResponse getAward(GetAwardPojo pojo) {
    GetAwardResponse response = new GetAwardResponse(ResultType.SUCCESS);
    RedBonus redBonus = quesOperationFacade.queryRedBonusById(pojo.getBonusId());
    if (null == redBonus || QuesBaseConstant.RED_BONUS_STATUS_GET.equals(redBonus.getStatue())) {
      receiveTaskAward(pojo.getModule(), pojo.getUid(), redBonus.getMissionId(), null);
      return response;
    }
    redBonus.setTypeCode(pojo.getTypeCode());
    BonusUtil.useBonus(redBonus);
    receiveTaskAward(pojo.getModule(), pojo.getUid(), redBonus.getMissionId(), redBonus.getId());
    BonusUtil.followUp(redBonus);
    return response;
  }

  private void receiveTaskAward(String module, String uid, String missionId, String bonusId) {
    ReceiveAward award = new ReceiveAward();
    award.setModule(module);
    award.setUserId(uid);
    award.setMissionId(missionId);
    award.setBonusId(bonusId);
    RabbitMQSender.getInstance().send(new ReceiveAwardMessage(award));
  }

}
