package middleware.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @title: TopicConsumer2
 * @description:
 * @author: xMustang
 * @version: 1.0
 * @create: 2019-07-05 20:53
 */
public class TopicConsumer2 {
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://192.168.121.130:61616"
		);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topicTest");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(message -> {
			if (message instanceof TextMessage) {
				try {
					System.out.println("消费者2收到：" + ((TextMessage) message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
