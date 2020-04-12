package com.hemaapps.hematrace.Model;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 11, 2020
 * @Subclass Base Description: 
 */
//Imports

//Begin Subclass Base
public class Base {
    
    private Integer base_id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public int getBase_id() {
        return base_id;
    }

    public void setBase_id(int base_id) {
        this.base_id = base_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString() {
        return "Base{" + "base_id=" + base_id + ", name=" + name + ", address=" + address + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + '}';
    }

} //End Subclass Base