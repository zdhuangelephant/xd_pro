package com.xiaodou.userCenter.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.userCenter.common.enums.AlarmTypeEnum;
import com.xiaodou.userCenter.model.alarm.AlarmRecordModel;
import com.xiaodou.userCenter.model.alarm.LoginInfoModel;
import com.xiaodou.userCenter.model.alarm.PretreatmentDTO;
import com.xiaodou.userCenter.model.http.GeocodingResponse.Result;
import com.xiaodou.userCenter.model.message.AlarmRecordMessage;
import com.xiaodou.userCenter.model.message.AlarmRecordMessage.AlarmRecordDTO;
import com.xiaodou.userCenter.model.message.LoginInfoMessage;
import com.xiaodou.userCenter.model.message.LoginInfoMessage.LoginInfoDTO;
import com.xiaodou.userCenter.request.NewLoginRequest;
import com.xiaodou.userCenter.service.facade.IUcenterServiceFacade;
import com.xiaodou.userCenter.service.http.HttpService;
import com.xiaodou.userCenter.util.TimeUtil;

@Service("userLoginInfoService")
public class UserLoginInfoService {

  @Resource
  IUcenterServiceFacade ucenterServiceFacade;


  /**
   * 
   * @description 保存登录日志 并发送消息
   * @author 李德洪
   * @Date 2017年5月10日
   * @param request
   * @param userId
   * @return
   */
  public LoginInfoModel saveUserLoginInfo(NewLoginRequest request, Long userId) {
    LoginInfoModel model = new LoginInfoModel();
    model.setUserId(userId);
    model.setSystemType(request.getClientType());
    model.setDeviceId(request.getDeviceId());
    model.setLoginTime(new Timestamp(System.currentTimeMillis()));
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    float lat = Float.valueOf(request.getLat());
    float lng = Float.valueOf(request.getLng());
    model.setLat(lat);
    model.setLng(lng);
    String area = getArea(lat,lng);
    model.setArea(area);
    LoginInfoModel loginInfoModel = ucenterServiceFacade.saveUserLoginInfo(model);
    LoginInfoDTO lidto = new LoginInfoDTO(loginInfoModel);
    lidto.setArea(area);
    LoginInfoMessage.send(new LoginInfoMessage(lidto));
    return loginInfoModel;
  }

  private String getArea(float lat,float lng) {
    String area = "无";
    // 根据经度纬度判定地区
    Result result = HttpService.geocodingByBaidu(lat, lng);
    if (null != result && (lat != -1 || lng != -1)) {
      if (HttpService.isEspecially(result.getCityCode())) {
        area = result.getAddressComponent().getCity() + result.getAddressComponent().getDistrict();
      } else {
        area = result.getAddressComponent().getProvince() + result.getAddressComponent().getCity();
      }
    }
    return area;
  }

  /**
   * @description 获取省或者直辖市或者特别行政区 正规全名
   * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
   * @Date 2018年5月14日
   * @param request
   * @return
   */
  public String getProvince(NewLoginRequest request) {
    String area = StringUtils.EMPTY;
    if(StringUtils.isBlank(request.getLat()) || StringUtils.isBlank(request.getLng())){
      return area;
    }
    // 纬度
    float latitude = Float.valueOf(request.getLat());
    // 经度
    float longitude = Float.valueOf(request.getLng());
    // 根据经度纬度判定地区
    Result result = HttpService.geocodingByBaidu(latitude,longitude);
    if (null != result && (longitude != -1 || latitude != -1)) {
      area = result.getAddressComponent().getProvince();
    }
    return area;
  }
  
  public void saveAlarmRecord(AlarmRecordModel alarmRecord) {
    if (null == alarmRecord) return;
    ucenterServiceFacade.saveUserLoginAlarmRecord(alarmRecord); //57 2business_temp
    AlarmRecordDTO ardto = new AlarmRecordDTO(alarmRecord);
    AlarmRecordMessage.send(new AlarmRecordMessage(ardto)); // 56 3b_report
  }

