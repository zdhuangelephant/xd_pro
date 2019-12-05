package com.xiaodou.oms.agent.common.enums;

/**
 * PaymentOperationType
  * @Title: PaymentOperationType
  * @Desription payment操作类型
  * @author Guanguo.Gao
  * @date 2014年12月4日 下午2:02:36
 */
public enum PaymentOperationType {
    Refund(1, "Refund", "退款"),
    Receipt(0, "Receipt", "收款");
    
    private int typeId;
    
    private String typeName;
    
    private String typeDesc;

    /** Constructor for PaymentOperationType. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param typeId
      * @param typeName
      * @param typeDesc
      */
    
    private PaymentOperationType(int typeId, String typeName, String typeDesc) {
	this.typeId = typeId;
	this.typeName = typeName;
	this.typeDesc = typeDesc;
    }

    /**
     * getter method for typeId
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * setter method for typeId
     * @Description the typeId to set
     * @param typeId 
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * getter method for typeName
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * setter method for typeName
     * @Description the typeName to set
     * @param typeName 
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * getter method for typeDesc
     * @return the typeDesc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * setter method for typeDesc
     * @Description the typeDesc to set
     * @param typeDesc 
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
    
}
