package com.example.demo;

public class UserPlane extends FighterPlane {

	private LevelParent levelParent;  // 添加 LevelParent 实例

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -25;
	private static final double Y_LOWER_BOUND = 700.0;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 1200.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 40;
	private static final int HORIZONTAL_VELOCITY = 40;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplierY;
	private int velocityMultiplierX;
	private int numberOfKills;

	public UserPlane(int initialHealth, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.levelParent = levelParent;  // 保存 LevelParent 实例
		velocityMultiplierY = 0;
		velocityMultiplierX = 0;

		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.25);

		// 将 hitbox 添加到 LevelParent 的 root 中
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();  // 确保 hitbox 在最上层
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateX = getTranslateX();
			double initialTranslateY = getTranslateY();

			this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierX);
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierY);

			double newPositionX = getLayoutX() + getTranslateX();
			double newPositionY = getLayoutY() + getTranslateY();

			if (newPositionX < X_LEFT_BOUND || newPositionX > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
		getHitbox().toFront();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		// 将 int 转换为 double 类型
		double projectileXPosition = (double) PROJECTILE_X_POSITION;
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

		// 传递正确的 LevelParent 实例
		return new UserProjectile(projectileXPosition, projectileYPosition, levelParent);
	}

	private boolean isMoving() {
		return velocityMultiplierY != 0 || velocityMultiplierX != 0;
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

	public void stop() {
		velocityMultiplierY = 0;
		velocityMultiplierX = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}