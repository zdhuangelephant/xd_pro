package com.xiaodou.oms.statemachine.engine.model.temp;

/**
 * <p>模板Pojo类型枚举</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public enum TempPojo{
    SimpleTemp("SimpleTemp",SimpleTemp.class),
    CompleteTemp("CompleteTemp",CompleteTemp.class);
    
    private String name;
    private Class<? extends IBaseTempPojo> pojo;
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public Class<? extends IBaseTempPojo> getPojo() {
      return pojo;
    }
    public void setPojo(Class<? extends IBaseTempPojo> pojo) {
      this.pojo = pojo;
    }
    TempPojo(String name, Class<? extends IBaseTempPojo> pojo){
      this.name=name;
      this.pojo=pojo;
    }
  }