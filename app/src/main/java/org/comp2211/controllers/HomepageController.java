package org.comp2211.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.comp2211.model.ClickCalculator;
import org.comp2211.model.ImpressionCalculator;

public class HomepageController {

    public ClickCalculator clickCalculator = new ClickCalculator();
    public Button refreshButton;
    public Label totalClicksLabel, totalCostLabel, cPCLabel;
    private ImpressionCalculator impressionCalculator = new ImpressionCalculator();
    public Label totalImprLabel, uniqueImprLabel, cPMLabel;

    public void handleRefreshButton(){
        System.out.println(clickCalculator.getTotalClicks());
        totalClicksLabel.setText(Integer.toString(clickCalculator.getTotalClicks()));
        totalCostLabel.setText(Double.toString(clickCalculator.getTotalCost()));
        cPCLabel.setText("£" + clickCalculator.getCPC());
        totalImprLabel.setText(String.valueOf(impressionCalculator.getImpr()));
        uniqueImprLabel.setText(String.valueOf(impressionCalculator.getUniques()));
        cPMLabel.setText("£" + impressionCalculator.getCPM());
    }

}
