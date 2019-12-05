package com.xiaodou.engine.bonus;

import java.util.LinkedList;

import com.xiaodou.domain.product.RedBonus.CourseBonus;

public interface IBonus {

  CourseBonus getCourseBonus();
  
  void useBonus();

  LinkedList<Long> getBonusScore(Long m);

  void followUp();

}
