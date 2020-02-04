/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class BaseDashboardViewController implements Initializable {

    private final String title = "HemaTrace";
    @FXML
    private Label baseIdentifierLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(String baseLocation) {
        this.baseIdentifierLabel.setText(baseLocation);
    }

    public void handleLogoutButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../BaseLoginView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent baseLoginViewParent = loader.load();
        Scene baseLoginView = new Scene(baseLoginViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(baseLoginView);
        window.setTitle(title + "Base Login");
        window.setResizable(false);
        window.show();
    }

}
