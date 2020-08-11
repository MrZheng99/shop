package com.zyl.shop.entity;

public class Sale {
    private String type;
    private String goodsName;
    private Integer sales;
    private Double amount;
    private Integer y;
    private Integer m;
    private Integer d;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "type='" + type + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", sales=" + sales +
                ", amount=" + amount +
                ", y=" + y +
                ", m=" + m +
                ", d=" + d +
                '}';
    }
}
