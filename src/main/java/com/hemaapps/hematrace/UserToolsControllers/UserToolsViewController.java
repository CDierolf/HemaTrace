/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.UserToolsControllers;

import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.Model.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class UserToolsViewController implements Initializable {

    @FXML private TableView userTableView;
    
    
    private List<User> userList = new ArrayList<User>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // get list of users from db
       // populate table view with list of users
    }    
    
    /**
     * Modifies global settings for the BaseDataTransactionTableView transactionTableView
     */
    private void adjustTableView() {
        userTableView.setEditable(false);
        userTableView.setStyle("-fx-alignment: CENTER");
    }

    /**
     * Adds live data to the BaseDashboardView table view.
     *
     */
    private void addTableViewData() {
        TableColumn<Integer, Transaction> dbUserIdColumn = new TableColumn<>("Database User ID");
        TableColumn<String, Transaction> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<String, Transaction> lastNameColumn = new TableColumn<>("Last Name");
        TableColumn<String, Transaction> emailColumn = new TableColumn<>("E-Mail");
        TableColumn<String, Transaction> usernameColumn = new TableColumn<>("UserName");
        TableColumn<String, Transaction> userIdColumn = new TableColumn<>("UserID");

        dbUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("crewmember"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("donorNumber"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        userTableView.getColumns().addAll(dbUserIdColumn, firstNameColumn, lastNameColumn,
                emailColumn, userIdColumn);

        for (User u : userList) {
            userTableView.getItems().add(u);
        }

    }
    
}
