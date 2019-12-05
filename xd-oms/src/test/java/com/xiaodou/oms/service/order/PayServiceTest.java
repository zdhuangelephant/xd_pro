package com.xiaodou.oms.service.order;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.payment.vo.response.inner.MixPaymentTransOperationInfo;

/**
 * Date: 2014/9/16
 * Time: 10:18
 *
 * @author Tian.Dong
 */
public class PayServiceTest extends BaseSpringTest {

  @Resource
  PayService payService;

  @Resource
  OrderServiceFacade orderServiceFacade;

  @Test
  public void testDate() throws ParseException {
    Date date = new Date();
    String startTime = "2014-09-20 00:00:00";
    String endTime = "2014-09-25 00:00:00";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startDate = sdf.parse(startTime);
    Date endDate = sdf.parse(endTime);

    System.out.println(date.after(startDate));
    System.out.println(date.before(endDate));
  }

  @Test
  public void testJudgeIsRedBonusesActivityOn() {
    //判断红包活动是否开始
    boolean flag = payService.judgeIsRedBonusesActivityOn();
    System.out.println(flag);
  }

  @Test
  public void testJudgeIsRedBonusesActivityTime() {
    //判断是否符合红包活动时间
    boolean flag = payService.judgeIsRedBonusesActivityTime(new Timestamp(new Date().getTime()));
    System.out.println(flag);
  }

  @Test
  public void testJudgeIsAppTrainFirst(){
    PayRecord payRecord = orderServiceFacade.queryPayRecordByPayNo("-100000000000071319");
    boolean flag = payService.judgeIsAppTrainFirst(payRecord);
    System.out.println(flag);
  }

  @Test
  public void getNewestRecordExceptionTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<MixPaymentTransOperationInfo> mixPaymentTransOperationInfos = null ;
    MixPaymentTransOperationInfo newestInfo =PayService.getNewestRecord(mixPaymentTransOperationInfos);//MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(null, newestInfo);

    mixPaymentTransOperationInfos = new ArrayList<>();
    MixPaymentTransOperationInfo newestInfo2 =PayService.getNewestRecord(mixPaymentTransOperationInfos);  //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(null,newestInfo2);

    Calendar calendar = Calendar.getInstance();
    Date nowDate = calendar.getTime();
    mixPaymentTransOperationInfos = new ArrayList<>();
    MixPaymentTransOperationInfo info = new MixPaymentTransOperationInfo();
    info.setOperationTime(nowDate);
    mixPaymentTransOperationInfos.add(info);
    MixPaymentTransOperationInfo newestInfo3 =PayService.getNewestRecord(mixPaymentTransOperationInfos);  //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(info,newestInfo3);


    mixPaymentTransOperationInfos = new ArrayList<>();
    MixPaymentTransOperationInfo info2 = new MixPaymentTransOperationInfo();
    mixPaymentTransOperationInfos.add(info2);
    MixPaymentTransOperationInfo newestInfo4 =PayService.getNewestRecord(mixPaymentTransOperationInfos);  //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(null,newestInfo4);

    mixPaymentTransOperationInfos = new ArrayList<>();
    mixPaymentTransOperationInfos.add(info2);
    mixPaymentTransOperationInfos.add(info2);
    MixPaymentTransOperationInfo newestInfo5 =PayService.getNewestRecord(mixPaymentTransOperationInfos);  //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(null, newestInfo5);

    mixPaymentTransOperationInfos = new ArrayList<>();
    mixPaymentTransOperationInfos.add(info2);
    mixPaymentTransOperationInfos.add(info);
    MixPaymentTransOperationInfo newestInfo6 =PayService.getNewestRecord(mixPaymentTransOperationInfos); //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(info, newestInfo6);

  }


  @Test
  public void getNewestRecordTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Calendar calendar = Calendar.getInstance();
    Date nowDate = calendar.getTime();
    calendar.add(Calendar.MINUTE,1);
    Date date1 = calendar.getTime();
    calendar.add(Calendar.MINUTE,-2);
    Date date2 = calendar.getTime();
    List<MixPaymentTransOperationInfo> mixPaymentTransOperationInfos = new ArrayList<>();
    MixPaymentTransOperationInfo info1 = new MixPaymentTransOperationInfo();
    info1.setOperationTime(nowDate);
    MixPaymentTransOperationInfo info2 = new MixPaymentTransOperationInfo();
    info2.setOperationTime(date1);
    MixPaymentTransOperationInfo info3 = new MixPaymentTransOperationInfo();
    info3.setOperationTime(date2);
    mixPaymentTransOperationInfos.add(info1);
    mixPaymentTransOperationInfos.add(info2);
    mixPaymentTransOperationInfos.add(info3);
    MixPaymentTransOperationInfo newestInfo = PayService.getNewestRecord(mixPaymentTransOperationInfos); //(MixPaymentTransOperationInfo)method.invoke(fraudInitStateHandler, mixPaymentTransOperationInfos);
    Assert.assertEquals(info2, newestInfo);
  }

}
