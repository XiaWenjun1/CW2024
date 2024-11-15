package com.example.demo.Actor;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private Rectangle hitbox; // New: hitbox for displaying and calculating collisions

	public ActiveActor(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitWidth(imageWidth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		// Initialize the hitbox
		hitbox = new Rectangle(imageWidth, imageHeight);
		hitbox.setFill(Color.TRANSPARENT);  // 设置填充为透明
		hitbox.setStrokeWidth(2);           // 设置边框宽度
		hitbox.setVisible(true);            // 确保 hitbox 可见

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
