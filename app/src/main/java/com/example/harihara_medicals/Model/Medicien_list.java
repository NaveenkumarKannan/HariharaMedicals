package com.example.harihara_medicals.Model;

public class Medicien_list {

    private String medicien_name, medicien_addto_cart, medicien_sub, medicien_add, med_id;
    private int medicien_price, medicien_count;

    int medi_count=1;

    public int getMedi_count() {
        return medi_count;
    }

    public void setMedi_count(int medi_count) {
        this.medi_count = medi_count;
    }


    public String getMedicien_name() {
        return medicien_name;
    }

    public void setMedicien_name(String medicien_name) {
        this.medicien_name = medicien_name;
    }

    public int getMedicien_price() {
        return medicien_price;
    }

    public void setMedicien_price(int medicien_price) {
        this.medicien_price = medicien_price;
    }

    public int getMedicien_count() {
        return medicien_count;
    }

    public void setMedicien_count(int medicien_count) {
        this.medicien_count = medicien_count;
    }

    public String getMedicien_addto_cart() {
        return medicien_addto_cart;
    }

    public void setMedicien_addto_cart(String medicien_addto_cart) {
        this.medicien_addto_cart = medicien_addto_cart;
    }

    public String getMedicien_sub() {
        return medicien_sub;
    }

    public void setMedicien_sub(String medicien_sub) {
        this.medicien_sub = medicien_sub;
    }

    public String getMedicien_add() {
        return medicien_add;
    }

    public void setMedicien_add(String medicien_add) {
        this.medicien_add = medicien_add;
    }

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }
}
