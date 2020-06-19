package ch.nblotti.securities.loader;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {


  @Autowired
  private RabbitTemplate template;

  @Autowired
  private FanoutExchange fanoutExchange;

  public void send(LoadingEvent message) {
    this.template.convertAndSend(fanoutExchange.getName(), "", message);
    System.out.println(" [x] Sent '" + message + "'");
  }

}
