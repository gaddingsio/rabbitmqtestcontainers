package io.gaddings.messaging.demomessaging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ExtendWith(OutputCaptureExtension.class)
public class MessagingApplicationIT {

  @Container
  public final static GenericContainer RABBIT_CONTAINER = new GenericContainer("rabbitmq:3-management")
          .withExposedPorts(5672, 15672);

  @Autowired
  private MessageSender messageSender;

  @Test
  void testBroadcast(CapturedOutput output) {
    messageSender.broadcast("Broadcast Test");
    await().atMost(5, TimeUnit.SECONDS).until(() -> output.toString().contains("Broadcast Test"));
  }

  @DynamicPropertySource
  static void overrideConfiguration(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.host", RABBIT_CONTAINER::getHost);
    registry.add("spring.rabbitmq.port", () -> RABBIT_CONTAINER.getMappedPort(5672));
  }

}
