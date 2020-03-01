package com.hemaapps.hematrace.Model;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 4, 2020
 * @Subclass User Description: 
 */
//Imports

//Begin Subclass User
public class User {
    private String userId;
    
    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    

} //End Subclass User