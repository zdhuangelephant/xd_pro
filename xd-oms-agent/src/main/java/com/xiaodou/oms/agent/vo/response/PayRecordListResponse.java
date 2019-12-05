package com.xiaodou.oms.agent.vo.response;



import com.xiaodou.oms.agent.model.PayRecord;

import java.util.List;

/**
 * Created by zyp on 14-7-4.
 */
public class PayRecordListResponse extends BaseResponse {

    /**分页信息*/
    private PageInfo page;

    /**payrecordList*/
    private List<PayRecord> list;

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List<PayRecord> getList() {
        return list;
    }

    public void setList(List<PayRecord> list) {
        this.list = list;
    }
}
