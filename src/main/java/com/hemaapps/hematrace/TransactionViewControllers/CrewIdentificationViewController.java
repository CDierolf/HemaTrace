package com.hemaapps.hematrace.TransactionViewControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/** 
 */
//Imports

//Begin Subclass CrewIdentificationViewController
public class CrewIdentificationViewController implements Initializable{

    @FXML
    private TextField crewIdTextField;
    @FXML
    private Button proceedToTransactionButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void handleProceedToTransactionButtonClick() {
        
    }
    
    private boolean validateCrewId() {
        if (crewIdTextField.getText().isEmpty() || crewIdTextField.getText().isBlank()) {
            return false;
        }
    }

} //End Subclass CrewIdentificationViewController