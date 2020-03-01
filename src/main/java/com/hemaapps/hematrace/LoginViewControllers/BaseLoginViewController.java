package com.hemaapps.hematrace.LoginViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DashboardViewControllers.BaseDashboardViewController;
import com.hemaapps.hematrace.Database.DatabaseService;
import com.hemaapps.hematrace.utilities.Alerts;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
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
    List<String> bases = new ArrayList<>();

    /**
     * Initializes the controller class. Calls getBases() to retrieve a list of
     * bases from the database to populate the baseComboBox
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        log.info("BaseLoginViewController successfully initialized.");
        try {
            populateBaseComboBox();
            this.loginButton.setDefaultButton(true);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(BaseLoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve the list of base names from the database
     *
     * @throws SQLException
     */
    private void populateBaseComboBox() throws SQLException {
        BaseDAO baseDAO = BaseDAO.getInstance();
        bases = baseDAO.getBaseNames();
        bases.forEach((s) -> {
            baseComboBox.getItems().add(s);
        });
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

        Parent baseDashboardViewParent = loader.load();
        Scene baseDashboardView = new Scene(baseDashboardViewParent, 640, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(baseDashboardView);
        window.setTitle(title + " Administrator Login");
        window.setResizable(false);
        window.show();

    }

    /**
     * Method to login into the Base Dashboard Ensure a base is selected.
     *
     * @param event
     * @throws IOException
     */
    public void handleBaseLoginButtonClicked(ActionEvent event) throws IOException, SQLException, ParseException {

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

            double width = 940;
            double height = 1391;
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((screenBounds.getWidth() - width) / 4);
            window.setY((screenBounds.getHeight() - height) / 2);

            window.setScene(baseDashboardScene);
            window.setTitle(String.format("%s - %s - Dashboard", title, baseValue));
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
     *
     * @param event
     */
    public void handleCloseButtonClicked() {
        log.info("Application is closing.");
        Platform.exit();
    }
}
