package com.splittit.splittitapp.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by scagj on 7/20/2017.
 */

public class PaymentItem implements Serializable {

    // Payment data
    private String lineItem; // Item being paid for
    private ArrayList<Payer> payers; // Person paying for it
    private double cost; // How much it costs


    public PaymentItem(String lineItem, double cost) {
        this.setLineItem(lineItem);
        this.setCost(cost);
        payers = new ArrayList<>();
    }

    public PaymentItem(String lineItem, double cost, Payer payer) {
        this.setLineItem(lineItem);
        this.setCost(cost);
        payers = new ArrayList<>();
        this.addPayer(payer);
    }

    public String getLineItem() {
        return lineItem;
    }

    private void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public ArrayList<Payer> getPayers() {
        return payers;
    }

    public void addPayer(Payer payer) {
        payers.add(payer);
    }

    public boolean hasPayer() {
        return payers.size()> 0;
    }

    public double getCost() {
        return cost;
    }

    private void setCost(double cost) {
        this.cost = cost;
    }



}
