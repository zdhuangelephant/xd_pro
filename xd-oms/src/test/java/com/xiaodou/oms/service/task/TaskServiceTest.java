package com.xiaodou.oms.service.task;

import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.service.task.TaskService;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author Kai.Chen
 * @version V1.0
 * @date 14/11/21 上午9:55
 */
public class TaskServiceTest extends BaseSpringTest{

    @Resource
    TaskService taskService;

    @Test
    public void doRetryUnreachablePayToPaymentTest(){
//        taskService.doRetryUnreachablePayToPayment();
    }
}
