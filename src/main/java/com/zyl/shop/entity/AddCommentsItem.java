package com.zyl.shop.entity;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class AddCommentsItem {
    private List<Integer> goodsIds;
    private String comments;
    private Integer oid;

    public Integer getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "AddCommentsItem{" +
                "goodsIds=" + goodsIds +
                ", comments='" + comments + '\'' +
                ", oid=" + oid +
                ", uid=" + uid +
                ", exist=" + exist +
                '}';
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private Integer uid;

    private Boolean exist;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public List<Integer> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<Integer> goodsIds) {
        this.goodsIds = goodsIds;
    }

}
