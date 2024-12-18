package com.example.demo.Ui;

import com.example.demo.Level.LevelManager.AudioManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The Control_Control class manages the control panel in the game.
 * It is responsible for setting up UI elements such as images, buttons, and resizing them.
 * It also manages the closing of the control panel and releasing resources when necessary.
 */
public class Control_Control {

    /**
     * The button used to close the control panel or exit the current view.
     */
    @FXML
    private Button closeButton; // Close button

    /**
     * The ImageView displaying the player's plane in the game.
     */
    @FXML
    private ImageView planeImageView; // Aircraft image

    /**
     * The ImageView representing the first bullet fired by the user.
     */
    @FXML
    private ImageView userfire1ImageView; // User bullet

    /**
     * The ImageView representing the second bullet fired by the user.
     */
    @FXML
    private ImageView userfire2ImageView; // User bullet

    /**
     * The ImageView representing the third bullet fired by the user.
     */
    @FXML
    private ImageView userfire3ImageView; // User bullet

    /**
     * The ImageView representing the fourth bullet fired by the user.
     */
    @FXML
    private ImageView userfire4ImageView; // User bullet

    /**
     * The ImageView representing the fifth bullet fired by the user.
     */
    @FXML
    private ImageView userfire5ImageView; // User bullet

    /**
     * The ImageView representing a heart in the game, possibly used for health or score purposes.
     */
    @FXML
    private ImageView heartImageView; // Heart

    /**
     * The ImageView representing an ammo box, which may be collected for power-ups or replenishment.
     */
    @FXML
    private ImageView ammoboxImageView; // Ammo box

    /**
     * The root container of the main interface. It holds all the UI components for the main screen.
     */
    private Pane mainRoot; // The root container of the main interface

    /**
     * Default constructor for Control_Control.
     * Initializes default values for fields.
     */
    public Control_Control() {
        this.mainRoot = null;
    }

    /**
     * Initializes the control panel by setting up button hover sound effects,
     * loading images, and adjusting the sizes of UI elements.
     */
    public void initialize() {
        addHoverSoundToButton(closeButton);
        loadImages();
        setElementSizes();
    }

    /**
     * Adds a hover sound effect to the given button.
     *
     * @param button The button to which the hover sound will be added.
     */
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> AudioManager.getInstance().playHoverSound());
    }

    /**
     * Loads the images for the control panel elements, such as the aircraft, bullets, heart, and ammo box.
     */
    private void loadImages() {
        planeImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userplane.png")));
        userfire1ImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level1.png")));
        userfire2ImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level2.png")));
        userfire3ImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level3.png")));
        userfire4ImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level4.png")));
        userfire5ImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level5.png")));
        heartImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/heartItem.png")));
        ammoboxImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/ammobox.png")));
    }

    /**
     * Sets the size of the ImageViews for each control element.
     */
    private void setElementSizes() {
        setImageViewSize(planeImageView, 150, 150);
        setImageViewSize(userfire1ImageView, 100, 100);
        setImageViewSize(userfire2ImageView, 100, 100);
        setImageViewSize(userfire3ImageView, 100, 100);
        setImageViewSize(userfire4ImageView, 100, 100);
        setImageViewSize(userfire5ImageView, 100, 100);
        setImageViewSize(heartImageView, 50, 50);
        setImageViewSize(ammoboxImageView, 75, 75);
    }

    /**
     * Sets the width and height for the given ImageView.
     *
     * @param imageView The ImageView whose size will be set.
     * @param width The width to set.
     * @param height The height to set.
     */
    private void setImageViewSize(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    /**
     * Sets the root container of the main interface, used to remove the blur effect when closing the control panel.
     *
     * @param mainRoot The root container of the main interface.
     */
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    /**
     * Closes the control panel by removing the blur effect from the main interface and
     * removing the control pane from the main UI.
     */
    @FXML
    private void closeControl() {
        // Remove the blur effect from all elements in the main interface
        if (mainRoot != null) {
            for (Node child : mainRoot.getChildren()) {
                child.setEffect(null); // Remove the blur effect
            }
            // Remove the control pane from the main interface
            mainRoot.getChildren().removeIf(node -> "controlPane".equals(node.getId()));
        }
    }

    /**
     * Releases resources used by the images in the control panel by clearing their references.
     */
    public void releaseResources() {
        clearImageView(planeImageView);
        clearImageView(userfire1ImageView);
        clearImageView(userfire2ImageView);
        clearImageView(userfire3ImageView);
        clearImageView(userfire4ImageView);
        clearImageView(userfire5ImageView);
        clearImageView(heartImageView);
        clearImageView(ammoboxImageView);
    }

    /**
     * Clears the image of the given ImageView to release resources.
     *
     * @param imageView The ImageView whose image will be cleared.
     */
    private void clearImageView(ImageView imageView) {
        if (imageView != null) {
            imageView.setImage(null);
        }
    }
}