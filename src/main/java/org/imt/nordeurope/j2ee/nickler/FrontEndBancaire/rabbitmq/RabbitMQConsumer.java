package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(String json) {
        System.out.println("Message receive from the queue : " + json );
        // todo
        for(int i=0;i<100;i++){
            // todo
        }
    }
}