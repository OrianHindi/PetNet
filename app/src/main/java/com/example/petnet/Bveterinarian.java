package com.example.petnet;

import android.location.Address;

public class Bveterinarian implements BusinessUser{

    private String name;
    private String phone_number;
    private String description;
    private final int store_type = 4;
    private double store_rate;
    private Address address;


    public Bveterinarian(String name , String phone_number , String description , Address address){
        this.name = name;
        this.phone_number = phone_number;
        this.description = description;
        this.address = address;
    }

    @Override
    public String get_store_name() {
        return this.name;
    }

    @Override
    public int get_store_type() {
        return this.store_type;
    }

    @Override
    public Address get_address() {
        return this.address;
    }

    @Override
    public String get_phone_number() {
        return this.phone_number;
    }

    @Override
    public double get_store_rate() {
        return this.store_rate;
    }

    @Override
    public String get_description() {
        return this.description;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStore_rate(double store_rate) {
        this.store_rate = store_rate;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
