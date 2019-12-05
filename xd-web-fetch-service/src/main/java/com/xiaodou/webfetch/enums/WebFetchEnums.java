package com.xiaodou.webfetch.enums;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.webfetch.action.Collect;
import com.xiaodou.webfetch.action.Goto;
import com.xiaodou.webfetch.action.IncisePoint;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;



/**
 * @name @see com.xiaodou.webfetch.enums.WebFetchEnums.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月8日
 * @description 抓取服务枚举类
 * @version 1.0
 */
public class WebFetchEnums {

  public enum HttpApiType {
    /** Jsoup jsoup插件 */
    Jsoup,
    /** Phantomjs phantomjs插件 */
    Phantomjs
  }

  /**
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月8日
   * @description 调度域<br>
   * @version 1.0
   */
  public enum SchduleField {
    /** Month 指定月度 */
    Month,
    /** Week 指定星期 */
    Week,
    /** Day 指定天 */
    Day,
    /** Hour 指定小时 */
    Hour,
    /** Min 指定分钟 */
    Min,
    /** Second 指定秒 */
    Second
  }

  /**
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月8日
   * @description 调度类型<br>
   * @version 1.0
   */
  public enum SchduleType {
    /** FromTo 区间范围 */
    FromTo,
    /** Every 每次 */
    Every,
    /** Interval 间隔 */
    Interval,
    /** Design 指定 */
    Design,
    /** Last 最近 */
    Last,
    /** Work 工作日 */
    Work,
    /** ChooseBetween 间选 */
    ChooseBetween
  }

  /**
   * @name @see com.xiaodou.webfetch.action.ActionFactory.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月15日
   * @description 行为类型
   * @version 1.0
   */
  public enum ActionType {
    Incise {
      @Override
      IncisePoint buildAction0(FetchActionModel model) {
        return new IncisePoint(model);
      }
    },
    Collect {
      @Override
      IncisePoint buildAction0(FetchActionModel model) {
        return new Collect(model);
      }
    },
    Goto {
      @Override
      IncisePoint buildAction0(FetchActionModel model) {
        return new Goto(model);
      }
    };
    abstract IncisePoint buildAction0(FetchActionModel model);

    public static IncisePoint buildAction(FetchActionModel model) {
      ActionType actionTypeEnum =
          WebFetchEnums.getEnumValue(ActionType.class, model.getActionType());
      if (null == actionTypeEnum) {
        return null;
      }
      return actionTypeEnum.buildAction0(model);
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T extends Enum> T getEnumValue(Class<T> clazz, String name) {
    if (StringUtils.isBlank(name)) return null;
    return (T) Enum.valueOf(clazz, name);
  }

}
