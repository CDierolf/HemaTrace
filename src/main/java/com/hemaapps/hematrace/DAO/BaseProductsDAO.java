package com.hemaapps.hematrace.DAO;

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.Model.BloodProduct;
import com.hemaapps.hematrace.Model.PRBCImpl;
import com.hemaapps.hematrace.Model.PlasmaImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.DAO
 * @Date: Feb 4, 2020
 * @Subclass BaseProductsDAO Description: Singleton BaseProductsDAO class
 */
//Imports
//Begin Subclass BaseProductsDAO
public class BaseProductsDAO extends DatabaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseProductsDAO.class);

    private static BaseProductsDAO single_instance = null;
    private int baseId;
    private int maxNumProducts;
    private ResultSet resultSet;
    private List<PlasmaImpl> basePlasmaProducts = new ArrayList<>();
    private List<PRBCImpl> basePRBCProducts = new ArrayList<>();
    private static List<String> productDispositions = new ArrayList<>();
    private static HashMap<Integer, String> productTypeMap = new HashMap<>();
    //private List<BloodProduct> baseProducts = new ArrayList<>();

    private BaseProductsDAO() {
    }

    public static BaseProductsDAO getSingle_instance() {
        return single_instance;
    }

    public static void setSingle_instance(BaseProductsDAO single_instance) {
        BaseProductsDAO.single_instance = single_instance;
    }

    public static BaseProductsDAO getInstance() throws SQLException {
        if (single_instance == null) {
            log.info("BaseProductsDAO singleton initialized.");
            populateDispositions();
            populateProductTypes();
            single_instance = new BaseProductsDAO();

        }

        return single_instance;
    }

    public HashMap<Integer, String> getProductTypeMap() {
        return productTypeMap;
    }

    public void setProductTypeMap(HashMap<Integer, String> productTypeMap) {
        this.productTypeMap = productTypeMap;
    }
    
    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public void setMaxNumProducts(int maxNumProducts) {
        this.maxNumProducts = maxNumProducts;
    }

    public int getBaseId() {
        return this.baseId;
    }

    public int getMaxNumProducts() {
        return this.maxNumProducts;
    }

    public void setBaseBloodProductsResultSet(int baseId) {
        getBaseProductResultSet(baseId);
        parseBaseProductResultSet();

    }

    public List<PlasmaImpl> getBasePlasmaProducts() {
        return basePlasmaProducts;
    }

    public List<PRBCImpl> getBasePRBCProducts() {
        return basePRBCProducts;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * SQL - returns the base product result set from the 
     * base_product table given a base id
     * @param baseId 
     */
    private void getBaseProductResultSet(int baseId) {
        setBaseId(baseId);
        List<String> baseValues = new ArrayList<>();
        List<String> dataTypes = new ArrayList<>();
        DatabaseService db = new DatabaseService();

        String query = "{call [sp_retrieveBaseBloodProducts](?) }";
        baseValues.add(Long.toString(baseId));
        dataTypes.add("string");
        ResultSet rs = null;

        try {
            rs = db.callableStatementRs(query, baseValues.toArray(new String[baseValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            log.error("ERROR: ", ex);
        }

        this.setResultSet(rs);
    }

    /**
     * Parses through the retrieved base product result set and populates
     * the basePlasmaProducts and basePrbcProducts lists.
     */
    private void parseBaseProductResultSet() {
        basePlasmaProducts.clear();
        basePRBCProducts.clear();
        ResultSet rs = this.getResultSet();
        if (rs != null) {
            try {
                while (rs.next()) {

                    int productType = rs.getInt("product_type_id");

                    if (productType == 1) {
                        PlasmaImpl bloodProduct = new PlasmaImpl();
                        bloodProduct.setProductId(rs.getInt("product_id"));
                        bloodProduct.setBaseFkId((rs.getInt("base_id_fk")));
                        bloodProduct.setProductTypeId(productType);
                        bloodProduct.setProductStatusId(rs.getInt("product_status_id"));
                        bloodProduct.setDonorNumber(rs.getString("donor_number"));
                        bloodProduct.setExpirationDate(rs.getDate("expiration_date"));
                        bloodProduct.setObtainedDate(rs.getDate("obtained_datetime"));
                        bloodProduct.setIsExpiring(rs.getBoolean("is_expiring"));
                        bloodProduct.setIsExpired(rs.getBoolean("is_expired"));
                        basePlasmaProducts.add(bloodProduct);
                    }

                    if (productType == 2) {
                        PRBCImpl bloodProduct = new PRBCImpl();
                        bloodProduct.setProductId(rs.getInt("product_id"));
                        bloodProduct.setBaseFkId((rs.getInt("base_id_fk")));
                        bloodProduct.setProductTypeId(productType);
                        bloodProduct.setProductStatusId(rs.getInt("product_status_id"));
                        bloodProduct.setDonorNumber(rs.getString("donor_number"));
                        bloodProduct.setExpirationDate(rs.getDate("expiration_date"));
                        bloodProduct.setObtainedDate(rs.getDate("obtained_datetime"));
                        bloodProduct.setIsExpiring(rs.getBoolean("is_expiring"));
                        bloodProduct.setIsExpired(rs.getBoolean("is_expired"));
                        basePRBCProducts.add(bloodProduct);
                    }
                }
                maxNumProducts += basePRBCProducts.size();
                maxNumProducts += basePlasmaProducts.size();
            } catch (SQLException ex) {
                log.error("ERROR: ", ex);
            }
        } else {
            log.warn("Resultset was null.");
        }
        log.info("PRBC Products obtained for baseID: " + getBaseId()
                + ". " + basePRBCProducts.size() + " products retrieved.");
        log.info("PLASMA Products obtained for baseID: " + getBaseId() + ". "
                + basePlasmaProducts.size() + " products retrieved.");
    }

    /**
     * SQL - Returns the current number of products assigned to a given base.
     * @param base
     * @return
     * @throws SQLException
     * @throws ParseException 
     */
    public int getCurrentNumberOfProductsForBase(int base) throws SQLException, ParseException {
        List<String> inputs = new ArrayList<>();
        List<String> inputDataTypes = new ArrayList<>();
        DatabaseService db = new DatabaseService();
        String query = "{ call [sp_getCurrentNumProductsForBase](?,?) }";

        inputs.add(Long.toString(base));
        inputDataTypes.add("Int");
        int numProductsForBase = 0;

        numProductsForBase = db.callableStatementReturnInt(query,
                inputs.toArray(new String[inputs.size()]),
                inputDataTypes.toArray(new String[inputDataTypes.size()]));

        return numProductsForBase;

    }

    /**
     * SQL - Populates the disposition list from the database table lu_product_dispositions
     */
    private static void populateDispositions() {

        String query = "{call [sp_retrieveProductDispositions] }";
        ResultSet rs = null;
        DatabaseService db = new DatabaseService();

        try {
            rs = db.callableStatementRs(query, null, null);
        } catch (SQLException ex) {
            log.error("ERROR encountered attempting to populate the "
                    + "product disposition list: ", ex);
        }
        if (rs != null) {
            try {
                while (rs.next()) {
                    productDispositions.add(rs.getString("product_status"));
                }
            } catch (SQLException ex) {

            }
        }
    }
    
    /**
     * Populates the productTypeMap from the database.
     * @throws SQLException 
     */
    private static void populateProductTypes() throws SQLException {
        String query = "{call [sp_retrieveProductTypes] }";
        ResultSet rs = null;
        DatabaseService db = new DatabaseService();
        
        try {
            rs = db.callableStatementRs(query, null, null);
        } catch (SQLException ex) {
            log.error("ERROR encountered attempting to populate the "
                    + "product disposition list: ", ex);
        }
        
        if (rs != null) {
            try {
                while (rs.next()) {
                    productTypeMap.put(rs.getInt("product_type_id"), rs.getString("product_type".toLowerCase()));
                }
            } catch (SQLException ex) {
                log.error("ERROR encountered attempting to populate the "
                    + "product type map: ", ex);
            }
        }
    }

    public List<String> getProductDispositions() {
        return productDispositions;
    }

    public void setProductDispositions(List<String> productDispositions) {
        BaseProductsDAO.productDispositions = productDispositions;
    }
} //End Subclass BaseProductsDAO
