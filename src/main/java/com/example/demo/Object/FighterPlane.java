package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import java.util.List;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract List<ActiveActorDestructible> fireProjectiles();
	
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	public void increaseHealth() {
		health++;
	}


	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}
		
}
