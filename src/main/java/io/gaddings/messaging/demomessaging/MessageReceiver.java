package io.gaddings.messaging.demomessaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static io.gaddings.messaging.demomessaging.MessagingConfig.*;

@Component
public class MessageReceiver {

  @RabbitListener(queues = {FANOUT_QUEUE_NAME})
  public void receiveMessageFromFanout(String message) {
    System.out.println("Received broadcast message: " + message);
  }


  @RabbitListener(queues = {TOPIC_QUEUE_NAME})
  public void receiveMessageFromTopic(String message) {
    System.out.println("Received topic (" + BINDING_PATTERN_ERROR + ") message: " + message);
  }

}
