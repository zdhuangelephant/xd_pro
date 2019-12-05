package com.xiaodou.oms.agent.vo.response;

import java.util.Collections;
import java.util.List;

/**
 * Created by zyp on 14-7-16.
 */
public class BaseResponseList<T> extends BaseResponse {

    private int totalPage = 1;

    private int pageNo = 1;

    private long totalCount = 0;

    private List<T> list = Collections.emptyList();

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
