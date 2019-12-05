package com.xiaodou.sms.vo;

/**
 * 
 * 查询余额response
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public class GetNumResult extends MessageResult{
  
  /**
   * 余额
   */
  private String number;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
  
}
