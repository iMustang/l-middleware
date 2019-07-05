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

		//声明并初始化一个producer
		DefaultMQProducer producer = new DefaultMQProducer("producer1");

		//设置NameServer地址，多个地址之间用；分隔
		producer.setNamesrvAddr("192.168.121.130:9876");

		//调用start()方法启动一个producer实例
		producer.start();

		//发送10条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
		for (int i = 0; i < 10; i++) {
			try {
				Message msg = new Message("TopicTest",
						"TagA",
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
				);

				//这里调用的是同步的方式，所以会有返回结果
				SendResult sendResult = producer.send(msg);

				//打印返回结果，可以看到消息发送的状态以及一些相关信息
				System.out.println(sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}

		//发送完消息之后，调用shutdown()方法关闭producer
		producer.shutdown();
	}
}