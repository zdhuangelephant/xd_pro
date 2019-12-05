package com.xiaodou.st.dataclean.crontab;

import java.util.Date;
import java.util.Set;

import com.google.common.collect.Sets;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishAllMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.service.QueueService;
import com.xiaodou.st.dataclean.service.facade.DashBoardServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class CategorySessionPercentJob {
  DashBoardServiceFacade dashBoardServiceFacade;
  QueueService queueService;

  public void work() {
    dashBoardServiceFacade = SpringWebContextHolder.getBean("dashBoardServiceFacade");
    queueService = SpringWebContextHolder.getBean("queueService");
    Set<String> catPilotSet = Sets.newHashSet();
    Page<RawDataFinishAllMissionModel> finishAllMissionModelPage =
        dashBoardServiceFacade.queryAllRawDataFinishAllMissionModel();
    if (null == finishAllMissionModelPage || null == finishAllMissionModelPage.getResult()
        || finishAllMissionModelPage.getResult().size() == 0) return;
    for (RawDataFinishAllMissionModel rawDataFinishAllMissionModel : finishAllMissionModelPage
        .getResult()) {
      String key = getKey(rawDataFinishAllMissionModel);
      if (catPilotSet.contains(key)) continue;
      catPilotSet.add(key);
      queueService.pushFinishMissionJob(packageFinishMissionModel(rawDataFinishAllMissionModel));
    }
  }

  private RawDataFinishMissionModel packageFinishMissionModel(
      RawDataFinishAllMissionModel rawDataFinishAllMissionModel) {
    if (null == rawDataFinishAllMissionModel) return null;
    RawDataFinishMissionModel model = new RawDataFinishMissionModel();
    model.setCreateTime(rawDataFinishAllMissionModel.getCreateTime());
    model.setId(rawDataFinishAllMissionModel.getId());
    model.setModuleId(rawDataFinishAllMissionModel.getModuleId());
    model.setPilotUnitId(rawDataFinishAllMissionModel.getPilotUnitId());
    model.setChiefUnitId(rawDataFinishAllMissionModel.getChiefUnitId());
    model.setProductCategoryId(rawDataFinishAllMissionModel.getProductCategoryId());
    model.setProductId(rawDataFinishAllMissionModel.getProductId());
    model.setRecordTime(DateUtil.SDF_YMD.format(new Date()));
    model.setStudentId(rawDataFinishAllMissionModel.getStudentId());
    model.setTaughtUnitId(rawDataFinishAllMissionModel.getTaughtUnitId());
    model.setUserId(rawDataFinishAllMissionModel.getUserId());
    return model;
  }

  private String getKey(RawDataFinishAllMissionModel model) {
    return String.format("%s:%s", model.getProductCategoryId(), model.getPilotUnitId());
  }

}
