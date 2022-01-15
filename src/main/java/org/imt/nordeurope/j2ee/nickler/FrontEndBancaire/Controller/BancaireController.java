package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Controller;

import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Account;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model.Transaction;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.IAccountService;
import org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Service.ITransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import java.util.List;

@Controller
public class BancaireController {

    @Inject
    ITransactionService TransactionService;

    @Inject
    IAccountService AccountService;

    @GetMapping(value = { "/" })
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = { "/accounts" })
    public String accounts(Model model) {
        List<Account> accountList = AccountService.getAllAccounts();
        model.addAttribute("accountList", accountList);
        return "accounts";
    }

    @GetMapping(value = { "/transactions" })
    public String transactions(Model model) {
        List<Transaction> transactionList = TransactionService.getAllTransactions();
        model.addAttribute("transactionList", transactionList);
        return "transactions";
    }

    @GetMapping(value = { "/accounts/{Account_IBAN}/transactions" })
    public String transactions(Model model, @PathVariable String Account_IBAN) {
        List<Transaction> transactionList = TransactionService.getAllTransactionsOfAccount(Account_IBAN);
        model.addAttribute("transactionList", transactionList);
        return "transactions";
    }

    /*@PostMapping(value = "/checkIBAN")
    public String checkIBAN(Model model, @ModelAttribute("ibanForm") IBANForm ibanForm) {
        IBANValidation ibanValidation = IBANService.checkIBAN(ibanForm.getIBAN());
        model.addAttribute("iban", ibanValidation);
        return "iban";
    }*/
}
