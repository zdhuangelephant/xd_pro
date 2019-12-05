package com.xiaodou.course.vo.message;

import java.sql.Timestamp;

import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.vo.message.UserLearnRecordEvent.TransferUserLearnRecordData;
import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name UserLearnRecordEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 清洗用户学习记录事件
 * @version 1.0
 */
public class UserLearnRecordEvent extends DCCoreEvent<TransferUserLearnRecordData> {
  private static final String EVENT_NAME = "userLearnRecordEvent";

  public UserLearnRecordEvent() {
    setEventName(EVENT_NAME);
  }


  /**
   * @name TransferUserLearnRecordData
   * @CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月28日
   * @description 用户学习记录模型
   * @version 1.0
   */
  public static class TransferUserLearnRecordData {
    // 源记录ID
    @NotEmpty
    private Long srcRecordId;
    // 用户Id
    @NotEmpty
    private Long userId;
    /** typeCode 专业码值 */
    @NotEmpty
    private String typeCode;
    // 产品Id
    @NotEmpty
    private Long productId;
    // 章节Id
    @NotEmpty
    private Long chapterId;
    // 条目Id
    @NotEmpty
    private Long itemId;
    // 记录时间
    @NotEmpty
    private Timestamp recordTime;
    // 学习时长
    @NotEmpty
    private Integer learnTime;
    // appId
    @NotEmpty
    private Integer moduleId;
    @NotEmpty
    private Short learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

    @NotEmpty
    private String learnContent;// 学习内容（eg:第一章第一节）

    public TransferUserLearnRecordData(UserLearnRecordModel learnRecordModel) {
      this.srcRecordId = learnRecordModel.getId();
      this.chapterId = learnRecordModel.getChapterId();
      this.itemId = learnRecordModel.getItemId();
      this.learnContent = learnRecordModel.getLearnContent();
      this.learnTime = learnRecordModel.getLearnTime();
      this.learnType = learnRecordModel.getLearnType();
      this.moduleId = learnRecordModel.getModuleId();
      this.productId = learnRecordModel.getProductId();
      this.recordTime = learnRecordModel.getRecordTime();
      this.typeCode = learnRecordModel.getTypeCode();
      this.userId = learnRecordModel.getUserId();
    }

    public Long getSrcRecordId() {
      return srcRecordId;
    }

    public void setSrcRecordId(Long srcRecordId) {
      this.srcRecordId = srcRecordId;
    }

    public Long getUserId() {
      return userId;
    }

    public void setUserId(Long userId) {
      this.userId = userId;
    }

    public String getTypeCode() {
      return typeCode;
    }

    public void setTypeCode(String typeCode) {
      this.typeCode = typeCode;
    }

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }

    public Long getChapterId() {
      return chapterId;
    }

    public void setChapterId(Long chapterId) {
      this.chapterId = chapterId;
    }

    public Long getItemId() {
      return itemId;
    }

    public void setItemId(Long itemId) {
      this.itemId = itemId;
    }

    public Timestamp getRecordTime() {
      return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
      this.recordTime = recordTime;
    }

    public Integer getLearnTime() {
      return learnTime;
    }

    public void setLearnTime(Integer learnTime) {
      this.learnTime = learnTime;
    }

    public Integer getModuleId() {
      return moduleId;
    }

    public void setModuleId(Integer moduleId) {
      this.moduleId = moduleId;
    }

    public Short getLearnType() {
      return learnType;
    }

    public void setLearnType(Short learnType) {
      this.learnType = learnType;
    }

    public String getLearnContent() {
      return learnContent;
    }

    public void setLearnContent(String learnContent) {
      this.learnContent = learnContent;
    }
  }

}
