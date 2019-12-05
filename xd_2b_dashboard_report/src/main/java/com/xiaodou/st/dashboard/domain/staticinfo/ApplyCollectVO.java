package com.xiaodou.st.dashboard.domain.staticinfo;

import lombok.Data;

/**
 * @name ApplyCollectVO CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年3月7日
 * @description SC=studentCount(学生人数)<br>
 *              AC=applyCount(科次)
 * @version 1.0
 */
@Data
public class ApplyCollectVO {

  /** pilotUnitId 第三级单位id */
  private Long pilotUnitId;
  /** pilotUnitName 第三级单位名称 */
  private String pilotUnitName;
  /** totalApplySC 报名总人数 */
  private Integer totalApplySC = 0;
  /** notPaymentAC 未缴费科次 */
  private Integer notPaymentAC = 0;
  /** waitingPaymentAC 待缴费科次 */
  private Integer waitingPaymentAC = 0;
  /** alreadyPaymentAC 已缴费科次 */
  private Integer alreadyPaymentAC = 0;
  /** applyAC 后台报名完成科次 */
  private Integer applyAC = 0;
  /** businessApplySuccessAC 业务系统报名成功科次 */
  private Integer businessApplySuccessAC = 0;
  /** businessApplyAlreadyAC 重复购买科次 */
  private Integer businessApplyAlreadyAC = 0;
  /** totalApplyAC 总科次 */
  private Integer totalApplyAC = 0;


}
