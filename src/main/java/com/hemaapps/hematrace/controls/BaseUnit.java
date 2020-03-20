package com.hemaapps.hematrace.controls;

import com.hemaapps.hematrace.enums.BloodProductStatus;
import com.hemaapps.hematrace.shapes.StatusCircle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.controls
 * @Date: Mar 20, 2020
 * @Subclass BaseUnit Description: 
 */
//Imports

//Begin Subclass BaseUnit
public class BaseUnit extends HBox{
    
    private final String LABEL_STYLE = "-fx-font: 14px 'Arial Bold'; -fx-text-fill: #111;"
            + "-fx-border-width: 2px;";
    
    HBox mainHBox = new HBox();
    HBox unitDetail = new HBox();
    HBox unitStatus = new HBox();
    
    Label unitTypeLabel = new Label();
    Label unitDNLabel = new Label();
    Label statusLabel = new Label("Status");
    
    StatusCircle statusCircle = new StatusCircle();
    
    public BaseUnit(){}
    public BaseUnit(String unitType, String dn, BloodProductStatus bpStatus, StatusCircle statusCircle) {
        this.unitTypeLabel.setText(unitType);
        this.unitDNLabel.setText(dn);
        this.statusCircle = statusCircle;
        buildBaseUnit();
    }
    
    public void buildBaseUnit() {
        buildUnitDetailHBox();
        buildUnitStatusHBox();
        buildMainHBox();
    }
    
    private void buildMainHBox() {
        mainHBox.setSpacing(200.0);
        mainHBox.setMaxWidth(537.0);
        mainHBox.setMaxHeight(36.0);
        mainHBox.setAlignment(Pos.CENTER_LEFT);
        
        mainHBox.getChildren().addAll(unitDetail, unitStatus);
    }
    
    private void buildUnitDetailHBox() {
        unitDetail.setSpacing(30.0);
        unitDetail.setMaxWidth(350.0);
        unitDetail.setMaxHeight(36.0);
        unitDetail.setAlignment(Pos.CENTER_LEFT);
        
        unitTypeLabel.setMaxWidth(114.0);
        unitTypeLabel.setMaxHeight(17.0);
        unitTypeLabel.setStyle(LABEL_STYLE);
        unitTypeLabel.setAlignment(Pos.CENTER_LEFT);
        
        unitDNLabel.setMaxWidth(163.0);
        unitDNLabel.setMaxHeight(17.0);
        unitDNLabel.setStyle(LABEL_STYLE);
        unitDNLabel.setAlignment(Pos.CENTER_LEFT);
        
        unitDetail.getChildren().addAll(unitTypeLabel, unitDNLabel);
    }
    
    private void buildUnitStatusHBox() {
        unitStatus.setSpacing(60.0);
        unitStatus.setMaxWidth(200.0);
        unitStatus.setMaxHeight(36.0);
        unitStatus.setAlignment(Pos.CENTER_RIGHT);
        
        statusLabel.setMaxWidth(50.0);
        statusLabel.setMaxHeight(17);
        statusLabel.setStyle(LABEL_STYLE);
        statusLabel.setAlignment(Pos.CENTER_RIGHT);
        
        unitStatus.getChildren().addAll(statusLabel, statusCircle);
        
    }

    public HBox getMainHBox() {
        return mainHBox;
    }

    public void setMainHBox(HBox mainHBox) {
        this.mainHBox = mainHBox;
    }

    public HBox getUnitDetail() {
        return unitDetail;
    }

    public void setUnitDetail(HBox unitDetail) {
        this.unitDetail = unitDetail;
    }

    public HBox getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(HBox unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Label getUnitTypeLabel() {
        return unitTypeLabel;
    }

    public void setUnitTypeLabel(Label unitTypeLabel) {
        this.unitTypeLabel = unitTypeLabel;
    }

    public Label getUnitDNLabel() {
        return unitDNLabel;
    }

    public void setUnitDNLabel(Label unitDNLabel) {
        this.unitDNLabel = unitDNLabel;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public StatusCircle getStatusCircle() {
        return statusCircle;
    }

    public void setStatusCircle(StatusCircle statusCircle) {
        this.statusCircle = statusCircle;
    }

} //End Subclass BaseUnit