package com.example.demo.Actor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class for active actors that are destructible.
 * This class extends {@link ActiveActor} and implements {@link Destructible}.
 * It adds the functionality of handling hitboxes for collision detection and tracking the destroyed state of the object.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * A boolean flag that tracks whether the actor has been destroyed.
	 * When set to true, the actor is considered to no longer be active or
	 * participating in the game world.
	 */
	private boolean isDestroyed;

	/**
	 * The {@link Rectangle} representing the collision area of the actor.
	 * This hitbox is used to detect interactions with other game elements
	 * (e.g., bullets, obstacles) and is updated alongside the actor's position.
	 * The hitbox is transparent and is drawn for collision purposes.
	 */
	private Rectangle hitbox;

	/**
	 * Constructs a new destructible active actor.
	 *
	 * @param imageName The name of the image representing the actor.
	 * @param imageWidth The width of the actor's image.
	 * @param imageHeight The height of the actor's image.
	 * @param initialXPos The initial X position of the actor.
	 * @param initialYPos The initial Y position of the actor.
	 */
	public ActiveActorDestructible(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
		createAndAddHitbox(imageWidth, imageHeight);
	}

	/**
	 * Creates and adds the hitbox to the actor.
	 * The hitbox is a {@link Rectangle} that represents the collision area of the actor.
	 * The hitbox is initially transparent.
	 *
	 * @param width The width of the hitbox.
	 * @param height The height of the hitbox.
	 */
	private void createAndAddHitbox(double width, double height) {
		hitbox = new Rectangle(width, height);
		hitbox.setFill(Color.TRANSPARENT); // Transparent fill for the hitbox
		hitbox.setStrokeWidth(2); // Stroke width for visibility
		hitbox.setVisible(true);
	}

	/**
	 * Updates the position of the hitbox based on the actor's current position.
	 * This ensures that the hitbox moves along with the actor.
	 */
	protected void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX()); // Update X position of the hitbox
		hitbox.setY(getLayoutY() + getTranslateY()); // Update Y position of the hitbox
	}

	/**
	 * Sets the size of the hitbox.
	 *
	 * @param width The new width of the hitbox.
	 * @param height The new height of the hitbox.
	 */
	public void setHitboxSize(double width, double height) {
		hitbox.setWidth(width);
		hitbox.setHeight(height);
	}

	/**
	 * Returns the current hitbox of the actor.
	 *
	 * @return The {@link Rectangle} representing the hitbox.
	 */
	public Rectangle getHitbox() {
		return hitbox;
	}

	/**
	 * Abstract method to update the position of the actor.
	 * This method must be implemented by subclasses to update the actor's position.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method to update the actor's state or behavior.
	 * This method must be implemented by subclasses to define how the actor's behavior is updated.
	 */
	public abstract void updateActor();

	/**
	 * Abstract method to apply damage to the actor.
	 * This method must be implemented by subclasses to define how the actor reacts to damage.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor and sets its destroyed state to true.
	 * Once destroyed, the actor is no longer considered active.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed The new destroyed state of the actor.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}