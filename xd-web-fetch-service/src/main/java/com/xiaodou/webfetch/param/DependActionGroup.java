package com.xiaodou.webfetch.param;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.webfetch.action.IncisePoint;
import com.xiaodou.webfetch.unique.IUnique;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.param.DependActionGroup.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 依赖动作组
 * @version 1.0
 */
public class DependActionGroup implements IUnique {

  public DependActionGroup(Target depend) {
    this.target = depend;
  }

  private List<IncisePoint> actionList = Lists.newArrayList();

  public List<IncisePoint> getActionList() {
    return actionList;
  }

  public void pushAction(IncisePoint task) {
    this.actionList.add(task);
  }

  private Target target;

  @Override
  public String unique() {
    return target.unique();
  }

}
