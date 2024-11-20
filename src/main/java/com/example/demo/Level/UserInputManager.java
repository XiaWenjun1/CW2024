package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.UserPlane;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class UserInputManager {
    private static final double Y_UPPER_BOUND = 50;
    private static final double Y_LOWER_BOUND = 700.0;
    private static final double X_LEFT_BOUND = 0;
    private static final double X_RIGHT_BOUND = 1200.0;

    private final UserPlane user;
    private final Group root;
    private PauseMenuManager pauseMenuManager;
    private final List<ActiveActorDestructible> userProjectiles;
    private double initialMouseX;
    private double initialMouseY;
    private boolean isSpacePressed = false;
    private boolean isPaused = false;
    private boolean isDragging = false;

    public UserInputManager(UserPlane user, Group root, List<ActiveActorDestructible> userProjectiles, PauseMenuManager pauseMenuManager) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
        this.pauseMenuManager = pauseMenuManager;
        bindMouseEvents();
    }

    public void handleKeyPressed(KeyEvent e) {
        if (isPaused) return;

        KeyCode kc = e.getCode();
        switch (kc) {
            case UP -> user.moveUp();
            case RIGHT -> user.moveRight();
            case DOWN -> user.moveDown();
            case LEFT -> user.moveLeft();
            case SPACE -> {
                if (!isSpacePressed) {
                    fireProjectile();
                    isSpacePressed = true;
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        switch (kc) {
            case UP, DOWN -> user.stopVerticalMovement();
            case LEFT, RIGHT -> user.stopHorizontalMovement();
            case SPACE -> isSpacePressed = false;
        }
    }

    private void bindMouseEvents() {
        user.getHitbox().setOnMousePressed(this::handleMousePressed);
        user.getHitbox().setOnMouseDragged(this::handleMouseDragged);
        user.getHitbox().setOnMouseReleased(this::handleMouseReleased);
        user.getHitbox().setOnMouseEntered(this::handleMouseEntered);
        user.getHitbox().setOnMouseExited(this::handleMouseExited);
    }

    private void handleMousePressed(MouseEvent event) {
        if ("PRIMARY".equals(event.getButton().toString())) {
            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();

            if (isMouseInsidePlane(event.getSceneX(), event.getSceneY())) {
                isDragging = true;
            }
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (isDragging) {
            double deltaX = event.getSceneX() - initialMouseX;
            double deltaY = event.getSceneY() - initialMouseY;

            user.setTranslateX(user.getTranslateX() + deltaX);
            user.setTranslateY(user.getTranslateY() + deltaY);

            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();

            double newPositionX = user.getLayoutX() + user.getTranslateX();
            double newPositionY = user.getLayoutY() + user.getTranslateY();

            if (newPositionX < X_LEFT_BOUND || newPositionX > X_RIGHT_BOUND) {
                user.setTranslateX(user.getTranslateX() - deltaX);
            }
            if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
                user.setTranslateY(user.getTranslateY() - deltaY);
            }
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        if ("PRIMARY".equals(event.getButton().toString())) {
            isDragging = false;
        }
    }

    private void handleMouseEntered(MouseEvent event) {
        user.getHitbox().setStyle("-fx-cursor: hand;");
    }

    private void handleMouseExited(MouseEvent event) {
        user.getHitbox().setStyle("-fx-cursor: default;");
    }

    private boolean isMouseInsidePlane(double mouseX, double mouseY) {
        double planeMinX = user.getHitbox().getBoundsInParent().getMinX();
        double planeMaxX = user.getHitbox().getBoundsInParent().getMaxX();
        double planeMinY = user.getHitbox().getBoundsInParent().getMinY();
        double planeMaxY = user.getHitbox().getBoundsInParent().getMaxY();

        return mouseX >= planeMinX && mouseX <= planeMaxX && mouseY >= planeMinY && mouseY <= planeMaxY;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            user.getHitbox().setOnMousePressed(null);
            user.getHitbox().setOnMouseDragged(null);
            user.getHitbox().setOnMouseReleased(null);
            user.getHitbox().setOnMouseEntered(null);
            user.getHitbox().setOnMouseExited(null);
        } else {
            bindMouseEvents();
        }
    }

    private void fireProjectile() {
        if (isPaused) {
            return;
        }

        List<ActiveActorDestructible> projectiles = user.fireProjectiles();
        if (projectiles != null) {
            projectiles.forEach(projectile -> {
                root.getChildren().add(projectile);
                userProjectiles.add(projectile);
            });
        }
    }

    public void handleMouseMiddleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            togglePause();
        }
    }

    private void togglePause() {
        pauseMenuManager.togglePause();
    }

    public void setPauseMenuManager(PauseMenuManager pauseMenuManager) {
        this.pauseMenuManager = pauseMenuManager;
    }
}