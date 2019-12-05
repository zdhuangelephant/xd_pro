package com.xiaodou.st.dashboard.service.sms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.domain.score.ScoreDO;
import com.xiaodou.st.dashboard.domain.sms.SmsLogDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.ExcelUtil;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class SmsLogService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;

  public Page<SmsLogDO> listSmsLog(SmsLogDO smsLogDO,Page<SmsLogDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == smsLogDO) break INPUTS;
      if (null != smsLogDO.getMobile()) 
          inputs.put("mobile", smsLogDO.getMobile());
      if (null != smsLogDO.getBeginDate()) 
          inputs.put("beginDate", smsLogDO.getBeginDate());
      if (null != smsLogDO.getEndDate()) 
          inputs.put("endDate", smsLogDO.getEndDate());
    }
    System.out.println(inputs.toString());
    Page<SmsLogDO> page = stServiceFacade.listSmsLog(inputs, CommUtil.getAllField(SmsLogDO.class),pg);
    if (null == page) return null;
    return page;
  }
  

  public void exportScoreList(List<SmsLogDO> list, HttpServletResponse response) {
    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
    fieldMap.put("id", "编号");
    fieldMap.put("mobile", "手机号码");
    fieldMap.put("message", "短信内容");
    fieldMap.put("channelSendResult", "发送状态");
    fieldMap.put("createTime", "发送时间");
    try {
      ExcelUtil.listToExcel(list, fieldMap, "短信列表", response,"sms-log-list");
    } catch (Exception e) {
      LoggerUtil.error("导出excel异常！", e);
    }
  }
}
