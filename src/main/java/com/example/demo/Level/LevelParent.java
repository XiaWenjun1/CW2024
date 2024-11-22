package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelManager.*;
import com.example.demo.Display.LevelView;
import com.example.demo.Object.UserPlane.UserPlane;
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent {
	private static final int MILLISECOND_DELAY = 16;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition = 650;
	private final double enemyMinimumYPosition = 55;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final PauseMenuManager pauseMenuManager;
	private final EndGameMenuManager endGameMenuManager;
	private final UserInputManager userInputManager;
	private final ActiveActorManager activeActorManager;
	private final CleanDestroyedManager cleanDestroyedManager;
	private final GameStateManager gameStateManager;
	private final ActorSpawnerManager actorSpawnerManager;
	private final LevelView levelView;

	private final StringProperty currentLevelName = new SimpleStringProperty();

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, this);
		this.activeActorManager = new ActiveActorManager();

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
		this.gameStateManager = new GameStateManager(user, levelView, activeActorManager);
		this.actorSpawnerManager = new ActorSpawnerManager(activeActorManager, root, this);

		initializeTimeline();
		activeActorManager.getFriendlyUnits().add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		scene.setOnMouseClicked(userInputManager::handleMouseMiddleClick);
		pauseMenuManager.loadPauseMenu();
		root.getChildren().add(pauseMenuManager.getPauseMenuRoot());
		return scene;
	}

	private void updateScene() {
		actorSpawnerManager.updateActors();
		handleCollisionsAndPenetration();
		cleanUpDestroyedActors();
		updateGameStatus();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(userInputManager::handleKeyPressed);
		background.setOnKeyReleased(userInputManager::handleKeyReleased);
		root.getChildren().add(background);
	}

	private void handleCollisionsAndPenetration() {
		handleEnemyPenetration();
		handleCollisions();
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : activeActorManager.getEnemyUnits()) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	private void handleCollisions() {
		CollisionManager.handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
		CollisionManager.handleUserProjectileCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
		CollisionManager.handleEnemyProjectileCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
		CollisionManager.handleUserPlaneAndAmmoBoxCollisions(user, activeActorManager.getAmmoBoxes());
		CollisionManager.handleUserPlaneAndHeartCollisions(user, activeActorManager.getHearts());
	}

	private void cleanUpDestroyedActors() {
		cleanDestroyedManager.removeAllDestroyedActors();
		cleanDestroyedManager.cleanObj();
	}

	public void cleanUp() {
		timeline.stop();
		root.getChildren().clear();
		cleanUpActors();
	}

	private void cleanUpActors() {
		activeActorManager.getAllActors().clear();
	}

	private void updateGameStatus() {
		gameStateManager.updateStatus();
		checkIfGameOver();
	}

	protected abstract void checkIfGameOver();

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void winGame() {
		endGameMenuManager.winGame();
	}

	public void loseGame() {
		endGameMenuManager.loseGame();
	}

	public void goToNextLevel(String levelName) {
		cleanUp();
		setCurrentLevelName(levelName);
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		actorSpawnerManager.addEnemyUnit(enemy);
	}

	protected abstract void spawnEnemyUnits();

	public void spawnEnemies() { spawnEnemyUnits();}

	public UserPlane getUser() {
		return user;
	}

	public Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return activeActorManager.getEnemyUnits().size();
	}

	public LevelView getLevelView() { return levelView; }

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getEnemyMinimumYPosition() {
		return enemyMinimumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	public void setCurrentLevelName(String levelName) {
		this.currentLevelName.set(levelName);
	}

	public StringProperty currentLevelNameProperty() {
		return currentLevelName;
	}
}