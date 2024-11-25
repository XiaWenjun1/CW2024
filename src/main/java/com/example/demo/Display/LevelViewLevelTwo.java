package com.example.demo.Display;

import com.example.demo.Object.Boss.Boss;
import com.example.demo.Object.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final BossHealthBar bossHealthBar;
    private final TargetLevel hint;

    public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBar = new BossHealthBar(bossHealth);
        this.hint = new TargetLevel();

        initializeUI();
    }

    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint);
        Platform.runLater(() -> {
            bossHealthBar.show();
            hint.show();
        });
    }

    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY();
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    public void showShield() {
        shieldImage.show();
    }

    public void hideShield() {
        shieldImage.hide();
    }

    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 65;
        bossHealthBar.setLayout(bossPositionX, bossPositionY);
    }

    public void updateBossHealth(int bossHealth) {
        bossHealthBar.updateHealth(bossHealth);
    }
}