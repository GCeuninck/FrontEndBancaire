package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model;

import java.util.Date;

public class TransactionForm {
    private Account debtor;

    private Account creditor;

    private Double value;

    private Date date;

    public Account getDebtor() {
        return debtor;
    }

    public void setDebtor(Account debtor) {
        this.debtor = debtor;
    }

    public Account getCreditor() {
        return creditor;
    }

    public void setCreditor(Account creditor) {
        this.creditor = creditor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
