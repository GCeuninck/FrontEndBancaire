package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.rabbitmq;

import com.google.gson.Gson;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.AccountType;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.Currency;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.AccountService;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.TransactionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AccountService accountService;

    @GetMapping("/publish100")
    public String publishMessage() {
        // besoin d'avoir 2 comptes en BDD
        List<Account> accountList= accountService.getAllAccounts();
        Account account1, account2;
        account1 = accountList.get(0);
        account2 = accountList.get(1);
        for(int i=0;i<100;i++){
            Transaction transaction = new Transaction();
            if(Math.random()<=0.5){
                transaction.setCreditor(account1);
                transaction.setDebtor(account2);
            }else{
                transaction.setCreditor(account2);
                transaction.setDebtor(account1);
            }

            transaction.setDate(new Date());
            transaction.setValue(Math.random()*100);

            Gson gson = new Gson();
            String jsonTransaction = gson.toJson(transaction);

            rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE,
                    MessagingConfig.ROUTING_KEY, jsonTransaction);
        }

        return "redirect:/transactions";

    }
}
