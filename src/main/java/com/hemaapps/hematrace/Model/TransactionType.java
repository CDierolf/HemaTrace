package com.hemaapps.hematrace.Model;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 20, 2020
 * @Subclass TransactionType Description:  TransactionType object
 */
//Imports

//Begin Subclass TransactionType
public class TransactionType {
    
    private int transactionTypeId;
    private String transactionType;
    private String transactionDescription;
    
    public TransactionType() {}
    
    public TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

} //End Subclass TransactionType