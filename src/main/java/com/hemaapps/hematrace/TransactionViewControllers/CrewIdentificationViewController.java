package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 */
//Imports

//Begin Subclass CrewIdentificationViewController
public class CrewIdentificationViewController implements Initializable{
    
    private static final Logger log = LoggerFactory.getLogger(CrewIdentificationViewController.class);
    private static Alerts alerts;
    private static UserDAO uDao;

    @FXML
    private TextField crewIdTextField;
    @FXML
    private Button proceedToTransactionButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void handleProceedToTransactionButtonClick() throws SQLException, IOException, ParseException {
        
        if (validateCrewId()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../TransactionView.fxml"));
            Parent transactionView = loader.load();
            Scene transactionScene = new Scene(transactionView);
            TransactionViewController controller = loader.getController();
            // TODO Need to pass transaction type to crew ident and then on the transaction view
            
            Stage window = (Stage) this.crewIdTextField.getScene().getWindow();
            window.setScene(transactionScene);
            window.setTitle("HemaTrace - " + " - Crew Identification");
            window.setResizable(false);
            window.show();
        }
        
        
        
    }
    
    private boolean validateCrewId() throws SQLException, ParseException {
        
        boolean validUser = false;
        
        String crewId;
        if (crewIdTextField.getText().isEmpty() || crewIdTextField.getText().isBlank()) {
            alerts = new Alerts(Alert.AlertType.ERROR, "Crew identification fields is blank.",
                    "You must provide a valid crew id.", "In order to proceed, please ensure"
                    + " a valid crew id is provided.");
            alerts.showGenericAlert();
            this.crewIdTextField.requestFocus();
            return false;
        } else {
            crewId = crewIdTextField.getText();
        }
        
        if (uDao.validateCrewUser(crewId)) {
            validUser = true;
        } else {
            validUser = false;
        }
        
        return validUser;
    }

} //End Subclass CrewIdentificationViewController