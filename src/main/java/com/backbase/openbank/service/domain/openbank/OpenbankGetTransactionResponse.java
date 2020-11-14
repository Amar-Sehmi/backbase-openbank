package com.backbase.openbank.service.domain.openbank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OpenbankGetTransactionResponse implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
