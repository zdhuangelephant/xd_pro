package com.xiaodou.course.service.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserLearnRecordDayModel;
import com.xiaodou.course.model.user.UserLearnRecordDayModel.LearnRecordData;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.user.UserLearnRecordByDaysRequest;
import com.xiaodou.course.web.request.user.UserLearnRecordDataRequest;
import com.xiaodou.course.web.response.user.UserLearnRecordDataResponse;
import com.xiaodou.course.web.response.user.UserLearnRecordResponse;
import com.xiaodou.summer.vo.out.ResultType;

@Service("userLearnRecordDayService")
public class UserLearnRecordDayService {
  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  ProductService productService;

  private static final int LEARN_RECORD_DAYS = -7;

  private static final int PAGENUM = 20;

  /**
   * 根据条件查询学习时长记录
   * 
   * @param cond
   * @param output
   * @return
   */
  public List<UserLearnRecordDayModel> queryLearnRecordDay(Map<String, Object> cond,
      Map<String, Object> output) {
    return productServiceFacade.queryLearnRecordDay(cond, output);
  }

  /**
   * 增加每日学习记录
   * 
   * @param entity
   * @return
   */
  public UserLearnRecordDayModel addlearnRecordDay(UserLearnRecordDayModel entity) {
    return productServiceFacade.insertRecordDayEntity(entity);
  }

  /**
   * 修改每日学习记录
   * 
   * @param cond
   * @param model
   * @return
   */
  public boolean updateLearnRecordDay(Map<String, Object> cond, UserLearnRecordDayModel model) {
    return productServiceFacade.updateLearnRecordDay(cond, model);
  }

  public List<UserLearnRecordDayModel> selectLearnTimeByDay(Map<String, Object> cond) {
    Map<String, Object> output = Maps.newHashMap();
    // CommUtil.transferFromVO2Map(output, UserLearnRecordDayModel.class);
    output.put("id", 1);
    output.put("userId", 1);
    output.put("productId", 1);
    output.put("chapterId", 1);
    output.put("itemId", 1);
    output.put("moduleId", 1);
    output.put("learnType", 1);
    output.put("learnTime", 1);
    output.put("recordTime", 1);
    return productServiceFacade.selectLearnTimeByDay(cond, output);
  }

