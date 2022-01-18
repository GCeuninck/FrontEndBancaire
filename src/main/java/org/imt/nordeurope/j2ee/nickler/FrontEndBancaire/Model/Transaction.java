package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model;

import java.util.Date;

public class Transaction {

    public Transaction() {
    }

    public Transaction(Account debtor, Account creditor, Double value, Date date) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.value = value;
        this.date = date;
    }

    private Long id;

    private Account debtor;

    private Account creditor;

    private Double value;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
