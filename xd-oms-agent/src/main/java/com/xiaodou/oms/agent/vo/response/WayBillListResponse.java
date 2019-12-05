package com.xiaodou.oms.agent.vo.response;

import java.util.List;

import com.xiaodou.oms.agent.model.WayBill;

/**
 * WayBillListResponse
  * @Title: WayBillListResponse
  * @Desription 发票列表返回
  * @author Guanguo.Gao
  * @date 2014年12月2日 下午6:21:42
 */
public class WayBillListResponse extends BaseResponse{

    /** 分页信息 **/
    private PageInfo page;
    
    /** 发票列表 **/
    private List<WayBill> wayBillList;

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
     * getter method for wayBillList
     * @return the wayBillList
     */
    public List<WayBill> getWayBillList() {
        return wayBillList;
    }

    /**
     * setter method for wayBillList
     * @Description the wayBillList to set
     * @param wayBillList 
     */
    public void setWayBillList(List<WayBill> wayBillList) {
        this.wayBillList = wayBillList;
    }
    
}
