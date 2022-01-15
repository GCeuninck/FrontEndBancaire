package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.AccountForm;

import java.util.List;

public interface IAccountService {
    public List<Account> getAllAccounts();
    public void deleteAccount(String accountIBAN);
    public void createAccount(AccountForm accountForm);
}
