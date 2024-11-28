package com.example.demo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Controller class responsible for handling animations and interactions of the plane, bullets, enemies, and boss in the game.
 * It manages the movement and firing of the plane, bullets, and enemies, as well as their animations.
 */
public class Control_Animation {

    /**
     * The speed at which the plane moves across the screen.
     */
    private double planeSpeed;

    /**
     * The current X position of the plane.
     */
    private double planeXPosition;

    /**
     * The speed at which bullets move across the screen.
     */
    private double bulletSpeed;

    /**
     * A flag indicating whether the user's bullet is visible on the screen.
     */
    private boolean bulletVisible;

    /**
     * A flag indicating whether the enemy's bullet is visible on the screen.
     */
    private boolean enemyBulletVisible;

    /**
     * A flag indicating whether the boss's bullet is visible on the screen.
     */
    private boolean bossBulletVisible;

    /**
     * ImageView for the background of the game.
     */
    @FXML private ImageView backgroundImageView;

    /**
     * ImageView for the user's plane.
     */
    @FXML private ImageView planeImageView;

    /**
     * ImageView for the user's bullet.
     */
    @FXML private ImageView userfireImageView;

    /**
     * ImageView for the enemy plane.
     */
    @FXML private ImageView enemyImageView;

    /**
     * ImageView for the enemy's bullet.
     */
    @FXML private ImageView enemyfireImageView;

    /**
     * ImageView for the boss plane.
     */
    @FXML private ImageView bossImageView;

    /**
     * ImageView for the boss's bullet.
     */
    @FXML private ImageView bossfireImageView;

    /**
     * Timeline for animating the plane's movement.
     */
    private Timeline planeTimeline;

    /**
     * Timeline for firing the user's bullet.
     */
    private Timeline bulletTimeline;

    /**
     * Timeline for moving the user's bullet.
     */
    private Timeline bulletMovementTimeline;

    /**
     * Timeline for firing the enemy's bullet.
     */
    private Timeline enemyBulletTimeline;

    /**
     * Timeline for firing the boss's bullet.
     */
    private Timeline bossBulletTimeline;

    /**
     * PathTransition for animating the enemy's movement.
     */
    private PathTransition enemyPathTransition;

    /**
     * PathTransition for animating the boss's movement.
     */
    private PathTransition bossPathTransition;

    /**
     * Default constructor for the Control_Animation class.
     * <p>
     * This constructor initializes all essential fields of the controller to their default values,
     * ensuring the class is in a ready state for use. It sets initial speeds for the plane and bullets,
     * initializes visibility flags for bullets, and sets all animation timelines and path transitions to null.
     * These default values provide a clean starting point for animation control in the game.
     */
    public Control_Animation() {
        this.planeSpeed = 5;
        this.bulletSpeed = 10;
        this.planeXPosition = 0;
        this.bulletVisible = false;
        this.enemyBulletVisible = false;
        this.bossBulletVisible = false;

        this.planeTimeline = null;
        this.bulletTimeline = null;
        this.bulletMovementTimeline = null;
        this.enemyBulletTimeline = null;
        this.bossBulletTimeline = null;

        this.enemyPathTransition = null;
        this.bossPathTransition = null;
    }

    /**
     * Initializes the controller by loading images, setting element sizes, and starting animations.
     */
    public void initialize() {
        loadImages();
        setElementSizes();
        startAnimations();
    }

    /**
     * Loads images for all elements including the background, plane, user bullets, enemy, enemy bullets, boss, and boss bullets.
     */
    private void loadImages() {
        backgroundImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/start.jpg")));
        planeImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userplane.png")));
        userfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire_level1.png")));
        enemyImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/enemyplane.png")));
        enemyfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/enemyFire.png")));
        bossImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/bossplane.png")));
        bossfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/fireball.png")));
    }

    /**
     * Sets the sizes for the images of the plane, bullets, enemies, and boss.
     */
    private void setElementSizes() {
        setImageViewSize(planeImageView, 200, 150);
        setImageViewSize(userfireImageView, 50, 50);
        setImageViewSize(enemyImageView, 200, 150);
        setImageViewSize(enemyfireImageView, 50, 50);
        setImageViewSize(bossImageView, 200, 150);
        setImageViewSize(bossfireImageView, 50, 50);
    }

    /**
     * Helper method to set the size of an ImageView element.
     *
     * @param imageView The ImageView to resize.
     * @param width The width to set.
     * @param height The height to set.
     */
    private void setImageViewSize(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    /**
     * Starts all animations for the plane, bullets, enemies, and boss.
     */
    public void startAnimations() {
        if (planeTimeline == null) {
            planeTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> movePlane()));
            planeTimeline.setCycleCount(Timeline.INDEFINITE);
            planeTimeline.play();
        }

