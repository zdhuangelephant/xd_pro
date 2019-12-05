package com.xiaodou.sms;

import java.util.Date;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.contants.QueueConstants;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.model.ContainerParamModel;
import com.xiaodou.queue.worker.AliyunWorker;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      ClassNotFoundException {
    ContainerParamModel queueContainerModel = new ContainerParamModel();
    queueContainerModel.setWorkerMode(QueueConstants.BATCH_MODE);
    IMQClient client =
        new AbstractMQClient(null, queueContainerModel, AliyunWorker.class,
            DefaultMessageQueueManager.class);
    System.out.println(new Date());
    System.out.println("######################### start ########################");
    for (int i = 0; i < 10000; i++) {
      client.sendMessage("sms-checkcode", String.format("change it %d", i));
    }
  }
}
