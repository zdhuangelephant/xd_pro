package com.xiaodou.util;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.RedBonus;
import com.xiaodou.domain.product.RedBonus.CourseBonus;
import com.xiaodou.engine.bonus.IBonus;
import com.xiaodou.engine.bonus.LeakFillingBonus;
import com.xiaodou.engine.bonus.RandomPkBonus;
import com.xiaodou.engine.bonus.TollgateItemBonus;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.mission.engine.enums.RedBonusType;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class BonusUtil {

  public static CourseBonus fillCourseBonus(RedBonus bonus) {
    IBonus bonusRule = initIBonus(bonus);
    if (null == bonusRule) return null;
    CourseBonus courseBonus = bonusRule.getCourseBonus();
    bonus.setBonusDetail(FastJsonUtil.toJson(courseBonus));
    if (null != courseBonus && courseBonus.getOriginalScore() >= 100d)
      bonus.setStatue(QuesBaseConstant.RED_BONUS_STATUS_GET);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    quesOperationFacade.updateRedBonusById(bonus);
    return courseBonus;
  }

  public static void useBonus(RedBonus bonus) {
    IBonus bonusRule = initIBonus(bonus);
    if (null == bonusRule) return;
    bonusRule.useBonus();
    bonus.setStatue(QuesBaseConstant.RED_BONUS_STATUS_GET);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    quesOperationFacade.updateRedBonusById(bonus);
    return;
  }

  private static IBonus initIBonus(RedBonus bonus) {
    if (null == bonus) return null;
    IBonus bonusRule = null;
    if (RedBonusType.Random_pk_win.name().equals(bonus.getRedBonusType())) {
      bonusRule = new RandomPkBonus(bonus);
    }
    if (RedBonusType.Tollgate_complete_item.name().equals(bonus.getRedBonusType())
        || QuesBaseConstant.CREDIT_RED_BONUS_TYPE.equals(bonus.getRedBonusType())) {
      bonusRule = new TollgateItemBonus(bonus);
    }
    if (RedBonusType.Leak_filling_complete.name().equals(bonus.getRedBonusType())) {
      bonusRule = new LeakFillingBonus(bonus);
    }
    return bonusRule;
  }

  public static boolean checkNotEmpty(String id) {
    return StringUtils.isNotBlank(id) && !"-1".equals(id);
  }

  public static void followUp(RedBonus bonus) {
    IBonus bonusRule = initIBonus(bonus);
    if (null == bonusRule) return;
    bonusRule.followUp();
    return;
  }

}
