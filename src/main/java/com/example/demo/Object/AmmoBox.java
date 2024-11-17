package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

public class AmmoBox extends ActiveActorDestructible {

    private LevelParent levelParent;

    private static final String IMAGE_NAME = "ammobox.png";
    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -6;

    public AmmoBox(double initialXPos, double initialYPos, LevelParent levelParent) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.levelParent = levelParent;
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
    public void destroy() {
        super.destroy();
        this.setVisible(false);
        this.setDisable(true);
    }

    @Override
    public void takeDamage() {
    }
}
