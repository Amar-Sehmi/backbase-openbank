package com.backbase.openbank.service.domain.backbase;

import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BackbaseGetTransactionsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int count = 0;

    private List<BackbaseTransaction> transactions = new ArrayList<>();

    public List<BackbaseTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BackbaseTransaction> transactions) {
        this.transactions = transactions;
    }

    public int getCount() {
        if (CollectionUtils.isNotEmpty(transactions)) {
            count = transactions.size();
        }
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
