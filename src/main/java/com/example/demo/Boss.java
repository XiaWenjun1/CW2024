package com.example.demo;

import java.util.*;

public class Boss extends FighterPlane {
	private LevelParent levelParent;  // 添加 LevelParent 实例

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int IMAGE_WIDTH = 300;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_UPPER_BOUND = 0;
	private static final int Y_LOWER_BOUND = 700;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private ShieldImage shieldImage;

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

		// 创建并初始化 ShieldImage
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		// 添加 shieldImage 到 LevelParent 的 root 中
		if (levelParent != null && levelParent.getRoot() != null) {
			levelParent.getRoot().getChildren().add(shieldImage);  // 通过 root 添加 shieldImage
		}
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());  // 你现有的逻辑来更新 Boss 的垂直位置
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_UPPER_BOUND || currentPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY);  // 如果超出边界，恢复 Y 位置
		}
	}


	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		updateHitbox();
	}


	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition(), levelParent) : null;
	}

	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
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
		// 使用 hitbox 进行碰撞检测
		return getHitbox().getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent());
	}

	private void updateShield() {
		double currentPosition = getLayoutY() + getTranslateY();

		// 更新 shieldImage 位置
		shieldImage.setLayoutX(getLayoutX());  // 使 shield 与 Boss 的 X 坐标同步
		shieldImage.setLayoutY(currentPosition);  // 使 shield 与 Boss 的 Y 坐标同步

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

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
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

}