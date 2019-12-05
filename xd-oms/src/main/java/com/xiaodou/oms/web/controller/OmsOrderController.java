package com.xiaodou.oms.web.controller;

import com.xiaodou.oms.entity.order.UpdateGorderPojo;
import com.xiaodou.oms.service.message.MessageManager;
import com.xiaodou.oms.service.oms.OmsOrderService;
import com.xiaodou.oms.vo.UpdateBuyAccountIdByGorderIdResponse;
import com.xiaodou.oms.vo.input.order.*;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultInfoList;
import com.xiaodou.oms.vo.result.order.*;
import com.xiaodou.oms.web.BaseController;
import com.xiaodou.oms.web.ServiceHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller("omsController")
@RequestMapping("/order")
public class OmsOrderController extends BaseController {

  @Resource
  OmsOrderService omsOrderService;

  @Resource
  MessageManager messageManager;

  /**
   * 下单逻辑
   * 
   * @param pojo 参数pojo
   * @return result
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/create_gorder", headers = {"content-type=application/json;charset=utf-8"})
  public String createGorder(@RequestBody CreateOrderPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<CreateOrderPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public CreateOrderVO doService(CreateOrderPojo pojo) throws Exception {
        return omsOrderService.createGorder(pojo);
      }
    });
  }

  /**
   * 触发事件方法
   * 
   * @param pojo 参数pojo
   * @return ResultInfo
   * @throws Throwable
   */
  @RequestMapping(value = "/fire", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String fireEvent(@RequestBody FirePojo pojo) throws Throwable {
    return doMain(pojo, new ServiceHandler<FirePojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(FirePojo pojo) throws Exception {
        return omsOrderService.fireEvent(pojo);
      }
    });
  }

