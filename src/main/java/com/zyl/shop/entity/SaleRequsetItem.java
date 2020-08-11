package com.zyl.shop.entity;

/**
 * 报表请求
 */
public class SaleRequsetItem {
    private Integer gtid;
    private Integer gid;
    private Boolean assignType;
    private Boolean assignDate;
    private Integer y;
    private Integer m;
    private Integer d;

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



    public Boolean getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Boolean assignDate) {
        this.assignDate = assignDate;
    }

    public Boolean getAssignType() {
        return assignType;
    }

    public void setAssignType(Boolean assignType) {
        this.assignType = assignType;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getGtid() {
        return gtid;
    }

    public void setGtid(Integer gtid) {
        this.gtid = gtid;
    }


    @Override
    public String toString() {
        return "SaleRequsetItem{" +
                "gtid=" + gtid +
                ", gid=" + gid +
                ", assignType=" + assignType +
                ", assignDate=" + assignDate +
                ", y=" + y +
                ", m=" + m +
                ", d=" + d +
                '}';
    }
}
