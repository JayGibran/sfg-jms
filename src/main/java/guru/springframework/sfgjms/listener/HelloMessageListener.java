package guru.springframework.sfgjms.listener;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {
	
	private final JmsTemplate jmsTemplate;
	
	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
		System.out.println("I Got a Message!");
		
		System.out.println(helloWorldMessage);
	}
	
	@JmsListener(destination = JmsConfig.MY_SEND_RC_QUEUE)
	public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) throws JmsException, JMSException {
		HelloWorldMessage payloadMessage = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("World!")
				.build();
		
		jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMessage);
	}

}
