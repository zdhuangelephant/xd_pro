package com.xiaodou.userCenter.model.alarm;

import java.util.List;

import com.xiaodou.userCenter.common.enums.AlarmLevelEnum;
import com.xiaodou.userCenter.common.enums.AlarmTypeEnum;
import com.xiaodou.userCenter.common.enums.PretreatmentEnum;

import lombok.Data;

/**
 * 
 * @name PretreatmentDTO 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年5月10日
 * @description 预处理 转换 模型
 * @version 1.0
 */
@Data
public class PretreatmentDTO {

  private PretreatmentEnum petreatmentEnum;

  private AlarmLevelEnum alarmLevelEnum;

  private AlarmTypeEnum alarmTypeEnum;

  private String deviceId;

  private List<Long> userIds;
}
