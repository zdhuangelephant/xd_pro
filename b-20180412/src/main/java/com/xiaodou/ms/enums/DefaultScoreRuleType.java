package com.xiaodou.ms.enums;

public enum DefaultScoreRuleType {    
  LEARN(501, "课件学习","课件学习", 0.00, 0), 
  ACTIVITY(502, "班级活动","班级活动",  0.00, 0),
  CHAPTER_EXERCISE(901,"节练习","节练习",  0.00,  0),
  SUMMRISE(902, "章总结","章总结", 0.00 ,0),
  FINAL_TEST(903, "期末测试", "期末测试", 0.00 ,0),
  MACHINE_TEST(904,"机考", "机考",0.00 , 0),
  STADARD_TASK(905, "标准任务" ,"标准任务", 0.00 ,0), 
  MAKE_UP(906, "查漏补缺", "查漏补缺" , 0.00 , 0);
  
  
  private final Short code;    
  private final String abtractInfo;    
  
  /** moreInfo 详细描述 */
  private final String moreInfo;
  /** weight 权重 */
  private final Double weight;
  /** order 排序 */
  private final Integer order;
  
  
  
  private DefaultScoreRuleType(int code, String abtractInfo,String moreInfo,Double weight,Integer order) {    
      this.code = (short) code;    
      this.abtractInfo = abtractInfo;    
      this.moreInfo = moreInfo;    
      this.weight = weight;    
      this.order = order;    
  }    
  
  public String getMoreInfo() {
    return moreInfo;
  }

  public Double getWeight() {
    return weight;
  }

  public Integer getOrder() {
    return order;
  }

  public String getAbtractInfo() {    
      return abtractInfo;    
  }    
  
  public Short getCode() {    
      return code;    
  }    
}   