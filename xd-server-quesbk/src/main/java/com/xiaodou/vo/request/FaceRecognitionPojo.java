package com.xiaodou.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name FaceRecognitionPojo
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月30日
 * @description 面部识别接口参数类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FaceRecognitionPojo extends QuesBasePojo {
  @NotEmpty
  @LegalValueList(value = {QuesBaseConstant.FACE_RECOGNITION_ALARM,
      QuesBaseConstant.FACE_RECOGNITION_NOTALARM})
  private String type;
  /** srcFaceId 源面容ID */
  private String srcFaceId;
  /** paperId 试卷ID */
  @NotEmpty(field = "type", value = QuesBaseConstant.FACE_RECOGNITION_ALARM)
  private String paperId;
  /** faceUrl 面部图片地址 */
  @NotEmpty
  private String faceUrl;
  /** deviceId 登录设备ID */
  @NotEmpty
  private String deviceId;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSrcFaceId() {
    return srcFaceId;
  }

  public void setSrcFaceId(String srcFaceId) {
    this.srcFaceId = srcFaceId;
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

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
