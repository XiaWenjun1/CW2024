package com.example.demo.Object.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

public class AmmoBox extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "ammobox.png";
    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double SPAWN_PROBABILITY = 0.01;
    private static final double MaximumXPosition = 1350;
    private static final double MaximumYPosition = 700;

    public AmmoBox(double initialXPos, double initialYPos, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.setTranslateX(initialXPos);
        this.setTranslateY(initialYPos);
    }

    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    @Override
    public void takeDamage() {}

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
