package com.example.course.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tr_mcc_codes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int mcc_code;
    private String mcc_description;

    public int getMcc_code() {
        return mcc_code;
    }

    public void setMcc_code(int mcc_code) {
        this.mcc_code = mcc_code;
    }

    public String getMcc_description() {
        return mcc_description;
    }

    public void setMcc_description(String mcc_description) {
        this.mcc_description = mcc_description;
    }

    public Tr_mcc_codes() {}

    public Tr_mcc_codes(int mcc_code, String mcc_description) {
        this.mcc_code = mcc_code;
        this.mcc_description = mcc_description;
    }
}
