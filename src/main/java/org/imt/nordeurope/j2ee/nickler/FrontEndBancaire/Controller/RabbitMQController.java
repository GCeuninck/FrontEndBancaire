package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Controller;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.AccountForm;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.AccountType;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Enums.Currency;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.TransactionForm;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.IAccountService;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.ITransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class RabbitMQController {

    @Inject
    ITransactionService TransactionService;

    @Inject
    IAccountService AccountService;

    @GetMapping("/rabbitmq/publish100")
    public String publishMessage() {

        List<Account> accountList = AccountService.getAllAccounts();

        // besoin d'avoir au moins un compte en BDD
        if(accountList.size() == 0){
            AccountForm autoGenerateAccount = new AccountForm();
            autoGenerateAccount.setOwnerLastName("autoGenerateAccountLastName");
            autoGenerateAccount.setOwnerFirstName("autoGenerateAccountFirstName");
            autoGenerateAccount.setAccountName("autoGenerateAccountName");
            autoGenerateAccount.setAccountType(AccountType.CURRENT);
            autoGenerateAccount.setBalance(Math.random()*100);
            autoGenerateAccount.setCurrency(Currency.EURO);
            autoGenerateAccount.setIban("FR7630001007941234567890185");
            AccountService.createAccount(autoGenerateAccount);

            accountList = AccountService.getAllAccounts();
        }

        Account account1, account2;

        account1 = accountList.get(new Random().nextInt(accountList.size()));
        account2 = accountList.get(new Random().nextInt(accountList.size()));
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

            TransactionService.createTransaction(transaction);
        }
        return "redirect:/transactions";
    }

    @PostMapping(value = "/transactions")
    public String createTransaction(@ModelAttribute("transactionForm") TransactionForm transactionForm) {
        Transaction transaction = new Transaction();
        transaction.setDate(transactionForm.getDate());
        transaction.setValue(transactionForm.getValue());
        transaction.setDebtor(AccountService.getAccount(transactionForm.getDebtorIBAN()));
        transaction.setCreditor(AccountService.getAccount(transactionForm.getCreditorIBAN()));
        TransactionService.createTransaction(transaction);
        return "redirect:/transactions";
    }
}
