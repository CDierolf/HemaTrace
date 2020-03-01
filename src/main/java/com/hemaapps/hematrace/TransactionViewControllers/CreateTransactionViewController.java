package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.Model.TransactionType;
import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    private BaseDAO baseDAO;
    private int baseId;
    private String baseValue;
    
    @FXML
    private ComboBox transactionTypeComboBox;
    @FXML
    private Button createTransactionButton;
    
    private List<TransactionType> transactionTypeList = new ArrayList<>();
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDAO = BaseDAO.getInstance();
            baseId = baseDAO.getBaseIdForInstance();
            baseValue = baseDAO.getBaseValue();
            createTransactionButton.setDefaultButton(true);
            
        } catch (SQLException ex) {
            log.error("Unable to get the BaseDAO instance in CreateTransactionViewController.");
        }
        populateTransactionTypeComboBox();
    }
    
    private void populateTransactionTypeComboBox() {
        BaseTransactionDAO baseTransactionDAO = new BaseTransactionDAO();
        try {
            transactionTypeList = baseTransactionDAO.getTransactionTypeList();
        } catch (SQLException ex) {
            log.error("Unable to retrieve the transaction types list.");
        }
        
        log.info("Populating transaction type combobox.");
        for (TransactionType tt : transactionTypeList) {
            transactionTypeComboBox.getItems().add(tt.getTransactionType());
        }
    }
    
    
    
    public void handleProceedToCrewIdentViewButton() throws IOException {
        // Ensure that a base is selected.
        if (transactionTypeComboBox.getValue() != null) {
            String baseValue = transactionTypeComboBox.getValue().toString();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../CrewIdentificationView.fxml"));
            Parent crewIdentificationView = loader.load();
            Scene crewIdentificationScene = new Scene(crewIdentificationView);
            CrewIdentificationViewController controller = loader.getController();
            TransactionType tType = new TransactionType(this.transactionTypeComboBox.getValue().toString());
            controller.setTransactionType(tType);
            
            Stage window = (Stage)this.transactionTypeComboBox.getScene().getWindow();

            window.setScene(crewIdentificationScene);
            window.setTitle("HemaTrace - " + baseValue + " - Crew Identification");
            window.setResizable(false);
            window.show();

        } else {
            alerts = new Alerts(Alert.AlertType.ERROR, "Transaction Type is Blank",
                    "Please select a transaction type to proceed.", "In order to proceed, please ensure"
                    + " that a transaction type is selected.");
            alerts.showGenericAlert();
            transactionTypeComboBox.requestFocus();
        }
    }
    
    
    

} //End Subclass CreateTransactionViewController