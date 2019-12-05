package com.xiaodou.autotest.core.deserialize.dmodel;

import org.springframework.util.Assert;

import com.xiaodou.autotest.core.ActionEnum.DataType;
import com.xiaodou.autotest.core.ActionEnum.ParamType;
import com.xiaodou.autotest.core.ActionTool;
import com.xiaodou.autotest.core.vo.SandBoxContext;

/**
 * @name @see com.xiaodou.server.mapi.engine.model.Param.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 反序列化参数模型
 * @version 1.0
 */
public class MidParam {

  /** constructor **/
  public MidParam() {}

  /** name 参数名 */
  private String name;
  /** type 参数类型 */
  private ParamType paramType;
  /** dataType 数据类型 */
  private DataType dataType;
  /** desc 参数描述 */
  private String desc;
  /** value 参数值 */
  private String value;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public ParamType getParamType() {
    return paramType;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setParamType(ParamType paramType) {
    Assert.notNull(paramType, "Param's paramType can't be null.");
    this.paramType = paramType;
  }

  public void setDataType(DataType dataType) {
    Assert.notNull(dataType, "Param's dataType can't be null.");
    this.dataType = dataType;
  }

  public void setParamType(String typeName) {
    ParamType type = ActionTool.getEnumValue(ParamType.class, typeName);
    setParamType(type);
  }

  public void setDataType(String typeName) {
    DataType type = ActionTool.getEnumValue(DataType.class, typeName);
    setDataType(type);
  }

  public void fillParam(SandBoxContext sandBox) {
    Object oValue = ActionTool.getValue(sandBox, value);
    if (dataType == null) {
      value = oValue.toString();
    } else {
      value = dataType.formatData(oValue);
    }
  }
}
