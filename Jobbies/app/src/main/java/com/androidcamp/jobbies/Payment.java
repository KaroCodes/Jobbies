package com.androidcamp.jobbies;

import java.util.Currency;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class Payment {

    private int price;
    private Currency currency;

    public Payment(int price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
