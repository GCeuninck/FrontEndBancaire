package org.imt.nordeurope.j2ee.nickler.FrontEndBancaire.Model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TransactionForm {
    private String debtorIBAN;

    private String creditorIBAN;

    private Double value;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    public String getDebtorIBAN() {
        return debtorIBAN;
    }

    public void setDebtorIBAN(String debtorIBAN) {
        this.debtorIBAN = debtorIBAN;
    }

    public String getCreditorIBAN() {
        return creditorIBAN;
    }

    public void setCreditorIBAN(String creditorIBAN) {
        this.creditorIBAN = creditorIBAN;
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
