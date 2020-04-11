package com.hemaapps.hematrace.utilities;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.utilities
 * @Date: Mar 28, 2020
 * @Subclass FormUtils Description: 
 */
//Imports

//Begin Subclass FormUtils
public class FormUtils {
    
    public static void closeWindow(Stage stage) {
       stage.close();
    }
    
    public static Boolean validateAllFieldsFilled(List<Node> nodeList) {
        Alerts alerts = new Alerts();
        Boolean valid = Boolean.FALSE;
        for (Node n : nodeList) {
            if (n instanceof TextField) {
                if (!((TextField) n).getText().isBlank() || !((TextField) n).getText().isEmpty()) {
                    valid = Boolean.TRUE;
                } else {
                    valid = Boolean.FALSE;
                    break;
                }
            } if (n instanceof ComboBox) {
                if (((ComboBox) n).getValue() != null) {
                    valid = Boolean.TRUE;
                } else {
                    valid = Boolean.FALSE;
                }
            }
        }
        
        return valid;
    }
    public static void clearTextPasswordFields(List<Node> nodeList) {
        for (Node n : nodeList) {
            if (n instanceof TextField) {
                ((TextField) n).clear();
            }
            if (n instanceof PasswordField) {
                ((PasswordField) n).clear();
            }
        }
    }

} //End Subclass FormUtils