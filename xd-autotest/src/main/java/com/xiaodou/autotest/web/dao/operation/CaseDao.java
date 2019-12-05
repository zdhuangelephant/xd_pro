package com.xiaodou.autotest.web.dao.operation;

import org.springframework.stereotype.Repository;

import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.summer.dao.jdbc.BaseDao;
/**
 * Created by zhouhuan on 17.08.15.
 */
@Repository("caseDao")
public class CaseDao extends BaseDao<Case> {
}
