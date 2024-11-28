package com.example.demo.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main entry point of the Sky Battle game application.
 * This class extends the JavaFX Application class and is responsible for setting up the main game window.
 */
public class Main extends Application {

    /**
     * Default constructor for the Main class.
     * Initializes the application.
     */
    public Main() {
        // Default constructor, nothing to initialize here.
    }

    /**
     * The main entry point for the JavaFX application.
     * It initializes the primary stage, loads the main FXML layout, and sets the scene for the game.
     *
     * @param primaryStage The main stage for the application window.
     * @throws Exception If there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the main layout
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
        Parent root = fxmlLoader.load();

        // Set the scene with the loaded layout and fixed window size
        primaryStage.setScene(new Scene(root, 1300, 750));
        primaryStage.setResizable(false); // Disable resizing the window
        primaryStage.setTitle("Sky Battle"); // Set the title of the window

        // Show the primary stage (window)
        primaryStage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     * It calls the launch method which triggers the start method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}