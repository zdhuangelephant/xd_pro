package com.xiaodou.summer.validator.enums;

import com.xiaodou.common.util.log.LoggerUtil;


/**
 * 值范围 LT:小于 LE:小于等于 EQ:等于 GT:大于 GE:大于等于
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月11日
 */
@SuppressWarnings("rawtypes")
public enum ValueScope {
  // 小于
  LT {
    @Override
    public boolean compare(Comparable src, Comparable target) {
      try {
        Double srcInt = Double.valueOf(src.toString());
        Double targetInt = Double.valueOf(target.toString());
        return srcInt.compareTo(targetInt) < 0;
      } catch (Exception e) {
        LoggerUtil.error("Err:非法数值.", e);
        return false;
      }
    }
  },
  // 小于等于
  LE {
    @Override
    public boolean compare(Comparable src, Comparable target) {
      try {
        Double srcInt = Double.valueOf(src.toString());
        Double targetInt = Double.valueOf(target.toString());
        return srcInt.compareTo(targetInt) <= 0;
      } catch (Exception e) {
        LoggerUtil.error("Err:非法数值.", e);
        return false;
      }
    }
  },
  // 等于
  EQ {
    @Override
    public boolean compare(Comparable src, Comparable target) {
      return src.equals(target);
    }
  },
  // 大于
  GT {
    @Override
    public boolean compare(Comparable src, Comparable target) {
      try {
        Double srcInt = Double.valueOf(src.toString());
        Double targetInt = Double.valueOf(target.toString());
        return srcInt.compareTo(targetInt) > 0;
      } catch (Exception e) {
        LoggerUtil.error("Err:非法数值.", e);
        return false;
      }
    }
  },
  // 大于等于
  GE {
    @Override
    public boolean compare(Comparable src, Comparable target) {
      try {
        Double srcInt = Double.valueOf(src.toString());
        Double targetInt = Double.valueOf(target.toString());
        return srcInt.compareTo(targetInt) >= 0;
      } catch (Exception e) {
        LoggerUtil.error("Err:非法数值.", e);
        return false;
      }
    }
  };
  public abstract boolean compare(Comparable src, Comparable target);
}
