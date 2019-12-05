package com.xiaodou.oms.agent.vo.response;

import java.util.List;

import com.xiaodou.oms.agent.model.OrderItem;

/**
 * OrderOrderItemResponse
  * @Title: OrderOrderItemResponse
  * @Desription order - orderItem返回
  * @author Guanguo.Gao
  * @date 2015年1月4日 下午7:52:57
 */
public class OrderOrderItemResponse extends BaseResponse{
    
    /**
     * 分页信息
     */
    private PageInfo page;
    
    /**
     * item列表
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
