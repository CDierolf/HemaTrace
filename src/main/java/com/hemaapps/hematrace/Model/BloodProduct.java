/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.Model;

import java.util.Date;

/**
 *
 * @author pis7ftw
 */
public interface BloodProduct {
    
    public void setProductId(int productId);
    public void setBaseFkId(int baseFkId);
    public void setProductTypeId(int productTypeId);
    public void setProductStatusId(int productStatusId);
    public void setDonorNumber(String donorNumber);
    public void setExpirationDate(Date expirationDate);
    public void setObtainedDate(Date obtainedDate);
    public void setAssignedLocation(String location);
    public void setIsExpired(boolean isExpired);
    public void setIsExpiring(boolean isExpiring);
    public void setIsCheckedIn(boolean isCheckedIn);
    public void setIsValidDonorNumber(boolean isValidDonorNumber);
    public int getProductId();
    public int getBaseFKId();
    public int getProductTypeId();
    public int getProductStatusId();
    public String getDonorNumber();
    public Date getExpirationDate();
    public Date getObtainedDate();
    public String getAssignedLocation();
    public boolean getIsExpired();
    public boolean getIsExpiring();
    public boolean getIsCheckedIn();
    public boolean getIsValidDonorNumber();
    
}
