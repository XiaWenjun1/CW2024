package com.example.demo.Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.Level.LevelParent;

/**
 * The Control_Start class is responsible for controlling the game's start process,
 * including launching and transitioning between levels.
 * It handles level loading using reflection, so levels can be dynamically loaded based on class names.
 */
public class Control_Start {
    /**
     * The class name for the first level.
     * This constant stores the fully qualified class name for the first level of the game.
     */
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Level.LevelOne"; // The class name for the first level

    /**
     * The width of the level window.
     * This constant defines the width of the window for the game level.
     */
    private static final double LEVEL_WIDTH = 1300; // Width of the level window

    /**
     * The height of the level window.
     * This constant defines the height of the window for the game level.
     */
    private static final double LEVEL_HEIGHT = 750; // Height of the level window

    /**
     * The main stage for the application.
     * This variable holds the primary stage (window) of the JavaFX application.
     */
    private final Stage stage; // The main stage for the application

    /**
     * The current active level.
     * This variable stores the instance of the current active level in the game.
     * It is used to manage transitions and interactions with the active level.
     */
    private LevelParent currentLevel = null; // Variable to store the current active level

    /**
     * Constructor for Control_Start, initializing the stage.
     *
     * @param stage The primary stage for the game.
     */
    public Control_Start(Stage stage) {
        this.stage = stage;
    }

    /**
     * Launches the game by showing the primary stage and loading the first level.
     *
     * @throws ClassNotFoundException If the level class cannot be found.
     * @throws NoSuchMethodException If the constructor for the level class is not found.
     * @throws SecurityException If there is a security issue while accessing the class.
     * @throws InstantiationException If the class cannot be instantiated.
     * @throws IllegalAccessException If there is an illegal access to the class or constructor.
     * @throws IllegalArgumentException If the constructor receives incorrect arguments.
     * @throws InvocationTargetException If the constructor invocation throws an exception.
     */
    public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.show();
        goToLevel(LEVEL_ONE_CLASS_NAME); // Go to the first level when the game starts
    }

    /**
     * Navigates to the specified level by dynamically loading the class using reflection.
     *
     * @param className The class name of the level to be loaded.
     * @throws ClassNotFoundException If the class cannot be found.
     * @throws NoSuchMethodException If the constructor is not found in the class.
     * @throws SecurityException If there is a security issue when accessing the constructor.
     * @throws InstantiationException If the level class cannot be instantiated.
     * @throws IllegalAccessException If the constructor cannot be accessed.
     * @throws IllegalArgumentException If the constructor arguments are invalid.
     * @throws InvocationTargetException If the constructor invocation fails.
     */
    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // If the current level is already loaded and matches the requested class, return to avoid reloading
        if (currentLevel != null && currentLevel.getClass().getName().equals(className)) {
            return;
        }

        // Create a new instance of the level using reflection
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        currentLevel = (LevelParent) constructor.newInstance(LEVEL_HEIGHT, LEVEL_WIDTH);

        // Add a listener to the current level's currentLevelName property to handle level transitions
        currentLevel.currentLevelNameProperty().addListener((observable, oldValue, newValue) -> {
            try {
                goToLevel(newValue); // When the level name changes, load the new level
            } catch (Exception e) {
                showError(e); // Handle errors when loading the new level
            }
        });

        // Initialize the scene for the current level and set it on the stage
        Scene scene = currentLevel.initializeScene();
        stage.setScene(scene);
        currentLevel.startGame(); // Start the game for the current level
    }

    /**
     * Displays an error alert with the exception details.
     *
     * @param e The exception that occurred during level loading or game transitions.
     */
    private void showError(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(e.getClass().toString()); // Display the exception class type
        alert.show(); // Show the alert to the user
    }
}