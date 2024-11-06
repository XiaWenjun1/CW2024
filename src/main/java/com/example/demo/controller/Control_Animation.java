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

public class Control_Animation {
    private double planeSpeed = 5; // Aircraft speed
    private double planeXPosition; // Aircraft X coordinate
    private double bulletSpeed = 10; // Bullet speed
    private boolean bulletVisible = false; // User bullet visible
    private boolean enemyBulletVisible = false; // Enemy bullet visible
    private boolean bossBulletVisible = false; // Boss bullet visible

    @FXML private ImageView backgroundImageView; // Background
    @FXML private ImageView planeImageView; // Aircraft image
    @FXML private ImageView userfireImageView; // User bullet
    @FXML private ImageView enemyImageView; // Enemy
    @FXML private ImageView enemyfireImageView; // Enemy bullet
    @FXML private ImageView bossImageView; // Boss
    @FXML private ImageView bossfireImageView; // Boss bullet

    // Animation instances
    private Timeline planeTimeline;
    private Timeline bulletTimeline;
    private Timeline bulletMovementTimeline;
    private Timeline enemyBulletTimeline;
    private Timeline bossBulletTimeline;
    private PathTransition enemyPathTransition;
    private PathTransition bossPathTransition;

    public void initialize() {
        loadImages();
        setElementSizes();
        startAnimations();
    }

    private void loadImages() {
        backgroundImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/start.jpg")));
        planeImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userplane.png")));
        userfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/userfire.png")));
        enemyImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/enemyplane.png")));
        enemyfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/enemyFire.png")));
        bossImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/bossplane.png")));
        bossfireImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/images/fireball.png")));
    }

    private void setElementSizes() {
        setImageViewSize(planeImageView, 200, 150);
        setImageViewSize(userfireImageView, 300, 300);
        setImageViewSize(enemyImageView, 200, 150);
        setImageViewSize(enemyfireImageView, 50, 50);
        setImageViewSize(bossImageView, 200, 150);
        setImageViewSize(bossfireImageView, 50, 50);
    }

    private void setImageViewSize(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

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

    private void fireBullet() {
        if (!bulletVisible) {
            userfireImageView.setLayoutX(planeXPosition + 90);
            userfireImageView.setLayoutY(220);
            userfireImageView.setVisible(true);
            bulletVisible = true;
        }
    }

    private void fireEnemyBullet() {
        if (!enemyBulletVisible) {
            enemyfireImageView.setLayoutX(enemyImageView.getLayoutX() + enemyImageView.getTranslateX() - 10);
            enemyfireImageView.setLayoutY(enemyImageView.getLayoutY() + enemyImageView.getTranslateY() + 50);
            enemyfireImageView.setVisible(true);
            enemyBulletVisible = true;
        }
    }

    private void fireBossBullet() {
        if (!bossBulletVisible) {
            bossfireImageView.setLayoutX(bossImageView.getLayoutX() + bossImageView.getTranslateX() - 10);
            bossfireImageView.setLayoutY(bossImageView.getLayoutY() + bossImageView.getTranslateY() + 50);
            bossfireImageView.setVisible(true);
            bossBulletVisible = true;
        }
    }

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

    private void movePlane() {
        planeXPosition += planeSpeed;
        if (planeXPosition >= 1300) {
            planeXPosition = -300;
        }
        planeImageView.setLayoutX(planeXPosition);
    }

    private void moveEnemy() {
        if (enemyPathTransition == null) {
            Path path = new Path();
            path.getElements().add(new MoveTo(1500, -200));
            path.getElements().add(new CubicCurveTo(1084, 100, 868, 467, 650, 900));

            enemyPathTransition = new PathTransition(Duration.seconds(30), path, enemyImageView);
            enemyPathTransition.setCycleCount(PathTransition.INDEFINITE);
            enemyPathTransition.setAutoReverse(false);
            enemyPathTransition.play();
        }
    }

    private void moveBoss() {
        if (bossPathTransition == null) {
            Path path = new Path();
            path.getElements().add(new MoveTo(1500, 900));
            path.getElements().add(new CubicCurveTo(1200, 600, 650, 300, -200, 200));

            bossPathTransition = new PathTransition(Duration.seconds(25), path, bossImageView);
            bossPathTransition.setCycleCount(PathTransition.INDEFINITE);
            bossPathTransition.setAutoReverse(false);
            bossPathTransition.play();
        }
    }

    public void switchToAnotherScene() {
        stopAnimations(); // 停止所有动画
        // 切换到另一个场景的逻辑
    }

    public void returnToMainScene() {
        startAnimations(); // 重新启动所有动画
        // 返回主场景的逻辑
    }
}