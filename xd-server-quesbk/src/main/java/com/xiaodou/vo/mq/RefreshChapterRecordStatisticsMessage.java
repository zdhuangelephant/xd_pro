package com.xiaodou.vo.mq;

import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.jmsg.entity.AbstractMessagePojo;

/**
 * @name RefreshChapterRecordStatisticsMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 刷新章节记录统计异步消息参数类
 * @version 1.0
 */
public class RefreshChapterRecordStatisticsMessage extends AbstractMessagePojo {

  private static final String MESSAGE_NAME = "refreshChapterRecordStatistics";

  public RefreshChapterRecordStatisticsMessage() {
    super(null);
  }

  public RefreshChapterRecordStatisticsMessage(UserChapterRecord userExamRecord) {
    super(MESSAGE_NAME);
    setTransferObject(userExamRecord);
  }

}
