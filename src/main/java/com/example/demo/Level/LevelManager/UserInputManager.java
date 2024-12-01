package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.UserPlane.UserPlane;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages user input for controlling the player's plane and handling interactions
 * such as movement, shooting, and pausing the game.
 */
public class    UserInputManager {
    /** The game loop that continuously updates user movement and actions. */
    private AnimationTimer gameLoop;

    /** The user plane controlled by the player. */
    private final UserPlane user;

    /** The root group in which projectiles will be added. */
    private final Group root;

    /** The manager that handles pausing the game and showing the pause menu. */
    private PauseMenuManager pauseMenuManager;

    /** A list to keep track of the user's projectiles. */
    private final List<ActiveActorDestructible> userProjectiles;

    /** A set to store active keys that are being pressed. */
    private final Set<KeyCode> activeKeys = new HashSet<>();

    /** The initial X coordinate of the mouse when drag starts. */
    private double initialMouseX;

    /** The initial Y coordinate of the mouse when drag starts. */
    private double initialMouseY;

    /** Flag indicating whether the game is paused or not. */
    private boolean isPaused = false;

    /**Flag indicating if the game is over. */
    private boolean gameIsOver = false;

    /** Flag indicating whether the user is currently dragging the plane with the mouse. */
    private boolean isDragging = false;

    /** Flag indicating whether the user is on cooldown after firing a projectile. */
    private boolean isOnCooldown = false;

    /** The cooldown duration (in milliseconds) for firing projectiles. */
    private static final int BULLET_COOLDOWN_MS = 200;

    /**
     * Constructs a UserInputManager for handling user inputs.
     *
     * @param user the user plane that will respond to input
     * @param root the root group in which projectiles will be added
     * @param userProjectiles a list to keep track of the user's projectiles
     * @param pauseMenuManager the pause menu manager to handle game pausing
     */
    public UserInputManager(UserPlane user, Group root, List<ActiveActorDestructible> userProjectiles, PauseMenuManager pauseMenuManager) {
        this.user = user;
        this.root = root;
        this.userProjectiles = userProjectiles;
        this.pauseMenuManager = pauseMenuManager;
        bindMouseEvents();
        startGameLoop();
    }

    /**
     * Handles key press events for controlling the user plane.
     *
     * @param e the key event triggered
     */
    public void handleKeyPressed(KeyEvent e) {
        if (isPaused) return;
        activeKeys.add(e.getCode());
    }

    /**
     * Handles key release events and stops the corresponding action.
     *
     * @param e the key event triggered
     */
    public void handleKeyReleased(KeyEvent e) {
        activeKeys.remove(e.getCode());
    }

    /**
     * Updates the user's movement and shooting based on active keys.
     */
    private void updateUserMovement() {
        if (activeKeys.contains(KeyCode.UP) && !activeKeys.contains(KeyCode.DOWN)) {
            user.moveUp();
        } else if (activeKeys.contains(KeyCode.DOWN) && !activeKeys.contains(KeyCode.UP)) {
            user.moveDown();
        } else {
            user.stopVerticalMovement();
        }

        if (activeKeys.contains(KeyCode.LEFT) && !activeKeys.contains(KeyCode.RIGHT)) {
            user.moveLeft();
        } else if (activeKeys.contains(KeyCode.RIGHT) && !activeKeys.contains(KeyCode.LEFT)) {
            user.moveRight();
        } else {
            user.stopHorizontalMovement();
        }

        if (activeKeys.contains(KeyCode.SPACE)) {
            fireProjectile();
        }
    }

    /**
     * Binds mouse events to the user plane for drag movement and interaction.
     */
    private void bindMouseEvents() {
        user.getHitbox().setOnMousePressed(this::handleMousePressed);
        user.getHitbox().setOnMouseDragged(this::handleMouseDragged);
        user.getHitbox().setOnMouseReleased(this::handleMouseReleased);
        user.getHitbox().setOnMouseEntered(this::handleMouseEntered);
        user.getHitbox().setOnMouseExited(this::handleMouseExited);
    }

    /**
     * Handles mouse press events for initiating drag movement.
     *
     * @param event the mouse event triggered
     */
    private void handleMousePressed(MouseEvent event) {
        if ("PRIMARY".equals(event.getButton().toString())) {
            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();

            if (isMouseInsidePlane(event.getSceneX(), event.getSceneY())) {
                isDragging = true;
            }
        }
    }

    /**
     * Handles mouse drag events to move the user plane.
     *
     * @param event the mouse event triggered
     */
    private void handleMouseDragged(MouseEvent event) {
        if (gameIsOver) {
            return;
        }

        if (isDragging) {
            double deltaX = event.getSceneX() - initialMouseX;
            double deltaY = event.getSceneY() - initialMouseY;

            user.moveUserPlane(deltaX, deltaY);

            initialMouseX = event.getSceneX();
            initialMouseY = event.getSceneY();
        }
    }