  /**
   * 获取7天学习时长数据
   * 
   * @param pojo
   * @return
   */
  public UserLearnRecordDataResponse learnRecordTimeStatisticList(UserLearnRecordByDaysRequest pojo) {
    UserLearnRecordDataResponse response = new UserLearnRecordDataResponse(ResultType.SUCCESS);
    try {
      List<LearnRecordData> learnList =
          this.getLearnRecordDataList(pojo.getUid(), pojo.getCourseId(),
              CourseConstant.LEARN_RECORD_STATISTIC_DATA, LEARN_RECORD_DAYS, 0, null);
      response.setLearnList(learnList);
    } catch (Exception e) {
      LoggerUtil.error("获取7天学习时长数据异常", e);
      response = new UserLearnRecordDataResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 获取自第一次登录以来的每日学习时长数据
   * 
   * @param pojo
   */
  public UserLearnRecordDataResponse learnRecordTimeDetailList(UserLearnRecordDataRequest pojo) {
    UserLearnRecordDataResponse response = new UserLearnRecordDataResponse(ResultType.SUCCESS);
    try {
      List<LearnRecordData> learnList =
          this.getLearnRecordDataList(pojo.getUid(), pojo.getCourseId(),
              CourseConstant.LEARN_RECORD_DETAIL_DATA, PAGENUM, Integer.valueOf(pojo.getPage()),
              pojo.getFirstLoginTime());
      response.setLearnList(learnList);
    } catch (Exception e) {
      LoggerUtil.error("获取自第一次登录以来的每日学习时长数据异常", e);
      response = new UserLearnRecordDataResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 获取每日学习时长数据
   * 
   * @param pojo
   */
  public UserLearnRecordResponse learnRecordTimeList(BaseRequest pojo) {
    UserLearnRecordResponse response = new UserLearnRecordResponse(ResultType.SUCCESS);
    try {
      Map<String, Object> params = Maps.newHashMap();
      params.put("userId", pojo.getUid());
      List<UserLearnRecordDayModel> learnList = this.selectLearnTimeByDay(params);
      response.setLearnList(learnList);
    } catch (Exception e) {
      LoggerUtil.error("获取自第一次登录以来的每日学习时长数据异常", e);
      response = new UserLearnRecordResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 每日学习时长数据
   * 
   * @param userId 用户id
   * @param courseId 课程id
   * @param count 数据数量
   * @param type 类型 1：统计页面数据 2：详情列表数据
   * @return
   * @throws ParseException
   */
  private List<LearnRecordData> getLearnRecordDataList(String userId, String courseId, int type,
      int count, int page, String firstLoginTime) throws ParseException {
    List<LearnRecordData> examDateList = new ArrayList<LearnRecordData>(Math.abs(count));
    List<String> _examStrList = Lists.newArrayList();
    Date date = new Date();
    ProductModel product = productService.findProductById(Long.valueOf(courseId));
    if (null != product && null != product.getEndApplyTime()
        && product.getEndApplyTime().before(date)) date = product.getEndApplyTime();
    if (type == CourseConstant.LEARN_RECORD_STATISTIC_DATA) {
      _examStrList = this.getLearnDataStrList(date, LEARN_RECORD_DAYS);
    } else if (type == CourseConstant.LEARN_RECORD_DETAIL_DATA) {
      _examStrList = this.getLearnDataStrList(date, page, PAGENUM, firstLoginTime);
    } else
      return null;
    Map<String, Object> params = Maps.newHashMap();
    params.put("userId", userId);
    params.put("productId", courseId);
    List<UserLearnRecordDayModel> userLearnRecordDayList = this.selectLearnTimeByDay(params);
    int i = 0;
    Map<String, LearnRecordData> examMap = Maps.newHashMap();
    if (null != userLearnRecordDayList && userLearnRecordDayList.size() > 0) {
      for (UserLearnRecordDayModel userLearnRecordDay : userLearnRecordDayList) {
        String recordTime = StringUtils.EMPTY;
        String learnTime = StringUtils.EMPTY;
        if (null != userLearnRecordDay.getRecordTime())
          recordTime = DateUtil.SDF_YMD.format(userLearnRecordDay.getRecordTime());
        if (null != userLearnRecordDay.getLearnTime())
          learnTime = userLearnRecordDay.getLearnTime().toString();
        examMap.put(recordTime, new LearnRecordData(recordTime, learnTime));
      }
    }
    if (null == _examStrList || _examStrList.size() == 0) return examDateList;
    for (String examStr : _examStrList) {
      if (i >= Math.abs(count)) break;
      if (examMap.containsKey(examStr)) {
        examDateList.add(examMap.get(examStr));
      } else {
        examDateList.add(new LearnRecordData(examStr, "0"));
      }
      ++i;
    }
    return examDateList;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   */
  private List<String> getLearnDataStrList(Date date, int days) {
    List<String> _examStrList = Lists.newArrayList();
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -6));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -5));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -4));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -3));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -2));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, -1));
    // _examStrList.add(DateUtils.getDateForDays(0, 0, 0));
    for (int i = days + 1; i <= 0; i++) {
      days++;
      _examStrList.add(DateUtil.getDateForDateAndDays(date, 0, 0, days));
    }
    return _examStrList;
  }

  /**
   * 获取规定范围日期list
   * 
   * @return
   * @throws ParseException
   */
  private List<String> getLearnDataStrList(Date date, int page, int pageNum, String firstLoginTime)
      throws ParseException {
    if (page <= 0) page = 1;
    if (pageNum <= 0) pageNum = PAGENUM;
    int beforeCount = (page - 1) * pageNum;
    int afterCount = page * pageNum;
    List<String> examStrList = Lists.newArrayList();
    // List<String> _examStrList = Lists.newArrayList();
    // 获取用户首次登录日期
    for (int i = beforeCount; i <= afterCount - 1; i++) {
      if (DateUtil.SDF_YMD.parse(DateUtil.getDateForDateAndDays(date, 0, 0, -i)).before(
          DateUtil.SDF_YMD.parse(firstLoginTime))) break;
      examStrList.add(DateUtil.getDateForDateAndDays(date, 0, 0, -i));
    }
    // for (int i = 0; i <= beforeCount - 1; i++) {
    // _examStrList.add(DateUtil.getDateForDateAndDays(date, 0, 0, -i));
    // }
    // examStrList.removeAll(_examStrList);
    return examStrList;
  }

  /**
   * 获取某个用户的总学习时长
   */
  public Integer costLearnTime(String userId) {
    UserLearnRecordDayModel model = productServiceFacade.queryCostLearnTime(userId);
    if (null != model) return model.getLearnTime();
    return 0;
  }

  /**
   * @description 连续学习天数(距离今天连续了多少天)
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年5月8日
   * @param pojo
   * @return
   * @throws ParseException
   */
  public String getLearnDays(BaseRequest pojo) {
    Integer learnDays = 0;
    String userId = pojo.getUid();
    List<UserLearnRecordDayModel> list = productServiceFacade.findEntityListByGroup(userId);
    int i = 0;
    if (null != list && list.size() > 0 && null != list.get(0)
        && null != list.get(0).getRecordTime()) {
      int day = DateUtil.getDiffDays(list.get(0).getRecordTime(), new Date());
      if (day == 0)
        i = 0;
      else if (day == 1)
        i = 1;
      else {
        return learnDays.toString();
      }
    }
    for (UserLearnRecordDayModel model : list) {
      int day = DateUtil.getDiffDays(model.getRecordTime(), new Date());
      if (day == 0 || day == 1) {
        learnDays++;
      } else if (day == i) {
        learnDays++;
      }
      i++;
    }
    return learnDays.toString();
  }

}
