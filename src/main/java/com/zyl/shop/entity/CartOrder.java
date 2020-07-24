package com.zyl.shop.entity;

import java.util.List;

public class CartOrder {
    private List<OrderDetailItem> items;
    private Double amount;

    public List<OrderDetailItem> getItems() {
        return items;
    }

    public void setItems(List<OrderDetailItem> items) {
        this.items = items;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartOrder{" +
                "items=" + items +
                ", amount=" + amount +
                '}';
    }
}
