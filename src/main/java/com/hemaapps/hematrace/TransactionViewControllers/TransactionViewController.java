package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.controls.UnitTransactionVBox;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.BloodProductValidation;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.TransactionViewControllers
 * @Date: Feb 18, 2020
 * @Subclass TransactionView Description:
 */
//Imports
//Begin Subclass TransactionViewController
public class TransactionViewController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(TransactionViewController.class);

    private final String INVALID_DN_BORDER_STYLE = "-fx-text-box-border: red; fx-focus-color: red";
    private final String DUPLICATE_LABEL_STYLE = "-fx-font: 12px 'Arial Bold'; -fx-text-fill: #FFFF00";
    private final String INVALID_LABEL_STYLE = "-fx-font: 12px 'Arial Bold'; -fx-text-fill: #FF0000";
    private Alerts alerts = new Alerts();

    private static BaseDAO baseDao;
    private static BaseProductsDAO baseProductDao;
    private BloodProductValidation validation;
    private List<String> donorNumberList = new ArrayList<>();
    private boolean invalidSet = true;

    @FXML
    private VBox checkOutVBox;
    @FXML
    private VBox checkInVBox;
    @FXML
    private TextField transactionDateTimeTextField;
    @FXML
    private Button checkInButton;
    @FXML
    private Button commitTransactionButton;
    @FXML
    private Button cancelTransactionButton;

    private List<UnitTransactionVBox> transactionVBoxList = new ArrayList<>();
    private int numValidDonorNumbers = 0;
    private Date date = new Date();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
            baseProductDao = BaseProductsDAO.getInstance();
            validation = new BloodProductValidation();
            commitTransactionButton = new Button("Commit Transaction");
            commitTransactionButton.setDisable(true);
        } catch (SQLException ex) {
            log.error("An error occured initializing the TransactionViewController class.");
        }

        populateUnitTransactionVBox();
        this.transactionDateTimeTextField.setText(new Timestamp(date.getTime()).toString());
        this.transactionDateTimeTextField.setEditable(false);
        this.checkOutVBox.getChildren().add(commitTransactionButton);
        handleListeners();
    }

    private void populateUnitTransactionVBox() {
        for (int i = 1; i <= baseDao.getNumBaseProducts(); i++) {
            String unitLabelValue = String.format("Unit # %s", i);
            String unitTextFieldIdValue = String.format("unit%sTextField", i);
            UnitTransactionVBox unitVBox = new UnitTransactionVBox(unitLabelValue, unitTextFieldIdValue);
            this.checkOutVBox.getChildren().add(unitVBox.getUnitTransactionVBox());
            this.transactionVBoxList.add(unitVBox);
        }
    }
    
    public void handleCheckInButtonClicked() {
        System.out.println(invalidSet);
    }
    
    public void handleCancelTransactionButtonClicked() {
        Stage stage = (Stage)this.commitTransactionButton.getScene().getWindow();
        log.info("Transaction cancelled by user.");
        stage.close();
    }

    private void handleListeners() {
        for (int i = 0; i < transactionVBoxList.size(); i++) {
            // Need to do this to reference the loop index within a lambda
            // since it isn't and can't be final.
            IntegerProperty index = new SimpleIntegerProperty();
            index.set(i);
            transactionVBoxList.get(i).getUnitTransactionTextField()
                    .textProperty()
                    .addListener((observable, oldV, newV) -> {
                        boolean valid = validateDN(newV);
                        if (index.get() < transactionVBoxList.size() - 1) {
                            if (valid) {
                                String dn = transactionVBoxList.get(index.get()).getUnitTransactionTextField().getText();
                                if (!donorNumberList.contains(dn)) {
                                    donorNumberList.add(dn);
                                    setNormal(index.get());
                                    transactionVBoxList.get(index.get() + 1).getUnitTransactionTextField().requestFocus();
                                    invalidSet = false;
                                } else {
                                    setDuplicate(index.get());
                                    invalidSet = true;
                                }
                            } else {
                                setInvalid(index.get());
                                invalidSet = true;
                            }
                        }
                        
                        if (numValidDonorNumbers == baseProductDao.getMaxNumProducts() && !invalidSet) {
                            this.commitTransactionButton.setDisable(false);
                        }
                    });
        }
        
    }

    private boolean validateDN(String newV) {
        boolean valid = false;

        if (newV.length() == 11) {
            System.out.println("Scanning for plasma dn validation: " + newV);
            valid = validation.isValidDonorNumber(newV, newV.length());
            numValidDonorNumbers++;
            if (numValidDonorNumbers == baseProductDao.getMaxNumProducts()) {
                this.transactionDateTimeTextField.requestFocus();
            }
        } else if (!valid) {
            if (newV.length() == 13) {
                System.out.println("Scanning for prbc dn validation: " + newV);
                valid = validation.isValidDonorNumber(newV, newV.length());
            }
        } else {
            valid = false;
        }

        return valid;
    }

    private void setInvalid(int index) {
        transactionVBoxList.get(index).getUnitTransactionTextField().setStyle(INVALID_DN_BORDER_STYLE);
        transactionVBoxList.get(index).getInvalidLabel().setVisible(true);
        transactionVBoxList.get(index).getInvalidLabel().setText("INVALID");
        transactionVBoxList.get(index).getInvalidLabel().setStyle(this.INVALID_LABEL_STYLE);
        transactionVBoxList.get(index).getUnitTransactionTextField().requestFocus();
        transactionVBoxList.get(index).getUnitTransactionTextField().selectAll();
    }

    private void setDuplicate(int index) {
        transactionVBoxList.get(index).getInvalidLabel().setVisible(true);
        transactionVBoxList.get(index).getInvalidLabel().setText("Duplicate");
        transactionVBoxList.get(index).getInvalidLabel().setStyle(this.DUPLICATE_LABEL_STYLE);
    }
    
    private void setNormal(int index) {
        transactionVBoxList.get(index).getInvalidLabel().setVisible(false);
        transactionVBoxList.get(index).getUnitTransactionTextField().setStyle(null);
    }
} //End Subclass TransactionViewController
