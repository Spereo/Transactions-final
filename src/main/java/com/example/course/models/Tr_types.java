package com.example.course.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tr_types {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int tr_type;
    private String tr_description;

    public int getTr_type() {
        return tr_type;
    }

    public void setTr_type(int tr_type) {
        this.tr_type = tr_type;
    }

    public String getTr_description() {
        return tr_description;
    }

    public void setTr_description(String tr_description) {
        this.tr_description = tr_description;
    }

    public Tr_types() {}

    public Tr_types(int tr_type, String tr_description) {
        this.tr_type = tr_type;
        this.tr_description = tr_description;
    }
}
