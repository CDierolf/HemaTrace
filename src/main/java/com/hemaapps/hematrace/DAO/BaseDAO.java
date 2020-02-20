package com.hemaapps.hematrace.DAO;

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.Model.Base;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.DAO
 * @Date: Feb 11, 2020
 * @Subclass BaseDAO Description:
 */
//Imports
//Begin Subclass BaseDAO
public class BaseDAO {

    private static BaseDAO single_instance = null;
    private static int baseIdForInstance;
    private static String baseValue;

    

    private static List<Base> bases = new ArrayList<>();
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

    public int getBaseIdFromMapWithBaseName(String baseName) {
        if (baseMap.containsKey(baseName)) {
            this.setBaseIdForInstance(baseMap.get(baseName));
            this.setBaseValue(baseName);
            
            return baseMap.get(baseName);
        } else {
            return 0;
        }
    }

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

    private static void populateBaseMap() {
        for (Base b : bases) {
            baseMap.put(b.getName().toLowerCase().trim(), b.getBase_id());
        }
    }

    private static void populateBaseNames() {
        for (Base b : bases) {
            baseNames.add(b.getName());
        }
    }

} //End Subclass BaseDAO
