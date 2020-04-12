package com.hemaapps.hematrace.Model;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Model
 * @Date: Feb 4, 2020
 * @Subclass User Description:  User Object
 */
//Imports

//Begin Subclass User
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String userId;
    private String crewId;
    
    public User() {}
    public User(String userId) {
        this.userId = userId;
    }
    public User(String firstName, String lastName, String email, String username, String password, String userId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getCrewId() {
        return crewId;
    }
    public void setCrewId(String crewId) {
        this.crewId = crewId;
    }
    
    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", username=" + username + ", password=" + password + ", userId=" + userId + ", crewId=" + crewId + '}';
    }
    
    
    
    

} //End Subclass User