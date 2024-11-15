package com.example.demo.Actor;

public abstract class ActiveActorDestructible extends ActiveActor {

	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	public abstract void updatePosition();

	public abstract void updateActor();

	//Destroy function
	public void takeDamage() {
		// Logic for handling damage
	}

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