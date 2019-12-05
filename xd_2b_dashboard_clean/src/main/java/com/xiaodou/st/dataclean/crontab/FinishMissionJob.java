package com.xiaodou.st.dataclean.crontab;

import java.util.Map;

import com.xiaodou.st.dataclean.constant.Constant;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.st.dataclean.crontab.UserLearnRecordJob.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年10月17日
 * @description 每小时更新学习记录
 * @crontab
 * @version 1.0
 */
public class FinishMissionJob {

  private QueueService queueService;

  public void work() {
    queueService = SpringWebContextHolder.getBean("queueService");
    Map<String, RawDataFinishMissionModel> finishMissionMap = Constant.FinishMissionMap;
    for (Map.Entry<String, RawDataFinishMissionModel> entry : finishMissionMap.entrySet()) {
      queueService.pushFinishMissionJob(entry.getValue());
    }
    Constant.FinishMissionMap.clear();
  }
}
