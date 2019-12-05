package com.xiaodou.st.dashboard.dao;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.st.dashboard.dao.GroupParam.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description 聚合查询参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class GroupParam extends QueryParam implements IGroupParam {

  /** group 聚合查询参数 */
  private Map<String, Object> group = Maps.newHashMap();


  @Override
  public void addGroup(String columnName) {
    group.put(columnName, StringUtils.EMPTY);
  }

  public void addGroups(List<String> columnNames) {
    for (String column : columnNames)
      addGroup(column);
  }

  @Override
  public Map getGroup() {
    return group;
  }

}
