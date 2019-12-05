package com.xiaodou.oms.agent.common.enums;

/**
 * TableSortOrder
  * @Title: TableSortOrder
  * @Desription 数据库排序方式
  * @author Guanguo.Gao
  * @date 2015年2月12日 下午5:50:39
 */
public enum OrderSortType {
    
    DESC("DESC"), ASC("ASC");
    
    /**
     * Constructor for OrderSortType. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param orderType
     */
    private OrderSortType(String orderType){
	this.name = orderType;
    }
    
    /**
     * name
     */
    private String name;

    /**
     * getter method for name
     * @return the name
     */
    public String getName() {
        return name;
    }

}
