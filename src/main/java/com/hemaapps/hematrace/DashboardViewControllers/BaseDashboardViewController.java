/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hemaapps.hematrace.DashboardViewControllers;

import com.hemaapps.hematrace.DAO.BaseDAO;
import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.Model.Transaction;
import com.hemaapps.hematrace.enums.BloodProductStatus;
import com.hemaapps.hematrace.shapes.StatusCircle;
import com.hemaapps.hematrace.utilities.ExpiryStatusUtil;
import java.awt.MouseInfo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * FXML: BaseDashboardViewController.fxml
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
    private TableView transactionTableView;
    @FXML
    private Circle logoutButton;
    @FXML
    private Circle newTransactionButton;

    private int numBaseProducts = 0;
    private int baseId;
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    String baseLocation = "";
    private BaseProductsDAO baseProductsDao;
    private final BaseTransactionDAO baseTransactionDAO = new BaseTransactionDAO();
    private BaseDAO baseDAO;

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            baseProductsDao = BaseProductsDAO.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setHandlers();
    }

    /**
     * Initialize base data. Get the BaseDAO Singleton Instance Get the baseID
     * based on the Base Location
     *
     * @param baseLocation
     */
    public void initData(String baseLocation) throws SQLException, ParseException {

        try {
            baseDAO = BaseDAO.getInstance();
            baseDAO.setBaseValue(baseLocation);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.baseLocation = baseLocation;
        this.baseIdentifierLabel.setText(this.baseLocation);
        this.baseId = baseDAO.getBaseIdFromMapWithBaseNameSetInstance(baseLocation.toLowerCase().trim());
        getNumberOfBaseProducts();
        getBaseProducts();
        adjustGridPaneForBase();
        getTransactionData();
        adjustTableView();
        addTableViewData();
    }

    /**
     * Handles the logout button click event.
     * @param event
     * @throws IOException 
     */
    public void handleLogoutButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../BaseLoginView.fxml"));
        Parent baseLoginViewParent = loader.load();
        Scene baseLoginView = new Scene(baseLoginViewParent);
        Stage window =(Stage)productGridPane.getScene().getWindow();

        window.setScene(baseLoginView);
        window.setTitle(title + "Base Login");
        window.setResizable(false);
        window.show();
    }
    
    public void handleCreateTransactionButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../CreateTransactionView.fxml"));
        Parent createTransactionViewParent = loader.load();
        Scene createTransactionView = new Scene(createTransactionViewParent);
        Stage window = new Stage();
        
        window.setScene(createTransactionView);
        window.setTitle(title + "- " + this.baseLocation + " -Create Transaction");
        window.setResizable(false);
        window.show();
    }

    /**
     * Adjust the global settings for the product gridpane.
     */
    private void adjustGridPaneForBase() {
        productGridPane.setVgap(10);
        productGridPane.setHgap(10);
        productGridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < numBaseProducts; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(10.0);
            rc.setMaxHeight(50.0);
            rc.setValignment(VPos.CENTER);

        }

        for (int i = 0; i < COLUMN_COUNT; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(30);
            cc.setMaxWidth(75);
            cc.setHalignment(HPos.CENTER);
        }

        addColumnLabels();
        addUnitLabels();
        addDonorNumbersAndTypes();
        addExpDateAndObtainedDate();
        addStatusCircleShape();
    }

    /**
     * Create column headers
     */
    private void addColumnLabels() {
        Label unitNumLabel = new Label("Unit #");
        unitNumLabel.setStyle(COL_LABEL_STYLE);
        unitNumLabel.setPadding(new Insets(10));
        productGridPane.add(unitNumLabel, 0, 0);
        Label donorNumLabel = new Label("Donor #");
        donorNumLabel.setStyle(COL_LABEL_STYLE);
        donorNumLabel.setPadding(new Insets(10));
        productGridPane.add(donorNumLabel, 1, 0);
        Label productTypeLabel = new Label("Product Type");
        productTypeLabel.setStyle(COL_LABEL_STYLE);
        productTypeLabel.setPadding(new Insets(10));
        productGridPane.add(productTypeLabel, 2, 0);
        Label expDateLabel = new Label("Expiration Date");
        expDateLabel.setStyle(COL_LABEL_STYLE);
        expDateLabel.setPadding(new Insets(10));
        productGridPane.add(expDateLabel, 3, 0);
        Label obtainedDateLabel = new Label("Date Obtained");
        obtainedDateLabel.setStyle(COL_LABEL_STYLE);
        obtainedDateLabel.setPadding(new Insets(10));
        productGridPane.add(obtainedDateLabel, 4, 0);
        Label productCircleStatusLabel = new Label("Status");
        productCircleStatusLabel.setStyle(COL_LABEL_STYLE);
        productCircleStatusLabel.setPadding(new Insets(10));
        productGridPane.add(productCircleStatusLabel, 5, 0);

    }

    /** Display the unit labels 
     * 
     */
    private void addUnitLabels() {
        for (int i = 0; i < numBaseProducts; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / i);
            Label unitLabel = new Label("Unit: " + (i + 1));
            unitLabel.setPadding(new Insets(10));
            unitLabel.setStyle(LABEL_STYLE);
            productGridPane.add(unitLabel, 0, i + 1);
        }
    }

    /**
     * Display the donor numbers and types of the blood products into the
     * gridpane.
     */
    private void addDonorNumbersAndTypes() {
        int numProductsDisplayed = 1;
        int numPlasmaProducts = baseProductsDao.getBasePlasmaProducts().size();
        int numPRBCProducts = baseProductsDao.getBasePRBCProducts().size();
        for (int i = 0; i < numPlasmaProducts; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 7);
            Label donorNumberLabel = new Label(baseProductsDao.getBasePlasmaProducts().get(i).getDonorNumber());
            Label productTypeLabel = new Label("Plasma");
            donorNumberLabel.setPadding(new Insets(10));
            donorNumberLabel.setStyle(LABEL_STYLE);
            productTypeLabel.setPadding(new Insets(10));
            productTypeLabel.setStyle(LABEL_STYLE);

            productGridPane.add(donorNumberLabel, 1, numProductsDisplayed);
            productGridPane.add(productTypeLabel, 2, numProductsDisplayed);
            numProductsDisplayed++;

        }

        for (int i = 0; i < numPRBCProducts; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / 7);
            Label donorNumberLabel = new Label(baseProductsDao.getBasePRBCProducts().get(i).getDonorNumber());
            Label productTypeLabel = new Label("PRBC");
            donorNumberLabel.setPadding(new Insets(10));
            donorNumberLabel.setStyle(LABEL_STYLE);
            productTypeLabel.setPadding(new Insets(10));
            productTypeLabel.setStyle(LABEL_STYLE);
            productGridPane.add(donorNumberLabel, 1, numProductsDisplayed);
            productGridPane.add(productTypeLabel, 2, numProductsDisplayed);
            numProductsDisplayed++;
        }
    }

    /**
     * Display the expiration and obtained dates in the gridpane.
     */
    private void addExpDateAndObtainedDate() {
        int numProductsDisplayed = 1;
        int numPlasmaProducts = baseProductsDao.getBasePlasmaProducts().size();
        int numPRBCProducts = baseProductsDao.getBasePRBCProducts().size();
        for (int i = 0; i < numPlasmaProducts; i++) {
            Label expirationDateLabel = new Label(baseProductsDao.getBasePlasmaProducts().get(i).getExpirationDate().toString());
            Label obtainedDateLabel = new Label(baseProductsDao.getBasePlasmaProducts().get(i).getObtainedDate().toString());
            expirationDateLabel.setPadding(new Insets(10));
            expirationDateLabel.setStyle(LABEL_STYLE);
            obtainedDateLabel.setPadding(new Insets(10));
            obtainedDateLabel.setStyle(LABEL_STYLE);

            productGridPane.add(expirationDateLabel, 3, numProductsDisplayed);
            productGridPane.add(obtainedDateLabel, 4, numProductsDisplayed);
            numProductsDisplayed++;

        }
        for (int i = 0; i < numPRBCProducts; i++) {
            Label expirationDateLabel = new Label(baseProductsDao.getBasePRBCProducts().get(i).getExpirationDate().toString());
            Label obtainedDateLabel = new Label(baseProductsDao.getBasePRBCProducts().get(i).getObtainedDate().toString());
            expirationDateLabel.setPadding(new Insets(10));
            expirationDateLabel.setStyle(LABEL_STYLE);
            obtainedDateLabel.setPadding(new Insets(10));
            obtainedDateLabel.setStyle(LABEL_STYLE);

            productGridPane.add(expirationDateLabel, 3, numProductsDisplayed);
            productGridPane.add(obtainedDateLabel, 4, numProductsDisplayed);
            numProductsDisplayed++;

        }
    }

    /**
     * Create a status circle for each base product and display it
     * in the gridpane.
     */
    private void addStatusCircleShape() {
        int numProductsDisplayed = 1;
        int numPlasmaProducts = baseProductsDao.getBasePlasmaProducts().size();
        int numPRBCProducts = baseProductsDao.getBasePRBCProducts().size();
        for (int i = 0; i < numPlasmaProducts; i++) {
            StatusCircle statusCircle = new StatusCircle();
            
            BloodProductStatus bpStatus = ExpiryStatusUtil.getExpiryStatus(baseProductsDao.getBasePlasmaProducts().get(i).getExpirationDate());
            if (bpStatus == BloodProductStatus.EXPIRED) {
                statusCircle.setFill(Color.RED);
                statusCircle.setToolTip("Product is expired");
            } else if (bpStatus == BloodProductStatus.EXPIRING) {
                statusCircle.setFill(Color.YELLOW);
                statusCircle.setToolTip("Product is expiring.");
            } else if (bpStatus == BloodProductStatus.OK){
                statusCircle.setFill(Color.GREEN);
                statusCircle.setToolTip("Product is OK");
            } else if (bpStatus == BloodProductStatus.NOT_AVAILABLE) {
                statusCircle.setFill(Color.BLACK);
                statusCircle.setToolTip("Product status is not available");
            }
            statusCircle.setRadius(15.0);
            productGridPane.add(statusCircle, 5, numProductsDisplayed);
            numProductsDisplayed++;
        }
        for (int i = 0; i < numPRBCProducts; i++) {
            StatusCircle statusCircle = new StatusCircle();
             BloodProductStatus bpStatus = ExpiryStatusUtil.getExpiryStatus(baseProductsDao.getBasePRBCProducts().get(i).getExpirationDate());
            if (bpStatus == BloodProductStatus.EXPIRED) {
                statusCircle.setFill(Color.RED);
                statusCircle.setToolTip("Product is expired");
            } else if (bpStatus == BloodProductStatus.EXPIRING) {
                statusCircle.setFill(Color.YELLOW);
                statusCircle.setToolTip("Product is expiring.");
            } else if (bpStatus == BloodProductStatus.OK){
                statusCircle.setFill(Color.GREEN);
                statusCircle.setToolTip("Product is OK");
            } else if (bpStatus == BloodProductStatus.NOT_AVAILABLE) {
                statusCircle.setFill(Color.BLACK);
                statusCircle.setToolTip("Product status is not available");
            }
            statusCircle.setRadius(15.0);
            statusCircle.setToolTip("Product is expired.");
            productGridPane.add(statusCircle, 5, numProductsDisplayed);
            numProductsDisplayed++;
        }
    }

    /**
     * Retrieve the number of base products to allow GridPane adjustment.
     */
    private void getNumberOfBaseProducts() throws ParseException {
        try {
            this.numBaseProducts = baseProductsDao.getCurrentNumberOfProductsForBase(2);
            this.baseDAO.setNumBaseProducts(this.numBaseProducts);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve the current base products through the BaseProductsDAO
     */
    private void getBaseProducts() {
        baseProductsDao.setBaseBloodProductsResultSet(baseId);

    }

    /**
     * Retrieve the data from the database using the BaseTransactionDAO object
     * into a list object.
     * @throws SQLException 
     */
    private void getTransactionData() throws SQLException {
        baseTransactionDAO.setBaseId(this.baseId);
        baseTransactionDAO.setTransactions();
        this.transactionList = baseTransactionDAO.getTransactionList();
    }

    /**
     * Modifies global settings for the BaseDashboardView transactionTableView
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
    
    /**
     * private method to set all of the handlers for the BaseDashboardViewController
     */
    private void setHandlers() {
        // Shows a popover containing a more detailed view of 
        // the hovered over transaction row.
//        transactionTableView.setRowFactory(tableView -> {
//            final TableRow<Transaction> row = new TableRow<>();
//            
//            TransactionPopover tPop = new TransactionPopover();
//            row.setOnMouseEntered(MouseEvent -> {
//                final Transaction transaction = row.getItem();
//                    java.awt.Point p = returnMousePosition();
//                    tPop.showPopOver(row, transaction.getDonorNumber(), p.x, p.y);
//            });
//            
//            row.setOnMouseExited(MouseEvent -> {
//                tPop.goAwayPopOver();
//            });
//            
//            return row;
//        });

        // Change the shape color of the top buttons
        logoutButton.setOnMouseEntered(MouseEvent -> {
            logoutButton.setStyle("-fx-fill: #000");
        });
        logoutButton.setOnMouseExited(MouseEvent -> {
            logoutButton.setStyle("-fx-fille: #111");
        });
        newTransactionButton.setOnMouseEntered(MouseEvent -> {
            newTransactionButton.setStyle("-fx-fill: #000");
        });
        newTransactionButton.setOnMouseExited(MouseEvent -> {
            newTransactionButton.setStyle("-fx-fille: #111");
        });
        newTransactionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent MouseEvent) {
                try {
                    handleCreateTransactionButtonClicked();
                }catch (IOException ex) {
                    Logger.getLogger(BaseDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
                
    }
    
    
    /**
     * Utilizes java AWT library to get the current position of the mouse
     * pointer and returns the x/y coordinates as a Point object.
     * @return 
     */
    private java.awt.Point returnMousePosition() {
    
        return MouseInfo.getPointerInfo().getLocation();
    }

}
