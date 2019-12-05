package com.xiaodou.st.dashboard.domain.rule;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.st.dashboard.domain.rule.RawDataProductScorePointRule.RuleInfo;

import lombok.Data;

/**
 * @name @see com.xiaodou.st.dataclean.model.domain.raw.RawDataUserScorePointRecord.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">huangzedong</a>
 * @date 2018年4月20日
 * @description 慕享用户积分点条目明细表
 * @version 1.0
 */
@Data
public class RawDataUserScorePointRecord {

  /** id 主键 */
  @Column(isMajor = true, sortBy = true, listValue = true)
  private String id;
  /** ruleType 规则类型 */
  @Column(listValue = true)
  private Short ruleType;
  /** module 地域模块 */
  private String module;
  /** typeCode 专业码值(非持久化字段) */
  @Column(persistent = false)
  private String typeCode;
  /** productId 产品ID */
  private Long productId;
  /** weight 所占权重(非持久化字段) */
  @Column(persistent = false)
  private RuleInfo ruleInfo;
  /** userId 用户ID */
  private Long userId;
  /** score 计分点成绩 */
  private Double score = 0d;
  /** completePercent 计分点完成度 */
  private Double completePercent = 0d;
  /** createTime 创建时间 */
  @Column(betweenScope = true, sortBy = true)
  private Timestamp createTime;
  /** modifyTime 修改时间 */
  @Column(betweenScope = true, sortBy = true)
  private Timestamp modifyTime;

  public static void main(String[] args) {
    MybatisXmlTool.getInstance(RawDataUserScorePointRecord.class, "xd_raw_data_user_score_point_record",
        "src/main/resources/conf/mybatis/raw")
        .buildXml();
  }
}
