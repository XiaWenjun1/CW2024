package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelManager.*;
import com.example.demo.Level.LevelView.LevelView;
import com.example.demo.Actor.UserPlane.UserPlane;
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.util.Duration;

/**
 * Abstract class representing the parent level in the game. It manages game flow, such as
 * initializing friendly and enemy units, handling collisions, and updating the game status.
 */
public abstract class LevelParent {
	/**
	 * Constant for game loop delay in milliseconds. This value controls the time interval between each game loop cycle.
	 */
	private static final int MILLISECOND_DELAY = 16;
	/**
	 * The height of the screen in pixels.
	 */
	private final double screenHeight;
	/**
	 * The width of the screen in pixels.
	 */
	private final double screenWidth;
	/**
	 * The maximum Y position for enemy units to spawn on the screen.
	 */
	private final double enemyMaximumYPosition = 650;
	/**
	 * The minimum Y position for enemy units to spawn on the screen.
	 */
	private final double enemyMinimumYPosition = 55;
	/**
	 * The current number of enemy units in the level.
	 */
	private int currentNumberOfEnemies;
	/**
	 * The previous number of enemy units in the level, used to calculate kill counts.
	 */
	private int previousNumberOfEnemies;
	/**
	 * The root node of the scene, containing all visual elements of the level.
	 */
	private final Group root;
	/**
	 * The timeline for the game loop, which controls the update rate and timing of the game.
	 */
	private final Timeline timeline;
	/**
	 * The user's plane (player's character), which is controlled by the player during the game.
	 */
	private final UserPlane user;
	/**
	 * The scene that represents the level in the game, containing all visual and interactive elements.
	 */
	private final Scene scene;
	/**
	 * The background image for the level, displayed in the game scene.
	 */
	private final ImageView background;
	/**
	 * Manager for handling pause menu operations, such as displaying and hiding the pause menu.
	 */
	private final PauseMenuManager pauseMenuManager;
	/**
	 * Manager for handling end game menu operations, such as displaying the win or loss screen.
	 */
	private final EndGameMenuManager endGameMenuManager;
	/**
	 * Manager for processing user input, including key presses, mouse clicks, and pause actions.
	 */
	private final UserInputManager userInputManager;
	/**
	 * Manager for handling the active actors in the game, including both friendly and enemy units.
	 */
	private final ActiveActorManager activeActorManager;
	/**
	 * Manager for cleaning up destroyed actors and objects from the game scene.
	 */
	private final CleanDestroyedManager cleanDestroyedManager;
	/**
	 * Manager for spawning and controlling the behavior of enemy units within the level.
	 */
	private final ActorSpawnerManager actorSpawnerManager;
	/**
	 * View that handles the display of the level's UI elements, such as health and score.
	 */
	private final LevelView levelView;
	/**
	 * The name of the current level, represented as a {@link StringProperty} for easy data binding in the UI.
	 */
	private final StringProperty currentLevelName = new SimpleStringProperty();

	/**
	 * Constructs a new level with the specified background image, screen dimensions,
	 * and initial player health.
	 *
	 * @param backgroundImageName The name of the background image.
	 * @param screenHeight The height of the screen.
	 * @param screenWidth The width of the screen.
	 * @param playerInitialHealth The initial health of the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.activeActorManager = new ActiveActorManager();
		this.currentNumberOfEnemies = 0;
		this.previousNumberOfEnemies = 0;

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		this.levelView = instantiateLevelView();
		this.userInputManager = new UserInputManager(user, root, activeActorManager.getUserProjectiles(), null);
		this.pauseMenuManager = new PauseMenuManager(timeline, scene, userInputManager);
		this.userInputManager.setPauseMenuManager(pauseMenuManager);
		pauseMenuManager.loadPauseMenu();
		this.endGameMenuManager = new EndGameMenuManager(this);
		this.cleanDestroyedManager = new CleanDestroyedManager(root, activeActorManager);
		this.actorSpawnerManager = new ActorSpawnerManager(activeActorManager, user, root);

		initializeTimeline();
		activeActorManager.getFriendlyUnits().add(user);
	}

	/**
	 * Initializes friendly units (the player) for the level. This method must be implemented by subclasses.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Instantiates and returns a LevelView specific to the current level.
	 * This method must be implemented by subclasses.
	 *
	 * @return the LevelView for the current level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene with the background, friendly units, level view, and input handlers.
	 *
	 * @return the initialized scene.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		scene.setOnMouseClicked(userInputManager::handleMouseMiddleClick);
		scene.setOnKeyPressed(userInputManager::handleKeyPress);
		pauseMenuManager.loadPauseMenu();
		root.getChildren().add(pauseMenuManager.getPauseMenuRoot());
		return scene;
	}

	/**
	 * Updates the scene by spawning enemies, handling collisions, and updating game status.
	 */
	private void updateScene() {
		actorSpawnerManager.updateActors();
		spawnEnemyUnits();
		handleCollisionsAndPenetration();
		cleanUpDestroyedActors();
		updateGameStatus();
	}

	/**
	 * Initializes the game loop with a fixed time interval.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background image and adds it to the root.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(userInputManager::handleKeyPressed);
		background.setOnKeyReleased(userInputManager::handleKeyReleased);
		root.getChildren().add(background);
	}

	/**
	 * Handles both enemy penetration and collisions in the game.
	 * This method calls `handleEnemyPenetration` and `handleCollisions`.
	 */
	private void handleCollisionsAndPenetration() {
		handleEnemyPenetration();
		handleCollisions();
	}

