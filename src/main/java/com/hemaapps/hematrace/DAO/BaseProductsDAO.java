package com.hemaapps.hematrace.DAO;

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.Model.BloodProduct;
import com.hemaapps.hematrace.Model.BloodProductImpl;
import com.hemaapps.hematrace.Model.PRBCImpl;
import com.hemaapps.hematrace.Model.PlasmaImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 350 ~ Java Programming II
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
    private List<List<BloodProduct>> combinedProducts = new ArrayList<>();
    private static List<String> productDispositions = new ArrayList<>();
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
            single_instance = new BaseProductsDAO();

        }

        return single_instance;
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
        
        System.out.println("DISPOSITION LIST SIZE: " + productDispositions.size());

    }

    public List<String> getProductDispositions() {
        return productDispositions;
    }

    public void setProductDispositions(List<String> productDispositions) {
        BaseProductsDAO.productDispositions = productDispositions;
    }
} //End Subclass BaseProductsDAO
