package com.xiaodou.common.util.warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 增强的ArrayList 增加了addAttribute方法，和getAttribute方法 对应xml节点的attribute
 * 
 * Date: 2014/5/12 Time: 10:21
 * 
 * @author Tian.Dong
 */
public class CustomArrayList<T> extends ArrayList<T> {
  /** serialVersionUID */
  private static final long serialVersionUID = 2546110050058633857L;
  private Map<String, Object> map = new HashMap<String, Object>();

  public void addAttribute(String key, Object value) {
    map.put(key, value);
  }

  public Object getAttribute(String key) {
    return map.get(key);
  }
}
