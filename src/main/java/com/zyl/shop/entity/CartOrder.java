package com.zyl.shop.entity;

import java.util.List;

public class CartOrder {
    private List<OrderDetailItem> items;
    private Double amount;
    private Integer oid;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

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
