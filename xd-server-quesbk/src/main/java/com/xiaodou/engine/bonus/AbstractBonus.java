package com.xiaodou.engine.bonus;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;


/**
 * @name @see com.xiaodou.service.bonus.AbstractBonus.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月20日
 * @description 红包主干操作方法实现
 * @version 1.0
 */
public abstract class AbstractBonus extends BaseBonusParam implements IBonus {
  /**
   * 构造方法
   * 
   * @param bonus
   */
  public AbstractBonus(RedBonus bonus) {
    super(bonus);
  }

  @Override
  public void useBonus() {
    CourseBonus courseBonus = FastJsonUtil.fromJson(getBonus().getBonusDetail(), CourseBonus.class);
    if (bonusCanUse(courseBonus)) useBonus0(courseBonus);
  }

  @Override
  public void followUp() {
    CourseBonus courseBonus = FastJsonUtil.fromJson(getBonus().getBonusDetail(), CourseBonus.class);
    if (bonusCanUse(courseBonus)) followUp0(courseBonus);
  }

  /**
   * 使用红包后后续调用方法
   */
  protected void followUp0(CourseBonus courseBonus) {}

  /**
   * 实际调用使用红包方法
   */
  protected abstract void useBonus0(CourseBonus courseBonus);

  public boolean bonusCanUse(CourseBonus courseBonus) {
    if (null == courseBonus) return false;
    return true;
  }

}
