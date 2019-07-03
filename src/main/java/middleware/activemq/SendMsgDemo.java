package middleware.activemq;

import middleware.activemq.entity.Student;
import middleware.activemq.entity.Teacher;
import com.alibaba.fastjson.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @title: SendMsgDemo
 * @description:
 */
public class SendMsgDemo {
	public static void main(String[] args) throws JMSException {
//		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
//				ActiveMQConnectionFactory.DEFAULT_USER,
//				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
//				ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL
//		);
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://192.168.121.130:61616"
		);
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("queueTest");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

//		TextMessage textMessage = session.createTextMessage();
//		textMessage.setText("Hello ActiveMQ");
//		producer.send(textMessage);

		Student student1 = new Student();
		student1.setStuName("mustang");
		student1.setAge(15);
		Student student2 = new Student();
		student2.setAge(34);
		student2.setStuName("horse");
		List<Student> students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		Teacher teacher = new Teacher();
		teacher.setTearchername("xiaoming");
		teacher.setStudentList(students);
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("teacher", JSON.toJSONString(teacher));
		producer.send(mapMessage);
		if (connection != null) {
			connection.close();
		}
	}
}
