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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = { "/addAccount" })
    public String addAccount(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("accountTypeList", AccountType.values());
        model.addAttribute("currencyList", Currency.values());
        return "addAccount";
    }

    @GetMapping(value = { "/addTransaction" })
    public String addTransaction(Model model) {
        model.addAttribute("transactionForm", new TransactionForm());
        model.addAttribute("accountList", AccountService.getAllAccounts());
        return "addTransaction";
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

    @DeleteMapping(value = {"/accounts/{Account_IBAN}"})
    public String deleteAccount(Model model, @PathVariable String Account_IBAN) {
        AccountService.deleteAccount(Account_IBAN);
        return "redirect:/accounts";
    }

    @PostMapping(value = "/accounts")
    public String createAccount(Model model, @ModelAttribute("accountForm") AccountForm accountForm) {
        AccountService.createAccount(accountForm);
        return "redirect:/accounts";
    }
}
