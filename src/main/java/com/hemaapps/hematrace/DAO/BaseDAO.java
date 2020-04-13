package com.hemaapps.hematrace.DAO;

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.Model.Base;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.DAO
 * @Date: Feb 11, 2020
 * @Subclass BaseDAO Description: Base Data Access Object - Singleton.
 */
//Imports
//Begin Subclass BaseDAO
public class BaseDAO {

    private static BaseDAO single_instance = null;
    private static int baseIdForInstance;
    private static String baseValue;
    private static int numBaseProducts;

    public static BaseDAO getSingle_instance() {
        return single_instance;
    }

    public static void setSingle_instance(BaseDAO single_instance) {
        BaseDAO.single_instance = single_instance;
    }

    private static ObservableList<Base> bases = FXCollections.observableArrayList();
    private static HashMap<String, Integer> baseMap = new HashMap<>();
    private static List<String> baseNames = new ArrayList<>();
    private static final DatabaseService db = new DatabaseService();
    private static final Logger log = LoggerFactory.getLogger(BaseDAO.class);

    private BaseDAO() {
    }

    public static BaseDAO getInstance() throws SQLException {
        if (single_instance == null) {
            log.info("BaseDAO singleton initialized.");
            single_instance = new BaseDAO();
            populateBaseList();
            populateBaseMap();
            populateBaseNames();
        }

        return single_instance;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(String baseValue) {
        BaseDAO.baseValue = baseValue;
    }

    public int getBaseIdForInstance() {
        return baseIdForInstance;
    }

    public void setBaseIdForInstance(int baseIdForInstance) {
        BaseDAO.baseIdForInstance = baseIdForInstance;
    }

    public List<Base> getBases() {
        return this.bases;
    }

    public List<String> getBaseNames() {
        return this.baseNames;
    }

    public HashMap<String, Integer> getBaseMap() {
        return this.baseMap;
    }

    /**
     * Singleton Instance - when the base name is selected at login
     * set the base Id.
     * @param baseName
     * @return 
     */
    public int getBaseIdFromMapWithBaseNameSetInstance(String baseName) {
        if (baseMap.containsKey(baseName)) {
            this.setBaseIdForInstance(baseMap.get(baseName));
            return baseMap.get(baseName);
        } else {
            return 0;
        }
    }
    /**
     * Retrieve the base Id given the base name
     * @param baseName
     * @return 
     */
    public int getBaseIdFromMap(String baseName) {
        if (baseMap.containsKey(baseName.toLowerCase())) {
            return baseMap.get(baseName.toLowerCase());
        } else {
            return 0;
        }
    }

    /**
     * Retrieve the base data given the base Id
     * @param baseId
     * @return 
     */
    public Base getBaseInfoFromList(int baseId) {
        Base base = null;
        for (Base b : bases) {
            if (b.getBase_id() == baseId) {
                base = b;
                break;
            }
        }
        return base;
    }

    /**
     * Populate the base list.
     * @throws SQLException 
     */
    private static void populateBaseList() throws SQLException {
        db.init();
        String query = "{ call [sp_retrieveBaseResultSet] }";
        ResultSet rs = null;
        rs = db.callableStatementRs(query);
        if (rs != null) {
            try {
                while (rs.next()) {
                    try {
                        Base base = new Base();
                        base.setBase_id(rs.getInt("base_id"));
                        base.setName(rs.getString("name"));
                        base.setAddress(rs.getString("address"));
                        base.setCity(rs.getString("city"));
                        base.setState(rs.getString("state"));
                        base.setZipCode(rs.getString("zipcode"));
                        bases.add(base);
                    } catch (SQLException ex) {
                        log.error("Inner Exception Error: An error occured parsing the resultset of bases returned from the database.", ex);
                    }
                }
            } catch (SQLException ex) {
                log.error("Outer Exception: An error occured parsing the resultset of bases returned from the database.", ex);
            }
        } else {
            log.error("No bases were returned from the database.");
        }
    }

    /**
     * SQL call to update base information
     * @param base
     * @return
     * @throws SQLException 
     */
    public boolean updateBaseInfo(Base base) throws SQLException, ParseException {
        ArrayList<String> tValues = new ArrayList<>();
        ArrayList<String> tTypes = new ArrayList<>();
        int successfulUpdate;

        tValues.add(Integer.toString(base.getBase_id()));
        tTypes.add("int");
        tValues.add(base.getName());
        tTypes.add("string");
        tValues.add(base.getAddress());
        tTypes.add("string");
        tValues.add(base.getCity());
        tTypes.add("string");
        tValues.add(base.getState());
        tTypes.add("string");
        tValues.add(base.getZipCode());
        tTypes.add("String");
        log.info("BaseDAO singleton refreshed...");

        db.init();
        String q1 = "{ call [sp_updatebase](?,?,?,?,?,?,?) }";

        successfulUpdate = db.callableStatementReturnInt(q1, tValues.toArray(new String[tValues.size()]),
                tTypes.toArray(new String[tTypes.size()]));

        return successfulUpdate == 0;
    }

    /**
     * Populates the base map
     */
    private static void populateBaseMap() {
        for (Base b : bases) {
            baseMap.put(b.getName().toLowerCase().trim(), b.getBase_id());
        }
    }

    /**
     * Populates the list of base names
     */
    private static void populateBaseNames() {
        for (Base b : bases) {
            baseNames.add(b.getName());
        }
    }
    public int getNumBaseProducts() {
        return numBaseProducts;
    }

    public void setNumBaseProducts(int numBaseProducts) {
        BaseDAO.numBaseProducts = numBaseProducts;
    }
} //End Subclass BaseDAO
