package com.backbase.openbank.service.domain.openbank;

public class OtherAccount extends Account {

    private Holder holder = new Holder();

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }


}
