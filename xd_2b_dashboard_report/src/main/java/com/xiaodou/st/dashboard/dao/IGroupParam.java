package com.xiaodou.st.dashboard.dao;

import java.util.List;
import java.util.Map;

import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.st.dashboard.dao.IGroupParam.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月21日
 * @description sql聚合查询参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IGroupParam extends IQueryParam {

  public void addGroup(String columnName);

  public void addGroups(List<String> columnNames);

  public Map getGroup();

}
