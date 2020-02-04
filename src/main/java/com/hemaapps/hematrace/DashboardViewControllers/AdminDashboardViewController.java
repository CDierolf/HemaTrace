/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

//import Enums.BloodProductStatus;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class AdminDashboardViewController implements Initializable {

    @FXML
    private Label dashboardLabel;
    @FXML
    private VBox leftVBox;
    @FXML
    private VBox rightVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            //loadUI();
//        } catch (IOException ex) {
//            Logger.getLogger(AdminDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void loadUI() throws IOException {
        
        // TODO
        // Get base data from database
        // If plasma base, load PlasmaBaseDataView and pass data into its controller.
        // If regular base, load BaseDataView and pass data into IT's controller.
        // In respective controller, hook View Details button to load detailed view based on
        // the first parameter of initData.
        // Then load the views into the AdminDashboardView.
        for (int i = 1; i <= 9; i++) {
            
            FXMLLoader loader = new FXMLLoader();
           // loader.setLocation(getClass().getResource("/Views/DashboardViews/AdminDashboardView/BaseDataView.fxml"));
            AnchorPane pane = loader.load();
            pane.setId("LifeFlight" + i);
            
//            BaseDataViewController controller = loader.getController();
//            controller.initData("LifeFlight " + i, "W201919620342342343", "W22342340192818219",
//                    BloodProductStatus.OK, BloodProductStatus.CHECKED_OUT);
//            
            
            // Sort the Views
            // If odd, leftVBox, even, rightVBox.
            if (i % 2 != 0){
                leftVBox.getChildren().add(pane);
            } else {
                rightVBox.getChildren().add(pane);
            }
        }

    }

    public void initData(String userName) {
        System.out.println(userName);
    }

    FXMLLoader loader = new FXMLLoader();

}
