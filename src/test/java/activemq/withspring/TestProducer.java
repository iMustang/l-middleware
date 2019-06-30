package activemq.withspring;

import activemq.BaseJunit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @title: TestProducer
 * @description:
 */
public class TestProducer extends BaseJunit4Test {

	@Autowired
	private MyProducer myProducer;

	@Test
	public void send() {
		this.myProducer.sendMessage("the message come from Spring!");
	}
}
