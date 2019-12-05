package com.xiaodou.common.util.warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 支持一个key多value key->List<value>
 * 
 * Date: 2014/5/12 Time: 10:52
 * 
 * @author Tian.Dong
 */
public class CustomMapList<K, V> extends HashMap<K, List<V>> {

  /** serialVersionUID */
  private static final long serialVersionUID = 7624087761013607272L;

  public void putMapList(K key, V value) {
    List<V> list = this.get(key);
    if (list == null) {
      list = new ArrayList<V>();
      list.add(value);
      this.put(key, list);
    } else {
      list.add(value);
    }
  }

  public List<V> getAllValue(K key) {
    return this.get(key);
  }

  public V getFirst(K key) {
    List<V> list = this.get(key);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
}