        if (bulletTimeline == null) {
            bulletTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> fireBullet()));
            bulletTimeline.setCycleCount(Timeline.INDEFINITE);
            bulletTimeline.play();
        }

        if (bulletMovementTimeline == null) {
            bulletMovementTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> moveBullet()));
            bulletMovementTimeline.setCycleCount(Timeline.INDEFINITE);
            bulletMovementTimeline.play();
        }

        if (enemyBulletTimeline == null) {
            enemyBulletTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> fireEnemyBullet()));
            enemyBulletTimeline.setCycleCount(Timeline.INDEFINITE);
            enemyBulletTimeline.play();
        }

        if (bossBulletTimeline == null) {
            bossBulletTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> fireBossBullet()));
            bossBulletTimeline.setCycleCount(Timeline.INDEFINITE);
            bossBulletTimeline.play();
        }

        moveEnemy();
        moveBoss();
    }

    /**
     * Stops all animations.
     */
    public void stopAnimations() {
        if (planeTimeline != null) {
            planeTimeline.stop();
        }
        if (bulletTimeline != null) {
            bulletTimeline.stop();
        }
        if (bulletMovementTimeline != null) {
            bulletMovementTimeline.stop();
        }
        if (enemyBulletTimeline != null) {
            enemyBulletTimeline.stop();
        }
        if (bossBulletTimeline != null) {
            bossBulletTimeline.stop();
        }
        if (enemyPathTransition != null) {
            enemyPathTransition.stop();
        }
        if (bossPathTransition != null) {
            bossPathTransition.stop();
        }
    }

    /**
     * Fires the user's bullet if it's not already visible.
     */
    private void fireBullet() {
        if (!bulletVisible) {
            userfireImageView.setLayoutX(planeXPosition + 200);
            userfireImageView.setLayoutY(340);
            userfireImageView.setVisible(true);
            bulletVisible = true;
        }
    }

    /**
     * Fires the enemy's bullet if it's not already visible.
     */
    private void fireEnemyBullet() {
        if (!enemyBulletVisible) {
            enemyfireImageView.setLayoutX(enemyImageView.getLayoutX() + enemyImageView.getTranslateX() - 10);
            enemyfireImageView.setLayoutY(enemyImageView.getLayoutY() + enemyImageView.getTranslateY() + 50);
            enemyfireImageView.setVisible(true);
            enemyBulletVisible = true;
        }
    }

    /**
     * Fires the boss's bullet if it's not already visible.
     */
    private void fireBossBullet() {
        if (!bossBulletVisible) {
            bossfireImageView.setLayoutX(bossImageView.getLayoutX() + bossImageView.getTranslateX() - 10);
            bossfireImageView.setLayoutY(bossImageView.getLayoutY() + bossImageView.getTranslateY() + 50);
            bossfireImageView.setVisible(true);
            bossBulletVisible = true;
        }
    }

    /**
     * Moves the user's bullet along the screen, making it invisible when it goes off screen.
     */
    private void moveBullet() {
        if (bulletVisible) {
            userfireImageView.setLayoutX(userfireImageView.getLayoutX() + bulletSpeed);
            if (userfireImageView.getLayoutX() >= 1300) {
                userfireImageView.setVisible(false);
                bulletVisible = false;
            }
        }
        if (enemyBulletVisible) {
            enemyfireImageView.setLayoutX(enemyfireImageView.getLayoutX() - bulletSpeed);
            if (enemyfireImageView.getLayoutX() <= -80) {
                enemyfireImageView.setVisible(false);
                enemyBulletVisible = false;
            }
        }
        if (bossBulletVisible) {
            bossfireImageView.setLayoutX(bossfireImageView.getLayoutX() - bulletSpeed);
            if (bossfireImageView.getLayoutX() <= -80) {
                bossfireImageView.setVisible(false);
                bossBulletVisible = false;
            }
        }
    }

    /**
     * Moves the plane across the screen, looping when it reaches the edge.
     */
    private void movePlane() {
        planeXPosition += planeSpeed;
        if (planeXPosition >= 1300) {
            planeXPosition = -300;
        }
        planeImageView.setLayoutX(planeXPosition);
    }

    /**
     * Moves the enemy across a predefined path.
     */
    private void moveEnemy() {
        if (enemyPathTransition == null) {
            Path path = new Path();
            path.getElements().add(new MoveTo(1500, -200));
            path.getElements().add(new CubicCurveTo(1084, 100, 868, 467, 650, 900));

            enemyPathTransition = new PathTransition(Duration.seconds(10), path, enemyImageView);
            enemyPathTransition.setCycleCount(PathTransition.INDEFINITE);
            enemyPathTransition.setAutoReverse(false);
            enemyPathTransition.play();
        }
    }

    /**
     * Moves the boss across a predefined path.
     */
    private void moveBoss() {
        if (bossPathTransition == null) {
            Path path = new Path();
            path.getElements().add(new MoveTo(1500, 900));
            path.getElements().add(new CubicCurveTo(1200, 600, 650, 300, -200, 200));

            bossPathTransition = new PathTransition(Duration.seconds(10), path, bossImageView);
            bossPathTransition.setCycleCount(PathTransition.INDEFINITE);
            bossPathTransition.setAutoReverse(false);
            bossPathTransition.play();
        }
    }

    /**
     * Releases resources by clearing the images from the ImageViews.
     */
    public void releaseResources() {
        clearImageView(backgroundImageView);
        clearImageView(planeImageView);
        clearImageView(userfireImageView);
        clearImageView(enemyImageView);
        clearImageView(enemyfireImageView);
        clearImageView(bossImageView);
        clearImageView(bossfireImageView);
    }

    /**
     * Clears the image of an ImageView.
     *
     * @param imageView The ImageView to clear the image from.
     */
    private void clearImageView(ImageView imageView) {
        if (imageView != null) {
            imageView.setImage(null);
        }
    }
}