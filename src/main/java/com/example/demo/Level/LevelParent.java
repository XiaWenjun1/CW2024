package com.example.demo.Level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.*;
import com.example.demo.Display.LevelView;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 16;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private PauseMenuManager pauseMenuManager;
	private EndGameMenuManager endGameMenuManager;
	private UserInputManager userInputManager;
	private ActiveActorManager activeActorManager;
	private CleanDestroyedManager cleanDestroyedManager;

	private final Random random = new Random();

	private static final double AmmoBox_SPAWN_PROBABILITY = 0.01;
	private static final double Heart_SPAWN_PROBABILITY = 0.01;

	private int currentNumberOfEnemies;
	private final LevelView levelView;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, this);
		this.activeActorManager = new ActiveActorManager();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;

		this.userInputManager = new UserInputManager(user, root, activeActorManager.getUserProjectiles(), null);
		this.pauseMenuManager = new PauseMenuManager(timeline, scene, user, background, activeActorManager, userInputManager);
		this.userInputManager.setPauseMenuManager(pauseMenuManager);
		pauseMenuManager.loadPauseMenu();
		this.endGameMenuManager = new EndGameMenuManager(this);
		this.cleanDestroyedManager = new CleanDestroyedManager(root, activeActorManager);

		initializeTimeline();
		activeActorManager.getFriendlyUnits().add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();

		scene.setOnMouseClicked(event -> userInputManager.handleMouseMiddleClick(event));
		pauseMenuManager.loadPauseMenu();
		root.getChildren().add(pauseMenuManager.getPauseMenuRoot());

		return scene;
	}

	private void updateScene() {
		updateActors();
		spawnRandomItems();
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

	private void updateActors() {
		activeActorManager.updateActors();
		updateNumberOfEnemies();
		spawnEnemyUnits();
		generateEnemyFire();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = activeActorManager.getEnemyUnits().size();
	}

	protected abstract void spawnEnemyUnits();

	private void generateEnemyFire() {
		activeActorManager.getEnemyUnits().forEach(enemy -> {
			if (enemy instanceof FighterPlane) {
				List<ActiveActorDestructible> projectiles = ((FighterPlane) enemy).fireProjectiles();
				if (projectiles != null) {
					projectiles.forEach(this::spawnEnemyProjectile);
				}
			}
		});
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			activeActorManager.getEnemyProjectiles().add(projectile);
		}
	}

	private void spawnRandomItems() {
		spawnRandomAmmoBox();
		spawnRandomHeart();
	}

	protected void spawnRandomAmmoBox() {
		if (random.nextDouble() < AmmoBox_SPAWN_PROBABILITY) {
			double randomX = random.nextDouble(screenWidth);
			double randomY = random.nextDouble(screenWidth);
			AmmoBox ammoBox = new AmmoBox(randomX, randomY, this);
			addAmmoBox(ammoBox);
		}
	}

	protected void spawnRandomHeart() {
		if (random.nextDouble() < Heart_SPAWN_PROBABILITY) {
			double randomX = random.nextDouble(screenWidth);
			double randomY = random.nextDouble(screenWidth);
			Heart heart = new Heart(randomX, randomY, this);
			addHeart(heart);
		}
	}

	private void addActorToScene(ActiveActorDestructible actor, List<ActiveActorDestructible> actorList) {
		actorList.add(actor);
		root.getChildren().add(actor);
		Node hitbox = actor.getHitbox();
		root.getChildren().add(hitbox);
	}

	protected void addAmmoBox(AmmoBox ammoBox) {
		addActorToScene(ammoBox, activeActorManager.getAmmoBoxes());
	}

	protected void addHeart(Heart heart) {
		addActorToScene(heart, activeActorManager.getHearts());
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

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		activeActorManager.getEnemyUnits().add(enemy);
		getRoot().getChildren().add(enemy);
		Node hitbox = ((ActiveActor) enemy).getHitbox();
		getRoot().getChildren().add(hitbox);
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
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - activeActorManager.getEnemyUnits().size(); i++) {
			user.incrementKillCount();
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.addHearts(user.getHealth());
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
		setChanged();
		notifyObservers(levelName);
	}

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

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}
}