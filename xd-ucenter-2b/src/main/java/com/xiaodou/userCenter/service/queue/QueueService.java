package com.xiaodou.userCenter.service.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.model.UserPraiseModel;
import com.xiaodou.userCenter.vo.mq.UpdatePraiseNumMessage;

import lombok.Data;

@Service("queueService")
public class QueueService {

    public enum Message {
        LogoutMessage("LogoutMessage"), RegistIMAccount("RegistIMAccount"), UpdatePraiseNum(
                "updatePraiseNum");
        
        Message(String message) {
            this.message = message;
        }

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private IMQClient m =
            new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

    public void pushLoginOut(PushLoginOutDTO pushLoginOutDTO) {
        m.sendMessage(Message.LogoutMessage.getMessage(), pushLoginOutDTO);
    }

    public void updatePraiseNum(UserPraiseModel model) {
        RabbitMQSender.getInstance().send(new UpdatePraiseNumMessage(model));
    }

    @Data
    public static class PushLoginOutDTO {
        private UserModel entity;
        private String registrationId;
        private String systemType;
    }

}
