package com.xiaodou.oms.service.orderServiceFacadeTest;
/*package com.elong.oms.service.orderServiceFacadeTest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junitx.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elong.common.util.warp.FastJsonUtil;
import com.elong.oms.dao.order.GorderDao;
import com.elong.oms.dao.order.OrderDao;
import com.elong.oms.dao.order.PayRequestDao;
import com.elong.oms.dao.page.Page;
import com.elong.oms.entity.order.Gorder;
import com.elong.oms.entity.order.Order;
import com.elong.oms.entity.order.OrderItem;
import com.elong.oms.entity.order.PayRecord;
import com.elong.oms.entity.order.PayRequest;
import com.elong.oms.service.facade.OrderServiceFacade;
import com.elong.oms.service.order.GorderService;
import com.elong.oms.service.order.OrderItemService;
import com.elong.oms.service.order.PayService;
import com.elong.oms.service.order.QueryService;
import com.elong.oms.service.order.UpdateService;
import com.elong.oms.vo.input.pay.QueryFirstPayInfoPojo;
import com.elong.oms.vo.input.pay.QueryRecordPojo;
import com.elong.oms.vo.result.ResultType;
import com.elong.oms.vo.result.pay.FirstPayInfoVO;
import com.elong.oms.vo.result.pay.PayRecordListVO;

//import static org.easymock.EasyMock.createMock;
//import static org.easymock.classextension.EasyMock;


*//**
 * Created by wwk on 2014/8/7.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/conf/core/elong-oms-servlet.xml"})
public class OrderServiceFacadeImpTest extends EasyMockSupport {


  @Resource
  OrderServiceFacade orderServiceFacadeImpl;

  OrderServiceFacade mockOrderServiceFacade = createMock(OrderServiceFacade.class);

  @Resource
  OrderDao orderDao;
  @Resource
  GorderDao gorderDao;

  //OrderServiceFacadeImpl mockOrderServiceFacadeImp = org.easymock.classextension.EasyMock.createMock(OrderServiceFacadeImpl.class);

  *//**
   * Test for void method ---- different from non-void method
   * public void updateOrder(Order order, String remark, String remoteIp) throws ValidationException, OrderException
   *//*
//  @Test
  public void updateOrderSuccessTest() {

    String orderId = "20140813000000004";
    String expect;
    String orgRemark;
    String remark = "";
    String orderIp;
    Order order;

    Map map = new HashMap();
    map.put("id", "");
    map.put("orderIp", "");
    map.put("remark", "");

    order = orderServiceFacadeImpl.queryOrderDetail(orderId, map);
    orgRemark = order.getRemark();
    orderIp = order.getOrderIp();
    order.setOrderIp("127.0.0.1");
    expect = orgRemark + "||" + remark;

    //mock
    UpdateService updateService = EasyMock.createMock(UpdateService.class);
    updateService.updateOrder(order, "", orderIp);
    EasyMock.replay(updateService);

    orderServiceFacadeImpl.updateOrder(order, remark, orderIp);

    order = orderServiceFacadeImpl.queryOrderDetail(orderId, map);

    assertEquals(expect, order.getRemark());

  }

  @Test
  public void createGorderSuccessTest() {

  }

  *//**
   * Test for void method ---- different from non-void method
   * public void updateOrderNote(String orderId, String note) throws ValidationException, OrderException
   * <p/>
   * note:passed
   *//*
  @Test
  public void updateOrderNoteSuccessTest() {

    String orderId = "20140813000000004";
    String note = "updateTest";

    orderServiceFacadeImpl.updateOrderNote(orderId, note);
    Map map = new HashMap();
    map.put("note", "");
    Order order = orderServiceFacadeImpl.queryOrderDetail(orderId, map);

    String expectedNote = order.getNote() + "||updateTest";
    UpdateService updateServiceMock = org.easymock.EasyMock.createMock(UpdateService.class);

    updateServiceMock.updateOrderNote(orderId, note);
    org.easymock.EasyMock.replay(updateServiceMock);

    orderServiceFacadeImpl.updateOrderNote(orderId, note);

    order = orderServiceFacadeImpl.queryOrderDetail(orderId, map);
    assertEquals(order.getNote(), expectedNote);

  }

  @Test
  public void updateOrderFailTest() {

  }

  *//**
   * Test for void method
   * public Order queryOrderDetail(String id)
   * public Order queryOrderDetail(String id,Map output)
   * public Order queryOrderDetail(Map input,Map output)
   *//*
  //pass  该函数未被调用
  @Test
  public void queryOrderDetailTest1() {

    String orderId = "20140813000000004";
    String gorderId = "20004";

    Order order;
    //expect
    Order expOrder = new Order();
    expOrder.setId(orderId);
    expOrder.setGorderId(gorderId);
    //mock
    QueryService queryService = EasyMock.createMock(QueryService.class);
    queryService.queryOrderDetail(orderId);
    expectLastCall().andReturn(expOrder);
    EasyMock.replay(queryService);

    order = orderServiceFacadeImpl.queryOrderDetail(orderId);
    Assert.assertNotNull(order);
    assertEquals(order.getGorderId(), gorderId);
  }

//  @Test
  public void queryOrderDetailSuccessTest2() {

    String orderId = "20140813000000004";
    String gorderId = "20004";

    Order order;
    Map map = new HashMap();
    map.put("gorderId", "");
    //expect
    Order expOrder = new Order();
    expOrder.setId(orderId);
    expOrder.setGorderId(gorderId);
    //mock
    QueryService queryService = EasyMock.createMock(QueryService.class);
    queryService.queryOrderDetail(orderId, map);
    expectLastCall().andReturn(expOrder);
    EasyMock.replay(queryService);

    order = orderServiceFacadeImpl.queryOrderDetail(orderId, map);
    assertEquals(order.getGorderId(), gorderId);

  }

  //nested exception is org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 4
//  @Test
  public void queryOrderDetailTest3() {

    String orderId = "20140813000000004";
    String gorderId = "20004";
    String productType = "0501";
    Order order;
    Map input = new HashMap();
    input.put("orderId", orderId);
    input.put("gorderId", gorderId);

    Map output = new HashMap();
    output.put("productType", "");

    //expect
    Order expOrder = new Order();
    //expOrder.setId(orderId);
    //expOrder.setGorderId(gorderId);
    expOrder.setProductType(productType);
    //mock
    QueryService queryService = EasyMock.createMock(QueryService.class);
    queryService.queryOrderDetail(input, output);
    expectLastCall().andReturn(expOrder);
    EasyMock.replay(queryService);

    order = orderServiceFacadeImpl.queryOrderDetail(input, output);
    assertEquals(order.getProductType(), productType);
  }

    
    *   Test for
    *   public OrderItem queryCascadeOrderDetail(String orderItemId, Map output) throws ValidationException, OrderException
    *
    * 
  //note:fail
    @Test
    public void queryCascadeOrderDetailSuccessTest1(){

        String orderItemId = "20140813000000001";
        OrderItem orderItem;
        Map output = new HashMap();
        output.put("gorderId","");
        output.put("orderId","");

        OrderItem expect = new OrderItem();
        expect.setOrderId("20140813000000001");
        expect.setGorderId("20001");

        //mock
        CascadeQueryService cascadeQueryService = EasyMock.createMock(CascadeQueryService.class);
        cascadeQueryService.queryOrderItemDetail(orderItemId,output);
        expectLastCall().andReturn(expect);
        EasyMock.replay(cascadeQueryService);

        orderItem = orderServiceFacadeImpl.queryCascadeOrderDetail(orderItemId,output);

        assertNotNull(orderItem);
        assertEquals("20140813000000001",orderItem.getOrderId());
    }
    

    //note: fail
    @Test
    public void queryCascadeOrderDetailTest2(){

        String orderItemId = "20140729210567889";
        Map output = new HashMap();
        output.put("gorderId","");
        output.put("orderId","");

        Map input = new HashMap();
        input.put("orderItemId",orderItemId);

        OrderItem expect = new OrderItem();
        expect.setOrderId("20140729210567888");
        expect.setGorderId("450217");

        CascadeQueryService cascadeQueryService = EasyMock.createMock(CascadeQueryService.class);
        cascadeQueryService.queryOrderItemDetail(input,output);
        expectLastCall().andReturn(expect);

        OrderItem item= orderServiceFacadeImpl.queryCascadeOrderDetail(input,output);
        assertNotNull(item);

    }


  *//**
   * Test for
   * public Gorder gorderPayCallback(Integer type, Gorder gorder, String remoteIp) throws ValidationException, OrderException
   *//*
  //做完测试需要将数据恢复原状，否则下次不可测试，特别是note部分，很容易就超过界限。
  @Test
  public void gorderPayCallbackSuccessTest() {

    int payStatus = 1;
    Integer expStatus = 1;

    String gorderId = "20004";
    String remoteIp;

    Gorder gorder = orderServiceFacadeImpl.queryGorderDetailById(gorderId);
    remoteIp = gorder.getGorderIp();

    Map recoverMap = new HashMap();
    recoverMap.put("payStatus", gorder.getGorderStatus());
    recoverMap.put("note", gorder.getNote());
    recoverMap.put("remoteIp", gorder.getGorderIp());

    Gorder expGorder = new Gorder();
    expGorder.setId(gorderId);
    expGorder.setGorderStatus(1);

    GorderService gorderService = EasyMock.createMock(GorderService.class);
    gorderService.gorderPayCallback(payStatus, gorder, remoteIp);
    expectLastCall().andReturn(expGorder);
    EasyMock.replay(gorderService);

    try {
      gorder = orderServiceFacadeImpl.gorderPayCallback(payStatus, gorder, remoteIp);

    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals(expStatus, gorder.getGorderStatus());
  }

  //pass
  @Test
  public void gorderPayCallbackFailTest() {
    int payStatus = -1;
    Integer expStatus = -1;
    String gorderId = "20004";
    String remoteIp;

    Integer originStatus;
    String originRemark;

    Gorder gorder = orderServiceFacadeImpl.queryGorderDetailById(gorderId);
    remoteIp = gorder.getGorderIp();
    originStatus = gorder.getGorderStatus();

    Gorder expGorder = new Gorder();
    expGorder.setId(gorderId);
    expGorder.setGorderStatus(0);

    GorderService gorderService = EasyMock.createMock(GorderService.class);
    gorderService.gorderPayCallback(payStatus, gorder, remoteIp);
    expectLastCall().andReturn(expGorder);
    EasyMock.replay(gorderService);

    try {
      gorder = orderServiceFacadeImpl.gorderPayCallback(payStatus, gorder, remoteIp);

    } catch (Exception e) {
      e.printStackTrace();
    }

    assertEquals(expStatus, gorder.getGorderStatus());

    //Map map = new HashMap();
    //map.put("gorderStatus",originStatus);
    //gorderDao.recoverGorder(gorder, map);

  }

  *//**
   * public void addPayRequest(PayRequest payRequest) throws ValidationException, OrderException { payService.addPayRequest(payRequest);}
   *//*
    @Test

    public void addPayRequestTest(){

        PayRequest payRequest = new PayRequest();
        String payNo = "-100000000000042182";
        payRequest.setPayNo(payNo);
        payRequest.setGorderId("451541");
        payRequest.setBuyAccountId("tester");

        PayService payServiceMock = EasyMock.createMock(PayService.class);
        payServiceMock.addPayRequest(payRequest);
        replay(payServiceMock);

        orderServiceFacadeImpl.addPayRequest(payRequest);

        Map input = new HashMap();
        Map output = new HashMap();
        input.put("payNo",payNo);

        output.put("payNo","");
        Page<PayRequest> page = new Page<PayRequest>();
        try {
            Page<PayRequest> payRequestPage = orderServiceFacadeImpl.queryPagedPayRequestList(input, output, page);
            assertEquals(payNo, payRequestPage.getResult().get(0).getPayNo());

        }catch(Exception e){

        }

    }

  //
  @Test
  public void addPayRequestTest() {

    String str =
        "{\"gorderId\":\"20005\",\"orderId\":\"20140813000000005\",\"buyAccountId\":\"test\",\"operType\":\"1\",\"payType\":\"1\",\"payStatus\":\"1\",\"amount\":\"100\",\"payNo\":\"-100000000000000005\",\"createTime\":\"2014-08-13 20:49:30\",\"sentTime\":\"2014-08-13 20:49:32\",\"payTime\":\"2014-08-13 20:49:34\",\"productType\":\"0501\",\"clientType\":\"ios\",\"outerOrigin\":\"yilong_app\",\"callBackUrl\":\"refund_notify.do\"}";
    PayRequest expPayRequest = FastJsonUtil.fromJson(str, PayRequest.class);

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.addPayRequest(expPayRequest);
    replay(payServiceMock);

    Map input = new HashMap();
    input.put("payNo", expPayRequest.getPayNo());
    Map output = new HashMap();
    output.put("gorderId", "");
    output.put("orderId", "");
    orderServiceFacadeImpl.addPayRequest(expPayRequest);
    Page<PayRequest> ret = orderServiceFacadeImpl.queryPagedPayRequestList(input, output, null);

    assertEquals(expPayRequest.getGorderId(), ret.getResult().get(0).getGorderId());
    assertEquals(expPayRequest.getOrderId(), ret.getResult().get(0).getOrderId());


  }

    //前提是保证payNo只有一条数据记录，或者多条，但是对应的gorderId都是一样的
    @Test
    public void queryPagedPayRequestListTest(){

        String payNo = "-100000000000042182";
        String gorderId = "451541";
        Map input = new HashMap();
        input.put("payNo",payNo);

        Map output = new HashMap();
        output.put("GorderId","");

        Page<PayRequest> page = new Page<PayRequest>();
        Page<PayRequest> expPage = new Page<PayRequest>();
        expPage.setPageNo(1);
        PayRequest payRequest = new PayRequest();
        payRequest.setPayNo(payNo);
        payRequest.setGorderId(gorderId);
        List<PayRequest> list = new ArrayList<PayRequest>();
        list.add(payRequest);
        expPage.setResult(list);

        PayService payServiceMock = EasyMock.createMock(PayService.class);
        payServiceMock.queryPayRequestList(input,output,page);
        expectLastCall().andReturn(expPage);
        replay(payServiceMock);

        try{
            Page<PayRequest> payRequestPage = orderServiceFacadeImpl.queryPagedPayRequestList(input,output,page);
            assertEquals(gorderId,payRequestPage.getResult().get(0).getGorderId());

        }catch(Exception e){

        }
    }


    @Test
    public void queryPagedPayRequestListExceptionTest(){

        String payNo = "-100000000000042182";
        String gorderId = "451541";

        Map input = new HashMap();
        Map output = new HashMap();

        Page<PayRequest> page = new Page<PayRequest>();
        //Page<PayRequest> expPage = new Page<PayRequest>();
        //expPage.setPageNo(1);
        //PayRequest payRequest = new PayRequest();
        //payRequest.setPayNo(payNo);
        //payRequest.setGorderId(gorderId);
        //List<PayRequest> list = new ArrayList<PayRequest>();
        //list.add(payRequest);
        //expPage.setResult(list);

        PayService payServiceMock = EasyMock.createMock(PayService.class);
        payServiceMock.queryPayRequestList(input,output,page);
        expectLastCall().andThrow(new RuntimeException()).times(1);
        replay(payServiceMock);

        try{
            Page<PayRequest> payRequestPage = orderServiceFacadeImpl.queryPagedPayRequestList(input,output,page);
            //assertEquals(gorderId,payRequestPage.getResult().get(0).getGorderId());
            EasyMock.verify(payServiceMock);
        }catch(Exception e){

        }
    }


  @Test
  public void queryPagedPayRecordListTest() {
    String payNo = "-100000000000000001";
    PayRecord expPayRecord = new PayRecord();

    expPayRecord.setPayNo(payNo);
    expPayRecord.setGorderId("20001");
    Page<PayRecord> expPage = new Page<PayRecord>();
    Map input = new HashMap();
    Map output = new HashMap();
    input.put("payNo", payNo);
    output.put("payNo", "");
    output.put("gorderId", "");

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.queryPayRecordList(input, output, expPage);
    expectLastCall().andReturn(expPage);
    replay(payServiceMock);


    Page<PayRecord> page = new Page<PayRecord>();
    try {
      page = orderServiceFacadeImpl.queryPagedPayRecordList(input, output, page);
      assertEquals(expPayRecord.getGorderId(), page.getResult().get(0).getGorderId());

    } catch (Exception e) {

    }
  }


  
      @Test
      public void getTokenTest(){

          GetTokenPojo pojo = new GetTokenPojo();
          pojo.setProductLine("0501");

          GetTokenVO getTokenVo = new GetTokenVO(ResultType.SUCCESS);
          PayService payServiceMock = EasyMock.createMock(PayService.class);
          payServiceMock.getToken(pojo);
          expectLastCall().andReturn(getTokenVo);
          EasyMock.replay(payServiceMock);

          GetTokenVO ret = orderServiceFacadeImpl.getToken(pojo);
          assertEquals(ret.getToken(),getTokenVo.getToken());

      }
  
  //pass
//  @Test
  public void queryPayRecordByPayNoTest() {

    String payNo = "-100000000000000001";
    PayRecord expPayRecord = new PayRecord();
    expPayRecord.setGorderId("20001");
    expPayRecord.setOrderId("20140813000000001");
    expPayRecord.setProductType("0501");

    //mock
    PayService payServiceMock = EasyMock.createMock(PayService.class);
    Map input = new HashMap();
    input.put("payNo", payNo);

    Map output = new HashMap();
    output.put("gorderId", "1");
    output.put("orderId", "1");
    output.put("productType", "1");

    Page<PayRecord> page = payServiceMock.queryPayRecordList(input, output, null);
    Page<PayRecord> exppage = new Page<PayRecord>();
    List list = new ArrayList<PayRecord>();
    list.add(expPayRecord);
    exppage.setResult(list);
    expectLastCall().andReturn(exppage);
    EasyMock.replay(payServiceMock);

    //actual
    PayRecord payRecord = orderServiceFacadeImpl.queryPayRecordByPayNo(payNo);

    //assert
    assertEquals(expPayRecord.getGorderId(), payRecord.getGorderId());
    assertEquals(expPayRecord.getOrderId(), payRecord.getOrderId());
    assertEquals(expPayRecord.getProductType(), payRecord.getProductType());

  }

  //pass
  @Test
  public void queryPayRecordListTest() {

    QueryRecordPojo queryRecordPojo = new QueryRecordPojo();
    queryRecordPojo.setGorderId("20001");
    queryRecordPojo.setSign("MD5");
    queryRecordPojo.setProductLine("0501");
    queryRecordPojo.setJsonStr("");

    PayRecordListVO expPayRecordListVO = new PayRecordListVO(ResultType.SUCCESS);

    List list = new ArrayList<PayRecord>();
    PayRecord expPayRecord = new PayRecord();
    expPayRecord.setGorderId("20001");
    expPayRecord.setOrderId("20140813000000001");
    expPayRecord.setId(1001);
    list.add(expPayRecord);
    expPayRecordListVO.setList(list);

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.queryPayRecordList(queryRecordPojo);
    expectLastCall().andReturn(expPayRecordListVO);
    EasyMock.replay(payServiceMock);

    PayRecordListVO payRecordListVO = orderServiceFacadeImpl.queryPayRecordList(queryRecordPojo);

    assertEquals(expPayRecordListVO.getList().get(0).getId(), payRecordListVO.getList().get(0).getId());
    assertEquals(expPayRecordListVO.getList().get(0).getGorderId(), payRecordListVO.getList().get(0).getGorderId());
    assertEquals(expPayRecordListVO.getList().get(0).getOrderId(), payRecordListVO.getList().get(0).getOrderId());
  }

  //pass
  @Test
  public void queryLastPayNoTest() {

    PayRecord payRecord = new PayRecord();
    payRecord.setId(1001);//reset

    PayRecord expPayRecord = new PayRecord();
    expPayRecord.setId(1001);
    expPayRecord.setPayNo("-100000000000000001");

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.queryLastPayNo(payRecord);
    expectLastCall().andReturn(expPayRecord);
    EasyMock.replay(payServiceMock);

    String gorderId = "20001";
    PayRecord actual = orderServiceFacadeImpl.queryLastPayNo(gorderId);

    assertEquals(expPayRecord.getPayNo(), actual.getPayNo());


  }

  //pass
//  @Test
  public void queryOrderByPayNoTest() {

    String payNo = "-100000000000000001";//reset

    Order exp = new Order();
    exp.setGorderId("20001");//reset
    exp.setId("20140813000000001");//reset
    exp.setProductType("0501");//reset

    Page page = new Page();
    List list = new ArrayList<PayRecord>();
    PayRecord payRecord = new PayRecord();

    payRecord.setOrderId("20140813000000001");
    payRecord.setGorderId("20001");
    payRecord.setProductType("0501");
    list.add(payRecord);
    page.setResult(list);

    PayService payService = EasyMock.createMock(PayService.class);
    Map input = new HashMap();
    input.put("payNo", payNo);

    Map output = new HashMap();
    output.put("gorderId", "");
    output.put("orderId", "");
    output.put("productType", "");

    payService.queryPayRecordList(input, output, null);
    expectLastCall().andReturn(page);
    EasyMock.replay(payService);

    Order order = orderServiceFacadeImpl.queryOrderByPayNo(payNo);
    assertEquals(exp.getGorderId(), order.getGorderId());
    assertEquals(exp.getId(), order.getId());
    assertEquals(exp.getProductType(), order.getProductType());

  }

  //pass
  @Test
  public void queryFirstPayRecordTest() {
    Gorder gorder = new Gorder();
    gorder.setId("20001");

    List list = new ArrayList<PayRecord>();
    PayRecord payRecord = new PayRecord();
    payRecord.setGorderId("20001");
    payRecord.setOrderId("20140813000000001");//和前面的gorderId要一致
    list.add(payRecord);

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.queryFirstPayRecord(gorder);
    expectLastCall().andReturn(list);
    EasyMock.replay(payServiceMock);

    List<PayRecord> retList = new ArrayList<PayRecord>();
    retList = orderServiceFacadeImpl.queryFirstPayRecord(gorder);
    //orderServiceFacadeImpl.queryPayRecordList();

    assertEquals(gorder.getId(), retList.get(0).getGorderId());

  }

  //pass
  @Test
  public void movePayRequestToRecordTest() {

    Map input = new HashMap();
    input.put("payNo", "-100000000000000003");

    Map output = new HashMap();
    output.put("id", "");
    output.put("gorderId", "");
    output.put("orderId", "");
    output.put("operType", "");
    output.put("payType", "");
    output.put("amount", "");
    output.put("payNo", "");
    output.put("prePayNo", "");
    output.put("payStatus", "");
    output.put("createTime", "");
    output.put("sentTime", "");
    output.put("paymentTime", "");
    output.put("productType", "");
    output.put("note", "");
    output.put("failureReason", "");
    output.put("processDays", "");
    output.put("clientType", "");
    output.put("outerOrigin", "");
    output.put("callbackUrl", "");
    output.put("uuid", "");

    Page<PayRequest> payRequestPage = new Page<PayRequest>();

    payRequestPage = orderServiceFacadeImpl.queryPagedPayRequestList(input, output, payRequestPage);
    List<PayRequest> list = payRequestPage.getResult();//查询结果为空
    PayRequest payRequest = list.get(0);

    PayRecord payRecord = new PayRecord();
    payRecord.setId(payRequest.getId());
    payRecord.setBuyAccountId(payRequest.getBuyAccountId());
    payRecord.setOperType(payRequest.getOperType());
    payRecord.setAmount(payRequest.getAmount());
    payRecord.setPaymentTime(payRequest.getPaymentTime());
    payRecord.setGorderId(payRequest.getGorderId());
    payRecord.setOrderId(payRequest.getOrderId());
    payRecord.setCreateTime(payRequest.getCreateTime());
    payRecord.setPayNo(payRequest.getPayNo());
    payRecord.setPayStatus(2);
    payRecord.setFailureReason("testOK");

    PayService payServiceMock = EasyMock.createMock(PayService.class);
    payServiceMock.movePayRequestToRecord(payRequest, payRecord);
    EasyMock.replay(payServiceMock);

    orderServiceFacadeImpl.movePayRequestToRecord(payRequest, payRecord);

    PayRecord ret = orderServiceFacadeImpl.queryPayRecordByPayNo(payRequest.getPayNo());

    assertNotNull(ret);

  }

  
      //级联查询
      @Test
      public void queryPagedOrderListTest(){

          Map input = new HashMap();
          input.put("id","20140813000000001");
          Map output = new HashMap();
          output.put("id","");
          output.put("gorderId","");

          Page<Order> page = new Page<Order>();
          List<Order> orderList = new ArrayList<Order>();
          Order expOrder = new Order();
          expOrder.setId("20140813000000001");
          expOrder.setGorderId("20001");
          orderList.add(expOrder);
          page.setResult(orderList);

          CascadeQueryService cascadeQueryServiceMock = EasyMock.createMock(CascadeQueryService.class);
          cascadeQueryServiceMock.queryPagedOrderList(input, output, null);
          expectLastCall().andReturn(page);
          EasyMock.replay(cascadeQueryServiceMock);

          Page<Order> page2 = new Page<Order>();
          page2 = orderServiceFacadeImpl.queryPagedOrderList(input,output,page2);

          assertNotNull(page2);

          assertEquals(expOrder.getId(),page2.getResult().get(0).getId());//这里查询的结果为空
          assertEquals(expOrder.getGorderId(),page2.getResult().get(0).getGorderId());

      }
  
  //这几直接mock那些orderItemService可能会出现这样的问题：null;因为在orderServiceFacadImp里面，这个类是通过@resource资源加载进来的，

    //fail
    @Test
    public void queryOrderItemListTest(){

        Map cond = new HashMap();
        cond.put("id","20140813211091608");

        //cond.put("orderId","20140813000000001");
        List<OrderItem> list = orderServiceFacadeImpl.queryOrderItemList(cond);
        System.out.println(list.get(0).getId());
        /*OrderItem orderItem = new OrderItem();
        orderItem.setId("20140813211091608");
        orderItem.setOrderId("20140813000000001");

        List<OrderItem> exp = new ArrayList<OrderItem>();
        exp.add(orderItem);

        OrderItemService orderItemService = EasyMock.createMock(OrderItemService.class);
        orderItemService.queryOrderItemList(cond);
        expectLastCall().andReturn(exp);
        EasyMock.replay(orderItemService);

        List<OrderItem> ret = orderServiceFacadeImpl.queryOrderItemList(cond);
        assertEquals(exp.get(0).getOrderId(),ret.get(0).getOrderId());//这里执行结果为ret列表为空
        assertEquals(exp.get(0).getId(),ret.get(0).getId());


    }
    
  //级联查询
  //pass
  @Test
  public void queryOrderItemDetailTest() {

    Map input = new HashMap();
    input.put("id", "20140813211091608");

    Map output = new HashMap();
    output.put("orderId", "");

    OrderItem expOrderItem = new OrderItem();
    expOrderItem.setId("20140813211091608");
    expOrderItem.setOrderId("20140813000000001");

    OrderItemService orderItemServiceMock = EasyMock.createMock(OrderItemService.class);
    orderItemServiceMock.queryOrderItemDetail(input, output);
    expectLastCall().andReturn(expOrderItem);
    EasyMock.replay(orderItemServiceMock);

    OrderItem ret = orderServiceFacadeImpl.queryOrderItemDetail(input, output);
    assertNotNull(ret);
    if (ret != null) {
      assertEquals(expOrderItem.getOrderId(), ret.getOrderId());
    }
  }

  //pass
  @Test
  public void queryGorderDetailTest() {

    Map input = new HashMap();
    input.put("id", "20001");

    Map output = new HashMap();
    output.put("productType", "");
    output.put("id", "");

    Gorder expGorder = new Gorder();
    expGorder.setId("20001");
    expGorder.setProductType("0501");

    GorderService gorderService = EasyMock.createMock(GorderService.class);
    gorderService.queryGorderDetail(input, output);
    expectLastCall().andReturn(expGorder);
    EasyMock.replay(gorderService);

    Gorder ret = orderServiceFacadeImpl.queryGorderDetail(input, output);

    assertEquals(expGorder.getId(), ret.getId());
    assertEquals(expGorder.getProductType(), ret.getProductType());

  }

  //pass
  @Test
  public void queryGorderDetailByIdTest() {


    String gorderId = "20001";

    Gorder expGorder = new Gorder();
    expGorder.setId("20001");
    expGorder.setProductType("0501");

    GorderService gorderService = EasyMock.createMock(GorderService.class);
    gorderService.queryGorderDetail(gorderId);
    expectLastCall().andReturn(expGorder);

    Gorder ret = orderServiceFacadeImpl.queryGorderDetailById(gorderId);

    assertEquals(expGorder.getId(), ret.getId());
    assertEquals(expGorder.getProductType(), ret.getProductType());

  }

  ////public void updateOrderStatus(String orderId, Integer orderStatus, String note, String remoteIp) throws ValidationException, OrderException
  //pass
  @Test
  public void updateOrderStatusTest() {

    String note = "updateStatus";//对于order而言，note被写入到了remark字段中
    String romateIp = "192.168.31.145";
    String orderId = "20140813000000005";
    Integer orderStatus = 2;

    //Order expOrder = orderServiceFacadeImpl.queryOrderDetail(orderId);
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(orderStatus);
    UpdateService updateServiceMock = EasyMock.createMock(UpdateService.class);
    updateServiceMock.updateOrderStatus(order, note, romateIp);
    replay(updateServiceMock);

    Order org = orderServiceFacadeImpl.queryOrderDetail(orderId);
    Integer orgStatus = org.getOrderStatus();
    orderServiceFacadeImpl.updateOrderStatus(orderId, orderStatus, note, romateIp);

    Order ret = orderServiceFacadeImpl.queryOrderDetail(orderId);
    assertEquals(orderStatus, ret.getOrderStatus());
    //recover order status
    orderServiceFacadeImpl.updateOrderStatus(orderId, orgStatus, "", romateIp);
  }

  
      //public boolean updatePayRecord(PayRecord condition, PayRecord entity) throws ValidationException,OrderException
      @Test
      public void updatePayRecordTest(){

          String orgPayNo = "-100000000000000006";
          String expOrderId = "0001";
          String orgOrderId = "20140813000000006";

          PayRecord cond = new PayRecord();
          cond.setId(1006);
          PayRecord entity = new PayRecord();
          entity.setOrderId(expOrderId);

          PayService payServiceMock = EasyMock.createMock(PayService.class);
          payServiceMock.updatePayRecord(cond,entity);
          expectLastCall().andReturn(true);
          replay(payServiceMock);

          System.out.print(orderServiceFacadeImpl.updatePayRecord(cond,entity));

          PayRecord ret = orderServiceFacadeImpl.queryPayRecordByPayNo(orgPayNo);
          String retOrderId= ret.getOrderId();

          assertEquals(expOrderId,retOrderId);
          //recover
          entity.setOrderId(orgOrderId);
          orderServiceFacadeImpl.updatePayRecord(cond, entity);

      }
      
  @Resource
  PayRequestDao payRequestDao;

  @Before
  public void before() {
    //在运行movePayRequestToPayRecordTest时再运行这段代码
    //add the deleted entity from the payRequest table when run the movePayRequestToPayRecord
    String str =
        "{\"gorderId\":\"100003\",\"orderId\":\"20140813000000003\",\"buyAccountId\":\"test\",\"operType\":\"1\",\"payType\":\"1\",\"payStatus\":\"1\",\"amount\":\"100\",\"payNo\":\"-100000000000000003\",\"createTime\":\"2014-08-13 20:49:30\",\"sentTime\":\"2014-08-13 20:49:32\",\"payTime\":\"2014-08-13 20:49:34\",\"productType\":\"0501\",\"clientType\":\"ios\",\"outerOrigin\":\"yilong_app\",\"callBackUrl\":\"refund_notify.do\"}";

    PayRequest payRequest = FastJsonUtil.fromJson(str, PayRequest.class);

    payRequestDao.addEntity(payRequest);

  }

  @After
  public void after() {

    //在运行movePayRequestToPayRecordTest时再运行这段代码，删除增加在payRecord表中的记录

  }

  @Test
  public void queryFirstPayInfoTest(){
    QueryFirstPayInfoPojo pojo = new QueryFirstPayInfoPojo();
    pojo.setGorderId("20006");
    pojo.setProductLine("05");
    FirstPayInfoVO vo = orderServiceFacadeImpl.queryFirstPayInfo(pojo);
    assertEquals(vo.getNotifyUrl(),"http://192.168.14.99:8102/pay/first_pay_notify.do");
    assertEquals(vo.getPayAmount(),100.00);
    assertEquals(vo.getTradeNo(),"-100000000000000006");
  }


}
*/