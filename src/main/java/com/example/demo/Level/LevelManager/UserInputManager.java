package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.UserPlane;
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
public class UserInputManager {
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

    /** A flag that indicates whether the system is able to accept user input. */
    private boolean canAcceptInput = false;

    /**
     * A {@link PauseTransition} that controls the delay before the system can accept user input.
     * The timer triggers after a set delay (2 seconds in this case) to enable input acceptance.
     */
    private PauseTransition inputDelayTimer;

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
        startInputDelay();
    }

    /**
     * Starts a timer with a 2-second delay before the system can accept input.
     * After the delay, input acceptance is enabled.
     */
    public void startInputDelay() {
        inputDelayTimer = new PauseTransition(Duration.seconds(2));
        inputDelayTimer.setOnFinished(event -> canAcceptInput = true);
        inputDelayTimer.play();
    }

    /**
     * Handles key press events by adding the pressed key to the active keys set.
     * Input is processed only if the game is not paused, input is accepted, and the game is not over.
     *
     * @param e the key event for the key press
     */
    public void handleKeyPressed(KeyEvent e) {
        if (isPaused || !canAcceptInput || gameIsOver) return; // Only process input when allowed
        activeKeys.add(e.getCode());
    }

    /**
     * Handles key release events by removing the released key from the active keys set.
     * Input is processed only if the game is not paused, input is accepted, and the game is not over.
     *
     * @param e the key event for the key release
     */
    public void handleKeyReleased(KeyEvent e) {
        if (isPaused || !canAcceptInput || gameIsOver) return; // Only process input when allowed
        activeKeys.remove(e.getCode());
    }

    /**
     * Updates the user's movement based on the active keys.
     * Moves the player up, down, left, or right, and allows firing projectiles if the spacebar is pressed.
     * Input is processed only if the system can accept input.
     */
    private void updateUserMovement() {
        if (!canAcceptInput || gameIsOver) return; // Wait until input delay is over

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
     * the cooldown has elapsed, and the game is still running.
     * <p>Fires one or more projectiles, adds them to the game,
     * plays the shoot sound, and triggers a cooldown for the next shot.</p>
     */
    private void fireProjectile() {
        // Check if the game is paused, on cooldown, or over
        if (isPaused || isOnCooldown || gameIsOver) {
            return;
        }

        // Fire projectiles and add them to the game root
        List<ActiveActorDestructible> projectiles = user.fireProjectiles();
        if (projectiles != null && !projectiles.isEmpty()) {
            projectiles.forEach(projectile -> {
                root.getChildren().add(projectile);  // Add to game UI
                userProjectiles.add(projectile);     // Track active projectiles
            });

            // Play shoot sound and start cooldown
            AudioManager.getInstance().triggerShootAudio();
            startCooldown(calculateCooldownBasedOnPower(user.getCurrentProjectilePowerLevel()));
        }
    }

    /**
     * Calculates the cooldown period based on the user's projectile power level.
     *
     * @param powerLevel The power level of the user's projectile.
     * @return The cooldown time in milliseconds.
     */
    private int calculateCooldownBasedOnPower(int powerLevel) {
        switch (powerLevel) {
            case 1: return 500;
            case 2: return 400;
            case 3: return 300;
            case 4: return 200;
            case 5: return 100;
            default: return 500;
        }
    }

    /**
     * Starts a cooldown timer to prevent continuous projectile firing.
     *
     * @param cooldownDuration The cooldown duration in milliseconds.
     */
    private void startCooldown(int cooldownDuration) {
        isOnCooldown = true;
        PauseTransition cooldownTimer = new PauseTransition(Duration.millis(cooldownDuration));
        cooldownTimer.setOnFinished(event -> isOnCooldown = false); // Reset cooldown after duration
        cooldownTimer.play();
    }

    /**
     * Handles middle mouse button click to toggle the pause state.
     * @param event the mouse event triggered
     */
    public void handleMouseMiddleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE && !gameIsOver && canAcceptInput) {
            togglePause();
        }
    }

    /**
     * Handles the Enter key press to toggle the pause state.
     * @param event the key event triggered
     */
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !gameIsOver && canAcceptInput) {
            togglePause();
        }
    }

    /**
     * Toggles the pause menu visibility and pauses or resumes the game.
     * Clears active keys to prevent input during the pause state.
     */
    private void togglePause() {
        pauseMenuManager.togglePause();
        this.clearActiveKeys();
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