  /**
   * 触发事件方法
   * 
   * @param pojo 参数pojo
   * @return ResultInfo
   * @throws Exception
   */
  @RequestMapping(value = "/fire_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String fireListEvent(@RequestBody FireListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<FireListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfoList<FireVO> doService(FireListPojo pojo) throws Exception {
        return omsOrderService.fireListEvent(pojo);
      }
    });
  }

  /**
   * 查询Gorder详情的方法
   * 
   * @param pojo 参数pojo
   * @return 大订单详情
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_detail", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderDetail(@RequestBody GorderDetailPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderDetailPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GorderDetailVO doService(GorderDetailPojo pojo) throws Exception {
        return omsOrderService.gorderDetail(pojo);
      }
    });
  }

  /**
   * 查询Order详情的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/order_detail", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String orderDetail(@RequestBody OrderDetailPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderDetailPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderDetailVO doService(OrderDetailPojo pojo) throws Exception {
        return omsOrderService.orderDetail(pojo);
      }
    });
  }

  /**
   * 查询OrderItem详情的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/orderitem_detail", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String orderItemDetail(@RequestBody OrderItemDetailPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderItemDetailPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GorderOrderItemDetailVO doService(OrderItemDetailPojo pojo) throws Exception {
        return omsOrderService.orderItemDetail(pojo);
      }
    });
  }

  /**
   * 查询Gorder-Order详情的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_order_detail", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderOrderItemDetail(@RequestBody GorderOrderDetailPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderOrderDetailPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderDetailVO doService(GorderOrderDetailPojo pojo) throws Exception {
        return omsOrderService.gorderOrderDetail(pojo);
      }
    });
  }

  /**
   * 查询Gorder-Order-OrderItem详情的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_order_item_detail", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderOrderItemDetail(@RequestBody GorderOrderItemDetailPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderOrderItemDetailPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GorderOrderItemDetailVO doService(GorderOrderItemDetailPojo pojo) throws Exception {
        return omsOrderService.gorderOrderItemDetail(pojo);
      }
    });
  }

  /**
   * 查询GorderList的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderList(@RequestBody GorderListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GorderListVO doService(GorderListPojo pojo) {
        return omsOrderService.gorderList(pojo);
      }
    });
  }

  /**
   * 查询GorderList的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/order_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String orderList(@RequestBody OrderListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderListVO doService(OrderListPojo pojo) throws Exception {
        return omsOrderService.orderList(pojo);
      }
    });
  }

  /**
   * 查询GorderList的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/orderitem_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String orderitemList(@RequestBody OrderItemListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderItemListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderItemListVO doService(OrderItemListPojo pojo) throws Exception {
        return omsOrderService.orderItemList(pojo);
      }
    });
  }

  /**
   * 联查Gorder-Order列表的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_order_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderOrderList(@RequestBody GorderOrderListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderOrderListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderListVO doService(GorderOrderListPojo pojo) throws Exception {
        return omsOrderService.gorderOrderList(pojo);
      }
    });
  }


  /**
   * 联查Gorder-Order-OrderItem列表的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/gorder_order_item_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String gorderOrderItemList(@RequestBody GorderOrderItemListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<GorderOrderItemListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public GorderOrderItemListVO doService(GorderOrderItemListPojo pojo) throws Exception {
        return omsOrderService.gorderOrderItemList(pojo);
      }
    });
  }
  
  /**
   * 联查Order-OrderItem列表的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/order_orderitem_list", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String orderOrderItemList(@RequestBody OrderOrderItemListPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderOrderItemListPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public OrderOrderItemListVO doService(OrderOrderItemListPojo pojo) throws Exception {
        return omsOrderService.orderOrderItemList(pojo);
      }
    });
  }

  /**
   * 添加Order备注
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/add_order_note", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String addOrderNote(@RequestBody OrderNotePojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<OrderNotePojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(OrderNotePojo pojo) throws Exception {
        return omsOrderService.addOrderNote(pojo);
      }
    });
  }

  /**
   * 添加Order备注
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/add_item_note", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String addItemNote(@RequestBody ItemNotePojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<ItemNotePojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(ItemNotePojo pojo) throws Exception {
        return omsOrderService.addOrderItemNote(pojo);
      }
    });
  }

  /**
   * 更改合作方信息
   * 
   * @param pojo 参数pojo
   * @return 更新结果
   * @throws Exception 
   */
  @RequestMapping(value = "/change_merchant", headers = {"content-type=application/json;charset=utf-8"})
  public @ResponseBody
  String changeMerchant(@RequestBody UpdateMerchantPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<UpdateMerchantPojo>(this) {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(UpdateMerchantPojo pojo) throws Exception {
        return omsOrderService.changeMerchant(pojo);
      }
    });
  }
  
  /**
   *  前台支付，根据GOrder更新BuyAccountId
    * updateBuyAccountIdByGorderId
    * @Title: updateBuyAccountIdByGorderId
    * @Description: TODO
    * @param pojo
    * @return
    * @throws Exception
   */
  @RequestMapping(value = "/updateBuyAccountIdByGorderId", headers = {"content-type=application/json;charset=utf-8"}, method = RequestMethod.POST)
  @ResponseBody
  public String updateBuyAccountIdByGorderId(@RequestBody UpdateBuyAccountIdByGorderIdPojo pojo) throws Exception {
	  return doMain(pojo, new ServiceHandler<UpdateBuyAccountIdByGorderIdPojo>(this) {
	      @SuppressWarnings("unchecked")
	      @Override
	      public ResultInfo doService(UpdateBuyAccountIdByGorderIdPojo pojo) throws Exception {
	    	  UpdateBuyAccountIdByGorderIdResponse response = omsOrderService.updateBuyAccountIdByGorderId(pojo);
	    	  ResultInfo resultInfo = new ResultInfo();
	    	  resultInfo.setRetcode(String.valueOf(response.getRetcode()));
	    	  resultInfo.setRetdesc(response.getRetdesc());
	        return resultInfo;
	      }
	    });
  }
  
	/**
	 * 更新Gorder Tags
	 * 
	 * @title: updateGorderTags
	 * @date 2015年1月29日 下午5:50:09
	 * @param pojo
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateGorderTags", headers = { "content-type=application/json;charset=utf-8" }, method = RequestMethod.POST)
	public @ResponseBody String updateGorderTags(
			@RequestBody UpdateGorderPojo pojo) throws Exception {
		return doMain(pojo, new ServiceHandler<UpdateGorderPojo>(this) {
			@SuppressWarnings("unchecked")
			@Override
			public ResultInfo doService(UpdateGorderPojo pojo) throws Exception {
				return omsOrderService.updateGorderTags(pojo);
			}
		});
	}

}
