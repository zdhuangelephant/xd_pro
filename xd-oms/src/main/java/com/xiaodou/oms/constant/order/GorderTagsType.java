package com.xiaodou.oms.constant.order;

/**
 * Gorder订单类型
 * @ClassName GorderTagsType
 * @author Xiaodong.Fan
 * @date 2015年1月29日 下午4:07:57
 */
public enum GorderTagsType {

    NORMAL("1", "正常订单"), 
    DELETED("2", "已删除订单"), 
    HoldingToPay("4","先占座后支付模式"),
    DelayOrderDisable("8","不可延单");
    
    private String code;
    private String name;
    
    private GorderTagsType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    /**
     * 获取假删除标志
     * @param tag
     * @return
     */
    public static String getFakeDeletedStat(int tag){
      return String.valueOf(tag & (Integer.parseInt(GorderTagsType.DELETED.getCode())));
    }
    
    /**
     * 获取先占座后支付模式标志
     * @param tag
     * @return
     */
    public static String getHoldingToPayStat(int tag){
      return String.valueOf(tag & (Integer.parseInt(GorderTagsType.HoldingToPay.getCode())));
    }
    
    /**
     * 获取是否延单的标志
     * @param tag
     * @return
     */
    public static String getDelayOrderStat(int tag){
      return String.valueOf(tag & (Integer.parseInt(GorderTagsType.DelayOrderDisable.getCode())));
    }
    
    /**
     * 获取初始tags值
     * @param types
     * @return
     */
    public static String getInitTag(GorderTagsType...types){
      int code = Integer.valueOf( types[0].getCode()).intValue();
      for(GorderTagsType type:types)
        code = code | Integer.valueOf(type.getCode()).intValue();
      return String.valueOf(code);
    }
    
    public static void main(String[] args) {
    	System.out.println(getInitTag(GorderTagsType.NORMAL,GorderTagsType.DelayOrderDisable,GorderTagsType.HoldingToPay));
	}

}
