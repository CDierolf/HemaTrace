package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.utilities.FormUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Christopher Dierolf
 */
public class BaseDataTransactionTableViewController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button baseInfoButton;
    @FXML
    private TableView transactionTableView;
    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane tableViewScrollPane;

    private BaseDAO baseDao;
    private BaseTransactionDAO baseTransactionDAO = new BaseTransactionDAO();
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    private String baseName;
    private int baseId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseDao = BaseDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDataTransactionTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initData(String baseName) throws SQLException {
        this.baseName = baseName;
        System.out.println("BASENAME: " + baseName);
        this.baseId = baseDao.getBaseIdFromMap(baseName);
        System.out.println("BASEID: " + baseId);
        getTransactionData();
        adjustTableView();
        addTableViewData();
    }

    /**
     * Retrieve the data from the database using the BaseTransactionDAO object
     * into a list object.
     *
     * @throws SQLException
     */
    private void getTransactionData() throws SQLException {
        baseTransactionDAO.setBaseId(this.baseId);
        baseTransactionDAO.setTransactions();
        this.transactionList = baseTransactionDAO.getTransactionList();
    }

    /**
     * Modifies global settings for the BaseDataTransactionTableView transactionTableView
     */
    private void adjustTableView() {
        transactionTableView.setEditable(false);
        transactionTableView.setStyle("-fx-alignment: CENTER");
    }

    /**
     * Adds live data to the BaseDashboardView table view.
     *
     */
    private void addTableViewData() {
        TableColumn<Integer, Transaction> transIdColumn = new TableColumn<>("Transaction Id");
        TableColumn<Integer, Transaction> prodIdColumn = new TableColumn<>("Product Id");
        TableColumn<String, Transaction> baseColumn = new TableColumn<>("Base");
        TableColumn<String, Transaction> crewmemberColumn = new TableColumn<>("Crewmember");
        TableColumn<String, Transaction> donorNumberColumn = new TableColumn<>("Donor Number");
        TableColumn<String, Transaction> productTypeColumn = new TableColumn<>("Product Type");
        TableColumn<String, Transaction> transTypeColumn = new TableColumn<>("Transaction Type");
        TableColumn<Date, Transaction> transDateColumn = new TableColumn<>("Transaction Date/Time");

        transIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        baseColumn.setCellValueFactory(new PropertyValueFactory<>("base"));
        crewmemberColumn.setCellValueFactory(new PropertyValueFactory<>("crewmember"));
        donorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("donorNumber"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        transTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDateTime"));
        transactionTableView.getColumns().addAll(transIdColumn, prodIdColumn, baseColumn,
                crewmemberColumn, donorNumberColumn, productTypeColumn, transTypeColumn,
                transDateColumn);

        for (Transaction t : transactionList) {
            transactionTableView.getItems().add(t);
        }

    }
    
    public void handleCloseButtonClicked() {
        FormUtils.closeWindow((Stage) this.closeButton.getScene().getWindow());
    }
    
    public void handleBaseInformationButtonClicked() throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../BaseInformationView.fxml"));
        Parent baseInfoViewParent = loader.load();
        Scene baseInfoView = new Scene(baseInfoViewParent);
        Stage window = new Stage();
        Stage ownerStage = (Stage)this.baseInfoButton.getScene().getWindow();
        BaseInformationViewController controller = loader.getController();
        controller.initData(baseName);
        window.initOwner(ownerStage);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(baseInfoView);
        window.setTitle(baseName + " - Information");
        window.setResizable(false);
        window.showAndWait();
    }

}
