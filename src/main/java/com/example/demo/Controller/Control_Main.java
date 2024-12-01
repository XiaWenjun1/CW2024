package com.example.demo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Control_Main class handles the main user interface of the game, including
 * the buttons for starting the game, opening settings, and viewing control information.
 * It also manages background music, animations, and loading of different UI elements.
 */
public class Control_Main {

    /**
     * The controller responsible for managing animations in the game.
     * This controller handles animations for various elements such as the plane, bullets, and enemies.
     */
    private Control_Animation controlAnimation; // Animation controller

    /**
     * The controller responsible for managing the game settings.
     * This controller handles user interactions with the game settings.
     */
    private Control_Setting settingsController;

    /**
     * The controller responsible for managing user input controls in the game.
     * This controller handles user interactions related to input configuration.
     */
    private Control_Control controlController;

    /**
     * The main root container of the user interface.
     * This container holds all UI components for the main menu and other views.
     */
    @FXML
    private StackPane rootPane; // Main interface root container

    /**
     * The button to start the game.
     * This button triggers the game start when clicked by the user.
     */
    @FXML
    private Button startButton;

    /**
     * The button to open the control settings menu.
     * This button allows the user to configure the game controls.
     */
    @FXML
    private Button controlButton;

    /**
     * The button to open the settings menu.
     * This button allows the user to configure general game settings.
     */
    @FXML
    private Button settingsButton;

    /**
     * The AnchorPane that holds the animation controller.
     * This pane is responsible for displaying animations of the game.
     */
    @FXML
    private AnchorPane animationController; // AnchorPane for animation controller

    /**
     * The settings pane for configuring game settings.
     * This pane contains UI elements related to adjusting the game settings.
     */
    private AnchorPane settingsPane; // Settings pane

    /**
     * The control pane for managing user input controls.
     * This pane contains UI elements related to configuring the game controls.
     */
    private AnchorPane controlPane; // Control pane

    /**
     * The blur effect applied to the background when settings or control panels are active.
     * This effect adds a blur to the background to focus on the active panel.
     */
    private final BoxBlur blurEffect = new BoxBlur(10, 10, 3); // Blur effect

    /**
     * Default constructor for the Control_Main class.
     * <p>
     * This constructor initializes the Control_Main object. It is a no-argument constructor
     * required by JavaFX for instantiating the controller. Initialization logic is handled
     * by the {@link #initialize()} method after the object is created.
     * </p>
     */
    public Control_Main() {
        // Default constructor required by JavaFX
    }

    /**
     * Initialization method called automatically by JavaFX.
     * Sets up background music, button actions, and loads animations.
     */
    public void initialize() {
        initBackgroundMusic();
        setupButtonActions();
        loadAnimation();
    }

    /**
     * Initializes and plays background music if needed.
     */
    private void initBackgroundMusic() {
        if (AudioManager.getBackgroundMusic() == null) {
            AudioManager.initBackgroundMusic();
        }

        if (AudioManager.isBackgroundMusicEnabled() && !AudioManager.isBackgroundMusicPlaying()) {
            AudioManager.playBackgroundMusic();
        }
    }

    /**
     * Sets up actions for buttons (start, control, settings) and adds hover sound effects.
     */
    private void setupButtonActions() {
        setupHoverSounds(startButton, controlButton, settingsButton);

        startButton.setOnAction(event -> startGame());
        settingsButton.setOnAction(event -> openSettings());
        controlButton.setOnAction(event -> openControl());
    }

    /**
     * Loads the animation into the animation controller pane.
     * If the animation is already loaded, it starts the animations.
     */
    private void loadAnimation() {
        if (controlAnimation != null) {
            controlAnimation.startAnimations();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Animation/Animation.fxml"));
            AnchorPane animationPane = loader.load();
            animationController.getChildren().add(animationPane);

            controlAnimation = loader.getController();
            controlAnimation.initialize();
            controlAnimation.startAnimations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the settings pane and applies a blur effect to the background.
     */
    private void openSettings() {
        if (settingsPane == null) {
            loadSettingsPane();
        }

        applyBlurEffectToBackground();
        addSettingsPaneToRoot();
    }

    /**
     * Opens the control pane and applies a blur effect to the background.
     */
    private void openControl() {
        if (controlPane == null) {
            loadControlPane();
        }

        applyBlurEffectToBackground();
        addControlPaneToRoot();
    }

    /**
     * Starts the game, releasing any resources and clearing UI components.
     */
    private void startGame() {
        releaseResources();

        Control_Start controlStart = new Control_Start((Stage) rootPane.getScene().getWindow());
        try {
            controlStart.launchGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Releases resources and clears UI components before starting the game.
     */
    private void releaseResources() {
        stopAndReleaseAnimation();
        clearSettingsPane();
        clearControlPane();
        clearRootPane();

        hideCurrentWindow();
    }

    /**
     * Stops and releases resources associated with the animation.
     */
    private void stopAndReleaseAnimation() {
        if (controlAnimation != null) {
            controlAnimation.stopAnimations();
            controlAnimation.releaseResources();
            controlAnimation = null;
        }
    }

    /**
     * Clears the settings pane from the root container.
     */
    private void clearSettingsPane() {
        if (settingsPane != null) {
            rootPane.getChildren().remove(settingsPane);
            settingsPane = null;
        }
    }

    /**
     * Clears the control pane from the root container and releases its resources.
     */
    private void clearControlPane() {
        if (controlPane != null) {
            controlController.releaseResources();
            rootPane.getChildren().remove(controlPane);
            controlPane = null;
        }
    }

    /**
     * Clears all children from the root pane.
     */
    private void clearRootPane() {
        rootPane.getChildren().clear();
    }

    /**
     * Hides the current window (used when starting the game).
     */
    private void hideCurrentWindow() {
        if (rootPane.getScene() != null && rootPane.getScene().getWindow() != null) {
            rootPane.getScene().getWindow().hide();
        }
    }

    /**
     * Adds hover sound effects to the specified buttons.
     *
     * @param buttons The buttons to which hover sounds should be added.
     */
    private void setupHoverSounds(Button... buttons) {
        for (Button button : buttons) {
            button.setOnMouseEntered(event -> AudioManager.playHoverSound());
        }
    }

    /**
     * Applies a blur effect to all children in the root pane except the settings pane.
     */
    private void applyBlurEffectToBackground() {
        for (Node child : rootPane.getChildren()) {
            if (!"settingsPane".equals(child.getId())) {
                child.setEffect(blurEffect);
            }
        }
    }

    /**
     * Loads the settings pane if it hasn't been loaded yet.
     */
    private void loadSettingsPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Setting/Setting.fxml"));
            settingsPane = loader.load();
            settingsPane.setId("settingsPane");

            settingsController = loader.getController();
            settingsController.setMainRoot(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the control pane if it hasn't been loaded yet.
     */
    private void loadControlPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Control/Control.fxml"));
            controlPane = loader.load();
            controlPane.setId("controlPane");

            controlController = loader.getController();
            controlController.setMainRoot(rootPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the settings pane to the root container if it's not already present.
     */
    private void addSettingsPaneToRoot() {
        if (!rootPane.getChildren().contains(settingsPane)) {
            rootPane.getChildren().add(settingsPane);
        }
    }

    /**
     * Adds the control pane to the root container if it's not already present.
     */
    private void addControlPaneToRoot() {
        if (!rootPane.getChildren().contains(controlPane)) {
            rootPane.getChildren().add(controlPane);
        }
    }
}