package com.xiaodou.st.dashboard.domain.sms;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SmsLogDO {
  private Long id;
  private String message;
  private int sendStatus;
  private int channelId;
  private int templateId;
  private String channelSendResult;
  private String mobile;
  private Timestamp createTime;
  private int typeId;

  private String beginDate;
  private String endDate;

  private Integer pageNo;
  private Integer pageSize;
}
