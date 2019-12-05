package com.xiaodou.resources.service.quesbk;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.resources.vo.mq.ModifyScoreDateVo;
import com.xiaodou.resources.vo.mq.UpdateUserScoreVo;

@Service("asyncMessageService")
public class AsyncMessageService extends AbstractQuesService {

  @Resource
  QuesOperationFacade quesOperationFacade;

  // @Resource(name = "quesRecordService")
  // QuesRecordService quesRecordService;
  //
  // public MessageRemoteResult modifyScoreByDate(JMSGPojo pojo) {
  // MessageRemoteResult messageRemoteResult =
  // new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
  // if (StringUtils.isJsonBlank(pojo.getMessage())) {
  // LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
  // messageRemoteResult.setExceptionMessgage("消息体为空。");
  // return messageRemoteResult;
  // }
  // ModifyScoreDateVo scoreDate = FastJsonUtil.fromJson(pojo.getMessage(),
  // ModifyScoreDateVo.class);
  // boolean flag = quesRecordService.modifyScoreByDate(scoreDate);
  // if (!flag) return new MessageRemoteResult(MessageRemoteResultType.FAIL);
  // return messageRemoteResult;
  // }

  public MessageRemoteResult updateUserScore(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    UpdateUserScoreVo updateVo = FastJsonUtil.fromJson(pojo.getMessage(), UpdateUserScoreVo.class);
    if (StringUtils.isBlank(updateVo.getUserId(), updateVo.getProductId()))
      return new MessageRemoteResult(MessageRemoteResultType.FAIL);
    quesOperationFacade.updateUserScore(updateVo.getProductId(), updateVo.getUserId());
    return messageRemoteResult;
  }

}
