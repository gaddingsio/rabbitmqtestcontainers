package io.gaddings.messaging.demomessaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

  private final RabbitTemplate rabbitTemplate;

  public MessageSender(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void broadcast(String message) {
    this.rabbitTemplate.convertAndSend(MessagingConfig.FANOUT_EXCHANGE_NAME, "", message);
  }

  public void sendError(String message) {
    this.rabbitTemplate.convertAndSend(MessagingConfig.TOPIC_EXCHANGE_NAME, "this.is.an.error", message);
  }

}
