package com.example.demo.Actor;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private Rectangle hitbox;

	public ActiveActor(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitWidth(imageWidth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
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

	public abstract void updatePosition();

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

	// Check for collision with other ActiveActors
	public boolean checkCollision(ActiveActor otherActor) {
		return hitbox.getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent());
	}

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
