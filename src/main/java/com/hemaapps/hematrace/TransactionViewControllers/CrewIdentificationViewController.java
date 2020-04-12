package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.UserDAO;
import com.hemaapps.hematrace.Model.TransactionType;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 */
//Imports

//Begin Subclass CrewIdentificationViewController
public class CrewIdentificationViewController implements Initializable{
    
    private static final Logger log = LoggerFactory.getLogger(CrewIdentificationViewController.class);
    private static Alerts alerts;
    private UserDAO uDao;
    private BaseDAO baseDao;
    private User user;
    private TransactionType tType;

    @FXML
    private TextField crewIdTextField;
    @FXML
    private Button proceedToTransactionButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
            proceedToTransactionButton.setDefaultButton(true);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CrewIdentificationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setTransactionType(TransactionType tType) {
        this.tType = tType;
    }
    
    public void handleProceedToTransactionButtonClick() throws SQLException, IOException, ParseException {
        
        if (validateCrewId()) {
            System.out.println(validateCrewId());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../TransactionView.fxml"));
            Parent transactionView = loader.load();
            Scene transactionScene = new Scene(transactionView);
            TransactionViewController controller = loader.getController();
            // TODO Need to pass transaction type to crew ident and then on the transaction view
            controller.setUser(user);
            controller.setTransactionType(tType);
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(transactionScene);
            stage.setTitle("HemaTrace - " + baseDao.getBaseValue() + " - Crew Identification");
            stage.setResizable(false);
            stage.show();
            
            closeWindow();
            
        } else {
            alerts = new Alerts(Alert.AlertType.ERROR, "Identification is not valid or user is not authorized.",
                    "You must provide a valid crew id.", "In order to proceed, please ensure"
                    + " a valid crew id is provided.");
            alerts.showGenericAlert();
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
            user = new User(crewId);
            validUser = true;
        } else {
            validUser = false;
        }
        
        return validUser;
    }
    
    private void closeWindow() {
        Stage thisStage = (Stage) this.crewIdTextField.getScene().getWindow();
        thisStage.close();
    }

} //End Subclass CrewIdentificationViewController