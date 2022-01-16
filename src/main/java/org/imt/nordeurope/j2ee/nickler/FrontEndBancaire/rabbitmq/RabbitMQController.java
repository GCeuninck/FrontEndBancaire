package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.rabbitmq;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/publish")
    public String publishMessage() {

        Transaction transaction = new Transaction();
        String jsonTransaction = "todo";
        // todo
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE,
                MessagingConfig.ROUTING_KEY, jsonTransaction);
        return "OK";
    }
}
