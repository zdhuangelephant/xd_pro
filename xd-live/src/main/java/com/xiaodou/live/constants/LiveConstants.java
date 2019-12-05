package com.xiaodou.live.constants;

/**
 * @name LiveConstants 
 * CopyRright (c) 2016 by zhaodan
 *
 * @author zhaodan
 * @date 2016年8月26日
 * @description 直播系统常量
 * @version 1.0
 */
public interface LiveConstants {

  /** FIRST_PAGENO 首页页码 */
  public static final Integer I_FIRST_PAGENO = 1;
  public static final String S_FIRST_PAGENO = "1";
  
  /** LIVE_CHARGETYPE_FREE 直播收费类型 免费 */
  public static final Short LIVE_CHARGETYPE_FREE = 0;
  /** LIVE_CHARGETYPE_UNFREE 直播收费类型 收费 */
  public static final Short LIVE_CHARGETYPE_UNFREE = 1;
  
  /** LIVE_REVIEWRESULT_DRAFT 直播审核状态 草稿 */
  public static final Short LIVE_REVIEWRESULT_DRAFT = 99;
  /** LIVE_REVIEWRESULT_TOENROLL 直播审核状态 待审核 */
  public static final Short LIVE_REVIEWRESULT_TOENROLL = 0;
  /** LIVE_REVIEWRESULT_FAILED 直播审核状态 审核失败 */
  public static final Short LIVE_REVIEWRESULT_FAILED = -1;
  /** LIVE_REVIEWRESULT_VERIFIED 直播审核状态 审核通过 */
  public static final Short LIVE_REVIEWRESULT_VERIFIED = 1;

}
