package com.example.demo;

public class UserProjectile extends Projectile {

	private LevelParent levelParent;  // 添加 LevelParent 实例

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_WIDTH = 50;
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = 6;

	public UserProjectile(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
		this.levelParent = levelParent;

		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);

		// 将 hitbox 添加到 LevelParent 的 root 中
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();  // 确保 hitbox 在最上层
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}
	
}
