package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;

public abstract class Projectile extends ActiveActorDestructible {

	public Projectile(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

}
