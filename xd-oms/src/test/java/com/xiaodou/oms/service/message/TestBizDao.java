package com.xiaodou.oms.service.message;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.dao.message.BizMessageDao;

public class TestBizDao extends BaseSpringTest {

//    @Test
    public void testConnection() {
        BizMessageDao bizdao = new BizMessageDao();
        Map<String, Object> condMap = new HashMap<String, Object>();
        condMap.put("productLine", "05");
        condMap.put("checkTimeUpper", new Timestamp(System.currentTimeMillis()));
        condMap.put("checkTimeLower", new Timestamp(System.currentTimeMillis() + 48 * 60 *60 *1000));
        List<String> tags = bizdao.queryBizTagList(condMap);
        System.out.println(tags);
    }
}
