/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christopher K. Dierolf
 */
public class BaseDashboardViewController implements Initializable {

    private final String title = "HemaTrace";
    private final String LABEL_STYLE = "-fx-text-fill: #111; -fx-font: normal bold 20px 'Arial Bold'";
    private final String COL_LABEL_STYLE = "-fx-text-fill: #111; -fx-font: normal bold 30px 'Arial Bold'";
    private final int COLUMN_COUNT = 7;
    @FXML
    private Label baseIdentifierLabel;
    @FXML
    private GridPane productGridPane;
    @FXML
    private VBox gridPaneVBox;

    int numBaseProducts = 0;
    int baseId;
    String baseLocation = "";
    private final BaseProductsDAO dao = new BaseProductsDAO();
    BaseDAO baseDAO;

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Initialize base data. Get the BaseDAO Singleton Instance Get the baseID
     * based on the Base Location
     *
     * @param baseLocation
     */
    public void initData(String baseLocation) {

        try {
            baseDAO = BaseDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.baseLocation = baseLocation;
        this.baseIdentifierLabel.setText(this.baseLocation);
        this.baseId = baseDAO.getBaseIdFromMapWithBaseName(baseLocation.toLowerCase().trim());
        getNumberOfBaseProducts();
        getBaseProducts();
        adjustGridPaneForBase();
    }

    public void handleLogoutButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../BaseLoginView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent baseLoginViewParent = loader.load();
        Scene baseLoginView = new Scene(baseLoginViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(baseLoginView);
        window.setTitle(title + "Base Login");
        window.setResizable(false);
        window.show();
    }

    private void adjustGridPaneForBase() {
        productGridPane.setVgap(10);
        productGridPane.setHgap(10);

        addColumnLabels();
        addUnitLabels();
        addDonorNumbersAndTypes();
        addExpDateAndObtainedDate();
    }

    private void addColumnLabels() {
        Label unitNumLabel = new Label("Unit #");
        unitNumLabel.setAlignment(Pos.CENTER);
        unitNumLabel.setStyle(COL_LABEL_STYLE);
        unitNumLabel.setPadding(new Insets(20, 20, 20, 200));
        productGridPane.add(unitNumLabel, 0, 0);

    }

    private void addUnitLabels() {
        for (int i = 0; i < numBaseProducts; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / i);
            Label unitLabel = new Label("Unit: " + (i + 1));
            unitLabel.setAlignment(Pos.CENTER_LEFT);
            unitLabel.setPadding(new Insets(10));
            unitLabel.setStyle(LABEL_STYLE);
            productGridPane.add(unitLabel, 0, i + 1);
        }
    }

    private void addDonorNumbersAndTypes() {
        int numProductsDisplayed = 0;
        int numPlasmaProducts = dao.getBasePlasmaProducts().size();
        int numPRBCProducts = dao.getBasePRBCProducts().size();
        for (int i = 0; i < numPlasmaProducts; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 7);
            Label donorNumberLabel = new Label(dao.getBasePlasmaProducts().get(i).getDonorNumber());
            Label productTypeLabel = new Label("Plasma");
            donorNumberLabel.setAlignment(Pos.CENTER_LEFT);
            donorNumberLabel.setPadding(new Insets(10));
            donorNumberLabel.setStyle(LABEL_STYLE);
            productTypeLabel.setAlignment(Pos.CENTER_LEFT);
            productTypeLabel.setPadding(new Insets(10));
            productTypeLabel.setStyle(LABEL_STYLE);

            productGridPane.add(donorNumberLabel, 1, numProductsDisplayed);
            productGridPane.add(productTypeLabel, 2, numProductsDisplayed);
            numProductsDisplayed++;

        }

        for (int i = 0; i < numPRBCProducts; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 7);
            Label donorNumberLabel = new Label(dao.getBasePRBCProducts().get(i).getDonorNumber());
            Label productTypeLabel = new Label("PRBC");
            donorNumberLabel.setAlignment(Pos.CENTER_LEFT);
            donorNumberLabel.setPadding(new Insets(10));
            donorNumberLabel.setStyle(LABEL_STYLE);
            productTypeLabel.setAlignment(Pos.CENTER_LEFT);
            productTypeLabel.setPadding(new Insets(10));
            productTypeLabel.setStyle(LABEL_STYLE);
            productGridPane.add(donorNumberLabel, 1, numProductsDisplayed);
            productGridPane.add(productTypeLabel, 2, numProductsDisplayed);
            numProductsDisplayed++;
        }

        System.out.println(numProductsDisplayed);

    }

    private void addExpDateAndObtainedDate() {
        int numProductsDisplayed = 0;
        int numPlasmaProducts = dao.getBasePlasmaProducts().size();
        int numPRBCProducts = dao.getBasePRBCProducts().size();
        for (int i = 0; i < numPlasmaProducts; i++) {
            Label expirationDateLabel = new Label(dao.getBasePlasmaProducts().get(i).getExpirationDate().toString());
            Label obtainedDateLabel = new Label(dao.getBasePlasmaProducts().get(i).getObtainedDate().toString());
            expirationDateLabel.setAlignment(Pos.CENTER_LEFT);
            expirationDateLabel.setPadding(new Insets(10));
            expirationDateLabel.setStyle(LABEL_STYLE);
            obtainedDateLabel.setAlignment(Pos.CENTER_LEFT);
            obtainedDateLabel.setPadding(new Insets(10));
            obtainedDateLabel.setStyle(LABEL_STYLE);

            productGridPane.add(expirationDateLabel, 3, numProductsDisplayed);
            productGridPane.add(obtainedDateLabel, 4, numProductsDisplayed);
            numProductsDisplayed++;

        }
        for (int i = 0; i < numPRBCProducts; i++) {
            Label expirationDateLabel = new Label(dao.getBasePRBCProducts().get(i).getExpirationDate().toString());
            Label obtainedDateLabel = new Label(dao.getBasePRBCProducts().get(i).getObtainedDate().toString());
            expirationDateLabel.setAlignment(Pos.CENTER_LEFT);
            expirationDateLabel.setPadding(new Insets(10));
            expirationDateLabel.setStyle(LABEL_STYLE);
            obtainedDateLabel.setAlignment(Pos.CENTER_LEFT);
            obtainedDateLabel.setPadding(new Insets(10));
            obtainedDateLabel.setStyle(LABEL_STYLE);

            productGridPane.add(expirationDateLabel, 3, numProductsDisplayed);
            productGridPane.add(obtainedDateLabel, 4, numProductsDisplayed);
            numProductsDisplayed++;

        }
    }

    private void getNumberOfBaseProducts() {
        BaseProductsDAO dao = new BaseProductsDAO();
        try {
            this.numBaseProducts = dao.getCurrentNumberOfProductsForBase(2);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getBaseProducts() {
        dao.getBaseBloodProductsResultSet(this.baseId);

    }

}
