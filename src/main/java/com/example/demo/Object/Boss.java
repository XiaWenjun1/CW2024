package com.example.demo.Object;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Display.ShieldImage;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.*;

public class Boss extends FighterPlane {
	private LevelParent levelParent;

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0;
	private static final double BOSS_FIRE_RATE = 0.01;
	private static final double BOSS_SHIELD_PROBABILITY = .001;
	private static final int IMAGE_WIDTH = 300;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 5;
	private static final int HEALTH = 50;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_UPPER_BOUND = 50;
	private static final int Y_LOWER_BOUND = 700;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private ShieldImage shieldImage;

	//Declare healthBar as a class member variable
	private ProgressBar healthBar;

	public Boss(LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;

		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.2);

		initializeMovePattern();

		// Create and initialize ShieldImage
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		// Add shieldImage to the root of LevelParent
		if (levelParent != null && levelParent.getRoot() != null) {
			levelParent.getRoot().getChildren().add(shieldImage);
		}

		// Call the createHealthBar method to create and initialize the health bar
		healthBar = createHealthBar();
		// Delay adding the health bar to the scene to ensure root is initialized
		Platform.runLater(() -> {
			if (levelParent != null && levelParent.getRoot() != null) {
				levelParent.getRoot().getChildren().add(healthBar);
			}
		});
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_UPPER_BOUND || currentPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		updateHitbox();
		updateHealthBar();
	}

	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		if (bossFiresInCurrentFrame()) {
			int attackType = selectAttackType();
			if (attackType == 1) {
				projectiles.add(createStraightProjectile());
			} else if (attackType == 2) {
				projectiles.addAll(createScatterProjectiles());
			} else if (attackType == 3) {
				projectiles.addAll(createDirectionalProjectiles());
			}
		}

		return projectiles;
	}

	private int selectAttackType() {
		return (int) (Math.random() * 3) + 1;
	}

	private ActiveActorDestructible createStraightProjectile() {
		double projectileXPosition = getLayoutX();
		double projectileYPosition = getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;

		return new BossProjectile(projectileXPosition, projectileYPosition, levelParent);
	}

	private List<ActiveActorDestructible> createScatterProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();
		double[] yOffsets = {-50, 0, 50};

		for (double yOffset : yOffsets) {
			double projectileXPosition = getLayoutX();
			double projectileYPosition = getLayoutY() + getTranslateY() + yOffset;

			BossProjectile projectile = new BossProjectile(projectileXPosition, projectileYPosition, levelParent);
			projectile.setLayoutY(projectileYPosition);

			double horizontalVelocity = -3;
			double verticalVelocity = 0;

			projectile.setVelocity(horizontalVelocity, verticalVelocity);

			if (!levelParent.getRoot().getChildren().contains(projectile.getHitbox())) {
				levelParent.getRoot().getChildren().add(projectile.getHitbox());
			}

			projectiles.add(projectile);
		}
		return projectiles;
	}

	private List<ActiveActorDestructible> createDirectionalProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		double straightX = getLayoutX();
		double straightY = getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;

		double leftUpY = straightY - 50;
		double leftDownY = straightY + 50;

		BossProjectile straightProjectile = new BossProjectile(straightX, straightY, levelParent);
		straightProjectile.setLayoutY(straightY);
		straightProjectile.setVelocity(-3, 0);
		projectiles.add((ActiveActorDestructible) straightProjectile);

		BossProjectile leftUpProjectile = new BossProjectile(straightX, leftUpY, levelParent);
		leftUpProjectile.setLayoutY(leftUpY);
		leftUpProjectile.setVelocity(-3, -1);
		projectiles.add((ActiveActorDestructible) leftUpProjectile);

		BossProjectile leftDownProjectile = new BossProjectile(straightX, leftDownY, levelParent);
		leftDownProjectile.setLayoutY(leftDownY);
		leftDownProjectile.setVelocity(-3, 1);
		projectiles.add((ActiveActorDestructible) leftDownProjectile);

		for (BossProjectile projectile : List.of(straightProjectile, leftUpProjectile, leftDownProjectile)) {
			if (!levelParent.getRoot().getChildren().contains(projectile.getHitbox())) {
				levelParent.getRoot().getChildren().add(projectile.getHitbox());
			}
		}

		return projectiles;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}

		double healthPercentage = Math.max(0, (double) getHealth() / HEALTH);
		healthBar.setProgress(healthPercentage);

		if (getHealth() <= 0) {
			die();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	public boolean checkCollision(ActiveActor otherActor) {
		return getHitbox().getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent());
	}

	private ProgressBar createHealthBar() {
		double currentPositionY = getLayoutY() + getTranslateY();

		// Create and initialize the health bar
		ProgressBar healthBar = new ProgressBar();
		healthBar.setPrefWidth(IMAGE_WIDTH);
		healthBar.setPrefHeight(10);
		healthBar.setStyle("-fx-accent: red; -fx-background-color: lightgray;");
		healthBar.setProgress(1.0);

		// Bind the health bar layout to the Boss layout
		healthBar.setLayoutX(getLayoutX());
		healthBar.setLayoutY(currentPositionY + 60);

		return healthBar;
	}

	private void updateHealthBar() {
		// Calculate the current position of the Boss
		double currentPositionY = getLayoutY() + getTranslateY();

		// Update the position of the health bar
		healthBar.setLayoutX(getLayoutX());
		healthBar.setLayoutY(currentPositionY + 60);

		// Update the health bar progress bar
		double healthPercentage = Math.max(0, (double) getHealth() / HEALTH);
		healthBar.setProgress(healthPercentage);
	}

	private void updateShield() {
		double currentPosition = getLayoutY() + getTranslateY();

		// Update shieldImage position
		shieldImage.setLayoutX(getLayoutX());
		shieldImage.setLayoutY(currentPosition);

		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
		} else if (shieldShouldBeActivated()) {
			activateShield();
			shieldImage.showShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
			shieldImage.hideShield();
		}

		shieldImage.toFront();
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	public ProgressBar getHealthBar() {
		return healthBar;
	}

	public void hideHealthBar() {
		if (healthBar != null) {
			healthBar.setVisible(false);
		}
	}

	public ImageView getShieldImage() {
		return shieldImage;
	}

	private void die() {
		System.out.println("Boss is died！");
	}

}