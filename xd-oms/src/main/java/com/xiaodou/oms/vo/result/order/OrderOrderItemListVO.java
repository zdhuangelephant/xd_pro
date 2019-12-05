package com.xiaodou.oms.vo.result.order;

import java.util.List;

import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * OrderOrderItemListVO
  * @Title: OrderOrderItemListVO
  * @Desription order - orderitem 结果返回
  * @author Guanguo.Gao
  * @date 2015年1月5日 上午10:26:35
 */
public class OrderOrderItemListVO extends ResultInfo{

    /**
     * Constructor for OrderOrderItemListVO. 
      * <p>Title: </p>
      * <p>Description: </p>
      * @param type
     */
    public OrderOrderItemListVO(ResultType type){
	super(type);
    }
    
    /**
     * 分页信息
     */
    private PageInfo page;
    
    /**
     * 查询结果集
     */
    private List<OrderItem> list;

    /**
     * getter method for page
     * @return the page
     */
    public PageInfo getPage() {
        return page;
    }

    /**
     * setter method for page
     * @Description the page to set
     * @param page 
     */
    public void setPage(PageInfo page) {
        this.page = page;
    }

    /**
     * getter method for list
     * @return the list
     */
    public List<OrderItem> getList() {
        return list;
    }

    /**
     * setter method for list
     * @Description the list to set
     * @param list 
     */
    public void setList(List<OrderItem> list) {
        this.list = list;
    }
    
}
