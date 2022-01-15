package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.AccountForm;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class AccountService implements IAccountService {

    static final String URL_BACKEND = "http://localhost:9091/";

    @Override
    public List<Account> getAllAccounts() {
        RestTemplate restTemplate = new RestTemplate();
        List<Account> accountList = Arrays.asList(restTemplate.getForObject(URL_BACKEND + "account", Account[].class));
        return accountList;
    }

    @Override
    public void deleteAccount(String accountIBAN) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL_BACKEND + "account/" + accountIBAN);
    }

    @Override
    public void createAccount(AccountForm accountForm) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<AccountForm> request = new HttpEntity<>(accountForm);
        ResponseEntity<Account> response = restTemplate.exchange(URL_BACKEND + "account", HttpMethod.POST, request, Account.class);

        assert(response.getStatusCode() == (HttpStatus.CREATED));
    }


}
