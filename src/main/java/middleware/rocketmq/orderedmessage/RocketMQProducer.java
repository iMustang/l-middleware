package middleware.rocketmq.orderedmessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * RocketMQProducer
 * description: 发送有序消息
 * author: xMustang
 * version: 1.0
 * created: 2019/7/6 14:16
 */
public class RocketMQProducer {
	static String NAMESER_ADDR = "192.168.121.130:9876";

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			DefaultMQProducer orderedProducer = new DefaultMQProducer("ordered_producer");
			orderedProducer.setNamesrvAddr(NAMESER_ADDR);

			orderedProducer.start();

			String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD"};
			for (int i = 0; i < 10; i++) {
				int orderId = i % 10;
				Message msg =
						new Message("orderedTopic", tags[i % tags.length], "KEY" + i,
								("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

				SendResult sendResult = orderedProducer.send(msg,
						(List<MessageQueue> mqs, Message message, Object arg) -> {

							// arg的值其实就是orderId
							Integer id = (Integer) arg;

							// mqs是队列集合，也就是topic所对应的所有队列
							int index = id % mqs.size();

							// 这里根据前面的id对队列集合大小求余来返回所对应的队列
							return mqs.get(index);
						}, orderId);

				System.out.println(sendResult);
			}
			orderedProducer.shutdown();
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
