package com.hemaapps.hematrace.controls;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.controls
 * @Date: Mar 5, 2020
 * @Subclass UnitCheckinControls Description: 
 */
//Imports

//Begin Subclass UnitCheckinControls
public class UnitCheckInControls extends VBox{
    
    private BaseProductsDAO baseProductsDao;
    
    private final double VBOX_HEIGHT = 58.0;
    private final double VBOX_WIDTH = 487.0;
    private final double VBOX_SPACING = 2.0;
    private final double VBOX_PADDING = 2.0;
    private final double TEXT_FIELD_WIDTH = 250.0;
    private final double COMBO_BOX_WIDTH = 250.0;
    private final String LABEL_STYLE = "-fx-font: 16px 'Arial Bold'; -fx-text-fill: #FFF;"
            + "-fx-border-width: 2px;";
    
    private TextField unitDNTextField = new TextField();
    private Label unitLabel = new Label();
    private ComboBox dispositionComboBox = new ComboBox();

    public TextField getUnitDNTextField() {
        return unitDNTextField;
    }
    public ComboBox getDispositionComboBox() {
        return dispositionComboBox;
    }
    
    public UnitCheckInControls(String unitLabelText, String dnText) throws SQLException {
        baseProductsDao = BaseProductsDAO.getInstance();
        this.unitDNTextField.setText(dnText);
        this.unitLabel.setText(unitLabelText);
        buildVBox();
    }
    
    private void buildVBox() {
        this.setMaxHeight(VBOX_HEIGHT);
        this.setMaxWidth(VBOX_WIDTH);
        this.setSpacing(VBOX_SPACING);
        this.setPadding(new Insets(VBOX_PADDING));
        unitLabel.setStyle(LABEL_STYLE);
        unitDNTextField.setMaxWidth(TEXT_FIELD_WIDTH);
        unitDNTextField.setDisable(true);
        dispositionComboBox.setMaxWidth(COMBO_BOX_WIDTH);
        for (String s : baseProductsDao.getProductDispositions()) {
            dispositionComboBox.getItems().add(s);
        }
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(unitLabel, unitDNTextField, dispositionComboBox);
        
        
    }
    
    
    

} //End Subclass UnitCheckinControls