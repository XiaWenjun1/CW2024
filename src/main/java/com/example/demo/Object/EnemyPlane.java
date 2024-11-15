package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

public class EnemyPlane extends FighterPlane {

	private LevelParent levelParent;  // 添加 LevelParent 实例

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -3;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion, levelParent);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}

}
