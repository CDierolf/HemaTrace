package com.hemaapps.hematrace.DAO;

//Begin Subclass UserDAO

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.utilities.PasswordUtilities;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    
    private static DatabaseService db = new DatabaseService();
    private static PasswordUtilities passwordUtilties = null;
    
    
    public static long loginUser(String username, String password) throws NoSuchAlgorithmException, SQLException {
        db.init();
        String Q1 = "{call sp_loginUser(?,?,?) }";
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        ArrayList<String> userValues = new ArrayList<>();
        ArrayList<String> dataTypes = new ArrayList<>();

        userValues.add(username);
        dataTypes.add("string");
        // Hash the password -- compare the hashed password
        String hashedPassword = "";
        passwordUtilties = new PasswordUtilities(password);
        hashedPassword = passwordUtilties.getHashedPassword();
        System.out.println(hashedPassword);
        userValues.add(hashedPassword);
        dataTypes.add("string");

        int loggedin = db.callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        return loggedin;
    }
    
    //TODO
    public static boolean validateCrewUser(String userId) {
        return true;
    }

} //End Subclass UserDAO