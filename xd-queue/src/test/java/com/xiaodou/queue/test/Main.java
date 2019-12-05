package com.xiaodou.queue.test;

import java.util.Date;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.contants.QueueConstants;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.model.ContainerParamModel;

public class Main {
  public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      ClassNotFoundException {
    ContainerParamModel queueContainerModel = new ContainerParamModel();
    queueContainerModel.setWorkerMode(QueueConstants.NORMAL_MODE);
    IMQClient client =
        new AbstractMQClient(null, queueContainerModel, MyWorker.class,
            DefaultMessageQueueManager.class);
    System.out.println(new Date());
    System.out.println("######################### start #########################");
//    MessageBox box = new MessageBox();
//    for (int i = 0; i < 10; i++) {
//      box.addCurrentLevelMessage("firstLevel", String.format("change it firstLevel %d", i));
//      box.addNextLevelMessage("secondLevel", String.format("change it secondLevel %d", i));
//    }
//    client.sendMessage(box);
    client.sendMessage("handler", "1234");
  }
}
