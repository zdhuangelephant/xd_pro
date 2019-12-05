package com.xiaodou._2b_dashboard_report;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.st.dashboard.dao.grade.ClassDao;
import com.xiaodou.st.dashboard.dao.student.StudentDao;
import com.xiaodou.st.dashboard.domain.dashboard.SessionLearnPercentDTO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.dashboard.DashboardService;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.pay.WxPayService;
import com.xiaodou.st.dashboard.util.StDateUtil;
import com.xiaodou.st.dashboard.util.StaticInfoProp;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

public class DashBoardTest extends BaseUnitils {


  @SpringBean("dashboardService")
  DashboardService dashboardService;

  @SpringBean("wxPayService")
  WxPayService wxPayService;
  
  @SpringBean("stServiceFacade")
  IStServiceFacade stServiceFacade;
  @SpringBean("classDao")
  ClassDao classDao;
  @SpringBean("studentDao")
  StudentDao studentDao;
  
  @Test
  public void aaa(){
    IQueryParam queryParam = new QueryParam();
    queryParam.addOutputs(CommUtil.getAllField(ClassDO.class));
    Page<ClassDO> page = stServiceFacade.list(queryParam, classDao);
    System.out.println(page.getPageSize()+page.getTotalCount()+page.getTotalPage());
    
    IQueryParam queryParam1 = new QueryParam();
    queryParam1.addOutputs(CommUtil.getAllField(StudentDO.class));
    Page<StudentDO> page1 = stServiceFacade.list(queryParam1, studentDao);
    System.out.println(page1.getPageSize()+page1.getTotalCount()+page1.getTotalPage());
  }
  
  
  //@Test
  public void aa() throws Exception{
    System.out.println(wxPayService.queryPay(1494929231181l));
  }

  // @Test
  public void message() throws ParseException {
    int count = 0;
    while (count < 5) {
      count++;
      List<SessionLearnPercentDTO> list = dashboardService.getSessionLearnPercentDTO(7);
      for (SessionLearnPercentDTO s : list) {
        System.out.print(s.getDateTime() + "|" + s.getLearnPercent() + "|" + s.getMissionPercent()
            + ",");
      }
      System.out.println();
    }
  }

  // @Test
  public void a() {
    RunnableDemo R1 = new RunnableDemo("Thread-1");
    R1.start();

    RunnableDemo R2 = new RunnableDemo("Thread-2");
    R2.start();
  }

  class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo(String name) {
      threadName = name;
      System.out.println("Creating " + threadName);
    }

    public void run() {
      System.out.println("Running " + threadName);
      try {
        for (int i = 4; i > 0; i--) {
          System.out.println("Thread: " + threadName + ", " + i);
          List<SessionLearnPercentDTO> list = dashboardService.getSessionLearnPercentDTO(7);
          for (SessionLearnPercentDTO s : list) {
            System.out.print(s.getDateTime() + ",");
          }
          int count = 0;
          while (count < 5) {
            count++;
            List<String> list1 = getBeforeDateStrList(7);
            for (String a : list1) {
              System.out.print(a + "'");
            }
            System.out.println();
          }
          // 让线程睡眠一会
          Thread.sleep(50);
        }
      } catch (InterruptedException e) {
        System.out.println("Thread " + threadName + " interrupted.");
      } catch (ParseException e) {
        System.out.println("Thread " + threadName + " error.");
      }
      System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
      System.out.println("Starting " + threadName);
      if (t == null) {
        t = new Thread(this, threadName);
        t.start();
      }
    }
  }

  public static void main(String[] args) throws ParseException {
    int count = 0;
    while (count < 5) {
      count++;
      List<String> list = getBeforeDateStrList(7);
      for (String a : list) {
        System.out.print(a + "'");
      }
      System.out.println();
    }
  }

  public static synchronized List<String> getBeforeDateStrList(int days) throws ParseException {
    List<String> examStrList = Lists.newArrayList();
    Date nowDate = new Date();
    Date date = DateUtil.SDF_YMD.parse(StaticInfoProp.productEndApplyTime());
    if (date.after(nowDate)) date = nowDate;
    AtomicInteger ds = new AtomicInteger(days - 1);
    for (; ds.intValue() >= 0; ds.getAndDecrement()) {
      examStrList.add(StDateUtil.getDateForDateAndDays(date, 0, 0, -ds.intValue()));
    }
    return examStrList;
  }
}
