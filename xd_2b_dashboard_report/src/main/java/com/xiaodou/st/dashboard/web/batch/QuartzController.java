package com.xiaodou.st.dashboard.web.batch;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.staticinfo.SyncLogDO;
import com.xiaodou.st.dashboard.service.http.HttpService;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.util.SyncLogUtil;

@Controller("quartzController")
@RequestMapping("/quartz")
public class QuartzController {

  @Resource(name = "httpService")
  HttpService httpService;

  @Resource
  ManageStaticInfoService manageStaticInfoService;

  private static String msg = "上一次同步未完成";
  private static Integer topCount = 3;
  
  @RequestMapping("/student")
  @ResponseBody
  public String quartzStudent() {
    if (!isFinish(Constants.SYNC_STUDENT)) {
      return msg;
    }
    return httpService.quartzStudent();
  }

  @RequestMapping("/apply")
  @ResponseBody
  public String quartzApply() {
    if (!isFinish(Constants.SYNC_APPLY)) {
      return msg;
    }
    return httpService.quartzApply();
  }

  @RequestMapping("/admission_card_code")
  @ResponseBody
  public String syncAdmissionCardCode() {
    if (!isFinish(Constants.SYNC_CARD)) {
      return msg;
    }
    return httpService.quartzAdminssionCardCode();
  }

  public Boolean isFinish(Short type) {
    SyncLogDO lastSyncLogDO = SyncLogUtil.lastSyncLogCacheMap.get(type);
    if (null == lastSyncLogDO) {
      return true;
    }
    boolean flag = SyncLogUtil.getCompleteCount(lastSyncLogDO.getSyncId()).equals(SyncLogUtil.getTotalCount(lastSyncLogDO.getSyncId()));
    if(!flag){
      //每执行3次允许成功一次
      if(SyncLogUtil.incrementAndGetExecuteCount(type) >= topCount){
        SyncLogUtil.clearExecuteCount(type);
        return true;
      }
    }
    return flag;
  }
  
}
