package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.constant.QuesBaseConstant;
import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name FaceRecognitionRequest
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 面部识别请求类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FaceRecognitionRequest extends MapiBaseRequest {
  /** type 类型 */
  @NotEmpty
  @LegalValueList(value = {QuesBaseConstant.FACE_RECOGNITION_ALARM,
      QuesBaseConstant.FACE_RECOGNITION_NOTALARM})
  private String type;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;
  /** paperId 试卷ID */
  @NotEmpty(field = "type", value = QuesBaseConstant.FACE_RECOGNITION_ALARM)
  private String paperId;
  /** faceUrl 面庞图片地址 */
  @NotEmpty
  private String faceUrl;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getPaperId() {
    return paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getFaceUrl() {
    return faceUrl;
  }

  public void setFaceUrl(String faceUrl) {
    this.faceUrl = faceUrl;
  }

}
