/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.UserToolsControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.FormUtils;
import com.hemaapps.hematrace.utilities.PasswordUtilities;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class NewUserViewController implements Initializable {

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField userIdTextField;
    @FXML private Button addUserButton;
    @FXML private Button closeButton;
   
    private List<Node> nodeList = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateNodeList();
    }    
    
    public void handleAddUserButtonClicked() throws NoSuchAlgorithmException {
        if (FormUtils.validateAllFieldsFilled(nodeList)) {
            User user = new User();
            user.setFirstName(this.firstNameTextField.getText());
            user.setLastName(this.lastNameTextField.getText());
            user.setEmail(this.emailTextField.getText());
            user.setUsername(this.userNameTextField.getText());
            String hashedPassword = PasswordUtilities.getHashedPassword(this.passwordTextField.getText());
            user.setPassword(hashedPassword);
            user.setUserId(this.userIdTextField.getText());
            UserDAO.insertUser(user);
        } else {
            Alerts alerts;
            alerts = new Alerts(Alert.AlertType.ERROR, "Please ensure all fields are filled out before continuing.",
                    "Checkn the form, fill out all required information and try again.", "If you have received this message in error"
                            + ", contact the blood bank administrator.");
            alerts.showGenericAlert();
        }
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
        this.nodeList.add(this.userIdTextField);
    }
}
