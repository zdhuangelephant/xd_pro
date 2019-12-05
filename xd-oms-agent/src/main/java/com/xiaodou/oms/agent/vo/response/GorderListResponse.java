package com.xiaodou.oms.agent.vo.response;
import com.xiaodou.oms.agent.model.Gorder;
import java.util.List;

/**
 * Created by zyp on 14-7-2.
 */
public class GorderListResponse extends BaseResponse {

    /**分页信息*/
    private PageInfo page;

    /**gorder 列表*/
    private List<Gorder> list;

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List<Gorder> getList() {
        return list;
    }

    public void setList(List<Gorder> list) {
        this.list = list;
    }
}
