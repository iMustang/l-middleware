package middleware.rocketmq.postponemessage;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * RocketMQProducer
 * description: 生产延时消息
 */
public class RocketMQProducer {
	public static void main(String[] args) throws MQClientException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("postpone_producer");
		producer.setNamesrvAddr("192.168.121.130:9876");
		producer.start();

		for (int i = 0; i < 10; i++) {
			try {
				Message msg = new Message("postponeTopic",
						"TagA",
						("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
				);
				// 设置延时消息
				msg.setDelayTimeLevel(3);
				SendResult sendResult = producer.send(msg);

				System.out.println(sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}

		producer.shutdown();
	}
}
