/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.UserToolsControllers.UserToolsViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 * FXML: AdminToolsViewController
 * @author Christopher Dierolf
 */
public class AdminToolsViewController implements Initializable {

    @FXML
    private AnchorPane userToolsAnchorPane;
    @FXML // must be public to let the UserToolsViewController access the dynamicViewPane
    public AnchorPane dynamicViewPane;
    @FXML
    private Button userToolsButton;
    @FXML
    private Button productToolsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showUserTools();
        } catch (IOException ex) {
            Logger.getLogger(AdminToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showUserTools() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../UserToolsView.fxml"));
        this.userToolsAnchorPane = loader.load();
        UserToolsViewController userToolsViewController = new UserToolsViewController();
        userToolsViewController = loader.getController();
        userToolsViewController.setAdminToolsController(this);
        this.dynamicViewPane.getChildren().clear();
        this.dynamicViewPane.getChildren().add(userToolsAnchorPane);
    }
    
    public void handleUserToolsButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../UserToolsView.fxml"));
        this.userToolsAnchorPane = loader.load();
        UserToolsViewController controller = loader.getController();
        controller.setAdminToolsController(this);
        dynamicViewPane.getChildren().add(userToolsAnchorPane);
    }
    
  

}
