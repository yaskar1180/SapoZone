package com.example.sapozone.data.shop;

public class Quote {

    private String detail;
    private int price;
    private int lead;

    public Quote(String detail, int price, int lead) {
        this.detail = detail;
        this.price = price;
        this.lead = lead;
    }

    public String getDetail() {
        return detail;
    }

    public int getPrice() {
        return price;
    }

    public int getLead() {
        return lead;
    }
}


