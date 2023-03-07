package org.comp2211.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.comp2211.model.ClickCalculator;
import org.comp2211.model.ClickImporter;
import org.comp2211.model.ImpressionCalculator;
import org.comp2211.model.ImpressionImporter;

import java.io.File;

public class HomepageController {

    public ClickCalculator clickCalculator = new ClickCalculator();
    private ClickImporter clickImporter = new ClickImporter();
    public Button refreshButton;
    public Label totalClicksLabel, totalCostLabel, cPCLabel;
    private ImpressionCalculator impressionCalculator = new ImpressionCalculator();
    private ImpressionImporter imprImporter = new ImpressionImporter();
    public Label totalImprLabel, uniqueImprLabel, cPMLabel;
    private Stage fileChooserStage = new Stage();
    FileChooser fileChooser = new FileChooser();


    public void handleRefreshButton(){
        System.out.println(clickCalculator.getTotalClicks());
        totalClicksLabel.setText(Integer.toString(clickCalculator.getTotalClicks()));
        totalCostLabel.setText("£" + clickCalculator.getTotalCost());
        cPCLabel.setText("£" + clickCalculator.getCPC());
        totalImprLabel.setText(String.valueOf(impressionCalculator.getImpr()));
        uniqueImprLabel.setText(String.valueOf(impressionCalculator.getUniques()));
        cPMLabel.setText("£" + impressionCalculator.getCPM());
    }

    public void chooseClickFile() {
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter(".csv files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File clickFile = fileChooser.showOpenDialog(fileChooserStage);
        clickImporter.setFilePath(clickFile.getPath());
        clickImporter.initialise();
        clickImporter.insertRecord();
    }
    public void chooseImprFile() {
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter(".csv files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File imprFile = fileChooser.showOpenDialog(fileChooserStage);
        imprImporter.setFilePath(imprFile.getPath());
        imprImporter.initialise();
        imprImporter.insertRecord();
    }

}
