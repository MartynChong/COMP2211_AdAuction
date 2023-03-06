package org.comp2211;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.comp2211.controllers.DashboardWindow;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    private Stage stage;

    /**
     * Set up the main window.
     *
     * @param stage primary Javafx stage(window) created by Javafx
     */
    @Override
    public void start(Stage stage) {
//        this.stage = stage;
//        stage.setTitle("Ad Auction Dashboard");
//        loadDashBoard();

        launchHomepage(stage);

    }

    private void launchHomepage(Stage stage) {

        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/fxml/HomePage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Ad Auction");
        stage.setScene(new Scene(root, 1200, 900));
        stage.show();

    }

    public void loadDashBoard() {
        var dashboardWindow = new DashboardWindow(this);
        stage.setScene(dashboardWindow.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}