	/**
	 * Handles enemy penetration by checking if any enemy has crossed the screen boundary.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : activeActorManager.getEnemyUnits()) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Determines if an enemy has penetrated the player's defenses (crossed the screen width).
	 *
	 * @param enemy the enemy to check.
	 * @return true if the enemy has penetrated the defenses, otherwise false.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Handles collisions between various game elements (enemies, user projectiles, etc.).
	 */
	private void handleCollisions() {
		CollisionManager.handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
		CollisionManager.handleUserProjectileCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
		CollisionManager.handleEnemyProjectileCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
		CollisionManager.handleUserPlaneAndAmmoBoxCollisions(user, activeActorManager.getAmmoBoxes());
		CollisionManager.handleUserPlaneAndHeartCollisions(user, activeActorManager.getHearts());
	}

	/**
	 * Cleans up destroyed actors to keep the game environment free of unnecessary elements.
	 */
	private void cleanUpDestroyedActors() {
		cleanDestroyedManager.removeAllDestroyedActors();
		cleanDestroyedManager.cleanObj();
	}

	/**
	 * Cleans up the level by stopping the game timeline, stopping the game loop,
	 * clearing any active input keys, and removing all UI components and actors
	 * associated with the current level.
	 * This method is typically called when transitioning to a new level or when the game is ending.
	 * It ensures that all active elements are cleared and that no processes (such as game loops or animations)
	 * continue running after the level has been cleaned up.
	 */
	public void cleanUp() {
		timeline.stop();
		userInputManager.stopGameLoop();
		userInputManager.clearActiveKeys();
		root.getChildren().clear();
		cleanUpActors();
	}

	/**
	 * Clears all actors from the game.
	 */
	private void cleanUpActors() {
		activeActorManager.getAllActors().clear();
	}

	/**
	 * Updates the game status by updating the number of enemies, kill count, and level view.
	 */
	private void updateGameStatus() {
		updateStatus();
		checkIfGameOver();
	}

	/**
	 * Updates the number of enemies, kill count, and the level view display.
	 */
	public void updateStatus() {
		updateNumberOfEnemies();
		updateKillCount();
		updateLevelView();
	}

	/**
	 * Updates the level view based on the current health of the user.
	 */
	public void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.addHearts(user.getHealth());
	}

	/**
	 * Updates the number of enemies currently in the level.
	 */
	private void updateNumberOfEnemies() {
		previousNumberOfEnemies = currentNumberOfEnemies;
		currentNumberOfEnemies = activeActorManager.getEnemyUnits().size();
	}

	/**
	 * Updates the kill count based on the number of defeated enemies.
	 */
	private void updateKillCount() {
		int defeatedEnemies = previousNumberOfEnemies - currentNumberOfEnemies;
		for (int i = 0; i < defeatedEnemies; i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Checks if the game is over by verifying if the player has been destroyed.
	 * This method must be implemented by subclasses.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Starts the game by playing the timeline (game loop).
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Ends the game with a victory, displaying the win screen to the player.
	 */
	public void winGame() {
		userInputManager.setGameIsOver(true);
		endGameMenuManager.winGame();
	}

	/**
	 * Ends the game with a defeat, displaying the loss screen to the player.
	 */
	public void loseGame() {
		userInputManager.setGameIsOver(true);
		endGameMenuManager.loseGame();
	}

	/**
	 * Transitions to the next level by cleaning up the current level and setting the next level's name.
	 *
	 * @param levelName The name of the next level.
	 */
	public void goToNextLevel(String levelName) {
		cleanUp();
		setCurrentLevelName(levelName);
	}

	/**
	 * Adds a new enemy unit to the level.
	 *
	 * @param enemy the enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		actorSpawnerManager.addEnemyUnit(enemy);
	}

	/**
	 * Spawns enemy units for the current level. This method must be implemented by subclasses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Returns the user plane (player's character).
	 *
	 * @return the user plane.
	 */
	public UserPlane getUser() {
		return user;
	}

	/**
	 * Returns the root of the level scene.
	 *
	 * @return the root node.
	 */
	public Group getRoot() {
		return root;
	}

	/**
	 * Returns the current number of enemies in the level.
	 *
	 * @return the current number of enemies.
	 */
	protected int getCurrentNumberOfEnemies() {
		return activeActorManager.getEnemyUnits().size();
	}

	/**
	 * Returns the LevelView associated with the level.
	 *
	 * @return the level view.
	 */
	public LevelView getLevelView() {
		return levelView;
	}

	/**
	 * Returns the maximum Y position for enemies.
	 *
	 * @return the maximum Y position.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Returns the minimum Y position for enemies.
	 *
	 * @return the minimum Y position.
	 */
	protected double getEnemyMinimumYPosition() {
		return enemyMinimumYPosition;
	}

	/**
	 * Returns the width of the screen.
	 *
	 * @return the screen width.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Returns whether the user has been destroyed.
	 *
	 * @return true if the user is destroyed, otherwise false.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Sets the name of the current level.
	 *
	 * @param levelName the level name.
	 */
	public void setCurrentLevelName(String levelName) {
		this.currentLevelName.set(levelName);
	}

	/**
	 * Gets the {@link StringProperty} representing the name of the current level.
	 * This property can be used for data binding or observing changes to the current level's name.
	 * @return the {@link StringProperty} representing the name of the current level
	 */
	public StringProperty currentLevelNameProperty() {
		return currentLevelName;
	}
}