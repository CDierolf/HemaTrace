package com.hemaapps.hematrace.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.shapes
 * @Date: Feb 13, 2020
 * @Subclass StatusCircle Description: 
 */
//Imports

//Begin Subclass StatusCircle
public class StatusCircle extends Circle {
    
    private Color color;
    private String toolTip;
    
    public StatusCircle() {}
    public StatusCircle(Color color, double radius, String toolTip) {
        this.setFill(color);
        this.setRadius(radius);
        this.setToolTip(toolTip);
        this.setStroke(Color.BLACK);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    
    
    

} //End Subclass StatusCircle