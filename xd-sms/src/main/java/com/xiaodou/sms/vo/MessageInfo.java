package com.xiaodou.sms.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name MessageInfo 
 * CopyRright (c) 2018 by 李德洪
 *
 * @author 李德洪
 * @date 2018年3月9日
 * @description TODO
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageInfo extends PlatformInfo {

  /** content 短信内容 */
  private String content;
  /** telephone 手机号 */
  private String telephone;
  /**productLine 产品线*/
  private String productLine;

}
