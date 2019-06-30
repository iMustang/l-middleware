package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.leveldb.replicated.SlaveLevelDBStore;

import javax.jms.*;

/**
 * @title: ReceiveMsgDemo
 * @description:
 */
public class ReceiveMsgDemo {
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
		TextMessage receive = (TextMessage) consumer.receive();
		String text = receive.getText();
		System.out.println(text);
		if (connection != null) {
			connection.close();
		}
	}
}
