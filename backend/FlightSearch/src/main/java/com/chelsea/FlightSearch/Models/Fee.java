package com.chelsea.FlightSearch.Models;

public class Fee {
    private float amount;
    private String type;

    public Fee(String type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
