package com.example.course.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Gender_train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int customer_id;
    private String gender;

    public int getCustomer_id() {
        return customer_id;
    }

    public Gender_train setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Gender_train setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Gender_train() {}

    public Gender_train(int customer_id, String gender) {
        this.customer_id = customer_id;
        this.gender = gender;
    }
}
