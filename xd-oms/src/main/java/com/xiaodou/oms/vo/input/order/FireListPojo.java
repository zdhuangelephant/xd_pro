package com.xiaodou.oms.vo.input.order;

import java.util.List;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>
 * 批量触发状态机事件Pojo对象
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月7日
 */
public class FireListPojo extends BasePojo {

  /**
   * 触发事件
   */
  @NotEmpty
  private String event;

  /**
   * 事件来源IP
   */
  @NotEmpty
  private String ip;

  /**
   * 批量参数pojo
   */
  @NotEmpty
  private List<FirePojo> pojoList;

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public List<FirePojo> getPojoList() {
    return pojoList;
  }

  public void setPojoList(List<FirePojo> pojoList) {
    this.pojoList = pojoList;
  }

}
