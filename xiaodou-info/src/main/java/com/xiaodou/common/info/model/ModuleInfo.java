package com.xiaodou.common.info.model;

import lombok.Data;

@Data
public class ModuleInfo {
  private Integer id;
  private String module;
  private String moduleName;
  private String listOrder;
  private String detail;
  private Short isFirstChoice;

}
