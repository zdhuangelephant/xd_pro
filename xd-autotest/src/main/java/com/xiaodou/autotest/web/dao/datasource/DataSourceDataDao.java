package com.xiaodou.autotest.web.dao.datasource;

import org.springframework.stereotype.Repository;

import com.xiaodou.autotest.web.model.datasource.DataSourceData;
import com.xiaodou.autotest.web.model.operation.Case;
import com.xiaodou.summer.dao.jdbc.BaseDao;
/**
 * Created by zhouhuan on 17.08.15.
 */
@Repository("dataSourceDataDao")
public class DataSourceDataDao extends BaseDao<DataSourceData> {
}
