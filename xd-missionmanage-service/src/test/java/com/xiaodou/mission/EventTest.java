package com.xiaodou.mission;

import org.junit.Test;

import com.xiaodou.mission.engine.event.BeFollowedEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.FollowEvent;
import com.xiaodou.mission.engine.event.TollgateEvent;

/**
 * @name @see com.xiaodou.mission.EventTest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 事件单测类
 * @version 1.0
 */
public class EventTest extends BaseSpringTest {

  // @Test
  // public void sendPkEvent0() throws InterruptedException {
  // for (int i = 0; i < 6; i++) {
  // PkEvent event = EventBuilder.buildPkEvent();
  // event.setModule("2");
  // event.setUserId("3");
  // event.setTargetUserId("918");
  // event.setIsWinner(true);
  // event.send();
  // Thread.sleep(100);
  // }
  // }
  //
  // @Test
  // public void sendPkEvent2() throws InterruptedException {
  // for (int i = 0; i < 6; i++) {
  // PkEvent event = EventBuilder.buildPkEvent();
  // event.setModule("2");
  // event.setUserId("918");
  // event.setTargetUserId("3");
  // event.setIsFailer(true);
  // event.send();
  // Thread.sleep(100);
  // }
  // }
  //
  // @Test
  // public void sendPkEvent1() throws InterruptedException {
  // for (int i = 0; i < 6; i++) {
  // PkEvent event = EventBuilder.buildPkEvent();
  // event.setModule("2");
  // event.setUserId("3");
  // event.setCount(15);
  // event.setScore(10);
  // event.setCredit(10);
  // event.send();
  // Thread.sleep(100);
  // }
  // }

//  @Test
  public void sendBeFollowEvent() throws InterruptedException {
    for (int i = 0; i < 6; i++) {
      BeFollowedEvent event = EventBuilder.buildBeFollowedEvent();
      event.setModule("2");
      event.setUserId("3");
      event.setTargetUserId("918");
      event.send();
      Thread.sleep(100);
    }
  }

//  @Test
  public void sendFollowEvent() throws InterruptedException {
    for (int i = 0; i < 6; i++) {
      FollowEvent event = EventBuilder.buildFollowEvent();
      event.setModule("2");
      event.setUserId("3");
      event.setTargetUserId("918");
      event.send();
      Thread.sleep(100);
    }
  }

//  @Test
  public void sendTollgateEvent1() throws InterruptedException {
    String courseId = "17";
    for (int i = 0; i < 6; i++) {
      TollgateEvent event = EventBuilder.buildTollgateEvent();
      event.setModule("2");
      event.setUserId("3");
      event.setCourseId(courseId);
      event.setCount(10);
      event.setCredit(10);
      event.setScore(10d);
      event.setStarLevel(1);
      event.setTollgateId(courseId + i);
      event.send();
      Thread.sleep(100);
    }
  }

//  @Test
  public void sendTollgateEvent2() throws InterruptedException {
    String courseId = "18";
    for (int i = 0; i < 6; i++) {
      TollgateEvent event = EventBuilder.buildTollgateEvent();
      event.setModule("2");
      event.setUserId("3");
      event.setCourseId(courseId);
      event.setCount(10);
      event.setCredit(10);
      event.setScore(10d);
      event.setStarLevel(2);
      event.setTollgateId(courseId + i);
      event.send();
      Thread.sleep(100);
    }
  }

//  @Test
  public void sendTollgateEvent3() throws InterruptedException {
    String courseId = "19";
    for (int i = 0; i < 6; i++) {
      TollgateEvent event = EventBuilder.buildTollgateEvent();
      event.setModule("2");
      event.setUserId("3");
      event.setCourseId(courseId);
      event.setCount(10);
      event.setCredit(10);
      event.setScore(10d);
      event.setStarLevel(3);
      event.setTollgateId(courseId + i);
      event.send();
      Thread.sleep(100);
    }
  }
  
  @Test
  public void testEvent(){
	  System.out.println("TestEvent");
  }

}
