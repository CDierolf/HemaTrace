package com.hemaapps.hematrace.TransactionViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.Model.TransactionType;
import com.hemaapps.hematrace.Model.User;
import com.hemaapps.hematrace.controls.UnitCheckInControls;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.TransactionViewControllers
 * @Date: Feb 18, 2020
 * @Subclass TransactionView Description:
 */
//Imports
//Begin Subclass TransactionViewController
public class TransactionViewController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(TransactionViewController.class);

    private final String FORM_LABEL_STYLE = "-fx-font: 16px 'Arial Bold'; -fx-text-fill: #FFF;";
    private final String INVALID_DN_BORDER_STYLE = "-fx-text-box-border: red; fx-focus-color: red";
    private final String DUPLICATE_LABEL_STYLE = "-fx-font: 12px 'Arial Bold'; -fx-text-fill: #FFFF00";
    private final String INVALID_LABEL_STYLE = "-fx-font: 12px 'Arial Bold'; -fx-text-fill: #FF0000";
    private final String VALID_LABEL_STYLE = "-fx-font: 12px 'Arial Bold'; -fx-text-fill: #00FF00";
    private Alerts alerts = new Alerts();

    private static BaseDAO baseDao;
    private static BaseProductsDAO baseProductDao;
    private static BaseTransactionDAO baseTransactionDao = new BaseTransactionDAO();
    private BloodProductValidation validation;
    private List<String> checkOutDonorList = new ArrayList<>();
    private List<String> checkInDonorList = new ArrayList<>();
    private List<String> checkInProductStatusList = new ArrayList<>();
    private boolean invalidSet = true;
    private boolean checkedOutFlag = false;
    private User user;
    private TransactionType tType;
    private List<UnitTransactionVBox> checkOutTransactionVBoxList = new ArrayList<>();
    private List<UnitCheckInControls> checkInTransactionVBoxList = new ArrayList<>();
    private int numValidDonorNumbers = 0;
    private Date date = new Date();

    @FXML
    private VBox checkOutVBox;
    @FXML
    private VBox checkInVBox;
    @FXML
    private TextField transactionDateTimeTextField;
    @FXML
    private Button checkInButton;
    @FXML
    private Button commitCheckoutTransactionButton;
    @FXML
    private Button cancelTransactionButton;
    @FXML
    private Button commitCheckInTransactionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
            baseProductDao = BaseProductsDAO.getInstance();
            validation = new BloodProductValidation();
            commitCheckoutTransactionButton = new Button("Commit Transaction");
            commitCheckoutTransactionButton.setDisable(true);
        } catch (SQLException ex) {
            log.error("An error occured initializing the TransactionViewController class.");
        }

        populateCheckoutUnitVBox();
        this.transactionDateTimeTextField.setText(new Timestamp(date.getTime()).toString());
        this.transactionDateTimeTextField.setEditable(false);
        this.checkOutVBox.getChildren().add(commitCheckoutTransactionButton);
        checkOutValidationListener();
    }

    /**
     * Populates the CheckOutVBox
     */
    private void populateCheckoutUnitVBox() {
        for (int i = 1; i <= baseDao.getNumBaseProducts(); i++) {
            String unitLabelValue = String.format("Unit # %s", i);
            String unitTextFieldIdValue = String.format("checkOutUnit%sTextField", i);
            UnitTransactionVBox checkoutUnitVBox = new UnitTransactionVBox(unitLabelValue, unitTextFieldIdValue);
            this.checkOutVBox.getChildren().add(checkoutUnitVBox.getUnitTransactionVBox());
            this.checkOutTransactionVBoxList.add(checkoutUnitVBox);
        }
    }

    /**
     * Populates the CheckInVBox
     */
    private void populateCheckinUnitVBox() throws SQLException {
        VBox checkInDTVBox = new VBox();
        Label label = new Label("Check In Date/Time");
        label.setStyle(FORM_LABEL_STYLE);
        checkInDTVBox.setMaxWidth(487.0);
        checkInDTVBox.setMaxHeight(58.0);
        checkInDTVBox.setPadding(new Insets(2.0));
        checkInDTVBox.setSpacing(2.0);
        TextField dtTextField = new TextField();
        dtTextField.setMaxWidth(250.0);
        dtTextField.setText(new Timestamp(date.getTime()).toString());
        dtTextField.setDisable(true);
        checkInDTVBox.getChildren().addAll(label, dtTextField);
        checkInDTVBox.setAlignment(Pos.CENTER);
        this.checkInVBox.getChildren().add(checkInDTVBox);
        for (int i = 0; i < numValidDonorNumbers; i++) {
            UnitCheckInControls ucc = new UnitCheckInControls(String.format("Unit # %s", i + 1), checkOutDonorList.get(i));
            this.checkInTransactionVBoxList.add(ucc);
            this.checkInVBox.getChildren().add(ucc);
        }
        commitCheckInTransactionButton = new Button("Commit check-in");
        commitCheckInTransactionButton.setOnAction((ActionEvent event) -> {
            commitCheckInTransactions();
        });
        this.checkInVBox.getChildren().add(commitCheckInTransactionButton);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransactionType(TransactionType tType) {
        this.tType = tType;
    }

    /**
     * Check In Button Handler
     */
    public void handleCheckInButtonClicked() throws SQLException {
        if (!checkedOutFlag) {
            alerts = new Alerts(Alert.AlertType.ERROR, "Checkout transaction has not yet been completed.",
                    "Unable to start the check in transaction process.", "You must complete the checkout transaction first.");
            alerts.showGenericAlert();
        } else {
            this.checkInButton.setVisible(false);
            populateCheckinUnitVBox();
        }
    }

    /**
     * Cancel Transaction Button Handler
     */
    public void handleCancelTransactionButtonClicked() {
        if (!checkedOutFlag) {
            Stage stage = (Stage) this.commitCheckoutTransactionButton.getScene().getWindow();
            log.info("Transaction cancelled by user.");
            stage.close();
        } else {
            alerts = new Alerts(Alert.AlertType.ERROR, "Transaction has been initialized",
                    "Cannot cancel a transaction in progress.", "You must complete this transaction.");
            alerts.showGenericAlert();
        }
    }

    private void checkOutValidationListener() {
        for (int i = 0; i < checkOutTransactionVBoxList.size(); i++) {
            // Need to do this to reference the loop index within a lambda
            // since it isn't and can't be final.
            IntegerProperty index = new SimpleIntegerProperty();
            index.set(i);
            checkOutTransactionVBoxList.get(i).getUnitTransactionTextField()
                    .textProperty()
                    .addListener((observable, oldV, newV) -> {
                        boolean valid = validateDN(newV);
                        if (index.get() < checkOutTransactionVBoxList.size()) {
                            if (valid) {
                                String dn = checkOutTransactionVBoxList.get(index.get()).getUnitTransactionTextField().getText();
                                if (!checkOutDonorList.contains(dn)) {
                                    if (checkOutDonorList.size() <= baseProductDao.getMaxNumProducts()) {
                                        checkOutDonorList.add(dn);
                                    }
                                    setValid(index.get());
                                    if (index.get() + 1 == checkOutTransactionVBoxList.size()) {
                                        this.transactionDateTimeTextField.requestFocus();
                                    } else {
                                        checkOutTransactionVBoxList.get(index.get() + 1).getUnitTransactionTextField().requestFocus();
                                    }
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

                        if (numValidDonorNumbers == baseDao.getNumBaseProducts() && !invalidSet) {
                            this.commitCheckoutTransactionButton.setDisable(false);
                        }
                    });
            System.out.println("NUM VALID: " + numValidDonorNumbers);
        }

        commitCheckoutTransactionButton.setOnAction((ActionEvent e) -> {
            commitCheckOutTransactions();
        });
    }

    private boolean validateDN(String newV) {
        boolean valid = false;

        if (newV.length() == 11) {
            log.info("Scanning for plasma dn validation: " + newV);
            valid = validation.isValidDonorNumber(newV, newV.length());
//            if (numValidDonorNumbers == baseProductDao.getMaxNumProducts()) {
//                this.transactionDateTimeTextField.requestFocus();
//            }
        } else if (!valid) {
            if (newV.length() == 13) {
                log.info("Scanning for prbc dn validation: " + newV);
                valid = validation.isValidDonorNumber(newV, newV.length());
            }
        } else {
            valid = false;
            log.info("Validation scan for DN: " + newV + "failed.");
        }

        if (valid) {
            numValidDonorNumbers++;
        }

        return valid;
    }

    private void setInvalid(int index) {
        checkOutTransactionVBoxList.get(index).getUnitTransactionTextField().setStyle(INVALID_DN_BORDER_STYLE);
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setVisible(true);
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setText("INVALID");
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setStyle(this.INVALID_LABEL_STYLE);
        checkOutTransactionVBoxList.get(index).getUnitTransactionTextField().requestFocus();
        checkOutTransactionVBoxList.get(index).getUnitTransactionTextField().selectAll();
    }

    private void setDuplicate(int index) {
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setVisible(true);
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setText("Duplicate");
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setStyle(this.DUPLICATE_LABEL_STYLE);
    }

    private void setNormal(int index) {
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setVisible(false);
        checkOutTransactionVBoxList.get(index).getUnitTransactionTextField().setStyle(null);
    }

    private void setValid(int index) {
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setVisible(true);
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setText("Valid");
        checkOutTransactionVBoxList.get(index).getInvalidLabel().setStyle(this.VALID_LABEL_STYLE);
        checkOutTransactionVBoxList.get(index).getUnitTransactionTextField().setStyle(null);
    }

    private void commitCheckOutTransactions() {
        Transaction transaction = null;
        for (String s : checkOutDonorList) {
            transaction = new Transaction();
            transaction.setBase(Integer.toString(baseDao.getBaseIdForInstance()));
            transaction.setCrewmember(user.getUserId());
            transaction.setDonorNumber(s);
            transaction.setTransactionType("Check Out");
            transaction.setProductStatus("2"); // Check Out Status
            try {
                if (baseTransactionDao.insertTransaction(transaction)) {
                    log.info("Transaction: " + transaction + " successfully inserted.");
                }
            } catch (SQLException ex) {
                log.error("An error occured committing the check out transactions", ex);
            }
        }
        this.commitCheckoutTransactionButton.setDisable(true);
        this.checkedOutFlag = true;

    }

    private void commitCheckInTransactions() {
        if (!dispositionsEmpty()) {
            System.out.println("SUBMITTING TRANSACTION!");
            Transaction transaction = null;
            System.out.println("CHECKIN DONOR LIST SIZE: " + checkInDonorList.size());
            for (String s : checkOutDonorList) {
                System.out.println("TRANSQCTION IN PROGRESS");
                transaction = new Transaction();
                transaction.setBase(Integer.toString(baseDao.getBaseIdForInstance()));
                transaction.setCrewmember(user.getUserId());
                transaction.setDonorNumber(s);
                transaction.setTransactionType("Check In");
                try {
                    if (baseTransactionDao.insertTransaction(transaction)) {
                        log.info("Transaction: " + transaction + " successfully inserted.");
                    }
                } catch (SQLException ex) {
                    log.error("An error occured commiting the check in transactions", ex);
                }
            }
            
            Stage stage = (Stage) this.commitCheckInTransactionButton.getScene().getWindow();
            stage.close();
        } else {
            alerts = new Alerts(Alert.AlertType.ERROR, "One or more unit dispositions are not selected.",
                    "Cannot commit check in without unit dispositions", 
                    "Ensure all units contain a disposition before proceeding.");
            alerts.showGenericAlert();
        }
    }

    private boolean dispositionsEmpty() {
        System.out.println("DISPOSITIONSEMPTY");
        boolean containsEmpty = false;
        System.out.println("checkINTransactionVBoxList: " + checkInTransactionVBoxList.size());
        for (int i = 0; i < checkInTransactionVBoxList.size(); i++) {
            System.out.println("LOOP");
            if (checkInTransactionVBoxList.get(i).getDispositionComboBox().getValue() == null) {
                containsEmpty = true;
                System.out.println("BREAK");
                break;
            } else {
                System.out.println("NOT EMPTY");
                containsEmpty = false;
            }
        }
        
        System.out.println("RETURNING");
        return containsEmpty;
    }

} //End Subclass TransactionViewController
