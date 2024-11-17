package com.example.demo.Level;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.*;
import com.example.demo.Display.LevelView;
import com.example.demo.controller.Control_EndGameMenu;
import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

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

	private static final String EXPLOSION_SOUND_PATH = "/com/example/demo/sounds/explosion.mp3";
	private static AudioClip explosionSound;
	private static boolean explosionSoundEnabled = true;

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

		initializeTimeline();
		friendlyUnits.add(user);
		explosionSound = new AudioClip(getClass().getResource(EXPLOSION_SOUND_PATH).toExternalForm());
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
		// 检查游戏是否暂停，如果是，直接返回，不发射子弹
		if (isPaused) {
			return;
		}

		// 发射子弹的正常逻辑
		List<ActiveActorDestructible> projectiles = user.fireProjectiles(); // 修改为返回列表
		if (projectiles != null) {
			projectiles.forEach(projectile -> {
				root.getChildren().add(projectile); // 添加到场景
				userProjectiles.add(projectile);    // 添加到用户子弹列表
			});
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
			if (enemy instanceof FighterPlane) {
				List<ActiveActorDestructible> projectiles = ((FighterPlane) enemy).fireProjectiles(); // 调用 fireProjectiles()
				if (projectiles != null) {
					projectiles.forEach(this::spawnEnemyProjectile); // 添加每个子弹
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
				handleExplosionEffect((FighterPlane) actor);
			}

			removeActorFromScene(actor);
		});

		actors.removeAll(destroyedActors);
	}

	private void handleExplosionEffect(FighterPlane fighterPlane) {
		if (LevelParent.isExplosionSoundEnabled()) {
			explosionSound.play();
		}

		double actorX = fighterPlane.localToScene(fighterPlane.getBoundsInLocal()).getMinX();
		double actorY = fighterPlane.localToScene(fighterPlane.getBoundsInLocal()).getMinY();

		ImageView explosionImage = new ImageView(new Image(
				getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm()));
		explosionImage.setFitWidth(150);
		explosionImage.setFitHeight(150);
		explosionImage.setX(actorX);
		explosionImage.setY(actorY);

		root.getChildren().add(explosionImage);

		Timeline removeExplosion = new Timeline(new KeyFrame(Duration.seconds(1), e -> root.getChildren().remove(explosionImage)));
		removeExplosion.setCycleCount(1);
		removeExplosion.play();
	}

	public static void setExplosionSoundEnabled(boolean enabled) {
		explosionSoundEnabled = enabled;
	}

	public static void playExplosionSound() {
		if (explosionSoundEnabled) {
			explosionSound.play();
		}
	}

	public static boolean isExplosionSoundEnabled() {
		return explosionSoundEnabled;
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

	protected void winGame() {
		Stage currentStage = getCurrentStage();

		Platform.runLater(() -> {
			cleanUp();
			levelView.showWinImage();

			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished(event -> showEndGameMenu(currentStage, true));
			delay.play();
		});
	}

	protected void loseGame() {
		Stage currentStage = getCurrentStage();

		Platform.runLater(() -> {
			cleanUp();
			levelView.showGameOverImage();

			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished(event -> showEndGameMenu(currentStage, false));
			delay.play();
		});
	}

	private Stage getCurrentStage() {
		Scene currentScene = root.getScene();
		if (currentScene != null) {
			return (Stage) currentScene.getWindow();
		}
		return null;
	}

	private void showEndGameMenu(Stage stage, boolean isWin) {
		try {
			// 加载 EndGameMenu 控制器并初始化
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/EndGameMenu/EndGameMenu.fxml"));
			Parent endGameRoot = loader.load();

			// 获取 EndGameMenu 控制器并初始化
			Control_EndGameMenu controller = loader.getController();
			controller.initialize(this);  // 将 LevelParent 对象传入控制器

			// 创建新场景并设置为当前 stage
			Scene endGameScene = new Scene(endGameRoot);
			stage.setScene(endGameScene);  // 替换场景
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		if (!enemyUnits.contains(enemy)) { // 检查敌人列表中是否已经存在该敌人
			enemyUnits.add(enemy);
			if (!getRoot().getChildren().contains(enemy)) { // 确认没有重复添加到场景中
				getRoot().getChildren().add(enemy);
			}
			// 添加 hitbox 到场景中，确保不重复
			if (enemy instanceof ActiveActor) {
				Node hitbox = ((ActiveActor) enemy).getHitbox();
				if (!getRoot().getChildren().contains(hitbox)) {
					getRoot().getChildren().add(hitbox);
				}
			}
		}
	}


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

	private void spawnRandomAmmoBox() {
		if (random.nextDouble() < AmmoBox_SPAWN_PROBABILITY) {
			double randomX = random.nextDouble(screenWidth);
			double randomY = random.nextDouble(screenWidth);

			AmmoBox ammoBox = new AmmoBox(randomX, randomY, this);

			addAmmoBox(ammoBox);
		}
	}

	protected void addAmmoBox(AmmoBox ammoBox) {
		if (!ammoBoxes.contains(ammoBox)) {
			ammoBoxes.add(ammoBox);
			if (!getRoot().getChildren().contains(ammoBox)) {
				getRoot().getChildren().add(ammoBox);
			}

			Node hitbox = ammoBox.getHitbox();
			if (!getRoot().getChildren().contains(hitbox)) {
				getRoot().getChildren().add(hitbox);
			}
		}
	}

	private void removeActorFromScene(ActiveActorDestructible actor) {
		root.getChildren().remove(actor);
		root.getChildren().remove(actor.getHitbox());
	}

	private static final Boundary RIGHT_BOUNDARY = new Boundary(1350, 0, 1, 900);
	private static final Boundary LEFT_BOUNDARY = new Boundary(-50, 0, 1, 900);
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