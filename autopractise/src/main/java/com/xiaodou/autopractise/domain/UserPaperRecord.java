package com.xiaodou.autopractise.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.dao.mongo.annotion.Pk;
import com.xiaodou.summer.dao.mongo.model.MongoBaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserPaperRecord extends MongoBaseModel {
  /** recordId 记录ID */
  @Pk
  private String recordId;
  /** userId 用户ID */
  private String userId;
  /** paperId 试卷ID */
  private String paperId;
}
