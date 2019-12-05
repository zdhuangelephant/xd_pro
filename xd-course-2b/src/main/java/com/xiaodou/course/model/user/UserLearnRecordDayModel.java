package com.xiaodou.course.model.user;

import java.sql.Timestamp;
import java.util.Date;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.util.StringUtils;

import lombok.Data;

/**
 * 
 * @name UserLearnRecordDayModel
 * @CopyRright (c) 2016 by 李德洪
 * 
 * @author 李德洪
 * @date 2016年5月31日
 * @description TODO
 * @version 1.0
 */
@Data
public class UserLearnRecordDayModel {

  // id
  @Column(isMajor = true)
  private Long id;

  // 用户Id
  @Column(canUpdate = false)
  private Long userId;

  // 产品Id
  @Column(canUpdate = false)
  private Long productId;

  // appId
  @Column(canUpdate = false)
  private Integer moduleId;

  // 章节Id
  @Column(canUpdate = true)
  private Long chapterId;

  // 条目Id
  @Column(canUpdate = true)
  private Long itemId;

  // 学习时长
  @Column(canUpdate = true)
  private Integer learnTime;

  // 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）
  private Short learnType;

  // 记录时间(按天算)
  private Date recordTime;

  // 创建时间
  private Timestamp createTime;


  public Integer getLearnTime() {
    return learnTime;
  }

  public void setLearnTime(Integer learnTime) {
    this.learnTime = learnTime;
  }

  public Short getLearnType() {
    return learnType;
  }

  public void setLearnType(Short learnType) {
    this.learnType = learnType;
  }

  public Date getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(Date recordTime) {
    this.recordTime = recordTime;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public static class LearnRecordData {
    /* 日期eg:2016-02-10 */
    private String recordTime = StringUtils.EMPTY;
    /* 学习时长 */
    private String learnTime = StringUtils.EMPTY;

    public LearnRecordData() {}

    public LearnRecordData(String recordTime) {
      this.recordTime = recordTime;
    }

    public LearnRecordData(String recordTime, String learnTime) {
      super();
      this.recordTime = recordTime;
      this.learnTime = learnTime;
    }

    public String getRecordTime() {
      return recordTime;
    }

    public void setRecordTime(String recordTime) {
      this.recordTime = recordTime;
    }


    public String getLearnTime() {
      return StringUtils.isNotBlank(learnTime) && !("0").equals(learnTime)
          ? this.getLongStringValue(Double.valueOf(learnTime) / 60)
          : "0";
    }

    /**
     * 获取字符串类型整型
     * 
     * @param dValue 双精度浮点类型
     * @return 字符串整型
     */
    public String getLongStringValue(Double dValue) {
      try {
        return Long.toString(new Double(Math.ceil(dValue)).longValue());
      } catch (Exception e) {
        return "0";
      }
    }

    public void setLearnTime(String learnTime) {
      this.learnTime = learnTime;
    }
  }

  public static void main(String[] args) {
    MybatisXmlTool
        .getInstance(UserLearnRecordDayModel.class, "xd_user_learn_record_day",
            "D:\\work\\workspace_xiaodou\\xd-course-2b/src/main/resources/conf/mybatis/user/")
        .buildXml();
    // System.out.println(10d / 60);
    // System.out.println(Long.toString(new Double(Math.ceil(10d / 60)).longValue()));
  }
}
