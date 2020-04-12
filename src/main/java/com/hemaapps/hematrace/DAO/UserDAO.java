package com.hemaapps.hematrace.DAO;

//Begin Subclass UserDAO
import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.LoginViewControllers.AdminLoginViewController;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.PasswordUtilities;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Java 450 ~ Java Programming III
 *
 * @author Christopher Dierolf User data access object
 */
public class UserDAO {

    private static final Logger log = LoggerFactory.getLogger(AdminLoginViewController.class);
    private static DatabaseService db = new DatabaseService();
    private static PasswordUtilities passwordUtilties = null;

    /**
     * Logs in the user Ensures that the user name exists and that the hashed
     * password matches the given username.
     *
     * @param username
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     * @throws ParseException
     */
    public static long loginUser(String username, String password) throws NoSuchAlgorithmException, SQLException, ParseException {
        db.init();
        String Q1 = "{call sp_loginUser(?,?,?) }";
        int loggedin = 0;
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

        try {
            loggedin = db.callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            log.error("An error occured loging in user: " + username);
            log.error(ex.getMessage());
            log.error(ex.getStackTrace().toString());
        }

        return loggedin;
    }

    /**
     * Inserts a new user into the user table
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public static boolean insertUser(User user) throws SQLException {
        int successfulUserEntry;
        ArrayList<String> tValues = new ArrayList<>();
        ArrayList<String> tTypes = new ArrayList<>();
        tValues.add(user.getFirstName());
        tTypes.add("string");
        tValues.add(user.getLastName());
        tTypes.add("string");
        tValues.add(user.getEmail());
        tTypes.add("string");
        tValues.add(user.getUsername());
        tTypes.add("string");
        tValues.add(user.getPassword());
        tTypes.add("string");
        tValues.add(user.getCrewId());
        tTypes.add("string");
        String q1 = "{call [sp_insertUser](?,?,?,?,?,?,?)}";

        successfulUserEntry = db.callableStatementReturnInt(q1, tValues.toArray(new String[tValues.size()]),
                tTypes.toArray(new String[tTypes.size()]));

        return successfulUserEntry == 1;

    }

    /**
     * Validates a users crewId when scanning blood products
     *
     * @param userId
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static boolean validateCrewUser(String userId) throws SQLException, ParseException {
        String q1 = "{call [sp_validateAndRetrieveUserBasedOnCrewId](?,?)}";
        ArrayList<String> userValues = new ArrayList<>();
        ArrayList<String> dataTypes = new ArrayList<>();

        userValues.add(userId);
        dataTypes.add("string");

        int validUser = db.callableStatementReturnInt(q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        return validUser > 0;
    }

    /**
     * Returns an observable list of users.
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> getUsers() throws SQLException {
        String q1 = "{call [sp_retrieveUsers]}";
        ObservableList<User> userList = FXCollections.observableArrayList();
        ResultSet rs = null;
        try {
            rs = db.callableStatementRs(q1);
        } catch (SQLException ex) {
            System.out.println("An error occured: " + ex);
        }

        if (rs != null) {
            try {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(Integer.toString(rs.getInt("user_id")));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setCrewId(Integer.toString(rs.getInt("crewId")));

                    userList.add(user);
                }
            } catch (SQLException ex) {
                System.out.println("An exception occured: " + ex);
                log.error(ex.toString());
            }
        }
        return userList;
    }

    public static boolean updateUser(User user) throws SQLException {
        int successfulUserUpdate = 0;
        ArrayList<String> tValues = new ArrayList<>();
        ArrayList<String> tTypes = new ArrayList<>();
        tValues.add(user.getUserId());
        tTypes.add("int");
        tValues.add(user.getFirstName());
        tTypes.add("string");
        tValues.add(user.getLastName());
        tTypes.add("string");
        tValues.add(user.getEmail());
        tTypes.add("string");
        tValues.add(user.getUsername());
        tTypes.add("string");
        tValues.add(user.getCrewId());
        tTypes.add("string");
        String q1 = "{call [sp_updateUser](?,?,?,?,?,?,?)}";

        try {
            successfulUserUpdate = db.callableStatementReturnInt(q1, tValues.toArray(new String[tValues.size()]),
                    tTypes.toArray(new String[tTypes.size()]));
        } catch (SQLException ex) {
            log.error("An error occured updating user: " + user.toString());
            log.error(ex.getMessage());
            log.error(ex.getStackTrace().toString());
        }

        return successfulUserUpdate == 1;
    }

    public static boolean deleteUser(String userId) throws SQLException {
        int successfulUserDelete = 0;
        ArrayList<String> tValues = new ArrayList<>();
        ArrayList<String> tTypes = new ArrayList<>();
        tValues.add(userId);
        tTypes.add("int");

        String q1 = "{call [sp_deleteUser](?,?)}";

        try {
            successfulUserDelete = db.callableStatementReturnInt(q1, tValues.toArray(new String[tValues.size()]),
                    tTypes.toArray(new String[tTypes.size()]));
        } catch (SQLException ex) {
            log.error("An error occured deleting userId: " + userId);
            log.error(ex.getMessage());
            log.error(ex.getStackTrace().toString());
        }

        return successfulUserDelete == 1;
    }

} //End Subclass UserDAO
