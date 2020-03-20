/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.controls.BaseUnit;
import com.hemaapps.hematrace.enums.BloodProductStatus;
import com.hemaapps.hematrace.shapes.StatusCircle;
import com.hemaapps.hematrace.utilities.ExpiryStatusUtil;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class BaseDataViewController implements Initializable {

    private static BaseProductsDAO baseProductsDao;
    private static BaseDAO baseDao;
    private String baseName = "";

    @FXML
    private CheckBox plasmaCheck;
    @FXML
    private Label baseLabel;
    @FXML
    private Label prbc1Text;
    @FXML
    private Label prbc2Text;
    @FXML
    private Circle prbc1Circle;
    @FXML
    private Circle prbc2Circle;
    @FXML
    private VBox detailsVBox;
    @FXML
    private Button viewDetailsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseProductsDao = BaseProductsDAO.getInstance();
            baseDao = BaseDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDataViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Load data for all bases and populate each instance of the BaseDataView
     * (one for each base) with the corresponding base data.
     * Run in thread to prevent user boredom.
     * @param baseName 
     */
    public void initData(String baseName) {
        
        this.baseName = baseName;
        Thread thread = new Thread(() -> {
            Runnable run = () -> {
                int baseId = baseDao.getBaseIdFromMap(baseName);
                baseProductsDao.setBaseBloodProductsResultSet(baseId);
                int prbcNum = baseProductsDao.getBasePRBCProducts().size();
                int plasmaNum = baseProductsDao.getBasePlasmaProducts().size();
                if ((prbcNum + plasmaNum) > 0) {
                    for (int i = 0; i < baseProductsDao.getBasePRBCProducts().size(); i++) {
                        String type = "PRBC";
                        String dn = baseProductsDao.getBasePRBCProducts().get(i).getDonorNumber();
                        BloodProductStatus bpStatus = ExpiryStatusUtil.getExpiryStatus(baseProductsDao.getBasePRBCProducts().get(i).getExpirationDate());
                        StatusCircle statusCircle = new StatusCircle(ExpiryStatusUtil.getExpiryColor(bpStatus), 13, null);
                        BaseUnit baseUnit = new BaseUnit(type, dn, bpStatus, statusCircle);
                        this.detailsVBox.getChildren().add(baseUnit.getMainHBox());
                    }
                    for (int i = 0; i < baseProductsDao.getBasePlasmaProducts().size(); i++) {
                        String type = "Plasma";
                        String dn = baseProductsDao.getBasePlasmaProducts().get(i).getDonorNumber();
                        BloodProductStatus bpStatus = ExpiryStatusUtil.getExpiryStatus(baseProductsDao.getBasePlasmaProducts().get(i).getExpirationDate());
                        StatusCircle statusCircle = new StatusCircle(ExpiryStatusUtil.getExpiryColor(bpStatus), 13, null);
                        BaseUnit baseUnit = new BaseUnit(type, dn, bpStatus, statusCircle);
                        this.detailsVBox.getChildren().add(baseUnit.getMainHBox());
                    }
                } else {
                    this.detailsVBox.getChildren().add(new Label("No products are available for this base."));
                }
                this.baseLabel.setText(baseName);
            };
            Platform.runLater(run);
        });

        thread.setDaemon(true);
        thread.start();
    }
}
