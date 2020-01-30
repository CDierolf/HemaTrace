/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.LoginViewControllers;

import com.hemaapps.hematrace.Database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class BaseLoginViewController implements Initializable {

    private final String title = "Blood Product Tracking Application";

    @FXML
    private ImageView logo;
    @FXML
    private ComboBox baseComboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button adminLoginButton;
    
    Database db = new Database();

    //Database db = new Database();
    ArrayList<String> bases = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            getBases();
            bases.forEach((s) -> {
                baseComboBox.getItems().add(s);
            });
        } catch (SQLException ex) {
            Logger.getLogger(BaseLoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve the list of base names from the database
     * @throws SQLException 
     */
    private void getBases() throws SQLException {
        db.init();
        String query = "{ call [sp_retrieveBaseNames] }";
        ResultSet rs = null;
        rs = db.callableStatementRs(query);

        if (rs != null) {
            try {
                while (rs.next()) {
                    try {
                        bases.add(rs.getString("name"));
                    } catch (SQLException ex) {
                        Logger.getLogger(BaseLoginViewController.class.getName()).log(Level.SEVERE, "Inner Exception", ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(BaseLoginViewController.class.getName()).log(Level.SEVERE, "Outter Exception", ex);
            }
        } else {
            Logger.getLogger(BaseLoginViewController.class.getName()).log(Level.SEVERE, "No bases retrieved from database.");
        }
    }

    /**
     * Method to switch to the AdminLoginView scene.
     *
     * @param event
     * @throws IOException
     */
    public void handleAdminLoginButtonClicked(ActionEvent event) throws IOException {

        // Gotta go up one level to access resources
        FXMLLoader loader = new FXMLLoader(BaseLoginViewController.class.getResource("../AdminLoginView.fxml"));
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent baseDashboardViewParent = loader.load();
        Scene baseDashboardView = new Scene(baseDashboardViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(baseDashboardView);
        window.setTitle(title + "Administrator Login");
        window.setResizable(false);
        window.show();

    }

    /**
     * Method to login into the Base Dashboard
     *
     * @param event
     * @throws IOException
     */
    public void handleBaseLoginButtonClicked(ActionEvent event) throws IOException {

        // Ensure that a base is selected.
        if (baseComboBox.getValue() != null) {
            String baseValue = baseComboBox.getValue().toString();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/DashboardViews/BaseDashboardView/BaseDashboardView.fxml"));
            Parent detailedPersonViewParent = loader.load();
            Scene detailedViewScene = new Scene(detailedPersonViewParent);

            // Get PersonViewController
            //BaseDashboardViewController controller = loader.getController();
            // Pass data into the controller
            //controller.initData(baseComboBox.getValue().toString());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(detailedViewScene);
            window.setTitle(title + baseValue + " Dashboard");
            window.setResizable(false);
            window.show();

        } else {
//            alerts = new AlertsClass(AlertType.ERROR, "Base Selection is Blank",
//                    "Please select a base to login.", "In order to proceed, please ensure"
//                    + " that a base is selected.");
//            System.out.println("wtf");
//            alerts.showGenericAlert();
            baseComboBox.requestFocus();
        }

    }

    /**
     * Handles the close button clicked action - closes the application.
     *
     * @param event
     */
    public void handleCloseButtonClicked(ActionEvent event) {
        System.exit(0);
    }
}
