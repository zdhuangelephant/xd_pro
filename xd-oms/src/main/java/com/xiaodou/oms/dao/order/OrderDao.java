package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.xiaodou.oms.entity.order.Abatement;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.StatusLog;
import com.xiaodou.oms.service.order.OrderAlerter;
import com.xiaodou.oms.util.IDGenerator;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.summer.dao.BaseDao;

/**
 * 订单的数据库操作。 以后移到 业务-DAO转换层。
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderDao extends BaseDao<Order> {

  /**
   * 新增分页查询接口，供内部调用，对外屏蔽分页实现
   * 
   * @param parameterMap
   * @param page
   * @return
   */

  // public Page<Order> getPagedOrderList(String sqlId,Map parameterMap, Page<Order> page) {
  // // TODO Auto-generated method stub
  // int pageSize = page.getPageSize();
  // List<Order> resultList = Collections.EMPTY_LIST;
  // int totalCount, totalPage;
  // // 当pageSize为0时，不用分页查询
  // if (pageSize == 0) {
  // resultList = this.getSqlSession().selectList(sqlId, parameterMap); //"Gorder.queryGorder"
  // totalCount = resultList.size();
  // totalPage = 1;
  // } else {
  // // 分页查询
  // int skipResults = (page.getPageNo() - 1) * pageSize;
  // if (page.getPageNo() < 1) {
  // skipResults = 0;
  // }
  // int maxResults = page.getPageSize();
  // resultList = this.getSqlSession().selectList(sqlId, parameterMap, new RowBounds(skipResults,
  // maxResults));
  // totalCount = (Integer) getSqlSession().selectOne(sqlId+"Count", parameterMap);
  // totalPage = totalCount / page.getPageSize() + 1;
  // }
  // page.setResult(resultList);
  // page.setTotalCount(totalCount);
  // page.setTotalPage(totalPage);
  // return page;
  // }
  /**
   * 分页查询
   * 
   * @param sqlId 待查询的sqlId
   * @param param 查询参数
   * @param pagination 是否分页
   * @return
   * @throws DataAccessException
   */
  // public Map queryPagnatedList(String sqlId, Map param, int pagination ) {
  // List result = null;//查询结果
  //
  // /* 1.如果不分页... */
  // if(0 == pagination){
  // result = this.getSqlSession().selectList(
  // sqlId, param);
  // logger.info(String.valueOf(result.size()));
  // Map rsMap = new HashMap();
  // rsMap.put("pageSize", 0);//不分页
  // rsMap.put("result", result);
  // return rsMap;
  // }
  //
  // /* 2.如果分页 */
  // int PAGE_SIZE = 20; //每页多少条记录
  // int pageNumber = 0; //查询第几页
  // int skipRows = 0; //跳过多少行
  // int pageCount = 0; //一共多少页
  // int lastRow = 0; //本次要显示的最后一行
  //
  // Object pageSizeOri = param.get("pageSize");
  // if(null != pageSizeOri){
  // PAGE_SIZE = Integer.parseInt((String)pageSizeOri);
  // }
  //
  // Object pageNumberStr = param.get("pageNumber");//String类型的pageNumber
  // if(null == pageNumberStr){pageNumberStr = "1";}
  // pageNumber = Integer.parseInt( String.valueOf(pageNumberStr) );
  // if (pageNumber < 1)
  // pageNumber = 1;
  //
  // skipRows = (pageNumber - 1) * PAGE_SIZE;
  //
  // int count = (Integer)this.getSqlSession().selectOne(
  // sqlId + "Count", param);
  //
  // // logger.debug(String.valueOf(count));
  // if (count % PAGE_SIZE == 0)
  // pageCount = count / PAGE_SIZE;
  // else
  // pageCount = count / PAGE_SIZE + 1;
  //
  // /* 计算制定页的开始位置 */
  // if( skipRows >= count ){
  // skipRows = count / PAGE_SIZE * PAGE_SIZE;
  // }else if(skipRows < 0){
  // skipRows = 0;
  // }
  //
  // /* 计算指定页的结束位置 */
  // lastRow = skipRows + PAGE_SIZE;
  // if( lastRow >= count ){
  // lastRow = count;
  // pageNumber = pageCount;
  // }
  // Page pageObject = new Page();
  // pageObject.setPageSize(PAGE_SIZE);
  // pageObject.setPageNo(pageNumber);
  //
  // Page<Order> pageResult = this.getPagedOrderList(sqlId,param,
  // pageObject);//getSqlSession().selectList(sqlId, param, skipRows, PAGE_SIZE );
  // result = pageResult.getResult();
  // // result = this.getSqlMapClientTemplate().queryForList(
  // // sqlId, param, skipRows, PAGE_SIZE );
  //
  // param.put("pageNumber", pageNumber);
  // param.put("pageCount", pageCount);
  // param.put("count", count);
  // param.put("result", result);
  //
  // return param;
  // }
  /**
   * 新建订单
   * 
   * @param order 订单明细
   */
  public Integer insertOrder(Order order) {
    Integer ret = 0;
    try {
      ret = (Integer) getSqlSession().insert("Order.insertOrder", order);
    } catch (Exception e) {
      OrderAlerter.alert("[插入订单数据异常，订单ID:" + order.getId() + "]");
      OrderLoggerUtil.error("插入订单数据产生异常", e);
      throw new RuntimeException(e);
    }

    // trigger，注释掉trigger机制
    // trigger.trigger(order.getId());

    return ret;
  }

  /**
   * 修改订单
   * 
   * @param condition where条件，不作修改
   * @param value 修改内容
   * @return 成功更新了多少条数据
   */
  public Integer updateOrder(Map input, Order order) {
    Map mapParam = new HashMap();
    mapParam.put("input", input);
    mapParam.put("value", order);
    Integer ret = 0;
    try {
      ret = getSqlSession().update("Order.updateOrder", mapParam);
    } catch (Exception e) {
      OrderAlerter.alert("[插入订单数据异常，订单ID:" + order.getId() + "]");
      OrderLoggerUtil.error("插入订单数据产生异常", e);
      throw new RuntimeException(e);
    }

    return ret;
  }

  /**
   * 修改订单
   * 
   * @param condition where条件，不作修改
   * @param value 修改内容
   * @return 成功更新了多少条数据
   */
  public Integer updateBuyAccountIdByGorderId(Map input, Order order) {
    Map mapParam = new HashMap();
    mapParam.put("input", input);
    mapParam.put("value", order);
    Integer ret = 0;
    try {
      ret = getSqlSession().update("Order.updateBuyAccountIdByGorderId", mapParam);
    } catch (Exception e) {
      OrderAlerter.alert("[更新客人卡号数据异常，订单ID:" + order.getId() + "]");
      OrderLoggerUtil.error("更新客人卡号数据异常", e);
      throw new RuntimeException(e);
    }

    return ret;
  }

  /**
   * 查询单个订单明细
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return
   */
  public Order queryOrderDetail(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    Order order = null;
    try {
      order = (Order) getSqlSession().selectOne("Order.findEntityListByCond", mapParam);
    } catch (Exception e) {
      OrderLoggerUtil.error("查询订单异常", e);
    }
    return order;
  }

  /**
   * 查询单个订单明细
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return
   */
  public List<Order> queryOrderDetailList(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return getSqlSession().selectList("Order.findEntityListByCond", mapParam);
  }

  /**
   * 查询多个订单.
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return 订单列表
   */
  public List<Order> queryOrderList(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return getSqlSession().selectList("Order.findEntityListByCond", mapParam);
  }

  /**
   * 查询多个订单ID.
   * 
   * @param inputArgument 查询条件
   * @return 订单ID列表
   */
  public List<String> queryOrderIdList(Map inputArgument) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);

    return getSqlSession().selectList("Order.findIdListByCond", mapParam);
  }

  /**
   * 查询多个订单.分页
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @return 订单列表
   */
  // public Map queryPageOrderList(Map inputArgument, Map outputField, int pageNumber){
  // Map mapParam = new HashMap<String,Map>();
  // mapParam.put("input",inputArgument);
  // mapParam.put("output",outputField);
  // mapParam.put("pageNumber",pageNumber);
  //
  // return this.queryPagnatedList("Order.findEntityListByCond", mapParam, 1);
  // }

  /**
   * 查询订单统计
   * 
   * @param inputArgument 查询条件
   * @param outputField 输出字段
   * @param groupBy 分组方法
   * @return 订单列表
   */
  public List<Order> queryOrderStatistics(Map inputArgument, Map outputField, Map groupBy) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);
    mapParam.put("groupBy", groupBy);

    return getSqlSession().selectList("Order.queryOrderStatistics", mapParam);

    // queryForList("Order.queryOrderStatistics", mapParam);
  }


  /**
   * 查询订单数量
   * 
   * @param input
   * @param output
   * @return 订单数量
   */
  // public Integer queryOrderCount(Map inputArgument, Map outputField) {
  // Map<String,Map> mapParam = new HashMap<String,Map>();
  // mapParam.put("input",inputArgument);
  // mapParam.put("output",outputField);
  //
  // return (Integer)getSqlSession().selectOne("Order.queryOrderCount", mapParam);
  // }



  /***
   * 查询订单状态变化日志
   * 
   * @param inputArgument
   * @param outputField
   * @return 订单变化日志
   */

  public List<StatusLog> queryStatusLog(Map inputArgument, Map outputField) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    mapParam.put("output", outputField);

    return getSqlSession().selectList("Order.queryStatusLog", mapParam);
  }

  /**
   * 新建订单状态变化日志
   */
  public Integer insertStatusLog(StatusLog statusLog) {
    Integer ret = 0;
    try {
      ret = getSqlSession().insert("Order.insertStatusLog", statusLog);
    } catch (Exception e) {
      // OrderAlerter.alert("[订单日志更新异常！]");
      OrderLoggerUtil.orderInfo("订单日志更新异常：" + e.getStackTrace());
    }
    return ret;
  }

  private List<Abatement> queryAbatementList(Map abatementCondition, Map abatementOutput) {
    Map<String, Map> mapParam = new HashMap<String, Map>();
    mapParam.put("input", abatementCondition);
    mapParam.put("output", abatementOutput);

    return getSqlSession().selectList("Order.queryAbatement", mapParam);
  }

  public List<Order> queryLatestFailOrderByMerchantProductId(String merchantPrctId) {
    return getSqlSession().selectList("Order.queryLatestFailOrderByMerchantProductId",
        merchantPrctId);
  }

  /**
   * 新建费用减免
   * 
   * @param
   */
  public Integer insertAbatement(Abatement abate) {
    Integer ret = (Integer) getSqlSession().insert("Order.insertAbatement", abate);

    return ret;
  }

  /**
   * 查找物流公司ID
   */
  public String queryLogisticsId(String logisticsName) {
    return (String) getSqlSession().selectOne("Order.queryLogisticsId", logisticsName);
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
