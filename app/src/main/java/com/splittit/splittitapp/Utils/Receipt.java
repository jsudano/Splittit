package com.splittit.splittitapp.Utils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by scagj on 7/23/2017.
 */

public class Receipt implements Serializable {

    private String title;
    private double cost;
    private int committedItems;
    private ArrayList<PaymentItem> paymentItems;

    public Receipt(String title, ArrayList<PaymentItem> paymentItems) {
        this.setTitle(title);
        this.setPaymentItems(paymentItems);
    }

    public Receipt() {
        this.setPaymentItems(new ArrayList<PaymentItem>());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public String getCostText() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(cost);
    }

    private void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<PaymentItem> getPaymentItems() {
        return paymentItems;
    }

    private void setPaymentItems(ArrayList<PaymentItem> paymentItems) {
        this.paymentItems = paymentItems;
        for (int i = 0; i < paymentItems.size(); i++) {
            PaymentItem item = paymentItems.get(i);
            if (item.hasPayer()) {
                committedItems++;
            }
            cost += item.getCost();
        }

        // Add null last item to hold place for add_item view
        paymentItems.add(null);
    }

    public void addPaymentItem(PaymentItem item) {
        // Add the item before the null object
        paymentItems.add(paymentItems.size() - 1, item);
        if (item.hasPayer()) {
            committedItems++;
        }
        cost += item.getCost();
    }

    public int getSize() {
        return paymentItems.size();
    }

    public int getCommittedItems() {
        return committedItems;
    }

    public String getCommittedItemsString() {
        return Integer.toString(getCommittedItems()) + "/" + Integer.toString(getSize() - 1);
    }

    public void appendReceipt(Receipt r) {
        setTitle(title + ", " + r.getTitle());
        paymentItems.remove(getSize() - 1);
        paymentItems.addAll(r.getPaymentItems());
        setCost(cost + r.getCost());
    }


}
