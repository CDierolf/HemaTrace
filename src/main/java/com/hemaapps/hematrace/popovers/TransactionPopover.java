package com.hemaapps.hematrace.popovers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.popovers
 * @Date: Feb 18, 2020
 * @Subclass TransactionPopover Description:
 */
//Imports
//Begin Subclass TransactionPopover
public class TransactionPopover {

    PopOver pop = null;
    BorderPane bPane = new BorderPane();

    public TransactionPopover() {
    }

    public void showPopOver(Object o, String dn, int x, int y) {
        Label label = new Label(dn);
        bPane.setTop(setTop());
        VBox vBox = new VBox(label);
        vBox.setMinWidth(75.0);
        vBox.setMinHeight(50.0);
        this.pop = new PopOver(bPane);
        pop.show((Node) o);
        pop.setAnchorX(x);
        pop.setAnchorY(y -50);
        
    }
    
    private Node setTop() {
        Label topLabel = new Label();
        topLabel.setText("Transaction Details");
        topLabel.setStyle("-fx-text-fill: #111; -fx-font: normal bold 20px 'Arial Bold'");
        topLabel.setAlignment(Pos.CENTER);
        
        return topLabel;
    }
    
//    private Node setLeft() {
//        GridPane leftGridPane = new GridPane();
//        Label baseLabel = new Label();
//    }

    public void goAwayPopOver() {
        pop.hide();
    }

} //End Subclass TransactionPopover
