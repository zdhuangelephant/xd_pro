package com.xiaodou.st.dashboard.domain.staticinfo;

import lombok.Data;

/**
 * @name StudentCollectVO CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年3月7日
 * @description SC=studentCount(学生人数)
 * @version 1.0
 */
@Data
public class StudentCollectVO {
  /** pilotUnitId 第三级单位id */
  private Long pilotUnitId;
  /** pilotUnitName 第三级单位名称 */
  private String pilotUnitName;
  /** notRegisterSC 未注册 */
  private Integer notRegisterSC = 0;
  /** successRegisterSC 注册成功 */
  private Integer successRegisterSC = 0;
  /** failRegisterSC 注册失败，已经存在该学生 */
  private Integer failRegisterSC = 0;
  /** errorRegisterSC 注册异常 */
  private Integer errorRegisterSC = 0;
  /** successImportSC 成功导入 */
  private Integer successImportSC = 0;
  /** totalSC 总人数 */
  private Integer totalSC = 0;
}
