package com.xiaodou.st.dashboard.web.controller.sms;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.PhoneUtil;
import com.xiaodou.st.dashboard.domain.sms.SmsLogDO;
import com.xiaodou.st.dashboard.service.sms.SmsLogService;
import com.xiaodou.st.dashboard.web.controller.BaseController;
import com.xiaodou.summer.dao.pagination.Page;

@Controller("smsPhoneController")
@RequestMapping("/sms/sms_log")
public class SmsLogController extends BaseController {
  @Resource
  SmsLogService smsLogService;

  @RequestMapping("/smsLoglist")
  public ModelAndView smsLoglist(SmsLogDO smsLogDO) {
    ModelAndView mv = new ModelAndView("/sms/sms_log/smsLogList");
    if (null == smsLogDO || StringUtils.isBlank(smsLogDO.getMobile())
        || !PhoneUtil.validatePhone(smsLogDO.getMobile())){
      mv.addObject("totalPage", 0);
      mv.addObject("totalCount", 0);
      mv.addObject("pageNo", 0);
      mv.addObject("pageSize", 0);
      return mv;
    }
    // 处理分页
    Page<SmsLogDO> pg = new Page<SmsLogDO>();
    Integer pageNo = smsLogDO.getPageNo();
    if (pageNo == null) {
      pageNo = 1;
      smsLogDO.setPageNo(pageNo);
    }
    pg.setPageNo(pageNo);
    Integer pageSize = smsLogDO.getPageSize();
    if (null == pageSize) pageSize = 10;
    pg.setPageSize(pageSize);

    mv.addObject("smsLogDO", smsLogDO);
    mv.addObject("adminUser", super.getAdminUser());
    Page<SmsLogDO> listSmsLog = smsLogService.listSmsLog(smsLogDO, pg);
    mv.addObject("listSmsLog", listSmsLog.getResult());
    mv.addObject("totalPage", listSmsLog.getTotalPage());
    mv.addObject("totalCount", listSmsLog.getTotalCount());
    mv.addObject("pageNo", listSmsLog.getPageNo());
    mv.addObject("pageSize", pageSize);
    return mv;
  }

  @RequestMapping("/export_sms_log_list")
  public void exportSmsLogList(SmsLogDO smsLogDO, HttpServletResponse response) {
    Page<SmsLogDO> page = smsLogService.listSmsLog(smsLogDO, null);
    if (null != page && null != page.getResult() && !page.getResult().isEmpty())
      smsLogService.exportScoreList(page.getResult(), response);
  }

}
