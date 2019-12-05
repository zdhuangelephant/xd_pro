package com.xiaodou.ms.enums;

public enum ExamRuleType {
    CHAPTER_SUMMRISE ("章总结闯关",5),
    SECTION_PRACTICE("节闯关练习",6),
    RANDOM_CHALLENGE("随机挑战",7),
    FIENDS_CHALLENGE("好友挑战",8),
    DALIY_PRACTICE("每日一练",9),
    FINAL_TEST("期末测试",10),
    MAKE_UP("查漏补缺",11);
    
    /**name 命题蓝图名字*/
    private final String name;
    /**value 命题蓝图代号*/
    private final Integer value;
    
    private ExamRuleType(String name,Integer value) {
        this.value = value;
        this.name= name;
    }
    
    public Integer getExamRuleTypeValue() {
        return value;
    }
    
    public String getExamRuleTypeName() {
        return name;
    }
}
