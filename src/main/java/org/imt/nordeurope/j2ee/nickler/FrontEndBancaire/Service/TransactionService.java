package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class TransactionService implements ITransactionService {

    static final String URL_BACKEND = "http://localhost:9091/";

    @Override
    public List<Transaction> getAllTransactions() {
        RestTemplate restTemplate = new RestTemplate();
        List<Transaction> transactions = Arrays.asList(restTemplate.getForObject(URL_BACKEND + "transactions", Transaction[].class));
        return transactions;
    }

    @Override
    public List<Transaction> getAllTransactionsOfAccount(String accountIBAN) {
        RestTemplate restTemplate = new RestTemplate();
        List<Transaction> transactionList = Arrays.asList(restTemplate.getForObject(URL_BACKEND + "account/" + accountIBAN + "/transactions", Transaction[].class));
        return transactionList;
    }
}
