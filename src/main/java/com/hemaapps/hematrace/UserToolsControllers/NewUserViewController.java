/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.UserToolsControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.DashboardViewControllers.AdminToolsViewController;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.FormUtils;
import com.hemaapps.hematrace.utilities.PasswordUtilities;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class NewUserViewController implements Initializable {
    
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField crewIdTextField;
    @FXML
    private Button addUserButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button doneButton;
    @FXML
    private AnchorPane userToolsAnchorPane;
    
    private List<Node> nodeList = new ArrayList<>();
    private AdminToolsViewController adminToolsViewController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateNodeList();
    }    
    
    public void handleAddUserButtonClicked() throws NoSuchAlgorithmException, SQLException, IOException, ParseException {
        if (FormUtils.validateAllFieldsFilled(nodeList)) {
            User user = new User();
            user.setFirstName(this.firstNameTextField.getText());
            user.setLastName(this.lastNameTextField.getText());
            user.setEmail(this.emailTextField.getText());
            user.setUsername(this.userNameTextField.getText());
            String hashedPassword = PasswordUtilities.getHashedPassword(this.passwordTextField.getText());
            user.setPassword(hashedPassword);
            user.setCrewId(this.crewIdTextField.getText());
            if (UserDAO.insertUser(user)) {
                FormUtils.clearTextPasswordFields(nodeList);
            } else {
                Alerts alerts;
                alerts = new Alerts(Alert.AlertType.ERROR, "An error occured attempting to add this user.",
                        "", "Please contact the blood bank administrator.");
                alerts.showGenericAlert();
            }
        } else {
            Alerts alerts;
            alerts = new Alerts(Alert.AlertType.ERROR, "Please ensure all fields are filled out before continuing.",
                    "Check the form, fill out all required information and try again.", "If you have received this message in error"
                    + ", contact the blood bank administrator.");
            alerts.showGenericAlert();
        }
    }
    
    public void setAdminToolsViewController(AdminToolsViewController adminToolsViewController) {
        this.adminToolsViewController = adminToolsViewController;
    }
    
    public void handleCloseButtonClicked() {
        FormUtils.closeWindow((Stage) this.closeButton.getScene().getWindow());
    }
    
    private void populateNodeList() {
        this.nodeList.add(this.firstNameTextField);
        this.nodeList.add(this.lastNameTextField);
        this.nodeList.add(this.emailTextField);
        this.nodeList.add(this.userNameTextField);
        this.nodeList.add(this.passwordTextField);
        this.nodeList.add(this.crewIdTextField);
    }
    
    private void closeNewUserView() {
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
    }
    
    private void openUserToolsView() throws IOException {
        closeNewUserView();
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../UserToolsView.fxml"));
        this.userToolsAnchorPane = loader.load();
        UserToolsViewController controller = loader.getController();
        controller.setAdminToolsController(this.adminToolsViewController);
        this.adminToolsViewController.dynamicViewPane.getChildren().add(userToolsAnchorPane);
    }
    
    public void handleDoneButtonClicked() throws IOException {
        closeNewUserView();
        openUserToolsView();
    }
}
