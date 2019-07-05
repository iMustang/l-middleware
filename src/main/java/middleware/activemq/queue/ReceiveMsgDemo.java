package middleware.activemq.queue;

import middleware.activemq.entity.Teacher;
import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;

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
				"tcp://192.168.121.130:61616");
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Destination queue = session.createQueue("queueTest");
		MessageConsumer consumer = session.createConsumer(queue);
//		TextMessage receive = (TextMessage) consumer.receive();
//		String text = receive.getText();
//		System.out.println(text);

		MapMessage mapMsg = (MapMessage) consumer.receive();
		String teacher = mapMsg.getString("teacher");
		Teacher teacher1 = JSON.parseObject(teacher, Teacher.class);
		System.out.println(teacher1.getStudentList().get(0).getStuName());

		if (connection != null) {
			connection.close();
		}
	}
}
