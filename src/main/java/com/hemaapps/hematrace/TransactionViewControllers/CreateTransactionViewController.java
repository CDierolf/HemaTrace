package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.DashboardViewControllers
 * @Date: Feb 18, 2020
 * @Subclass CreateTransactionViewController Description: 
 */
//Imports

//Begin Subclass CreateTransactionViewController
public class CreateTransactionViewController implements Initializable{
    
    private static final Logger log = LoggerFactory.getLogger(CreateTransactionViewController.class);
    private static Alerts alerts;

    private int baseId;
    
    @FXML
    private ComboBox transactionTypeComboBox;
    @FXML
    private Button createTransactionButton;
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
//    public void handleCreateTransactionButton(ActionEvent event) throws IOException {
//        // Ensure that a base is selected.
//        if (transactionTypeComboBox.getValue() != null) {
//            String baseValue = transactionTypeComboBox.getValue().toString();
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("../CrewIdentification.fxml"));
//            Parent baseDashboardView = loader.load();
//            Scene baseDashboardScene = new Scene(baseDashboardView);
//
//            // Dashboard View Controller
//            BaseDashboardViewController controller = loader.getController();
//            // Pass data into the controller
//            controller.initData(baseComboBox.getValue().toString());
//
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            double width = 940;
//            double height = 1391;
//            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//            window.setX((screenBounds.getWidth() - width) / 4);
//            window.setY((screenBounds.getHeight() - height) / 2);
//
//            window.setScene(baseDashboardScene);
//            window.setTitle(title + baseValue + " Dashboard");
//            window.setResizable(false);
//            window.show();
//
//        } else {
//            alerts = new Alerts(Alert.AlertType.ERROR, "Base Selection is Blank",
//                    "Please select a base to login.", "In order to proceed, please ensure"
//                    + " that a base is selected.");
//            alerts.showGenericAlert();
//            baseComboBox.requestFocus();
//        }
    
    
    
    

} //End Subclass CreateTransactionViewController