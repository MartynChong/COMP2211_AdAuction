package org.comp2211.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.comp2211.model.*;

import java.io.File;

public class HomepageController {

    public ClickCalculator clickCalculator = new ClickCalculator();
    private ClickImporter clickImporter = new ClickImporter();
    public Button refreshButton;
    public Label totalClicksLabel, totalCostLabel, cPCLabel;
    private ImpressionCalculator impressionCalculator = new ImpressionCalculator();
    private ImpressionImporter imprImporter = new ImpressionImporter();
    private ServerLogImporter serverLogImporter = new ServerLogImporter();
    private ServerLogCalculator serverLogCalc = new ServerLogCalculator();
    public Label totalImprLabel, uniqueImprLabel, cPMLabel;
    public Label bounceLabel, conversionLabel, ctrLabel, cpaLabel, bounceRateLabel;
    private Stage fileChooserStage = new Stage();
    FileChooser fileChooser = new FileChooser();


    public void handleRefreshButton(){
        System.out.println(clickCalculator.getTotalClicks());
        totalClicksLabel.setText(Integer.toString(clickCalculator.getTotalClicks()));
        totalCostLabel.setText("£" + clickCalculator.getTotalCost());
        cPCLabel.setText("£" + clickCalculator.getCPC());
        totalImprLabel.setText(String.valueOf(impressionCalculator.getImpr()));
        uniqueImprLabel.setText(String.valueOf(impressionCalculator.getUniques()));
        cPMLabel.setText("£" + String.format("%.02f",impressionCalculator.getCPM()));
        bounceLabel.setText(String.valueOf(serverLogCalc.getBounce()));
        conversionLabel.setText(String.valueOf(serverLogCalc.getConver()));
        ctrLabel.setText(String.format("%.02f",impressionCalculator.getCTR()));
        cpaLabel.setText(String.format("%.02f",serverLogCalc.getCPA()));
        bounceRateLabel.setText(String.format("%.02f", serverLogCalc.getBounceRate()));
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

    public void chooseServerLogFile() {
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter(".csv files", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File serverLogFile = fileChooser.showOpenDialog(fileChooserStage);
        serverLogImporter.setFilePath(serverLogFile.getPath());
        serverLogImporter.initialise();
        serverLogImporter.insertRecord();
    }
}
