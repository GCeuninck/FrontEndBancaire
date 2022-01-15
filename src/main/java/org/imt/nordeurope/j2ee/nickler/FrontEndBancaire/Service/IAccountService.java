package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;

import java.util.List;

public interface IAccountService {
    public List<Account> getAllAccounts();
}
