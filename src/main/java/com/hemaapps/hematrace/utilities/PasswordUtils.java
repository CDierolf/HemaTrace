package com.hemaapps.hematrace.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.UtilityClasses
 * @Date: Nov 25, 2019
 * @Subclass PasswordUtils Description: 
 */
//Imports

//Begin Subclass PasswordUtils
public class PasswordUtils {
    public static String getHashedPassword(String pw) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //Add password bytes to digest
        md.update(pw.getBytes());
        //Get the hash's bytes 
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        String encryptedPassword = sb.toString();        
        return encryptedPassword;
    }

} //End Subclass PasswordUtils