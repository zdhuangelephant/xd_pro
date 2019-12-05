package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.util.IDGenerator;


/**
 * 订单的数据库操作。 以后移到 业务-DAO转换层。
 * 
 * @author Administrator
 * 
 */

public class GorderDao extends BaseDao<Gorder> {

  public String querySeqId(String prefix) {
    final int INC_MIN_LIMIT = 3;
    final int INC_MAX_LIMIT = 5;
    final int INC_RANGE = INC_MAX_LIMIT - INC_MIN_LIMIT;
    final String SEQ_TABLE_NAME = "xd_order_sequence_id";

    Random r = new Random();
    int count = INC_MIN_LIMIT + r.nextInt(INC_RANGE);
    String seqId = "";

    for (int i = 0; i < count; i++) {
      seqId = IDGenerator.getSeqID(SEQ_TABLE_NAME);
    }
    return seqId;
  }

  /**
   * 新建大订单
   * 
   * @param gorder 大订单明细
   */
  public Integer insertGorder(Gorder gorder) {
    Integer ret = (Integer) getSqlSession().insert("Gorder.insertGorder", gorder);
    return ret;
  }

  /**
   * 修改大订单
   * 
   * @param condition where条件，不作修改
   * @param value 修改内容
   * @return 成功更新了多少条数据
   */
  public Integer updateGorder(Map condition, Gorder value) {
    Map mapParam = new HashMap();
    mapParam.put("input", condition);
    mapParam.put("value", value);

    Integer ret = getSqlSession().update("Gorder.updateGorder", mapParam);

    return ret;
  }

  /**
   * 更具GorderId修改BusAccountId updateBuyAccountIdByGorderId
   * 
   * @Title: updateBuyAccountIdByGorderId
   * @Description: TODO
   * @param condition
   * @param value
   * @return
   */
  public Integer updateBuyAccountIdByGorderId(Map condition, Gorder value) {
    Map mapParam = new HashMap();
    mapParam.put("input", condition);
    mapParam.put("value", value);

    Integer ret = getSqlSession().update("Gorder.updateBuyAccountIdByGorderId", mapParam);

    return ret;
  }

  /**
   * 查询单个订单明细
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return
   */
  public Gorder queryGorderDetail(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return (Gorder) getSqlSession().selectOne("Gorder.findEntityListByCond", mapParam);
  }

  /**
   * 查询单个订单明细
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return
   */
  public List<Gorder> queryGorderDetailList(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return getSqlSession().selectList("Gorder.findEntityListByCond", mapParam);
  }

  /**
   * 查询多个订单.
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return 订单列表
   */
  public List<Gorder> queryGorderList(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return getSqlSession().selectList("Gorder.findEntityListByCond", mapParam);
  }

}
