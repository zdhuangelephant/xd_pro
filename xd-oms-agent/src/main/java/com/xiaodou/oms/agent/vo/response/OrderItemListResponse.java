package com.xiaodou.oms.agent.vo.response;


import com.xiaodou.oms.agent.model.OrderItem;

import java.util.List;

/**
 * Created by zyp on 14-7-4.
 */
public class OrderItemListResponse extends BaseResponse {

    private List<OrderItem> list;

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }
}
