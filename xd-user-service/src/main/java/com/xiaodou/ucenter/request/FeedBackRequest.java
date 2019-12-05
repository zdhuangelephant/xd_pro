package com.xiaodou.ucenter.request;

import org.springframework.validation.Errors;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public class FeedBackRequest extends BaseRequest {

  @NotEmpty
  private String feedContent;

  public String getFeedContent() {
    return feedContent;
  }

  public void setFeedContent(String feedContent) {
    this.feedContent = feedContent;
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (errors.hasErrors()) return errors;
    if (StringUtils.isNotBlank(feedContent) && feedContent.length() > 1000)
      errors.rejectValue("feedContent", null, "feedContent is too long.");
    return errors;
  }
}
