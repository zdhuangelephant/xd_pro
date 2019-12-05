package com.xiaodou.st.dashboard.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.st.dashboard.util.StDateUtil;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;


/**
 * @name BaseDashboardService
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 基础service
 * @version 1.0
 */
public class BaseDashboardService {

  @Resource
  ManageStaticInfoService manageStaticInfoService;

  /**
   * 获取当前登录用户
   */
  protected static AdminUser getAdminUser() {
    return (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * 获取公共QueryParam查询参数
   * 
   * @return
   */
  public IQueryParam getCommonQueryParam() {
    IQueryParam param = new QueryParam();
    AdminUser user = getAdminUser();
    if (null == user) return param;
    if (null != user.getUnitId()) param.addInput("unitId", user.getUnitId());
    if (null != user.getRole()) param.addInput("roleType", user.getRole());
    return param;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   * @throws ParseException
   */
  public List<String> getDesignedDateStrList(Date beginTime, Date endTime) throws ParseException {
    int diffDays = StDateUtil.getDiffDays(beginTime, endTime);
    List<String> examStrList = Lists.newArrayList();
    for (int i = diffDays; i >= 0; i--) {
      examStrList.add(StDateUtil.getDateForDateAndDays(endTime, 0, 0, -i));
    }
    return examStrList;
  }


  /**
   * 获取上周查询日期条件List
   * 
   * @throws ParseException
   */
  public List<String> getBeforeDateStrListOld(int days) throws ParseException {
    // 取结课时间
    SimpleDateFormat ftime = new SimpleDateFormat("YYYY-MM-dd");// 时间格式化不用年月日，只用时
    Date today = new Date();
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    Date date = DateUtil.SDF_YMD.parse(staticInfo.getEndApplyTime());
    if (date.after(today)) date = today;
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    List<String> dateTimeList = Lists.newArrayList();
    dateTimeList.add(ftime.format(cal.getTime()));
    for (int i = 1; i < days; i++) {
      cal.add(Calendar.DAY_OF_MONTH, -1);
      dateTimeList.add(ftime.format(cal.getTime()));
    }
    return dateTimeList;
  }


  /**
   * 获取上周查询日期条件List
   * 
   * @throws ParseException
   */
  public List<String> getBeforeDateStrList(int days) throws ParseException {
    DateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat SDF_MD = new SimpleDateFormat("MM-dd");
    Date today = new Date();
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    Date date = SDF_YMD.parse(staticInfo.getEndApplyTime());
    if (date.after(today)) date = today;
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    List<String> dateTimeList = Lists.newArrayList();
    dateTimeList.add(SDF_MD.format(cal.getTime()));
    for (int i = 1; i < days; i++) {
      cal.add(Calendar.DAY_OF_MONTH, -1);
      dateTimeList.add(SDF_MD.format(cal.getTime()));
    }
    Collections.reverse(dateTimeList);
    return dateTimeList;
  }

  /**
   * 
   * @description 验证时间数据排序的正确性
   * @author 李德洪
   * @Date 2017年5月19日
   * @param list
   * @return
   */
  public <T extends SessionLearnPercentDTO> boolean isSuccessOrderData(List<T> list) {
    String temp = StringUtils.EMPTY;
    for (SessionLearnPercentDTO sdto : list) {
      if (StringUtils.isBlank(temp)) {
        temp = sdto.getDateTime();
        break;
      }
      try {
        int diffDays = DateUtil.getDiffDays(temp, sdto.getDateTime());
        if (diffDays != 1) return false;
        temp = sdto.getDateTime();
      } catch (Exception e) {
        LoggerUtil.error("验证时间数据排序的正确性 出错", e);
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * @description 获取报名列表中的报名学生数目
   * @author 李德洪
   * @Date 2017年4月11日
   * @param listDO
   * @return
   */
  public Integer getStudentCount(List<ApplyDO> listDO) {
    if (null == listDO || listDO.isEmpty()) return 0;
    List<Integer> studentIds = Lists.transform(listDO, new Function<ApplyDO, Integer>() {
      @Override
      public Integer apply(ApplyDO input) {
        return input.getStudentId();
      }
    });
    HashSet<Integer> h = new HashSet<Integer>(studentIds);
    return h.size();
  }

  /**
   * 
   * @description 获取报名列表中的学生数目
   * @author 李德洪
   * @Date 2017年4月11日
   * @param listDO
   * @return
   */
  public Integer getCatIdCount(List<ApplyDO> listDO) {
    if (null == listDO || listDO.isEmpty()) return 0;
    List<Integer> studentIds = Lists.transform(listDO, new Function<ApplyDO, Integer>() {
      @Override
      public Integer apply(ApplyDO input) {
        return input.getStudentId();
      }
    });
    HashSet<Integer> h = new HashSet<Integer>(studentIds);
    return h.size();
  }
}
