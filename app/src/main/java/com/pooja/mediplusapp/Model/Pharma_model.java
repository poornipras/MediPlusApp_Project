package com.pooja.mediplusapp.Model;

/**
 * Created by Pooja on 3/15/2017.
 */

public class Pharma_model {
    int pharma_id;
    String pharma_name,pharma_number,pharma_address;

    public Pharma_model(int pharma_id, String pharma_name, String pharma_number, String pharma_address) {
        this.pharma_id = pharma_id;
        this.pharma_name = pharma_name;
        this.pharma_number = pharma_number;
        this.pharma_address = pharma_address;
    }

    public String getPharma_address() {
        return pharma_address;
    }

    public void setPharma_address(String pharma_address) {
        this.pharma_address = pharma_address;
    }

    public int getPharma_id() {
        return pharma_id;

    }

    public String getPharma_name() {
        return pharma_name;
    }

    public void setPharma_name(String pharma_name) {
        this.pharma_name = pharma_name;
    }

    public String getPharma_number() {
        return pharma_number;
    }

    public void setPharma_number(String pharma_number) {
        this.pharma_number = pharma_number;
    }
}
