package com.example.demo.Display;

import com.example.demo.Object.Boss.Boss;
import com.example.demo.Object.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.Map;

public class LevelViewLevelFour extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final Map<Boss, BossHealthBar> bossHealthBars;  // 使用 Map 存储Boss与其对应的血条
    private final TargetLevel hint;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int bossHealth) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBars = new HashMap<>();
        this.hint = new TargetLevel();

        initializeUI();  // 只初始化其他UI元素，而不初始化血条
    }

    private void initializeUI() {
        root.getChildren().addAll(shieldImage, hint);
        Platform.runLater(() -> {
            hint.show();
        });
    }

    // 更新Boss的血条
    public void updateBossHealth(int bossHealth, Boss boss) {
        if (!bossHealthBars.containsKey(boss)) {
            // 如果血条不存在，创建并添加到UI
            BossHealthBar healthBar = new BossHealthBar(bossHealth);
            bossHealthBars.put(boss, healthBar);
            root.getChildren().add(healthBar);
        }

        // 更新现有血条
        BossHealthBar healthBar = bossHealthBars.get(boss);
        Platform.runLater(() -> {
            healthBar.updateHealth(bossHealth);
        });
    }

    // 更新Boss的血条位置
    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 235;

        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.setLayout(bossPositionX, bossPositionY);
        }
    }

    // 更新护盾的位置
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX() + 90;
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 80;
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    // 显示护盾
    public void showShield() {
        shieldImage.show();
    }

    // 隐藏护盾
    public void hideShield() {
        shieldImage.hide();
    }

    // 隐藏Boss的血条
    public void hideBossHealthBar(Boss boss) {
        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.hide();
        }
    }

    // 显示Boss的血条
    public void showBossHealthBar(Boss boss) {
        if (bossHealthBars.containsKey(boss)) {
            BossHealthBar healthBar = bossHealthBars.get(boss);
            healthBar.show();
        }
    }
}
