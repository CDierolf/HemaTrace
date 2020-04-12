package com.hemaapps.hematrace.UserToolsControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.DashboardViewControllers.AdminToolsViewController;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.FormUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.UserToolsControllers
 * @Date: Apr 12, 2020
 * @Subclass EditUserViewController Description:
 */
//Imports
//Begin Subclass EditUserViewController
public class EditUserViewController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField crewIdTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button doneButton;
    @FXML
    private AnchorPane userToolsAnchorPane;

    private List<Node> nodeList = new ArrayList<>();
    private AdminToolsViewController adminToolsViewController;
    private UserToolsViewController userToolsViewController;
    private User selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNodesDisabled();
        populateNodeList();
    }

    public void setAdminToolsViewController(AdminToolsViewController adminToolsViewController) {
        this.adminToolsViewController = adminToolsViewController;
    }
    
    public void setUserToolsViewController(UserToolsViewController userToolsViewController) {
        this.userToolsViewController = userToolsViewController;
    }

    public void setSelectedUser(User user) {
        this.selectedUser = user;
        this.firstNameTextField.setText(user.getFirstName());
        this.lastNameTextField.setText(user.getLastName());
        this.emailTextField.setText(user.getEmail());
        this.userNameTextField.setText(user.getUsername());
        this.crewIdTextField.setText(user.getCrewId());
    }

    public void handleSaveButtonClicked() throws SQLException {
        if (FormUtils.validateAllFieldsFilled(nodeList)) {
            User user = new User();
            user.setUserId(user.getUserId());
            user.setFirstName(this.firstNameTextField.getText());
            user.setLastName(this.lastNameTextField.getText());
            user.setEmail(this.emailTextField.getText());
            user.setUsername(this.userNameTextField.getText());
            user.setCrewId(this.crewIdTextField.getText());
            user.setUserId(this.selectedUser.getUserId());
            if (UserDAO.updateUser(user)) {
                setNodesDisabled();
                this.deleteButton.setDisable(false);
                userToolsViewController.updateUserList();
            } else {
                Alerts alerts;
                alerts = new Alerts(Alert.AlertType.ERROR, "An error occured attempting to edit this user.",
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

    public void handleEditButtonClicked() {
        FormUtils.setNodesEditable(nodeList, Boolean.TRUE);
        this.deleteButton.setDisable(true);

    }
    
    public void handleDeleteButtonClicked() throws SQLException, IOException {
        if (UserDAO.deleteUser(this.selectedUser.getUserId())) {
            closeEditUserView();
            openUserToolsView();
        } else {
            Alerts alerts;
            alerts = new Alerts(Alert.AlertType.ERROR, "An error occurred attempting to delete this user",
                    "", "Please contact the blood bank administrator.");
            alerts.showGenericAlert();
        }
        
        
    }

    public void handleDoneButtonClicked() throws IOException {
        closeEditUserView();
        openUserToolsView();
    }
    
    private void setNodesDisabled() {
        FormUtils.setNodesEditable(nodeList, Boolean.FALSE);
    }

    private void populateNodeList() {
        this.nodeList.add(this.firstNameTextField);
        this.nodeList.add(this.lastNameTextField);
        this.nodeList.add(this.emailTextField);
        this.nodeList.add(this.userNameTextField);
        this.nodeList.add(this.crewIdTextField);
    }

    private void closeEditUserView() {
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
    }

    private void openUserToolsView() throws IOException {
        closeEditUserView();
        this.adminToolsViewController.dynamicViewPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../UserToolsView.fxml"));
        this.userToolsAnchorPane = loader.load();
        UserToolsViewController controller = loader.getController();
        controller.setAdminToolsController(this.adminToolsViewController);
        this.adminToolsViewController.dynamicViewPane.getChildren().add(userToolsAnchorPane);
    }
} //End Subclass EditUserViewController
