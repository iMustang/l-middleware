package middleware.rocketmq.messagesendways;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * AsyncRocketMQProducer
 * description: 异步发送消息
 */
public class AsyncRocketMQProducer {
	static String NAMESER_ADDR = "192.168.121.130:9876";

	public static void main(String[] args) throws MQClientException {
		DefaultMQProducer producer = new DefaultMQProducer("async_producer");
		producer.setNamesrvAddr(NAMESER_ADDR);
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Message msg = new Message("asyncTopic",
						"TagA",
						"OrderID188",
						("Hello RMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

				producer.send(msg, new SendCallback() {
					public void onSuccess(SendResult sendResult) {
						System.out.printf("%-10d OK %s %n", index,
								sendResult.getMsgId());
					}
					public void onException(Throwable e) {
						System.out.printf("%-10d Exception %s %n", index, e);
						e.printStackTrace();
					}
				});
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemotingException e) {
				e.printStackTrace();
			} catch (MQClientException e) {
				e.printStackTrace();
			}

		}
//		producer.shutdown();
	}
}
