package com.xiaodou.autotest.core.model;

import org.springframework.util.Assert;

import com.xiaodou.autotest.core.ActionEnum.DataSource;
import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.VarScope;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.interfaces.SetVar;
import com.xiaodou.autotest.core.vo.SandBoxContext;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.autotest.core.model.PreSet.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月11日
 * @description 前置设置
 * @version 1.0
 */
public class PreSet implements SetVar {
  /** dataSource 数据源 */
  private DataSource dataSource = DataSource.Local;
  /** scope 影响范围 */
  private VarScope scope = VarScope.part;
  /** key 键 */
  private String key;
  /** dataType 数据类型 */
  private DataType dataType = DataType.sTring;
  /** assignment 赋值语句 */
  private String assignment;

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    Assert.notNull(dataSource, "dataSource can't be null.");
    this.dataSource = dataSource;
  }

  public void setDataSource(String dataSource) {
    DataSource eDataSource = ActionTool.getEnumValue(DataSource.class, dataSource);
    setDataSource(eDataSource);
  }

  public VarScope getScope() {
    return scope;
  }

  public void setScope(VarScope scope) {
    Assert.notNull(scope, "scope can't be null.");
    this.scope = scope;
  }

  public void setScope(String scope) {
    VarScope varScope = ActionTool.getEnumValue(VarScope.class, scope);
    setScope(varScope);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    Assert.notNull(dataType, "dataType can't be null.");
    this.dataType = dataType;
  }

  public void setDataType(String dataType) {
    DataType eDataType = ActionTool.getEnumValue(DataType.class, dataType);
    setDataType(eDataType);
  }

  public String getAssignment() {
    return assignment;
  }

  public void setAssignment(String assignment) {
    this.assignment = assignment;
  }

  @Override
  public void setVar(SandBoxContext sandBox) {
    if (StringUtils.isNotBlank(assignment)) {
      dataSource.setValue(assignment, this, sandBox);
    }
  }
}
