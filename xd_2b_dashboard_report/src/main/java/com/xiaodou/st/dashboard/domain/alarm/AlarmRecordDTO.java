package com.xiaodou.st.dashboard.domain.alarm;
import com.xiaodou.st.dashboard.constants.enums.AlarmLevelEnum;
import com.xiaodou.st.dashboard.constants.enums.AlarmTypeEnum;

import lombok.Data;
@Data
public class AlarmRecordDTO {

  /* alarmLevel 报警级别 初级，中级，高级 */
  private AlarmLevelEnum alarmLevel;
  /* alarmTime 报警时间 */
  private String alarmTime;
  /* alarmType 报警类型 */
  private AlarmTypeEnum alarmType;
  
  private Integer pageNo;
  private Integer pageSize;
}
