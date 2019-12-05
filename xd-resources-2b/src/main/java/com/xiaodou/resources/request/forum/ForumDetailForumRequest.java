package com.xiaodou.resources.request.forum;

import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.OverComeField;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * 话题详情请求－话题部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午6:12:07
 */

@OverComeField(field = {"uid", "module"}, annotiation = AnnotationType.NotEmpty)
public class ForumDetailForumRequest extends BaseRequest {

  /** 资源ID */
  private String resourcesId;


  /** 倒序查找 的 消息最后一条Id */
  private String idUpper;
  /** 正序查找 的 消息最后一条Id */
  private String idLower;
  /** size 单次查询消息数 */
  private String size = ForumConstant.PAGESIZE;

  public String getIdUpper() {
    return idUpper;
  }

  public void setIdUpper(String idUpper) {
    this.idUpper = idUpper;
  }

  public String getIdLower() {
    return idLower;
  }

  public void setIdLower(String idLower) {
    this.idLower = idLower;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(String resourcesId) {
    this.resourcesId = resourcesId;
  }

}
