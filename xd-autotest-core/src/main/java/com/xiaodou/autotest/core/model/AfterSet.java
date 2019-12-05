package com.xiaodou.autotest.core.model;

import org.springframework.util.Assert;

import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.VarScope;
import com.xiaodou.autotest.core.interfaces.SetVar;
import com.xiaodou.autotest.core.vo.SandBoxContext;

/**
 * @name @see com.xiaodou.autotest.core.model.AfterSet.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年8月14日
 * @description 后置设置
 * @version 1.0
 */
public class AfterSet implements SetVar {

  /** resultkey 结果集中键值 */
  private String resultkey;
  /** dataType 数据类型 */
  private DataType dataType = DataType.sTring;
  /** mappingKey 目标映射键值 */
  private String mappingKey;

  public String getResultkey() {
    return resultkey;
  }

  public void setResultkey(String resultkey) {
    this.resultkey = resultkey;
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

  public String getMappingKey() {
    return mappingKey;
  }

  public void setMappingKey(String mappingKey) {
    this.mappingKey = mappingKey;
  }

  @Override
  public void setVar(SandBoxContext sandBox) {
    Object value = sandBox.getParam(resultkey);
    if (null != value) {
      VarScope.global.setVar(sandBox, mappingKey, dataType.formatData(value));
    }
  }

}
