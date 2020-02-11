package com.hemaapps.hematrace.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @Course: Applied Software Practice - SDEV 450
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Database
 * @Date: Jan 29, 2020
 * @Subclass Database Description: Handles all database related connections and
 * JDBC queries.
 */
//Imports
//Begin Subclass Database
public class DatabaseService {

    final static Logger log = LoggerFactory.getLogger(DatabaseService.class);

    private String connectionString = "";

    private String dbIpaddress;
    private String dbUsername;
    private String dbPassword;
    private String dbName;
    private String dbPort;
    private BasicDataSource connectionPool = new BasicDataSource();
    private Connection connection;
    private Statement stmt;
    private ResultSet rs;
    private boolean connectionStringSet = false; // do we have a connection string?

    public void init() {
        if (!connectionStringSet) {
            loadProperties();
            buildConnectionString();
            connectionPool.setUsername(getDbUsername());
            connectionPool.setPassword(getDbPassword());
            connectionPool.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connectionPool.setUrl("jdbc:sqlserver://" + getDbIpaddress() + ":"
                    + getDbPort() + ";databaseName=" + getDbName());
            connectionPool.setInitialSize(1);
            connectionStringSet = true;
            try {
                if (connection == null) {
                    connection = connectionPool.getConnection();
                    log.info("Database connection successful.");
                }
            } catch (SQLException ex) {
                log.error(String.format("Database connection failed with message %s.", ex.getMessage()));
                System.out.println(ex.getMessage());
            }
        }
    }

    public void loadProperties() {

        try (InputStream input = new FileInputStream("src/main/resources/properties/database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            setDbIpAddress(prop.getProperty("dbIpaddress"));
            setDbUsername(prop.getProperty("dbUsername"));
            setDbPassword(prop.getProperty("dbPassword"));
            setDbName(prop.getProperty("dbName"));
            setDbPort(prop.getProperty("dbPort"));
            log.info("Database properties successfully loaded.");
        } catch (IOException ex) {
            log.error(String.format("Database properties failed to load: %s.", ex.getMessage()));
        }

    }

    private void buildConnectionString() {
        //loadProperties();
        connectionString = "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s";
        connectionString = String.format(connectionString, dbIpaddress, dbPort,
                dbName, dbUsername, dbPassword);

    }

    /**
     * Return ResultSet from stored procedure
     *
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet callableStatementRs(String query) throws SQLException {
        try {
            if (connection == null) {
                connection = connectionPool.getConnection();
            }
            CallableStatement cs = null;
            cs = connection.prepareCall(query);
            rs = cs.executeQuery();
        } catch (SQLException ex) {
            log.error(String.format("Prepared statement: %s failed with message: %s.", query, ex.getMessage()));
        }

        return rs;
    }

    public int callableStatementReturnInt(String query, String[] inputs,
            String[] inputDataTypes) throws SQLException {
        init();
        
        CallableStatement cs = null;
        int returnValue = 0;
        try {
            if(connection == null)
                connection = connectionPool.getConnection();

            cs = connection.prepareCall(query);
            // This section sets up parameters for the query from the arguments            
            for (int i = 0; i < inputs.length; i++) {
                if ("int".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setInt(i+1, Integer.parseInt(inputs[i]));
                } else if ("bit".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setBoolean(i+1, Boolean.parseBoolean(inputs[i]) );
                } else if ("money".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setDouble(i+1, Double.parseDouble(inputs[i]) );
                } else if ("string".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setString(i+1, inputs[i]);
                } else if ("date".equalsIgnoreCase(inputDataTypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    cs.setDate(i+1, java.sql.Date.valueOf(inputs[i]));
                } else if ("time".equalsIgnoreCase(inputDataTypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("HH:mm:ss");
                    cs.setDate(i+1, java.sql.Date.valueOf(inputs[i]));
                } else if ("datetime".equalsIgnoreCase(inputDataTypes[i])) {
                    java.util.Date result;
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse (inputs[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());
                    cs.setDate(i+1, sqlDate);
                } // other data types
            }
            cs.registerOutParameter(inputs.length+1, java.sql.Types.INTEGER);
            cs.execute();
            returnValue = cs.getInt(inputs.length+1);
            close();
        } catch (Exception e) {
            //String module, String query, Boolean exit, String error
            e.printStackTrace();
            log.error("callableStatement", query, true, e.getMessage());
        }
        return returnValue;

    }

    public ResultSet callableStatementRs(String query, String[] inputs,
            String[] inputDataTypes) throws SQLException {

        CallableStatement cs = null;
        init();

        try {
            if (connection == null) {
                connection = connectionPool.getConnection();
            }

            cs = connection.prepareCall(query);
            // This section sets up parameters for the query from the arguments            
            for (int i = 0; i < inputs.length; i++) {
                if ("int".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setInt(i + 1, Integer.parseInt(inputs[i]));
                } else if ("bit".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setBoolean(i + 1, Boolean.parseBoolean(inputs[i]));
                } else if ("money".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setDouble(i + 1, Double.parseDouble(inputs[i]));
                } else if ("string".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setString(i + 1, inputs[i]);
                } else if ("date".equalsIgnoreCase(inputDataTypes[i])) {
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d");
                    cs.setDate(i + 1, java.sql.Date.valueOf(inputs[i]));
                } else if ("time".equalsIgnoreCase(inputDataTypes[i])) {
                    cs.setDate(i + 1, Date.valueOf(inputs[i]));
                } else if ("datetime".equalsIgnoreCase(inputDataTypes[i])) {
                    java.util.Date result;
                    SimpleDateFormat d = new SimpleDateFormat("y-M-d HH:mm:ss");//2018-09-18 11:09:44
                    result = d.parse(inputs[i]);
                    java.sql.Date sqlDate = new java.sql.Date(result.getTime());

                    cs.setDate(i + 1, sqlDate);
                } // other data types
            }
            rs = cs.executeQuery();

            return rs;
        } catch (Exception e) {
            log.error("Error!: ", e);
        } 
        return rs;
    }
    
    private void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                log.error("close", "", true, e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                log.error("close", "", true, e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("close", e.getStackTrace());
            }
        }
    }

    public void setDbIpAddress(String dbIpAddress) {
        this.dbIpaddress = dbIpAddress;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDbIpaddress() {
        return dbIpaddress;
    }

    public void setDbIpaddress(String dbIpaddress) {
        this.dbIpaddress = dbIpaddress;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public BasicDataSource getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnectionStringSet() {
        return connectionStringSet;
    }

    public void setConnectionStringSet(boolean connectionStringSet) {
        this.connectionStringSet = connectionStringSet;
    }

} //End Subclass Database
