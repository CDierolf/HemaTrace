package com.hemaapps.hematrace.DAO;

import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.Model.TransactionType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.DAO
 * @Date: Feb 13, 2020
 * @Subclass BaseTransactionDAO Description:
 */
//Imports
//Begin Subclass BaseTransactionDAO
public class BaseTransactionDAO {

    private final static Logger log = LoggerFactory.getLogger(BaseTransactionDAO.class);

    private int baseId;
    private ResultSet transactionResultSet;
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    private List<TransactionType> transactionTypeList = new ArrayList<>();

    public List<TransactionType> getTransactionTypeList() throws SQLException {
        if (transactionTypeList.size() <= 0) {
            retrieveTransactionTypes();
        }
        return transactionTypeList;
    }

    
    public void setTransactions() throws SQLException {
        this.getTransactions(this.getBaseId());
        this.parseTransactionResultSet();
    }
    
    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }
    public int getBaseId() {
        return this.baseId;
    }
    public void setTransactionResultSet(ResultSet rs) {
        this.transactionResultSet = rs;
    }
    public ResultSet getTransactionResultSet() {
        return this.transactionResultSet;
    }
    public int getTransactionListSize() {
        return this.transactionList.size();
    }
    public ObservableList<Transaction> getTransactionList(int baseId) {
        this.setBaseId(baseId);
        getTransactions(this.getBaseId());
        if (transactionList != null) {
            return this.transactionList;
        } else {
            return null;
        }
    }
    public ObservableList<Transaction> getTransactionList() {
        return this.transactionList;
    }

    private void getTransactions(int baseId) {
        setBaseId(baseId);
        List<String> baseValues = new ArrayList<>();
        List<String> dataTypes = new ArrayList<>();
        DatabaseService db = new DatabaseService();

        String query = "{call [sp_retrieveTransactionDataForBase](?) }";
        baseValues.add(Long.toString(baseId));
        dataTypes.add("string");
        ResultSet rs = null;

        try {
            rs = db.callableStatementRs(query, baseValues.toArray(new String[baseValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            log.error("ERROR: ", ex);
        }

        this.setTransactionResultSet(rs);
    }

    private void parseTransactionResultSet() throws SQLException {
        ResultSet rs = this.getTransactionResultSet();
        if (rs != null) {
            log.info("Parsing transaction resultset.");
            try {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setProductId(rs.getInt("product_id"));
                    transaction.setBase(rs.getString("name"));
                    transaction.setCrewmember(rs.getString("crew_member"));
                    transaction.setDonorNumber(rs.getString("donor_number"));
                    transaction.setProductType(rs.getString("product_type"));
                    transaction.setTransactionType(rs.getString("transaction_type"));
                    transaction.setTransactionDateTime(rs.getDate("transaction_datetime"));

                    transactionList.add(transaction);
                }
            } catch (Exception ex) {
                log.error("An exception occured parsing the transaction resultset. ", ex);
            }

        } else {
            log.warn("Transaction list was null. Nothing retrieved from the database.");
        }

        log.info("transactions retrieved for baseID: " + getBaseId()
                + ". " + getTransactionListSize() + " transactions retrieved.");

    }
    
    private void retrieveTransactionTypes() throws SQLException {
        DatabaseService db = new DatabaseService();
        db.init();
        String query = "{call [sp_retrieveTransactionTypes] }";
        ResultSet rs = null;

        try {
            rs = db.callableStatementRs(query);
        } catch (SQLException ex) {
            log.error("An error occurred attempting to retrieve"
                    + "the transactiontypes from the database.: ", ex);
        }
        parseTransactionTypeResultSet(rs);
    }
    
    private void parseTransactionTypeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            log.info("Parsing transaction type result set.");
            while (rs.next()) {
                TransactionType transactionType = new TransactionType();
                transactionType.setTransactionTypeId(rs.getInt("transaction_type_id"));
                transactionType.setTransactionType(rs.getString("transaction_type"));
                transactionType.setTransactionDescription(rs.getString("description"));
                transactionTypeList.add(transactionType);
            }
            log.info("Transaction type list retrieved successfully: " + transactionTypeList.size() + " items retrieved.");
        } else {
            log.error("Unable to parse the transaction type resultset.");
        }
        
    }
    
    public void insertTransaction(Transaction transaction) {
        
    }

} //End Subclass BaseTransactionDAO
