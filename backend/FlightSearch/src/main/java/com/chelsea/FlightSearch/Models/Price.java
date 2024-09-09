package com.chelsea.FlightSearch.Models;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Price {
    private String currency;
    private float total;
    private float base;
    private List<Fee> fees = new ArrayList<Fee>();

    public Price(JSONObject priceObject) {
        this.currency = priceObject.getString("currency");
        this.total = Float.parseFloat(priceObject.getString("total"));
        this.base = Float.parseFloat(priceObject.getString("base"));
        constructFees(priceObject.getJSONArray("fees"));
    }

    private void constructFees(JSONArray feesArray) {
        JSONObject fee;
        for (int k = 0; k < feesArray.length(); k++) {
            fee = feesArray.getJSONObject(k);
            fees.add(new Fee(fee.getString("type"), fee.getFloat("amount")));
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getBase() {
        return base;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }
}
