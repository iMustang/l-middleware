package middleware.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @title: ReceiveMsgListenerDemo
 * @description:
 */
public class ReceiveMsgListenerDemo {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Destination queue = session.createQueue("queueTest");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					try {
						System.out.println(((TextMessage) message).getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
