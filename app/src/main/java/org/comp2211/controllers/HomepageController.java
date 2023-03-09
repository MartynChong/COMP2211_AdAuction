package org.comp2211.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.comp2211.model.*;

import java.io.File;

public class HomepageController {

    public ClickCalculator clickCalculator = new ClickCalculator();
    private final ClickImporter clickImporter = new ClickImporter();
    public Button refreshButton;
    public Label totalClicksLabel, totalCostLabel, cPCLabel;
    private final ImpressionCalculator impressionCalculator = new ImpressionCalculator();
    private final ImpressionImporter imprImporter = new ImpressionImporter();
    private final ServerLogImporter serverLogImporter = new ServerLogImporter();
    private final ServerLogCalculator serverLogCalc = new ServerLogCalculator();
    public Label totalImprLabel, uniqueImprLabel, cPMLabel;
    public Label bounceLabel, conversionLabel, ctrLabel, cpaLabel, bounceRateLabel;
    private final Stage fileChooserStage = new Stage();
    FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter(".csv files", "*.csv");
    FileChooser fileChooser = new FileChooser();

    public void handleRefreshButton(){
        totalClicksLabel.setText(Integer.toString(clickCalculator.getTotalClicks()));
        totalCostLabel.setText("£" + String.format("%.02f", clickCalculator.getTotalCost() / 100));
        cPCLabel.setText(clickCalculator.getCPC() + " pence");
        totalImprLabel.setText(String.valueOf(impressionCalculator.getImpr()));
        uniqueImprLabel.setText(String.valueOf(clickCalculator.getUniqueClicks()));
        cPMLabel.setText(String.format("%.02f",impressionCalculator.getCPM())+ " pence");
        bounceLabel.setText(String.valueOf(serverLogCalc.getBounce()));
        conversionLabel.setText(String.valueOf(serverLogCalc.getConver()));
        ctrLabel.setText(String.format("%.02f",clickCalculator.getCTR()) + "%");
        cpaLabel.setText(String.format("%.02f",serverLogCalc.getCPA())+ " pence");
        bounceRateLabel.setText(String.format("%.02f", serverLogCalc.getBounceRate()) + "%");
    }

    public void chooseClickFile() {
        fileChooser.getExtensionFilters().add(csvFilter);
        File clickFile = fileChooser.showOpenDialog(fileChooserStage);
        if (clickFile != null) {
            if (clickImporter.isValid(clickFile.getPath())) {
                clickImporter.setFilePath(clickFile.getPath());
                clickImporter.initialise();
                clickImporter.insertRecord();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The file provided is not a valid click file.");
                error.setHeaderText("Invalid format");
                error.showAndWait();
            }
        }
    }

    public void chooseImprFile() {
        fileChooser.getExtensionFilters().add(csvFilter);
        File imprFile = fileChooser.showOpenDialog(fileChooserStage);
        if (imprFile != null) {
            if (imprImporter.isValid(imprFile.getPath())) {
                imprImporter.setFilePath(imprFile.getPath());
                imprImporter.initialise();
                imprImporter.insertRecord();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The file provided is not a valid impression file.");
                error.setHeaderText("Invalid format");
                error.showAndWait();
            }
        }
    }

    public void chooseServerLogFile() {
        fileChooser.getExtensionFilters().add(csvFilter);
        File serverLogFile = fileChooser.showOpenDialog(fileChooserStage);
        if (serverLogFile != null) {
            if (serverLogImporter.isValid(serverLogFile.getPath())) {
                serverLogImporter.setFilePath(serverLogFile.getPath());
                serverLogImporter.initialise();
                serverLogImporter.insertRecord();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("The file provided is not a valid server log file.");
                error.setHeaderText("Invalid format");
                error.showAndWait();
            }
        }
    }
}
