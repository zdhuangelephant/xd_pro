package com.xiaodou.st.dashboard.domain.dashboard;

import lombok.Data;

@Data
public class SessionLearnPercentDTO {
  private String learnPercent = "0";
  private String missionPercent = "0";
  private Long learnTimePercent = 0l;
  private String dateTime;

}
