package com.xiaodou.oms.service.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.OperationType;
import com.xiaodou.oms.dao.OperationTypeWrapper;
import com.xiaodou.oms.dao.order.GorderDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.oms.service.task.thread.GetPayTypeThread;
import com.xiaodou.oms.service.task.thread.SendRequestThread;
import com.xiaodou.oms.util.FileUtils;
import com.xiaodou.oms.vo.GorderVo;
import com.xiaodou.oms.vo.input.sign.SignMessConf;
import com.xiaodou.oms.vo.input.task.BatchPojo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Date: 2014/7/1 Time: 16:57
 * 
 * @author Tian.Dong
 */
@Service
public class TaskService {

  @Resource
  OrderServiceFacade orderServiceFacade;

  @Resource
  ThreadPoolTaskExecutor taskExecutor;

  @Resource
  GorderDao gorderDao;

  /**
   * 新单 在表tb_shop_gorder 中gorder_status中的表示
   */
  private static final int GORDER_STATUS_NEW_ORDER = 0;
  /**
   * 支付失败 在表tb_shop_gorder 中gorder_status中的表示
   */
  private static final int GORDER_STATUS_PAY_FAIL = -1;

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  private String getGorderTimeLower() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR,
        -FileUtils.PAYMENT_PROPERTIES.getPropertiesInt("gorder_time_lower_dif_hour"));
    return sdf.format(calendar.getTime());
  }

  private String getGorderTimeUpper() {
    Calendar calendar = Calendar.getInstance();
    return sdf.format(calendar.getTime());
  }

  public ResultInfo unpayGorderToMq() {
    List<Integer> gorderStatusList = new ArrayList<>();
    gorderStatusList.add(OrderConstant.STATUS_INITIATE);
    gorderStatusList.add(OrderConstant.STATUS_PAYFAILURE);
    Map<String, Object> inputArgument = new HashMap<>();
    inputArgument.put("listGorderStatus", gorderStatusList);
    inputArgument.put("gorderTimeUpper", getGorderTimeUpper());
    inputArgument.put("gorderTimeLower", getGorderTimeLower());
    String productTypeList =
        FileUtils.PAYMENT_PROPERTIES.getProperties("gorder_query_payment_product_type_list");
    if (!productTypeList.contains(",")) {
      inputArgument.put("productType", productTypeList);
    } else {
      String[] types = productTypeList.split(",");
      List<String> typeList = Arrays.asList(types);
      inputArgument.put("productTypeList", typeList);
    }
    Map<String, Object> outputArgument = new HashMap<>();
    outputArgument.put("id", "");
    outputArgument.put("gorderTime", "");
    List<Gorder> gorders = gorderDao.queryGorderList(inputArgument, outputArgument);
    int limitMinute = FileUtils.PAYMENT_PROPERTIES.getPropertiesInt("gorder_time_limit_minute");
    for (Gorder gorder : gorders) {
      // 剔除成单时间在20分钟以内的新单
      if (new Date().getTime() - gorder.getGorderTime().getTime() < limitMinute * 60 * 1000) {
        continue;
      }
      GorderVo gorderVo = new GorderVo();
      gorderVo.setGorderId(gorder.getId());
      // TODO send message
      // rabbitService.sendMessage(gorderVo,
      // FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue"),
      // FileUtils.RABBITMQ_PROPERTIES.getProperties("rabbit.queryPaymentQueue"));
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  public ResultInfo getPayType(BatchPojo pojo) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.WRITE);
    // 取前top条 gorder记录
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(pojo.getTop());
    Map<String, Object> output = new HashMap<String, Object>();
    output.put("id", OrderConstant.STRING_NEEDED);
    output.put("productType", OrderConstant.STRING_NEEDED);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("realPayMethod", "~");
    input.put("gorderStatus", "1");
    Page<Gorder> resultPage = orderServiceFacade.queryPagedGorderList(input, output, page);
    List<Gorder> gorderList = resultPage.getResult();
    for (Gorder gorder : gorderList) {
      String gorderId = gorder.getId();
      int id;
      try {
        id = Integer.parseInt(gorderId);
      } catch (NumberFormatException e) {
        LoggerUtil.error("[批处理][gorderId不是数字]gorderId:" + gorderId, e);
        continue;
      }
      if (id % pojo.getMod() != pojo.getRemainder()) {
        continue;
      }
      taskExecutor.execute(new GetPayTypeThread(orderServiceFacade, gorder));
    }
    ResultInfo info = new ResultInfo(ResultType.SUCCESS);
    return info;
  }

  /**
   * 批处理发送支付/退款请求 并把payRequest记录移到payRecord
   * 
   * @param pojo
   * @return
   */
  public ResultInfo sendPayRequest(BatchPojo pojo) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.WRITE);
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(pojo.getTop());
    Map<String, Object> output = new HashMap<String, Object>();
    fillPagedPayRequestListOutput(output);
    Map<String, Object> input = new HashMap<String, Object>();

    /** 支持多产品线 **/
    List<String> productTypes = getProductTypesFromConfig();
    // input.put("productType", "0501");
    input.put("productTypeList", productTypes);
    Page<PayRequest> resultPage = orderServiceFacade.queryPagedPayRequestList(input, output, page);

    List<PayRequest> payRequestList = resultPage.getResult();
    for (PayRequest payRequest : payRequestList) {
      // if (payRequest.getId() % pojo.getMod() != pojo.getRemainder()) {
      // continue;
      // }
      // 执行sendRequest

      taskExecutor.execute(new SendRequestThread(orderServiceFacade, payRequest));

    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * getProductTypesFromConfig
   * 
   * @Title: getProductTypesFromConfig
   * @Description: 从配置文件中取得payment 业务代码列表
   * @return
   */
  public List<String> getProductTypesFromConfig() {
    List<String> productTypes = new ArrayList<>();
    String productTypesStr = SignMessConf.getValueByKey("payment.productTypes");
    if (productTypesStr.endsWith(";")) {
      productTypesStr = productTypesStr.substring(0, productTypesStr.length() - 1);
    }
    String[] strs = productTypesStr.split(",");
    for (String productType : strs) {
      productTypes.add(productType);
    }
    return productTypes;
  }


  private void fillPagedPayRequestListOutput(Map<String, Object> output) {
    output.put("id", OrderConstant.STRING_NEEDED);
    output.put("gorderId", OrderConstant.STRING_NEEDED);
    output.put("operType", OrderConstant.INTEGER_NEEDED);
    output.put("payNo", OrderConstant.STRING_NEEDED);
    output.put("prePayNo", OrderConstant.STRING_NEEDED);
    output.put("payType", OrderConstant.STRING_NEEDED);
    output.put("amount", OrderConstant.STRING_NEEDED);
    output.put("productType", OrderConstant.STRING_NEEDED);
    output.put("callbackUrl", OrderConstant.STRING_NEEDED);
  }

}
