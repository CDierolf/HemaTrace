package com.hemaapps.hematrace.LoginViewControllers;

import com.hemaapps.hematrace.DashboardViewControllers.BaseDashboardViewController;
import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/*
 * BaseLoginViewController
 * Associated FXML: BaseLoginView
 * Created 2-1-2020
 * Author: Christopher Dierolf
 */
public class BaseLoginViewController implements Initializable {

    private final String title = "HemaTrace";

    @FXML
    private ImageView logo;
    @FXML
    private ComboBox baseComboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button adminLoginButton;
    
    final static Logger log = LoggerFactory.getLogger(BaseLoginViewController.class);
    
    private final DatabaseService db = new DatabaseService();
    private Alerts alerts = new Alerts();

    //Database db = new Database();
    ArrayList<String> bases = new ArrayList<>();

    /**
     * Initializes the controller class.
     * Calls getBases() to retrieve a list of bases
     * from the database to populate the baseComboBox
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        log.info("BaseLoginViewController successfully initialized.");

        try {
            getBases();
            bases.forEach((s) -> {
                baseComboBox.getItems().add(s);
            });
            log.info("baseComboBox successfully populated with base names from database.");
        } catch (SQLException ex) {
            log.error("An error occured trying to load the bases.", ex);
        }
    }

    /**
     * Retrieve the list of base names from the database
     * @throws SQLException 
     */
    private void getBases() throws SQLException {
        db.init();
        String query = "{ call [sp_retrieveBaseNames] }";
        ResultSet rs = null;
        rs = db.callableStatementRs(query);

        if (rs != null) {
            try {
                while (rs.next()) {
                    try {
                        bases.add(rs.getString("name"));
                    } catch (SQLException ex) {
                        log.error("Inner Exception Error: An error occured parsing the resultset of bases returned from the database.", ex);             
                    }
                }
            } catch (SQLException ex) {
                log.error("Outer Exception: An error occured parsing the resultset of bases returned from the database.", ex);
            }
        } else {
            log.error("No bases were returned from the database.");
        }
    }

    /**
     * Method to switch to the AdminLoginView scene.
     *
     * @param event
     * @throws IOException
     */
    public void handleAdminLoginButtonClicked(ActionEvent event) throws IOException {

        // Gotta go up one level to access resources
        FXMLLoader loader = new FXMLLoader(BaseLoginViewController.class.getResource("../AdminLoginView.fxml"));
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent baseDashboardViewParent = loader.load();
        Scene baseDashboardView = new Scene(baseDashboardViewParent, 640, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(baseDashboardView);
        window.setTitle(title + " Administrator Login");
        window.setResizable(false);
        window.show();

    }

    /**
     * Method to login into the Base Dashboard
     * Ensure a base is selected.
     *
     * @param event
     * @throws IOException
     */
    public void handleBaseLoginButtonClicked(ActionEvent event) throws IOException {

        // Ensure that a base is selected.
        if (baseComboBox.getValue() != null) {
            String baseValue = baseComboBox.getValue().toString();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../BaseDashboardView.fxml"));
            Parent baseDashboardView = loader.load();
            Scene baseDashboardScene = new Scene(baseDashboardView);

            // Dashboard View Controller
            BaseDashboardViewController controller = loader.getController();
            // Pass data into the controller
            controller.initData(baseComboBox.getValue().toString());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(baseDashboardScene);
            window.setTitle(title + baseValue + " Dashboard");
            window.setResizable(false);
            window.show();

        } else {
            alerts = new Alerts(AlertType.ERROR, "Base Selection is Blank",
                    "Please select a base to login.", "In order to proceed, please ensure"
                    + " that a base is selected.");
            alerts.showGenericAlert();
            baseComboBox.requestFocus();
        }

    }
    
    /**
     * Method to exit the application
     * @param event 
     */
    public void handleCloseButtonClicked() {
        log.info("Application is closing.");
        Platform.exit();
    }
}
