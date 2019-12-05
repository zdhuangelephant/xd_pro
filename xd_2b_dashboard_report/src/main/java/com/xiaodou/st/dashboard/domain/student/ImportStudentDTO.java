package com.xiaodou.st.dashboard.domain.student;

import lombok.Data;

@Data
public class ImportStudentDTO {
    //public String className;
    public String realName;
    public String gender;
    public String telephone;
    public String admissionCardCode;
    
    public String msg;
}
