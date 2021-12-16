package com.lenovo.lcp.core.voiceservice.service;

import java.io.Serializable;

public class Address implements Cloneable, Serializable {
    private String city;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
