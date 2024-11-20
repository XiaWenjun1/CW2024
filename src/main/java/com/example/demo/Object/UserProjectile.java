package com.example.demo.Object;

import com.example.demo.Level.LevelParent;
import javafx.scene.image.ImageView;

public class UserProjectile extends Projectile {

	private int powerLevel = 1;

	private static final String DEFAULT_IMAGE = "userfire.png";

	private static final int DEFAULT_IMAGE_WIDTH = 50;
	private static final int DEFAULT_IMAGE_HEIGHT = 50;

	private static final int POWERED_IMAGE_WIDTH = 75;
	private static final int POWERED_IMAGE_HEIGHT = 75;

	private static final int MAX_POWERED_IMAGE_WIDTH = 100;
	private static final int MAX_POWERED_IMAGE_HEIGHT = 100;

	private static final double DEFAULT_HITBOX_WIDTH = DEFAULT_IMAGE_WIDTH;
	private static final double DEFAULT_HITBOX_HEIGHT = DEFAULT_IMAGE_HEIGHT * 0.2;

	private static final double POWERED_HITBOX_WIDTH = POWERED_IMAGE_WIDTH * 1.1;
	private static final double POWERED_HITBOX_HEIGHT = POWERED_IMAGE_HEIGHT * 0.25;

	private static final double MAX_POWERED_HITBOX_WIDTH = MAX_POWERED_IMAGE_WIDTH * 1.15;
	private static final double MAX_POWERED_HITBOX_HEIGHT = MAX_POWERED_IMAGE_HEIGHT * 0.3;

	private static final int DEFAULT_VELOCITY = 6;
	private static final int POWERED_VELOCITY = 10;
	private static final int MAX_POWERED_VELOCITY = 14;

	private int horizontalVelocity = DEFAULT_VELOCITY;

	private ImageView imageView;

	public UserProjectile(double initialXPos, double initialYPos, LevelParent levelParent, int imageWidth, int imageHeight) {
		super(DEFAULT_IMAGE, imageWidth, imageHeight, initialXPos, initialYPos);

		this.imageView = new ImageView();
		this.imageView.setPreserveRatio(true);

		updateProjectileState();

		setHitboxSize(DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT);

		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}

	public int getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(int newPowerLevel) {
		this.powerLevel = newPowerLevel;
		updateProjectileState();
	}

	public void updateProjectileState() {
		switch (powerLevel) {
			case 1:
				setVelocity(DEFAULT_VELOCITY);
				setHitboxSize(DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT);
				setImageSize(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
				break;
			case 2:
				setVelocity(POWERED_VELOCITY);
				setHitboxSize(POWERED_HITBOX_WIDTH, POWERED_HITBOX_HEIGHT);
				setImageSize(POWERED_IMAGE_WIDTH, POWERED_IMAGE_HEIGHT);
				break;
			case 3:
				setVelocity(MAX_POWERED_VELOCITY);
				setHitboxSize(MAX_POWERED_HITBOX_WIDTH, MAX_POWERED_HITBOX_HEIGHT);
				setImageSize(MAX_POWERED_IMAGE_WIDTH, MAX_POWERED_IMAGE_HEIGHT);
				break;
			default:
				System.out.println("Invalid power level: " + powerLevel);
		}
	}

	private void setVelocity(int velocity) {
		horizontalVelocity = velocity;
	}

	public void setImageSize(int width, int height) {
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
	}

	public double getWidth() {
		return imageView.getFitWidth();
	}

	public double getHeight() {
		return imageView.getFitHeight();
	}
}