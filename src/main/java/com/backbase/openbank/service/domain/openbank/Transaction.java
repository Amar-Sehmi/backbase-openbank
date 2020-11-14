package com.backbase.openbank.service.domain.openbank;

public class Transaction {
    private String id;
    private Account this_account = new Account();
    private OtherAccount other_account = new OtherAccount();
    private Details details = new Details();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getThis_account() {
        return this_account;
    }

    public void setThis_account(Account this_account) {
        this.this_account = this_account;
    }

    public OtherAccount getOther_account() {
        return other_account;
    }

    public void setOther_account(OtherAccount other_account) {
        this.other_account = other_account;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
