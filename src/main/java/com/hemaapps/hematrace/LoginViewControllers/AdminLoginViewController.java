package com.hemaapps.hematrace.LoginViewControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.DashboardViewControllers.AdminDashboardViewController;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.PasswordUtilities;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * AdminLoginViewController
 * Associated FXML: AdminLoginView
 * Created 2-1-2020
 * Author: Christopher Dierolf
 */
public class AdminLoginViewController implements Initializable {
    
    private static final Logger log = LoggerFactory.getLogger(AdminLoginViewController.class);
    private static Alerts alerts;
    private PasswordUtilities passwordUtilities = null;
    
    @FXML
    private Button closeButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        this.loginButton.setDefaultButton(true);
    }
    
    public void handleLoginButtonClicked(ActionEvent event) throws SQLException, ParseException {
       
        if (userIsAuthorized()) {
           log.info("User credentials valid for username: " + userNameTextField.getText() + " loading Admin Dashboard.");
            loadAdminDashboard(event);
        } else {
            alerts = new Alerts(Alert.AlertType.ERROR, "Invalid username or password",
                    "Check your credentials and try again.", "If you have received this message in error"
                            + ", contact the blood bank administrator.");
            alerts.showGenericAlert();
            log.info("Invalid credentials entered for username: " + userNameTextField.getText());
        }
    }
    
    
    public void handleCloseButtonClicked() {
        log.info("Application is exiting from AdminLoginViewController.");
        Platform.exit();
    }
    
    private boolean userIsAuthorized() throws SQLException, ParseException {
        
        boolean userIsAuthenticated;
        String userName = userNameTextField.getText();
        String pWord = passwordField.getText();
        long loggedIn = 0;
        
        try {
            loggedIn = UserDAO.loginUser(userName, pWord);
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(AdminLoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return loggedIn > 0;
    }
    
    private void loadAdminDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(AdminDashboardViewController.class.getResource("../AdminDashboardView.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent adminDashboardViewParent = loader.load();
            Scene adminDashboardView = new Scene(adminDashboardViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AdminDashboardViewController controller = loader.getController();
            
            window.setScene(adminDashboardView);
            // TODO get user's real first name
            window.setTitle("Welcome, " + " Administrator Dashboard");
            window.setResizable(false);
            window.show();
        } catch (IOException ex) {
            log.error("An error occurred attempting to load the AdminDashboardView from AdminLoginView");
        }
    }
}
