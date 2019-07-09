package middleware.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @title: RocketMQProducer
 * @description:
 */
public class RocketMQProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        // 声明并初始化一个producer
        DefaultMQProducer producer = new DefaultMQProducer("common_producer");

        // 设置NameServer地址，多个地址之间用；分隔
        producer.setNamesrvAddr("192.168.121.130:9876");

        // 指定自己的在 Producer Group 中的名称
        producer.setInstanceName("producer1");

        // 调用start()方法启动一个producer实例
        // 初始化一次即可，切忌不可每次发送消息时，都调用start方法
        producer.start();

        //发送10条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
        for (int i = 0; i < 10; i++) {
            try {
                // 一个 Producer 对象可以发送多个 topic（主题），多个 tag 的消息
                Message msg;
                if (i < 5) {
                    msg = new Message("commonTopic",
                            "TagA",
                            ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );
                } else {
                    msg = new Message("commonTopicB",
                            "TagBA",
                            ("another Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );
                }
                //这里调用的是同步的方式，所以会有返回结果
                SendResult sendResult = producer.send(msg);

                //打印返回结果，可以看到消息发送的状态以及一些相关信息
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        // 应用退出时，调用 shutdown 关闭网络连接，清理资源，从 MocketMQ 服务器上注销自己
        // 建议应用在 JBOSS、Tomcat 等容器的退出钩子里调用 shutdown 方法
        producer.shutdown();
    }
}