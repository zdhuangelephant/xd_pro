package com.xiaodou.oms.service.order;
//package com.elong.oms.service.order;
//
//import com.elong.oms.BaseSpringTest;
//import com.elong.oms.entity.order.Order;
//import com.elong.oms.entity.order.OrderItem;
//import com.elong.oms.service.oms.OmsOrderService;
//import com.elong.oms.util.model.QueryPaymentModel;
//import com.elong.oms.vo.input.Page;
//import com.elong.oms.vo.input.order.Gorder;
//import com.elong.oms.vo.input.order.GorderOrderItemListPojo;
//import com.elong.oms.vo.result.order.GorderOrderItemListVO;
//
//import org.junit.Test;
//import org.unitils.spring.annotation.SpringBean;
//
//import javax.annotation.Resource;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CascadeQueryServiceTest extends BaseSpringTest {
//    @Resource
//    CascadeQueryService cascadeQueryService;
//
//    @Resource(name = "omsOrderService")
//    OmsOrderService orderService;
//
//    @Test
//    public void testQueryGorderPayRecordById() throws Exception {
//        String id="35235";
//        Map<String,Object> output = new HashMap<>();
//
//        QueryPaymentModel result= cascadeQueryService.queryGorderPayRecordById(id);
//        System.out.println(result);
//    }
//
//    /**
//     * testQueryGorderOrderItemList
//     * @throws Exception
//      * @Title: testQueryGorderOrderItemList
//      * @Description: 测试三级联查
//     */
//    @Test
//    public void testQueryGorderOrderItemList() throws Exception{
//	GorderOrderItemListPojo pojo = new GorderOrderItemListPojo();
//
//	/**
//	 * 分页信息
//	 */
//	com.elong.oms.vo.input.Page page = new Page();
//	page.setPageNo(1);
//	page.setPageSize(30);
//	pojo.setPage(page);
//
//	pojo.setProductLine("07");
//
//	Map<String, Object> gorderOutput = new HashMap<>();
//	gorderOutput.put("id", "1");
//	Map<String, Object> orderOutput = new HashMap<>();
//	orderOutput.put("id", "1");
//	orderOutput.put("deliveryBeginTime", "1");
//	//orderOutput.put("deliveryEndTime", "1");
//	Map<String, Object> itemOutput = new HashMap<>();
//	itemOutput.put("id", "1");
//
//	pojo.setGorderOutputProperties(gorderOutput);
//	pojo.setOrderOutputProperties(orderOutput);
//	pojo.setOrderItemOutputProperties(itemOutput);
//
//	com.elong.oms.vo.input.order.Gorder gorder = new Gorder();
//	gorder.setProductType("0701");
//	gorder.setGorderStatus(0);
//	pojo.setGorderQueryParams(gorder);
//
//	com.elong.oms.vo.input.order.Order order = new com.elong.oms.vo.input.order.Order();
//	//order.setDeliveryBeginTimeOrder("DESC");
//	pojo.setOrderQueryParams(order);
//	com.elong.oms.vo.input.order.OrderItem item1 = new com.elong.oms.vo.input.order.OrderItem();
//	pojo.setOrderItemQueryParams(item1);
//
//	GorderOrderItemListVO vo = orderService.gorderOrderItemList(pojo);
//	List<OrderItem>  itemList = vo.getList();
//	for(OrderItem item :itemList){
//	    Order order1 = item.getOrder();
//	    System.out.println(order1.getDeliveryBeginTime());
//	    System.out.println(order1.getDeliveryEndTime());
//	    System.out.println("-------------------");
//	}
//
//    }
//
//
//}