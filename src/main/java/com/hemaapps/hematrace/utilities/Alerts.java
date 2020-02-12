package com.hemaapps.hematrace.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** 
 * @Course: Applied Software Development SDEV-450
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.UtilityClasses
 * @Date: Jan 30, 2020
 * @Subclass AlertsClass Description: 
 */
//Imports

//Begin Subclass AlertsClass
public class Alerts {
    
    Alert alert;
    AlertType alertType;
    String title;
    String header;
    String content;

    public Alerts() {}
    
    public Alerts(AlertType alertType, String title, String header, 
            String content) {
        this.alertType = alertType;
        this.title = title;
        this.header = header;
        this.content = content;
    }
    
    public void showGenericAlert() {
        alert = new Alert(this.alertType);
        alert.setTitle(this.title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        alert.show();
    }

} //End Subclass AlertsClass