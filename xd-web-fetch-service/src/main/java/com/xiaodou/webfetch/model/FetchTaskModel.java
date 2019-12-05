package com.xiaodou.webfetch.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;

import lombok.Data;

/**
 * @name @see com.xiaodou.webfetch.model.FetchTaskModel.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月8日
 * @description 抓取任务模型
 * @version 1.0
 */
@Data
public class FetchTaskModel {
  /** id 主键ID */
  private String id;
  private String url;
  private String dependActionId;
  private String targetFieldId;
  private String startActionId;
  private List<FetchActionModel> actionList = Lists.newArrayList();

  /**
   * @name @see com.xiaodou.webfetch.model.FetchTaskModel.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年12月15日
   * @description 抓取动作模型
   * @version 1.0
   */
  @Data
  public static class FetchActionModel {
    /** id 主键ID */
    private String id;
    /** actionType 动作类型 */
    private String actionType;
    /** dependActionId 依赖动作ID */
    private String dependActionId;
    /** supporter 载体数据集Id */
    private String supporterFieldId;
    /** targetFieldId 切割数据集ID */
    private String targetFieldId;
    /** inciseRule 切割规则 */
    private String inciseRule;
    /** needLoop 是否需要轮训,默认不需要 */
    private Boolean needLoop = Boolean.FALSE;
    /** splitSep 切割符 */
    private String splitSep = StringUtils.EMPTY;
    /** split 是否切割 */
    private boolean split = false;
    /** storeKey 保存key */
    private List<String> storeKey = Lists.newArrayList();
  }
}
