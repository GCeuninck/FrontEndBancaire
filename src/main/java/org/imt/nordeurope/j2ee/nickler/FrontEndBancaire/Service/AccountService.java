package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
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
}
