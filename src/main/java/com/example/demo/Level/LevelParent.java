package com.example.demo.Level;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.AmmoBox;
import com.example.demo.Display.LevelView;
import com.example.demo.Object.Boss;
import com.example.demo.Object.FighterPlane;
import com.example.demo.Object.UserPlane;
import com.example.demo.controller.Control_EndGameMenu;
import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.input.*;
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

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private List<AmmoBox> ammoBoxes;
	private Random random = new Random();

	private static final double SPAWN_PROBABILITY = 0.005;  // 每次更新时，生成 AmmoBox 的概率（0 到 1 之间）

	private boolean isSpacePressed = false;  // 标志位，用来判断空格是否按下

	private int currentNumberOfEnemies;
	private LevelView levelView;

	private static final String EXPLOSION_SOUND_PATH = "/com/example/demo/sounds/explosion.mp3";
	private static AudioClip explosionSound;
	private static boolean explosionSoundEnabled = true;

	private boolean isPaused = false;
	private Control_PauseMenu Control_PauseMenu;
	private Parent pauseMenuRoot;

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

		initializeTimeline();
		friendlyUnits.add(user);
		explosionSound = new AudioClip(getClass().getResource(EXPLOSION_SOUND_PATH).toExternalForm());

		// 只加载一次 FXML 文件
		loadPauseMenu();
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	// 单独一个方法来加载暂停菜单
	private void loadPauseMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/PauseMenu/PauseMenu.fxml"));
			pauseMenuRoot = loader.load();
			Control_PauseMenu = loader.getController();
			Control_PauseMenu.initialize(this);  // 传递当前游戏的引用
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();

		// 设置鼠标事件监听
		scene.setOnMouseClicked(this::handleMouseMiddleClick);

		// 添加暂停菜单到场景中
		root.getChildren().add(pauseMenuRoot);

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
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleUserPlaneAndAmmoBoxCollisions(user, ammoBoxes);
		removeAllDestroyedActors();
		removeAmmoBox();
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
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
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
		// 筛选出已销毁的演员
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());

		destroyedActors.forEach(actor -> {
			// 调用 destroy 方法
			actor.destroy();

			// 处理爆炸效果
			if (actor instanceof FighterPlane) {
				handleExplosionEffect((FighterPlane) actor);
			}

			// 从场景中移除
			removeActorFromScene(actor);
		});

		// 从列表中移除已销毁的演员
		actors.removeAll(destroyedActors);
	}

	// 处理爆炸效果
	private void handleExplosionEffect(FighterPlane fighterPlane) {
		if (LevelParent.isExplosionSoundEnabled()) {
			explosionSound.play();
		}

		// 计算飞机的场景位置
		double actorX = fighterPlane.localToScene(fighterPlane.getBoundsInLocal()).getMinX();
		double actorY = fighterPlane.localToScene(fighterPlane.getBoundsInLocal()).getMinY();

		// 创建并添加爆炸图片
		ImageView explosionImage = new ImageView(new Image(
				getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm()));
		explosionImage.setFitWidth(150);
		explosionImage.setFitHeight(150);
		explosionImage.setX(actorX);
		explosionImage.setY(actorY);

		root.getChildren().add(explosionImage);

		// 定时移除爆炸图片
		Timeline removeExplosion = new Timeline(new KeyFrame(Duration.seconds(1), e -> root.getChildren().remove(explosionImage)));
		removeExplosion.setCycleCount(1);
		removeExplosion.play();
	}

	public static void setExplosionSoundEnabled(boolean enabled) {
		explosionSoundEnabled = enabled; // 正确保存音效状态
	}

	// 播放爆炸音效的方法（根据爆炸音效的开关来决定是否播放）
	public static void playExplosionSound() {
		if (explosionSoundEnabled) {
			explosionSound.play();
		}
	}

	public static boolean isExplosionSoundEnabled() {
		return explosionSoundEnabled;
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor instanceof ActiveActor && otherActor instanceof ActiveActor) {
					Node actorHitbox = ((ActiveActor) actor).getHitbox();
					Node otherActorHitbox = ((ActiveActor) otherActor).getHitbox();
					if (actorHitbox.getBoundsInParent().intersects(otherActorHitbox.getBoundsInParent())) {
						actor.takeDamage();
						otherActor.takeDamage();
					}
				}
			}
		}
	}

	private void handleUserPlaneAndAmmoBoxCollisions(UserPlane userPlane, List<AmmoBox> ammoBoxes) {
		Iterator<AmmoBox> iterator = ammoBoxes.iterator();
		while (iterator.hasNext()) {
			AmmoBox ammoBox = iterator.next();
			if (userPlane.checkCollision(ammoBox)) {
				handleAmmoBoxPickup(userPlane, ammoBox);
				iterator.remove();
			}
		}
	}

	private void handleAmmoBoxPickup(UserPlane userPlane, AmmoBox ammoBox) {
		userPlane.upgradeProjectile();
		ammoBox.destroy();
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
		Scene currentScene = root.getScene();  // 获取当前 Scene
		if (currentScene != null) {
			return (Stage) currentScene.getWindow();
		}
		return null;  // 如果没有找到 Stage，返回 null
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

	protected UserPlane getUser() {
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

	private void handleMouseMiddleClick(MouseEvent event) {
		if (event.getButton() == MouseButton.MIDDLE) {
			togglePause();
		}
	}

	// 随机生成 AmmoBox 的方法
	private void spawnRandomAmmoBox() {
		// 使用随机概率来决定是否生成 AmmoBox
		if (random.nextDouble() < SPAWN_PROBABILITY) {
			// 随机生成 AmmoBox 的位置
			double randomX = random.nextDouble(screenWidth);  // 随机 X 位置
			double randomY = random.nextDouble(screenWidth);  // 随机 Y 位置

			// 创建 AmmoBox 并设置位置
			AmmoBox ammoBox = new AmmoBox(randomX, randomY, this);

			// 添加到场景中
			addAmmoBox(ammoBox);
		}
	}

	// 添加 AmmoBox 的方法（参考之前的逻辑）
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

	// 切换暂停和继续
	private void togglePause() {
		if (isPaused) {
			continueGame();
		} else {
			pauseGame();
		}
	}

	public void pauseGame() {
		isPaused = true;
		timeline.stop(); // 停止游戏循环
		user.setPaused(true);  // 暂停时禁用输入

		// 禁用按键事件
		scene.setOnKeyPressed(event -> {
			// 暂停时不响应任何按键
		});

		// 为背景和所有活跃的 Actor 添加虚化效果
		GaussianBlur blur = new GaussianBlur(10);
		background.setEffect(blur);
		applyBlurToActiveActors(10);  // 给所有 Actor 加上虚化效果

		// 显示暂停菜单
		Control_PauseMenu.showPauseMenu();
	}

	public void continueGame() {
		isPaused = false;
		timeline.play(); // 恢复游戏循环
		user.setPaused(false);

		// 移除背景的虚化效果
		background.setEffect(null);

		// 移除ActiveActor的虚化效果
		removeBlurFromActiveActors();

		// 隐藏暂停菜单
		Control_PauseMenu.showPauseMenu(); // 隐藏暂停界面
	}

	// 假设这是LevelParent中的applyBlurToActiveActors方法
	private void applyBlurToActiveActors(double radius) {
		// 为所有 ActiveActor 对象添加虚化效果
		List<ActiveActorDestructible> allActors = new ArrayList<>();
		allActors.addAll(friendlyUnits);    // 友军单位
		allActors.addAll(enemyUnits);       // 敌军单位
		allActors.addAll(userProjectiles);  // 用户子弹
		allActors.addAll(enemyProjectiles); // 敌方子弹

		for (ActiveActorDestructible actor : allActors) {
			if (actor instanceof Node) {
				((Node) actor).setEffect(new GaussianBlur(radius));
			}

			// 检查是否是Boss对象，并对Boss的血条和护盾应用虚化
			if (actor instanceof Boss) {
				Boss boss = (Boss) actor;

				// 对Boss的血条进行虚化
				if (boss.getHealthBar() != null) {
					boss.getHealthBar().setEffect(new GaussianBlur(radius));
				}

				// 对Boss的护盾进行虚化
				if (boss.getShieldImage() != null) {
					boss.getShieldImage().setEffect(new GaussianBlur(radius));
				}
			}
		}
	}

	// 恢复虚化效果的代码（移除虚化）
	private void removeBlurFromActiveActors() {
		List<ActiveActorDestructible> allActors = new ArrayList<>();
		allActors.addAll(friendlyUnits);
		allActors.addAll(enemyUnits);
		allActors.addAll(userProjectiles);
		allActors.addAll(enemyProjectiles);

		for (ActiveActorDestructible actor : allActors) {
			if (actor instanceof Node) {
				((Node) actor).setEffect(null); // 清除虚化效果
			}

			// 对Boss的血条和护盾移除虚化效果
			if (actor instanceof Boss) {
				Boss boss = (Boss) actor;

				// 移除Boss的血条虚化
				if (boss.getHealthBar() != null) {
					boss.getHealthBar().setEffect(null);
				}

				// 移除Boss的护盾虚化
				if (boss.getShieldImage() != null) {
					boss.getShieldImage().setEffect(null);
				}
			}
		}
	}

	public void removeAmmoBox() {
		getRoot().getChildren().removeIf(node ->
				node instanceof AmmoBox && !node.isVisible()
		);
	}

	// 从场景中移除对象
	private void removeActorFromScene(ActiveActorDestructible actor) {
		root.getChildren().remove(actor);          // 移除演员本体
		root.getChildren().remove(actor.getHitbox()); // 移除演员的 hitbox
	}

	public void cleanUp() {
		// 停止时间线，停止所有动画
		timeline.stop();

		// 清除所有在 Group 中的节点，避免清理和 Stage 相关的 UI 元素
		root.getChildren().clear();

		// 清理所有与游戏相关的列表
		cleanUpActors();

		// 清理与音效相关的资源
		if (explosionSound != null) {
			explosionSound.stop();   // 停止播放爆炸音效
		}

		// 如果有虚化效果，移除所有虚化效果
		removeBlurFromActiveActors();

		// 确保暂停菜单的清理
		if (pauseMenuRoot != null) {
			pauseMenuRoot.setVisible(false);  // 隐藏暂停菜单
			pauseMenuRoot = null;  // 清理引用，防止内存泄漏
		}
	}

	// 清理所有的 Actor 对象（包括 Boss）
	private void cleanUpActors() {
		// 清空友军、敌军、子弹列表的同时，也要从场景中移除它们
		for (ActiveActorDestructible actor : friendlyUnits) {
			if (actor instanceof Node) {
				root.getChildren().remove(actor); // 从场景中移除
			}
		}
		for (ActiveActorDestructible actor : enemyUnits) {
			if (actor instanceof Node) {
				root.getChildren().remove(actor); // 从场景中移除
			}
		}
		for (ActiveActorDestructible actor : userProjectiles) {
			if (actor instanceof Node) {
				root.getChildren().remove(actor); // 从场景中移除
			}
		}
		for (ActiveActorDestructible actor : enemyProjectiles) {
			if (actor instanceof Node) {
				root.getChildren().remove(actor); // 从场景中移除
			}
		}

		// 清空列表
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();
	}

}