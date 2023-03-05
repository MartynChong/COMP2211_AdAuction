package org.comp2211;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.comp2211.view.DashboardWindow;


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
        this.stage = stage;
        stage.setTitle("Ad Auction Dashboard");
        loadDashBoard();
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