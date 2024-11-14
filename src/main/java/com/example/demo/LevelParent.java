package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.input.*;
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
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
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
			// 如果是敌机或我方飞机类型，则播放爆炸声音和显示爆炸效果
			if (actor instanceof FighterPlane) {
				// 只有在 explosionSoundEnabled 为 true 时才播放音效
				if (LevelParent.isExplosionSoundEnabled()) {
					explosionSound.play();
				}

				// 获取飞机的相对于场景的绝对位置
				double actorX = actor.localToScene(actor.getBoundsInLocal()).getMinX();
				double actorY = actor.localToScene(actor.getBoundsInLocal()).getMinY();

				// 创建爆炸图片
				ImageView explosionImage = new ImageView(new Image(getClass().getResource("/com/example/demo/images/explosion.png").toExternalForm()));
				explosionImage.setFitWidth(150);  // 设置爆炸图片的宽度
				explosionImage.setFitHeight(150); // 设置爆炸图片的高度
				explosionImage.setX(actorX);  // 使用飞机的位置
				explosionImage.setY(actorY);  // 使用飞机的位置

				// 将爆炸图片添加到场景中
				root.getChildren().add(explosionImage);

				// 使用 Timeline 在 1 秒后移除爆炸图片
				Timeline removeExplosion = new Timeline(new KeyFrame(Duration.seconds(1), e -> root.getChildren().remove(explosionImage)));
				removeExplosion.setCycleCount(1);
				removeExplosion.play();
			}

			// 移除飞机
			root.getChildren().remove(actor);
		});

		actors.removeAll(destroyedActors);
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
		cleanUp();
		levelView.showWinImage();
	}

	protected void loseGame() {
		cleanUp();
		levelView.showGameOverImage();
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

	//clean up
	public void cleanUp() {
		timeline.stop();
		root.getChildren().clear();
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();
	}

}