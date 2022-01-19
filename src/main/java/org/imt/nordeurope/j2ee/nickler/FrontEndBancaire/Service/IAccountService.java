package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.AccountForm;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccounts();
    Account getAccount(String accountIBAN);
    void deleteAccount(String accountIBAN);
    void createAccount(AccountForm accountForm);
}
