package com.example.demo.Object.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

public class Heart extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_WIDTH = 30;
    private static final int IMAGE_HEIGHT = 30;
    private static final int HORIZONTAL_VELOCITY = -3;
    private static final double SPAWN_PROBABILITY = 0.01;
    private static final double MaximumXPosition = 1350;
    private static final double MaximumYPosition = 700;
    private static final double VERTICAL_AMPLITUDE = 50;
    private static final double VERTICAL_VELOCITY = 0.05;
    private double time = 0;

    public Heart(double initialXPos, double initialYPos, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.setTranslateX(initialXPos);
        this.setTranslateY(initialYPos);
    }

    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    protected void moveVertically() {
        time += VERTICAL_VELOCITY;
        double offsetY = VERTICAL_AMPLITUDE * Math.sin(time);
        this.setTranslateY(this.getInitialY() + offsetY);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically();
    }

    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    @Override
    public void takeDamage() {}

    private double getInitialY() {
        return this.getLayoutY();
    }

    public static double getSpawnProbability() {
        return SPAWN_PROBABILITY;
    }

    public static double getMaximumXPosition() {
        return MaximumXPosition;
    }

    public static double getMaximumYPosition() {
        return MaximumYPosition;
    }
}