package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

import java.util.ArrayList;
import java.util.List;

public class EnemyPlane extends FighterPlane {

	private LevelParent levelParent;  // 添加 LevelParent 实例

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -3;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .005;

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
	public List<ActiveActorDestructible> fireProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		if (Math.random() < FIRE_RATE) { // 确定是否发射子弹
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

			// 创建单发子弹
			projectiles.add(new EnemyProjectile(projectileXPosition, projectileYPosition, levelParent));
		}

		return projectiles; // 即使为空也返回列表，避免返回 null
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}

}
