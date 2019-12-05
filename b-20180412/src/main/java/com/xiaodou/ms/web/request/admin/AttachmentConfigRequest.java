package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-26.
 */
public class AttachmentConfigRequest extends BaseRequest {

  /**
   * 附件最大尺寸
   */
  private String attachmentSize;

  /**
   * 附件类型
   */
  private String attachmentType;

  /**
   * 是否开始水印
   */
  private String openWatermark;

  /**
   * 水印添加条件
   */
  private String watermarkCondition;

  /**
   * 水印图片
   */
  private String watermarkPic;

  /**
   * 水印文本
   */
  private String watermarkText;

  /**
   * 水印透明度
   */
  private String watermarkTransparency;

  /**
   * 水印位置
   */
  private String watermarkPosition;

  /**
   * 图片质量
   */
  private String picWeight;

  public String getAttachmentSize() {
    return attachmentSize;
  }

  public void setAttachmentSize(String attachmentSize) {
    this.attachmentSize = attachmentSize;
  }

  public String getAttachmentType() {
    return attachmentType;
  }

  public void setAttachmentType(String attachmentType) {
    this.attachmentType = attachmentType;
  }

  public String getOpenWatermark() {
    return openWatermark;
  }

  public void setOpenWatermark(String openWatermark) {
    this.openWatermark = openWatermark;
  }

  public String getWatermarkCondition() {
    return watermarkCondition;
  }

  public void setWatermarkCondition(String watermarkCondition) {
    this.watermarkCondition = watermarkCondition;
  }

  public String getWatermarkPic() {
    return watermarkPic;
  }

  public void setWatermarkPic(String watermarkPic) {
    this.watermarkPic = watermarkPic;
  }

  public String getWatermarkText() {
    return watermarkText;
  }

  public void setWatermarkText(String watermarkText) {
    this.watermarkText = watermarkText;
  }

  public String getWatermarkTransparency() {
    return watermarkTransparency;
  }

  public void setWatermarkTransparency(String watermarkTransparency) {
    this.watermarkTransparency = watermarkTransparency;
  }

  public String getWatermarkPosition() {
    return watermarkPosition;
  }

  public void setWatermarkPosition(String watermarkPosition) {
    this.watermarkPosition = watermarkPosition;
  }

  public String getPicWeight() {
    return picWeight;
  }

  public void setPicWeight(String picWeight) {
    this.picWeight = picWeight;
  }

  @Override
  public void validate(Object o, Errors errors) {
    //    super.validate(o, errors);
  }
}
