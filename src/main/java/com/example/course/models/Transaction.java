package com.example.course.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int customer_id;
    private String tr_datetime;
    private int mcc_code;
    private int tr_type;
    private double amount;
    private String term_id;

    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }
    public void setTr_datetime(String tr_datetime) { this.tr_datetime = tr_datetime; }
    public void setMcc_code(int mcc_code) { this.mcc_code = mcc_code; }
    public void setTr_type(int tr_type) { this.tr_type = tr_type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setTerm_id(String term_id) { this.term_id = term_id; }

    public int getCustomer_id() { return customer_id; }
    public String getTr_datetime() { return tr_datetime; }
    public int getMcc_code() { return mcc_code; }
    public int getTr_type() { return tr_type; }
    public double getAmount() { return amount; }
    public String getTerm_id() { return term_id; }
}
