package org.comp2211.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.comp2211.App;



/* This file will be deprecated
*
**/





/**
 * DashBoard window, for increment 1
 */
public class DashboardWindow {

    private final App app;
    private final Scene scene;
    private final BorderPane dashPane;

    /**
     * Initialise this scene.
     *
     * @param app the app.
     */
    public DashboardWindow(App app) {
        this.app = app;
        this.dashPane = new BorderPane();
        Label hello_world = new Label("This is the dashboard window");
        dashPane.setCenter(hello_world);
        this.scene = new Scene(dashPane, 640,480);
    }

    /**
     * Return the scene that has been built.
     *
     * @return dashboard scene
     */
    public Scene getScene() {
        return this.scene;
    }
}
