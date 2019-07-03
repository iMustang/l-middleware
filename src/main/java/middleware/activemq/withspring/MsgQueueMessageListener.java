package middleware.activemq.withspring;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @title: MsgQueueMessageListener
 * @description:
 */
public class MsgQueueMessageListener implements SessionAwareMessageListener<Message> {

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if (message instanceof TextMessage) {
			System.out.println("消费者收到消息：" + ((TextMessage) message).getText());
		}
	}
}

