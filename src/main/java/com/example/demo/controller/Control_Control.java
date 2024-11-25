package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Control_Control {
    @FXML
    private Button closeButton; // Close button

    @FXML private ImageView planeImageView; // Aircraft image
    @FXML private ImageView userfireImageView; // User bullet
    @FXML private ImageView heartImageView; // Heart
    @FXML private ImageView ammoboxImageView; // Ammo box

    private Pane mainRoot; // The root container of the main interface

    public void initialize() {
        addHoverSoundToButton(closeButton);
        loadImages();
        setElementSizes();
    }

    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> AudioManager.playHoverSound());
    }

    private void loadImages() {
        planeImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userplane.png")));
        userfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level1.png")));
        heartImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/heart.png")));
        ammoboxImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/ammobox.png")));
    }

    private void setElementSizes() {
        setImageViewSize(planeImageView, 150, 150);
        setImageViewSize(userfireImageView, 100, 100);
        setImageViewSize(heartImageView, 50, 50);
        setImageViewSize(ammoboxImageView, 75, 75);
    }

    private void setImageViewSize(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    // Set the root container of the main interface to remove the blur effect when closing
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    // Close the settings window
    @FXML
    private void closeControl() {
        // Remove the blur effect
        if (mainRoot != null) {
            for (Node child : mainRoot.getChildren()) {
                child.setEffect(null); // Remove the blur effect from all elements
            }
            // Remove settingsPane from the main interface
            mainRoot.getChildren().removeIf(node -> "controlPane".equals(node.getId()));
        }
    }

    public void releaseResources() {
        clearImageView(planeImageView);
        clearImageView(userfireImageView);
        clearImageView(heartImageView);
        clearImageView(ammoboxImageView);
    }

    private void clearImageView(ImageView imageView) {
        if (imageView != null) {
            imageView.setImage(null);
        }
    }
}
