package com.example.course.models;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customer_id")
    private int customer_id;

    private String tr_datetime;

    @Column(name = "mcc_code")
    private int mcc_code;

    @Column(name = "tr_type")
    private int tr_type;

    private double amount;

    private String term_id;

    @OneToOne(fetch = FetchType.LAZY)
    private Gender_train gender_train;

    @OneToOne(fetch = FetchType.LAZY)
    private Tr_mcc_codes tr_mcc_codes;

    @OneToOne(fetch = FetchType.LAZY)
    private Tr_types tr_types;

    public Transaction setCustomer_id(int customer_id) { this.customer_id = customer_id; return this; }
    public Transaction setTr_datetime(String tr_datetime) { this.tr_datetime = tr_datetime; return this; }
    public Transaction setMcc_code(int mcc_code) { this.mcc_code = mcc_code; return this; }
    public Transaction setTr_type(int tr_type) { this.tr_type = tr_type; return this; }
    public Transaction setAmount(double amount) { this.amount = amount; return this; }
    public Transaction setTerm_id(String term_id) { this.term_id = term_id; return this; }

    public int getCustomer_id() { return customer_id; }
    public String getTr_datetime() { return tr_datetime; }
    public int getMcc_code() { return mcc_code; }
    public int getTr_type() { return tr_type; }
    public double getAmount() { return amount; }
    public String getTerm_id() { return term_id; }

    public Transaction() {}

    public Transaction(int customer_id,
                       String tr_datetime,
                       int mcc_code,
                       int tr_type,
                       double amount,
                       String term_id) {
        this.customer_id = customer_id;
        this.tr_datetime = tr_datetime;
        this.mcc_code = mcc_code;
        this.tr_type = tr_type;
        this.amount = amount;
        this.term_id = term_id;
    }
}
