package com.hemaapps.hematrace.Model;

import java.util.Date;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 13, 2020
 * @Subclass Transaction Description: Transaction object.
 */
//Imports

//Begin Subclass Transaction
public class Transaction {
    
     // DAO Objects
    private int transactionId;
    private int productId;
    private String base;
    private String crewmember;
    private String donorNumber;
    private String productType;
    private String transactionType;
    private Date transactionDateTime;
    private String productStatus;

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCrewmember() {
        return crewmember;
    }

    public void setCrewmember(String crewmember) {
        this.crewmember = crewmember;
    }

    public String getDonorNumber() {
        return donorNumber;
    }

    public void setDonorNumber(String donorNumber) {
        this.donorNumber = donorNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionId=" + transactionId + ", productId=" + productId + ", base=" + base + ", crewmember=" + crewmember + ", donorNumber=" + donorNumber + ", productType=" + productType + ", transactionType=" + transactionType + ", transactionDateTime=" + transactionDateTime + ", productStatus=" + productStatus + '}';
    }

} //End Subclass Transaction