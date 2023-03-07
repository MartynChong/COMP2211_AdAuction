package org.comp2211.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.comp2211.model.ClickCalculator;

public class HomepageController {

    public ClickCalculator clickCalculator = new ClickCalculator();
    public Button refreshButton;
    public Label totalClicksLabel, totalCostLabel, cPCLabel;

    public void handleRefreshButton(){
        System.out.println(clickCalculator.getTotalClicks());
        totalClicksLabel.setText(Integer.toString(clickCalculator.getTotalClicks()));
        totalCostLabel.setText(Double.toString(clickCalculator.getTotalCost()));
        cPCLabel.setText(Double.toString(clickCalculator.getCPC()));
    }

}
