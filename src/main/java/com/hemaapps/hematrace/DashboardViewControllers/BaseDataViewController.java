/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.enums.BloodProductStatus;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML private CheckBox plasmaCheck;
    @FXML private Label baseLabel;
    @FXML private Label prbc1Text;
    @FXML private Label prbc2Text;
    @FXML private Circle prbc1Circle;
    @FXML private Circle prbc2Circle;
    @FXML private VBox detailsVBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseProductsDao = BaseProductsDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDataViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initData(String baseName) {
       this.baseLabel.setText(baseName);
    }
    
    // Abstract out to separate class
    private Circle setBPStatus(BloodProductStatus status, Circle c) {
        switch (status) {
            case OK: 
                c.setFill(Color.GREEN);
                break;
            case EXPIRING: 
                c.setFill(Color.YELLOW);
                break;
            case EXPIRED: 
                c.setFill(Color.RED);
                break;
            default:
                c.setFill(Color.WHITE);
            }
        
        return c;
    }


    
}
