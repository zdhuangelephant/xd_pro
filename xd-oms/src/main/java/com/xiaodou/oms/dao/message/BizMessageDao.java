
package com.xiaodou.oms.dao.message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiaodou.oms.util.MessageConnUtil;
import com.xiaodou.oms.util.model.BizMessageInfo;



/**
 * @ClassName: ProductMessageDao

 * @Description: 获取各业务线消息记录dao

 * @author Guanguo.Gao

 * @date 2014年8月29日 下午3:31:38

 * @version V1.0
 */
@Repository("bizMessageDao")
public class BizMessageDao {

    /**
     * @Description: 根据业务线获得对应的数据
     * @param productLine 业务线代号
     * @return
     * @throws
     */
    public List<String> queryBizTagList(Map<String, Object> condMap){
        List<String> tagList = new ArrayList<>();
        String productLine = (String) condMap.get("productLine");
        Timestamp upperTime = (Timestamp) condMap.get("checkTimeUpper");
        Timestamp lowerTime = (Timestamp) condMap.get("checkTimeLower");
        BizMessageInfo info = MessageConnUtil.queryInfoByProduct(productLine);
        DBUtil dbUtil = new DBUtil(info.getDriverClassName(), info.getUrl(), info.getUsername(),
                info.getPassword(), info.getTagColumnName());
        StringBuffer bufferSql = new StringBuffer("select " + info.getTagColumnName() +" from "
                              + info.getTableName() + " where 1 = 1 ");
        if(upperTime != null){
            bufferSql.append(" and insert_time <='" + upperTime.toString()+"'");
        }
        if(lowerTime != null){
            bufferSql.append(" and insert_time >='" + lowerTime.toString()+"'");
        }

        tagList = dbUtil.queryTagList(bufferSql.toString());
        dbUtil.close();
        return tagList;
    }
    
}
