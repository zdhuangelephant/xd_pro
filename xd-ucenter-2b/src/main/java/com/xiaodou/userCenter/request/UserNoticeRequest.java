package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public class UserNoticeRequest extends BaseRequest {

    @NotEmpty
    private String noticeId;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public static class UserNoticeGetRequest extends BaseValidatorPojo {
        @NotEmpty
        private String noticeId;

        public String getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(String noticeId) {
            this.noticeId = noticeId;
        }
    }

}
