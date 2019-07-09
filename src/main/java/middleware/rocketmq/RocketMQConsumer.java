package middleware.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @title: RocketMQConsumer
 * @description:
 */
public class RocketMQConsumer {

    /**
     * 当前例子是 PushConsumer 用法，给用户感觉是消息从 RocketMQ 服务器推到了应用客户端。
     * 实际是 PushConsumer 内部是使用长轮询 Pull(拉取) 方式从 RocketMQ 服务器拉消息，然后再回调用户 Listener方法
     */
    public static void main(String[] args) throws MQClientException {

        //声明并初始化一个consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("common_consumer");

        consumer.setNamesrvAddr("192.168.121.130:9876;");

        // 指定自己在 Consumer Group 组中的名称
        consumer.setInstanceName("consumber");

        //这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置consumer所订阅的Topic和Tag，*代表全部的Tag
        // 多Topic组合，"TagA || TagB || TagC"
        consumer.subscribe("commonTopic", "*");

        // 注册一个Listener，主要进行消息的逻辑处理
        // 默认 msgs 里只有一条消息，可以通过设置 consumeMessageBatchMaxSize 参数来批量接收消息
        // consumeThreadMin：消费线程池数量，默认最小值10
        // consumeThreadMax：消费线程池数量，默认最大值20
        consumer.registerMessageListener((List<MessageExt> msgs,
                                          ConsumeConcurrentlyContext context) -> {
            System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
            System.out.println("msgs的大小：" + msgs.size());
            //返回消费状态
            //CONSUME_SUCCESS 消费成功
            //RECONSUME_LATER 消费失败，需要稍后重新消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 调用start()方法启动consumer
        consumer.start();

        System.out.println("Consumer Started.");
    }
}
