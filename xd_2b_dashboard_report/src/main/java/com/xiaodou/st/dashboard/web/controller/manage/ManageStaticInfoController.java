package com.xiaodou.st.dashboard.web.controller.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.constants.enums.SyncResult;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncAdmissionCardCodeDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncApplyDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncStudentDO;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.st.dashboard.util.SyncLogUtil;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("manageStaticInfoController")
@RequestMapping("/manage")
public class ManageStaticInfoController extends BaseController {
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  @RequestMapping("/static_info")
  public ModelAndView staticInfo(SyncLogDO syncLogDO) {
    ModelAndView modelAndView = new ModelAndView("/manage/staticinfo/staticInfo");
    modelAndView.addObject("adminUser", super.getAdminUser());
    modelAndView.addObject("syncLogDO", syncLogDO);
    modelAndView.addObject("staticInfo", manageStaticInfoService.staticInfo());
    List<SyncLogDO> listSyncLog = manageStaticInfoService.listSyncLog(syncLogDO);
    if (null != listSyncLog && !listSyncLog.isEmpty()) for (SyncLogDO syncLog : listSyncLog) {
      if (SyncResult.SyncWait.getCode().equals(syncLog.getSyncResult())) {
        syncLog.setTotalCount(SyncLogUtil.getTotalCount(syncLog.getSyncId().toString()));
        syncLog.setCompleteCount(SyncLogUtil.getCompleteCount(syncLog.getSyncId().toString()));
      }
    }
    modelAndView.addObject("listSyncLog", listSyncLog);
    modelAndView.addObject("goEasySubscribekey", StaticInfoProp.goEasySubscribekey());
    return modelAndView;
  }

  @RequestMapping("/list_sync_log")
  @ResponseBody()
  public String listSyncLog(SyncLogDO syncLogDO){
    List<SyncLogDO> listSyncLog = manageStaticInfoService.listSyncLog(syncLogDO);
    if (null != listSyncLog && !listSyncLog.isEmpty()) for (SyncLogDO syncLog : listSyncLog) {
      if (SyncResult.SyncWait.getCode().equals(syncLog.getSyncResult())) {
        syncLog.setTotalCount(SyncLogUtil.getTotalCount(syncLog.getId().toString()));
        syncLog.setCompleteCount(SyncLogUtil.getCompleteCount(syncLog.getId().toString()));
      }
    }
    return FastJsonUtil.toJson(listSyncLog);
  }
  
  @RequestMapping("/update_static_info")
  @ResponseBody()
  public String updateStaticInfo(StaticInfoDO staticInfoDO) {
    return manageStaticInfoService.updateStaticInfo(staticInfoDO);
  }

  /**
   * @throws ParseException
   *******************************/

  @RequestMapping("/download_excel")
  public void downloadExcel(Short syncType, String syncId, String syncTime,
      HttpServletResponse response) throws ParseException {
    String dateFileName =
        new SimpleDateFormat("yyyyMMddhhmmss").format(DateUtil.SDF_FULL.parse(syncTime)).toString();
    switch (syncType) {
      case Constants.SYNC_STUDENT:
        List<SyncStudentDO> listSycnStudent = manageStaticInfoService.listSyncStudent(syncId);
        manageStaticInfoService.downloadStudentExcel("student", "student" + dateFileName,
            listSycnStudent, response);
        break;
      case Constants.SYNC_APPLY:
        List<SyncApplyDO> listSycnApply = manageStaticInfoService.listSyncApply(syncId);
        manageStaticInfoService.downloadApplyExcel("apply", "apply" + dateFileName, listSycnApply,
            response);
        break;
      case Constants.SYNC_CARD:
        List<SyncAdmissionCardCodeDO> listSycnCard = manageStaticInfoService.listSyncCard(syncId);
        manageStaticInfoService.downloadCardExcel("admissionCardCode", "admissionCardCode"
            + dateFileName, listSycnCard, response);
        break;
      default:
        break;
    }
  }
  
  @RequestMapping("/batch_score_by_apply")
  @ResponseBody()
  public String batchScoreByApply(){
    return manageStaticInfoService.batchScoreByApply();
  }
}
