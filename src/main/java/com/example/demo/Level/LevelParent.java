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
import javafx.scene.input.*;
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
	private boolean isPaused = false;
	private EndGameMenuManager endGameMenuManager;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> ammoBoxes;
	private final Random random = new Random();

	private static final double AmmoBox_SPAWN_PROBABILITY = 0.005;

	private boolean isSpacePressed = false;

	private int currentNumberOfEnemies;
	private final LevelView levelView;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth, this);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.ammoBoxes = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;

		pauseMenuManager = new PauseMenuManager(timeline, scene, user, background, friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, ammoBoxes);
		pauseMenuManager.loadPauseMenu();

		this.endGameMenuManager = new EndGameMenuManager(this);

		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();

		scene.setOnMouseClicked(this::handleMouseMiddleClick);
		pauseMenuManager.loadPauseMenu();
		root.getChildren().add(pauseMenuManager.getPauseMenuRoot());

		return scene;
	}

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

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		spawnRandomAmmoBox();
		handleEnemyPenetration();
		CollisionManager.handleCollisions(friendlyUnits, enemyUnits);
		CollisionManager.handleUserProjectileCollisions(userProjectiles, enemyUnits);
		CollisionManager.handleEnemyProjectileCollisions(enemyProjectiles, friendlyUnits);
		CollisionManager.handleUserPlaneAndAmmoBoxCollisions(user, ammoBoxes);
		removeAllDestroyedActors();
		cleanObj();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
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
		background.setOnKeyPressed(this::handleKeyPressed);
		background.setOnKeyReleased(this::handleKeyReleased);
		root.getChildren().add(background);
	}

	private void handleMouseMiddleClick(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			togglePause();
		}
	}

	public void togglePause() {
		pauseMenuManager.togglePause();
	}

	private void handleKeyPressed(KeyEvent e) {
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

	private void handleKeyReleased(KeyEvent e) {
		KeyCode kc = e.getCode();
		switch (kc) {
			case UP, DOWN -> user.stopVerticalMovement();
			case LEFT, RIGHT -> user.stopHorizontalMovement();
			case SPACE -> isSpacePressed = false;
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

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
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
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		ammoBoxes.forEach(box -> box.updateActor());
	}

	private void removeActorFromScene(ActiveActorDestructible actor) {
		root.getChildren().remove(actor);
		root.getChildren().remove(actor.getHitbox());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());

		destroyedActors.forEach(actor -> {
			actor.destroy();

			if (actor instanceof FighterPlane) {
				ExplosionEffectManager.triggerExplosionEffect(root, (FighterPlane) actor);
			}

			removeActorFromScene(actor);
		});

		actors.removeAll(destroyedActors);
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	public UserPlane getUser() {
		return user;
	}

	public Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
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

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		getRoot().getChildren().add(enemy);
		Node hitbox = ((ActiveActor) enemy).getHitbox();
		getRoot().getChildren().add(hitbox);
	}

	protected void spawnRandomAmmoBox() {
		if (random.nextDouble() < AmmoBox_SPAWN_PROBABILITY) {
			double randomX = random.nextDouble(screenWidth);
			double randomY = random.nextDouble(screenWidth);
			AmmoBox ammoBox = new AmmoBox(randomX, randomY, this);
			addAmmoBox(ammoBox);
		}
	}

	protected void addAmmoBox(AmmoBox ammoBox) {
		ammoBoxes.add(ammoBox);
		getRoot().getChildren().add(ammoBox);
		Node hitbox = ammoBox.getHitbox();
		getRoot().getChildren().add(hitbox);
	}

	private static final Boundary RIGHT_BOUNDARY = new Boundary(1350, 0, 1, 1000);
	private static final Boundary LEFT_BOUNDARY = new Boundary(-50, 0, 1, 1000);
	public void cleanObj() {
		CollisionManager.cleanObjects(
				userProjectiles,
				enemyProjectiles,
				ammoBoxes,
				RIGHT_BOUNDARY,
				LEFT_BOUNDARY,
				this::removeActorFromScene,
				CollisionManager::checkCollision
		);
	}

	public void cleanUp() {
		timeline.stop();
		root.getChildren().clear();
		cleanUpActors();
	}

	private void cleanUpActors() {
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();
	}
}