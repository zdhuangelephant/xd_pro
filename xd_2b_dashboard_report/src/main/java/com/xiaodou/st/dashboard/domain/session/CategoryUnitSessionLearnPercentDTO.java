package com.xiaodou.st.dashboard.domain.session;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDTO;

@EqualsAndHashCode(callSuper=false)
@Data
public class CategoryUnitSessionLearnPercentDTO extends SessionLearnPercentDTO{

  private String rightPercent = "0";
}
