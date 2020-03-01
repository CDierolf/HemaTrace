package com.hemaapps.hematrace.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.controls
 * @Date: Feb 25, 2020
 * @Subclass UnitTransactionVBox Description: 
 */
//Imports

//Begin Subclass UnitTransactionVBox
public class UnitTransactionVBox extends VBox {
    
    private final double VBOX_HEIGHT = 58.0;
    private final double VBOX_WIDTH = 487.0;
    private final double VBOX_SPACING = -1;
    private final double VBOX_PADDING = 5;
    private final double TEXT_FIELD_WIDTH = 250;
    private final String LABEL_STYLE = "-fx-font: 16px 'Arial Bold'; -fx-text-fill: #FFF;"
            + "-fx-border-width: 2px;";
    
    
    private VBox unitTransactionVBox;
    private Label unitTransactionLabel;
    private Label invalidLabel;
    private TextField unitTransactionTextField;
    private String unitTransactionLabelText;
    private String unitTransactionTextFieldId;
    private double vBoxHeight;

    public double getvBoxHeight() {
        return vBoxHeight;
    }

    public void setvBoxHeight(double vBoxHeight) {
        this.vBoxHeight = vBoxHeight;
    }

    
    
    
    public UnitTransactionVBox() {}
    public UnitTransactionVBox(String unitTransactionLabelText, String unitTransactionTextFieldId) {
        this.unitTransactionLabelText = unitTransactionLabelText;
        this.unitTransactionTextFieldId = unitTransactionTextFieldId;
        
        buildTransactionVBox();
    }
    
    public Label getInvalidLabel() {
        return invalidLabel;
    }

    public void setInvalidLabel(Label invalidLabel) {
        this.invalidLabel = invalidLabel;
    }

    public String getUnitTransactionLabelText() {
        return unitTransactionLabelText;
    }

    public void setUnitTransactionLabelText(String unitTransactionLabelText) {
        this.unitTransactionLabelText = unitTransactionLabelText;
    }

    public String getUnitTransactionTextFieldId() {
        return unitTransactionTextFieldId;
    }

    public void setUnitTransactionTextFieldId(String unitTransactionTextFieldId) {
        this.unitTransactionTextFieldId = unitTransactionTextFieldId;
    }

    public VBox getUnitTransactionVBox() {
        return unitTransactionVBox;
    }

    public void setUnitTransactionVBox(VBox unitTransactionVBox) {
        this.unitTransactionVBox = unitTransactionVBox;
    }

    public Label getUnitTransactionLabel() {
        return unitTransactionLabel;
    }

    public void setUnitTransactionLabel(Label unitTransactionLabel) {
        this.unitTransactionLabel = unitTransactionLabel;
    }

    public TextField getUnitTransactionTextField() {
        return unitTransactionTextField;
    }

    public void setUnitTransactionTextField(TextField unitTransactionTextField) {
        this.unitTransactionTextField = unitTransactionTextField;
    }
    
    
    
    private void buildTransactionVBox() {
        this.unitTransactionVBox = new VBox();
        this.unitTransactionVBox.setAlignment(Pos.CENTER);
        this.unitTransactionVBox.setMaxHeight(VBOX_HEIGHT);
        this.unitTransactionVBox.setMaxWidth(VBOX_WIDTH);
        this.unitTransactionVBox.setPadding(new Insets(VBOX_PADDING));
        this.unitTransactionVBox.setSpacing(VBOX_SPACING);
        this.unitTransactionLabel = new Label(this.getUnitTransactionLabelText());
        this.unitTransactionLabel.setStyle(LABEL_STYLE);
        this.unitTransactionLabel.setAlignment(Pos.CENTER_LEFT);
        this.invalidLabel = new Label();
        this.invalidLabel.setVisible(false);
        this.unitTransactionTextField = new TextField();
        this.unitTransactionTextField.setMaxWidth(TEXT_FIELD_WIDTH);
        this.unitTransactionTextField.setId(getUnitTransactionTextFieldId());
        this.unitTransactionVBox.getChildren().addAll(unitTransactionLabel, 
                invalidLabel,
                unitTransactionTextField);
    }
    
    

} //End Subclass UnitTransactionVBox