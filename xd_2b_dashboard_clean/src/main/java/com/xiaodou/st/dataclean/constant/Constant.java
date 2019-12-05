package com.xiaodou.st.dataclean.constant;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataExamTotalModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataFinishMissionModel;
import com.xiaodou.st.dataclean.model.domain.raw.RawDataLearnRecordModel;

/**
 * @name @see com.xiaodou.st.dataclean.constant.Constant.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年4月6日
 * @description 常量类
 * @version 1.0
 */
public class Constant {
  /************** 人脸识别 **************/
  /** IS_NOT_SELF 不是本人 */
  public static final Short IS_NOT_SELF = 0;
  /** IS_SELF 是本人 */
  public static final Short IS_SELF = 1;
  /************** 报警 **************/
  /** TRIGGRT_TYPE_LOGIN 报警类型:登录 */
  public static final Short TRIGGRT_TYPE_LOGIN = 0;
  /** TRIGGRT_TYPE_FACE_RECOGNITION 报警类型:人脸识别 */
  public static final Short TRIGGRT_TYPE_FACE_RECOGNITION = 1;
  /** RECORD_STATUS_UNREAD 记录状态:未读 */
  public static final Short RECORD_STATUS_UNREAD = 0;
  /** RECORD_STATUS_READED 记录状态:已读 */
  public static final Short RECORD_STATUS_READED = 1;
  /** TENDENCY_UP 趋势:上升 */
  public static final Short TENDENCY_UP = 1;
  /** TENDENCY_SAME 趋势:持平 */
  public static final Short TENDENCY_SAME = 0;
  /** TENDENCY_DOWN 趋势:下降 */
  public static final Short TENDENCY_DOWN = -1;
  /** COLLECT_WAY_BYUSER 采集方式:用户上传 */
  public static final String COLLECT_WAY_BYUSER = "用户上传";
  
  /* 角色1、自考办，2、主考院校，3、助学机构 */
  public static final Short SELF_TAUGHT_UNIT_ROLE = 1;
  public static final Short CHIEF_UNIT_ROLE = 2;
  public static final Short POILT_UNIT_ROLE = 3;
  public static final Integer SELF_TAUGHT_UNIT_ID = 1;
  
  
  public static final String TAUGHT ="TAUGHT";
  public static final String CHIEF ="CHIEF";
  public static final String POILT ="POILT";
  public static final String POILTCAT ="POILTCAT";
  
  public static final Map<String,RawDataLearnRecordModel> UserLearnMap=Maps.newHashMap();
  public static final Map<String,RawDataExamTotalModel> UserExamTotalMap=Maps.newHashMap();
  public static final Map<String,RawDataFinishMissionModel> FinishMissionMap=Maps.newHashMap();
  
//  public static final Map<String,RawDataLearnRecordModel> UserLearnTrackerMap=Maps.newHashMap();
}
