package com.hemaapps.hematrace.Model;

import java.util.Date;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 11, 2020
 * @Subclass BloodProductImpl Description: Implements the BloodProduct interface.
 */
//Imports

//Begin Subclass BloodProductImpl
public class BloodProductImpl implements BloodProduct{
    
    private int productId;
    private int baseFkId;
    private int productTypeId;
    private int productStatusId;
    private String donorNumber;
    private Date expirationDate;
    private Date obtainedDate;
    private String assignedLocation;
    private boolean isExpired;
    private boolean isExpiring;
    private boolean isCheckedIn;
    private boolean isCheckedOut;
    private boolean isValidDonorNumber;

    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public void setBaseFkId(int baseFkId) {
        this.baseFkId = baseFkId;
    }

    @Override
    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    public void setProductStatusId(int productStatusId) {
        this.productStatusId = productStatusId;
    }

    @Override
    public void setDonorNumber(String donorNumber) {
        this.donorNumber = donorNumber;
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public void setObtainedDate(Date obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    @Override
    public void setAssignedLocation(String location) {
        this.assignedLocation = location;
    }

    @Override
    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    @Override
    public void setIsExpiring(boolean isExpiring) {
        this.isExpiring = isExpiring;
    }

    @Override
    public void setIsCheckedIn(boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    @Override
    public void setIsValidDonorNumber(boolean isValidDonorNumber) {
        this.isValidDonorNumber = isValidDonorNumber;
    }

    @Override
    public int getProductId() {
        return this.productId;
    }

    @Override
    public int getBaseFKId() {
        return this.baseFkId;
    }

    @Override
    public int getProductTypeId() {
        return this.productTypeId;
    }

    @Override
    public int getProductStatusId() {
        return this.productStatusId;
    }

    @Override
    public String getDonorNumber() {
        return this.donorNumber;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public Date getObtainedDate() {
        return this.obtainedDate;
    }

    @Override
    public String getAssignedLocation() {
        return this.assignedLocation;
    }

    @Override
    public boolean getIsExpired() {
        return this.isExpired;
    }

    @Override
    public boolean getIsExpiring() {
        return this.isExpiring;
    }

    @Override
    public boolean getIsCheckedIn() {
        return this.isCheckedIn;
    }

    @Override
    public boolean getIsValidDonorNumber() {
        return this.isValidDonorNumber;
    }

    

} //End Subclass BloodProductImpl