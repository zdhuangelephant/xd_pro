package com.xiaodou.userCenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.CommUtil;

/**
 * 
 * 表操作基本抽象service类
 *
 * 2015年3月26日 下午4:25:55   weirong.li     1.0   
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public abstract class BaseDaoService {

  public void deleteById(List<Long> listId) {}


  /**
   * 
   * 构建查询条件 
   *
   * @param queryIdList
   * @param listSequence
   * @param queryCond
   * @return
   */
  protected Map<String, Object> getQueryCond(List<Long> queryIdList, List<String> listSequence,
      Object queryCond) {
    Map<String, Object> input = new HashMap<String, Object>();
    if (queryIdList != null && queryIdList.size() > 0) {
      input.put("listId", queryIdList);
    }

    if (listSequence != null && listSequence.size() > 0) {
      input.put("listSequence", listSequence);
    }

    if (queryCond != null) {
      CommUtil.transferFromVO2Map(input, queryCond);
    }

    return input;
  }
}
