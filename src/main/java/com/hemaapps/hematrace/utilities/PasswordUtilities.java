package com.hemaapps.hematrace.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtilities {
    
    private final static Logger log = LoggerFactory.getLogger(PasswordUtilities.class);
    
    private String hashedPassword;
    
    
    private PasswordUtilities(){}
    public PasswordUtilities(String passwordToHash) {
        try {
            setHashedPassword(getHashedPassword(passwordToHash));
        } catch (NoSuchAlgorithmException ex) {
            log.error("Exception encountered hashing password", ex);
        }
    }
    
    private void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
    public String getHashedPassword() {
        return this.hashedPassword;
    }
    
    
    public String getHashedPassword(String pw) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
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
    
} //End Subclass PasswordUtilities