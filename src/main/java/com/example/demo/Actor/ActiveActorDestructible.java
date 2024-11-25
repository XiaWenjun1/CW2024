package com.example.demo.Actor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible{

	private boolean isDestroyed;
	private Rectangle hitbox;

	public ActiveActorDestructible(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
		createAndAddHitbox(imageWidth, imageHeight);
	}

	private void createAndAddHitbox(double width, double height) {
		hitbox = new Rectangle(width, height);
		hitbox.setFill(Color.TRANSPARENT);
		hitbox.setStrokeWidth(2);
		hitbox.setVisible(true);

//		hitbox = new Rectangle(width, height);
//		hitbox.setFill(Color.RED.deriveColor(1.0, 1.0, 1.0, 0.3));
//		hitbox.setStroke(Color.RED);
//		hitbox.setStrokeWidth(2);
	}

	// Update the hitbox synchronously when updating the position
	protected void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX()); // Update the X coordinate of the hitbox
		hitbox.setY(getLayoutY() + getTranslateY()); // Update the Y coordinate of the hitbox
	}

	public void setHitboxSize(double width, double height) {
		hitbox.setWidth(width);
		hitbox.setHeight(height);
	}

	// Return the current hitbox
	public Rectangle getHitbox() {
		return hitbox;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}