package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.Level.LevelParent;

public class Control_Start implements Observer {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Level.LevelOne";
    private static final double LEVEL_WIDTH = 1300;
    private static final double LEVEL_HEIGHT = 750;
    private final Stage stage;

    // Variable to store the current active level
    private LevelParent currentLevel = null;

    public Control_Start(Stage stage) {
        this.stage = stage;
    }

    public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.show();
        goToLevel(LEVEL_ONE_CLASS_NAME);
    }

    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // If the current level is already loaded and matches the requested class, return to avoid reloading
        if (currentLevel != null && currentLevel.getClass().getName().equals(className)) {
            return;
        }

        // Create a new level instance and assign it to currentLevel
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        currentLevel = (LevelParent) constructor.newInstance(LEVEL_HEIGHT, LEVEL_WIDTH);
        currentLevel.addObserver(this);

        // Initialize the scene and start the game
        Scene scene = currentLevel.initializeScene();
        stage.setScene(scene);
        currentLevel.startGame();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        try {
            goToLevel((String) arg1);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }
}