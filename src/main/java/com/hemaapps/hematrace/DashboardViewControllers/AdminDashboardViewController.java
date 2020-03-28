/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

//import Enums.BloodProductStatus;
import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class AdminDashboardViewController implements Initializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AdminDashboardViewController.class);

    private BaseDAO baseDao;
    private BaseProductsDAO baseProductsDao;
    @FXML
    private Label dashboardLabel;
    @FXML
    private VBox leftVBox;
    @FXML
    private VBox rightVBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
            baseProductsDao = BaseProductsDAO.getInstance();
            loadUI();
        } catch (IOException ex) {
            log.error("Error initializing the AdminDashboardViewController: ", ex);
        } catch (SQLException ex) {
            log.error("Error initializing the AdminDashboardViewController: ", ex);
        } catch (ParseException ex) {
            Logger.getLogger(AdminDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUI() throws IOException, SQLException, ParseException {
        for (int i = 0; i < baseDao.getBases().size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../BaseDataView.fxml"));
            AnchorPane pane = loader.load();
            String baseName = baseDao.getBases().get(i).getName();
            BaseDataViewController controller = loader.getController();
            controller.initData(baseName);
//             Sort the Views
//             If odd, leftVBox, even, rightVBox.
            if (i % 2 != 0) {
                leftVBox.getChildren().add(pane);
            } else {
                rightVBox.getChildren().add(pane);
            }
        }
    }
}
