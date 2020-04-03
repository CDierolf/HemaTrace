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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML
    private Circle adminToolsButton;
    @FXML
    private Circle recentTransactionsButton;
    @FXML
    private Circle logoutButton;

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
        setHandlers();
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

    private void setHandlers() {
        // Change the shape color of the top buttons
        adminToolsButton.setOnMouseEntered(MouseEvent -> {
            adminToolsButton.setStyle("-fx-fill: #000");
        });
        adminToolsButton.setOnMouseExited(MouseEvent -> {
            adminToolsButton.setStyle("-fx-fille: #111");
        });
        adminToolsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                handleAdminToolsButtonClicked();
            }
        });
        logoutButton.setOnMouseEntered(MouseEvent -> {
            logoutButton.setStyle("-fx-fill: #000");
        });
        logoutButton.setOnMouseExited(MouseEvent -> {
            logoutButton.setStyle("-fx-fill: #000");
        });
        logoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                try {
                    handleLogoutButtonClicked();
                } catch (IOException ex) {
                    Logger.getLogger(AdminDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        recentTransactionsButton.setOnMouseEntered(MouseEvent -> {
            recentTransactionsButton.setStyle("-fx-fill: #000");
        });
        recentTransactionsButton.setOnMouseExited(MouseEvent -> {
            recentTransactionsButton.setStyle("-fx-fille: #111");
        });
        recentTransactionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                try {
                    handleRecentTransactionsClicked();
                } catch (IOException ex) {
                    Logger.getLogger(AdminDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void handleRecentTransactionsClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../RecentTransactionsView.fxml"));
        Parent baseDataTransactionTableViewParent = loader.load();
        Scene baseDataTransactionTableView = new Scene(baseDataTransactionTableViewParent);
        Stage window = new Stage();
        Stage ownerStage = (Stage)this.leftVBox.getScene().getWindow();
        RecentTransactionsViewController controller = loader.getController();
        window.initOwner(ownerStage);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(baseDataTransactionTableView);
        window.setTitle("Base-wide recent transactions");
        window.setResizable(false);
        window.showAndWait();
    }

    private void handleLogoutButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../AdminLoginView.fxml"));
        Parent baseLoginViewParent = loader.load();
        Scene baseLoginView = new Scene(baseLoginViewParent);
        Stage window = (Stage) this.logoutButton.getScene().getWindow();
        window.setScene(baseLoginView);
        window.setTitle("HemaTrace - Admin Login");
        window.setResizable(false);
        window.show();
    }

    private void handleAdminToolsButtonClicked() {

    }
}
