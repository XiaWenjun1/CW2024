package com.example.demo;

import javafx.scene.input.MouseEvent;

public class UserPlane extends FighterPlane {

	private LevelParent levelParent;

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -25;
	private static final double Y_LOWER_BOUND = 700.0;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 1200.0;
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

	private double initialMouseX;
	private double initialMouseY;
	private boolean isDragging; // Used to determine whether the aircraft is being dragged


	public UserPlane(int initialHealth, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.levelParent = levelParent;
		velocityMultiplierY = 0;
		velocityMultiplierX = 0;
		isDragging = false; // No dragging during initialization

		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.25);

		// Add the hitbox to the root of LevelParent
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront(); // Make sure the hitbox is on top

		// Bind mouse events to the hitbox
		getHitbox().setOnMousePressed(this::handleMousePressed);
		getHitbox().setOnMouseDragged(this::handleMouseDragged);
		getHitbox().setOnMouseReleased(this::handleMouseReleased);
		getHitbox().setOnMouseEntered(this::handleMouseEntered);
		getHitbox().setOnMouseExited(this::handleMouseExited);
	}

	//Record the current position when the mouse is pressed
	private void handleMousePressed(MouseEvent event) {
		if (event.getButton().toString().equals("PRIMARY")) { // Dragging is only allowed when the left button is pressed
			initialMouseX = event.getSceneX();
			initialMouseY = event.getSceneY();

			if (isMouseInsidePlane(event.getSceneX(), event.getSceneY())) {  // Check if the mouse is in the aircraft area
				isDragging = true;
			}
		}
	}

	// Check if the mouse is clicked in the aircraft area
	private boolean isMouseInsidePlane(double mouseX, double mouseY) {
		// Get the hitbox bounds of the aircraft
		double planeMinX = getHitbox().getBoundsInParent().getMinX();
		double planeMaxX = getHitbox().getBoundsInParent().getMaxX();
		double planeMinY = getHitbox().getBoundsInParent().getMinY();
		double planeMaxY = getHitbox().getBoundsInParent().getMaxY();

		// Determine whether the mouse is in the hitbox area
		return mouseX >= planeMinX && mouseX <= planeMaxX && mouseY >= planeMinY && mouseY <= planeMaxY;
	}

	// Determine whether the mouse is in the hitbox area
	private void handleMouseDragged(MouseEvent event) {
		if (isDragging) {
			double deltaX = event.getSceneX() - initialMouseX;
			double deltaY = event.getSceneY() - initialMouseY;

			// Update the aircraft's position
			this.setTranslateX(getTranslateX() + deltaX);
			this.setTranslateY(getTranslateY() + deltaY);

			// Update the initial mouse position
			initialMouseX = event.getSceneX();
			initialMouseY = event.getSceneY();

			// Prevent the aircraft from going beyond the boundary
			double newPositionX = getLayoutX() + getTranslateX();
			double newPositionY = getLayoutY() + getTranslateY();

			if (newPositionX < X_LEFT_BOUND || newPositionX > X_RIGHT_BOUND) {
				this.setTranslateX(getTranslateX() - deltaX); // Restore position
			}
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(getTranslateY() - deltaY); // Restore position
			}
		}
	}

	// Stop dragging when the mouse is released
	private void handleMouseReleased(MouseEvent event) {
		if (event.getButton().toString().equals("PRIMARY")) { // Stop dragging only when the left button is released
			isDragging = false;
		}
	}

	// When the mouse enters the hitbox area, change the cursor style to pointer
	private void handleMouseEntered(MouseEvent event) {
		getHitbox().setStyle("-fx-cursor: hand;");  // Set the cursor to finger
	}

	// When the mouse leaves the hitbox area, restore the default cursor style
	private void handleMouseExited(MouseEvent event) {
		getHitbox().setStyle("-fx-cursor: default;");  // Restore the default cursor
	}

	@Override
	public void updatePosition() {
		this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierX);
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierY);

		// Check and limit the horizontal and vertical boundaries of the aircraft
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
	public ActiveActorDestructible fireProjectile() {
		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

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

	// Improved stop method to stop vertical and horizontal movement respectively
	public void stopVerticalMovement() {
		velocityMultiplierY = 0;
	}

	public void stopHorizontalMovement() {
		velocityMultiplierX = 0;
	}

	// Used to control the smoothing effect when the aircraft stops
	public void stop() {
		stopVerticalMovement();
		stopHorizontalMovement();
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}