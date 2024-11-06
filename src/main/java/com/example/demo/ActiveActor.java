package com.example.demo;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private Rectangle hitbox;  // 新增：用于显示和计算碰撞的 hitbox

	public ActiveActor(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitWidth(imageWidth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);

		// 初始化 hitbox
		hitbox = new Rectangle(imageWidth, imageHeight);
		hitbox.setFill(Color.RED.deriveColor(1.0, 1.0, 1.0, 0.3));  // 半透明红色
		hitbox.setStroke(Color.RED);        // 设置为红色边框用于调试
		hitbox.setStrokeWidth(2);           // 设置边框宽度，确保能看到
	}

	public abstract void updatePosition();

	// 在更新位置时同步更新 hitbox
	protected void updateHitbox() {
		hitbox.setX(getLayoutX() + getTranslateX());  // 更新 hitbox 的 X 坐标
		hitbox.setY(getLayoutY() + getTranslateY());  // 更新 hitbox 的 Y 坐标
	}

	public void setHitboxSize(double width, double height) {
		hitbox.setWidth(width);
		hitbox.setHeight(height);
	}

	// 返回当前的 hitbox
	public Rectangle getHitbox() {
		return hitbox;
	}

	// 检查与其他 ActiveActor 的碰撞
	public boolean checkCollision(ActiveActor otherActor) {
		return hitbox.getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent());
	}

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
