package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;

import java.util.ArrayList;
import java.util.List;

public class UserPlane extends FighterPlane {
	private LevelParent levelParent;
	private boolean isPaused = false;

	private static final double Y_UPPER_BOUND = 50;
	private static final double Y_LOWER_BOUND = 700.0;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 1200.0;
	private static final String IMAGE_NAME = "userplane.png";
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 150;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplierY;
	private int velocityMultiplierX;
	private int numberOfKills;

	private UserProjectile userProjectile;

	public UserPlane(int initialHealth, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);

		userProjectile = new UserProjectile(INITIAL_X_POSITION, INITIAL_Y_POSITION, levelParent, 50, 50);
		updateProjectileSize();

		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	public void upgradeProjectile() {
		if (userProjectile.getPowerLevel() < 3) {
			userProjectile.setPowerLevel(userProjectile.getPowerLevel() + 1);
		}
	}

	@Override
	public void updatePosition() {
		this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierX);
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierY);

		double newPositionX = getLayoutX() + getTranslateX();
		double newPositionY = getLayoutY() + getTranslateY();

		if (newPositionX < X_LEFT_BOUND) {
			setTranslateX(X_LEFT_BOUND - getLayoutX());
		} else if (newPositionX > X_RIGHT_BOUND) {
			setTranslateX(X_RIGHT_BOUND - getLayoutX());
		}

		if (newPositionY < Y_UPPER_BOUND) {
			setTranslateY(Y_UPPER_BOUND - getLayoutY());
		} else if (newPositionY > Y_LOWER_BOUND) {
			setTranslateY(Y_LOWER_BOUND - getLayoutY());
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
		getHitbox().toFront();
	}

	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		if (isPaused) {
			return new ArrayList<>();
		}

		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

		UserProjectile projectile = new UserProjectile(
				projectileXPosition,
				projectileYPosition,
				levelParent,
				(int) userProjectile.getWidth(),
				(int) userProjectile.getHeight()
		);
		projectile.setPowerLevel(userProjectile.getPowerLevel());

		projectiles.add(projectile);
		return projectiles;
	}

	private void updateProjectileSize() {
		userProjectile.updateProjectileState();
	}

	public void moveUp() {
		velocityMultiplierY = -1;
	}

	public void moveRight() {
		velocityMultiplierX = 1;
	}

	public void moveLeft() {
		velocityMultiplierX = -1;
	}

	public void moveDown() {
		velocityMultiplierY = 1;
	}

	public void stopVerticalMovement() {
		velocityMultiplierY = 0;
	}

	public void stopHorizontalMovement() {
		velocityMultiplierX = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}