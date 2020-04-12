/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.UserToolsControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.DashboardViewControllers.AdminToolsViewController;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.FormUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class UserToolsViewController implements Initializable {

    @FXML
    private TableView userTableView;
    @FXML
    private Button addUserButton;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane newUserAnchorPane;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private UserDAO userDao;
    private AdminToolsViewController adminToolsViewController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.userList = UserDAO.getUsers();
            adjustTableView();
            addTableViewData();
        } catch (SQLException ex) {
            Logger.getLogger(UserToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maintain an instance of the AdminToolsViewController in order to change
     * the dynamicViewPane's scenes
     *
     * @param adminToolsViewController
     */
    public void setAdminToolsController(AdminToolsViewController adminToolsViewController) {
        this.adminToolsViewController = adminToolsViewController;
    }

    /**
     * Modifies global settings for the BaseDataTransactionTableView
     * transactionTableView
     */
    private void adjustTableView() {
        userTableView.setEditable(false);
        userTableView.setStyle("-fx-alignment: CENTER");
        addTableViewHandler();
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

        dbUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("crewId"));
        userTableView.getColumns().addAll(dbUserIdColumn, firstNameColumn, lastNameColumn,
                emailColumn, userIdColumn);

        for (User u : userList) {
            userTableView.getItems().add(u);
        }
    }

    /**
     * Accesses the AdminToolsViewController's dynamicViewPane to alter the
     * scenes
     *
     * @throws IOException
     */
    public void handleAddUserButtonClicked() throws IOException {
        closeUserToolsView();
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../NewUserView.fxml"));
        this.newUserAnchorPane = loader.load();
        NewUserViewController controller = loader.getController();
        controller.setAdminToolsViewController(this.adminToolsViewController);
        this.adminToolsViewController.dynamicViewPane.getChildren().add(newUserAnchorPane);

    }

    private void addTableViewHandler() {
        this.userTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (userTableView.getSelectionModel().getSelectedItem() != null) {
                    TableViewSelectionModel selectionModel = userTableView.getSelectionModel();
                    User user = (User)selectionModel.getSelectedItem();
                    try {
                        handleUserSelectedClick(user);
                    } catch (IOException ex) {
                        Logger.getLogger(UserToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void handleCloseButtonClicked() {
        FormUtils.closeWindow((Stage) this.closeButton.getScene().getWindow());
    }

    private void closeUserToolsView() {
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
    }
    
    private void handleUserSelectedClick(User user) throws IOException {
        closeUserToolsView();
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../EditUserView.fxml"));
        this.newUserAnchorPane = loader.load();
        EditUserViewController controller = loader.getController();
        controller.setSelectedUser(user);
        controller.setAdminToolsViewController(this.adminToolsViewController);
        controller.setUserToolsViewController(this);
        this.adminToolsViewController.dynamicViewPane.getChildren().add(newUserAnchorPane);
    }
    
    public void updateUserList() throws SQLException {
        this.userList.clear();
        this.userList = UserDAO.getUsers();
    }

}
