
package com.xiaodou.oms.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author Guanguo.Gao
 * @version V1.0
 * @ClassName: CollectionUtil
 * @Description: 集合操作类
 * @date 2014年9月2日 下午2:42:12
 */
public class CollectionUtil {

  /**
   * @param t1 第一个集合
   * @param t2 第二个集合
   * @return 返回在第一个集合中，不在第二个集合中的元素列表
   * @throws
   * @Description: 求两个集合的差异，返回在第一个集合中，不在第二个集合中的元素列表
   * 采用的方法是hashset，然后判断第一个集合中的元素是否在set中
   */
  public static <T> List<T> compareAndReturnFirstPlus(List<T> t1, List<T> t2) {
    if (t2 == null) {
      return t1;
    }
    List<T> retList = new ArrayList<>();
    //这样更加友好一些
    if (t1 == null)
      return retList;
    Set<T> t2_set = new HashSet<>(t2);
    Iterator<T> iter = t1.iterator();
    while (iter.hasNext()) {
      T value = iter.next();
      //如果包含该字段
      if (!t2_set.contains(value)) {
        retList.add(value);
      }
    }
    return retList;
  }

}
