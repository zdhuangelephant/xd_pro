package com.xiaodou.st.dashboard.service.alarm;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.goeasy.GoEasy;
import com.xiaodou.goeasy.publish.GoEasyError;
import com.xiaodou.goeasy.publish.PublishListener;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO.ARVO;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDO.AlarmRecordVO;
import com.xiaodou.st.dashboard.domain.alarm.AlarmRecordDTO;
import com.xiaodou.st.dashboard.domain.alarm.LoginInfoDO;
import com.xiaodou.st.dashboard.domain.alarm.RawDataFacePortraitVO;
import com.xiaodou.st.dashboard.domain.alarm.RawDataFaceRecognitionDO;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.session.CategorySessionPercentDO;
import com.xiaodou.st.dashboard.domain.session.CategoryUnitSessionPercentDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.domain.unit.ChiefUnitRelationDO;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class AlarmService {

  @Resource
  IStServiceFacade stServiceFacade;

  @Resource
  ApplyService applyService;

  public static GoEasy goEasy;

  static {
    if (null == goEasy) goEasy = new GoEasy(StaticInfoProp.goEasyCommonkey());
  }

  public List<AlarmRecordDO> listAlarmRecord(AlarmRecordDTO adto) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == adto) break INPUTS;
      if (null != adto.getAlarmLevel()) inputs.put("alarmLevel", adto.getAlarmLevel());
      // if (null != adto.getPilotUnitId()) inputs.put("pilotUnitId", ado.getPilotUnitId());
      // if (null != adto.getClassId()) inputs.put("classId", ado.getClassId());
      if (StringUtils.isNotBlank(adto.getAlarmTime())) inputs.put("alarmTime", adto.getAlarmTime());
      if (null != adto.getAlarmType()) inputs.put("alarmType", adto.getAlarmType());
    }
    List<Integer> studentList = applyService.listSuccessApplyByCid(null);
    if (null != studentList && !studentList.isEmpty()) inputs.put("studentList", studentList);
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecord(inputs, CommUtil.getAllField(AlarmRecordDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * 
   * @description 根据报警id获取报警信息并更改状态
   * @author 李德洪
   * @Date 2017年4月6日
   * @param alarmId
   * @return
   */
  public AlarmRecordDO getAlarmRecord(Integer alarmId) {
    AlarmRecordDO alarmRecordDO = new AlarmRecordDO();
    alarmRecordDO.setId(alarmId);
    alarmRecordDO.setReadStatus(Constants.HAS_READ_STATUS);
    stServiceFacade.updateAlarmRecord(alarmRecordDO);
    return stServiceFacade.getAlarmRecord(alarmId);
  }

  /**
   * 
   * @description 获取未读报警
   * @author 李德洪
   * @Date 2017年4月6日
   * @return
   */
  public Page<AlarmRecordDO> listUnreadAlarmRecord() {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("readStatus", Constants.UN_READ_STATUS);
    List<Integer> studentList = applyService.listSuccessApplyByCid(null);
    // 总记录数
    if (null != studentList && !studentList.isEmpty()) inputs.put("studentList", studentList);
    Page<AlarmRecordDO> page = new Page<>();
    page.setPageNo(1);
    page.setPageSize(5);
    Page<AlarmRecordDO> result = stServiceFacade.listAlarmRecord(inputs, CommUtil.getAllField(AlarmRecordDO.class),page );
    if (null == result) return null;
    return result;
  }


  /**
   * 
   * @description 获取未读报警数目
   * @author 李德洪
   * @Date 2017年4月6日
   * @return
   */
  public Integer getUnreadCount() {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("readStatus", Constants.UN_READ_STATUS);
    return stServiceFacade.findCountByCond(inputs);
  }

  /**
   * 查询该次测试的记录头像
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param testId
   * @return
   */
  public List<RawDataFaceRecognitionDO> listRawDataFaceRecognition(String testId) {
    Map<String, Object> inputs = Maps.newHashMap();
    if (null != testId) inputs.put("testId", testId);
    Page<RawDataFaceRecognitionDO> page = stServiceFacade.listRawDataFaceRecognition(inputs,
        CommUtil.getAllField(RawDataFaceRecognitionDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  /**
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月10日
   * @param testId
   * @return
   */
  public List<RawDataFacePortraitVO> listRawDataFacePortraitVO(String testId) {
    List<RawDataFacePortraitVO> listVO = Lists.newArrayList();
    List<RawDataFaceRecognitionDO> listDO = this.listRawDataFaceRecognition(testId);
    for (RawDataFaceRecognitionDO rdo : listDO) {
      RawDataFacePortraitVO rvo = new RawDataFacePortraitVO();
      rvo.setSmallSrc(rdo.getCollectPortrait());
      rvo.setSrc(rdo.getCollectPortrait());
      if (Constants.IS_RESULT == rdo.getResult()) {
        rvo.setTitle("是本人");
      } else {
        rvo.setTitle("不是本人");
      }
      listVO.add(rvo);
    }
    return listVO;
  }


  /*
   * public List<LoginInfoDO> listLoginInfo(AlarmRecordDO alarm) { Map<String, Object> input = new
   * HashMap<String, Object>(); if (null == alarm) return null; if (null != alarm.getTriggerId())
   * input.put("businessIdUpper", alarm.getTriggerId()); if (null != alarm.getPretreatment()) {
   * switch (alarm.getPretreatment()) { case AccountNoPretreat: case AccountClosure:
   * input.put("studentId", alarm.getStudentId()); break; case DeviceNoPretreat: case DeviceClosure:
   * input.put("deviceId", alarm.getDeviceId()); break; case AccountDeviceClosure:
   * input.put("studentAndDevice", 1); input.put("deviceId", alarm.getDeviceId());
   * input.put("studentId", alarm.getStudentId()); break; case ResponseNothing:
   * input.put("studentId", alarm.getStudentId()); break; default: break; } } Page<LoginInfoDO> page
   * = stServiceFacade.listLoginInfo(input, CommUtil.getAllField(LoginInfoDO.class)); if (null ==
   * page) return null; return page.getResult(); }
   */

  public List<LoginInfoDO> listLoginInfo(AlarmRecordDO alarm) {
    Map<String, Object> input = new HashMap<String, Object>();
    if (null == alarm) return null;
    if (null != alarm.getTriggerId()) input.put("businessIdUpper", alarm.getTriggerId());

    if (null == alarm.getAlarmType()) return null;
    String code = alarm.getAlarmType().getCode();
    if (code.equals("14") || code.equals("15")) {
      if (null != alarm.getStudentId()) input.put("studentId", alarm.getStudentId());
      Page<LoginInfoDO> page =
          stServiceFacade.listLoginInfo(input, CommUtil.getAllField(LoginInfoDO.class));
      if (null == page) return null;
      HashSet<String> deviceIdList = Sets.newHashSet();
      for (LoginInfoDO login : page.getResult()) {
        deviceIdList.add(login.getDeviceId());
      }
      if (!deviceIdList.isEmpty()) input.put("deviceIdList", deviceIdList);
      input.remove("studentId");
    }

    //
    if (code.equals("16") || code.equals("17")) {
      if (null != alarm.getDeviceId()) input.put("deviceId", alarm.getDeviceId());
      Page<LoginInfoDO> page =
          stServiceFacade.listLoginInfo(input, CommUtil.getAllField(LoginInfoDO.class));
      if (null == page) return null;
      HashSet<Integer> studentIdList = Sets.newHashSet();
      for (LoginInfoDO login : page.getResult()) {
        studentIdList.add(login.getStudentId());
      }
      if (!studentIdList.isEmpty()) input.put("studentIdList", studentIdList);
      input.remove("deviceId");
    }

    // 无学习行为
    if (code.equals("11") || code.equals("12") || code.equals("13")) {
      if (null != alarm.getStudentId()) input.put("studentId", alarm.getStudentId());
    }

    Page<LoginInfoDO> allAccountOfDevicesPage =
        stServiceFacade.listLoginInfo(input, CommUtil.getAllField(LoginInfoDO.class));
    if (null == allAccountOfDevicesPage) return null;
    return allAccountOfDevicesPage.getResult();
  }


  public void saveAlarmRecord(AlarmRecordDO alarmRecordDO) {
    if (null == alarmRecordDO) return;
    // 第一级单位
    alarmRecordDO.setRoleType(Constants.SELF_TAUGHT_UNIT_ROLE);
    alarmRecordDO.setUnitId(Constants.SELF_TAUGHT_UNIT_ID);
    stServiceFacade.saveAlarmRecord(alarmRecordDO);
    this.sendWebMessage(Constants.SELF_TAUGHT_UNIT_ROLE, Constants.SELF_TAUGHT_UNIT_ID);
    // 第三级单位
    StudentDO sdo = stServiceFacade.getStudent(alarmRecordDO.getStudentId());
    alarmRecordDO.setRoleType(Constants.POILT_UNIT_ROLE);
    alarmRecordDO.setUnitId(sdo.getPilotUnitId());
    stServiceFacade.saveAlarmRecord(alarmRecordDO);
    this.sendWebMessage(Constants.POILT_UNIT_ROLE, sdo.getPilotUnitId());
    // 第二级单位
    List<Long> listTemp = Lists.newArrayList();
    List<ApplyDO> listApply = applyService.listApplyBySid(alarmRecordDO.getStudentId());
    for (ApplyDO ado : listApply) {
      ChiefUnitRelationDO curdo = stServiceFacade.getChiefUnitByCatId(ado.getCatId());
      if (null != curdo && !listTemp.contains(curdo.getUnitId())) {
        listTemp.add(curdo.getUnitId());
        alarmRecordDO.setRoleType(Constants.CHIEF_UNIT_ROLE);
        alarmRecordDO.setUnitId(curdo.getUnitId());
        stServiceFacade.saveAlarmRecord(alarmRecordDO);
        this.sendWebMessage(Constants.CHIEF_UNIT_ROLE, curdo.getUnitId());
      }
    }
  }

  public void sendWebMessage(Short roleType, Long unitId) {
    this.updateAlarmCount(roleType, unitId);
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("unitId", unitId);
    inputs.put("roleType", roleType);
    inputs.put("readStatus", Constants.UN_READ_STATUS);
    inputs.put("studentList", applyService.listSuccessApplyByCid(null));
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecordByCond(inputs, CommUtil.getAllField(AlarmRecordDO.class));
    if (null == page) return;
    List<AlarmRecordVO> listVO = Lists.newArrayList();
    AtomicInteger ai = new AtomicInteger(0);
    for (AlarmRecordDO ardo : page.getResult()) {
      if (ai.get() >= 5) {
        break;
      }
      ai.incrementAndGet();
      AlarmRecordVO arvo = new AlarmRecordVO();
      arvo.setId(ardo.getId());
      if (null != ardo.getAlarmLevel()) arvo.setAlarmLevel(ardo.getAlarmLevel().getDesc());
      if (null != ardo.getAlarmTime())
        arvo.setAlarmTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ardo.getAlarmTime()));
      listVO.add(arvo);
    }
    ARVO arVO = new ARVO();
    arVO.setListVO(listVO);
    arVO.setTotalCount(page.getTotalCount());
    goEasy.publish(String.format("alarm_channel_%s", unitId), FastJsonUtil.toJson(arVO),
        new PublishListener() {
          @Override
          public void onFailed(GoEasyError error) {
            LoggerUtil.alarmInfo(
                "推送失败了，Error code:" + error.getCode() + "; error content:" + error.getContent());
          }

          @Override
          public void onSuccess() {}
        });
  }

  public void updateAlarmCount(Short roleType, Long unitId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("unitId", unitId);
    inputs.put("roleType", roleType);
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecordByCond(inputs, CommUtil.getAllField(AlarmRecordDO.class));
    if (null == page) return;
    // 更改专业表
    if (Constants.CHIEF_UNIT_ROLE.equals(roleType)) {
      CategorySessionPercentDO categorySessionPercentDO = new CategorySessionPercentDO();
      categorySessionPercentDO.setAlarmCount((int) page.getTotalCount());
      categorySessionPercentDO.setStudentCount(null);// 不改动值
      Map<String, Object> input = Maps.newHashMap();
      input.put("chiefUnitId", unitId);
      stServiceFacade.updateCategorySessionPercent(input, categorySessionPercentDO);
    }
    // 更改专业下的第三级单位表
    if (Constants.POILT_UNIT_ROLE.equals(roleType)) {
      CategoryUnitSessionPercentDO categoryUnitSessionPercentDO =
          new CategoryUnitSessionPercentDO();
      categoryUnitSessionPercentDO.setAlarmCount((int) page.getTotalCount());
      categoryUnitSessionPercentDO.setStudentCount(null);// 不改动值
      Map<String, Object> _input = Maps.newHashMap();
      _input.put("pilotUnitId", unitId);
      stServiceFacade.updateCategoryUnitSessionPercent(_input, categoryUnitSessionPercentDO);
    }
  }


  public Long getLoginInfoIdByBid(Long businessId) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("businessId", businessId);
    Page<LoginInfoDO> page =
        stServiceFacade.listLoginInfo(input, CommUtil.getAllField(LoginInfoDO.class));
    if (null == page || null == page.getResult() || page.getResult().size() < 1
        || null == page.getResult().get(0))
      return null;
    return page.getResult().get(0).getId();
  }

  public Integer countAlarmRecordByCond(Short roleType, Long unitId, Integer catId) {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("unitId", unitId);
    inputs.put("roleType", roleType);
    inputs.put("studentList", applyService.listSuccessApplyByCid(catId));
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecordByCond(inputs, CommUtil.getAllField(AlarmRecordDO.class));
    if (null == page) return 0;
    return (int) page.getTotalCount();
  }

  // 第三级单位报警数量包装
  public void packageAlarmRecordCountMapByCatId(Map<Long, Integer> alarmCountMap, Integer catId) {
    Assert.notNull(alarmCountMap, "alarmCountMap can't be null.");
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("roleType", Constants.POILT_UNIT_ROLE);
    // inputs.put("unitId", unitId);
    inputs.put("studentList", applyService.listSuccessApplyByCid(catId));
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecordByCond(inputs, CommUtil.getAllField(AlarmRecordDO.class));
    if (null == page || null == page.getResult() || page.getResult().isEmpty()) return;
    for (AlarmRecordDO alarmRecord : page.getResult()) {
      long unitId = alarmRecord.getUnitId();
      if (!alarmCountMap.containsKey(unitId)) {
        alarmCountMap.put(unitId, 1);
      } else {
        alarmCountMap.put(unitId, alarmCountMap.get(unitId) + 1);
      }
    }
  }

  public Page<AlarmRecordDO> listAlarmRecord(AlarmRecordDTO adto, Page<AlarmRecordDO> pg) {
    Map<String, Object> inputs = Maps.newHashMap();
    INPUTS: {
      if (null == adto) break INPUTS;
      if (null != adto.getAlarmLevel()) inputs.put("alarmLevel", adto.getAlarmLevel());
      if (StringUtils.isNotBlank(adto.getAlarmTime())) inputs.put("alarmTime", adto.getAlarmTime());
      if (null != adto.getAlarmType()) inputs.put("alarmType", adto.getAlarmType());
    }
    List<Integer> studentList = applyService.listSuccessApplyByCid(null);

    if (!CollectionUtils.isEmpty(studentList)) inputs.put("studentList", studentList);
    Page<AlarmRecordDO> page =
        stServiceFacade.listAlarmRecord(inputs, CommUtil.getAllField(AlarmRecordDO.class), pg);
    return page;
  }

}
