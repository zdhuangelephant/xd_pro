package com.xiaodou.oms.agent.vo.response;


import com.xiaodou.oms.agent.model.OrderItem;

import java.util.List;

/**
 * Created by zyp on 14-7-2.
 */
public class GorderOrderItemListResponse extends BaseResponse {

    /**分页信息*/
    private PageInfo page;

    /**item列表*/
    private List<OrderItem> list;

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }
}
