/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.utilities.FormUtils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class RecentTransactionsViewController implements Initializable {

    @FXML
    private TableView transactionTableView;
    @FXML
    private Button closeButton;
    
    
    private BaseTransactionDAO baseTransactionDAO;
    private List<Transaction> transactionList = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseTransactionDAO = new BaseTransactionDAO();
            initData();
        } catch (SQLException ex) {
            Logger.getLogger(RecentTransactionsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void initData() throws SQLException {

        getTransactionData();
        adjustTableView();
        addTableViewData();
    }

    /**
     * Retrieve the data from the database using the BaseTransactionDAO object
     * into a list object.
     *
     * @throws SQLException
     */
    private void getTransactionData() throws SQLException {
        baseTransactionDAO.setAllTransactions();
        this.transactionList = baseTransactionDAO.getTransactionList();
    }

    /**
     * Modifies global settings for the BaseDataTransactionTableView transactionTableView
     */
    private void adjustTableView() {
        transactionTableView.setEditable(false);
        transactionTableView.setStyle("-fx-alignment: CENTER");
    }

    /**
     * Adds live data to the BaseDashboardView table view.
     *
     */
    private void addTableViewData() {
        TableColumn<Integer, Transaction> transIdColumn = new TableColumn<>("Transaction Id");
        TableColumn<Integer, Transaction> prodIdColumn = new TableColumn<>("Product Id");
        TableColumn<String, Transaction> baseColumn = new TableColumn<>("Base");
        TableColumn<String, Transaction> crewmemberColumn = new TableColumn<>("Crewmember");
        TableColumn<String, Transaction> donorNumberColumn = new TableColumn<>("Donor Number");
        TableColumn<String, Transaction> productTypeColumn = new TableColumn<>("Product Type");
        TableColumn<String, Transaction> transTypeColumn = new TableColumn<>("Transaction Type");
        TableColumn<Date, Transaction> transDateColumn = new TableColumn<>("Transaction Date/Time");

        transIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        baseColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        crewmemberColumn.setCellValueFactory(new PropertyValueFactory<>("crewmember"));
        donorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("donorNumber"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        transTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDateTime"));
        transactionTableView.getColumns().addAll(transIdColumn, prodIdColumn, baseColumn,
                crewmemberColumn, donorNumberColumn, productTypeColumn, transTypeColumn,
                transDateColumn);

        for (Transaction t : transactionList) {
            transactionTableView.getItems().add(t);
        }

    }
    
    public void handleCloseButtonClicked() {
        FormUtils.closeWindow((Stage) this.closeButton.getScene().getWindow());
    }
    
}
