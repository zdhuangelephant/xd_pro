package com.xiaodou.summer.dao.param;

import java.util.Map;

/**
 * @name @see com.xiaodou.summer.dao.param.IJoinQueryParam.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description SQL_JOIN查询参数类
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface IJoinQueryParam extends IQueryParam {

  public void addJoin(String key, Object value);

  public Map getJoin();

}
