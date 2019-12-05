package com.xiaodou.oms.agent.vo.response;


import com.xiaodou.oms.agent.model.Order;

import java.util.List;

/**
 * Created by zyp on 14-7-4.
 */
public class GorderOrderListResponse extends BaseResponse {

    private PageInfo page;

    private List<Order> list;

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
}
