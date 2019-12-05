package com.xiaodou.oms.dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.util.model.QueryPaymentModel;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * CascadeQueryDao
  * @Title: CascadeQueryDao
  * @Desription 级联查询dao
  * @author yang.zhao
  * @date 2015年1月5日 上午11:25:31
 */
@Repository("cascadeQueryDao")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CascadeQueryDao extends BaseDao {

  /**
   * queryPagedOrderItemList
    * @Title: queryPagedOrderItemList
    * @Description: 三级联查
    * @param parameterMap
    * @param outputMap
    * @param page
    * @return
   */
  public Page<OrderItem> queryPagedOrderItemList(Map parameterMap, Map outputMap,
      Page<OrderItem> page) {

    Map map = new HashMap();
    map.put("output", outputMap);
    map.put("condition", parameterMap);
    if (setCascadePage(parameterMap, map, page, "CascadeQuery.queryGorderIdItemListByCond")) {
      page.setResult(this.selectPaginationList("CascadeQuery.queryOrderItemListByCond", map, null)
          .getResult());
    }
    return page;
  }

  /*
   * public Page<OrderItem> queryPagedOrderItemList(Map parameterMap, Map outputMap, Page<OrderItem>
   * page) {
   * 
   * Map map = new HashMap(); map.put("output", outputMap); map.put("condition", parameterMap);
   * OrderLoggerUtil.cacheInfo(Thread.currentThread().getId() +
   * "----------------queryPagedOrderItemList-start-----------"); page =
   * this.selectPaginationList("CascadeQuery.queryOrderItemListByCondLimit", map, null);
   * OrderLoggerUtil.cacheInfo(Thread.currentThread().getId() +
   * "----------------queryPagedOrderItemList-end-----------"); return page; }
   */

  /**
   * queryOrderItemDetail
    * @Title: queryOrderItemDetail
    * @Description: 查询orderitem详情
    * @param orderItemId，根据orderitemid
    * @param output
    * @return
   */
  public OrderItem queryOrderItemDetail(String orderItemId, Map output) {

    Map<String, Object> condition = new HashMap<String, Object>();

    Map<String, Object> paramMap = new HashMap<String, Object>();
    Map orderItem = new HashMap();
    orderItem.put("id", orderItemId);
    paramMap.put("orderItem", orderItem);

    condition.put("output", output);
    condition.put("condition", paramMap);

    return (OrderItem) this.getSqlSession().selectOne("CascadeQuery.queryOrderItemListByCond",
        condition);
  }

  /**
   * queryOrderItemDetail
    * @Title: queryOrderItemDetail
    * @Description: 查询orderitem详情
    * @param input
    * @param output
    * @return
   */
  public OrderItem queryOrderItemDetail(Map input, Map output) {

    Map<String, Object> condition = new HashMap<String, Object>();

    condition.put("output", output);
    condition.put("condition", input);

    return (OrderItem) this.getSqlSession().selectOne("CascadeQuery.queryOrderItemListByCond",
        condition);
  }
  
  /**
   * queryGorderOrderDetail
    * @Title: queryGorderOrderDetail
    * @Description: gorder-order 详情
    * @param input
    * @param output
    * @return
   */
  public Order queryGorderOrderDetail(Map input, Map output) {

    Map<String, Object> condition = new HashMap<String, Object>();

    condition.put("output", output);
    condition.put("condition", input);

    return (Order) this.getSqlSession().selectOne("CascadeQuery.queryOrderListByCond", condition);
  }

  /**
   * queryPagedOrderList
    * @Title: queryPagedOrderList
    * @Description: gorder - order 列表
    * @param parameterMap
    * @param outputMap
    * @param page
    * @return
   */
  public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap, Page<Order> page) {

    Map map = new HashMap();
    map.put("output", outputMap);
    map.put("condition", parameterMap);
    if (setCascadePage(parameterMap, map, page, "CascadeQuery.queryGorderIdListByCond")) {
      page.setResult(this.selectPaginationList("CascadeQuery.queryOrderListByCond", map, null)
          .getResult());
    }
    return page;
  }
  
  /**
   * queryPagedOrderOrderItemList
    * @Title: queryPagedOrderOrderItemList
    * @Description: 查询order - orderitem 列表
    * @param queryParam
    * @param outputMap
    * @param page
    * @return
   */
  public Page<OrderItem> queryPagedOrderOrderItemList(Map<String, Object> queryParam,
	    Map<String, Object> outputMap, Page<OrderItem> page){
      Map map = new HashMap();
      map.put("output", outputMap);
      map.put("condition", queryParam);
      if (setCascadeOrderPage(queryParam, map, page, "CascadeQuery.queryOrderIdListByCond")) {
        page.setResult(this.selectPaginationList("CascadeQuery.queryOrderOrderItemListByCond", map, null)
            .getResult());
      }
      return page;
  }

  /**
   * queryGorderPayRecordById
    * @Title: queryGorderPayRecordById
    * @Description: gorder - payrecord 查询
    * @param parameterMap
    * @param outputMap
    * @return
   */
  public QueryPaymentModel queryGorderPayRecordById(Map parameterMap,Map outputMap){
    Map map = new HashedMap();
    map.put("input",parameterMap);
    map.put("output",outputMap);
    return (QueryPaymentModel)this.getSqlSession().selectOne("CascadeQuery.queryGorderJoinPayRecordById",map);
  }

  /*
   * public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap, Page<Order> page) {
   * 
   * Map map = new HashMap(); map.put("output", outputMap); map.put("condition", parameterMap);
   * OrderLoggerUtil.cacheInfo(Thread.currentThread().getId() +
   * "----------------queryPagedOrderItemList-start-----------"); page =
   * this.selectPaginationList("CascadeQuery.queryOrderListByCondLimit", map, page);
   * OrderLoggerUtil.cacheInfo(Thread.currentThread().getId() +
   * "----------------queryPagedOrderItemList-end-----------"); return page; }
   */

  /**
   * setCascadePage
    * @Title: setCascadePage
    * @Description: 设置gorder查询分页列表
    * @param parameterMap
    * @param map
    * @param page
    * @param sql
    * @return
   */
  private boolean setCascadePage(Map parameterMap, Map map, Page<?> page, String sql) {
    Page<String> gorder = new Page<String>();
    gorder.setPageNo(page.getPageNo());
    gorder.setPageSize(page.getPageSize());
    Page gorderIdListPage = this.selectPaginationList(sql, map, gorder);
    List<String> gorderIdList = gorderIdListPage.getResult();
    page.setPageNo(gorderIdListPage.getPageNo());
    page.setPageSize(gorderIdListPage.getPageSize());
    page.setTotalPage(gorderIdListPage.getTotalPage());
    page.setTotalCount(gorderIdListPage.getTotalCount());
    if (null != gorderIdList && gorderIdList.size() > 0) {
      Map<String, Object> gorderParam = Maps.newHashMap();
      if (null != parameterMap.get("gorder")) {
        gorderParam = (Map<String, Object>) parameterMap.get("gorder");
      }
      gorderParam.put("listId", gorderIdList);
      parameterMap.put("gorder", gorderParam);
      return true;
    }
    return false;
  }
  
  /**
   * setCascadePage
    * @Title: setCascadePage
    * @Description: 设置gorder查询分页列表
    * @param parameterMap
    * @param map
    * @param page
    * @param sql
    * @return
   */
  private boolean setCascadeOrderPage(Map parameterMap, Map map, Page<?> page, String sql) {
    Page<String> order = new Page<String>();
    order.setPageNo(page.getPageNo());
    order.setPageSize(page.getPageSize());
    Page orderIdListPage = this.selectPaginationList(sql, map, order);
    List<String> orderIdList = orderIdListPage.getResult();
    page.setPageNo(orderIdListPage.getPageNo());
    page.setPageSize(orderIdListPage.getPageSize());
    page.setTotalPage(orderIdListPage.getTotalPage());
    page.setTotalCount(orderIdListPage.getTotalCount());
    if (null != orderIdList && orderIdList.size() > 0) {
      Map<String, Object> orderParam = Maps.newHashMap();
      if (null != parameterMap.get("order")) {
	  orderParam = (Map<String, Object>) parameterMap.get("order");
      }
      orderParam.put("listId", orderIdList);
      parameterMap.put("order", orderParam);
      return true;
    }
    return false;
  }


}
