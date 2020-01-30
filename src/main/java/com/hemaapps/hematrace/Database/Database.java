package com.hemaapps.hematrace.Database;

import com.hemaapps.hematrace.App;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @Course: Applied Software Practice - SDEV 450
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.Database
 * @Date: Jan 29, 2020
 * @Subclass Database Description: Handles all database related
 *           connections and JDBC queries.
 */
//Imports
//Begin Subclass Database
public class Database {
    
    final static Logger log = LoggerFactory.getLogger(Database.class);

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
    private boolean debug = true; // for debug mode
    private boolean logToFile = false; // for debugging, log to file or screen
    private boolean connectionStringSet = false; // do we have a connection string?

    public Database() {
    }

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

    public void setDbIpAddress(String dbIpAddress) {
        this.dbIpaddress = dbIpAddress;
    }
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isLogToFile() {
        return logToFile;
    }

    public void setLogToFile(boolean logToFile) {
        this.logToFile = logToFile;
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
