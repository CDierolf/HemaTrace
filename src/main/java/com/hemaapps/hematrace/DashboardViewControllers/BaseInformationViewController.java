/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.Model.Base;
import com.hemaapps.hematrace.utilities.Alerts;
import com.hemaapps.hematrace.utilities.FormUtils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class BaseInformationViewController implements Initializable {

    @FXML
    private TextField baseIdTextField;
    @FXML
    private TextField baseNameTextField;
    @FXML
    private TextField baseAddressTextField;
    @FXML
    private TextField baseCityTextField;
    @FXML
    private TextField baseStateTextField;
    @FXML
    private TextField baseZipTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button closeButton;

    private BaseDAO baseDao;
    private Base base;
    private Alerts alerts = new Alerts();
    private boolean formEditableFlag = false;
    private List<TextField> fieldList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseInformationViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * If null is returned, no base information for that base. Error Close
     * window.
     *
     * @param baseId
     */
    public void initData(String baseName) {
        int baseId = baseDao.getBaseIdFromMap(baseName);
        this.base = baseDao.getBaseInfoFromList(baseId);
        populateFieldList();
        if (base == null) {
            alerts = new Alerts(Alert.AlertType.ERROR, "The base information could not be retrieved.",
                    "Confirm that the base exists.", "If you have received this message in error"
                    + ", contact the blood bank administrator.");
            alerts.showGenericAlert();
        } else {
            populateData();
        }
    }

    private void populateData() {
        this.baseIdTextField.setText(Integer.toString(base.getBase_id()));
        this.baseNameTextField.setText(base.getName());
        this.baseAddressTextField.setText(base.getAddress());
        this.baseCityTextField.setText(base.getCity());
        this.baseStateTextField.setText(base.getState());
        this.baseZipTextField.setText(base.getZipCode());
    }

    private void populateFieldList() {
        this.fieldList.add(baseIdTextField);
        this.fieldList.add(baseNameTextField);
        this.fieldList.add(baseAddressTextField);
        this.fieldList.add(baseCityTextField);
        this.fieldList.add(baseStateTextField);
        this.fieldList.add(baseZipTextField);
    }

    public void handleCloseButtonClicked() {
        FormUtils.closeWindow((Stage) this.baseAddressTextField.getScene().getWindow());
    }

    public void handleEditButtonClicked() {
        formEditableFlag = !formEditableFlag;

        fieldList.forEach((t) -> {
            t.setEditable(formEditableFlag);
        });
    }

}