    /**
     * Handles mouse release events to stop dragging.
     *
     * @param event the mouse event triggered
     */
    private void handleMouseReleased(MouseEvent event) {
        if ("PRIMARY".equals(event.getButton().toString())) {
            isDragging = false;
        }
    }

    /**
     * Changes the cursor style when the mouse enters the user plane hitbox.
     *
     * @param event the mouse event triggered
     */
    private void handleMouseEntered(MouseEvent event) {
        user.getHitbox().setStyle("-fx-cursor: hand;");
    }

    /**
     * Resets the cursor style when the mouse exits the user plane hitbox.
     *
     * @param event the mouse event triggered
     */
    private void handleMouseExited(MouseEvent event) {
        user.getHitbox().setStyle("-fx-cursor: default;");
    }

    /**
     * Checks if the mouse pointer is inside the user plane hitbox.
     *
     * @param mouseX the x-coordinate of the mouse
     * @param mouseY the y-coordinate of the mouse
     * @return true if the mouse is inside the plane's bounds, false otherwise
     */
    private boolean isMouseInsidePlane(double mouseX, double mouseY) {
        double planeMinX = user.getHitbox().getBoundsInParent().getMinX();
        double planeMaxX = user.getHitbox().getBoundsInParent().getMaxX();
        double planeMinY = user.getHitbox().getBoundsInParent().getMinY();
        double planeMaxY = user.getHitbox().getBoundsInParent().getMaxY();

        return mouseX >= planeMinX && mouseX <= planeMaxX && mouseY >= planeMinY && mouseY <= planeMaxY;
    }

    /**
     * Sets the paused state and disables/enables user interactions accordingly.
     *
     * @param paused true to pause the game, false to resume
     */
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

    /**
     * Fires a projectile from the user's plane if the game is not paused,
     * a cooldown period is not active, and the game is not over.
     *
     * <p>This method ensures that projectiles are only fired under appropriate
     * game conditions:
     * <ul>
     *   <li>The game must not be paused.</li>
     *   <li>The cooldown period must have elapsed since the last shot.</li>
     *   <li>The game must still be running (not in an end state).</li>
     * </ul>
     *
     * <p>If all conditions are met, the user's plane fires one or more projectiles,
     * which are added to the game root for rendering and to the list of active
     * user projectiles. Additionally, it triggers a cooldown period to prevent
     * excessive firing and plays a sound effect to indicate the action.
     */
    private void fireProjectile() {
        // Return early if the game is paused, a cooldown is active, or the game is over
        if (isPaused || isOnCooldown || gameIsOver) {
            return;
        }

        // Fire projectiles from the user's plane
        List<ActiveActorDestructible> projectiles = user.fireProjectiles();
        if (projectiles != null) {
            // Add each projectile to the game root and track it in the active list
            projectiles.forEach(projectile -> {
                root.getChildren().add(projectile);
                userProjectiles.add(projectile);
            });

            // Play the shooting sound effect
            ShootAudioManager.triggerShootAudio();
        }

        // Start the cooldown timer to prevent rapid firing
        startCooldown();
    }

    /**
     * Starts the cooldown timer to prevent continuous firing of projectiles.
     */
    private void startCooldown() {
        isOnCooldown = true;
        PauseTransition cooldownTimer = new PauseTransition(Duration.millis(BULLET_COOLDOWN_MS));
        cooldownTimer.setOnFinished(event -> isOnCooldown = false);
        cooldownTimer.play();
    }

    /**
     * Handles middle mouse button click to toggle the pause state.
     * @param event the mouse event triggered
     */
    public void handleMouseMiddleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE && !gameIsOver) {
            togglePause();
        }
    }

    /**
     * Handles the Enter key press to toggle the pause state.
     * @param event the key event triggered
     */
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !gameIsOver) {
            togglePause();
        }
    }

    /**
     * Toggles the pause menu visibility and pauses or resumes the game.
     */
    private void togglePause() {
        pauseMenuManager.togglePause();
    }

    /**
     * Sets the PauseMenuManager for managing the pause menu functionality.
     *
     * @param pauseMenuManager the pause menu manager to set
     */
    public void setPauseMenuManager(PauseMenuManager pauseMenuManager) {
        this.pauseMenuManager = pauseMenuManager;
    }

    /**
     * Starts the game loop for continuously updating user movement.
     */
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    updateUserMovement();
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Stops the game loop to terminate user input handling.
     */
    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * Sets the game over flag and ensures the user can't interact with the pause menu.
     * @param gameIsOver true if the game is over, false otherwise
     */
    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }

    /**
     * Clears all active key states to ensure no unwanted actions carry over.
     */
    public void clearActiveKeys() {
        activeKeys.clear();
    }
}