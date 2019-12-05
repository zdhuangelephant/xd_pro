package com.xiaodou.engine.bonus;

import com.xiaodou.domain.product.RedBonus;

/**
 * @name @see com.xiaodou.service.bonus.BaseBonusParam.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 基础红包参数
 * @version 1.0
 */
public class BaseBonusParam {
  public BaseBonusParam(RedBonus bonus) {
    this.bonus = bonus;
  }

  /** bonus 红包 */
  private RedBonus bonus;

  public RedBonus getBonus() {
    return bonus;
  }

  public void setBonus(RedBonus bonus) {
    this.bonus = bonus;
  }

}
