package com.backbase.openbank.service.util;

import com.backbase.openbank.service.domain.backbase.BackbaseGetTransactionsResponse;
import com.backbase.openbank.service.domain.backbase.BackbaseTransaction;
import com.backbase.openbank.service.domain.openbank.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class ResponseTransformerUtil {

    // ----------------
    // public methods
    // ----------------
    public static BackbaseGetTransactionsResponse getTransactionResponse(OpenbankGetTransactionResponse openBankResponse, String transactionType) {
        if (openBankResponse == null || CollectionUtils.isEmpty(openBankResponse.getTransactions())) {
            return null;
        }

        List<Transaction> openBankTransactions = openBankResponse.getTransactions();
        if (StringUtils.isNotBlank(transactionType)) {
            openBankTransactions = (List<Transaction>) CollectionUtils.select(openBankTransactions, transaction ->
                    transaction.getDetails() != null && StringUtils.equalsIgnoreCase(transactionType, transaction.getDetails().getType()));
        }

        List<BackbaseTransaction> transactions = new ArrayList<>();
        openBankTransactions.forEach(openBankTransaction -> transactions.add(transformTransaction(openBankTransaction)));

        BackbaseGetTransactionsResponse backbaseResponse = new BackbaseGetTransactionsResponse();
        backbaseResponse.setTransactions(transactions);
        return backbaseResponse;
    }

    public static float getTransactionAmount(OpenbankGetTransactionResponse openBankResponse, String transactionType) {
        if (openBankResponse == null || CollectionUtils.isEmpty(openBankResponse.getTransactions())) {
            return 0;
        }

        List<Transaction> openBankTransactions = openBankResponse.getTransactions();
        if (StringUtils.isNotBlank(transactionType)) {
            openBankTransactions = (List<Transaction>) CollectionUtils.select(openBankTransactions, transaction ->
                    transaction.getDetails() != null && StringUtils.equalsIgnoreCase(transactionType, transaction.getDetails().getType()));
        }

        float totalAmount = 0f;
        for (Transaction openBankTransaction : openBankTransactions) {
            if (openBankTransaction.getDetails() != null) {
                totalAmount += openBankTransaction.getDetails().getValue().getAmount();
            }
        }

        return new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    // ----------------
    // private methods
    // ----------------
    private static BackbaseTransaction transformTransaction(Transaction openBankTransaction) {
        if (openBankTransaction == null) {
            return null;
        }
        BackbaseTransaction backbaseTransaction = new BackbaseTransaction();
        backbaseTransaction.setId(openBankTransaction.getId());
        transformThisAccount(backbaseTransaction, openBankTransaction.getThis_account());
        transformOtherAccount(backbaseTransaction, openBankTransaction.getOther_account());
        transformDetails(backbaseTransaction, openBankTransaction.getDetails());
        return backbaseTransaction;
    }

    private static void transformThisAccount(BackbaseTransaction backbaseTransaction, Account account) {
        if (account == null) {
            return;
        }

        backbaseTransaction.setAccountId(account.getId());
    }

    private static void transformOtherAccount(BackbaseTransaction backbaseTransaction, OtherAccount otherAccount) {
        if (otherAccount == null) {
            return;
        }

        backbaseTransaction.setCounterpartyAccount(otherAccount.getNumber());

        if (otherAccount.getHolder() != null) {
            backbaseTransaction.setCounterpartyName(otherAccount.getHolder().getName());
        }

        if (otherAccount.getMetadata() != null) {
            backbaseTransaction.setCounterPartyLogoPath(otherAccount.getMetadata().getImage_URL());
        }
    }

    private static void transformDetails(BackbaseTransaction backbaseTransaction, Details openBankDetails) {
        if (openBankDetails == null) {
            return;
        }

        backbaseTransaction.setTransactionType(openBankDetails.getType());
        backbaseTransaction.setDescription(openBankDetails.getDescription());

        if (openBankDetails.getValue() != null) {
            backbaseTransaction.setInstructedAmount(openBankDetails.getValue().getAmount());
            backbaseTransaction.setInstructedCurrency(openBankDetails.getValue().getCurrency());
            backbaseTransaction.setTransactionAmount(openBankDetails.getValue().getAmount());
            backbaseTransaction.setTransactionCurrency(openBankDetails.getValue().getCurrency());
        }
    }
}
