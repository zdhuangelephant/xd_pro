package com.xiaodou.autotest.web.dao.operation;

import org.springframework.stereotype.Repository;

import com.xiaodou.autotest.web.model.operation.CaseRequest;
import com.xiaodou.autotest.web.model.operation.DocRequest;
import com.xiaodou.summer.dao.jdbc.BaseDao;
/**
 * Created by zhouhuan on 17.08.15.
 */
@Repository("caseRequestDao")
public class CaseRequestDao extends BaseDao<CaseRequest> {
}
