package com.example.demo.Object.EnemyPlane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.FighterPlane;

import java.util.ArrayList;
import java.util.List;

public class EnemyPlane extends FighterPlane {

	// Constants for the enemy plane's properties
	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .0025;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.35);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

			projectiles.add(new EnemyProjectile(projectileXPosition, projectileYPosition));
		}

		return projectiles;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}
}