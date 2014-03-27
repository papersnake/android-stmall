package com.wjdrf.stmall.app.services;

/**
 * Created by papersnake on 14-3-23.
 */
public class Good {
    private int id;
    private int good_id;
    private String barcode;
    private String category_id;
    private String good_name;
    private String good_spec;
    private Double good_price;
    private Double good_pur_price;
    private String units;
    private String good_belong;
    private int good_num;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_spec() {
        return good_spec;
    }

    public void setGood_spec(String good_spec) {
        this.good_spec = good_spec;
    }

    public Double getGood_price() {
        return good_price;
    }

    public void setGood_price(Double good_price) {
        this.good_price = good_price;
    }

    public Double getGood_pur_price() {
        return good_pur_price;
    }

    public void setGood_pur_price(Double good_pur_price) {
        this.good_pur_price = good_pur_price;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getGood_belong() {
        return good_belong;
    }

    public void setGood_belong(String good_belong) {
        this.good_belong = good_belong;
    }

    public int getGood_num() {
        return good_num;
    }

    public void setGood_num(int good_num) {
        this.good_num = good_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return good_name;
    }
}
