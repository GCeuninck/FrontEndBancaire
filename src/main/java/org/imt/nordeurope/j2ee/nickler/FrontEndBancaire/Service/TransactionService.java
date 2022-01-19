package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import com.google.gson.Gson;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.rabbitmq.MessagingConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class TransactionService implements ITransactionService {

    static final String URL_BACKEND = "http://localhost:9091/";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<Transaction> getAllTransactions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction[]> response = restTemplate.getForEntity(URL_BACKEND + "transactions", Transaction[].class);
        assert(response.getStatusCode() == (HttpStatus.OK));

        List<Transaction> transactionList = Arrays.asList(response.getBody());

        return transactionList;
    }

    @Override
    public List<Transaction> getAllTransactionsOfAccount(String accountIBAN) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction[]> response = restTemplate.getForEntity(URL_BACKEND + "account/" + accountIBAN + "/transactions", Transaction[].class);
        assert(response.getStatusCode() == (HttpStatus.OK));

        List<Transaction> transactionList = Arrays.asList(response.getBody());

        return transactionList;
    }

    @Override
    public void createTransaction(Transaction transaction){
        Gson gson = new Gson();
        String jsonTransaction = gson.toJson(transaction);
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, jsonTransaction);
    }
}
