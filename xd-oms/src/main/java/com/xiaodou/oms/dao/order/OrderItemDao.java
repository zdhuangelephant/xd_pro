package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.util.IDGenerator;
import com.xiaodou.summer.dao.BaseDao;

/**
 * 订单条目Dao
 * 
 * @author bjfyzhao 2012-09-13 19:05
 * 
 */
public class OrderItemDao extends BaseDao<OrderItem> {
  /**
   * 
   * @param orderItem
   * @return 成功插入的数据总记录条数
   */
  public Integer insert(OrderItem orderItem) {
    if (null == orderItem) {
      return 0;
    }

    Integer ret = this.getSqlSession().insert("OrderItem.insertOrderItem", orderItem);
    return ret;
  }

  /**
   * 
   * @param codition
   * @param orderItem
   * @return 成功更新订单条目记录条数
   */
  public Integer update(Map condition, OrderItem orderItem) {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("condition", condition);
    paramMap.put("value", orderItem);
    Integer ret = this.getSqlSession().update("OrderItem.updateOrderItem", paramMap);

    return ret;
  }

  /**
   * 查找订单条目列表
   * 
   * @param condition
   * @return
   */
  public List<OrderItem> queryOrderItemList(Map condition) {
    Map paramMap = new HashMap();
    paramMap.put("condition", condition);
    return (List<OrderItem>) this.getSqlSession().selectList("OrderItem.queryEntityListByCond",
        paramMap);
  }


  /**
   * 根据id查找订单条目
   * 
   * @param id
   * @return
   */
  public OrderItem getById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }

    return (OrderItem) this.getSqlSession().selectOne("OrderItem.selectById", id);
  }

  /**
   * 根据大订单号查找用户一次性购买的所有商品列表
   * 
   * @param gorderId
   * @return
   */
  public List<OrderItem> queryItemListByGorderId(String gorderId) {
    return this.getSqlSession().selectList("OrderItem.queryItemByGorderId", gorderId);
  }

  /**
   * 查找订单条目表下一个可用序列id
   * 
   * @return
   */
  private Long selectNextSeqId() {
    return (Long) this.getSqlSession().selectOne("OrderItem.selectNextItemId");
  }

  public OrderItem queryOrderItemDetail(Map<String, Object> inputParam,
      Map<String, Object> outputParam) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("condition", inputParam);
    mapParam.put("output", outputParam);

    return (OrderItem) this.getSqlSession().selectOne("OrderItem.queryEntityListByCond", mapParam);
  }

  public List<OrderItem> queryOrderItemList(Map condition, Map output) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("condition", condition);
    mapParam.put("output", output);

    return (List<OrderItem>) this.getSqlSession().selectList("OrderItem.queryEntityListByCond",
        mapParam);
  }

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

}