  /**
   * 2
   * 
   * @param inputs 查询信息 查看该账号登陆列表
   */
  public List<LoginInfoModel> listUserLoginInfo(Map<String, Object> inputs) {
    IQueryParam param = new QueryParam();
    param.addInputs(inputs);
    param.addOutputs(CommUtil.getAllField(LoginInfoModel.class));
    Page<LoginInfoModel> page = ucenterServiceFacade.listUserLoginInfo(param);
    List<LoginInfoModel> list = page.getResult();
    return list;
  }

  /**
   * 3、查询 报警异常
   */
  public List<PretreatmentDTO> checkUserLoginInfo(LoginInfoModel model) {
    if ("ON".equals(ConfigProp.getParams("check.alarm.mock"))) return null;
    List<PretreatmentDTO> voList = Lists.newArrayList();
    Long userId = model.getUserId();
    String deviceId = model.getDeviceId();
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_1")))
      voList.add(isAlarmType_1(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_2")))
      voList.add(isAlarmType_2(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_3")))
      voList.add(isAlarmType_3(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_4")))
      voList.add(isAlarmType_4(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_5")))
      voList.add(isAlarmType_5(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_6")))
      voList.add(isAlarmType_6(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_7")))
      voList.add(isAlarmType_7(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_8")))
      voList.add(isAlarmType_8(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_9")))
      voList.add(isAlarmType_9(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_14")))
      voList.add(isAlarmType_14(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_15")))
      voList.add(isAlarmType_15(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_16")))
      voList.add(isAlarmType_16(userId, deviceId));
    if (!"ON".equals(ConfigProp.getParams("check.alarm.mock.AlarmType_17")))
      voList.add(isAlarmType_17(userId, deviceId));
    return voList;
  }

  /**
   * 
   * @description 同一设备登陆3个及以上不同账号
   * @author 李德洪
   * @Date 2017年6月7日
   * @return
   */
  public PretreatmentDTO isAlarmType_9(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<Long, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getUserId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_9);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_9.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_9.getAlarmLevelEnum());
    }
    return vo;
  }


  /**
   * 
   * @description 同一设备24小时内切换账号登陆2次以上A-B-A-B
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_8(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    inputs.put("loginTimeLower", TimeUtil.getDateByHour(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<Long, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      if (map.containsKey(loginInfo.getUserId())) {
        if (map.size() >= 2) {
          vo = new PretreatmentDTO();
          vo.setDeviceId(deviceId);
          List<Long> userIds = Lists.newArrayList(userId);
          vo.setUserIds(userIds);
          vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_8);
          vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_8.getPretreatmentEnum());
          vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_8.getAlarmLevelEnum());
        }
      }
      map.put(loginInfo.getUserId(), loginInfo);
    }
    return vo;
  }

  /**
   * 
   * @description 同一账号24小时内在三个不同地区登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_7(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("loginTimeLower", TimeUtil.getDateByHour(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getArea(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_7);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_7.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_7.getAlarmLevelEnum());
    }
    return vo;
  }

  /**
   * 
   * @description 同一账号24小时内更换3台及以上设备登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @return
   */
  public PretreatmentDTO isAlarmType_6(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("loginTimeLower", TimeUtil.getDateByHour(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_6);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_6.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_6.getAlarmLevelEnum());
    }
    return vo;
  }

  /**
   * 
   * @description 同一设备7日内切换账号登陆2次以上A-B-A-B
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_5(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    inputs.put("loginTimeLower", TimeUtil.getDateByDays(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<Long, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      if (map.containsKey(loginInfo.getUserId())) {
        if (map.size() >= 2) {
          vo = new PretreatmentDTO();
          vo.setDeviceId(deviceId);
          List<Long> userIds = Lists.newArrayList(userId);
          vo.setUserIds(userIds);
          vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_5);
          vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_5.getPretreatmentEnum());
          vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_5.getAlarmLevelEnum());
        }
      }
      map.put(loginInfo.getUserId(), loginInfo);
    }
    return vo;
  }


  /**
   * 
   * @description 同一账号7日内在三个不同地区登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_4(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("loginTimeLower", TimeUtil.getDateByDays(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getArea(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_4);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_4.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_4.getAlarmLevelEnum());
    }
    return vo;
  }

  /**
   * 
   * @description 同一账号7日内更换3台及以上设备登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_3(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("loginTimeLower", TimeUtil.getDateByDays(new Date()));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_3);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_3.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_3.getAlarmLevelEnum());
    }
    return vo;
  }


  /**
   * 
   * @description 同一设备登陆2个不同账号
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_2(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 2) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_2);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_2.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_2.getAlarmLevelEnum());
    }
    return vo;
  }

  /**
   * 
   * @description 同一账号更换3台设备登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_1(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_1);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_1.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_1.getAlarmLevelEnum());
    }
    return vo;
  }
  
  /**
   * 
   * @description 同一账号7天内更换3台及以上设备登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_14(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    
    // 如果已经报了‘同一账号近1日内登陆3个及以上设备’的预警，则‘同一账号近7日内登陆3个及以上设备’不会触发。仅预警级别高的
    PretreatmentDTO dto = isAlarmType_15(userId, deviceId);
    if(null != dto) return vo;
    
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    // AND login_time > '2018-03-28 00:00:00'
    // AND login_time <= '2018-04-03 14:19:51'
    inputs.put("loginTimeLower", TimeUtil.getDayBeforeZero(6)); 
    inputs.put("loginTimeUpper", new Date()); 
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_14);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_14.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_14.getAlarmLevelEnum());
    }
    return vo;
  }
  
  
  /**
   * 
   * @description 同一账号1天内更换3台及以上设备登陆
   * @author 李德洪
   * @Date 2017年6月7日
   * @return
   */
  public PretreatmentDTO isAlarmType_15(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("userId", userId);
    inputs.put("loginTimeLower", TimeUtil.getDayBeforeZero(0));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<String, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getDeviceId(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_15);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_15.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_15.getAlarmLevelEnum());
    }
    return vo;
  }
  
  
  /**
   * 
   * @description 同一设备近7日内登陆3个及以上账号
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_16(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    // 如果已经报了‘同一设备近1日内登陆3个及以上账号’的预警，则‘同一设备近7日内登陆3个及以上账号’不会触发。仅预警级别高的
    PretreatmentDTO dto = isAlarmType_17(userId, deviceId);
    if(null != dto) return vo;
    
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    inputs.put("loginTimeLower", TimeUtil.getDayBeforeZero(6)); 
    inputs.put("loginTimeUpper", new Date()); 
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<Long, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getUserId().longValue(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_16);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_16.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_16.getAlarmLevelEnum());
    }
    
    return vo;
  }
  
  
  /**
   * 
   * @description 同一设备近1日内登陆3个及以上账号
   * @author 李德洪
   * @Date 2017年6月7日
   * @param userId
   * @param deviceId
   * @return
   */
  public PretreatmentDTO isAlarmType_17(Long userId, String deviceId) {
    PretreatmentDTO vo = null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("deviceId", deviceId);
    inputs.put("loginTimeLower", TimeUtil.getDayBeforeZero(0));
    List<LoginInfoModel> list = this.listUserLoginInfo(inputs);
    // 判断
    Map<Long, Object> map = Maps.newHashMap();
    for (LoginInfoModel loginInfo : list) {
      map.put(loginInfo.getUserId().longValue(), loginInfo);
    }
    if (map.size() >= 3) {
      vo = new PretreatmentDTO();
      vo.setDeviceId(deviceId);
      List<Long> userIds = Lists.newArrayList(userId);
      vo.setUserIds(userIds);
      vo.setAlarmTypeEnum(AlarmTypeEnum.AlarmType_17);
      vo.setPetreatmentEnum(AlarmTypeEnum.AlarmType_17.getPretreatmentEnum());
      vo.setAlarmLevelEnum(AlarmTypeEnum.AlarmType_17.getAlarmLevelEnum());
    }
    return vo;
  }

}
