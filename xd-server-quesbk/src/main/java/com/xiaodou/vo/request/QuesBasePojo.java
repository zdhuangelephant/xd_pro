package com.xiaodou.vo.request;

import com.xiaodou.summer.source.jdbc.helper.RealSqlSessionKeyHolder;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class QuesBasePojo extends BaseValidatorPojo {

    protected String traceId;

    @NotEmpty
    protected String uid;

    @NotEmpty
    // @LegalValueList({"2"})
    private String module;

    @NotEmpty
    private String typeCode;
    /** majorId 专业ID */
    private String majorId;

    private String courseId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
        setRealSqlSessionKey(module);
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }


    /**
     * @description 设置多数据源key
     * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
     * @Date 2018年6月20日
     * @param module
     */
    public void setRealSqlSessionKey(String module) {
        RealSqlSessionKeyHolder.getHolder().setIsUsekey(true);
        RealSqlSessionKeyHolder.getHolder().setRealSqlSessionKey(module);
    }
    
}
